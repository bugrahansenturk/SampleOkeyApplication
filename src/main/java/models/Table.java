package models;

import java.util.*;
import java.util.stream.Collectors;

public class Table {
    private static final int MAX_PLAYER_COUNT = 4;
    private List<Player> players;
    private TileSet tileSet;

    public Table() {
        players = new ArrayList<>();
        for (int i = 0; i < MAX_PLAYER_COUNT; i++) {
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
