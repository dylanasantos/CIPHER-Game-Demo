package handlers;

import java.util.ArrayList;
import java.util.List;

import customExceptions.entityNotFoundException;
import customExceptions.invalidStartException;
import entityTypes.CoinEntity;
import entityTypes.EnemyEntity;
import entityTypes.EntityTypeHandler.ENTITY_TYPE;
import interfaces.Entity;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import main.ScreenScaler;

public class NPCHandler {
    private static final int MAX_RAND_HEIGHT = 400;

    private int respawnCount = 0;

    private Pane scene;
    private List<Entity> entityList = new ArrayList<>(); //stores every model

    private boolean managerActive = false;

    private int enemyCounter = 0;
    private int coinCounter = 0;


    public NPCHandler(Pane scene) 
    {
        this.scene = scene;
    }



    //Creates npc with randomized Y level out of scene view
    public void add_npc(Rectangle npcReference, ENTITY_TYPE type) //Only supports rectangle objects -> adjust in later versions when more shapes are included
    {
        Rectangle model = new Rectangle(npcReference.getWidth(), npcReference.getHeight());
        model.setStroke(npcReference.getStroke());
        model.setFill(npcReference.getFill());

        ScreenScaler.applyObjectScale(scene, model);

        if(type == ENTITY_TYPE.ENEMY)
        {
            enemyCounter++;
            model.setId("enemy" + enemyCounter);

            EnemyEntity new_enemy = new EnemyEntity(model);
            entityList.add(new_enemy);
        }
        else if(type == ENTITY_TYPE.COIN)
        {
            model.setOpacity(1.0);

            coinCounter++;
            model.setId("coin" + coinCounter);
            
            CoinEntity new_coin = new CoinEntity(model);
            entityList.add(new_coin);

            scene.getChildren().add(model);
            respawn(new_coin, new_coin.getInstanceCount());
            return;
        }

        model.setLayoutY(0 - ( Math.random() * (MAX_RAND_HEIGHT - model.getHeight()) + model.getHeight()) );
        model.setLayoutX(-40);

        scene.getChildren().add(model);
    }



    public Entity getNPCObjectAt(int index)
    {
        return entityList.get(index);
    }



    public Rectangle getNPCModelAt(int index)
    {
        return entityList.get(index).getModel();
    }



    public int getNPCCount()
    {
        return entityList.size();
    }



    public int getRespawnCount()
    {
        return respawnCount;
    }


    //Runs on each frame
    public void update()
    {
        for(Entity e : entityList)
        {
            Rectangle model = e.getModel();
            if(model.getLayoutY() > scene.getHeight()) //Player is out of frame
            {
                respawn(e, e.getInstanceCount());
            }
            else
            {
                model.setLayoutY(model.getLayoutY() + e.getYVelocity());
            }
        }
    }

    /*
        *Runs on player pressing start -> ONLY CALL ONCE

        *Used for locating all models to their respective grids to properly space objects on game's first occurence
            ^Afterwards, each object will rely on respawn() to teleport to reset X and Y values when off-screen
    */
    public void start()
    {
        if(managerActive) { throw new invalidStartException("NPCManager start() can only be called once per scene set."); }
        managerActive = true;
        
        if(entityList.size() == 1) 
        { 
            entityList.get(0).getModel().setLayoutX((Math.random() * scene.getWidth()));
            return;
        }

        for(int gridSection = 1; gridSection <= entityList.size(); gridSection++)
        {
            Rectangle currModel = entityList.get(gridSection - 1).getModel();
            currModel.setOpacity(1.0);
            double gridPos = generateRandomXGridPos(gridSection, currModel.getWidth(), entityList.size());

            currModel.setLayoutX(gridPos);
        }
    }



    //Update model to reset with grids properly 
    private void respawn(Entity e, int gridSection)
    {
        Rectangle model = e.getModel();

        double gridXPos = generateRandomXGridPos(gridSection, model.getWidth(), e.getEntityTypeAmount());
        double yPos = 0 - ( Math.random() * (MAX_RAND_HEIGHT - model.getHeight()) + model.getHeight());

        model.setLayoutX(gridXPos);
        model.setLayoutY(yPos);

        respawnCount++;
    }


    //used for allowing objects to respawn without direct access to the NPCHandler object
    public static void invokeRespawn(Pane scene, Entity e)
    {
        if(scene == null)
            throw new entityNotFoundException("Invoked respawn on Entity with NPCHandler, yet not matching scene was found");

        Rectangle model = e.getModel();

        int typeDivisor = e.getEntityTypeAmount();
        int gridSection = e.getInstanceCount();

        double gridSize = scene.getWidth() / typeDivisor;

        double gridStart = gridSize * (gridSection - 1);
        double gridEnd = (gridSize * gridSection) - model.getWidth();

        double gridXPos = (Math.random() * (gridEnd - gridStart)) + gridStart;

        double yPos = 0 - ( Math.random() * (MAX_RAND_HEIGHT - model.getHeight()) + model.getHeight());

        model.setLayoutX(gridXPos);
        model.setLayoutY(yPos);
    }

    
    //Calculation function to prevent redundancy
    private double generateRandomXGridPos(double gridSection, double modelWidth, int typeDivisor)
    {
        double gridSize = scene.getWidth() / typeDivisor;

        double gridStart = gridSize * (gridSection - 1);
        double gridEnd = (gridSize * gridSection) - modelWidth;

        double gridXPos = (Math.random() * (gridEnd - gridStart)) + gridStart;

        return gridXPos;
    }
}