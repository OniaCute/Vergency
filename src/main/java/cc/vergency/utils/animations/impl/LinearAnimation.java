package cc.vergency.utils.animations.impl;

import cc.vergency.utils.animations.Animation;
import cc.vergency.utils.animations.utils.ValuedAnimation;

public class LinearAnimation extends Animation {
    private ValuedAnimation animation;
    private double numA, numB;

    public LinearAnimation(double numA, double numB, ValuedAnimation animation) {
        super(animation.getDuration());
        this.numA = numA;
        this.numB = numB;
        this.animation = animation;
    }

    public double getProgress() {
        double progress = animation.getProgress() * (numB - numA) + numA;
        if (animation.getProgress() >= 0.999) {
            return numB;
        }
        return progress;
    }

    public LinearAnimation setAnimation(ValuedAnimation animation) {
        this.animation = animation;
        return this;
    }

    public void setNumA(double numA) {
        this.numA = numA;
    }

    public void setNumB(double numB) {
        this.numB = numB;
    }

    public ValuedAnimation getAnimation() {
        return animation;
    }

    public double getNumA() {
        return numA;
    }

    public double getNumB() {
        return numB;
    }

    @Override
    public void start() {
        setStartTime(System.currentTimeMillis());
        animation.start();
    }

    @Override
    public void stop() {
        setStartTime(System.currentTimeMillis() - getDuration() - 1);
        animation.stop();
    }
}
