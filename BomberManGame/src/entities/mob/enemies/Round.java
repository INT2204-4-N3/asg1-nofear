package entities.mob.enemies;

import graphics.Screen;
import javafx.scene.image.WritableImage;

import java.util.Random;

import static sprites.SpritesImage.*;

public class Round extends Enemy{

    boolean isCollidingBomb = false;

    int chooseDirection = 0, updateTime = 0;

    public Round(int x, int y) {
        super(x, y);
        speed = 2;
        speedDelay = 4;
        spriteImage = roundLeft.image;
    }

    @Override
    public void update() {
        if (deadTime <= 60 && board.getBrick(x, y) == null) {
            if (updateTime == 0) {
                isAlive = true;
                updateTime = 1;
            }
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
        else if (deadTime > 60) isRemoved = true;
    }

    @Override
    public void render(Screen screen) {
        if (deadTime <= 60 && board.getBrick(x, y) == null) screen.renderEntity(x, y, this);
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

            if (x == board.player.x && (canMove(x, y - speed) || canMove(x, y + speed))) {
                if (y > board.player.y) direction = 0;
                else direction = 2;
            }
            else if (y == board.player.y && (canMove(x - speed, y) || canMove(x + speed, y))) {
                if (x < board.player.x) direction = 1;
                else direction = 3;
            }
            else {
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

            while (true) {
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
        if (deadTime%3 == 0) spriteImage = roundDead.image;
        else spriteImage = new WritableImage(16, 16);
    }

    private void getImage() {
        if (animate%6 == 0) {
            if (spriteImage.equals(roundLeft.image)) spriteImage = roundRight.image;
            else if (spriteImage.equals(roundRight.image)) spriteImage = roundLeft1.image;
            else if (spriteImage.equals(roundLeft1.image)) spriteImage = roundRight1.image;
            else if (spriteImage.equals(roundRight1.image)) spriteImage = roundLeft2.image;
            else if (spriteImage.equals(roundLeft2.image)) spriteImage = roundRight2.image;
            else if (spriteImage.equals(roundRight2.image)) spriteImage = roundLeft.image;
            else spriteImage = roundLeft.image;
        }
    }
}
