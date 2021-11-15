package Exceptions;

public class GameOverException extends Exception{
    public GameOverException() {
        super();
    }

    @Override
    public void printStackTrace() { // this isn't probably the adequate function
        System.out.println("NOOO!! You lost!!");
    }

    @Override
    public String getMessage() {
        return "NOOO!! You lost!!";
    }
}
