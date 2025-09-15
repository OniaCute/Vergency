package cc.vergency.injections.mixins.client;

import net.minecraft.client.util.Icons;
import net.minecraft.client.util.Window;
import net.minecraft.resource.InputSupplier;
import net.minecraft.resource.ResourcePack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Mixin(Window.class)
public class MixinWindow {
//    @Redirect(method = "setIcon", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Icons;getIcons(Lnet/minecraft/resource/ResourcePack;)Ljava/util/List;"))
//    private List<InputSupplier<InputStream>> setupIcon(Icons instance, ResourcePack resourcePack) throws IOException {
//        final InputStream stream16 = MixinWindow.class.getResourceAsStream("/assets/vergency/icons/logo_16x16.png");
//        final InputStream stream32 = MixinWindow.class.getResourceAsStream("/assets/vergency/icons/logo_32x32.png");
//
//        if (stream16 == null || stream32 == null) {
//            return instance.getIcons(resourcePack);
//        }
//
//        return List.of(() -> stream16, () -> stream32);
//    }
    @Redirect(method = "setIcon", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Icons;getIcons(Lnet/minecraft/resource/ResourcePack;)Ljava/util/List;"))
    private List<InputSupplier<InputStream>> setupHighResIcon(Icons instance, ResourcePack resourcePack) throws IOException {
        // 256×256 窗口图标
        InputStream s256 = MixinWindow.class.getResourceAsStream("/assets/vergency/icons/logo_256x256.png");
        // 128×128 macOS dock
        InputStream s128 = MixinWindow.class.getResourceAsStream("/assets/vergency/icons/logo_128x128.png");
        // 64×64 备用
        InputStream s64  = MixinWindow.class.getResourceAsStream("/assets/vergency/icons/logo_64x64.png");

        if (s256 == null || s128 == null) {
            // 回退到原版
            return instance.getIcons(resourcePack);
        }

        // GLFW 喜欢从大到小排列
        return List.of(
                (InputSupplier<InputStream>) () -> s256,
                () -> s128,
                () -> s64
        );
    }
}
