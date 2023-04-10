package models;

import java.util.HashMap;
import java.util.Map;

public enum TileColor {
    YELLOW(0), BLUE(1), BLACK(2), RED(3), FAKE_OKEY(4);

    private int value;
    TileColor(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }

    private static final Map<Integer, TileColor> colorMap = new HashMap<>();

    static {
        for (TileColor color : TileColor.values()) {
            colorMap.put(color.getValue(), color);
        }
    }

    public static TileColor valueOf(int value) {
        return colorMap.get(value);
    }
}