package cc.vergency.features.enums.hardware;

public enum MouseButton {
    Invalid,
    Left, // 0
    Right, // 1
    Middle, // 2
    FlankBack, // 3
    FlankFront; // 4

    public static MouseButton asMouseButton(int num) {
        if (num < 0 || num >= values().length - 1) {
            return Invalid;
        }
        return values()[num + 1];
    }

    public int asInt() {
        if (this == Invalid) {
            return -1;
        }
        return this.ordinal() - 1;
    }
}
