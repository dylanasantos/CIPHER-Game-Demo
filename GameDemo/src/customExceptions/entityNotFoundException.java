package customExceptions;

public class entityNotFoundException extends RuntimeException {

    public entityNotFoundException()
    {
        super();
    }

    public entityNotFoundException(String custom)
    {
        super(custom);
    }
}