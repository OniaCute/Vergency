package cc.vergency.features.event.events.render;

import cc.vergency.features.event.Event;
import net.minecraft.client.gui.DrawContext;

public class Render2DEvent extends Event {
    private final boolean contextIsNull; // when render event was post by skia renderer, in default, draw context is null.
    private final DrawContext context;
    private final float tickDelta;

    public Render2DEvent() {
        this.contextIsNull = true;
        this.context = null;
        this.tickDelta = -1;
    }

    public Render2DEvent(DrawContext context, float tickDelta) {
        this.contextIsNull = (context == null);
        this.context = context;
        this.tickDelta = tickDelta;
    }

    public float getTickDelta() {
        return tickDelta;
    }

    public DrawContext getContext() {
        return context;
    }

    public boolean isContextNull() {
        return contextIsNull;
    }
}
