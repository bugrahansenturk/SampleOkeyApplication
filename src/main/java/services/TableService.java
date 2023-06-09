package services;

import models.*;

import java.util.*;
import java.util.stream.Collectors;

public class TableService {

    TileSetService tileSetService = new TileSetService();
    public Table startGame() {
        Table table = new Table();
        tileSetService.initTileSet(table);
        return table;
    }
    public void determineBestHand(Table table) {
        int bestScore = Integer.MAX_VALUE;
        Player bestPlayer = null;

        for(Player player : table.getPlayers()){
            List<Tile> hand = new ArrayList<>(player.getHand());
            int pairsScore = getNumMovesLeftForPairs(hand, table);

            hand = new ArrayList<>(player.getHand()); // reset the hand to calculate other cases.
            List<List<Tile>> runs = getNumMovesLeftForRuns(hand);
            removeSets(hand,runs);
            List<List<Tile>> sets = getNumMovesLeftForSets(hand);
            removeSets(hand,sets);
            int runsThenSetsScore = hand.size();

            hand = new ArrayList<>(player.getHand()); // reset the hand to calculate other cases.
            List<List<Tile>> sets1 = getNumMovesLeftForSets(hand);
            removeSets(hand,sets1);
            List<List<Tile>> runs1 = getNumMovesLeftForRuns(hand);
            removeSets(hand,runs1);
            int setsThenRunsScore = hand.size();

            int minScoreForNotPairs = Math.min(runsThenSetsScore,setsThenRunsScore);
            int minScore = Math.min(pairsScore,minScoreForNotPairs);

            System.out.printf("Player %d scores: %d for pairs, %d for non-pairs %n", player.getId(), pairsScore, minScoreForNotPairs);
            if (minScore < bestScore) {
                bestScore = minScore;
                bestPlayer = player;
            }
        }
        System.out.printf("Best hand belongs to Player %d%n", bestPlayer.getId());
    }
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

        /**
        System.out.println("Sets:");
        for(List<Tile> set : sets){
            for(Tile tile : set){
                System.out.print(tile.getValue());
                if(tile.isOkey()){
                    System.out.print("(O)");
                }
                System.out.print(" ");
            }
            System.out.print(",");
        }
        System.out.println("");
        */

        return sets;
    }
    public List<List<Tile>> getNumMovesLeftForRuns(List<Tile> hand) { //1-2-3-4, 12-13-1
        int minSize = 3;
        List<List<Tile>> runs = new ArrayList<>();

        Map<TileColor, List<Tile>> groupedTiles = hand.stream()
                .filter(tile -> !tile.isOkey())
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
                boolean isPreviousTileOkey = !isFirstTile && currentSublist.get(currentSublist.size() - 1).isOkey();
                boolean isTwoBeforeTileValuePlusTwo = currentSublist.size() > 1 && currentSublist.get(currentSublist.size() - 2).getValue() + 2 == currentValue;

                boolean isConsecutiveValue = !isFirstTile && (currentValue == currentSublist.get(currentSublist.size() - 1).getValue() + 1 || (isPreviousTileOkey && isTwoBeforeTileValuePlusTwo));
                boolean isWrapAroundValue = !isFirstTile && currentValue == 1 && currentSublist.get(currentSublist.size() - 1).getValue() == 13;


                if (isFirstTile || isConsecutiveValue || isWrapAroundValue) {
                    currentSublist.add(coloredTile.get(i));
                } else {
                    if (okeyTiles.size() > 0 && currentSublist.size() == minSize - 1) {
                        currentSublist.add(okeyTiles.get(0));
                        okeyTiles.remove(okeyTiles.get(0)); // only use each okeyTiles once.
                    } else {
                        if (currentSublist.size() >= minSize) {
                            runs.add(currentSublist);
                        }
                        currentSublist = new ArrayList<>();
                        currentSublist.add(currentTile);
                    }
                }
                if (i == coloredTile.size() - 1 && currentSublist.size() >= minSize) {
                    runs.add(currentSublist);
                }
            }
        }
        /**
        System.out.println("Runs:");
        for(List<Tile> run : runs){
            for(Tile tile : run){
                System.out.print(tile.getValue());
                if(tile.isOkey()){
                    System.out.print("(O)");
                }
                System.out.print(" ");
            }
         System.out.print(",");
        }
        */
        System.out.println("");

        return runs;

    }
    public int getNumMovesLeftForPairs(List<Tile> hand,Table table) {
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
                return getNumMovesLeftForPairs(tiles,table);
            }
        }

        boolean hasHandOkeyTile = hand.contains(table.getTileSet().getOkeyTile()); // HANDLE HERE BETTER
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
