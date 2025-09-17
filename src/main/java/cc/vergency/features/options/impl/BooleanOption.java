package cc.vergency.features.options.impl;

import cc.vergency.Vergency;
import cc.vergency.features.event.events.client.OptionUpdateEvent;
import cc.vergency.features.options.Option;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.util.function.Predicate;

public class BooleanOption extends Option<Boolean> {
    public BooleanOption(String name) {
        super(name, false, v -> true);
    }

    public BooleanOption(String name, boolean defaultValue) {
        super(name, defaultValue, v -> true);
    }

    public BooleanOption(String name, boolean defaultValue, Predicate<?> invisibility) {
        super(name, defaultValue, invisibility);
    }

    @Override
    public void setValue(Boolean value) {
        this.value = value;
        Vergency.EVENTBUS.post(new OptionUpdateEvent(module, this));
    }

    @Override
    public Boolean getValue() {
        return this.value;
    }

    public JsonElement getJsonValue() {
        return new JsonPrimitive(getValue());
    }
}
