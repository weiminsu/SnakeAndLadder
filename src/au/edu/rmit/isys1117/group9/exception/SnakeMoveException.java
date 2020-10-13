package au.edu.rmit.isys1117.group9.exception;

public class SnakeMoveException extends Exception {
    public SnakeMoveException() {
        super();
    }

    public SnakeMoveException(String mesg)
    {
        super(mesg);
    }
}
