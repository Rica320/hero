import Elements.Arena;
import Elements.FinalArena;
import Exceptions.ExitArenaException;
import Exceptions.GameOverException;
import Exceptions.GameWonException;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.*;
import java.util.Scanner;

public class Game {
    Screen screen;
    private Arena arena;
    private final FinalArena finalArena;

    public Game() throws IOException {
        TerminalSize terminalSize = new TerminalSize(40, 20);
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory()
                .setInitialTerminalSize(terminalSize);
        Terminal terminal = terminalFactory.createTerminal();
        this.screen = new TerminalScreen(terminal);

        arena = new Arena( 40,20);
        finalArena = new FinalArena(40, 20);

        screen.setCursorPosition(null);   // we don't need a cursor
        screen.startScreen();             // screens must be started
        screen.doResizeIfNecessary();     // resize screen if necessary

    }

    private void draw() throws IOException {
        screen.clear();
        arena.draw(screen.newTextGraphics());
        screen.refresh();
    }

    public void menu_draw() throws IOException{ // BETTER THAN CREATING ANOTHER SCREEN(in a class MENU)
        screen.clear();
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(40, 20), ' ');
        graphics.putString(new TerminalPosition(10, 5), "Num Wins:" + getNWins());
        graphics.putString(new TerminalPosition(10, 8), "Press a key to play");
        graphics.putString(new TerminalPosition(10, 10), "Press q to leave");
        screen.refresh();
    }

    private int getNWins() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("src/main/java/nWins"));
        int nWins = sc.nextInt();
        sc.close();

        return nWins;
    }
    public void updateWins() throws IOException {

        int f = getNWins() + 1;
        File myFoo = new File("src/main/java/nWins");
        FileWriter fooWriter = new FileWriter(myFoo, false);
        fooWriter.write(String.valueOf(f));
        fooWriter.close();

    }

    public boolean toPlay() throws IOException {
        menu_draw();
        KeyStroke key = screen.readInput();
        if (key.getKeyType() == KeyType.EOF)
            return false;

        return processMenuKey(key);
    }

    private boolean processMenuKey(KeyStroke key) throws IOException{
        if ((key.getKeyType() == KeyType.Character) && key.getCharacter() == 'q') {
            screen.close();
            return false;
        }
        return true; // press a key diff than q to play
    }

    public void run() throws IOException, GameOverException, GameWonException {
        while (true) {
            try { // for arena in arenaList ...
                draw();
                KeyStroke key = screen.readInput();
                processKey(key);

                if (key.getKeyType() == KeyType.EOF) {
                    break;
                }
            }catch (GameOverException | GameWonException e) {
                screen.close();
                throw e; // let's let the app finish it
            }catch (ExitArenaException e) {
                arena = finalArena;
            }
        }
    }

    private void processKey(KeyStroke key) throws IOException, GameOverException, GameWonException, ExitArenaException {
        arena.processKey(key);
        if ((key.getKeyType() == KeyType.Character) && key.getCharacter() == 'q') {
            screen.close();
        }
    }

    public void displayGameStatus(String msg) throws IOException{
        screen.clear();
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(40, 20), ' ');
        graphics.putString(new TerminalPosition(8, 9), msg);

        screen.refresh();

        KeyStroke key = screen.readInput();
        processMenuKey(key); // the same procedure
    }
}
