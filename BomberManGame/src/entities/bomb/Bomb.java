package entities.bomb;

import audio.PlayAudio;
import entities.AnimatedEntity;
import entities.Entity;
import entities.tile.Wall;
import graphics.Screen;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import level.Board;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

import static sprites.SpritesImage.*;

public class Bomb extends AnimatedEntity {

    public boolean isPut = false, isExploded = true, isCollidingPlayer = false;
    public Explosion explosion = new Explosion();
    int updateTime = 0;
    Entity entity = this;

    public Bomb() {
        x = 0;
        y = 0;
        spriteImage = bomb.image;
    }

    @Override
    public boolean collide(Entity entity) {
        return false;
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
                    entity = explosion;
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
                entity = this;
                spriteImage = bomb.image;
            }
            getImage();
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
        if (_x >= x - 14 && _x < x + 15 && _y >= y - 14 && _y < y + 15) return true;
        return false;
    }


}
