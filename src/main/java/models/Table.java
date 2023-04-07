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

    public void startGame() {
        tileSet.selectOkeyTile();
        tileSet.dealHands(players);
        for(Player player : players ){
            List<Integer> liste = new ArrayList<>();
            for(Tile tile : player.getHand()){
                liste.add(tile.getValue());
            }
            Collections.sort(liste);

        }
    }

    public void determineBestHand() {
        // Implement a method to determine the best hand according to Okey rules.
        for(Player player : this.players){
            List<Tile> hand = player.getHand();
            getNumMovesLeftForSets(hand);
            getNumMovesLeftForRuns(hand);
        }
    }

    // 3-3-3, 4-4-4-4, 2-2-okey, 2-2-fakeOkey, 2-fakeOkey-fakeOkey
    public List<List<Tile>> getNumMovesLeftForSets(List<Tile> hand) {

        List<List<Tile>> sets = new ArrayList<>();
        List<Tile> irrelevants = new ArrayList<>();

        for (int i = 1; i <= 13; i++) { // for every number that can be a set search for different colors.
            int number = i;
            List<Tile> setOfNumber = hand.stream().filter(tile -> tile.getValue() == number).toList();
            // below fragment is for remove same color and same number tiles.
            setOfNumber = setOfNumber.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Tile::getColor))), ArrayList::new));
            if (setOfNumber.size() > 2) {
                sets.add(setOfNumber);
            }
        }
        sets.add(irrelevants);
        System.out.println("Sets:");
        sets.forEach(x -> x.forEach(y-> System.out.print(y.getValue()+",")));
        System.out.println("");
        return sets;
    }


    public List<List<Tile>> getNumMovesLeftForRuns(List<Tile> hand){ //1-2-3-4, 12-13-1
        int minSize = 3;
        List<List<Tile>> runs = new ArrayList<>();

        Map<TileColor, List<Tile>> groupedTiles = hand.stream()
                .collect(Collectors.groupingBy(Tile::getColor));

        for(List<Tile> coloredTile : groupedTiles.values()) {
            coloredTile.sort(Comparator.comparing(Tile::getValue));
        }

        for(List<Tile> coloredTile : groupedTiles.values()) {
            List<Tile> currentSublist = new ArrayList<>();
            for (int i = 0; i < coloredTile.size(); i++) {
                if (currentSublist.isEmpty() || coloredTile.get(i).getValue() == currentSublist.get(currentSublist.size() - 1).getValue() + 1 || (coloredTile.get(i).getValue() == 1 && currentSublist.get(currentSublist.size() - 1).getValue() == 13)) {
                    currentSublist.add(coloredTile.get(i));
                } else {
                    if (currentSublist.size() > minSize) {
                        runs.add(currentSublist);
                    }
                    currentSublist = new ArrayList<>();
                    currentSublist.add(coloredTile.get(i));
                }

                if (i == coloredTile.size() - 1 && currentSublist.size() >= minSize) {
                    runs.add(currentSublist);
                }
            }
            // think about fake & real okeys
        }
        System.out.println("Runs:");
        runs.forEach(x -> x.forEach(y-> System.out.print(y.getValue()+" ")));
        System.out.println("");
        return runs;
    }

    public int getNumMovesLeftForPairs(List<Tile> hand) {
        // Create a copy of the hand to avoid modifying the original list.
        List<Tile> tiles = new ArrayList<>(hand);

        Map<Integer, Integer> tileCounts = new HashMap<>();
        for (Tile tile : tiles) {
            tileCounts.put(tile.getId(), tileCounts.getOrDefault(tile.getId(), 0) + 1);
        }

        // Check each tile to see if it can form a pair.
        for (Tile tile : new HashSet<>(tiles)) {
            if (tileCounts.getOrDefault(tile.getId(), 0) >= 2) {
                tiles.removeIf(tileToRemove -> tileToRemove.getId() == tile.getId()); // removes all instances, not just the 1st one
                return getNumMovesLeftForPairs(tiles);
            }
        }

        boolean hasHandOkeyTile = hand.contains(tileSet.getOkeyTile());
        if(hasHandOkeyTile){
            return tiles.size() - 2;
        }
        return tiles.size();
    }


    Comparator<Tile> tileValueComparator = new Comparator<Tile>() {
        public int compare(Tile tile1, Tile tile2) {
            if (tile1.getValue() != tile2.getValue()) {
                return Integer.compare(tile1.getValue(), tile2.getValue());
            } else {
                return Integer.compare(tile1.getColor().ordinal(), tile2.getColor().ordinal());
            }
        }
    };

}
