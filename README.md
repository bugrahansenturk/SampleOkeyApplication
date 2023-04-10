# Okey Game

## Overview

This project is a simplified implementation of the popular Turkish game, Okey. The game is played by 4 players using numbered tiles with 4 different colors. The goal of this implementation is to determine the player who has the best hand based on Okey rules.

### testDetermineBestHand()

The `testDetermineBestHand()` method is used to determine the best hand among the players.

How it works?:
It checks each player's hand for pairs and non-pairs. The `testGetNumMovesLeftForPairs()` method is responsible for checking the pairs. It removes same-colored identical tiles from the player's hand as pairs, and the remaining number of tiles in the player's hand indicates the number of moves left.

Subsequently, the `testGetNumMovesLeftForSets()` and `testGetNumMovesLeftForRuns()` methods are executed in order, followed by the `testGetNumMovesLeftForRuns()` and `testGetNumMovesLeftForSets()` methods. These methods return sets and runs as `List<List<Tile>` objects. After each return, the returned elements are removed from the player's hand. This allows for determining which approach is better based on the calculation order by taking the shortest length of the player's hand and printing it on the screen.

## Classes and Components

### Tile

The `Tile` class represents a single tile in the game. Each tile has the following properties:

- `id`: A unique identifier for the tile.
- `color`: The color of the tile, represented by the `TileColor` enum.
- `value`: The value of the tile, ranging from 1 to 13.
- `isOkey`: A boolean flag indicating if the tile is an Okey tile.

### TileSet

The `TileSet` class represents the complete set of tiles used in the game. It includes:

- `allTiles`: A list of all 106 tiles, including duplicates and fake Okey tiles.
- `centerTiles`: A list of tiles in the center of the table.
- `okeyTile`: The designated Okey tile for the current game.

### Player

The `Player` class represents a player in the game. Each player has the following properties:

- `id`: A unique identifier for the player.
- `hand`: A list of tiles in the player's hand.
- `points`: The player's score based on their hand.

### Table

The `Table` class represents the game table, containing:

- `tileSet`: An instance of `TileSet`, representing the complete set of tiles.
- `players`: A list of `Player` objects, representing the 4 players in the game.

### Services

The following service classes are responsible for different game-related operations:

- `TileSetService`: Handles operations related to the `TileSet` class, such as initializing the tile set, selecting the Okey tile, and dealing hands.
- `TableService`: Handles operations related to the `Table` class, such as starting a game and determining the best hand.

## Usage

To run the game, create an instance of the `Game` class and call the `startAndDetermineBestHand()` method:

```java
public class Main {
    public static void main(String[] args) {
        TableService tableService = new TableService();
        Game game = new Game(tableService);
        game.startAndDetermineBestHand();
    }
}
```

This will start a new game, deal hands to the players, and determine the best hand according to Okey rules. The result will be displayed in the console.

## Dependencies

This project does not have any external dependencies. It only requires Java 8 or later.

## Tests

The project contains a series of test cases to ensure the functionality and correctness of the implementation. Below is a summary of the test cases provided:

### TableServiceTest

1. `testGetNumMovesLeftForPairs()`: This test verifies that the `getNumMovesLeftForPairs` method correctly calculates the number of moves left for pairs in a given hand.

2. `testGetNumMovesLeftForSets()`: This test checks that the `getNumMovesLeftForSets` method accurately identifies the sets in a given hand and returns the correct number of sets.

3. `testGetNumMovesLeftForRuns()`: This test ensures that the `getNumMovesLeftForRuns` method correctly identifies the runs in a given hand and returns the appropriate number of runs.

4. `testDetermineBestHand()`: This test verifies that the `determineBestHand` method can correctly identify the player with the best hand, taking into account pairs, sets, and runs.

To run the tests, execute the following command in your terminal:

```bash
./gradlew test
```

## Future Work

1. **Improve the `determineBestHand` method for better performance and results.**
   Refactor and optimize the `determineBestHand` method to provide more accurate results and enhance its performance.

2. **Add a database for storing game data.**
   Implement a database for storing game-related data, such as player information, game history, and statistics.

3. **Handle other Okey moves, such as throwing tiles and other gameplay elements.**
   Implement additional Okey game rules and moves, including throwing tiles, drawing tiles from the center, and more complex gameplay strategies.

4. **Add more tests to ensure the correctness and robustness of the code.**
   Develop comprehensive test suites to cover various scenarios and edge cases, ensuring the correctness of the implemented features and the overall stability of the application.

