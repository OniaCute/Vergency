package cc.vergency.features.event.events.hardwere;

import cc.vergency.features.enums.hardware.MouseButton;
import cc.vergency.features.event.Event;

public class MouseClickEvent extends Event {
    private final MouseButton button;
    private final boolean status;

    public MouseClickEvent(int button, boolean status) {
        this.status = status;
        switch (button) {
            case 0 -> this.button = MouseButton.Left;
            case 1 -> this.button = MouseButton.Right;
            case 2 -> this.button = MouseButton.Middle;
            case 3 -> this.button = MouseButton.FlankBack;
            case 4 -> this.button = MouseButton.FlankFront;
            default ->  this.button = MouseButton.Invalid;
        }
    }

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
