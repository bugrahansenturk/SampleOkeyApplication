package models;

import java.util.*;
import java.util.stream.Collectors;

public class Table {
    private List<Player> players;
    private TileSet tileSet;

    public Table() {
        players = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            players.add(new Player(i));
        }
        tileSet = new TileSet();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public TileSet getTileSet() {
        return tileSet;
    }
}
