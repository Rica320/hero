package Elements;

import Exceptions.ExitArenaException;
import Exceptions.GameOverException;
import Exceptions.GameWonException;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena {
    protected final int width;
    protected final int height;

    Hero hero;
    protected List<Wall> walls;
    protected List<Coin> coins;
    protected List<Monster> monsters;
    protected Door door;

    public Arena(int width, int height) {
        this.height = height;
        this.width = width;

        hero = new Hero(10, 10);
        this.walls = createWalls();
        this.coins = createCoins();
        this.monsters = createMonster();
        this.door = createDoor();

    }

    protected List<Wall> createWalls() {
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

    protected List<Coin> createCoins() { // TODO: CHECK IF COINS SPOON ON A WALL(final arena) !!! IGNORE FOR NOW
        Random random = new Random();
        ArrayList<Coin> coins = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Position pos = new Position(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1);
            if (walls.contains(new Wall(pos.getX(), pos.getY()))) {
                i--;
                continue;
            }
            if (pos.equals(hero.position)) {
                i--;
                continue;
            } else if (coins.contains(new Coin(pos.getX(), pos.getY()))) {
                i--;
                continue;
            }
            coins.add(new Coin(pos.getX(), pos.getY()));
        }
        return coins;
    }

    protected List<Monster> createMonster() {
        Random random = new Random(System.currentTimeMillis());
        ArrayList<Monster> monsters = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Position pos = new Position(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1);
            if (pos.equals(hero.position)) {
                i--;
                continue;
            }
            else if (monsters.contains(new Monster(pos.getX(), pos.getY()))) {
                i--;
                continue;
            }
            if (i == 4)
                monsters.add(new BigBossMonster(pos.getX(), pos.getY()));
            else
                monsters.add(new Monster(pos.getX(), pos.getY()));
        }


        return monsters;
    }

    protected Door createDoor() {
        Random random = new Random(System.currentTimeMillis());// curiosity: it will always spoon on the first place of one monster
        return new Door(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1); // just don't mind about the position
    }

    private void retrieveCoins() {
        coins.removeIf(coin -> coin.position.equals(hero.position));
    }

    private void openDoor() {
        if (coins.isEmpty() && hero.getPosition().equals(door.getPosition()))
            door.setOpen(true);
    }

    public void processKey(KeyStroke key) throws GameOverException, GameWonException , ExitArenaException {
        KeyType kT = key.getKeyType();

        switch (kT) {
            case ArrowUp -> moveHero(hero.moveUp());
            case ArrowDown -> moveHero(hero.moveDown());
            case ArrowRight -> moveHero(hero.moveRight());
            case ArrowLeft -> moveHero(hero.moveLeft());
        }

        verifyMonsterCollisions();
        verifyWin();
        moveMonsters();
        verifyMonsterCollisions();

    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        hero.draw(graphics); // NOTE: THE ORDER MATTERS ... we first paint the back and then the hero

        for (Wall wall : walls)
            wall.draw(graphics);

        for (Coin coin : coins)
            coin.draw(graphics);

        for (Monster monster : monsters) {
            monster.draw(graphics);
        }

        if (coins.isEmpty())
            door.draw(graphics);
    }

    public void moveHero(Position position) {
        if (canHeroMove(position)) {
            hero.setPosition(position);
            retrieveCoins();
            openDoor();
        }
    }

    public void moveMonsters() {
        for (Monster monster : monsters) {
            if (monster.getClass() == BigBossMonster.class)
                ((BigBossMonster) monster).setTargetPos(hero.position);

            // TODO: check if the monster is inside the arena after move
            Position pos;
            pos = monster.move();
            if (isMonsterInPos(new Position(pos.getX() + monster.getPosition().getX(),
                                             pos.getY() + monster.getPosition().getY()))) {
                pos.setX(0);
                pos.setY(0); // respect social distance
            }
            else if (!canMonsterMove(new Position(pos.getX() + monster.getPosition().getX(),
                                                  pos.getY() + monster.getPosition().getY()))) // monster should not runway from the arena
            {
                if (monster.getPosition().getX() == 1 || monster.getPosition().getX() == width-2)
                    pos.setX(0);

                if (monster.getPosition().getY() == 1 || monster.getPosition().getY() == height-2)
                    pos.setY(0);
            }

            monster.getPosition().add(pos);
        }
    }

    public boolean canHeroMove(Position position) {
        for (Wall wall : walls) {
            if (wall.getPosition().equals(position))
                return false;
        }
        return true;
    }

    public boolean canMonsterMove(Position position) {
        for (Wall wall : walls) {
            if (wall.getPosition().equals(position))
                return false;
        }
        return true;
    }

    public boolean isMonsterInPos(Position position) {
       for (Monster monster: monsters) {
           if (monster.getPosition().equals(position)) {
               return true; // Personal space is important even for monsters
           }
       }
       return false;

    }

    public void verifyMonsterCollisions() throws GameOverException {
        ArrayList<Monster> toRemove = new ArrayList<>();
        for (Monster monster : monsters) {
            if (monster.getPosition().equals(hero.position)) {
                int newEnergy = hero.getEnergy()-monster.getDamage();
                hero.setEnergy(newEnergy);
                toRemove.add(monster);
                if (hero.getEnergy() <= 0)
                    throw new GameOverException();
            }
        }
        monsters.removeAll(toRemove); // the hero beats the crap out of the monsters, if alive
    }

    public void verifyWin() throws ExitArenaException, GameWonException {
        if (coins.isEmpty() && door.isOpen())
            throw new ExitArenaException();
    }

}