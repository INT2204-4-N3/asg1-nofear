package input;

import entities.mob.Player;
import entities.mob.enemies.*;
import entities.powerup.*;
import entities.tile.Brick;
import entities.tile.Grass;
import entities.tile.Portal;
import entities.tile.Wall;
import level.Board;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static sprites.SpritesImage.*;
import static sprites.SpritesImage.brick;
import static sprites.SpritesImage.grass;

public class ReadFile {

    public static int level , mapCol, mapRow;


    public static void loadMap(String path, Board board) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String data;
            int line = 1;
            int x = 0, y = 0;
            while ((data = br.readLine()) != null) {
                if (line == 1) {
                    char[] number = data.toCharArray();
                    level = number[0] - 48;
                    mapRow = (number[2] - 48)*10 + number[3] - 48;
                    mapCol = (number[5] - 48)*10 + number[6] - 48;
                }
                else {
                    char[] mapData = data.toCharArray();
                    for (int i = 0; i < mapData.length; i++) {
                        if (mapData[i] == '#') {
                            board.walls.add(new Wall(x, y, wall.image));
                        }
                        else if (mapData[i] == '*') {
                            board.bricks.add(new Brick(x, y, brick.image));
                        }
                        else if (mapData[i] == 'x') {
                            board.portal = new Portal(x, y, portal.image);
                            board.bricks.add(new Brick(x, y, brick.image));
                        }
                        else if (mapData[i] == 'p') {
                            board.grasses.add(new Grass(x, y, grass.image));
                            board.player = new Player(x, y);
                        }
                        else if (mapData[i] == '1') {
                            board.grasses.add(new Grass(x, y, grass.image));
                            board.enemies.add(new Balloom(x, y));

                        }
                        else if (mapData[i] == '2') {
                            board.grasses.add(new Grass(x, y, grass.image));
                            board.enemies.add(new Oneal(x, y));
                        }
                        else if (mapData[i] == '3') {
                            board.grasses.add(new Grass(x, y, grass.image));
                            board.enemies.add(new Doll(x, y));
                        }
                        else if (mapData[i] == '4') {
                            board.grasses.add(new Grass(x, y, grass.image));
                            board.enemies.add(new Ghost(x, y));
                        }
                        else if (mapData[i] == '5') {
                            board.grasses.add(new Grass(x, y, grass.image));
                            board.enemies.add(new RedCoin(x, y));
                        }
                        else if (mapData[i] == '6') {
                            board.grasses.add(new Grass(x, y, grass.image));
                            board.enemies.add(new YellowCoin(x, y));
                        }
                        else if (mapData[i] == '7') {
                            board.grasses.add(new Grass(x, y, grass.image));
                            board.enemies.add(new BlueLiquid(x, y));
                            board.bricks.add(new Brick(x, y, brick.image));
                        }
                        else if (mapData[i] == '8') {
                            board.grasses.add(new Grass(x, y, grass.image));
                            board.enemies.add(new MouseFace(x, y));
                        }
                        else if (mapData[i] == '9') {
                            board.grasses.add(new Grass(x, y, grass.image));
                            board.enemies.add(new Round(x, y));
                            board.bricks.add(new Brick(x, y, brick.image));
                        }
                        else if (mapData[i] == 's') {
                            board.grasses.add(new Grass(x, y, grass.image));
                            board.powerUps.add(new SpeedItem(x, y));
                            board.bricks.add(new Brick(x, y, brick.image));
                        }
                        else if (mapData[i] == 'b') {
                            board.grasses.add(new Grass(x, y, grass.image));
                            board.powerUps.add(new BombItem(x, y));
                            board.bricks.add(new Brick(x, y, brick.image));
                        }
                        else if (mapData[i] == 'f') {
                            board.grasses.add(new Grass(x, y, grass.image));
                            board.powerUps.add(new FlameItem(x, y));
                            board.bricks.add(new Brick(x, y, brick.image));
                        }
                        else if (mapData[i] == 'w') {
                            board.grasses.add(new Grass(x, y, grass.image));
                            board.powerUps.add(new WallPassItem(x, y));
                            board.bricks.add(new Brick(x, y, brick.image));
                        }
                        else if (mapData[i] == 'F') {
                            board.grasses.add(new Grass(x, y, grass.image));
                            board.powerUps.add(new FlamePassItem(x, y));
                            board.bricks.add(new Brick(x, y, brick.image));
                        }
                        else if (mapData[i] == 'B') {
                            board.grasses.add(new Grass(x, y, grass.image));
                            board.powerUps.add(new BomPassItem(x, y));
                            board.bricks.add(new Brick(x, y, brick.image));
                        }
                        else if (mapData[i] == 'L') {
                            board.grasses.add(new Grass(x, y, grass.image));
                            board.powerUps.add(new LiveItem(x, y));
                            board.bricks.add(new Brick(x, y, brick.image));
                        }
                        else {
                            board.grasses.add(new Grass(x, y, grass.image));
                        }
                        x+=16;
                    }
                    x = 0;
                    y+=16;
                }
                if (line == 14) break;
                line ++;

            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
