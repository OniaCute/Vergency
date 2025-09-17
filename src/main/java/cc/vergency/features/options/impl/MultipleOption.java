package cc.vergency.features.options.impl;

import cc.vergency.Vergency;
import cc.vergency.features.event.events.client.OptionUpdateEvent;
import cc.vergency.features.options.Option;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.function.Predicate;

public class MultipleOption<E extends Enum<E>> extends Option<EnumSet<E>> {
    public Class<E> enumClass;
    private final ArrayList<String> hideItems = new ArrayList<>();

    public  MultipleOption(String name, EnumSet<E> value) {
        super(name, value, v -> true);
        if (!value.isEmpty()) {
            E first = value.iterator().next();
            this.enumClass = (Class<E>) first.getDeclaringClass();
        }
    }

    public  MultipleOption(String name, EnumSet<E> value, Predicate<?> invisibility) {
        super(name, value, invisibility);
        if (!value.isEmpty()) {
            E first = value.iterator().next();
            this.enumClass = (Class<E>) first.getDeclaringClass();
        }
    }

    public MultipleOption<E> addHideItem(String item) {
        hideItems.add(item);
        return this;
    }

    public boolean isHidden(String item) {
        return !hideItems.isEmpty() && hideItems.contains(item);
    }

    @Override
    public EnumSet<E> getValue() {
        return this.value;
    }

    @Override
    public void setValue(EnumSet<E> value) {
        EnumSet<E> filtered = EnumSet.noneOf(enumClass);
        for (E e : value) {
            if (!isHidden(e.name())) {
                filtered.add(e);
            }
        }
        this.value = filtered;
        if (enumClass == null && !filtered.isEmpty()) {
            enumClass = (Class<E>) filtered.iterator().next().getDeclaringClass();
        }
        Vergency.EVENTBUS.post(new OptionUpdateEvent(module, this));
    }

    public Class<E> getEnumClass() {
        return enumClass;
    }
}
