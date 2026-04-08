package main;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;

//Use for all Pane scaling related methods in the future
public abstract class ScreenScaler { 
    public static void ScaleElements(Pane scene)
    {
        final double SCENE_X_RATIO = (scene.getWidth() / scene.getPrefWidth());
        final double SCENE_Y_RATIO = (scene.getHeight() / scene.getPrefHeight());

        for(Node object : scene.getChildren())
        {
            if(object instanceof Region region)
            {
                region.setPrefWidth(region.getBoundsInParent().getWidth() * SCENE_X_RATIO);
                region.setPrefHeight(region.getBoundsInParent().getHeight() * SCENE_Y_RATIO);
                region.getParent().layout();

                region.setLayoutX(SCENE_X_RATIO * region.getLayoutX());
                region.setLayoutY(SCENE_Y_RATIO * region.getLayoutY());

                if(region instanceof Button button)
                    button.setFont(new Font(button.getFont().getSize() * SCENE_X_RATIO));
            }
            else if(object instanceof Shape shape)
            {
                shape.setScaleX(App.SIZE_RATIO);
                shape.setScaleY(App.SIZE_RATIO);

                shape.setLayoutX(SCENE_X_RATIO * shape.getLayoutX());
                shape.setLayoutY(SCENE_Y_RATIO * shape.getLayoutY());
            }
        }
    }

    public static void applyObjectScale(Pane scene, Node object)
    {
        final double SCENE_X_RATIO = (scene.getWidth() / scene.getPrefWidth());
        final double SCENE_Y_RATIO = (scene.getHeight() / scene.getPrefHeight());

        if(object instanceof Shape shape)
        {
            shape.setScaleX(App.SIZE_RATIO);
            shape.setScaleY(App.SIZE_RATIO);

            shape.setLayoutX(SCENE_X_RATIO * shape.getLayoutX());
            shape.setLayoutY(SCENE_Y_RATIO * shape.getLayoutY());
        }
    }
}