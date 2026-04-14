package handlers;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;

public class UserInputHandler {
    private final BooleanProperty aPressed = new SimpleBooleanProperty(false);
    private final BooleanProperty dPressed = new SimpleBooleanProperty(false);

    public UserInputHandler(Node scene) //Node is referring to active frame
    {
        scene.setFocusTraversable(true);

        scene.setOnKeyPressed(key -> { //Activates on key press
            updateKey(key.getCode(), true);
        });

        scene.setOnKeyReleased(key -> { //Activates on key release
            updateKey(key.getCode(), false);
        });
    }

    private void updateKey(KeyCode key, boolean state) 
    {
        switch (key) {
            case A -> aPressed.set(state);
            case D -> dPressed.set(state);
            default -> {} // Ignore other keys
        }
    }

    // Getters for the Controller to check values
    public boolean isAPressed() { return aPressed.get(); }
    public boolean isDPressed() { return dPressed.get(); }
}