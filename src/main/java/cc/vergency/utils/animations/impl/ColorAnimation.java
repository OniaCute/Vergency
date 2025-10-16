package cc.vergency.utils.animations.impl;

import cc.vergency.utils.animations.Animation;
import cc.vergency.utils.animations.utils.ValuedAnimation;

import java.awt.*;

public class ColorAnimation extends Animation {
    private ValuedAnimation animation;
    private Color colorA;
    private Color colorB;

    public ColorAnimation(Color colorA, Color colorB, ValuedAnimation animation) {
        super(animation.getDuration());
        this.colorA = colorA;
        this.colorB = colorB;
        this.animation = animation;
    }

    public Color getProgress() {
        double t = animation.getProgress();
        int r = (int) Math.round(colorA.getRed()   + t * (colorB.getRed()   - colorA.getRed()));
        int g = (int) Math.round(colorA.getGreen() + t * (colorB.getGreen() - colorA.getGreen()));
        int b = (int) Math.round(colorA.getBlue()  + t * (colorB.getBlue()  - colorA.getBlue()));
        int a = (int) Math.round(colorA.getAlpha() + t * (colorB.getAlpha() - colorA.getAlpha()));
        return new Color(r, g, b, a);
    }

    public void setAnimation(ValuedAnimation animation) {
        this.animation = animation;
    }

    public void setColorA(Color colorA) {
        this.colorA = colorA;
    }

    public void setColorB(Color colorB) {
        this.colorB = colorB;
    }

    public ValuedAnimation getAnimation() {
        return animation;
    }

    public Color getColorA() {
        return colorA;
    }

    public Color getColorB() {
        return colorB;
    }

    @Override
    public void start() {
        setStartTime(System.currentTimeMillis());
    }

    @Override
    public void stop() {
        setStartTime(System.currentTimeMillis() - getDuration() - 1);
    }
}
