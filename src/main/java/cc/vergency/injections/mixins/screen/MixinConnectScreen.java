package cc.vergency.injections.mixins.screen;

import cc.vergency.Vergency;
import cc.vergency.features.event.events.game.ServerEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen;
import net.minecraft.client.network.CookieStorage;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ConnectScreen.class)
public class MixinConnectScreen {
    @Inject(method = "connect(Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/client/network/ServerAddress;Lnet/minecraft/client/network/ServerInfo;Lnet/minecraft/client/network/CookieStorage;)V", at = @At("HEAD"))
    private void serverEventConnect$Hook(MinecraftClient client, ServerAddress address, ServerInfo serverInfo, CookieStorage cookieStorage, CallbackInfo info) {
        Vergency.EVENTBUS.post(new ServerEvent.Connect(serverInfo, address));
    }

    @Inject(method = "connect(Lnet/minecraft/client/gui/screen/Screen;Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/client/network/ServerAddress;Lnet/minecraft/client/network/ServerInfo;ZLnet/minecraft/client/network/CookieStorage;)V", at = @At("HEAD"))
    private static void serverEventConnect$Hook(Screen screen, MinecraftClient client, ServerAddress address, ServerInfo info, boolean quickPlay, CookieStorage cookieStorage, CallbackInfo ci) {
        Vergency.EVENTBUS.post(new ServerEvent.Connect(info, address));
    }
}
