package cc.vergency.utils.animations;

public abstract class Animation {
    protected Long startTime = -1L;
    protected Long duration;

    public Animation(Long duration) {
        this.duration = duration;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getStartTime() {
        return startTime;
    }

    public Long getDuration() {
        return duration;
    }

    public boolean isAlive() {
        if (startTime == -1L) { // has never been start
            return false;
        }
        return System.currentTimeMillis() < startTime + duration;
    }

    public abstract void start();

    public abstract void stop();
}
