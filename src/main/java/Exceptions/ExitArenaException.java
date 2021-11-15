package Exceptions;

public class ExitArenaException extends Exception{
    public ExitArenaException() {
        super();
    }

    @Override
    public void printStackTrace() {
        System.out.println("You WON this arena!!!");
    }
}
