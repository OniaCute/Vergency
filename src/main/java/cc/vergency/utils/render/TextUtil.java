package cc.vergency.utils.render;

import cc.vergency.features.enums.font.FontSize;
import cc.vergency.features.enums.font.Fonts;
import cc.vergency.features.enums.others.Aligns;
import cc.vergency.modules.client.Client;
import cc.vergency.utils.others.Pair;
import cc.vergency.utils.render.font.FontRenderers;
import io.github.humbleui.types.Rect;

import java.awt.*;

public class TextUtil {
    public static boolean LOADED;

    public static float asFontSizeValue(FontSize size) {
        switch (size) {
            case LARGEST -> {
                return 16;
            }
            case LARGE -> {
                return 12;
            }
            case SMALLEST -> {
                return 6;
            }
            default -> {
                return 8;
            }
        }
    }

    public static io.github.humbleui.skija.Font getClientFont(float size) {
        if (Client.INSTANCE != null) {
            switch ((Fonts) Client.INSTANCE.font.getValue()) {
                case Sans -> {
                    return FontRenderers.getSans(size);
                }
                case Icon -> {
                    return FontRenderers.getIcon(size);
                }
                default -> {
                    return FontRenderers.getRhr(size);
                }
            }
        }
        return FontRenderers.getRhr(0);
    }

    public static double getHeight(FontSize size) {
        return getTextBounds("I", getClientFont(asFontSizeValue(size))).getHeight();
    }

    public static double getHeight(double size) {
        return getTextBounds("I", getClientFont((float) (size))).getHeight();
    }

    public static double getWidth(String text, FontSize size) {
        return getTextBounds(text, getClientFont(asFontSizeValue(size))).getWidth();
    }

    public static double getWidth(String text, double size) {
        return getTextBounds(text, getClientFont((float) (size))).getWidth();
    }

    public static double[] getSides(String text, FontSize size) {
        Rect fontRect = getTextBounds(text, getClientFont(asFontSizeValue(size)));
        return new double[] {fontRect.getLeft(), fontRect.getTop(), fontRect.getRight(), fontRect.getBottom()};
    }

    public static Rect getTextBounds(String text, io.github.humbleui.skija.Font font) {
        return font.measureText(text);
    }

    public static void drawText(String text, Pair<Double> pos, Color color, FontSize size, boolean shadow) {
        drawText(text, pos, color, asFontSizeValue(size), shadow);
    }

    public static void drawText(String text, Pair<Double> pos, Color color, double size, boolean shadow) {
        if (Client.INSTANCE == null || !LOADED) {
            return;
        }
        double x = pos.getA();
        double y = pos.getB();
        io.github.humbleui.skija.Font font = getClientFont((float) (size));
        Rect bounds = getTextBounds(text, font);
        double baselineOffset = -bounds.getTop();
        if (shadow) {
            float shadowOffset = 2f;
            Render2DUtil.getCanvas().drawString(text, (float) (x + shadowOffset), (float) (y + baselineOffset + shadowOffset), font, Render2DUtil.getPaint(new Color(7, 7, 7)));
        }
        Render2DUtil.getCanvas().drawString(text, (float) x, (float) (y + baselineOffset), font, Render2DUtil.getPaint(color));
    }

    public static void drawText(String text, Pair<Double> pos, Pair<Double> endPos, Color color, double size, boolean shadow, Aligns aligns) {
        drawText(text, Render2DUtil.getAlignPositionAsPair(pos.getA(), pos.getB(), endPos.getA(), endPos.getB(), getWidth(text, size), getHeight(size), aligns), color, size, shadow);
    }

    public static void drawText(String text, Pair<Double> pos, Pair<Double> endPos, Color color, FontSize size, boolean shadow, Aligns aligns) {
        drawText(text, pos, endPos, color, asFontSizeValue(size), shadow, aligns);
    }

    public static void drawText(String text, Pair<Double> pos, Pair<Double> endPos, Color color, FontSize size, Aligns aligns) {
        drawText(text, pos, endPos, color, size,  false, aligns);
    }
}
