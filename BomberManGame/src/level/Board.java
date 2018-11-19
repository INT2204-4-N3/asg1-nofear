package level;

import entities.Entity;
import entities.bomb.Bomb;
import entities.mob.Mob;
import entities.mob.Player;
import entities.mob.enemies.Enemy;
import entities.powerup.PowerUp;
import entities.tile.Brick;
import entities.tile.Grass;
import entities.tile.Tile;
import entities.tile.Wall;
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
    public List<Wall> bombsArea = new LinkedList<>();
    public List<PowerUp> powerUps = new LinkedList<>();
    public Player player;
    public KeyboardEvent keyPressed, keyReleased;
    public boolean isRendering = false;

    public Board() {
        keyPressed = new KeyboardEvent() {
            @Override
            public void handle(KeyEvent event) {
                if (player.isAlive) {
                    player.keyPressed.handle(event);
                    if (event.getCode() == KeyCode.SPACE) {
                        for (int i = 0; i < bombs.size(); i++) {
                            if (bombs.get(i).isExploded) {
                                bombs.get(i).x = player.x;
                                bombs.get(i).y = player.y;
                                if (player.x % 16 > 0 && player.x % 16 < 8) {
                                    bombs.get(i).x -= player.x % 16;
                                } else if (player.x % 16 > 8) bombs.get(i).x += 16 - player.x % 16;

                                if (player.y % 16 > 0 && player.y % 16 < 8) {
                                    bombs.get(i).y -= player.y % 16;
                                } else if (player.y % 16 > 8) bombs.get(i).y += 16 - player.y % 16;

                                if (bombs.get(i).x % 16 == 0 && bombs.get(i).y % 16 == 0) {
                                    bombs.get(i).isPut = true;
                                    updateEnemies();
                                    updateBombs();
                                    break;
                                }
                            }
                        }
                    }
                }
                if (event.getCode() == KeyCode.H) {
                    player.isAlive = true;
                    player.updateTime = 0;
                    player.spriteImage = playerDown.image;
                }
            }
        };

        keyReleased = new KeyboardEvent() {
            @Override
            public void handle(KeyEvent event) {
                player.keyReleased.handle(event);
            }
        };
    }

    @Override
    public void update() {
        updateEnemies();
        updateBombs();
        updateBricks();
        updatePowerUps();
        updatePlayers();
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
        renderGrasses();
        renderPowerUps();
        renderBricks();
        renderWalls();
        renderEnemies();
        renderBombs();
        renderPlayers();
    }

    public void updateBombsArea() {
        for (int i = 0; i < bombsArea.size(); i++) {
            bombsArea.get(i).update();
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
        for (int i = 0; i < bricks.size(); i++) {
            if (bricks.get(i).isDestroyed) {
                grasses.add(new Grass(bricks.get(i).x, bricks.get(i).y, grass.image));
                bricks.get(i).update();
            }
            if (bricks.get(i).isRemoved) bricks.remove(i);
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
        player.update();
    }

    public void renderPlayers() {
        player.render(screen);
    }

    private void updateEnemies() {
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).update();
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
        for (int i = 0; i < bricks.size(); i++) {
            if (bricks.get(i).x == x && bricks.get(i).y == y) return true;

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
}
