package cc.vergency.features.value.player;

import cc.vergency.utils.interfaces.Wrapper;

public class Rotation {
    private boolean snapback;
    private boolean withClient;
    private boolean inProgress;
    private float yaw, pitch;
    private int priority;
    private double speed; // 0 -> 1

    public Rotation(float yaw, float pitch, int priority, double speed) {
        this.snapback = false;
        this.withClient = false;
        this.inProgress = false;
        this.yaw = yaw;
        this.pitch = pitch;
        this.priority = priority;
        this.speed = speed;
    }

    public Rotation(float yaw, float pitch, int priority, boolean snapback) {
        this.snapback = snapback;
        this.withClient = false;
        this.inProgress = false;
        this.yaw = yaw;
        this.pitch = pitch;
        this.priority = priority;
        this.speed = 1;
    }

    public Rotation(float yaw, float pitch, int priority) {
        this.snapback = false;
        this.withClient = false;
        this.inProgress = false;
        this.yaw = yaw;
        this.pitch = pitch;
        this.priority = priority;
        this.speed = 1;
    }

    public Rotation(float yaw, float pitch, boolean snapback) {
        this.snapback = snapback;
        this.withClient = false;
        this.inProgress = false;
        this.yaw = yaw;
        this.pitch = pitch;
        this.priority = 0;
        this.speed = 1;
    }

    public Rotation(float yaw, float pitch) {
        this.snapback = false;
        this.withClient = false;
        this.inProgress = false;
        this.yaw = yaw;
        this.pitch = pitch;
        this.priority = 0;
        this.speed = 1;
    }

    public void setSnapback(boolean snapback) {
        this.snapback = snapback;
    }

    public boolean isSnapback() {
        return snapback;
    }

    public void setWithClient(boolean withClient) {
        this.withClient = withClient;
    }

    public boolean isWithClient() {
        return withClient;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getYaw() {
        return yaw;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    public boolean isInProgress() {
        return inProgress;
    }

    public void setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
    }
}
