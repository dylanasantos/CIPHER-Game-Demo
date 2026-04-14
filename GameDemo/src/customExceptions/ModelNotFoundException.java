package customExceptions;

public class ModelNotFoundException extends RuntimeException {

    public ModelNotFoundException()
    {
        super();
    }

    public ModelNotFoundException(String custom)
    {
        super(custom);
    }
}
