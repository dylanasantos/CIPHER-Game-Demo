package customExceptions;

public class SceneNotFoundException extends RuntimeException {

    public SceneNotFoundException()
    {
        super();
    }

    public SceneNotFoundException(String custom)
    {
        super(custom);
    }
}