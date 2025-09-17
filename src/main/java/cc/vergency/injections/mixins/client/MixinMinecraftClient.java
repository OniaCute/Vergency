package cc.vergency.injections.mixins.client;

import cc.vergency.Vergency;
import cc.vergency.features.event.events.game.TickEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.thread.ReentrantThreadExecutor;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.sql.Wrapper;

@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient extends ReentrantThreadExecutor<Runnable> implements Wrapper {
    public MixinMinecraftClient(String string) {
        super(string);
    }

    @Inject(at = @At("HEAD"), method = "tick()V")
    public void tickEvent$Hook(CallbackInfo info) {
        Vergency.EVENTBUS.post(new TickEvent());
    }
}
