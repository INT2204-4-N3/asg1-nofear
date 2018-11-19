package entities;

import graphics.IRender;
import javafx.scene.image.WritableImage;
import level.Board;
import sprites.SpritesImage;

public abstract class Entity implements IRender {

    public int x, y;
    public boolean isRemoved = false;
    public SpritesImage sprite;
    public WritableImage spriteImage = new WritableImage(50, 50);
    protected Board board;

    public abstract boolean collide(Entity entity);

}
