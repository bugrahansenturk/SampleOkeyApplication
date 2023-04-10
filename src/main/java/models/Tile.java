package models;

import java.util.Comparator;

class Tile {
    private TileColor color;
    private int value;
    private int id;
    private boolean isOkey;

    public Tile(TileColor color, int value, int id) {
        this.color = color;
        this.value = value;
        this.id = id;
        this.isOkey = false;
    }

    // Getter and setter methods for Tile

    public TileColor getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }

    public int getId() {
        return id;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Tile)) {
            return false;
        }
        Tile other = (Tile) obj;
        return this.color == other.color &&
                this.value == other.value &&
                this.id == other.id;
    }
    // Other necessary methods for Tile

    public boolean isOkey() {
        return isOkey;
    }

    public void setOkey(boolean okey) {
        isOkey = okey;
    }

    public void setColor(TileColor color) {
        this.color = color;
    }
}