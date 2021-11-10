import com.googlecode.lanterna.graphics.TextGraphics;

abstract public class Element {
    protected Position position;

    Element(int x, int y) {
        this.position = new Position(x,y);
    }

    public Position getPosition() {return position;}

    protected void setPosition(Position position) { // a wall does not move ... does this make sense??
        this.position.setX(position.getX());
        this.position.setY(position.getY());
    }

    abstract public void draw(TextGraphics graphics);
}
