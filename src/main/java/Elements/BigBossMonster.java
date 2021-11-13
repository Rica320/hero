package Elements;

public class BigBossMonster extends Monster{
    private Position targetPos;
    private int energy = 100;
    BigBossMonster(int x, int y) {
        super(x, y);
        setDamage(100);
        color = "#f8d210";
    }

    @Override
    public Position move() {
        if (energy > 50) {
            int dX = (position.getX() >= targetPos.getX()) ? ((position.getX() == targetPos.getX()) ? 0 : -1) : 1;
            int dY = (position.getY() >= targetPos.getY()) ? ((position.getY() == targetPos.getY()) ? 0 : -1) : 1;

            energy -= 50;
            return new Position(dX,dY);
        }
        energy += 70; // the recharge... Note: otherwise the hero always looses
        return new Position(0,0);
    }

    public void setTargetPos(Position targetPos) {
        this.targetPos = targetPos;
    }

}
