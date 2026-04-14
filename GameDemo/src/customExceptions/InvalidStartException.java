package customExceptions;

public class InvalidStartException extends RuntimeException {

    public InvalidStartException()
    {
        super();
    }

    public InvalidStartException(String custom)
    {
        super(custom);
    }
}