package Exceptions;

public class GameWonException extends Exception{
    private final int energyLeft;
    public GameWonException(int energyLeft) {
        super();
        this.energyLeft = energyLeft;
    }

    @Override
    public void printStackTrace() {
        System.out.println("You WON!!");
        System.out.println("your energy: " + energyLeft);
    }

    @Override
    public String getMessage() {
        return "You WON!! With " + energyLeft + " of Energy";
    }
}
