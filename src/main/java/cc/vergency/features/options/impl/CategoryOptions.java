package cc.vergency.features.options.impl;

import cc.vergency.features.options.Option;
import cc.vergency.features.value.client.FoldStatus;

import java.util.function.Predicate;

public class CategoryOptions extends Option<FoldStatus> {
    public CategoryOptions(String name) {
        super(name, new FoldStatus(), v -> true);
    }

    public CategoryOptions(String name, Predicate<?> invisibility) {
        super(name, new FoldStatus(), invisibility);
    }

    @Override
    public FoldStatus getValue() {
        return this.value;
    }

    @Override
    public void setValue(FoldStatus value) {
        this.value = value;
    }

    public boolean isFold() {
        return getValue().isFold();
    }

    public void setFold(boolean fold) {
        getValue().setFold(fold);
    }
}
