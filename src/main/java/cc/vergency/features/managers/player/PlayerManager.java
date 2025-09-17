package cc.vergency.features.managers.player;

import cc.vergency.Vergency;
import cc.vergency.features.event.eventbus.EventHandler;
import cc.vergency.features.event.events.game.TickEvent;
import cc.vergency.utils.interfaces.Wrapper;

public class PlayerManager implements Wrapper {
    public boolean isRealtime = false;
    public String playerName;
    public double health, maxHealth;
    public double armor;

    public PlayerManager() {
        Vergency.EVENTBUS.subscribe(this);
    }

    @EventHandler
    public void onTick(TickEvent event) {
        isRealtime = (mc.player != null && mc.world != null);
    }
}
