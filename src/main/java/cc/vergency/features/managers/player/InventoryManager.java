package cc.vergency.features.managers.player;

import cc.vergency.Vergency;
import cc.vergency.features.event.eventbus.EventHandler;
import cc.vergency.features.event.events.game.PacketEvent;

public class InventoryManager {
    private int serverSlot = -1;
    private int clientSlot = -1;

    public InventoryManager() {
        Vergency.EVENTBUS.subscribe(this);
    }

    @EventHandler
    public void onReceivedPacket(PacketEvent.Receive event) {

    }
}
