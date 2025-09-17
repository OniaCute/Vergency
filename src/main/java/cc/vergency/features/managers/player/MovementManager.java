package cc.vergency.features.managers.player;

import cc.vergency.Vergency;

public class MovementManager {
    public double speed, speedPerS;

    public MovementManager() {
        Vergency.EVENTBUS.subscribe(this);
    }
}
