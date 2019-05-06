package evolith.game;

import java.sql.SQLException;


public class Evolith {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        Game game = new Game("Evolith", 1000, 700);
        game.start();

    }
}
