package entities.tile;

import entities.Entity;
import graphics.Screen;
import javafx.scene.image.WritableImage;

import static sprites.SpritesImage.wall;

public class Wall extends Tile {


    public Wall(int x, int y, WritableImage tileImage) {
        super(x, y, tileImage);
    }

    @Override
    public boolean collide(Entity entity) {
        if (entity.x >= x && entity.x < x + 50 && entity.y >= y && entity.y < y + 50) return true;

        return false;
    }

    @Override
    public boolean collide(int _x, int _y) {
        if (_x >= x - 15 && _x < x + 16 && _y >= y - 15 && _y < y + 16) return true;
        return false;
    }
}
