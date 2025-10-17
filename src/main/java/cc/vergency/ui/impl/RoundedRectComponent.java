package cc.vergency.ui.impl;

import cc.vergency.Vergency;
import cc.vergency.features.enums.client.AnimationType;
import cc.vergency.features.enums.client.ComponentType;
import cc.vergency.features.enums.hardware.MouseButton;
import cc.vergency.features.managers.ui.GuiManager;
import cc.vergency.ui.Component;
import cc.vergency.utils.animations.impl.ColorAnimation;
import cc.vergency.utils.animations.impl.LinearAnimation;
import cc.vergency.utils.animations.impl.Position2DAnimation;
import cc.vergency.utils.animations.utils.EasingAnimation;
import cc.vergency.utils.animations.utils.ValuedAnimation;
import cc.vergency.utils.interfaces.Wrapper;
import cc.vergency.utils.others.Pair;
import cc.vergency.utils.render.Render2DUtil;

import java.awt.*;

/**
 * @author Voury_, OniaCute
 * @version vergency_1_0_ui_gird
 */

public class RoundedRectComponent extends Component implements Wrapper {
    private double storedX;
    private double storedY;
    private double storedWidth;
    private double storedHeight;
    private double storedRadius;
    private double  storedOutlineWidth;
    private Color storedOutlineColor;
    private Color storedFillColor;
    private boolean storedBlur;

    private double width;
    private double height;
    private double radius;
    private double  outlineWidth;
    private Color outlineColor;
    private Color fillColor;
    private boolean blur;

    // Animations (for transfer)
    private final Position2DAnimation positionAnimation;
    private final Position2DAnimation sizeAnimation;
    private final LinearAnimation radiusAnimation;
    private final LinearAnimation outlineAnimation;
    private final ColorAnimation fillColorAnimation;
    private final ColorAnimation outlineColorAnimation;

    public RoundedRectComponent(double x, double y, double width, double height, double radius, Color fillColor, double outlineWidth, Color outlineColor, boolean blur, ValuedAnimation valuedAnimation) {
        super(x, y, ComponentType.RoundedRect);
        this.width = width;
        this.height = height;
        this.radius = radius;
        this.fillColor = fillColor;
        this.outlineWidth = outlineWidth;
        this.outlineColor = outlineColor;
        this.blur = blur;
        this.store();
        this.positionAnimation = new Position2DAnimation(
                new Pair<>(0.0, 0.0),
                new Pair<>(0.0, 0.0),
                valuedAnimation
        );
        this.sizeAnimation = new Position2DAnimation(
                new Pair<>(0.0, 0.0),
                new Pair<>(0.0, 0.0),
                valuedAnimation
        );
        this.radiusAnimation = new LinearAnimation(
                this.radius,
                this.radius,
                valuedAnimation
        );
        this.outlineAnimation = new LinearAnimation(
                this.outlineWidth,
                this.outlineWidth,
                valuedAnimation
        );
        this.fillColorAnimation = new ColorAnimation(
                this.fillColor,
                this.fillColor,
                valuedAnimation
        );
        this.outlineColorAnimation = new ColorAnimation(
                this.outlineColor,
                this.outlineColor,
                valuedAnimation
        );
    }

    public RoundedRectComponent(double x, double y, double width, double height, double radius, Color fillColor, double outlineWidth, Color outlineColor, boolean blur) {
        this(x, y, width, height, radius, fillColor, outlineWidth, outlineColor, blur, new EasingAnimation(100L).setEasingType(AnimationType.EasingInOut));
    }

    public RoundedRectComponent(double x, double y, double width, double height, double radius, Color fillColor, double outlineWidth, Color outlineColor) {
        this(x, y, width, height, radius, fillColor, outlineWidth, outlineColor, false);
    }

    public RoundedRectComponent(double x, double y, double width, double height, double radius, Color fillColor) {
        this(x, y, width, height, radius, fillColor, 0, new Color(0, 0, 0, 0));
    }

    public RoundedRectComponent(double x, double y, double width, double height, double radius) {
        this(x, y, width, height, radius, new Color(0, 0, 0, 0));
    }


    @Override
    public void transfer(Component component) {
        if (component.getType().equals(ComponentType.RoundedRect) && (component instanceof RoundedRectComponent targetRect)) {
            positionAnimation.modify(new Pair<>(x, y), new Pair<>(targetRect.getX(), targetRect.getY()));
            sizeAnimation.modify(new Pair<>(width, height), new Pair<>(targetRect.getWidth(), targetRect.getHeight()));
            radiusAnimation.setNumA(this.getRadius());
            radiusAnimation.setNumB(targetRect.getRadius());
            outlineAnimation.setNumA(this.getOutlineWidth());
            outlineAnimation.setNumB(targetRect.getOutlineWidth());
            fillColorAnimation.setColorA(this.getFillColor());
            fillColorAnimation.setColorB(targetRect.getFillColor());
            outlineColorAnimation.setColorA(this.getOutlineColor());
            outlineColorAnimation.setColorB(targetRect.getOutlineColor());
            positionAnimation.start();
            sizeAnimation.start();
            radiusAnimation.start();
            outlineAnimation.start();
            fillColorAnimation.start();
            outlineColorAnimation.start();
            this.blur = targetRect.isBlur();
        } else {
            Vergency.CONSOLE.logTransferWarn(this, component);
        }
    }

    @Override
    public void store() {
        this.storedX = x;
        this.storedY = y;
        this.storedWidth = width;
        this.storedHeight = height;
        this.storedRadius = radius;
        this.storedOutlineWidth = outlineWidth;
        this.storedFillColor = fillColor;
        this.storedOutlineColor = outlineColor;
        this.storedBlur = blur;
    }

    @Override
    public void restore() {
        this.transfer(new RoundedRectComponent(storedX, storedY, storedWidth, storedHeight, storedRadius, storedFillColor, storedOutlineWidth, storedOutlineColor, storedBlur));
    }

    @Override
    public void drag(double deltaX, double deltaY, MouseButton button) {
        if (this.draggable && this.isDragging() && this.inRect(x, y, width, height, new Pair<>(GuiManager.mousePos.getA(), GuiManager.mousePos.getB())) && button.equals(MouseButton.Left)) {
            this.x += deltaX;
            this.y += deltaY;
        }
    }

    @Override
    public void clicked(MouseButton button) {
        this.setDragging(this.draggable && this.inRect(x, y, width, height, new Pair<>(GuiManager.mousePos.getA(), GuiManager.mousePos.getB())) && button.equals(MouseButton.Left));
    }

    @Override
    public void released(MouseButton button) {
        if (this.draggable && button.equals(MouseButton.Left)) {
            this.setDragging(false);
        }
    }

    @Override
    public void draw() {
        if (positionAnimation.isAlive()) {
            this.x = positionAnimation.getProgress().getA();
            this.y = positionAnimation.getProgress().getB();
        }
        if (sizeAnimation.isAlive()) {
            this.width = sizeAnimation.getProgress().getA();
            this.height = sizeAnimation.getProgress().getB();
        }
        if (radiusAnimation.isAlive()) {
            this.radius = radiusAnimation.getProgress();
        }
        if (outlineAnimation.isAlive()) {
            this.outlineWidth = outlineAnimation.getProgress();
        }
        if (fillColorAnimation.isAlive()) {
            this.fillColor = fillColorAnimation.getProgress();
        }
        if (outlineColorAnimation.isAlive()) {
            this.outlineColor = outlineColorAnimation.getProgress();
        }

        if (blur) {
            Render2DUtil.drawRoundedBlur(
                    x,
                    y,
                    width,
                    height,
                    radius
            );
        }
        Render2DUtil.drawRoundedRectWithOutline(
                x,
                y,
                width,
                height,
                radius,
                outlineWidth,
                fillColor,
                outlineColor
        );
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void setOutlineWidth(double outlineWidth) {
        this.outlineWidth = outlineWidth;
    }

    public double getOutlineWidth() {
        return outlineWidth;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public void setOutlineColor(Color outlineColor) {
        this.outlineColor = outlineColor;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public Color getOutlineColor() {
        return outlineColor;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public RoundedRectComponent setBlur(boolean blur) {
        this.blur = blur;
        return this;
    }

    public boolean isBlur() {
        return blur;
    }
}
