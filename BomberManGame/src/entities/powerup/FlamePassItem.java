package entities.powerup;

import entities.Entity;
import entities.bomb.Explosion;
import graphics.Screen;

import static sprites.SpritesImage.flamePassItem;

public class FlamePassItem extends PowerUp {

    int updateTime = 0;

    public FlamePassItem(int _x, int _y) {
        x = _x;
        y = _y;
        spriteImage = flamePassItem.image;
        duration = 900;
    }

    @Override
    public void update() {
        if (showingTime <= 600 || isAdded) {
            if (board.getBrick(x, y) == null) {
                showingTime++;

                if (!isAdded) {
                    if (board.player.collide(x, y)) {
                        Explosion.flamePass = true;
                        isAdded = true;
                    }
                } else {
                    updateTime++;
                    if (updateTime >= duration) {
                        for (int i = 0; i < board.bombs.size(); i++) {
                            if (board.bombs.get(i).isExploded == false) break;
                            if (i == board.bombs.size() - 1) {
                                Explosion.flamePass = false;
                                isRemoved = true;
                                isAdded = false;
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
