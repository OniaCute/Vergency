package cc.vergency.features.options.impl;

import cc.vergency.Vergency;
import cc.vergency.features.event.events.client.OptionUpdateEvent;
import cc.vergency.features.options.Option;
import cc.vergency.modules.client.Client;
import cc.vergency.utils.color.ColorUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.awt.*;
import java.util.function.Predicate;

public class ColorOption extends Option<Color> {
    private boolean isRainbow;
    private long time = System.currentTimeMillis();

    public ColorOption(String name) {
        super(name, new Color(0), v -> true);
    }

    public ColorOption(String name, int defaultValue) {
        super(name, new Color(defaultValue), v -> true);
    }

    public ColorOption(String name, Color defaultValue) {
        super(name, defaultValue, v -> true);
    }

    public ColorOption(String name, Color defaultValue, Predicate<?> invisibility) {
        super(name, defaultValue, invisibility);
    }

    @Override
    public Color getValue() {
        if (isRainbow()) {
            float[] hsb = Color.RGBtoHSB(value.getRed(), value.getGreen(), value.getBlue(), null);
            final int minSpeed = 1;
            final int maxSpeed = 100;
            double rainbowSpeed = Client.INSTANCE != null ? Client.INSTANCE.rainbowSpeed.getValue() : 1;
            rainbowSpeed = 101 - rainbowSpeed;
            double mappedSpeed = minSpeed + (maxSpeed - minSpeed) * ((double) (rainbowSpeed - 1) / (maxSpeed - 1));
            double rainbowState = ((Client.INSTANCE != null && Client.INSTANCE.rainbowSync.getValue() ? System.currentTimeMillis() : System.currentTimeMillis() - time) / mappedSpeed) % 360;
            int rgb = Color.getHSBColor((float) (rainbowState / 360.0f), hsb[1], hsb[2]).getRGB();
            int red = (rgb >> 16) & 0xFF;
            int green = (rgb >> 8) & 0xFF;
            int blue = (rgb) & 0xFF;
            return new Color(red, green, blue, value.getAlpha());
        }
        return this.value;
    }

    @Override
    public void setValue(Color value) {
        this.value = value;
        Vergency.EVENTBUS.post(new OptionUpdateEvent(module, this));
    }

    public void setValue(int value) {
        this.value = new Color(value);
        Vergency.EVENTBUS.post(new OptionUpdateEvent(module, this));
    }

    public void setRainbow(boolean rainbow) {
        isRainbow = rainbow;
        Vergency.EVENTBUS.post(new OptionUpdateEvent(module, this));
    }

    public boolean isRainbow() {
        return isRainbow;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public float getGlRed() {
        return getValue().getRed() / 255f;
    }

    public float getGlBlue() {
        return getValue().getBlue() / 255f;
    }

    public float getGlGreen() {
        return getValue().getGreen() / 255f;
    }

    public float getGlAlpha() {
        return getValue().getAlpha() / 255f;
    }

    public JsonElement getJsonValue() {
        return new JsonPrimitive(ColorUtil.asRGBA(getValue().getRed(), getValue().getGreen(), getValue().getBlue(), getValue().getAlpha()));
    }
}
