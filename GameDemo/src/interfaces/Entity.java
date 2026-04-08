package interfaces;

import entityTypes.EntityTypeHandler.ENTITY_TYPE;
import javafx.scene.shape.Rectangle;

public interface Entity 
{
    public Rectangle getModel();
    public void onCollision(Rectangle collidingObject);

    public double getXVelocity();
    public double getYVelocity();

    public int getEntityTypeAmount();

    public int getInstanceCount();

    public ENTITY_TYPE getEntityType();
}