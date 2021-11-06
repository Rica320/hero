import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Arena {
    private int width;
    private int height;

    Hero hero;
    private List<Wall> walls;


    Arena(int width, int height) {
        this.height = height;
        this.width = width;

        hero = new Hero(10,10);
        this.walls = createWalls();

    }

    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();

        for (int c = 0; c < width; c++) {
            walls.add(new Wall(c, 0));
            walls.add(new Wall(c, height - 1));
        }

        for (int r = 1; r < height - 1; r++) {
            walls.add(new Wall(0, r));
            walls.add(new Wall(width - 1, r));
        }

        return walls;
    }

    public void processKey(KeyStroke key) throws IOException {
        KeyType kT = key.getKeyType();
        switch (kT) {
            case ArrowUp:
                System.out.println(key);
                moveHero(hero.moveUp());
                break;
            case ArrowDown:
                System.out.println(key);
                moveHero(hero.moveDown());
                break;
            case ArrowRight:
                System.out.println(key);
                moveHero(hero.moveRight());
                break;
            case ArrowLeft:
                moveHero(hero.moveLeft());
                System.out.println(key);
                break;
        }

    }
    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        hero.draw(graphics); // NOTE: THE ORDER MATTERS ... we first paint the back and then the hero

        for (Wall wall : walls)
            wall.draw(graphics);
    }

    public void moveHero(Position position) {
        if (canHeroMove(position))
            hero.setPosition(position);
    }

    public boolean canHeroMove(Position position) { // TODO: SEE IF IT IS RIGHT
        if(position.getX() < width && position.getX() >= 0 && position.getY() < height && position.getY() >= 0)
            return true;
        else
            return false;

        /*
        if (wall.getPosition().equals(position))
            return false;
        else
            return true;*/
    }
}
