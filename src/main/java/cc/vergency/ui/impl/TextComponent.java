package cc.vergency.ui.impl;

import cc.vergency.Vergency;
import cc.vergency.features.enums.client.AnimationType;
import cc.vergency.features.enums.client.ComponentType;
import cc.vergency.features.enums.font.FontSize;
import cc.vergency.features.enums.hardware.MouseButton;
import cc.vergency.features.managers.ui.GuiManager;
import cc.vergency.ui.Component;
import cc.vergency.utils.animations.impl.ColorAnimation;
import cc.vergency.utils.animations.impl.LinearAnimation;
import cc.vergency.utils.animations.impl.Position2DAnimation;
import cc.vergency.utils.animations.utils.EasingAnimation;
import cc.vergency.utils.animations.utils.ValuedAnimation;
import cc.vergency.utils.others.Pair;
import cc.vergency.utils.render.TextUtil;

import java.awt.*;

/**
 * @author Voury_, OniaCute
 * @version vergency_1_0_ui_gird
 */

public class TextComponent extends Component {
    private double storedX;
    private double storedY;
    private String storedText;
    private double storedFontSize;
    private Color storedColor;
    private boolean storedShadow;

    private String text;
    private double fontSize;
    private Color color;
    private boolean shadow;

    // Animations (for transfer)
    private final Position2DAnimation positionAnimation;
    private final ColorAnimation colorAnimation;
    private final LinearAnimation scaleAnimation;

    public TextComponent(double x, double y, String text, Color color, double fontSize, boolean shadow, ValuedAnimation valuedAnimation) {
        super(x, y, ComponentType.Text);
        this.text = text;
        this.fontSize = fontSize;
        this.color = color;
        this.shadow = shadow;
        this.store();
        this.positionAnimation = new Position2DAnimation(
                new Pair<>(0.0, 0.0),
                new Pair<>(0.0, 0.0),
                valuedAnimation
        );
        this.colorAnimation = new ColorAnimation(
                this.color,
                this.color,
                valuedAnimation
        );
        this.scaleAnimation = new LinearAnimation(
                fontSize,
                fontSize,
                valuedAnimation
        );
    }

    public TextComponent(double x, double y, String text, Color color, double fontSize, boolean shadow) {
        this(x, y, text, color, fontSize, shadow, new EasingAnimation(150L).setEasingType(AnimationType.EasingInOut));
    }

    public TextComponent(double x, double y, String text, Color color, FontSize fontSize) {
        this(x, y, text, color, TextUtil.asFontSizeValue(fontSize));
    }

    public TextComponent(double x, double y, String text, Color color, double fontSize) {
        this(x, y, text, color, fontSize, false);
    }

    @Override
    public void drag(double deltaX, double deltaY, MouseButton button) {
        if (this.draggable && this.isDragging() && this.inRect(x, y, getWidth(), getHeight(), new Pair<>(GuiManager.mouseX, GuiManager.mouseY)) && button.equals(MouseButton.Left)) {
            this.x += deltaX;
            this.y += deltaY;
        }
    }

    @Override
    public void clicked(MouseButton button) {
        this.setDragging(this.draggable && this.inRect(x, y, getWidth(), getHeight(), new Pair<>(GuiManager.mouseX, GuiManager.mouseY)) && button.equals(MouseButton.Left));
    }

    @Override
    public void released(MouseButton button) {
        if (this.draggable && button.equals(MouseButton.Left)) {
            this.setDragging(false);
        }
    }

    @Override
    public void transfer(Component component) {
        if (component.getType().equals(ComponentType.Text) && (component instanceof TextComponent targetRect)) {
            positionAnimation.modify(new Pair<>(x, y), new Pair<>(targetRect.getX(), targetRect.getY()));
            colorAnimation.setColorA(this.getColor());
            colorAnimation.setColorB(targetRect.getColor());
            scaleAnimation.setNumA(getFontSize());
            scaleAnimation.setNumB(targetRect.getFontSize());
            positionAnimation.start();
            colorAnimation.start();
            scaleAnimation.start();
            this.shadow = targetRect.isShadow();
        } else {
            Vergency.CONSOLE.logTransferWarn(this, component);
        }
    }

    @Override
    public void store() {
        this.storedX = x;
        this.storedY = y;
        this.storedText = text;
        this.storedFontSize = fontSize;
        this.storedColor = color;
        this.storedShadow = shadow;
    }

    @Override
    public void restore() {
        this.transfer(new TextComponent(storedX, storedY, storedText, storedColor, storedFontSize, storedShadow));
    }

    @Override
    public void draw() {
        if (positionAnimation.isAlive()) {
            this.x = positionAnimation.getProgress().getA();
            this.y = positionAnimation.getProgress().getB();
        }
        if (colorAnimation.isAlive()) {
            this.color = colorAnimation.getProgress();
        }
        if (scaleAnimation.isAlive()) {
            this.fontSize = scaleAnimation.getProgress();
        }

        TextUtil.drawText(text, new Pair<>(x, y), color, fontSize, shadow);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getFontSize() {
        return fontSize;
    }

    public void setFontSize(FontSize fontSize) {
        this.fontSize = TextUtil.asFontSizeValue(fontSize);
    }

    public void setShadow(boolean shadow) {
        this.shadow = shadow;
    }

    public boolean isShadow() {
        return shadow;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getWidth() {
        return TextUtil.getWidth(text, fontSize);
    }

    public double getHeight() {
        return TextUtil.getHeight(fontSize);
    }
}
