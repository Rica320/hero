import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.Random;

public class Monster extends Element{

    Monster(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#00FF00"));
        graphics.enableModifiers(SGR.BLINK);
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), "M");
    }

    public Position move() {
        Random random = new Random();
        Position dPos = new Position(random.nextInt(2) -1, random.nextInt(2) -1);

        // if (getPosition().getX() + dPos.getX() < 1 || getPosition().getX() + dPos.getX() >)
        return dPos;
    }
}
