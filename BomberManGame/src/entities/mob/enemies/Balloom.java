package entities.mob.enemies;

import entities.Entity;
import graphics.Screen;
import javafx.scene.image.WritableImage;
import level.Board;

import java.util.Random;

import static sprites.SpritesImage.*;

public class Balloom extends Enemy {

    int updateTime = 0;
    public int speedDelay = 3;

    public Balloom(int x, int y, Board board) {
        super(x, y, board);
        speed = 1;
        direction = new Random().nextInt(4);
    }

    @Override
    public boolean collide(Entity entity) {
        return false;
    }

    @Override
    public void update() {
        if (updateTime <= 60) {
            if (isAlive) {
                getImage();
                moveRandom();
                if (board.player.collide(x, y)) board.player.isAlive = false;
            } else {
                updateTime++;
                deadAnimation();
                moveRandom();
            }
            animate();
        }
        else isRemoved = true;
    }

    @Override
    public void render(Screen screen) {
       if (updateTime <= 60) screen.renderEntity(x, y, this);
    }

    private void deadAnimation() {
        if (updateTime%3 == 0) spriteImage = balloomDead.image;
        else spriteImage = new WritableImage(1, 1);
    }

    public boolean collide(int _x, int _y) {
        if (_x >= x - 15 && _x < x + 16 && _y >= y - 15 && _y < y + 16) return true;
        return false;
    }

    private void moveRandom() {
        if (animate%speedDelay == 0) {
            boolean moving = false;
            while (!moving) {
                if (direction == 0 && canMove(x, y - speed)) {
                    y -= speed;
                    moving = true;
                } else if (direction == 1 && canMove(x + speed, y)) {
                    x += speed;
                    moving = true;
                } else if (direction == 2 && canMove(x, y + speed)) {
                    y += speed;
                    moving = true;
                } else if (direction == 3 && canMove(x - speed, y)) {
                    x -= speed;
                    moving = true;
                } else direction = new Random().nextInt(4);
            }
        }
    }

    private void getImage() {
        if (animate%6 == 0) {
            if (spriteImage.equals(balloomLeft.image)) spriteImage = balloomRight.image;
            else if (spriteImage.equals(balloomLeft1.image)) spriteImage = balloomRight1.image;
            else if (spriteImage.equals(balloomLeft2.image)) spriteImage = balloomRight2.image;
            else if (spriteImage.equals(balloomRight.image)) spriteImage = balloomLeft.image;
            else if (spriteImage.equals(balloomRight1.image)) spriteImage = balloomLeft1.image;
            else if (spriteImage.equals(balloomRight2.image)) spriteImage = balloomLeft2.image;
            else spriteImage = balloomRight.image;
        }
    }
}
