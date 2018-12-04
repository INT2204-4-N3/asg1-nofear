package entities.mob.enemies;

import graphics.Screen;
import javafx.scene.image.WritableImage;

import java.util.Random;

import static sprites.SpritesImage.*;

public class BlueLiquid extends Enemy {

    boolean isCollidingBomb = false;

    int chooseDirection = 0, updateTime = 0;

    public BlueLiquid(int x, int y) {
        super(x, y);
        speed = 1;
        speedDelay = 2;
        spriteImage = blueLiquidLeft.image;
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
//                for (int i = 0; i < board.bombs.size(); i++) {
//                    if (board.bombs.get(i).collide(x, y)) {
//                        isCollidingBomb = true;
//                        break;
//                    }
//                    else if (i == board.bombs.size() - 1) isCollidingBomb = false;
//                }
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
//        if (!isCollidingBomb) {
//            for (int i = 0; i < board.bombs.size(); i++) {
//                if (board.bombs.get(i).collide(x, y)) return false;
//            }
//        }
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
        if (deadTime%3 == 0) spriteImage = blueLiquidDead.image;
        else spriteImage = new WritableImage(16, 16);
    }

    private void getImage() {
        if (animate%6 == 0) {
            if (spriteImage.equals(blueLiquidLeft.image)) spriteImage = blueLiquidRight.image;
            else if (spriteImage.equals(blueLiquidRight.image)) spriteImage = blueLiquidLeft1.image;
            else if (spriteImage.equals(blueLiquidLeft1.image)) spriteImage = blueLiquidRight1.image;
            else if (spriteImage.equals(blueLiquidRight1.image)) spriteImage = blueLiquidLeft2.image;
            else if (spriteImage.equals(blueLiquidLeft2.image)) spriteImage = blueLiquidRight2.image;
            else if (spriteImage.equals(blueLiquidRight2.image)) spriteImage = blueLiquidLeft.image;
            else spriteImage = blueLiquidLeft.image;
        }
    }
}
