package cc.vergency.injections.mixins.render;

import cc.vergency.Vergency;
import cc.vergency.features.event.events.render.Render2DEvent;
import cc.vergency.modules.client.Client;
import cc.vergency.ui.impl.RectComponent;
import cc.vergency.utils.interfaces.Wrapper;
import cc.vergency.utils.render.Render2DUtil;
import cc.vergency.utils.render.blur.KawaseBlur;
import cc.vergency.utils.render.skia.SkiaContext;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(InGameHud.class)
public class MixinInGameHud implements Wrapper {
    @Inject(method = "render", at = @At("HEAD"))
    private void onRender(DrawContext drawcontext, RenderTickCounter tickCounter, CallbackInfo ci) {
        KawaseBlur.INGAME_BLUR.draw(Client.INSTANCE.blurIntensity.getValue().intValue());
        SkiaContext.draw(context -> {
            Render2DUtil.save();
            Render2DUtil.scale((float) mc.getWindow().getScaleFactor());
            Vergency.EVENTBUS.post(new Render2DEvent(drawcontext, tickCounter.getTickDelta(true)));
            Render2DUtil.restore();
        });
    }
}
