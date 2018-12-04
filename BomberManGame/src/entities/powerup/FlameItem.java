package entities.powerup;

import entities.Entity;
import graphics.Screen;
import level.Board;

import static sprites.SpritesImage.flameItem;

public class FlameItem extends PowerUp {

    int updateTime = 0;

    public FlameItem(int _x, int _y) {
        x = _x;
        y = _y;
        spriteImage = flameItem.image;
        duration = 1200;
    }

    @Override
    public void update() {
        if (showingTime <= 600 || isAdded) {
            if (board.getBrick(x, y) == null) {
                showingTime++;
                if (!isAdded) {
                    if (board.player.collide(x, y)) {
                        board.player.bombRange++;
                        isAdded = true;
                    }
                } else {
                    updateTime++;
                    if (updateTime >= duration) {
                        for (int i = 0; i < board.bombs.size(); i++) {
                            if (board.bombs.get(i).isExploded == false) break;
                            if (i == board.bombs.size() - 1) {
                                isRemoved = true;
                                isAdded = false;
                                board.player.bombRange--;
                            }
                        }
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
