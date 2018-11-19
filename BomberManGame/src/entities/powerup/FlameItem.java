package entities.powerup;

import entities.Entity;
import graphics.Screen;
import level.Board;

import static sprites.SpritesImage.flameItem;

public class FlameItem extends PowerUp {

    int updateTime = 0;

    public FlameItem(int _x, int _y, Board _board) {
        x = _x;
        y = _y;
        board = _board;
        spriteImage = flameItem.image;
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
                    board.player.bombRange++;
//                    for (int i = 0; i < board.bombs.size(); i++) {
//                        board.bombs.get(i).explosion.bombRange++;
//                    }
//                    System.out.println(" tang xong");
                    isAdded = true;
                }
            } else {
                updateTime++;
                if (updateTime >= 1800) {
                    for (int i = 0; i < board.bombs.size(); i++) {
                        if (board.bombs.get(i).isExploded == false) break;
                        if (i == board.bombs.size() - 1) {
                            isRemoved = true;
                            board.player.bombRange--;
                            System.out.println(updateTime);
//                            System.out.println("giam xong");
                        }
                    }
                }
            }
        }
    }

    @Override
    public void render(Screen screen) {
        if (!isAdded) screen.renderEntity(x, y, this);
    }
}
