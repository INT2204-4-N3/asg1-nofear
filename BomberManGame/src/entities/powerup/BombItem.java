package entities.powerup;

import entities.Entity;
import entities.bomb.Bomb;
import graphics.Screen;
import level.Board;

import static sprites.SpritesImage.bombItem;

public class BombItem extends PowerUp {

    int updateTime = 0;

    public BombItem(int _x, int _y) {
        x = _x;
        y = _y;
        spriteImage = bombItem.image;
        duration = 1200;
    }

    @Override
    public boolean collide(Entity entity) {
        return false;
    }

    @Override
    public void update() {
        if (showingTime <= 600 || isAdded) {
            if (board.getBrick(x, y) == null) {
                showingTime++;

                if (!isAdded) {
                    if (board.player.collide(x, y)) {
                        board.bombs.add(new Bomb());
                        isAdded = true;
                    }
                } else {
                    updateTime++;
                    if (updateTime >= duration) {
                        for (int i = 0; i < board.bombs.size(); i++) {
                            if (board.bombs.get(i).isPut == false) {
                                board.bombs.remove(i);
                                isRemoved = true;
                                isAdded = false;
                                break;
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
