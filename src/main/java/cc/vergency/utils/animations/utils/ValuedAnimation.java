package cc.vergency.utils.animations.utils;

import cc.vergency.utils.animations.Animation;

public abstract class ValuedAnimation extends Animation {
    public ValuedAnimation(Long duration) {
        super(duration);
    }

    @Override
    public void start() {
        setStartTime(System.currentTimeMillis());
    }

    public void stop() {
        setStartTime(System.currentTimeMillis() - getDuration() - 1);
    }

    public abstract double getProgress();
}
