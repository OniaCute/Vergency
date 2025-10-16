package cc.vergency.features.managers.features;

import cc.vergency.features.event.eventbus.EventHandler;
import cc.vergency.features.event.events.game.TickEvent;
import cc.vergency.injections.accessories.world.ClientWorldAccessor;
import cc.vergency.modules.client.AntiCheat;
import cc.vergency.utils.interfaces.INetworkHandler;
import cc.vergency.utils.interfaces.Wrapper;
import net.minecraft.client.network.PendingUpdateManager;
import net.minecraft.client.network.SequencedPacketCreator;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.network.packet.Packet;

import java.util.HashSet;
import java.util.PriorityQueue;

public class NetworkManager implements Wrapper {
    private final HashSet<Packet<?>> cachedPackets = new HashSet<>();
    private final PriorityQueue<Packet<?>> taskPackets = new PriorityQueue<>();
    private final PriorityQueue<Packet<?>> silentTaskPackets = new PriorityQueue<>();

    @EventHandler
    public void onTickEvent(TickEvent event) {
        if (mc.player == null || mc.world == null || mc.getNetworkHandler() == null) {
            if (AntiCheat.INSTANCE == null || AntiCheat.INSTANCE.cancelPackets.getValue()) {
                taskPackets.clear();
                silentTaskPackets.clear();
            }
            return ;
        }
        if (!taskPackets.isEmpty()) {
            Packet<?> packet = taskPackets.poll();
            mc.getNetworkHandler().sendPacket(packet);
            cachedPackets.add(packet);
        }
        if (!silentTaskPackets.isEmpty()) {
            Packet<?> packet = silentTaskPackets.poll();
            ((INetworkHandler) mc.getNetworkHandler()).sendSilentPacket(packet);
            cachedPackets.add(packet);
        }
    }

    public void sendPackets(Packet<?> packet) {
        this.sendPackets(packet, false);
    }

    public void sendPackets(Packet<?> packet, boolean force) {
        if ((mc.player == null || mc.world == null || mc.getNetworkHandler() == null) && !force) {
            return;
        }
        taskPackets.add(packet);
    }

    public void sendSilentPackets(Packet<?> packet) {
        this.sendSilentPackets(packet, false);
    }

    public void sendSilentPackets(Packet<?> packet, boolean force) {
        if ((mc.player == null || mc.world == null || mc.getNetworkHandler() == null) && !force) {
            return;
        }
        silentTaskPackets.add(packet);
    }

    public void sendSequencedPacket(SequencedPacketCreator p) {
        if (mc.world != null) {
            PendingUpdateManager updater = ((ClientWorldAccessor) mc.world).invokeGetPendingUpdateManager().incrementSequence();
            try {
                int sequence = updater.getSequence();
                Packet<ServerPlayPacketListener> packet = p.predict(sequence);
                this.sendPackets(packet);
            } catch (Throwable e) {
                e.printStackTrace();
                if (updater != null) {
                    try {
                        updater.close();
                    } catch (Throwable e1) {
                        e1.printStackTrace();
                        e.addSuppressed(e1);
                    }
                }
                throw e;
            }
            if (updater != null) {
                updater.close();
            }
        }
    }

    public boolean isCached(Packet<?> packet) {
        return cachedPackets.contains(packet);
    }

    public void sendMessage(String message) {
        if (mc.player == null || mc.world == null || mc.getNetworkHandler() == null) {
            return ;
        }
        mc.getNetworkHandler().sendChatMessage(message);
    }

    public void sendCommand(String command) {
        if (mc.player == null || mc.world == null || mc.getNetworkHandler() == null) {
            return ;
        }
        mc.getNetworkHandler().sendChatCommand("/" + command);
    }
}
