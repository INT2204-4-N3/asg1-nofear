package entities.mob.enemies;

import graphics.Screen;
import javafx.scene.image.WritableImage;

import java.util.Random;

import static sprites.SpritesImage.*;

public class MouseFace extends Enemy {
    
    boolean isCollidingBomb = false;

    int chooseDirection = 0;
    
    public MouseFace(int x, int y) {
        super(x, y);
        speed = 2;
        speedDelay = 3;
        spriteImage = mouseFaceRight.image;
    }

    @Override
    public void update() {
        if (deadTime <= 60) {
            if (isAlive) {
                getImage();
                for (int i = 0; i < board.bombs.size(); i++) {
                    if (board.bombs.get(i).collide(x, y)) {
                        isCollidingBomb = true;
                        break;
                    }
                    else if (i == board.bombs.size() - 1) isCollidingBomb = false;
                }
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

        if (!isCollidingBomb) {
            for (int i = 0; i < board.bombs.size(); i++) {
                if (board.bombs.get(i).collide(x, y)) return false;
            }
        }
        return true;
    }

    private void moveRandom() {
        if (animate%speedDelay == 0) {
            if (animate%10 == 0) chooseDirection = new Random().nextInt(1);

            if (chooseDirection == 0) {
                if (x < board.player.x) direction = 1;
                if (x > board.player.x) direction = 3;
                if (x == board.player.x) {
                    direction = 2 - new Random().nextInt(1)*2;
                    chooseDirection = 1;
                }
            }
            else {
                if (y > board.player.y) direction = 0;
                if (y < board.player.y) direction = 2;
                if (y == board.player.y) {
                    direction = 3 - new Random().nextInt(1)*2;
                    chooseDirection = 1;
                }
            }
            while (true) {
                speed = 2 - new Random().nextInt(1);
                if (direction == 0 && canMove(x, y - speed)) {
                    y -= speed;
                    chooseDirection = 1;
                    break;
                }
                if (direction == 1 && canMove(x + speed, y)) {
                    x += speed;
                    chooseDirection = 0;
                    break;
                }
                if (direction == 2 && canMove(x, y + speed)) {
                    y += speed;
                    chooseDirection = 1;
                    break;
                }
                if (direction == 3 && canMove(x - speed, y)) {
                    x -= speed;
                    chooseDirection = 0;
                    break;
                }
                direction = new Random().nextInt(4);
            }
        }
    }

    private void deadAnimation() {
        if (deadTime%3 == 0) spriteImage = mouseFaceDead.image;
        else spriteImage = new WritableImage(16, 16);
    }

    private void getImage() {
        if (animate%6 == 0) {
            if (spriteImage.equals(mouseFaceLeft.image)) spriteImage = mouseFaceRight.image;
            else if (spriteImage.equals(mouseFaceRight.image)) spriteImage = mouseFaceLeft1.image;
            else if (spriteImage.equals(mouseFaceLeft1.image)) spriteImage = mouseFaceRight1.image;
            else if (spriteImage.equals(mouseFaceRight1.image)) spriteImage = mouseFaceLeft2.image;
            else if (spriteImage.equals(mouseFaceLeft2.image)) spriteImage = mouseFaceRight2.image;
            else if (spriteImage.equals(mouseFaceRight2.image)) spriteImage = mouseFaceLeft.image;
            else spriteImage = mouseFaceLeft.image;
        }
    }
}
