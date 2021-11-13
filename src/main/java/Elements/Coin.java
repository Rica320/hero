package Elements;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Coin extends Element{

    Coin(int x, int y) {
        super(x, y);
        color = "#FFFF33";
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString(color));
        graphics.enableModifiers(SGR.ITALIC);
        graphics.enableModifiers(SGR.BLINK);
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), "O");
        graphics.disableModifiers(SGR.ITALIC);
        graphics.disableModifiers(SGR.BLINK);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null) return false;

        if (getClass() != o.getClass()) return false;

        return getPosition().equals(((Coin) o).position); // No 2 coins at a same position
    }
}
