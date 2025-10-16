package cc.vergency.utils.animations.utils;

import cc.vergency.features.enums.client.AnimationType;
import cc.vergency.features.managers.client.NotificationManager;

public class EasingAnimation extends ValuedAnimation {
    private AnimationType easingType = AnimationType.EasingIn;

    public EasingAnimation(Long duration) {
        super(duration);
    }

    public EasingAnimation setEasingType(AnimationType type) {
        this.easingType = type;
        return this;
    }

    private double applyEasing(double t) {
        return switch (easingType) {
            case EasingIn -> t * t;
            case EasingOut -> 1 - Math.pow(1 - t, 2);
            case EasingInOut -> t < 0.5 ? 2 * t * t : 1 - Math.pow(-2 * t + 2, 2) / 2;
            default -> t;
        };
    }


    @Override
    public double getProgress() {
        if (getStartTime() == -1L) {
            return 0;
        }
        return applyEasing(Math.min(1.0, (System.currentTimeMillis() - getStartTime()) / (double) getDuration()));
    }
}
