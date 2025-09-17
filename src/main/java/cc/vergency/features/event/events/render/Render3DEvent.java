package cc.vergency.features.event.events.render;

import cc.vergency.features.event.Event;
import net.minecraft.client.util.math.MatrixStack;

public class Render3DEvent extends Event {
    private final MatrixStack matrixStack;
    private final float tickDelta;

    public Render3DEvent(MatrixStack matrixStack, float tickDelta) {
        this.matrixStack = matrixStack;
        this.tickDelta = tickDelta;
    }

    public float getTickDelta() {
        return tickDelta;
    }

    public MatrixStack getMatrixStack() {
        return matrixStack;
    }
}
