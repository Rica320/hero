package Exceptions;

public class GameOverException extends Exception{
    public GameOverException() {
        super();
    }

    @Override
    public void printStackTrace() {
        System.out.println("NOOO!! You lost!!");
    }
}
