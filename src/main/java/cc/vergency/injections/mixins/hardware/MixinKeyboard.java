package cc.vergency.injections.mixins.hardware;

import cc.vergency.Vergency;
import cc.vergency.features.event.events.hardwere.KeyboardEvent;
import cc.vergency.utils.interfaces.Wrapper;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class MixinKeyboard implements Wrapper {
    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = "onKey", at = @At("HEAD"))
    private void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        Vergency.EVENTBUS.post(new KeyboardEvent(key, action == 1));
    }

//    @Inject(method = "onChar", at = @At("HEAD"))
//    private void onKey(long window, int codePoint, int modifiers, CallbackInfo ci) {
//        char[] chars = Character.toChars(codePoint);
//        for (char c : chars) {
//            Vergency.EVENTBUS.post(new KeyboardEvent(c, true));
//        }
//    }
}
