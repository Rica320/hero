import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

public class Arena {
    private int width;
    private int height;

    Hero hero;

    Arena(int width, int height) {
        this.height = height;
        this.width = width;

        hero = new Hero(10,10);
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
    public void draw(Screen screen) {
        hero.draw(screen);
    }

    public void moveHero(Position position) {
        if (canHeroMove(position))
            hero.setPosition(position);
    }

    public boolean canHeroMove(Position position) { // TODO: SEE IF IT IS RIGHT
        if(position.getX() <= width && position.getX() >= 0 && position.getY() <= height && position.getY() >= 0)
            return true;
        else
            return false;
    }
}
