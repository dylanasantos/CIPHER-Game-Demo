package main;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

import handlers.NPCHandler;
import handlers.UserInputHandler;
import entityTypes.CoinEntity;
import entityTypes.EntityTypeHandler.*;
import interfaces.Entity;

public class Controller implements Initializable {
    @FXML private Pane scene;

    @FXML private Rectangle EnemyReference;
    private final int ENEMY_AMOUNT = 4;

    @FXML private Rectangle CoinReference;
    private final int MAX_COIN_AMOUNT = 4;

    @FXML private Rectangle PlayerDemo; //Player Model
    public boolean playerDead = false;

    @FXML private Button StartButton;

    @FXML private Label ScoreText;

    @FXML private Label DeathText;

    private UserInputHandler input;
    private final int movementSpeed = 5;

    private NPCHandler npcFigures;

    public static int score = 0;

    private final int NPC_UPDATE_COOLDOWN = 15; //cooldown in seconds
    private final int SCORE_UPDATE_COOLDOWN = 1; //cooldown in seconds
    private long npcTimer;
    private long scoreTimer;
    private int coinCounter = 0;

    private int coinAmount = 0;

    private boolean firstScoreChangeActive = false;
    private boolean secondScoreChangeActive = false;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) 
    {
        Platform.runLater(() -> scene.requestFocus()); //Makes focus on background to selector on reset button
        input = new UserInputHandler(scene);

        npcFigures = new NPCHandler(scene);
        for(int amount = 0; amount < ENEMY_AMOUNT; amount++) //Creating all NPC Objects
        {
            npcFigures.add_npc(EnemyReference, ENTITY_TYPE.ENEMY);
        }

        Platform.runLater(() -> ScreenScaler.ScaleElements(scene));
    }



    private final AnimationTimer timer = new AnimationTimer() 
    {
        @Override //Write all code expected to run per frame here
        public void handle(long timestamp)
        {
            double offset = PlayerDemo.getWidth() / 4;
            if (input.isAPressed() && PlayerDemo.getLayoutX() >= -offset) 
            {
                PlayerDemo.setLayoutX(PlayerDemo.getLayoutX() - movementSpeed);
            }
            if (input.isDPressed() && PlayerDemo.getLayoutX() <= (scene.getWidth() - PlayerDemo.getWidth() + offset) ) 
            {
                PlayerDemo.setLayoutX(PlayerDemo.getLayoutX() + movementSpeed);
            }

            npcFigures.update();

            collisionCheck();

            long currentTime = System.currentTimeMillis(); //updating score via time
            if(currentTime - npcTimer >= (NPC_UPDATE_COOLDOWN * 1_000))
            {
                if(coinAmount < MAX_COIN_AMOUNT)
                {
                    npcFigures.add_npc(CoinReference, ENTITY_TYPE.COIN);
                    coinCounter++;
                }
                npcTimer = currentTime;
            }
            else if(currentTime - scoreTimer >= (SCORE_UPDATE_COOLDOWN * 1_000))
            {
                scoreTimer = currentTime;
                updateScore( (int) (1 + (coinCounter * 1.5)) );
            }
        }
    };



    @FXML
    void start(ActionEvent event) //Set to player's starting pos on start button click
    {
        scene.requestFocus();

        StartButton.setOpacity(0.0);

        npcTimer = System.currentTimeMillis();
        scoreTimer = System.currentTimeMillis();

        npcFigures.start();

        timer.start(); //Starts gane
    }


    
    void collisionCheck()
    {
        for(int i = 0; i < npcFigures.getNPCCount(); i++)
        {
            Entity npc = npcFigures.getNPCObjectAt(i);
            Rectangle model = npcFigures.getNPCModelAt(i);
            
            if(PlayerDemo.getBoundsInParent().intersects(model.getBoundsInParent()))
            {
                npc.onCollision(PlayerDemo);
                switch(npc.getEntityType())
                {
                    case ENTITY_TYPE.ENEMY:
                        endGame();
                        break;

                    case ENTITY_TYPE.COIN: //updating score via token touch
                        updateScore(10 + (CoinEntity.getCoinsCollected() * 10));
                        break;
                }
            }
        }
    }


    void updateScore(int amount)
    {
        score += amount;
        ScoreText.setText("Score: " + score);

        if(score >= 200 && !firstScoreChangeActive)
        {
            firstScoreChangeActive = true;
            npcFigures.increaseHandlerDifficulty();
        }
        if(score >= 1_000 && !secondScoreChangeActive)
        {
            secondScoreChangeActive = true;
            npcFigures.increaseHandlerDifficulty();
        }
    }


    void endGame()
    {
        timer.stop();
        System.out.println("Score: " + score);

        DeathText.setOpacity(1.0);
    }
}