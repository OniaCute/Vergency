package cc.vergency.modules;

import cc.vergency.Vergency;
import cc.vergency.features.options.Option;
import cc.vergency.features.options.impl.*;
import cc.vergency.utils.interfaces.Wrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;

public abstract class Module implements Wrapper {
    private String name;
    private String displayName;
    private String description;
    private Category category;
    private BindOption bind;
    private DoubleOption priority;
    private BooleanOption draw;
    private double x;
    private double y;
    private double width;
    private double height;
    private boolean status; // default disabled
    private boolean alwaysEnable = false;
    private Predicate<?> invisibility;
    private ArrayList<Option<?>> options = new ArrayList<>();
    private HashMap<String, Option<?>> optionHashMap = new HashMap<>();
    public boolean isBlocked;
    public abstract String getDetails();

    public HashMap<String, Option<?>> getOptionHashMap() {
        return optionHashMap;
    }

    public ArrayList<Option<?>> getOptions() {
        return options;
    }

    public void setDraw(BooleanOption draw) {
        this.draw = draw;
    }

    public boolean shouldDraw() {
        return this.draw.getValue();
    }

    public static boolean isNull() {
        return mc.player == null || mc.world == null;
    }

    public void setAlwaysEnable(boolean alwaysEnable) {
        this.alwaysEnable = alwaysEnable;
        if (alwaysEnable) {
            this.bind.setValue(-1);
            this.bind.setInvisibility(v -> false);
            this.priority.setValue(0.00);
            this.priority.setInvisibility(v -> false);
            this.status = true;
        } else {
            this.bind.setInvisibility(v -> true);
            this.priority.setInvisibility(v -> true);
        }
    }

    public boolean isAlwaysEnable() {
        return alwaysEnable;
    }

    public Module(String name, Category category) {
        this.name = name;
        this.displayName = name;
        this.description = "No Desc.";
        this.category = category;

        this.bind = (BindOption) addOption(new BindOption("_BIND_", -1, BindOption.BindType.Click));
        this.priority = (DoubleOption) addOption(new DoubleOption("_PRIORITY_", -1, 10, 0).addSpecialValue(-1, "HIGHEST").addSpecialValue(10, "LOWEST").addSpecialValue(0, "DEFAULT"));
        this.draw = (BooleanOption) addOption(new BooleanOption("_DRAW_", true));

        this.invisibility = v -> true;
    }

    public Module(String name, Category category, int priority) {
        this.name = name;
        this.displayName = name;
        this.description = "No Desc.";
        this.category = category;

        this.bind = (BindOption) addOption(new BindOption("_BIND_", -1, BindOption.BindType.Click));
        this.priority = (DoubleOption) addOption(new DoubleOption("_PRIORITY_", -1, 10, priority).addSpecialValue(-1, "HIGHEST").addSpecialValue(10, "LOWEST").addSpecialValue(0, "DEFAULT"));
        this.draw = (BooleanOption) addOption(new BooleanOption("_DRAW_", true));

        this.invisibility = v -> true;
    }

    public void enable() {
        if (this.getStatus()) { // || SafeMode.INSTANCE == null
            return;
        }

//        if (isBlocked && SafeMode.INSTANCE.getStatus()) {
//            MessageManager.blockedMessage(this, SafeMode.INSTANCE);
//            return ;
//        }

//        NotifyManager.newNotification(this, Vergency.TEXT.get("Module.Special.Messages.Enable").replace("{module}", this.getDisplayName()));

        this.setStatus(true);
        this.onEnable();
    }

    public void disable() {
        if (!this.getStatus() || isAlwaysEnable()) {
            return;
        }

//        NotifyManager.newNotification(this, Vergency.TEXT.get("Module.Special.Messages.Disable").replace("{module}", this.getDisplayName()));

        this.setStatus(false);
        this.onDisable();
    }

    public void toggle() {
        if (this.getStatus()) {
            this.disable();
        } else {
            this.enable();
        }
    }

    public boolean block(Module module) {
        if (isAlwaysEnable() || module.getPriority().getValue() < this.getPriority().getValue()) {
            Vergency.CONSOLE.logWarn("Module \"" + module.getName() + "\" was failed to block module \"" + this.getName() + "\".");
            return false;
        }

//        MessageManager.blockedMessage(this, SafeMode.INSTANCE);
        isBlocked = true;

        this.setStatus(false);
        this.onBlock(module);
        return true;
    }

    public void unblock(boolean status) {
        if (isAlwaysEnable()) {
            return;
        }

//        MessageManager.unblockedMessage(this, SafeMode.INSTANCE);
        isBlocked = false;

        if (status) {
            this.enable();
        } else {
            this.disable();
        }
        this.onUnblock();
    }

    public <T> Option<T> addOption(Option<T> option) {
        options.add(option);
        optionHashMap.put(option.getName(), option);
        return option;
    }

    public void onEnable() {}
    public void onDisable() {}
    public void onBlock(Module module) {}
    public void onUnblock() {}

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public BindOption getBind() {
        return bind;
    }

    public DoubleOption getPriority() {
        return priority;
    }

    public boolean getStatus() {
        return status;
    }

    public Predicate<?> getInvisibility() {
        return invisibility;
    }

    public boolean isVisible() {
        if (invisibility != null) {
            return invisibility.test(null);
        }
        return true;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(DoubleOption priority) {
        this.priority = priority;
    }

    public void setBind(BindOption bind) {
        this.bind = bind;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setInvisibility(Predicate<?> invisibility) {
        this.invisibility = invisibility;
    }

    public static ArrayList<Category> getCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(Category.CLIENT);
        categories.add(Category.COMBAT);
        categories.add(Category.PLAYER);
        categories.add(Category.MOVEMENT);
        categories.add(Category.EXPLOIT);
        categories.add(Category.VISUAL);
        categories.add(Category.MISC);
        categories.add(Category.HUD);
        return categories;
    }

    public enum Category {
        CLIENT, COMBAT,PLAYER, MOVEMENT, EXPLOIT, VISUAL, MISC, HUD
    }
}
