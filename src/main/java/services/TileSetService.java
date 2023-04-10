package services;

import models.Player;
import models.Table;
import models.TileSet;

import java.util.List;

public class TileSetService {

    public TileSet initTileSet(Table table){
        List<Player> players = table.getPlayers();
        TileSet tileSet = new TileSet();
        tileSet.selectOkeyTile();
        tileSet.dealHands(players);
        return tileSet;
    }




}
