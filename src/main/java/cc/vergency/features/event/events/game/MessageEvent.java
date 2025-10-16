package cc.vergency.features.event.events.game;

import cc.vergency.features.event.Event;

public class MessageEvent extends Event {
    private final String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    public static class Send extends MessageEvent {
        public Send(String message) {
            super(message);
        }
    }

    public static class Receive extends MessageEvent {
        public Receive(String message) {
            super(message);
        }
    }

    public String getMessage() {
        return message;
    }
}
