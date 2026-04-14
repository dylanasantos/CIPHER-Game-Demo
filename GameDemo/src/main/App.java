package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class App extends Application {
    public static final double SIZE_RATIO = (1.0 / 2.0);

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("/fxmlFiles/sample.fxml"));
        primaryStage.setTitle("Game Demo");
        primaryStage.setScene(new Scene(root));

        Rectangle2D dimensions = Screen.getPrimary().getBounds();
        primaryStage.setWidth(dimensions.getWidth() * SIZE_RATIO);
        primaryStage.setHeight(dimensions.getHeight() * SIZE_RATIO);

        primaryStage.setResizable(false);

        primaryStage.show();
    }


    public static void main(String[] args) 
    {
        launch(args);
    }
}