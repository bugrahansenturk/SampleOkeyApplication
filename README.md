# Okey Game

## Overview

This project is a simplified implementation of the popular Turkish game, Okey. The game is played by 4 players using numbered tiles with 4 different colors. The goal of this implementation is to determine the player who has the best hand based on Okey rules.

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

##Dependencies

This project does not have any external dependencies. It only requires Java 8 or later.
