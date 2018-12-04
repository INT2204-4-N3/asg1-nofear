package entities.tile;

import javafx.scene.image.WritableImage;

public class Grass extends Tile {
    public Grass(int x, int y, WritableImage tileImage) {
        super(x, y, tileImage);
    }

    @Override
    public boolean collide(int _x, int _y) {
        return false;
    }

}
