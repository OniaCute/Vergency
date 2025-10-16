package cc.vergency.features.value.player;

import cc.vergency.utils.others.FastTimerUtil;

public class Rotation implements Comparable<Rotation>{
    private boolean snapback;
    private boolean isSnapback;
    private boolean withClient;
    private boolean progress;
    private float yaw, pitch;
    private int priority;
    private double speed; // 0 -> 1
    private FastTimerUtil timer;
    private Runnable task;
    private boolean isSuccess = false;

    private static final float EPS = 0.001F;

    public Rotation(float yaw, float pitch, int priority, double speed) {
        this.snapback = false;
        this.withClient = false;
        this.progress = false;
        this.yaw = yaw;
        this.pitch = pitch;
        this.priority = priority;
        this.speed = speed;
        this.timer = new FastTimerUtil();
    }

    public Rotation(float yaw, float pitch, int priority, boolean snapback) {
        this.snapback = snapback;
        this.withClient = false;
        this.progress = false;
        this.yaw = yaw;
        this.pitch = pitch;
        this.priority = priority;
        this.speed = 1;
        this.timer = new FastTimerUtil();
    }

    public Rotation(float yaw, float pitch, int priority) {
        this.snapback = false;
        this.withClient = false;
        this.progress = false;
        this.yaw = yaw;
        this.pitch = pitch;
        this.priority = priority;
        this.speed = 1;
        this.timer = new FastTimerUtil();
    }

    public Rotation(float yaw, float pitch, boolean snapback) {
        this.snapback = snapback;
        this.withClient = false;
        this.progress = false;
        this.yaw = yaw;
        this.pitch = pitch;
        this.priority = 0;
        this.speed = 1;
        this.timer = new FastTimerUtil();
    }

    public Rotation(float yaw, float pitch) {
        this.snapback = false;
        this.withClient = false;
        this.progress = false;
        this.yaw = yaw;
        this.pitch = pitch;
        this.priority = 0;
        this.speed = 1;
        this.timer = new FastTimerUtil();
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

    public boolean getProgress() {
        return progress;
    }

    public void setProgress(boolean progress) {
        this.progress = progress;
    }

    public Rotation setTask(Runnable task) {
        this.task = task;
        return this;
    }

    public Runnable getTask() {
        return task;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public FastTimerUtil getTimer() {
        return timer;
    }

    public Rotation setSnapbackSign(boolean flag) {
        this.isSnapback = flag;
        return this;
    }

    public boolean isSnapbackRotation() {
        return this.isSnapback;
    }

    public boolean isNearlyThere(float srcYaw, float srcPitch) {
        float dy = Math.abs(normalize(yaw - srcYaw));
        float dp = Math.abs(pitch - srcPitch);
        return dy < 0.01F && dp < 0.01F;
    }

    private float normalize(float yaw) {
        yaw %= 360.0F;
        if (yaw >= 180.0F) {
            yaw -= 360.0F;
        }
        if (yaw < -180.0F) {
            yaw += 360.0F;
        }
        return yaw;
    }

    @Override
    public int compareTo(Rotation o) {
        return Integer.compare(o.priority, this.priority);
    }
}
