package cc.vergency.features.managers.ui;

import cc.vergency.Vergency;
import cc.vergency.features.enums.font.FontSize;
import cc.vergency.features.event.eventbus.EventHandler;
import cc.vergency.features.event.events.hardwere.KeyboardEvent;
import cc.vergency.features.event.events.render.Render2DEvent;
import cc.vergency.features.managers.client.NotificationManager;
import cc.vergency.features.managers.player.ChatManager;
import cc.vergency.features.screens.HudEditorScreen;
import cc.vergency.ui.Component;
import cc.vergency.ui.impl.CircleComponent;
import cc.vergency.ui.impl.RectComponent;
import cc.vergency.ui.impl.RoundedRectComponent;
import cc.vergency.ui.impl.TextComponent;
import cc.vergency.utils.interfaces.Wrapper;
import cc.vergency.utils.others.Pair;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.ArrayList;

public class GuiManager implements Wrapper {
    public static ArrayList<Component> components = new ArrayList<>();
    public static Pair<Double> mousePos = new Pair<>(0.0, 0.0);
    public static Pair<Double> mouseDelta = new Pair<>(0.0, 0.0);
    public static boolean dragging = false;
    public static HudEditorScreen HudEditorScreen;

    public RoundedRectComponent rect = (RoundedRectComponent) (new RoundedRectComponent(5, 5, 50, 50, 2, new Color(255, 255, 255, 124), 1, new Color(255, 255, 255, 69)).setBlur(true).setDraggable(true));
    public RoundedRectComponent rect2 = new RoundedRectComponent(10, 40, 100, 100, 4, new Color(0, 0, 0, 113), 3, new Color(0, 0, 0, 50)).setBlur(true);
    public boolean flag, flag2, flag3;

    public TextComponent text = (TextComponent) (new TextComponent(5, 30, "Test Text", new Color(253, 253, 253), FontSize.LARGEST).setDraggable(true));
    public TextComponent text2 = new TextComponent(12, 33, "Test Text", new Color(20, 101, 255, 221), FontSize.MEDIUM);

    public CircleComponent circle = (CircleComponent) (new CircleComponent(5, 50, 5, new Color(0, 111, 255, 224)).setBlur(true).setDraggable(true));
    public CircleComponent circle2 = new CircleComponent(5, 55, 7, 2, 1, new Color(243, 243, 243, 100), new Color(255, 255, 255, 118));

    public GuiManager() {
        Vergency.EVENTBUS.subscribe(this);
        HudEditorScreen = new HudEditorScreen();
        components.add(rect);
        components.add(text);
        components.add(circle);
    }

    @EventHandler
    public void onDraw2D(Render2DEvent event) {
        NotificationManager.newMessage("UI", "Mouse Position, X: " + mousePos.getA() + " Y: " + mousePos.getB() + "  |  Delta X: " + mouseDelta.getA() + " Y: " + mouseDelta.getB(), -2);
        rect.draw();
        text.draw();
        circle.draw();
    }

    @EventHandler
    public void onKeyboardEvent(KeyboardEvent event) {
        if (event.getKey() == GLFW.GLFW_KEY_RIGHT_SHIFT && event.getStatus()) {
            NotificationManager.newMessage("UI", "Pressed Shift!", -3);
            mc.setScreen(HudEditorScreen);
        }
        if (event.getKey() == GLFW.GLFW_KEY_SPACE && event.getStatus()) {
            NotificationManager.newMessage("UI", "Pressed Space!", -3);
            if (!flag) {
                rect.store();
                rect.transfer(rect2);
            } else {
                rect.restore();
            }
            flag = !flag;

            if (!flag2) {
                text.store();
                text.transfer(text2);
            } else {
                text.restore();
            }
            flag2 = !flag2;

            if (!flag3) {
                circle.store();
                circle.transfer(circle2);
            } else {
                circle.restore();
            }
            flag3 = !flag3;
        }
    }
}
