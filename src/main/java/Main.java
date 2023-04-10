import models.Table;
import services.TableService;

public class Main {
    public static void main(String[] args) {
        TableService tableService = new TableService();
        Table table = tableService.startGame();
        tableService.determineBestHand(table);
    }
}
