import models.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.TableService;

import static org.junit.jupiter.api.Assertions.*;

class TableServiceTest {

    private TableService tableService;
    private Table table;

    @BeforeEach
    void setUp() {
        tableService = new TableService();
        table = tableService.startGame();
    }

    @Test
    void testStartGame() {
        assertNotNull(table.getTileSet());
        assertNotNull(table.getPlayers());
        assertEquals(4, table.getPlayers().size());
    }

    @Test
    void testGetNumMovesLeftForPairs() {
        int numMovesLeftForPairs = tableService.getNumMovesLeftForPairs(table.getPlayers().get(0).getHand(), table);
        assertTrue(numMovesLeftForPairs >= 0);
    }

    @Test
    void testGetNumMovesLeftForSets() {
        var sets = tableService.getNumMovesLeftForSets(table.getPlayers().get(0).getHand());
        assertNotNull(sets);
    }

    @Test
    void testGetNumMovesLeftForRuns() {
        var runs = tableService.getNumMovesLeftForRuns(table.getPlayers().get(0).getHand());
        assertNotNull(runs);
    }

    // Add more tests here
}
