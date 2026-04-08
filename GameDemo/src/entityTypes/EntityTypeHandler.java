package entityTypes;

import interfaces.Entity;
import javafx.scene.shape.Rectangle;

public abstract class EntityTypeHandler implements Entity{
    protected final int MAX_RAND_HEIGHT = 400;

    protected double xVelocity = 0; //For "sliding" effects on higher difficulties
    protected double yVelocity = 3.5;

    protected ENTITY_TYPE TYPE = null;
    
    public static enum ENTITY_STATE {
        PASSIVE,
        EASY,
        MODERATE,
        AGGRESSIVE
    };

    public static enum ENTITY_TYPE {
        COIN,
        ENEMY
    };

    public boolean canSlide()
    {
        return xVelocity > 0 ? true : false;
    }

    @Override
    public abstract int getEntityTypeAmount();

    

    @Override
    public double getYVelocity()
    {
        return yVelocity;
    }

    @Override
    public double getXVelocity()
    {
        return xVelocity;
    }

    @Override
    public abstract Rectangle getModel();

    @Override
    public abstract void onCollision(Rectangle collidingObject);

    @Override
    public abstract ENTITY_TYPE getEntityType();
}