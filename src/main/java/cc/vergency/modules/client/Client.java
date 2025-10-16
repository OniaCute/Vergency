package cc.vergency.modules.client;

import cc.vergency.features.enums.font.Fonts;
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

public class Client extends Module {
    public static Client INSTANCE;

    public Client() {
        super("Client", Category.CLIENT);
        INSTANCE = this;
    }

    public Option<Enum<Fonts>> font = addOption(new EnumOption<Fonts>("Font", Fonts.Sans));

    public Option<FoldStatus> categoryColor = addOption(new CategoryOptions("CategoryColor"));
    public Option<Boolean> rainbowSync = addOption(new BooleanOption("RainbowSync", true, v -> !categoryColor.getValue().isFold()));
    public Option<Double> rainbowSpeed = addOption(new DoubleOption("RainbowSpeed", 1, 100, 40, v -> !categoryColor.getValue().isFold()).setUnit("%"));

    public Option<FoldStatus> categoryBlur = addOption(new CategoryOptions("CategoryBlur"));
    public Option<Boolean> blurEffect = addOption(new BooleanOption("BlurEffect", true, v -> !categoryBlur.getValue().isFold()));
    public Option<Double> blurIntensity = addOption(new DoubleOption("BlurIntensity", 1.11, 19.9, 5, v -> blurEffect.getValue() && !categoryBlur.getValue().isFold()));

    @Override
    public String getDetails() {
        return "";
    }
}
