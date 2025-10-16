package cc.vergency.features.value.client;

public class FoldStatus {
    private boolean fold;

    public FoldStatus() {
        this.fold = true;
    }

    public void fold() {
        this.fold = true;
    }

    public void unfold() {
        this.fold = false;
    }

    public void setFold(boolean fold) {
        this.fold = fold;
    }

    public boolean isFold() {
        return fold;
    }
}
