package cc.vergency.features.event.events.player;

import cc.vergency.features.event.Event;

public class PlayerSyncEvent extends Event {
    private float yaw;
    private float pitch;
    private double x, y, z;

    public PlayerSyncEvent(float yaw, float pitch, double x, double y, double z) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public PlayerSyncEvent(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.x = -1;
        this.y = -1;
        this.z = -1;
    }

    public PlayerSyncEvent() {
        this.yaw = -1;
        this.pitch = -1;
        this.x = -1;
        this.y = -1;
        this.z = -1;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}
