package cc.vergency;

import cc.vergency.features.event.eventbus.EventBus;
import cc.vergency.utils.interfaces.Wrapper;
import cc.vergency.utils.others.Console;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.ModMetadata;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;

public class Vergency implements ModInitializer, Wrapper {

    // Information & Mod status
    public static final ModMetadata MOD_INFO;
    public static final String MOD_ID = "vergency";
    public static final String NAME = "Vergency";
    public static final String VERSION = "1.0.0";
    public static final String CONFIG_TEMPLATE_VERSION = "vergency_1_0_vcg_json";
    public static final String UI_STYLE_VERSION = "vergency_1_0_ui_gird";
    public static final ArrayList<String> AUTHORS = new ArrayList<String>();
    public static String PREFIX = "$";
    public static boolean LOADED = false;
    public static boolean OUT_OF_DATE = false;
    public static long LOAD_TIME;

    // Manager & Preload
    public static Console CONSOLE;
    public static EventBus EVENTBUS;

    // Mod Info Load
    static {
        MOD_INFO = FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow().getMetadata();
    }

    @Override
    public void onInitialize() {
        CONSOLE = new Console();
        CONSOLE.logInfo("Vergency Client | Preloading ...", true);
        CONSOLE.logInfo("VERSION: " + VERSION, true);
        CONSOLE.logInfo("Authors: Voury, Onia", true);

        if (!MOD_INFO.getId().equals(MOD_ID)) {
            CONSOLE.logWarn("Fabric mod value check failed!");
            CONSOLE.logWarn("Vergency Client was exited!");
            CONSOLE.logWarn("This version may have been acquired from informal sources or created without permission!");
            mc.stop();
        } else {
            load();
        }
    }

    public static void load() {
        // Pre load
        CONSOLE.logInfo("Event Bus is loading...");
        EVENTBUS = new EventBus();
        CONSOLE.logInfo("Event Bus is loaded.");
        CONSOLE.logInfo("Registering Event bus ...");
        EVENTBUS.registerLambdaFactory("cc.vergency", (lookupInMethod, klass) -> (MethodHandles.Lookup) lookupInMethod.invoke(null, klass, MethodHandles.lookup()));
        CONSOLE.logInfo("Lambda was loaded.");
        CONSOLE.logInfo("Vergency Client was preloaded.");
        // Information define & Intellectual property declaration
        AUTHORS.add("Voury");
        AUTHORS.add("Onia");
        // Real load
        LOAD_TIME = System.currentTimeMillis();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (LOADED) {
                save();
            }
        }));

        OUT_OF_DATE = needUpdate();

        CONSOLE.logInfo("Vergency Client Loaded!");
        CONSOLE.logInfo("Vergency Loaded In " + (System.currentTimeMillis() - LOAD_TIME) + " ms.");

        LOADED = true;
    }

    public static boolean needUpdate() {
        return false;
    }

    public static void unload() {
        CONSOLE.logInfo("Vergency Client is unloading ...");
        EVENTBUS.listenerMap.clear();
        save();
        CONSOLE.logInfo("Vergency Client is was unloaded");
    }

    public static void save() {
        CONSOLE.logInfo("Vergency Client is shutting down ...");
//        EVENTS.onShutDown();
        CONSOLE.logInfo("Vergency Client is saving ...");
//        CONFIG.save(CONFIG.getCurrentConfig());
        CONSOLE.logInfo("Vergency Client was saved");
    }
}
