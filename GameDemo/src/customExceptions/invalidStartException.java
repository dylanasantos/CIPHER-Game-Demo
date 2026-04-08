package customExceptions;

public class invalidStartException extends RuntimeException {

    public invalidStartException()
    {
        super();
    }

    public invalidStartException(String custom)
    {
        super(custom);
    }
}