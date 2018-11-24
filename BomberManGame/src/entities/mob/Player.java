package entities.mob;

import entities.Entity;
import graphics.Screen;
import input.KeyboardEvent;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import static sprites.SpritesImage.*;

public class Player extends Mob {

    public  WritableImage playerUpImage = fixPlayerUpAndDownImage(playerUp.image);
    public  WritableImage playerUpImage1 = fixPlayerUpAndDownImage(playerUp1.image);
    public  WritableImage playerUpImage2 = fixPlayerUpAndDownImage(playerUp2.image);

    public  WritableImage playerDownImage = fixPlayerUpAndDownImage(playerDown.image);
    public  WritableImage playerDownImage1 = fixPlayerUpAndDownImage(playerDown1.image);
    public  WritableImage playerDownImage2 = fixPlayerUpAndDownImage(playerDown2.image);

    public WritableImage playerRightImage = fixPlayerRightImage(playerRight.image);
    public WritableImage playerRightImage1 = fixPlayerRightImage1(playerRight1.image);
    public WritableImage playerRightImage2 = fixPlayerRightImage2(playerRight2.image);

    public KeyboardEvent keyPressed, keyReleased;
    public int speed = 2, speedDelay = 3, _x = 0, _y = 0, xMin = 9, xMax = 16, updateTime = 0, bombRange = 1;
    public static boolean canPassWall = false;
    public boolean isEnteredPortal = false;

    public Player(int x, int y) {
        super(x, y);
        spriteImage = playerRightImage;
        handleKeyPressedEvent();
        handleKeyReleasedEvent();
    }

    @Override
    public void update() {
        if (isAlive) {
            if (board.portal.collide(x, y) && board.getBrick(board.portal.x, board.portal.y) == null) isEnteredPortal = true;
            else isEnteredPortal = false;

            if (canMove(x, y + _y)) {
                y += _y;
            }
            else if (x%16 > 0 && x%16 < 7) x -= speed;
            else if (x%16 > 9) x += speed;

            if (canMove(x + _x, y)) {
                x += _x;
            }
            else if (y%16 > 0 && y%16 < 7) y -= speed;
            else if (y%16 > 9) y += speed;
            _x = 0;
            _y = 0;
            getImage(direction);
        }
        else {
            updateTime++;
            deadAnimation();
        }
        animate();
    }

    @Override
    public void render(Screen screen) {
        screen.renderEntity(x, y, this);
    }

    private void deadAnimation() {
        if (updateTime == 1) spriteImage = playerDead.image;
        else if (updateTime == 10) spriteImage = playerDead1.image;
        else if (updateTime >= 20) spriteImage = playerDead2.image;
    }

    public void handleKeyPressedEvent() {
        keyPressed = new KeyboardEvent() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W) {
                    direction = 0;
                    xMin = 13;
                    xMax = 14;
                }
                if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
                    direction = 1;
                    xMin = 9;
                    xMax = 16;
                }
                if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S) {
                    direction = 2;
                    xMin = 13;
                    xMax = 14;
                }
                if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) {
                    direction = 3;
                    xMin = 15;
                    xMax = 9;
                }
            }
        };
    }

    public void handleKeyReleasedEvent() {
        keyReleased = new KeyboardEvent() {
            @Override
            public void handle(KeyEvent event) {
            if (isAlive) {
                if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W) {
                    if (direction == 0) {
                        direction = -1;
                        spriteImage = playerUpImage;
                    }
                }
                if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
                    if (direction == 1) {
                        direction = -1;
                        spriteImage = playerRightImage;
                    }
                }

                if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S) {
                    if (direction == 2) {
                        direction = -1;
                        spriteImage = playerDownImage;
                    }
                }
                if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) {
                    if (direction == 3) {
                        direction = -1;
                        spriteImage = playerLeft.image;
                    }
                }
            }
            }
        };
    }

    public boolean collide(int _x, int _y) {
        if (_x >= x - xMin && _x < x + xMax && _y >= y - 15 && _y < y + 16) return true;
        return false;
    }

    public boolean canMove(int x, int y) {
        for (int i = 0; i < board.walls.size(); i++) {
            if (board.walls.get(i).collide(x, y)) return false;
        }
        if (!canPassWall) {
            for (int i = 0; i < board.bricks.size(); i++) {
                if (board.bricks.get(i).collide(x, y)) return false;
            }
        }
        for (int i = 0; i < board.bombs.size(); i++) {
            if (board.bombs.get(i).isPut && !board.bombs.get(i).isCollidingPlayer && board.bombs.get(i).collide(x, y)) return false;
        }

        return true;
    }

    private void getImage(int direction) {
        if (animate%speedDelay == 0) {
            switch (direction) {
                case 0:
                    _y -= speed;
                    if (animate % 6 == 0) {
                        if (spriteImage.equals(playerUpImage)) {
                            spriteImage = playerUpImage1;
                        } else if (spriteImage.equals(playerUpImage1)) {
                            spriteImage = playerUpImage2;
                        } else if (spriteImage.equals(playerUpImage2)) {
                            spriteImage = playerUpImage;
                        } else {
                            spriteImage = playerUpImage1;
                        }
                    }
                    break;
                case 1:
                    _x += speed;
                    if (animate % 6 == 0) {
                        if (spriteImage.equals(playerRightImage)) {
                            spriteImage = playerRightImage1;
                        } else if (spriteImage.equals(playerRightImage1)) {
                            spriteImage = playerRightImage2;
                        } else if (spriteImage.equals(playerRightImage2)) {
                            spriteImage = playerRightImage;
                        } else {
                            spriteImage = playerRightImage1;
                        }
                    }
                    break;
                case 2:
                    _y += speed;
                    if (animate % 6 == 0) {
                        if (spriteImage.equals(playerDownImage)) {
                            spriteImage = playerDownImage1;
                        } else if (spriteImage.equals(playerDownImage1)) {
                            spriteImage = playerDownImage2;
                        } else if (spriteImage.equals(playerDownImage2)) {
                            spriteImage = playerDownImage;
                        } else {
                            spriteImage = playerDownImage1;
                        }
                    }
                    break;
                case 3:
                    _x -= speed;
                    if (animate % 6 == 0) {
                        if (spriteImage.equals(playerLeft.image)) {
                            spriteImage = playerLeft1.image;
                        } else if (spriteImage.equals(playerLeft1.image)) {
                            spriteImage = playerLeft2.image;
                        } else if (spriteImage.equals(playerLeft2.image)) {
                            spriteImage = playerLeft.image;
                        } else {
                            spriteImage = playerLeft1.image;
                        }
                    }
                    break;
            }
        }

    }

    public WritableImage fixPlayerUpAndDownImage(WritableImage image) {
        int x = 0, y = 0;
        PixelReader pr = image.getPixelReader();
        WritableImage newImage = new WritableImage(16, 16);
        PixelWriter pw = newImage.getPixelWriter();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (j > 1 && j< 14){
                    pw.setArgb(j, i, pr.getArgb(x, y));
                    x++;
                }
                else pw.setArgb(j, i, -65281);
                if (x == 12) x = 0;
            }
            y++;
        }
        return newImage;
    }

    public WritableImage fixPlayerRightImage(WritableImage image) {
        int x = 0, y = 0;
        PixelReader pr = image.getPixelReader();
        WritableImage newImage = new WritableImage(16, 16);
        PixelWriter pw = newImage.getPixelWriter();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (j > 5){
                    pw.setArgb(j, i, pr.getArgb(x, y));
                    x++;
                }
                else pw.setArgb(j, i, -65281);
                if (x == 10) x = 0;
            }
            y++;
        }
        return newImage;
    }

    public WritableImage fixPlayerRightImage1(WritableImage image) {
        int x = 0, y = 0;
        PixelReader pr = image.getPixelReader();
        WritableImage newImage = new WritableImage(16, 16);
        PixelWriter pw = newImage.getPixelWriter();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (j > 4){
                    pw.setArgb(j, i, pr.getArgb(x, y));
                    x++;
                }
                else pw.setArgb(j, i, -65281);
                if (x == 11) x = 0;
            }
            y++;
        }
        return newImage;
    }

    public WritableImage fixPlayerRightImage2(WritableImage image) {
        int x = 0, y = 0;
        PixelReader pr = image.getPixelReader();
        WritableImage newImage = new WritableImage(16, 16);
        PixelWriter pw = newImage.getPixelWriter();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (j > 3){
                    pw.setArgb(j, i, pr.getArgb(x, y));
                    x++;
                }
                else pw.setArgb(j, i, -65281);
                if (x == 12) x = 0;
            }
            y++;
        }
        return newImage;
    }

    @Override
    public boolean collide(Entity entity) {
        return false;
    }
}
