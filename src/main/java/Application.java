import Exceptions.GameOverException;
import Exceptions.GameWonException;

import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        try {
            Game game = new Game();
            while (game.toPlay()) {
                try {

                    game.run();

                } catch (GameOverException | GameWonException e) {
                    if (e.getClass() == GameWonException.class)
                        game.updateWins();
                    game = new Game(); // we could just do a reset function but whatever
                    game.displayGameStatus(e.getMessage());
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
