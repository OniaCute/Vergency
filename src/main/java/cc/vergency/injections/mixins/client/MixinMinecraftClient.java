package cc.vergency.injections.mixins.client;

import cc.vergency.Vergency;
import cc.vergency.features.event.events.game.TickEvent;
import cc.vergency.utils.render.Render2DUtil;
import cc.vergency.utils.render.TextUtil;
import cc.vergency.utils.render.blur.KawaseBlur;
import cc.vergency.utils.render.skia.SkiaContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.util.Window;
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

    @Shadow
    @Final
    private Window window;

    @Inject(at = @At("HEAD"), method = "tick()V")
    public void tickEvent$Hook(CallbackInfo info) {
        Vergency.EVENTBUS.post(new TickEvent());
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    void postWindowInit(RunArgs args, CallbackInfo ci) {
        SkiaContext.createSurface(window.getWidth(), window.getHeight());
        TextUtil.LOADED = true;
    }

    @Inject(method = "onResolutionChanged", at = @At("TAIL"))
    public void onResolutionChanged(CallbackInfo info) {
        KawaseBlur.GUI_BLUR.resize();
        KawaseBlur.INGAME_BLUR.resize();
    }
}
