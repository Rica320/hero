package Elements;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.Random;

public class Monster extends Element{
    private int damage = 50;

    Monster(int x, int y) {
        super(x, y);
        color = "#00FF00";
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString(color));
        graphics.enableModifiers(SGR.CIRCLED);
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), "M");
        graphics.disableModifiers(SGR.CIRCLED);
    }

    public Position move() {
        Random random = new Random(System.currentTimeMillis());
        Position dPos = new Position(random.nextInt(3) -1, random.nextInt(3) -1);

        // if (getPosition().getX() + dPos.getX() < 1 || getPosition().getX() + dPos.getX() >)
        return dPos;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

}
