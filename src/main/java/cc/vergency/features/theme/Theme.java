package cc.vergency.features.theme;

import cc.vergency.utils.color.HexColor;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public abstract class Theme {
    private String name;
    private String displayName;
    private String description;
    private String author;

    private final HashMap<String, HexColor> colors = new HashMap<>();

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public HashMap<String, HexColor> getColors() {
        return colors;
    }

    public String getAuthor() {
        return author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public HexColor getColor(String color) {
        return colors.get(color);
    }
}
