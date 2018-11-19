package entities.powerup;

import entities.Entity;
import entities.bomb.Bomb;
import graphics.Screen;
import level.Board;

import static sprites.SpritesImage.bombItem;

public class BombItem extends PowerUp {

    int updateTime = 0;
    Bomb bomb;

    public BombItem(int _x, int _y, Board _board) {
        x = _x;
        y = _y;
        board = _board;
        spriteImage = bombItem.image;
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
                    bomb = new Bomb(0, 0, board);
                    board.bombs.add(bomb);
                    isAdded = true;
                }
            } else {
                updateTime++;
                if (updateTime >= 1200) {
                    System.out.println(updateTime);
                    for (int i = 0; i < board.bombs.size(); i++) {
                        System.out.println(board.bombs.get(i).isPut);
                        if (board.bombs.get(i).isPut == false) {
                            board.bombs.remove(i);
                            isRemoved = true;
                            break;
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
