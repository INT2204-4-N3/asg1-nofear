package entities.tile;

import entities.Entity;
import graphics.Screen;
import javafx.scene.image.WritableImage;

public abstract class Tile extends Entity {

    public Tile(int x, int y, WritableImage tileImage) {
        super.x = x;
        super.y = y;
        super.spriteImage = tileImage;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Screen screen) {
        screen.renderEntity(x, y, this);
    }

    public abstract boolean collide(int _x, int _y);
}
