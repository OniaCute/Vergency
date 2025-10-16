package cc.vergency.injections.mixins.network;

import cc.vergency.Vergency;
import cc.vergency.features.event.events.game.ServerEvent;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.DisconnectionInfo;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class MixinClientConnection {
    @Inject(method = "disconnect(Lnet/minecraft/text/Text;)V", at = @At("HEAD"))
    public void serverEventDisconnect$Hook(Text disconnectReason, CallbackInfo ci) {
        Vergency.EVENTBUS.post(new ServerEvent.Disconnect(disconnectReason.getString()));
    }

    @Inject(method = "disconnect(Lnet/minecraft/network/DisconnectionInfo;)V", at = @At("HEAD"))
    public void serverEventDisconnect$Hook(DisconnectionInfo disconnectionInfo, CallbackInfo ci) {
        Vergency.EVENTBUS.post(new ServerEvent.Disconnect(disconnectionInfo.reason().getString()));
    }
}
