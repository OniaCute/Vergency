package cc.vergency.injections.mixins.hardware;

import cc.vergency.Vergency;
import cc.vergency.features.event.events.hardwere.MouseClickEvent;
import cc.vergency.utils.interfaces.Wrapper;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MixinMouseActive implements Wrapper {
    @Inject(method = "onMouseButton", at = @At("HEAD"))
    private void onMouseButton(long window, int button, int action, int mods, CallbackInfo ci) {
        Vergency.EVENTBUS.post(new MouseClickEvent(button, action == 1));
    }
}
