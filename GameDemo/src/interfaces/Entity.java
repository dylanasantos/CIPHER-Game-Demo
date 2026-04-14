package interfaces;

import entityTypes.EntityTypeHandler.*;
import javafx.scene.shape.Rectangle;

public interface Entity 
{
    public Rectangle getModel();
    public void onCollision(Rectangle collidingObject);

    public double getXVelocity();
    public double getYVelocity();

    public ENTITY_STATE getEntityState();
    public void setEntityState(ENTITY_STATE state);

    public int getEntityTypeAmount(); //Returns the static total of said entity type (if there are 5 enemy objects -> returns 5)

    public int getInstanceCount(); //Use figure which instance is being observed (obj is the 3rd enemy, 4th, etc) -> Used for grids in NPCHandler

    public ENTITY_TYPE getEntityType();
}