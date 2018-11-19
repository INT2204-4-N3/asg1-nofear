package entities.bomb;

import entities.Entity;
import entities.tile.Brick;
import entities.tile.Wall;
import graphics.Screen;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import level.Board;

import static sprites.SpritesImage.*;

public class Explosion extends Entity {

    public int bombRange = 1;
    int updateTime = 0;
    public Board board;

    public Explosion() {

    }

    @Override
    public boolean collide(Entity entity) {
        return false;
    }

    @Override
    public void update() {
        updateTime++;
        if (updateTime == 1) createBombExplodedImage(0);
        else if (updateTime == 6) createBombExplodedImage(1);
        else if (updateTime == 11) createBombExplodedImage(2);
        else if (updateTime == 19) updateTime = 0;
    }

    @Override
    public void render(Screen screen) {
        screen.renderEntity(x, y, this);
    }

    public void collide() {
        int _x = x + bombRange*16;
        for (int i = y + bombRange*16; i >= y; i--) {
            if (board.isBarrier(_x, i)) {
                if (board.getBarrier(_x, i) instanceof Wall) break;
                else if (board.getBarrier(_x, i) instanceof Brick) {
                    ((Brick) board.getBarrier(_x, i)).isDestroyed = true;
                    break;
                }
            }
            else {
                if (board.player.collide(_x, i)) board.player.isAlive = false;
                for (int j = 0; j < board.enemies.size(); j++) {
                    if (board.enemies.get(j).collide(_x, i)) board.enemies.get(j).isAlive = false;
                }
            }
        }
        for (int i = y + bombRange*16; i <= y + bombRange*32; i++) {
            if (board.isBarrier(_x, i)) {
                if (board.getBarrier(_x, i) instanceof Wall) break;
                else if (board.getBarrier(_x, i) instanceof Brick) {
                    ((Brick) board.getBarrier(_x, i)).isDestroyed = true;
                    break;
                }

            }
            else {
                if (board.player.collide(_x, i)) board.player.isAlive = false;
                for (int j = 0; j < board.enemies.size(); j++) {
                    if (board.enemies.get(j).collide(_x, i)) board.enemies.get(j).isAlive = false;
                }
            }
        }

        int _y = y + bombRange*16;
        for (int i = x + bombRange*16; i >= x ; i--) {
            if (board.isBarrier(i, _y)) {
                if (board.getBarrier(i, _y) instanceof Wall) break;
                else if (board.getBarrier(i, _y) instanceof Brick) {
                    ((Brick) board.getBarrier(i, _y)).isDestroyed = true;
                    break;
                }

            }
            else {
                if (board.player.collide(i, _y)) board.player.isAlive = false;
                for (int j = 0; j < board.enemies.size(); j++) {
                    if (board.enemies.get(j).collide(i, _y)) board.enemies.get(j).isAlive = false;
                }
            }
        }
        for (int i = x + bombRange*16 + 16; i <= x + bombRange*32; i++) {
            if (board.isBarrier(i, _y)) {
                if (board.getBarrier(i, _y) instanceof Wall) break;
                else if (board.getBarrier(i, _y) instanceof Brick) {
                    ((Brick) board.getBarrier(i, _y)).isDestroyed = true;
                    break;
                }

            }
            else {
                if (board.player.collide(i, _y)) board.player.isAlive = false;
                for (int j = 0; j < board.enemies.size(); j++) {
                    if (board.enemies.get(j).collide(i, _y)) board.enemies.get(j).isAlive = false;
                }
            }
        }
    }

    public void getImage() {

    }

    public void createBombExplodedImage(int position) {
        int size = 16 + bombRange*32;
        int explosionSize = bombRange*16;
        WritableImage bombExplodedImage = new WritableImage(size, size);
        PixelWriter pw = bombExplodedImage.getPixelWriter();
        int x = 0, y = 0;

        int startY = 0, pass = 0;
        for (int i = bombRange; i > 0 ; i--) {
            if (board.isBarrier(this.x + explosionSize, this.y + (bombRange - i)*16)) {
                    startY = (bombRange - i + 1)*16;
                    pass ++;
            }
        }
        if (pass == 0) startY = 0;

        if (startY != explosionSize) {
            if (position == 0) pw.setPixels(explosionSize, startY, 16, 16, explosionAbove.image.getPixelReader(), 0, 0);
            else if (position == 1) pw.setPixels(explosionSize, startY, 16, 16, explosionAbove1.image.getPixelReader(), 0, 0);
            else pw.setPixels(explosionSize, startY, 16, 16, explosionAbove2.image.getPixelReader(), 0, 0);
        }

        for (int i = startY + 16; i < explosionSize; i++) {
            for (int j = explosionSize; j < (bombRange + 1)*16; j++) {
                
                if (position == 0) pw.setArgb(j, i, explosionVertical.image.getPixelReader().getArgb(x, y));
                else if (position == 1) pw.setArgb(j, i, explosionVertical1.image.getPixelReader().getArgb(x, y));
                else pw.setArgb(j, i, explosionVertical2.image.getPixelReader().getArgb(x, y));
                x++;
                if (x == 16) x = 0;
            }
            y++;
            if (y == 16) y = 0;
        }

        int endY = 0;
        pass = 0;
        for (int i = 1; i <= bombRange; i++) {
            if (board.isBarrier(this.x + explosionSize, this.y + size - i*16)) {
                endY = size - (i)*16;
                pass ++;
            }
        }
        if (pass == 0) endY = size;
        for (int i = explosionSize + 16; i < endY - 16; i++) {
            for (int j = explosionSize; j < explosionSize + 16; j++) {
                
                if (position == 0) pw.setArgb(j, i, explosionVertical.image.getPixelReader().getArgb(x, y));
                else if (position == 1) pw.setArgb(j, i, explosionVertical1.image.getPixelReader().getArgb(x, y));
                else pw.setArgb(j, i, explosionVertical2.image.getPixelReader().getArgb(x, y));
                x++;
                if (x == 16) x = 0;
            }
            y++;
            if (y == 16) y = 0;
        }
        if (endY - 16 != explosionSize) {
            if (position == 0) pw.setPixels(explosionSize, endY - 16, 16, 16, explosionBelow.image.getPixelReader(), 0, 0);
            else if (position == 1) pw.setPixels(explosionSize, endY - 16, 16, 16, explosionBelow1.image.getPixelReader(), 0, 0);
            else pw.setPixels(explosionSize, endY - 16, 16, 16, explosionBelow2.image.getPixelReader(), 0, 0);
        }


        for (int i = explosionSize; i < explosionSize + 16; i++) {
            for (int j = explosionSize; j < explosionSize + 16; j++) {
                
                if (position == 0) pw.setArgb(j, i, bombExploded.image.getPixelReader().getArgb(x, y));
                else if (position == 1) pw.setArgb(j, i, bombExploded1.image.getPixelReader().getArgb(x, y));
                else pw.setArgb(j, i, bombExploded2.image.getPixelReader().getArgb(x, y));
                x++;
                if (x == 16) x = 0;
            }
            y++;
            if (y == 16) y = 0;
        }

        int startX = 0;
        pass = 0;

        for (int i = bombRange; i > 0 ; i--) {
            if (board.isBarrier(this.x + (bombRange - i)*16, this.y + explosionSize)) {
                startX = (bombRange - i + 1)*16;
                pass ++;
            }
        }
        if (pass == 0) startX = 0;
        if (startX != explosionSize) {
            if (position == 0) pw.setPixels(startX, explosionSize, 16, 16, explosionLeft.image.getPixelReader(), 0, 0);
            else if (position == 1) pw.setPixels(startX, explosionSize, 16, 16, explosionLeft1.image.getPixelReader(), 0, 0);
            else pw.setPixels(startX, explosionSize, 16, 16, explosionLeft2.image.getPixelReader(), 0, 0);
        }

        for (int i = explosionSize; i < explosionSize + 16; i++) {
            for (int j = startX + 16; j < explosionSize; j++) {
                
                if (position == 0) pw.setArgb(j, i, explosionHorizontal.image.getPixelReader().getArgb(x, y));
                else if (position == 1) pw.setArgb(j, i, explosionHorizontal1.image.getPixelReader().getArgb(x, y));
                else pw.setArgb(j, i, explosionHorizontal2.image.getPixelReader().getArgb(x, y));
                x++;
                if (x == 16) x = 0;
            }
            y++;
            if (y == 16) y = 0;
        }

        int endX = 0;
        pass = 0;
        for (int i = 1; i <= bombRange; i++) {
            if (board.isBarrier(this.x + size - i*16, this.y + explosionSize)) {
                endX = size - (i)*16;
                pass ++;
            }
        }
        if (pass == 0) endX = size;
        if (endX != explosionSize + 16) {
            if (position == 0) pw.setPixels(endX - 16, explosionSize, 16, 16, explosionRight.image.getPixelReader(), 0, 0);
            else if (position == 1) pw.setPixels(endX - 16, explosionSize, 16, 16, explosionRight1.image.getPixelReader(), 0, 0);
            else pw.setPixels(endX - 16, explosionSize, 16, 16, explosionRight2.image.getPixelReader(), 0, 0);
        }

        for (int i = explosionSize; i < explosionSize + 16; i++) {
            for (int j = explosionSize + 16; j < endX - 16; j++) {
                
                if (position == 0) pw.setArgb(j, i, explosionHorizontal.image.getPixelReader().getArgb(x, y));
                else if (position == 1) pw.setArgb(j, i, explosionHorizontal1.image.getPixelReader().getArgb(x, y));
                else pw.setArgb(j, i, explosionHorizontal2.image.getPixelReader().getArgb(x, y));
                x++;
                if (x == 16) x = 0;
            }
            y++;
            if (y == 16) y = 0;
        }

        spriteImage = bombExplodedImage;
    }
}
