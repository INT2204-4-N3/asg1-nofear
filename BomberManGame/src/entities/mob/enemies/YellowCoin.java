package entities.mob.enemies;

import graphics.Screen;
import javafx.scene.image.WritableImage;

import java.util.Random;

import static sprites.SpritesImage.*;

public class YellowCoin extends Enemy {

    
    public YellowCoin(int x, int y) {
        super(x, y);
        speed = 3;
        speedDelay = 2;
        direction = 2 - new Random().nextInt(1)*2;
        spriteImage = yellowCoinLeft.image;
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
        if (deadTime%3 == 0) spriteImage = yellowCoinDead.image;
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
                if (direction == 0 && canMove(x, y - speed)) {
                    y -= speed;
                    break;
                }
                else direction = 2;

                if (canMove(x, y + speed)) {
                    y += speed;
                    break;
                }
                else direction = 0;
            }
        }
    }

    private void getImage() {
        if (animate%6 == 0) {
            if (spriteImage.equals(yellowCoinLeft.image)) spriteImage = yellowCoinRight.image;
            else if (spriteImage.equals(yellowCoinRight.image)) spriteImage = yellowCoinLeft1.image;
            else if (spriteImage.equals(yellowCoinLeft1.image)) spriteImage = yellowCoinRight1.image;
            else if (spriteImage.equals(yellowCoinRight1.image)) spriteImage = yellowCoinLeft2.image;
            else if (spriteImage.equals(yellowCoinLeft2.image)) spriteImage = yellowCoinRight2.image;
            else if (spriteImage.equals(yellowCoinRight2.image)) spriteImage = yellowCoinLeft.image;
            else spriteImage = yellowCoinLeft.image;
        }
    }
}
