package level;

import entities.Entity;
import entities.bomb.Bomb;
import entities.bomb.Explosion;
import entities.mob.Mob;
import entities.mob.Player;
import entities.mob.enemies.Enemy;
import entities.powerup.PowerUp;
import entities.tile.*;
import graphics.IRender;
import graphics.Screen;
import input.KeyboardEvent;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static sprites.SpritesImage.*;

public class Board implements IRender {

    public Screen screen;
    public List<Entity> entities = new ArrayList<>();
    public List<Tile> tiles = new ArrayList<>();
    public List<Enemy> enemies = new ArrayList<>();
    public List<Bomb> bombs = new LinkedList<>();
    public List<Grass> grasses = new ArrayList<>();
    public List<Brick> bricks = new LinkedList<>();
    public List<Wall> walls = new ArrayList<>();
    public List<PowerUp> powerUps = new LinkedList<>();
    public Portal portal;
    public Player player;
    public KeyboardEvent keyPressed, keyReleased;
    public boolean bombPass = false;
    public boolean isPause = false;
    int pressedTime = 0;

    public Board() {
        keyPressed = new KeyboardEvent() {
            @Override
            public void handle(KeyEvent event) {
                if (player.isAlive && !isPause) {

                    if (event.getCode() == KeyCode.SPACE) {
                        int xBomb, yBomb;
                        for (int i = 0; i < bombs.size(); i++) {
                            if (bombs.get(i).isExploded) {
                                xBomb = player.x;
                                yBomb = player.y;
                                if (player.x % 16 > 0 && player.x % 16 < 8) {
                                    xBomb -= player.x % 16;
                                } else if (player.x % 16 > 8) xBomb += 16 - player.x % 16;

                                if (player.y % 16 > 0 && player.y % 16 < 8) {
                                    yBomb -= player.y % 16;
                                } else if (player.y % 16 > 8) yBomb += 16 - player.y % 16;

                                if (xBomb % 16 == 0 && yBomb % 16 == 0) {
                                    if ((!isWall(xBomb, yBomb) && !isBrick(xBomb, yBomb)) || bombPass) {
                                        bombs.get(i).x = xBomb;
                                        bombs.get(i).y = yBomb;
                                        bombs.get(i).isPut = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    player.keyPressed.handle(event);
                    // Go S-U de de tang speed
                    if (event.getCode() == KeyCode.S) {
                        pressedTime = 1;
                    }
                    if (pressedTime == 1) {
                        if (event.getCode() == KeyCode.U) {
                            pressedTime = 2;
                        }
                    }
                    if (pressedTime == 2) {
                        player.speedDelay = 2;
                        pressedTime = 0;
                    }
                    // Go F-U de tang flame
                    if (event.getCode() == KeyCode.F) {
                        pressedTime = 3;
                    }
                    if (pressedTime == 3) {
                        if (event.getCode() == KeyCode.U) {
                            pressedTime = 4;
                        }
                    }
                    if (pressedTime == 4) {
                        player.bombRange ++;
                        pressedTime = 0;
                    }
                }
                if (event.getCode() == KeyCode.H) {
                    player.isAlive = true;
                    player.updateTime = 0;
                    player.spriteImage = playerDown.image;
                }
                if (event.getCode() == KeyCode.P) {
                    if (isPause) isPause = false;
                    else isPause = true;
                }
            }
        };

        keyReleased = new KeyboardEvent() {
            @Override
            public void handle(KeyEvent event) {
                if (!isPause) player.keyReleased.handle(event);
            }
        };
    }

    @Override
    public void update() {
        if (!isPause) {
            updateEnemies();
            updateBombs();
            updateBricks();
            updatePowerUps();
            updatePlayers();
        }
    }

    @Override
    public void render(Screen screen) {
        renderGrasses();
        renderBricks();
        renderBombs();
//        renderPlayers();
        screen.screenImage = this.screen.screenImage;
    }

    public void render() {
        if (!isPause) {
            renderGrasses();
            renderPortal();
            renderPowerUps();
            renderBricks();
            renderWalls();
            renderEnemies();
            renderBombs();
            renderPlayers();
        }
    }

    private void renderPortal() {
        portal.render(screen);
    }

    public void removeEntities() {
        Iterator iterator = bricks.iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
        iterator = walls.iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
        iterator = grasses.iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
        iterator = powerUps.iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
    }

    private void renderPowerUps() {
        for (int i = 0; i < powerUps.size(); i++) {
            powerUps.get(i).render(screen);
        }
    }

    private void updatePowerUps() {
        for (int i = 0; i < powerUps.size(); i++) {
            if (!powerUps.get(i).isRemoved)powerUps.get(i).update();
        }
    }

    public void renderWalls() {
        for (int i = 0; i < walls.size(); i++) {
            walls.get(i).render(screen);
        }
    }
    public void renderBricks() {
        for (int i = 0; i < bricks.size(); i++) {
            if (bricks.get(i).isDestroyed || bricks.get(i).updateTime == 0) bricks.get(i).render(screen);
        }
    }

    public void updateBricks() {
        Iterator iterator = bricks.iterator();
        Brick brick;
        while (iterator.hasNext()) {
            brick = (Brick) iterator.next();
            if (brick.isDestroyed) {
                grasses.add(new Grass(brick.x, brick.y, grass.image));
                brick.update();
            }
            if (brick.isRemoved) iterator.remove();
        }
    }

    public void renderGrasses() {
        for (int i = 0; i < grasses.size(); i++) {
            grasses.get(i).render(screen);
        }
    }

    public void reRenderGrass() {
        for (int i = 0; i < grasses.size(); i++) {
            if (grasses.get(i).y >= player.y - 16 && grasses.get(i).y <= player.y + 16 && grasses.get(i).x == player.x
             || grasses.get(i).x >= player.x - 16 && grasses.get(i).x <= player.x + 16 && grasses.get(i).y == player.y) {

                grasses.get(i).render(screen);
            }
        }
    }

    private void updateBombs() {
        for (int i = 0; i < bombs.size(); i++) {
            bombs.get(i).update();
        }

//        Iterator iterator = bombs.iterator();
//        while (iterator.hasNext()) {
//            Bomb bomb = (Bomb) iterator.next();
//            if (bomb.isRemoved) {
//                iterator.remove();
//            }
//            else bomb.update();
//        }
    }

    public void renderBombs() {
        for (int i = 0; i < bombs.size(); i++) {
            if (bombs.get(i).isPut) {
                bombs.get(i).render(screen);
            }
        }
    }

    public void updatePlayers() {
        if (!player.isEnteredPortal || enemies.size() != 0) player.update();
    }

    public void renderPlayers() {
        player.render(screen);
    }

    private void updateEnemies() {
        Iterator iterator = enemies.iterator();
        Enemy enemy;
        while (iterator.hasNext()) {
            enemy = (Enemy) iterator.next();
            if (!enemy.isRemoved) enemy.update();
            else iterator.remove();
        }
    }

    private void renderEnemies() {
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).render(screen);
        }
    }

    public void renderTiles() {
        for (int i = 0; i < tiles.size(); i++) {
            tiles.get(i).render(screen);
        }
    }

    public Entity getEntity(int x, int y) {
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).x == x && tiles.get(i).y == y) return tiles.get(i);
        }
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).x == x && enemies.get(i).y == y) return enemies.get(i);
        }
        for (int i = 0; i < bombs.size(); i++) {
            if (bombs.get(i).x == x && bombs.get(i).y == y) return bombs.get(i);
        }
        for (int i = 0; i < bricks.size(); i++) {
            if (bricks.get(i).x == x && bricks.get(i).y == y) return bricks.get(i);
        }
        for (int i = 0; i < walls.size(); i++) {
            if (walls.get(i).x == x && walls.get(i).y == y) return walls.get(i);
        }
        return null;
    }

    public boolean isGrass(int x, int y) {
        for (int i = 0; i < grasses.size(); i++) {
            if (grasses.get(i).x == x && grasses.get(i).y == y) return true;
        }
        return false;
    }

    public boolean isBarrier(int x, int y) {
        for (int i = 0; i < walls.size(); i++) {
            if (walls.get(i).x == x && walls.get(i).y == y) return true;

        }
        if (!Explosion.flamePass) {
            for (int i = 0; i < bricks.size(); i++) {
                if (bricks.get(i).x == x && bricks.get(i).y == y) return true;

            }
        }
        return false;
    }

    public boolean isWall(int x, int y) {
        for (int i = 0; i < walls.size(); i++) {
            if (walls.get(i).x == x && walls.get(i).y == y) return true;

        }
        return false;
    }

    public boolean isBrick(int x, int y) {
        for (int i = 0; i < bricks.size(); i++) {
            if (bricks.get(i).x == x && bricks.get(i).y == y) return true;

        }
        return false;
    }

    public boolean isPowerUp(int x, int y) {
        for (int i = 0; i < powerUps.size(); i++) {
            if (powerUps.get(i).x == x && powerUps.get(i).y == y) return true;
        }
        return false;
    }

    public Entity getBarrier(int x, int y) {
        for (int i = 0; i < walls.size(); i++) {
            if (walls.get(i).x == x && walls.get(i).y == y) return walls.get(i);

        }
        for (int i = 0; i < bricks.size(); i++) {
            if (bricks.get(i).x == x && bricks.get(i).y == y) return bricks.get(i);

        }
        return null;
    }

    public Brick getBrick(int x, int y) {
        for (int i = 0; i < bricks.size(); i++) {
            if (bricks.get(i).x == x && bricks.get(i).y == y) return bricks.get(i);

        }
        return null;
    }
}
