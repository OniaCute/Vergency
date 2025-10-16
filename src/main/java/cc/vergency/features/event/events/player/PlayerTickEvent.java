package cc.vergency.features.event.events.player;

import cc.vergency.features.event.Event;

public class PlayerTickEvent extends Event {
    private int iterations;

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }
}
