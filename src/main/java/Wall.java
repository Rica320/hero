import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Wall {
    private final int width;
    private final int height;

    public Wall(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFFF33"));
        graphics.putString(new TerminalPosition(width , height), "H");
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
