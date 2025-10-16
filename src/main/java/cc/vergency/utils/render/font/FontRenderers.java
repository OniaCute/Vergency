package cc.vergency.utils.render.font;

import io.github.humbleui.skija.Font;

public class FontRenderers {
	private static final String RHR = "rhr.ttf";
	private static final String SANS = "sans.ttf";
	private static final String ICON_FILL = "MaterialSymbolsRounded_Fill.ttf";
	private static final String ICON = "MaterialSymbolsRounded.ttf";

	public static void loadAll() {
		FontHelper.preloadFonts(RHR, SANS, ICON_FILL, ICON);
	}

	public static Font getRhr(float size) {
		return FontHelper.load(RHR, size);
	}

	public static Font getSans(float size) {
		return FontHelper.load(SANS, size);
	}

	public static Font getIconFill(float size) {
		return FontHelper.load(ICON_FILL, size);
	}

	public static Font getIcon(float size) {
		return FontHelper.load(ICON, size);
	}
}
