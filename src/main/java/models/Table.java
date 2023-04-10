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

    public void determineBestHand() { // implement max-min
        for(Player player : this.players){
            System.out.println("For player: " + player.getId());
            for(int i = 0 ; i<3;i++){
                List<Tile> hand = new ArrayList<>(player.getHand());
                if (i == 1) {
                    List<List<Tile>> runs = getNumMovesLeftForRuns(hand);
                    removeSets(hand,runs);
                    List<List<Tile>> sets = getNumMovesLeftForSets(hand);
                    removeSets(hand,sets);
                    System.out.println("for the "+ i + "'th selection hand size is: " + hand.size());
                }
            }
        }
    }

    // 3-3-3, 4-4-4-4, 2-2-okey, 2-2-fakeOkey, 2-fakeOkey-fakeOkey
    public List<List<Tile>> getNumMovesLeftForSets(List<Tile> hand) {

        List<List<Tile>> sets = new ArrayList<>();
        List<Tile> irrelevants = new ArrayList<>();

        List<Tile> okeyTiles = new ArrayList<>(hand.stream().filter(Tile::isOkey).toList());
        for (int i = 1; i <= 13; i++) { // for every number that can be a set search for different colors.
            int number = i;
            List<Tile> setOfNumber = hand.stream().filter(tile -> tile.getValue() == number).toList();
            // below fragment is for remove same color and same number tiles.
            setOfNumber = setOfNumber.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Tile::getColor))), ArrayList::new));
            if (setOfNumber.size() > 2) {
                sets.add(setOfNumber);
            } else if (setOfNumber.size() == 2 && okeyTiles.size() > 0) {
                setOfNumber.add(okeyTiles.get(0));
                okeyTiles.remove(okeyTiles.get(0)); // only use each okeyTiles once.
                sets.add(setOfNumber);
            }
        }
        sets.add(irrelevants);
        //sets.forEach(x -> x.forEach(y-> System.out.print(y.getValue()+",")));
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
        }

        System.out.println("Runs:");
        runs.forEach(x -> x.forEach(y-> System.out.print(y.getValue()+",")));
        System.out.println("");
        return runs;
    }


    /*
    public List<List<Tile>> getNumMovesLeftForRuns(List<Tile> hand) {
        int minSize = 3;
        List<List<Tile>> runs = new ArrayList<>();

        Map<TileColor, List<Tile>> groupedTiles = hand.stream()
                .collect(Collectors.groupingBy(Tile::getColor));

        for (List<Tile> coloredTile : groupedTiles.values()) {
            coloredTile.sort(Comparator.comparing(Tile::getValue));
        }

        List<Tile> okeyTiles = new ArrayList<>(hand.stream().filter(Tile::isOkey).toList());

        for (List<Tile> coloredTile : groupedTiles.values()) {
            List<Tile> currentSublist = new ArrayList<>();
            for (int i = 0; i < coloredTile.size(); i++) {
                Tile currentTile = coloredTile.get(i);
                int currentValue = currentTile.getValue();
                boolean isFirstTile = currentSublist.isEmpty();
                boolean isConsecutiveValue = !isFirstTile && currentValue == currentSublist.get(currentSublist.size() - 1).getValue() + 1;
                boolean isWrapAroundValue = !isFirstTile && currentValue == 1 && currentSublist.get(currentSublist.size() - 1).getValue() == 13;

                if (isFirstTile || isConsecutiveValue || isWrapAroundValue) {
                    System.out.println("Added " + currentValue);
                    currentSublist.add(currentTile);
                } else {

                        if (currentSublist.size() >= minSize) {
                            runs.add(currentSublist);
                        }
                        currentSublist = new ArrayList<>();
                        currentSublist.add(currentTile);

                }
                if (currentSublist.size() >= minSize) {
                    runs.add(currentSublist);
                }
            }
        }

        System.out.println("Runs:");
        runs.forEach(x -> x.forEach(y -> System.out.print(y.getValue() + " ")));
        System.out.println("");
        return runs;
    }
*/
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

    public static void removeSets(List<Tile> mainList, List<List<Tile>> setsToRemove) {
        for (List<Tile> set : setsToRemove) {
            for (Tile tile : set) {
                mainList.remove(tile);
            }
        }
    }
}
