package entityTypes;

import interfaces.Entity;
import javafx.scene.shape.Rectangle;

public abstract class EntityTypeHandler implements Entity{
    protected final int MAX_RAND_HEIGHT = 400;

    protected double xVelocity = 0; //For "sliding" effects on higher difficulties
    protected double yVelocity = 0;
    protected double standardYVelocity = 3.50;

    protected ENTITY_TYPE TYPE = null;
    
    public static enum ENTITY_STATE 
    {
        PASSIVE,
        EASY,
        MODERATE,
        AGGRESSIVE
    };
    protected ENTITY_STATE entityState = ENTITY_STATE.PASSIVE;

    public static enum ENTITY_TYPE 
    {
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
    public ENTITY_STATE getEntityState()
    {
        return this.entityState;
    }

    @Override
    public void setEntityState(ENTITY_STATE state)
    {
        this.entityState = state;

        switch(state)
        {
            case PASSIVE:
                yVelocity *= standardYVelocity * 0.00;
                break;

            case EASY:
                yVelocity = standardYVelocity * 1.00;
                break;
            
            case MODERATE:
                yVelocity = standardYVelocity * 1.60;
                break;
            
            case AGGRESSIVE:
                yVelocity = standardYVelocity * 2.15;
                break;
        }
    }

    @Override
    public abstract Rectangle getModel();

    @Override
    public abstract void onCollision(Rectangle collidingObject);

    @Override
    public abstract ENTITY_TYPE getEntityType();
}