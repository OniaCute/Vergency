package cc.vergency.features.event.events.player;

import cc.vergency.features.event.Event;
import cc.vergency.features.event.events.game.PacketEvent;
import net.minecraft.network.packet.Packet;

public class AntiCheatModifiedEvent extends Event {
    public static class Rotate extends AntiCheatModifiedEvent {
        private final float yaw;
        private final float pitch;

        public Rotate(float yaw, float pitch) {
            this.yaw = yaw;
            this.pitch = pitch;
        }

        public float getYaw() {
            return yaw;
        }

        public float getPitch() {
            return pitch;
        }
    }

    public static class Position extends AntiCheatModifiedEvent {
        private final double x, y, z;

        public Position(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public double getY() {
            return y;
        }

        public double getX() {
            return x;
        }

        public double getZ() {
            return z;
        }
    }

    public static class Full extends AntiCheatModifiedEvent {
        private final double x, y, z;
        private final float yaw;
        private final float pitch;

        public Full(double x, double y, double z, float yaw, float pitch) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.yaw = yaw;
            this.pitch = pitch;
        }

        public double getY() {
            return y;
        }

        public double getX() {
            return x;
        }

        public double getZ() {
            return z;
        }

        public float getYaw() {
            return yaw;
        }

        public float getPitch() {
            return pitch;
        }
    }
}
