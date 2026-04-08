package main;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

import handlers.NPCHandler;
import handlers.UserInputHandler;
import entityTypes.CoinEntity;
import entityTypes.EntityTypeHandler;
import interfaces.Entity;

public class Controller implements Initializable {
    @FXML private Pane scene;

    @FXML private Rectangle EnemyReference;
    private final int ENEMY_AMOUNT = 4;

    @FXML private Rectangle CoinReference;
    private final int MAX_COIN_AMOUNT = 4;

    @FXML private Rectangle playerDemo; //Player Model
    public boolean playerDead = false;

    @FXML private Button startButton;

    private UserInputHandler input;
    private final int movementSpeed = 5;

    private NPCHandler npcFigures;

    public static int score = 0;

    private final int NPC_UPDATE_COOLDOWN = 5; //cooldown in seconds
    private long previousTime;
    private int timeCounter = 0;

    private int coinAmount = 0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) 
    {
        Platform.runLater(() -> scene.requestFocus()); //Makes focus on background to selector on reset button
        input = new UserInputHandler(scene);

        npcFigures = new NPCHandler(scene);
        for(int amount = 0; amount < ENEMY_AMOUNT; amount++) //Creating all NPC Objects
        {
            npcFigures.add_npc(EnemyReference, EntityTypeHandler.ENTITY_TYPE.ENEMY);
        }

        Platform.runLater(() -> ScreenScaler.ScaleElements(scene));
    }



    private final AnimationTimer timer = new AnimationTimer() 
    {
        @Override //Write all code expected to run per frame here
        public void handle(long timestamp)
        {
            double offset = playerDemo.getWidth() / 4;
            if (input.isAPressed() && playerDemo.getLayoutX() >= -offset) 
            {
                playerDemo.setLayoutX(playerDemo.getLayoutX() - movementSpeed);
            }
            if (input.isDPressed() && playerDemo.getLayoutX() <= (scene.getWidth() - playerDemo.getWidth() + offset) ) 
            {
                playerDemo.setLayoutX(playerDemo.getLayoutX() + movementSpeed);
            }

            npcFigures.update();

            collisionCheck();

            long currentTime = System.currentTimeMillis(); //updating score via time
            if(currentTime - previousTime >= (NPC_UPDATE_COOLDOWN * 1_000))
            {
                if(timeCounter % 5 == 0 && coinAmount < MAX_COIN_AMOUNT)
                {
                    npcFigures.add_npc(CoinReference, EntityTypeHandler.ENTITY_TYPE.COIN);
                }

                previousTime = currentTime;
                score += 1 + (timeCounter * 1.5);
                timeCounter++;
            }
        }
    };



    @FXML
    void start(ActionEvent event) //Set to player's starting pos on start button click
    {
        scene.requestFocus();

        startButton.setOpacity(0.0);

        previousTime = System.currentTimeMillis();

        npcFigures.start();

        timer.start(); //Starts gane
    }


    
    void collisionCheck()
    {
        for(int i = 0; i < npcFigures.getNPCCount(); i++)
        {
            Entity npc = npcFigures.getNPCObjectAt(i);
            Rectangle model = npcFigures.getNPCModelAt(i);
            
            if(playerDemo.getBoundsInParent().intersects(model.getBoundsInParent()))
            {
                npc.onCollision(playerDemo);
                switch(npc.getEntityType())
                {
                    case EntityTypeHandler.ENTITY_TYPE.ENEMY:
                        timer.stop();
                        break;

                    case EntityTypeHandler.ENTITY_TYPE.COIN: //updating score via token touch
                        score += 10 + (CoinEntity.getCoinsCollected() * 10);
                        break;
                }
            }
        }
    }
}