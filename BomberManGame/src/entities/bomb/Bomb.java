package entities.bomb;

import audio.PlayAudio;
import entities.AnimatedEntity;

import graphics.Screen;

import static sprites.SpritesImage.*;

public class Bomb extends AnimatedEntity {

    public boolean isPut = false, isExploded = true, isCollidingPlayer = false;
    public Explosion explosion = new Explosion();
    int updateTime = 0;

    public Bomb() {
        x = 0;
        y = 0;
        spriteImage = bomb.image;
    }

    @Override
    public void update() {
        if (isPut){
            if (board.player.collide(x, y)) isCollidingPlayer = true;
            else isCollidingPlayer = false;
            updateTime ++;
            if (updateTime == 1) {
                isExploded = false;
                Explosion.bombRange = board.player.bombRange;
                explosion.x = x - Explosion.bombRange * 16;
                explosion.y = y - Explosion.bombRange * 16;
            }
            else if (updateTime >= 120 && updateTime <= 138) {
                if (updateTime == 120) {
                    isCollidingPlayer = false;
                    PlayAudio.playExplosionSound();

                }
                explosion.update();
            }
            else if (updateTime > 138) {
                explosion.collide();
                updateTime = 0;
                x = 0;
                y = 0;
                isPut = false;
                isExploded = true;
                spriteImage = bomb.image;
            }
            if (updateTime < 120) {
                getImage();
                animate();
            }
        }
    }

    @Override
    public void render(Screen screen) {
        if (updateTime <= 120) screen.renderEntity(x, y, this);
        else {
            screen.renderEntity(explosion.x, explosion.y, explosion);
        }

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
        if (_x >= x - 14 && _x < x + 15 && _y >= y - 14 && _y < y + 15) return true;
        return false;
    }


}
