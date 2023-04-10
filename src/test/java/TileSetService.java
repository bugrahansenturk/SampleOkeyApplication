import models.Table;
import org.junit.jupiter.api.Test;
import services.TileSetService;

import static org.junit.jupiter.api.Assertions.*;

class TileSetServiceTest {

    @Test
    void testInitTileSet() {
        Table table = new Table();
        TileSetService tileSetService = new TileSetService();
        tileSetService.initTileSet(table);

        assertNotNull(table.getTileSet());
        assertNotNull(table.getTileSet().getAllTiles());
        assertEquals(106, table.getTileSet().getAllTiles().size());
    }

    // Add more tests here
}
