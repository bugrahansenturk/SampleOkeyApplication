package models;

import java.util.Comparator;

class Tile {
    private TileColor color;
    private int value;
    private int id;

    public Tile(TileColor color, int value, int id) {
        this.color = color;
        this.value = value;
        this.id = id;
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
    public void setId(int id) {
        this.id = id;
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

    // Define a custom Comparator that compares tiles based on their value.


}