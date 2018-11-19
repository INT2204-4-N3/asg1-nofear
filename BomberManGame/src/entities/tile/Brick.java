package entities.tile;

import entities.Entity;
import entities.powerup.PowerUp;
import graphics.Screen;
import javafx.scene.image.WritableImage;

import static sprites.SpritesImage.brickExploded;
import static sprites.SpritesImage.brickExploded1;
import static sprites.SpritesImage.brickExploded2;

public class Brick extends Tile {

    public boolean isDestroyed = false;
    public int updateTime = 0;
    public PowerUp powerUp;

    public Brick(int x, int y, WritableImage tileImage) {
        super(x, y, tileImage);
    }

    @Override
    public boolean collide(Entity entity) {
        return false;
    }

    @Override
    public boolean collide(int _x, int _y) {
        if (_x >= x - 15 && _x < x + 16 && _y >= y - 15 && _y < y + 16) return true;
        return false;
    }

    @Override
    public void update() {
        updateTime++;
        if (updateTime == 1) spriteImage = brickExploded.image;
        else if (updateTime == 5) spriteImage = brickExploded1.image;
        else if (updateTime == 10) spriteImage = brickExploded2.image;
        else if (updateTime == 13) isRemoved = true;
    }

    @Override
    public void render(Screen screen) {
        screen.renderEntity(x, y, this);
    }
}
