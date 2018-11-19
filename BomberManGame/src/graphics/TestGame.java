package graphics;

import entities.bomb.Bomb;
import entities.mob.Player;
import entities.mob.enemies.Balloom;
import entities.powerup.BombItem;
import entities.powerup.FlameItem;
import entities.powerup.SpeedItem;
import entities.tile.Brick;
import entities.tile.Grass;
import entities.tile.Tile;
import entities.tile.Wall;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import level.Board;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

import static sprites.SpritesImage.*;

public class TestGame extends Application {

    int level, mapCol, mapRow;
    int x = 0, y = 0, i = 0;
    ImageView imageView;
    ImageView secondImageView = new ImageView();
    Image gameScreen;
    Board board = new Board();
    int xOffset = 325;
    private final long[] frameTimes = new long[100];
    private int frameTimeIndex = 0 ;
    private boolean arrayFilled = false ;
    Label label = new Label("FPS: ?");
    Screen screen;

    public void renderMap(String path) {
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
//                            board.grasses.add(new Grass(x, y, grass.image));
//                            board.enemies.add(new Balloom(x, y, board));
                        }
                        else if (mapData[i] == 'p') {
                            board.grasses.add(new Grass(x, y, grass.image));
                            board.player = new Player(x, y, board);
                        }
                        else if (mapData[i] == '1' || mapData[i] == '2') {
                            board.grasses.add(new Grass(x, y, grass.image));
                            board.enemies.add(new Balloom(x, y, board));

                        }
                        else if (mapData[i] == 's') {
                            board.grasses.add(new Grass(x, y, grass.image));
                            board.powerUps.add(new SpeedItem(x, y, board));
                            board.bricks.add(new Brick(x, y, brick.image));
                        }
                        else if (mapData[i] == 'b') {
                            board.grasses.add(new Grass(x, y, grass.image));
                            board.powerUps.add(new BombItem(x, y, board));
                            board.bricks.add(new Brick(x, y, brick.image));
                        }
                        else if (mapData[i] == 'f') {
                            board.grasses.add(new Grass(x, y, grass.image));
                            board.powerUps.add(new FlameItem(x, y, board));
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

    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane anchorPane = new AnchorPane();
        renderMap("C:\\Users\\cbg2\\BomberManGame\\src\\res\\levels\\Level1.txt");

        board.screen = new Screen(16*mapCol, 16*mapRow);
        screen = new Screen(16*mapCol, 16*mapRow);
        board.bombs.add(new Bomb(0, 0, board));
//        board.bombs.add(new Bomb(0, 0, board));
//        board.bombs.add(new Bomb(0, 0, board));
//        board.bombs.add(new Bomb(0, 0, board));
//        board.bombs.add(new Bomb(0, 0, board));
//        board.bombs.add(new Bomb(0, 0, board));
        board.render();
//        board.renderTiles();
        board.renderGrasses();
        board.renderWalls();
        board.renderBricks();
        board.render(screen);

        label.setLayoutX(10);
        label.setLayoutY(10);

        Canvas canvas = new Canvas(624, 624);
        canvas.setLayoutY(30);
        anchorPane.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        final long startNanoTime = System.nanoTime();


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    board.render();
                    gameScreen = new Image(createInput(board.screen.screenImage), 1488, 624, true, true);
                }
            }
        }).start();


//        gc.drawImage(new Image(createInput(screen.screenImage), 5000, 5000, true, true), 0, 0);
        new AnimationTimer()
        {
            public void handle(long now)
            {

                long oldFrameTime = frameTimes[frameTimeIndex] ;
                frameTimes[frameTimeIndex] = now ;
                frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length ;
                if (frameTimeIndex == 0) {
                    arrayFilled = true ;
                }
                if (arrayFilled) {
                    long elapsedNanos = now - oldFrameTime ;
                    long elapsedNanosPerFrame = elapsedNanos / frameTimes.length ;
                    double frameRate = 1_000_000_000.0 / elapsedNanosPerFrame ;
                    label.setText(String.format("FPS: %.0f", frameRate));
                }

                board.update();

                if (board.player.x*3 > 0 && board.player.x*3 < 312) gc.drawImage( gameScreen, 0, 0 );
                else if (board.player.x*3 > 1176 && board.player.x*3 < 1488) gc.drawImage( gameScreen, -864, 0 );
                else {
//                    for (int j = 1; j < 4; j++) {
                        gc.drawImage( gameScreen, -(board.player.x*3 - 312), 0 );
//                    }
                }

            }
        }.start();

        anchorPane.getChildren().add(label);

        Scene scene = new Scene(anchorPane);
        scene.setOnKeyPressed(board.keyPressed);
        scene.setOnKeyReleased(board.keyReleased);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public InputStream createInput(Image image) {
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] res = null;
        try {
            ImageIO.write(bImage, "png", outputStream);
            res  = outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(res);
    }
}
