package cc.vergency.utils.animations.impl;

import cc.vergency.utils.animations.Animation;
import cc.vergency.utils.animations.utils.ValuedAnimation;
import cc.vergency.utils.others.Pair;

// Such a way of being so lazy~
public class Position2DAnimation extends Animation {
    private LinearAnimation animA, animB;

    public Position2DAnimation(Pair<Double> positionA, Pair<Double> positionB, ValuedAnimation animation) {
        super(animation.getDuration());
        this.animA = new LinearAnimation(positionA.getA(), positionB.getA(), animation);
        this.animB = new LinearAnimation(positionA.getB(), positionB.getB(), animation);
    }

    public Pair<Double> getProgress() {
        return new Pair<>(animA.getProgress(), animB.getProgress());
    }

    public Position2DAnimation setAnimation(ValuedAnimation animation) {
        this.animA.setAnimation(animation);
        this.animB.setAnimation(animation);
        return this;
    }

    public void modify(Pair<Double> positionA, Pair<Double> positionB) {
        this.animA.setNumA(positionA.getA());
        this.animA.setNumB(positionB.getA());
        this.animB.setNumA(positionA.getB());
        this.animB.setNumB(positionB.getB());

        this.setStartTime(-1L);
        this.animA.setStartTime(-1L);
        this.animB.setStartTime(-1L);
    }

    @Override
    public void start() {
        setStartTime(System.currentTimeMillis());
        animA.start();
        animB.start();
    }

    @Override
    public void stop() {
        setStartTime(System.currentTimeMillis() - getDuration() - 1);
        animA.stop();
        animB.stop();
    }
}
