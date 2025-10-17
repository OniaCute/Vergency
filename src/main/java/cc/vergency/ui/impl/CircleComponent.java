package cc.vergency.ui.impl;

import cc.vergency.Vergency;
import cc.vergency.features.enums.client.ComponentType;
import cc.vergency.features.enums.hardware.MouseButton;
import cc.vergency.features.managers.ui.GuiManager;
import cc.vergency.ui.Component;
import cc.vergency.utils.animations.impl.ColorAnimation;
import cc.vergency.utils.animations.impl.LinearAnimation;
import cc.vergency.utils.animations.impl.Position2DAnimation;
import cc.vergency.utils.animations.utils.EasingAnimation;
import cc.vergency.utils.animations.utils.ValuedAnimation;
import cc.vergency.utils.others.Pair;
import cc.vergency.utils.render.Render2DUtil;

import java.awt.*;

/**
 * @author Voury_, OniaCute
 * @version vergency_1_0_ui_gird
 */

public class CircleComponent extends Component {
    private double storedX;
    private double storedY;
    private double storedRadius;
    private double storedInlineDistance;
    private double storedInlineWidth;
    private Color storedBaseColor;
    private Color storedInlineColor;
    private boolean storedBlur;

    private double radius;
    private double inlineDistance;
    private double inlineWidth;
    private Color baseColor;
    private Color inlineColor;
    private boolean blur;

    // Animations (for transfer)
    private final Position2DAnimation positionAnimation;
    private final LinearAnimation radiusAnimation;
    private final LinearAnimation inlineDistanceAnimation;
    private final LinearAnimation inlineWidthAnimation;
    private final ColorAnimation baseColorAnimation;
    private final ColorAnimation inlineColorAnimation;

    public CircleComponent(double x, double y, double radius, double inlineDistance, double inlineWidth, Color baseColor, Color inlineColor, boolean blur, ValuedAnimation valuedAnimation) {
        super(x, y, ComponentType.Circle);
        this.radius = radius;
        this.inlineDistance = inlineDistance;
        this.inlineWidth = inlineWidth;
        this.baseColor = baseColor;
        this.inlineColor = inlineColor;
        this.blur = blur;
        store();
        this.positionAnimation = new Position2DAnimation(
                new Pair<>(0.0, 0.0),
                new Pair<>(0.0, 0.0),
                valuedAnimation
        );
        this.radiusAnimation = new LinearAnimation(
                this.radius,
                this.radius,
                valuedAnimation
        );
        this.inlineDistanceAnimation = new LinearAnimation(
                this.inlineDistance,
                this.inlineDistance,
                valuedAnimation
        );
        this.inlineWidthAnimation = new LinearAnimation(
                this.inlineWidth,
                this.inlineWidth,
                valuedAnimation
        );
        this.baseColorAnimation = new ColorAnimation(
                this.baseColor,
                this.baseColor,
                valuedAnimation
        );
        this.inlineColorAnimation = new ColorAnimation(
                this.baseColor,
                this.baseColor,
                valuedAnimation
        );
    }

    public CircleComponent(double x, double y, double radius, double inlineDistance, double inlineWidth, Color baseColor, Color inlineColor, boolean blur) {
        this(x, y, radius, inlineDistance, inlineWidth, baseColor, inlineColor, blur, new EasingAnimation(100L));
    }

    public CircleComponent(double x, double y, double radius, double inlineDistance, double inlineWidth, Color baseColor, Color inlineColor) {
        this(x, y, radius, inlineDistance, inlineWidth, baseColor, inlineColor, false);
    }

    public CircleComponent(double x, double y, double radius, double inlineWidth, Color baseColor, Color inlineColor) {
        this(x, y, radius, 0, inlineWidth, baseColor, inlineColor);
    }

    public CircleComponent(double x, double y, double radius, Color baseColor) {
        this(x, y, radius, 0, baseColor, new Color(0, 0, 0, 0));
    }

    public CircleComponent(double x, double y, double radius, Color baseColor, boolean blur) {
        this(x, y, radius, 0, 0, baseColor, new Color(0, 0, 0, 0), blur);
    }

    public CircleComponent(double x, double y, double radius, Color baseColor, boolean blur, ValuedAnimation valuedAnimation) {
        this(x, y, radius, 0, 0, baseColor, new Color(0, 0, 0, 0), blur, valuedAnimation);
    }

    public CircleComponent(double x, double y, double radius, Color baseColor, ValuedAnimation valuedAnimation) {
        this(x, y, radius, 0, 0, baseColor, new Color(0, 0, 0, 0), false, valuedAnimation);
    }

    @Override
    public void drag(double deltaX, double deltaY, MouseButton button) {
        if (this.draggable && this.isDragging() && this.inCircle(x, y, radius, new Pair<>(GuiManager.mousePos.getA(), GuiManager.mousePos.getB())) && button.equals(MouseButton.Left)) {
            this.x += deltaX;
            this.y += deltaY;
        }
    }

    @Override
    public void clicked(MouseButton button) {
        this.setDragging(this.draggable && this.inCircle(x, y, radius, new Pair<>(GuiManager.mousePos.getA(), GuiManager.mousePos.getB())) && button.equals(MouseButton.Left));
    }

    @Override
    public void released(MouseButton button) {
        if (this.draggable && button.equals(MouseButton.Left)) {
            this.setDragging(false);
        }
    }

    @Override
    public void transfer(Component component) {
        if (component.getType().equals(ComponentType.Circle) && (component instanceof CircleComponent targetRect)) {
            positionAnimation.modify(new Pair<>(x, y), new Pair<>(targetRect.getX(), targetRect.getY()));
            radiusAnimation.setNumA(this.getRadius());
            radiusAnimation.setNumB(targetRect.getRadius());
            inlineDistanceAnimation.setNumB(targetRect.getInlineDistance());
            inlineDistanceAnimation.setNumB(targetRect.getInlineDistance());
            inlineWidthAnimation.setNumA(this.getInlineWidth());
            inlineWidthAnimation.setNumB(targetRect.getInlineWidth());
            baseColorAnimation.setColorA(this.getBaseColor());
            baseColorAnimation.setColorB(targetRect.getBaseColor());
            inlineColorAnimation.setColorA(this.getInlineColor());
            inlineColorAnimation.setColorB(targetRect.getInlineColor());

            positionAnimation.start();
            radiusAnimation.start();
            inlineDistanceAnimation.start();
            inlineWidthAnimation.start();
            baseColorAnimation.start();
            inlineColorAnimation.start();
            this.blur = targetRect.isBlur();
        } else {
            Vergency.CONSOLE.logTransferWarn(this, component);
        }
    }

    @Override
    public void store() {
        this.storedX = x;
        this.storedY = y;
        this.storedRadius = radius;
        this.storedInlineDistance = inlineDistance;
        this.storedInlineWidth = inlineWidth;
        this.storedBaseColor = baseColor;
        this.storedInlineColor = inlineColor;
        this.storedBlur = blur;
    }

    @Override
    public void restore() {
        transfer(new CircleComponent(storedX, storedY, storedRadius, storedInlineDistance, storedInlineWidth, storedBaseColor, storedInlineColor, storedBlur));
    }

    @Override
    public void draw() {
        if (positionAnimation.isAlive()) {
            this.x = positionAnimation.getProgress().getA();
            this.y = positionAnimation.getProgress().getB();
        }
        if (radiusAnimation.isAlive()) {
            this.radius = radiusAnimation.getProgress();
        }
        if (inlineDistanceAnimation.isAlive()) {
            this.inlineDistance = inlineDistanceAnimation.getProgress();
        }
        if (inlineWidthAnimation.isAlive()) {
            this.inlineWidth = inlineWidthAnimation.getProgress();
        }
        if (baseColorAnimation.isAlive()) {
            this.baseColor = baseColorAnimation.getProgress();
        }
        if (inlineColorAnimation.isAlive()) {
            this.inlineColor = inlineColorAnimation.getProgress();
        }

        if (blur) {
            Render2DUtil.drawCircleBlur(x, y, radius);
        }
        Render2DUtil.drawCircleWithInline(x, y, radius, inlineDistance, inlineWidth, baseColor, inlineColor);
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setBaseColor(Color baseColor) {
        this.baseColor = baseColor;
    }

    public CircleComponent setBlur(boolean blur) {
        this.blur = blur;
        return this;
    }

    public void setInlineColor(Color inlineColor) {
        this.inlineColor = inlineColor;
    }

    public void setInlineDistance(double inlineDistance) {
        this.inlineDistance = inlineDistance;
    }

    public void setInlineWidth(double inlineWidth) {
        this.inlineWidth = inlineWidth;
    }

    public double getRadius() {
        return radius;
    }

    public Color getBaseColor() {
        return baseColor;
    }

    public Color getInlineColor() {
        return inlineColor;
    }

    public boolean isBlur() {
        return blur;
    }

    public double getInlineWidth() {
        return inlineWidth;
    }

    public double getInlineDistance() {
        return inlineDistance;
    }
}
