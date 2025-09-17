package cc.vergency.features.event.events.client;

import cc.vergency.features.event.Event;
import cc.vergency.features.options.Option;
import cc.vergency.modules.Module;

public class OptionUpdateEvent extends Event {
    private final Module module;
    private final String option;

    public OptionUpdateEvent(Module module, String option) {
        this.module = module;
        this.option = option;
    }

    public OptionUpdateEvent(Module module, Option<?> option) {
        this.module = module;
        this.option = option.getName();
    }

    public Module getModule() {
        return module;
    }

    public String getOption() {
        return option;
    }
}
