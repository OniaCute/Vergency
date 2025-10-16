package cc.vergency.features.managers.features;

import cc.vergency.Vergency;
import cc.vergency.modules.Module;
import cc.vergency.modules.client.AntiCheat;
import cc.vergency.modules.client.ClickGui;
import cc.vergency.modules.client.Client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class ModuleManager {
    public static List<Module> modules = new ArrayList<Module>();
    public static HashMap<Module.Category, ArrayList<Module>> categoryModuleHashMap = new HashMap<>();

    public ModuleManager() {
        Vergency.EVENTBUS.subscribe(this);

        registerModule(new Client());
        registerModule(new ClickGui());
        registerModule(new AntiCheat());

        // sort
        Vergency.CONSOLE.logInfo("[UI] Sorting modules ...");
        modules.sort(Comparator
                .<Module>comparingInt(m -> m.isAlwaysEnable() ? 0 : 1)
                .thenComparing(Module::getDisplayName)
        );
        for (ArrayList<Module> categoryList : categoryModuleHashMap.values()) {
            categoryList.sort(Comparator
                    .<Module>comparingInt(m -> m.isAlwaysEnable() ? 0 : 1)
                    .thenComparing(Module::getDisplayName)
            );
        }
        Vergency.CONSOLE.logInfo("[UI] All modules are sorted!");

        // init UI
        Vergency.CONSOLE.logInfo("[UI] Init UI ...");
//        GuiManager.initClickGui();
        Vergency.CONSOLE.logInfo("[UI] UI was loaded!");
    }

    public void registerModule(Module module) {
        for (Module mod : modules) {
            if (mod.getName().equalsIgnoreCase(module.getName())) {
                Vergency.CONSOLE.logInfo("[MODULE] [WARN] Duplicate registration of module: " + module.getName() + " has been skipped!");
                return ;
            }
        }

        Vergency.CONSOLE.logInfo("[MODULE] Registering module \"" + module.getName() + "\" ...");
//        for (String s : module.getOptionHashMap().keySet()) {
//            settingOptionInfo(module, module.getOptionHashMap().get(s));
//        }
        modules.add(module);
//        categoryModuleHashMap.get(module.getCategory()).add(module);
        if (module.getCategory().equals(Module.Category.HUD)) {
//            HudManager.hudList.add(module);
        }
        module.onRegister();
        Vergency.CONSOLE.logInfo("[MODULE] Module \"" + module.getName() + "\" is loaded.");
    }
}
