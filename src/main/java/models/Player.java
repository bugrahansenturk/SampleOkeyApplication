package models;

import java.util.ArrayList;
import java.util.List;

class Player {
    private int id;
    private List<Tile> hand;
    private int points;

    public Player(int id) {
        this.id = id;
        this.hand = new ArrayList<>();
        this.points = 0;
    }

    public void addToHand(Tile tile) {
        hand.add(tile);
    }

    public List<Tile> getHand() {
        return hand;
    }

    // Other necessary methods for Player
}
