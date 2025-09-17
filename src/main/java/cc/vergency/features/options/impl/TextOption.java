package cc.vergency.features.options.impl;

import cc.vergency.Vergency;
import cc.vergency.features.event.events.client.OptionUpdateEvent;
import cc.vergency.features.options.Option;
import cc.vergency.utils.interfaces.Wrapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.util.function.Predicate;

public class TextOption extends Option<String> implements Wrapper {
    public int sizeLimit = -1; // default no limitation
    private boolean editable = true;
    public TextOption(String name, String defaultValue) {
        super(name, "", defaultValue, v -> true);
    }
    public TextOption(String name, String defaultValue, int sizeLimit) {
        super(name, "", defaultValue, v -> true);
        this.sizeLimit = sizeLimit;

    }
    public TextOption(String name, String defaultValue, Predicate<?> invisibility) {
        super(name, "", defaultValue, invisibility);
    }
    public TextOption(String name, String description, String defaultValue) {
        super(name, description, defaultValue, v -> true);
    }
    public TextOption(String name, String description, String defaultValue, Predicate<?> invisibility) {
        super(name, description, defaultValue, invisibility);
    }

    @Override
    public void setValue(String value) {
        this.value = value;
        Vergency.EVENTBUS.post(new OptionUpdateEvent(module, this));
    }

    @Override
    public String getValue() {
        String raw = this.getRawValue();
//        if (raw == null) {
//            return "";
//        }
//        if (Placeholder.INSTANCE == null || !Placeholder.INSTANCE.getStatus()) {
//            return raw.replaceAll("&", "§");
//        }
//        raw = raw
//                .replaceAll("\\{id}", Vergency.MOD_ID)
//                .replaceAll("\\{name}", Vergency.NAME)
//                .replaceAll("\\{full_name}", Vergency.NAME + " Client")
//                .replaceAll("\\{version}", Vergency.VERSION);
//        if (mc.player != null) {
//            raw = raw
//                    .replaceAll("\\{player}", mc.player.getName().getString())
//                    .replaceAll("\\{hp}", String.valueOf((int) mc.player.getHealth()))
//                    .replaceAll("\\{max_hp}", String.valueOf((int) mc.player.getMaxHealth()))
//                    .replaceAll("\\{armor}", String.valueOf((int) mc.player.getArmor()))
//                    .replaceAll("\\{speed}", String.format("%.2f", Vergency.INFO.getSpeedPerS()))
//                    .replaceAll("\\{speed_km}", String.format("%.2f", Vergency.INFO.getSpeed()))
//                    .replaceAll("\\{fps}", String.valueOf(Vergency.INFO.getCurrentFPS()))
//                    .replaceAll("\\{memory}", String.valueOf(Vergency.INFO.getSpentMemory()))
//                    .replaceAll("\\{memory_max}", String.valueOf(Vergency.INFO.getMaxMemory()))
//                    .replaceAll("\\{ping}", String.valueOf(Vergency.INFO.getPing()))
//                    .replaceAll("\\{combo}", String.valueOf(Vergency.INFO.getCombo()))
//                    .replaceAll("\\{x}", String.format("%.2f", mc.player.getPos().x))
//                    .replaceAll("\\{y}", String.format("%.2f", mc.player.getPos().y))
//                    .replaceAll("\\{z}", String.format("%.2f", mc.player.getPos().z))
//                    .replaceAll("\\{cps}", String.valueOf(Vergency.INFO.getLeftClicks()))
//                    .replaceAll("\\{right_cps}", String.valueOf(Vergency.INFO.getRightClicks()))
//                    .replaceAll("\\{gametime}", String.valueOf(Vergency.INFO.getGameTime()))
//                    .replaceAll("\\{gametime_formatted}", String.valueOf(Vergency.INFO.getGameTimeFormatted()))
//                    .replaceAll("\\{server}", String.valueOf(Vergency.SERVER.getChachServer()))
//                    .replaceAll("\\{tickshift_used}", String.valueOf(TickShift.INSTANCE != null ? TickShift.INSTANCE.getUsed() : "0%"))
//                    .replaceAll("\\{tickshift_saved}", String.valueOf(TickShift.INSTANCE != null ? TickShift.INSTANCE.getTicks() : "0"))
//                    .replaceAll("\\{tickshift_max}", String.valueOf(TickShift.INSTANCE != null ? TickShift.INSTANCE.maxTicks.getValue().intValue() : "0"))
//                    .replaceAll("\\{combat_distance}", String.format("%.2f", Vergency.INFO.getCombatDistance()));
//        }
//        if (mc.world != null) {
//            Dimensions dim = WorldUtil.getDimension();
//            String world;
//            if (Placeholder.INSTANCE != null) {
//                switch (dim) {
//                    case Overworld -> world = ((TextOption) Placeholder.INSTANCE.placeholder_world_overworld).getRawValue();
//                    case Nether -> world = ((TextOption) Placeholder.INSTANCE.placeholder_world_nether).getRawValue();
//                    case TheEnd -> world = ((TextOption) Placeholder.INSTANCE.placeholder_world_the_end).getRawValue();
//                    default -> world = dim.name();
//                }
//            } else {
//                world = dim.name();
//            }
//            raw = raw.replaceAll("\\{world}", world);
//        }
//        return raw.replaceAll("&", "§");
        return raw;
    }

    public String getRawValue() {
        return this.value;
    }

    public char getFirstChar() {
        return getValue().charAt(0);
    }

    public char getLastChar() {
        return getValue().charAt(getValue().length() - 1);
    }

    public TextOption setEditable(boolean editable) {
        this.editable = editable;
        return this;
    }

    public JsonElement getJsonValue() {
        return new JsonPrimitive(getRawValue());
    }

    public boolean isEditable() {
        return editable;
    }
}
