package Elements;

import Exceptions.GameWonException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

// In the final arena the monster can pass walls but the hero can't
public class FinalArena extends Arena{

    public FinalArena(int width, int height) {
        super(width, height);
        createWalls();
    }

    @Override
    protected List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();

        try {
            Scanner sc = new Scanner(new File("src/main/java/Farena"));
            int y = 0;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                for (int i=0; i < line.length(); i++) {
                    if ( line.charAt(i) == 'H')
                        walls.add(new Wall(i,y));
                }
                y++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return walls;
    }

    @Override
    protected List<Monster> createMonster() {
        List<Monster> monsters = super.createMonster();
        Random random = new Random(System.currentTimeMillis());
        Position pos = new Position(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1);
        monsters.add(new BigBossMonster(pos.getX(), pos.getY()));
        return monsters;
    }

    @Override
    public void verifyWin() throws GameWonException {
        if (coins.isEmpty() && door.isOpen())
            throw new GameWonException(hero.getEnergy());
    }

}
