package entities.mob.enemies;

import graphics.Screen;
import javafx.scene.image.WritableImage;

import java.util.Random;

import static sprites.SpritesImage.*;

public class RedCoin extends Enemy {

    public RedCoin(int x, int y) {
        super(x, y);
        speed = 3;
        speedDelay = 2;
        direction = 3 - new Random().nextInt(1)*2;
        spriteImage = redCoinRight.image;
    }

    @Override
    public void update() {
        if (deadTime <= 60) {
            if (isAlive) {
                getImage();
                if (board.player.collide(x, y)) board.player.isAlive = false;
                moveRandom();
            } else {
                deadTime++;
                deadAnimation();
                moveRandom();
            }
            animate();
        }
        else isRemoved = true;
    }

    @Override
    public void render(Screen screen) {
        if (deadTime <= 60) screen.renderEntity(x, y, this);
    }

    private void deadAnimation() {
        if (deadTime%3 == 0) spriteImage = redCoinDead.image;
        else spriteImage = new WritableImage(16, 16);
    }


    public boolean canMove(int x, int y) {
        for (int i = 0; i < board.walls.size(); i++) {
            if (board.walls.get(i).collide(x, y)) return false;
        }
        return true;
    }

    private void moveRandom() {
        if (animate%speedDelay == 0) {
            while (true) {
                if (direction == 1 && canMove(x + speed, y)) {
                    x += speed;
                    break;
                }
                else direction = 3;

                if (canMove(x - speed, y)) {
                    x -= speed;
                    break;
                }
                else direction = 1;
            }
        }
    }

    private void getImage() {
        if (animate%6 == 0) {
            if (spriteImage.equals(redCoinLeft.image)) spriteImage = redCoinRight.image;
            else if (spriteImage.equals(redCoinRight.image)) spriteImage = redCoinLeft1.image;
            else if (spriteImage.equals(redCoinLeft1.image)) spriteImage = redCoinRight1.image;
            else if (spriteImage.equals(redCoinRight1.image)) spriteImage = redCoinLeft2.image;
            else if (spriteImage.equals(redCoinLeft2.image)) spriteImage = redCoinRight2.image;
            else if (spriteImage.equals(redCoinRight2.image)) spriteImage = redCoinLeft.image;
            else spriteImage = redCoinLeft.image;
        }
    }
}
