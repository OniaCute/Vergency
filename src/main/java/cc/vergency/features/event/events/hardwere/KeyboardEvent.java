package cc.vergency.features.event.events.hardwere;

import cc.vergency.features.event.Event;

public class KeyboardEvent extends Event {
    private final int key;
    private final boolean status;

    public KeyboardEvent(int key, boolean status) {
        this.key = key;
        this.status = status;
    }

    public int getKey() {
        return key;
    }

    public boolean getStatus() {
        return status;
    }
}
