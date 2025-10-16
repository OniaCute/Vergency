package cc.vergency.modules.client;

import cc.vergency.features.options.Option;
import cc.vergency.features.options.impl.BooleanOption;
import cc.vergency.features.options.impl.CategoryOptions;
import cc.vergency.features.options.impl.DoubleOption;
import cc.vergency.features.options.impl.EnumOption;
import cc.vergency.features.value.client.FoldStatus;
import cc.vergency.modules.Module;

/*
 * The module under the "Client" category will not be displayed in the normal module format,
 * but instead it will be shown in the "Settings" section in the form of "line option".
 */

public class AntiCheat extends Module {
    public static AntiCheat INSTANCE;

    public AntiCheat() {
        super("AntiCheat", Category.CLIENT);
        INSTANCE = this;
    }

    public Option<Boolean> cancelPackets = addOption(new BooleanOption("CancelPackets", true)); // cancel all task packets when player quit the world

    public Option<FoldStatus> rotationCategory = addOption(new CategoryOptions("RotateCategory"));
    public Option<Boolean> rotateCancelable = addOption(new BooleanOption("RotateCancelable", true, v -> !rotationCategory.getValue().isFold())); // If the rotation was modified by Anti Cheat, cancel the runnable action of rotation
    public Option<Enum<RotateTick>> rotateTickMode = addOption(new EnumOption<RotateTick>("rotateTick", RotateTick.Ticks, v -> !rotationCategory.getValue().isFold()));
    public Option<Double> rotateTicks = addOption(new DoubleOption("RotateTicks", 1, 80, 1, v -> !rotationCategory.getValue().isFold()).setUnit("ticks"));

    @Override
    public String getDetails() {
        return "";
    }

    public enum RotateTick {
        Ticks,
        Render
    }
}
