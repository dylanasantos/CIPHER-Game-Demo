package customExceptions;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException()
    {
        super();
    }

    public EntityNotFoundException(String custom)
    {
        super(custom);
    }
}