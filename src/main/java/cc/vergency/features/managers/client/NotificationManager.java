package cc.vergency.features.managers.client;

import cc.vergency.modules.Module;
import cc.vergency.utils.interfaces.IChatHud;
import cc.vergency.utils.interfaces.Wrapper;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class NotificationManager implements Wrapper {
    public static final String SYNC = "§(";
    public static HashMap<OrderedText, StringVisitable> notifyMessages = new HashMap<>();

    public static void newMessage(Module module, String text, int id) {
        newMessage(module.getDisplayName(), text, id);
    }

    public static void newMessage(String source, String text, int id) {
        if (mc.player == null || mc.world == null) {
            return ;
        }
        ((IChatHud) mc.inGameHud.getChatHud()).vergency$add(Text.of(SYNC + "§r[" + source + "§r]§f " + text), id);
    }

    public static void newMessage(Module module, String text) {
        newMessage(module.getDisplayName(), text);
    }

    public static void newMessage(String source, String text) {
        if (mc.player == null || mc.world == null) {
            return ;
        }
        mc.inGameHud.getChatHud().addMessage(Text.of(SYNC + "§r[" + source + "§r] §r" + text));
    }
}
