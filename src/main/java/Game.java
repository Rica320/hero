import Elements.Arena;
import Exceptions.GameOverException;
import Exceptions.GameWonException;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Game {
    Screen screen;
    private Arena arena;

    public Game() throws IOException {
        TerminalSize terminalSize = new TerminalSize(40, 20);
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory()
                .setInitialTerminalSize(terminalSize);
        Terminal terminal = terminalFactory.createTerminal();
        this.screen = new TerminalScreen(terminal);

        arena = new Arena( 40,20);

        screen.setCursorPosition(null);   // we don't need a cursor
        screen.startScreen();             // screens must be started
        screen.doResizeIfNecessary();     // resize screen if necessary

    }

    private void draw() throws IOException {
        screen.clear();
        arena.draw(screen.newTextGraphics());
        screen.refresh();
    }

    public void run() throws IOException, GameOverException, GameWonException {
        while (true) {
            try {
                draw();
                KeyStroke key = screen.readInput();
                processKey(key);

                if (key.getKeyType() == KeyType.EOF) {
                    break;
                }
            }catch (GameOverException | GameWonException e) {
                screen.close();
                throw e; // let's let the app finish it
            }
        }
    }

    private void processKey(KeyStroke key) throws IOException, GameOverException, GameWonException {
        arena.processKey(key);
        if ((key.getKeyType() == KeyType.Character) && key.getCharacter() == 'q') { // TODO: MAYBE CHANGE THIS AND PUT AN EXCEPTION
            screen.close();
        }
    }
}
