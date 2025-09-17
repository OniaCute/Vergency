package cc.vergency.modules.client;

import cc.vergency.modules.Module;

/*
 * The module under the "Client" category will not be displayed in the normal module format,
 * but instead it will be shown in the "Settings" section in the form of "line option".
 */

public class ClickGui extends Module {
    public static ClickGui INSTANCE;

    public ClickGui() {
        super("ClickGui", Category.CLIENT);
        INSTANCE = this;
    }

    @Override
    public String getDetails() {
        return "";
    }
}
