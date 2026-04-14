package entityTypes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class EnemyEntity extends EntityTypeHandler {
    public static ENTITY_TYPE TYPE = ENTITY_TYPE.ENEMY;
    private Rectangle model;

    private static int enemyInstances = 0;
    private int instanceCount;


    public EnemyEntity(Rectangle model) 
    {
        this.model = model;

        setEntityState(ENTITY_STATE.EASY);

        enemyInstances++;
        instanceCount = enemyInstances;
    }

    @Override
    public Rectangle getModel()
    {
        return model;
    }

    @Override
    public void onCollision(Rectangle collidingObject)
    {
        collidingObject.setFill(Color.BLACK);
    }

    @Override
    public ENTITY_TYPE getEntityType()
    {
        return TYPE;
    }

    @Override
    public int getEntityTypeAmount()
    {
        return enemyInstances;
    }

    @Override
    public int getInstanceCount()
    {
        return instanceCount;
    }
}