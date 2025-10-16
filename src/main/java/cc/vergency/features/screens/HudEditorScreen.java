package cc.vergency.features.screens;

import cc.vergency.features.enums.hardware.MouseButton;
import cc.vergency.features.event.events.hardwere.MouseClickEvent;
import cc.vergency.features.managers.ui.GuiManager;
import cc.vergency.ui.Component;
import cc.vergency.utils.interfaces.Wrapper;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class HudEditorScreen extends Screen implements Wrapper {
    public HudEditorScreen() {
        super(Text.of("VergencyClientHudEditorScreen"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        GuiManager.dragging = false;
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        GuiManager.mouseX = mouseX;
        GuiManager.mouseY = mouseY;
        for (Component component : GuiManager.components) {
            component.drag(deltaX, deltaY, MouseButton.asMouseButton(button));
            GuiManager.dragging = true;
        }
        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        GuiManager.mouseX = mouseX;
        GuiManager.mouseY = mouseY;
        for (Component component : GuiManager.components) {
            component.clicked(MouseButton.asMouseButton(button));
        }
        return true;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        GuiManager.mouseX = mouseX;
        GuiManager.mouseY = mouseY;
        for (Component component : GuiManager.components) {
            component.released(MouseButton.asMouseButton(button));
        }
        return true;
    }
}
