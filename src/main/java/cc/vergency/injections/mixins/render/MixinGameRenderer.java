package cc.vergency.injections.mixins.render;

import cc.vergency.Vergency;
import cc.vergency.features.event.events.render.Render2DEvent;
import cc.vergency.modules.client.Client;
import cc.vergency.utils.interfaces.Wrapper;
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
public class MixinGameRenderer implements Wrapper {

}
