public class GameOverException extends Exception{
    GameOverException() {
        super();
    }

    @Override
    public void printStackTrace() {
        System.out.println("NOOO!! You lost!!");
    }
}
