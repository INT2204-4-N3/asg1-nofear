package entities.mob.enemies;

import entities.Entity;
import graphics.Screen;
import javafx.scene.image.WritableImage;
import level.Board;

import java.util.Random;

import static sprites.SpritesImage.*;

public class Balloom extends Enemy {

    boolean isCollidingBomb = false;
    int collidingBomb = 0;
    public Balloom(int x, int y) {
        super(x, y);
        speed = 1;
        speedDelay = 3;
        direction = new Random().nextInt(4);
        spriteImage = balloomRight.image;
    }

    @Override
    public boolean collide(Entity entity) {
        return false;
    }

    @Override
    public void update() {
        if (deadTime <= 60) {
            if (isAlive) {
                getImage();
//                collidingBomb = 0;
                if (board.player.collide(x, y)) board.player.isAlive = false;
                for (int i = 0; i < board.bombs.size(); i++) {
                    if (board.bombs.get(i).collide(x, y)) {
                        isCollidingBomb = true;
                        break;
                    }
                    else if (i == board.bombs.size() - 1) isCollidingBomb = false;
                }
//                if (collidingBomb == 1) isCollidingBomb = true;
//                else isCollidingBomb = false;
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
        if (deadTime%3 == 0) spriteImage = balloomDead.image;
        else spriteImage = null;
    }

    public boolean collide(int _x, int _y) {
        if (_x >= x - 15 && _x < x + 16 && _y >= y - 15 && _y < y + 16) return true;
        return false;
    }

    public boolean canMove(int x, int y) {
        for (int i = 0; i < board.walls.size(); i++) {
            if (board.walls.get(i).collide(x, y)) return false;
        }
        for (int i = 0; i < board.bricks.size(); i++) {
            if (board.bricks.get(i).collide(x, y)) return false;
        }
        if (!isCollidingBomb) {
            for (int i = 0; i < board.bombs.size(); i++) {
                if (board.bombs.get(i).collide(x, y)) return false;
            }
        }
        return true;
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
            else if (spriteImage.equals(balloomRight.image)) spriteImage = balloomLeft1.image;
            else if (spriteImage.equals(balloomLeft1.image)) spriteImage = balloomRight1.image;
            else if (spriteImage.equals(balloomRight1.image)) spriteImage = balloomLeft2.image;
            else if (spriteImage.equals(balloomLeft2.image)) spriteImage = balloomRight2.image;
            else if (spriteImage.equals(balloomRight2.image)) spriteImage = balloomLeft.image;
            else spriteImage = balloomLeft.image;
        }
    }
}
