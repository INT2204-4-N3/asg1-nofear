package entities.bomb;

import entities.AnimatedEntity;
import entities.Entity;
import entities.tile.Wall;
import graphics.Screen;
import level.Board;

import static sprites.SpritesImage.*;

public class Bomb extends AnimatedEntity {

    public boolean isPut = false, isExploded = true;
    public Explosion explosion = new Explosion();
    int explosionX, explosionY;
    int updateTime = 0;
    Entity entity = this;
    Wall wall;
    int index;
    boolean isAdded = false;
    public Board board;

    public Bomb(int _x, int _y, Board _board) {
        x = _x;
        y = _y;
        board = _board;
    }

    public Bomb() {

    }

    @Override
    public boolean collide(Entity entity) {
        return false;
    }

    @Override
    public void update() {
        if (isPut){
            isExploded = false;
            updateTime ++;
            getImage();
            if (updateTime >= 120) {
                explosion.bombRange = board.player.bombRange;
                explosion.x = x - explosion.bombRange*16;
                explosion.y = y - explosion.bombRange*16;
                explosion.board = board;
                explosion.update();
                entity = explosion;
                board.bombsArea.remove(wall);
            }
            if (updateTime >= 138) {
                explosion.collide();
                updateTime = 0;
                x = 0;
                y = 0;
                isAdded = false;
                isPut = false;
                isExploded = true;
                entity = this;
                spriteImage = bomb.image;
            }
            if (!isAdded) {
                for (int i = 0; i < board.enemies.size(); i++) {
                    if (board.enemies.get(i).collide(x, y)) break;
                    else /*if (board.player.x < x - 15 || board.player.x >= x + 16
                            || board.player.y >= y + 16 || board.player.y < y - 15) */{
                        wall = new Wall(x, y, null);
                        index = board.bombsArea.size();
                        board.bombsArea.add(wall);
                        isAdded = true;
                    }
                }
            }
            animate();
        }
    }

    @Override
    public void render(Screen screen) {
        screen.renderEntity(entity.x, entity.y, entity);
    }

    private void getImage() {
        if (animate%6 == 0) {
            if (spriteImage.equals(bomb.image)) {
                spriteImage = bomb1.image;
            } else if (spriteImage.equals(bomb1.image)) {
                spriteImage = bomb2.image;
            } else if (spriteImage.equals(bomb2.image)) {
                spriteImage = bomb.image;
            } else spriteImage = bomb.image;
        }
    }

    public boolean collide(int _x, int _y) {
        if (_x >= x - 15 && _x < x + 16 && _y >= y - 15 && _y < y + 16) return true;
        return false;
    }
}
