package cc.vergency.injections.mixins.render;

import cc.vergency.Vergency;
import cc.vergency.features.event.events.render.Render2DEvent;
import cc.vergency.modules.client.Client;
import cc.vergency.utils.render.Render2DUtil;
import cc.vergency.utils.render.blur.KawaseBlur;
import cc.vergency.utils.render.skia.SkiaContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;render(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/render/RenderTickCounter;)V", shift = At.Shift.BEFORE))
    public void render2DEvent$Hook(RenderTickCounter tickCounter, boolean tick, CallbackInfo ci) {
        KawaseBlur.INGAME_BLUR.draw(Client.INSTANCE.blurIntensity.getValue().intValue());
        SkiaContext.draw((context) -> {
            Render2DUtil.save();
            Render2DUtil.scale((float) MinecraftClient.getInstance().getWindow().getScaleFactor());
            Vergency.EVENTBUS.post(new Render2DEvent());
            Render2DUtil.restore();
        });
    }
}
