package cc.vergency.features.options.impl;

import cc.vergency.Vergency;
import cc.vergency.features.event.events.client.OptionUpdateEvent;
import cc.vergency.features.options.Option;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.util.HashMap;
import java.util.function.Predicate;

public class DoubleOption extends Option<Double> {
    private double maxValue;
    private double minValue;
    private String unit = "";
    private HashMap<Integer, String> specialValueMap = new HashMap<>();

    public DoubleOption(String name, String description, double minValue, double maxValue, double value) {
        super(name, description, value, v -> true);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public DoubleOption(String name, String description, double minValue, double maxValue, double value, Predicate<?> invisibility) {
        super(name, description, value, invisibility);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public DoubleOption(String name, String description, double minValue, double maxValue) {
        super(name, description, 0.00, v -> true);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public DoubleOption(String name, double minValue, double maxValue, double value) {
        super(name, value, v -> true);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public DoubleOption(String name, double minValue, double maxValue, double value, Predicate<?> invisibility) {
        super(name, value, invisibility);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public DoubleOption(String name, double minValue, double maxValue) {
        super(name, 0.00, v -> true);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public DoubleOption(String name, Double defaultValue) {
        super(name, defaultValue, v -> true);
    }

    @Override
    public Double getValue() {
        return this.value;
    }

    public Double getValueSquared() {
        return getValue() * getValue();
    }

    @Override
    public void setValue(Double value) {
        if (value > maxValue) {
            this.value = maxValue;
        }
        else if (value < minValue) {
            this.value = minValue;
        } else {
            this.value = value;
        }
        Vergency.EVENTBUS.post(new OptionUpdateEvent(module, this));
    }

    public Option<Double> setMinValue(double minValue) {
        this.minValue = minValue;
        return this;
    }

    public Option<Double> setMaxValue(double maxValue) {
        this.maxValue = maxValue;
        return this;
    }

    public DoubleOption setUnit(String unit) {
        Vergency.EVENTBUS.post(new OptionUpdateEvent(module, this));
        this.unit = unit;
        return this;
    }

    public String getUnit() {
        return unit;
    }

    public double getMinValue() {
        return minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public String getValueAsText() {
        if (specialValueMap.get(this.getValue().intValue()) == null) {
            return String.valueOf(getValue()) + getUnit();
        }
        return specialValueMap.get(this.getValue().intValue());
    }

    public DoubleOption addSpecialValue(Integer number, String displayString) {
        specialValueMap.put(number, displayString);
        return this;
    }

    public JsonElement getJsonValue() {
        return new JsonPrimitive(getValue());
    }
}
