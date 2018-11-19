package entities.powerup;

import entities.Entity;
import graphics.Screen;
import javafx.scene.image.WritableImage;
import level.Board;

import static sprites.SpritesImage.speedItem;

public class SpeedItem extends PowerUp {

    int updateTime = 0;

    public SpeedItem(int _x, int _y, Board _board) {
        x = _x;
        y = _y;
        board = _board;
        spriteImage = speedItem.image;
    }
    @Override
    public boolean collide(Entity entity) {
        return false;
    }

    @Override
    public void update() {
        if (!isRemoved) {
            if (!isAdded) {
                if (board.player.collide(x, y)) {
                    board.player.speedDelay = 2;
                    isAdded = true;
                }
            } else {
                updateTime++;
                if (updateTime == 1800) {
                    board.player.speedDelay = 3;
                    isRemoved = true;
                }
            }
        }
    }

    @Override
    public void render(Screen screen) {
        if (!isAdded) screen.renderEntity(x, y, this);
    }
}
