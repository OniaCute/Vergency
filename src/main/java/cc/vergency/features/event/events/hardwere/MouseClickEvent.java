package cc.vergency.features.event.events.hardwere;

import cc.vergency.features.enums.hardware.MouseButton;
import cc.vergency.features.event.Event;

public class MouseClickEvent extends Event {
    private final MouseButton button;
    private final boolean status;

    public MouseClickEvent(MouseButton button, boolean status) {
        this.button = button;
        this.status = status;
    }

    public MouseButton getButton() {
        return button;
    }

    public boolean getStatus() {
        return status;
    }
}
