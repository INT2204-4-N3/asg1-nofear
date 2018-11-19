package input;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public abstract class KeyboardEvent implements EventHandler<KeyEvent> {

    public static boolean up = false;
    public static boolean right = false;
    public static boolean down = false;
    public static boolean left = false;

    public void reset() {
        up = false;
        right = false;
        down = false;
        left = false;
    }

    @Override
    public void handle(KeyEvent event) {
        reset();
        if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W) {
            reset();
            up = true;
        }
        if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
            reset();
            right = true;
        }
        if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S) {
            reset();
            down = true;
        }
        if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) {
            reset();
            left = true;
        }
    }
}
