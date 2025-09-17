package cc.vergency.features.event.events.player;

import cc.vergency.features.event.Event;
import net.minecraft.util.Hand;

public class HandChangeEvent extends Event {
    private final Hand hand;

    public HandChangeEvent(Hand hand) {
        this.hand = hand;
    }

    public Hand getHand() {
        return hand;
    }
}
