package entityTypes;

import handlers.NPCHandler;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

//NOTE: CoinHandler should multiply in correspondance to the NPC count & speed
public class CoinEntity extends EntityTypeHandler {
    public ENTITY_TYPE TYPE = EntityTypeHandler.ENTITY_TYPE.COIN;

    private Rectangle model;

    private static int coinsCollected = 0;

    private static int coinInstances = 0;
    private int instanceCount;

    public CoinEntity(Rectangle model) {
        this.model = model;
        coinInstances++;
        instanceCount = coinInstances;
    }

    public Rectangle getModel()
    {
        return model;
    }

    public static int getCoinsCollected()
    {
        return coinsCollected;
    }

    @Override
    public void onCollision(Rectangle collidingObject) { //coin should become invisible after collected
        model.setOpacity(0.0);
        coinsCollected++;

        NPCHandler.invokeRespawn((Pane) model.getScene().getRoot(), this);
        model.setOpacity(1.0);
    }

    @Override
    public ENTITY_TYPE getEntityType() {
        return TYPE;
    }

    @Override
    public int getEntityTypeAmount()
    {
        return coinInstances;
    }

    @Override
    public int getInstanceCount()
    {
        return instanceCount;
    }
}