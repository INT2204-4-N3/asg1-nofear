package entities.tile;

import entities.Entity;
import graphics.Screen;
import javafx.scene.image.WritableImage;

public class Portal extends Tile {

    public Portal(int x, int y, WritableImage tileImage) {
        super(x, y, tileImage);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Screen screen) {
        screen.renderEntity(x, y, this);
    }

    @Override
    public boolean collide(int _x, int _y) {
        if (_x >= x - 2 && _x < x + 3 && _y >= y - 2 && _y < y + 3) return true;
        return false;
    }

}
