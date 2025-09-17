package cc.vergency.injections.mixins.player;

import cc.vergency.Vergency;
import cc.vergency.features.event.events.render.Render2DEvent;
import cc.vergency.utils.interfaces.Wrapper;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class MixinInGameHud implements Wrapper {
    @Inject(method = "render", at = @At("HEAD"))
    private void render2DEvent$Hook(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        Vergency.EVENTBUS.post(new Render2DEvent(context, tickCounter.getTickDelta(true)));
    }
}
