package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class TileSet {

    private List<Tile> allTiles;
    private List<Tile> centerTiles;
    private Tile okeyTile;

    public TileSet() {
        allTiles = new ArrayList<>();
        centerTiles = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                allTiles.add(new Tile(TileColor.valueOf(i), j + 1, j + (i * 13)));
                allTiles.add(new Tile(TileColor.valueOf(i), j + 1, j + (i * 13)));
            }
        }
        allTiles.add(new Tile(TileColor.FAKE_OKEY, 0, 52));
        allTiles.add(new Tile(TileColor.FAKE_OKEY, 0, 52));
        centerTiles.addAll(allTiles);
    }

    public void shuffleTiles() {
        Collections.shuffle(centerTiles);
    }

    public void selectOkeyTile() {
        Tile indicatorTile;
        int indicatorIndex;
        do {
            indicatorIndex = new Random().nextInt(centerTiles.size());
            indicatorTile = centerTiles.get(indicatorIndex);
        } while (indicatorTile.getColor() == TileColor.FAKE_OKEY);

        int nextValue = (indicatorTile.getValue() % 13) + 1;
        for (Tile tile : allTiles) {
            if (tile.getColor() == indicatorTile.getColor() && tile.getValue() == nextValue) {
                okeyTile = tile;
                break;
            }
        }

        int okeyTileIndex = centerTiles.indexOf(this.okeyTile);
        if (okeyTileIndex != -1) {
            List<Tile> fakeOkeyTiles = new ArrayList<>(centerTiles.stream().filter(tile1 -> tile1.getColor() == TileColor.FAKE_OKEY).toList());
            for(Tile tile : fakeOkeyTiles){
                tile.setValue(okeyTile.getValue());
            }
            centerTiles.remove(okeyTileIndex);
        }

    }

    public void dealHands(List<Player> players) {
        shuffleTiles();

        // Choose the starting player randomly
        int startingPlayerIndex = new Random().nextInt(players.size());

        // Deal 15 tiles to the starting player, and 14 tiles to the other players
        for (int i = 0; i < players.size(); i++) {
            Player currentPlayer = players.get(i);
            int tilesToDeal = (i == startingPlayerIndex) ? 15 : 14;

            for (int j = 0; j < tilesToDeal; j++) {
                Tile tile = centerTiles.remove(0);
                currentPlayer.addToHand(tile);
            }
        }
    }

    public List<Tile> getAllTiles() {
        return allTiles;
    }

    public List<Tile> getCenterTiles() {
        return centerTiles;
    }

    public Tile getOkeyTile() {
        return okeyTile;
    }

    // Other necessary methods for TileSet
}