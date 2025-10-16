package cc.vergency.ui;

import cc.vergency.features.enums.client.AnimationType;
import cc.vergency.features.enums.client.ComponentType;
import cc.vergency.features.enums.hardware.MouseButton;
import cc.vergency.features.enums.others.Aligns;
import cc.vergency.utils.others.Pair;
import cc.vergency.utils.render.Render2DUtil;

import java.util.ArrayList;
import java.util.Set;
import java.util.function.Predicate;

/**
 * @author Voury_, OniaCute
 * @version vergency_1_0_ui_gird
 */

public abstract class Component {
    protected String name;
    protected double x;
    protected double y;
    protected AnimationType animationType;
    protected double animationTime;
    protected boolean draggable = false;
    protected boolean dragging = false;
    protected ComponentType type = ComponentType.Unknown;
    protected Predicate<?> invisibility = v -> true;
    protected ArrayList<Component> subComponents = new ArrayList<>();
    protected Aligns align = Aligns.CENTER;

    public Component(double x, double y, ComponentType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public Component(double x, double y, ComponentType type, boolean draggable) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.draggable = draggable;
    }

    public Component(double x, double y, ComponentType type, AnimationType animationType, double animationTime) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.animationType = animationType;
        this.animationTime = animationTime;
    }

    public Component(double x, double y, ComponentType type, Predicate<?> invisibility) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.invisibility = invisibility;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(ComponentType type) {
        this.type = type;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setAnimationTime(double animationTime) {
        this.animationTime = animationTime;
    }

    public void setAnimationType(AnimationType animationType) {
        this.animationType = animationType;
    }

    public String getName() {
        return name;
    }

    public ComponentType getType() {
        return type;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public AnimationType getAnimationType() {
        return animationType;
    }

    public double getAnimationTime() {
        return animationTime;
    }

    public Component setDraggable(boolean draggable) {
        this.draggable = draggable;
        return this;
    }

    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    public boolean isDraggable() {
        return draggable;
    }

    public boolean isDragging() {
        return dragging;
    }

    public boolean isVisible() {
        if (invisibility != null) {
            return invisibility.test(null);
        }
        return true;
    }

    public void setInvisibility(boolean invisibility) {
        setInvisibility(v -> invisibility);
    }

    public void setInvisibility(Predicate<?> invisibility) {
        this.invisibility = invisibility;
    }

    public Component setAlign(Aligns align) {
        this.align = align;
        return this;
    }

    public Aligns getAlign() {
        return align;
    }

    public void getSubComponents(ArrayList<Component> subComponents) {
        this.subComponents = subComponents;
    }

    public void addSubComponents(Component component) {
        subComponents.add(component);
    }

    public void clearSubComponents() {
        subComponents.clear();
    }

    public void removeSubComponents(Component component) {
        subComponents.remove(component);
    }

    protected boolean inRect(double x, double y, double width, double height, Pair<Double> pos) {
        return pos.getA() >= x && pos.getB() >= y && pos.getA() <= x + width && pos.getB() <= y + height;
    }

    public abstract void drag(double deltaX, double deltaY, MouseButton button);

    public abstract void clicked(MouseButton button);

    public abstract void released(MouseButton button);

    public abstract void transfer(Component component);

    public abstract void store();

    public abstract void restore();

    public abstract void draw();
}
