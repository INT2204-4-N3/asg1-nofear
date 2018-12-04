package entities.powerup;

import entities.Entity;
import graphics.Screen;
import javafx.scene.image.WritableImage;
import level.Board;

import static sprites.SpritesImage.speedItem;

public class SpeedItem extends PowerUp {

    int updateTime = 0;

    public SpeedItem(int _x, int _y) {
        x = _x;
        y = _y;
        spriteImage = speedItem.image;
        duration = 1800;
    }

    @Override
    public void update() {
        if (showingTime <= 600 || isAdded) {
            if (board.getBrick(x, y) == null) {
                showingTime++;
//            System.out.println(showingTime);
                if (!isAdded) {
                    if (board.player.collide(x, y)) {
                        board.player.speedDelay = 2;
                        isAdded = true;
                    }
                } else {
                    updateTime++;
                    if (updateTime == duration) {
                        board.player.speedDelay = 3;
                        isRemoved = true;
                        isAdded = false;
                    }
                }
            }
        }
    }

    @Override
    public void render(Screen screen) {
        if (showingTime <= 600 && !isAdded) screen.renderEntity(x, y, this);
    }
}
