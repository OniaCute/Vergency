package cc.vergency.features.options.impl;

import cc.vergency.Vergency;
import cc.vergency.features.event.events.client.OptionUpdateEvent;
import cc.vergency.features.options.Option;

import java.util.ArrayList;
import java.util.function.Predicate;

public class EnumOption<E extends Enum<E>> extends Option<Enum<E>> {
    private final ArrayList<String> hideItems = new ArrayList<>();

    public EnumOption(String name, Enum<E> value) {
        super(name, value, v -> true);
    }

    public EnumOption(String name, Enum<E> value, Predicate<?> invisibility) {
        super(name, value, invisibility);
    }

    public EnumOption<E> addHideItem(String item) {
        hideItems.add(item);
        return this;
    }

    public boolean isHidden(String item) {
        return !hideItems.isEmpty() && hideItems.contains(item);
    }

    @Override
    public Enum<E> getValue() {
        return this.value;
    }

    @Override
    public void setValue(Enum<E> value) {
        Vergency.EVENTBUS.post(new OptionUpdateEvent(module, this));
        if (isHidden(value.name())) {
            return ;
        }
        this.value = value;
    }
}
