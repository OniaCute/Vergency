package cc.vergency.features.screens;

import cc.vergency.utils.interfaces.Wrapper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ClickGuiScreen extends Screen implements Wrapper {
    public ClickGuiScreen() {
        super(Text.of("VergencyClientClickGuiScreen"));
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}
