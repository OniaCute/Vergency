package cc.vergency.features.options.impl;

import cc.vergency.Vergency;
import cc.vergency.features.event.events.client.OptionUpdateEvent;
import cc.vergency.features.options.Option;

import java.util.ArrayList;

public class ListOption extends Option<ArrayList<Option<?>>> {
    private final ArrayList<String> hideItems = new ArrayList<>();

    public ListOption(String name) {
        super(name, new ArrayList<>(), v -> true);
    }

    public ListOption(String name, Option<?> defaultFirstOption) {
        super(name, new ArrayList<>(), v -> true);
        addItem(defaultFirstOption);
    }

    public ListOption addHideItem(String item) {
        hideItems.add(item);
        return this;
    }

    public boolean isHidden(String item) {
        return !hideItems.isEmpty() && hideItems.contains(item);
    }

    @Override
    public ArrayList<Option<?>> getValue() {
        return this.value;
    }

    @Override
    public void setValue(ArrayList<Option<?>> value) {
        Vergency.EVENTBUS.post(new OptionUpdateEvent(module, this));
        this.value = value;
    }

    public boolean addItem(Option<?> option) {
        if (this.value.contains(option) || isHidden(option.getName())) {
            return false; // repeated option or hidden
        }
        this.value.add(option);
        Vergency.EVENTBUS.post(new OptionUpdateEvent(module, this));
        return true;
    }

    public boolean removeItem(Option<?> option) {
        if (!this.value.contains(option)) {
            return false; // unknown option item
        }
        this.value.remove(option);
        Vergency.EVENTBUS.post(new OptionUpdateEvent(module, this));
        return true;
    }
}
