package cc.vergency.features.managers.player;

import cc.vergency.Vergency;

public class InventoryManager {
    private int serverSlot = -1;
    private int clientSlot = -1;

    public InventoryManager() {
        Vergency.EVENTBUS.subscribe(this);
    }
}
