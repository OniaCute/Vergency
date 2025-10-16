package cc.vergency.injections.mixins.chat;

import cc.vergency.utils.interfaces.IChatHudLine;
import net.minecraft.client.gui.hud.ChatHudLine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ChatHudLine.Visible.class)
public class MixinChatHudLineVisible implements IChatHudLine {
    @Unique
    private int id = 0;
    @Override
    public int vergency$getId() {
        return id;
    }

    @Override
    public void vergency$setId(int id) {
        this.id = id;
    }
}
