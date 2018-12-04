package entities.mob.enemies;

import entities.Entity;
import graphics.Screen;
import javafx.scene.image.WritableImage;

import java.util.Random;

import static sprites.SpritesImage.*;

public class Ghost extends Enemy {

    boolean isCollidingBomb = false;
    int collidingBomb = 0;

    int chooseDirection = 0;
    
    public Ghost(int x, int y) {
        super(x, y);
        speed = 2;
        speedDelay = 3;
        spriteImage = ghostLeft.image;
    }

    @Override
    public void update() {
        if (deadTime <= 60) {
            if (isAlive) {
                getImage();
                moveRandom();
                if (board.player.collide(x, y)) board.player.isAlive = false;
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
                } else if (direction == 1 && canMove(x + speed, y)) {
                    x += speed;
                    break;
                } else if (direction == 2 && canMove(x, y + speed)) {
                    y += speed;
                    break;
                } else if (direction == 3 && canMove(x - speed, y)) {
                    x -= speed;
                    break;
                } else direction = new Random().nextInt(4);
            }
        }
    }

    private void deadAnimation() {
        if (deadTime%3 == 0) spriteImage = ghostDead.image;
        else spriteImage = new WritableImage(16, 16);
    }

    private void getImage() {
        if (animate%6 == 0) {
            if (direction == 0 || direction == 3) {
                if (spriteImage.equals(ghostLeft.image)) spriteImage = ghostLeft1.image;
                else if (spriteImage.equals(ghostLeft1.image)) spriteImage = ghostLeft2.image;
                else if (spriteImage.equals(ghostLeft2.image)) spriteImage = ghostLeft.image;
                else spriteImage = ghostLeft.image;
            }
            else {
                if (spriteImage.equals(ghostRight.image)) spriteImage = ghostRight1.image;
                else if (spriteImage.equals(ghostRight1.image)) spriteImage = ghostRight2.image;
                else if (spriteImage.equals(ghostRight2.image)) spriteImage = ghostRight.image;
                else spriteImage = ghostRight.image;
            }
        }
    }
}
