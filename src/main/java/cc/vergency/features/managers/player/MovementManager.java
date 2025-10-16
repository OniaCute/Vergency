package cc.vergency.features.managers.player;

import cc.vergency.Vergency;
import cc.vergency.features.event.eventbus.EventHandler;
import cc.vergency.features.event.events.player.PlayerSyncEvent;
import cc.vergency.utils.interfaces.Wrapper;
import net.minecraft.util.math.Vec3d;

public class MovementManager implements Wrapper {
    public Vec3d currentPosition;
    public double speed, speedPerS;

    public MovementManager() {
        Vergency.EVENTBUS.subscribe(this);
    }

//    // Sync the position in tick
//    @EventHandler
//    public void onPlayerTick(PlayerTickEvent event) {
//        if (mc.player == null || mc.world == null) {
//            return ;
//        }
//        currentPosition = mc.player.getPos();
//    }

    @EventHandler
    public void onSyncMovement(PlayerSyncEvent event) {
        if (mc.player == null || mc.world == null) {
            return ;
        }

        currentPosition = new Vec3d(event.getX(), event.getY(), event.getZ());
    }
}
