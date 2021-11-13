import Exceptions.GameOverException;
import Exceptions.GameWonException;

import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        try {
            Game game = new Game();
            game.run();
        } catch (IOException | GameOverException | GameWonException e) {
            e.printStackTrace();
        }
    }
}
