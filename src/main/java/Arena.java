import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena {
    private int width;
    private int height;

    Hero hero;
    private List<Wall> walls;
    private List<Coin> coins;
    private List<Monster> monsters;


    Arena(int width, int height) {
        this.height = height;
        this.width = width;

        hero = new Hero(10,10);
        this.walls = createWalls();
        this.coins = createCoins();
        this.monsters = createMonster();

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

    private List<Coin> createCoins() {
        Random random = new Random();
        ArrayList<Coin> coins = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Position pos = new Position(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1);
            if(pos.equals(hero.position)){
                i--;
                System.out.println("IT WORKSSSS!!!!!!!");
                continue;
            } else if(coins.contains(new Coin(pos.getX(),pos.getY()))) {
                i--;
                System.out.println("IT WORKSSSS!!!!!!!");
                continue;
            }
            coins.add(new Coin(pos.getX(),pos.getY()));
        }
        return coins;
    }

    private List<Monster> createMonster() {
        Random random = new Random();
        ArrayList<Monster> monsters = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Position pos = new Position(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1);
            if(pos.equals(hero.position)){
                i--;
                System.out.println("IT WORKSSSS!!!!!!!");
                continue;
            }
            monsters.add(new Monster(pos.getX(),pos.getY()));
        }
        return monsters;
    }

    private void retrieveCoins() {
        coins.removeIf(coin -> coin.position.equals(hero.position));
    }

    public void processKey(KeyStroke key) throws IOException,GameOverException {
        KeyType kT = key.getKeyType();
        switch (kT) {
            case ArrowUp:
                moveHero(hero.moveUp());
                break;
            case ArrowDown:
                moveHero(hero.moveDown());
                break;
            case ArrowRight:
                moveHero(hero.moveRight());
                break;
            case ArrowLeft:
                moveHero(hero.moveLeft());
                break;
        }
        verifyMonsterCollisions();
        moveMonsters();
        verifyMonsterCollisions(); // it is maybe better to check the collision inside the move monster !?
    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        hero.draw(graphics); // NOTE: THE ORDER MATTERS ... we first paint the back and then the hero

        for (Wall wall : walls)
            wall.draw(graphics);

        for (Coin coin : coins)
            coin.draw(graphics);

        for (Monster monster: monsters) {
            monster.draw(graphics);
        }
    }

    public void moveHero(Position position) {
        if (canHeroMove(position)) {
            hero.setPosition(position);
            retrieveCoins();
        }
    }

    public void moveMonsters() {
        for (Monster monster: monsters) {
            // TODO: check if the monster is inside the arena after move
            monster.getPosition().add(monster.move());
        }
    }

    public boolean canHeroMove(Position position) { // TODO: SEE IF IT IS RIGHT
        for (Wall wall : walls) {
            if (wall.getPosition().equals(position))
                return false;
        }
        return true;
    }

    public void verifyMonsterCollisions() throws GameOverException{
        for (Monster monster: monsters) {
            if (monster.getPosition().equals(hero.position)){
                throw new GameOverException();
            }
        }
    }

}
