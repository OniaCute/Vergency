package cc.vergency.features.managers.player;

import cc.vergency.Vergency;
import cc.vergency.features.event.eventbus.EventHandler;
import cc.vergency.features.event.events.game.PacketEvent;
import cc.vergency.features.event.events.game.TickEvent;
import cc.vergency.features.event.events.player.AntiCheatModifiedEvent;
import cc.vergency.features.event.events.player.PlayerSyncEvent;
import cc.vergency.features.event.events.player.PlayerTickEvent;
import cc.vergency.features.event.events.render.Render3DEvent;
import cc.vergency.features.managers.client.NotificationManager;
import cc.vergency.features.value.player.Rotation;
import cc.vergency.modules.client.AntiCheat;
import cc.vergency.utils.interfaces.Wrapper;
import cc.vergency.utils.others.FastTimerUtil;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.jetbrains.annotations.Nullable;

import java.util.PriorityQueue;

public class RotateManager implements Wrapper {
    public PriorityQueue<Rotation> rotations = new PriorityQueue<>();
    public Rotation currentRotation = null;
    public float clientYaw, clientPitch; // client rot
    public float expectedServerYaw, expectedServerPitch; // modules expected rot value
    public float recordedYaw, recordedPitch; // the original rotation value for snapback
    public float realServerYaw, realServerPitch; // the real rot in server (it will be different from the expected rot if the anti cheat modifies the player's rot)
    private final FastTimerUtil syncTimer = new FastTimerUtil();

    public RotateManager() {
        Vergency.EVENTBUS.subscribe(this);
    }

    @EventHandler
    public void onReceivePackets(PacketEvent.Receive event) {
        if (event.getPacket() instanceof PlayerMoveC2SPacket packet && packet.changesLook()) {
            realServerYaw = packet.getYaw(0);
            realServerPitch = packet.getPitch(0);
        }
    }

    @EventHandler
    public void onSync(PlayerSyncEvent event) {
        if (mc.player == null) {
            return ;
        }

        // whatever the client rotate event is force sync
        clientYaw = event.getYaw();
        clientPitch = event.getPitch();

        if (currentRotation != null && currentRotation.getProgress()) {
            event.setYaw(expectedServerYaw);
            event.setPitch(expectedServerPitch);
        } else {
            event.setYaw(getPlayerYaw());
            event.setPitch(getPlayerPitch());
        }
    }

    @EventHandler
    public void onPlayerTick(PlayerTickEvent event) {
        if (mc.player == null || mc.world == null) {
            return ;
        }

        // Anti Cheat Check (Compare rotation recorded and real server rotation)
        if (expectedServerPitch != realServerPitch || expectedServerYaw != realServerYaw) {
            Vergency.EVENTBUS.post(new AntiCheatModifiedEvent.Rotate(realServerYaw, realServerPitch));
            if (AntiCheat.INSTANCE.rotateCancelable.getValue()) {
                currentRotation.setSuccess(false); // failed by anti cheat
                pop(true); // pop current rotation task
            }
        }

        if (AntiCheat.INSTANCE.rotateTickMode.getValue().equals(AntiCheat.RotateTick.Ticks)) {
            action();
        }
    }

    @EventHandler
    public void onRender3D(Render3DEvent e) {
        if (AntiCheat.INSTANCE.rotateTickMode.getValue().equals(AntiCheat.RotateTick.Render)) {
            action();
        }
    }

    private void action() {
        if (mc.player == null) {
            return;
        }
        if (rotations.isEmpty() && currentRotation == null) {
            return;
        }
        Rotation cur = currentRotation == null ? rotations.poll() : currentRotation;
        if (cur == null) {
            return;
        }
        if (!cur.getProgress()) {
            cur.setProgress(true);
            cur.getTimer().reset();
        }
        float srcYaw = cur.isWithClient() ? getPlayerYaw()   : realServerYaw;
        float srcPitch = cur.isWithClient() ? getPlayerPitch() : realServerPitch;
        if (cur.isNearlyThere(srcYaw, srcPitch)) {
            finishRotation(cur);
            return;
        }
        Rotation step = calcBySpeed(cur, cur.getSpeed());
        if (cur.isWithClient()) {
            clientYaw   = step.getYaw();
            clientPitch = step.getPitch();
            setPlayerYaw(clientYaw);
            setPlayerPitch(clientPitch);
        } else {
            expectedServerYaw   = step.getYaw();
            expectedServerPitch = step.getPitch();
            serverRotate(step);
        }
        currentRotation = step;
    }

    @Nullable
    public Rotation getCurrentRotation() {
        return getCurrentRotation(false);
    }

    @Nullable
    public Rotation getCurrentRotation(boolean remove) {
        if (remove) {
            return rotations.poll();
        }
        return rotations.peek();
    }

    public void clear() {
        rotations.clear();
    }

    public void pop() {
        this.pop(false);
    }

    public void pop(boolean force) {
        if (rotations.isEmpty()) {
            return ;
        }
        if (!rotations.peek().getProgress() || force) {
            Rotation rotation = rotations.poll();
            if (rotation != null) {
                rotation.setProgress(false);
            }
        }
    }

    public void sync(SyncType type) {
        if (rotations.isEmpty()) {
            if (type.equals(SyncType.Client)) {
                setPlayerYaw(clientYaw);
                setPlayerPitch(clientPitch);
            }
            return ;
        }

        if (type.equals(SyncType.Server)) {
            setPlayerYaw(realServerYaw);
            setPlayerPitch(realServerPitch);
            return ;
        }

        if (getCurrentRotation() != null && getCurrentRotation().getProgress() && type.equals(SyncType.Module)) {
            setPlayerYaw(expectedServerYaw);
            setPlayerPitch(expectedServerPitch);
        }
    }

    private void finishRotation(Rotation rotation) {
        rotation.setProgress(false);
        rotation.setSuccess(true);
        Runnable task = rotation.getTask();
        if (task != null) {
            task.run();
        }

        currentRotation = null;

        if (!rotation.isSnapbackRotation()) {
            snapback();
        }
    }

    public void rotate(Rotation rotation) {
        if (rotations.isEmpty() && currentRotation == null) {
            recordedYaw   = rotation.getYaw();
            recordedPitch = rotation.getPitch();
            if (rotation.isWithClient()) {
                clientRotate(rotation);
            } else {
                serverRotate(rotation);
            }
            return;
        }

        Rotation head = rotations.peek();
        int bestPrio = (head == null ? Integer.MIN_VALUE : head.getPriority());

        if (currentRotation != null) {
            bestPrio = Math.max(bestPrio, currentRotation.getPriority());
        }

        if (rotation.getPriority() > bestPrio) {
            if (currentRotation != null && !currentRotation.isSnapbackRotation()) {
                currentRotation.setProgress(false);
                rotations.add(currentRotation);
            }
            currentRotation = null;
            recordedYaw   = rotation.getYaw();
            recordedPitch = rotation.getPitch();
            if (rotation.isWithClient()) {
                clientRotate(rotation);
            } else {
                serverRotate(rotation);
            }
        } else {
            rotations.add(rotation);
        }
    }

    public void serverRotate(Rotation rotation) {
        if (mc.player == null) {
            return;
        }

        expectedServerYaw   = rotation.getYaw();
        expectedServerPitch = rotation.getPitch();

        Vergency.NETWORK.sendPackets(new PlayerMoveC2SPacket.LookAndOnGround(
                expectedServerYaw,
                expectedServerPitch,
                mc.player.isOnGround(),
                mc.player.horizontalCollision)
        );

        currentRotation = rotation;
    }

    public void clientRotate(Rotation rotation) {
        if (mc.player == null) {
            return ;
        }

        clientYaw = rotation.getYaw();
        clientPitch = rotation.getPitch();
        setPlayerYaw(clientYaw);
        setPlayerPitch(clientPitch);
    }

    public void snapback() {
        Rotation back = new Rotation(recordedYaw, recordedPitch, 0);
        back.setSpeed(0);
        back.setSnapback(true);
        rotate(back);
    }

    public void setPlayerYaw(double yaw) {
        setPlayerYaw((float) yaw);
    }

    public void setPlayerYaw(float yaw) {
        if (mc.player == null) {
            return ;
        }
        mc.player.setYaw(yaw);
    }

    public void setPlayerPitch(float pitch) {
        if (mc.player == null) {
            return ;
        }
        mc.player.setPitch(pitch);
    }

    public float getPlayerYaw() {
        if (mc.player == null) {
            return 0;
        }
        return mc.player.getYaw();
    }

    public float getPlayerPitch() {
        if (mc.player == null) {
            return 0;
        }
        return mc.player.getPitch();
    }

    public float normalize(float yaw) {
        yaw %= 360.0F;
        if (yaw >= 180.0F) {
            yaw -= 360.0F;
        }
        if (yaw < -180.0F) {
            yaw += 360.0F;
        }
        return yaw;
    }

    private Rotation calcBySpeed(Rotation rotation, double speed) {
        float srcYaw = rotation.isWithClient() ? getPlayerYaw() : realServerYaw;
        float srcPitch = rotation.isWithClient() ? getPlayerPitch() : realServerPitch;
        float dstYaw = rotation.getYaw();
        float dstPitch = rotation.getPitch();

        float deltaYaw = normalize(dstYaw - srcYaw);
        float deltaPitch = dstPitch - srcPitch;

        double maxTicks = AntiCheat.INSTANCE.rotateTicks.getValue();
        double speedFactor = (speed == 0 || speed == 1)
                ? Double.MAX_VALUE
                : 1.0 / ((1.0 - speed) * maxTicks);

        if (speedFactor == Double.MAX_VALUE) {
            rotation.setYaw(dstYaw);
            rotation.setPitch(dstPitch);
            return rotation;
        }

        double maxDelta = Math.max(Math.abs(deltaYaw), Math.abs(deltaPitch));
        double ratio = Math.min(1.0, speedFactor);

        rotation.setYaw(srcYaw + deltaYaw * (float) ratio);
        rotation.setPitch(srcPitch + deltaPitch * (float) ratio);
        return rotation;
    }

    public enum SyncType {
        Server, // sync to real server rotation (to fix the difference of real server rotation and real client rotation)
        Module, // sync to module excepted rotation
        Client // sync to recorded client rotation
    }
}
