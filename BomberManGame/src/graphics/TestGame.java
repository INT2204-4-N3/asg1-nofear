package graphics;

import audio.PlayAudio;
import entities.Entity;
import entities.bomb.Bomb;
import entities.bomb.Explosion;
import entities.mob.Player;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import level.Board;

import static input.ReadFile.*;

public class TestGame extends Application {

    int mapLevel = 1;
    WritableImage gameScreen;
    final Board board = new Board();
    private final long[] frameTimes = new long[100];
    private int frameTimeIndex = 0 ;
    private boolean arrayFilled = false ;
    Label label = new Label("FPS: ?");
    private boolean isExit = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Entity.board = board;
        AnchorPane anchorPane = new AnchorPane();
        loadMap(".\\src\\res\\levels\\Level" + mapLevel + ".txt", board);
        gameScreen = new WritableImage( 48*mapCol, 48*mapRow);
        board.screen = new Screen(16*mapCol, 16*mapRow);

        board.bombs.add(new Bomb());

        label.setLayoutX(570);
        label.setLayoutY(10);
        PlayAudio.playSound();

        Canvas canvas = new Canvas(624, 624);
        canvas.setLayoutY(30);
        anchorPane.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

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
                    gc.drawImage( gameScreen, -(board.player.x*3 - 312), 0 );
                }
                if (board.isPause){
                    gc.drawImage(new Image(TestGame.class.getClassLoader().getResourceAsStream("res/pausegame.jpg"), 80, 30, false, true), 300, 250);
                }

            }
        }.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isExit) {
                    synchronized (board) {
                        if (!board.player.isEnteredPortal || board.enemies.size() != 0) {
                            board.render();
                        } else {
                            board.removeEntities();
                            mapLevel++;
                            loadMap(".\\src\\res\\levels\\Level" + mapLevel + ".txt", board);
                            board.screen = new Screen(16 * mapCol, 16 * mapRow);
                            board.player.isEnteredPortal = false;
                        }
                        getImageWith(board.screen.screenImage, 3);
                    }
                }
            }
        }).start();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                isExit = true;
            }
        });
        anchorPane.getChildren().add(label);
        MenuBar menuBar = new MenuBar();


        // Tạo các Menu
        Menu fileMenu = new Menu("Option");
        Menu editMenu = new Menu("Edit");
        Menu helpMenu = new Menu("Help");


        // Tạo các MenuItem
        MenuItem newItem = new MenuItem("Power-Ups");
        MenuItem openFileItem = new MenuItem("Open File");
        MenuItem exitItem = new MenuItem("Exit");

        MenuItem copyItem = new MenuItem("Copy");
        MenuItem pasteItem = new MenuItem("Paste");

        newItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AnchorPane root = new AnchorPane();

                Label speedUpLabel = new Label("Increase Speed");
                speedUpLabel.setLayoutX(40);
                speedUpLabel.setLayoutY(10);

                CheckBox yes = new CheckBox();
                yes.setLayoutX(150);
                yes.setLayoutY(10);
                if (board.player.speedDelay == 3) yes.setSelected(false);
                else yes.setSelected(true);
                yes.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (board.player.speedDelay == 3) board.player.speedDelay = 2;
                        else board.player.speedDelay = 3;
                    }
                });

                Label flameLabel = new Label("Flame : " + board.player.bombRange);
                flameLabel.setLayoutX(40);
                flameLabel.setLayoutY(40);
                Button increaseFlame = new Button("+");
                increaseFlame.setLayoutX(150);
                increaseFlame.setLayoutY(40);
                increaseFlame.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        board.player.bombRange++;
                        flameLabel.setText("Flame : " + board.player.bombRange);
                    }
                });
                Button decreaseFlame = new Button("-");
                decreaseFlame.setLayoutX(180);
                decreaseFlame.setLayoutY(40);
                decreaseFlame.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        board.player.bombRange--;
                        flameLabel.setText("Flame : " + board.player.bombRange);
                    }
                });

                Label bombLabel = new Label("Bombs : ");
                bombLabel.setText("Bombs : " + board.bombs.size());
                bombLabel.setLayoutX(40);
                bombLabel.setLayoutY(70);

                Button increaseBomb = new Button("+");
                increaseBomb.setLayoutX(150);
                increaseBomb.setLayoutY(70);
                increaseBomb.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        board.bombs.add(new Bomb());
                        bombLabel.setText("Bombs : " + board.bombs.size());
                    }
                });
                Button decreaseBomb = new Button("-");
                decreaseBomb.setLayoutX(180);
                decreaseBomb.setLayoutY(70);
                decreaseBomb.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (board.bombs.size() > 1) board.bombs.remove(0);
                        bombLabel.setText("Bombs : " + board.bombs.size());
                    }
                });

                Label wallPassLabel = new Label("Wall Pass");
                wallPassLabel.setLayoutX(40);
                wallPassLabel.setLayoutY(100);

                CheckBox yes1 = new CheckBox();
                yes1.setLayoutX(150);
                yes1.setLayoutY(100);
                if (!Player.canPassWall) yes1.setSelected(false);
                else yes1.setSelected(true);
                yes1.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (!Player.canPassWall) Player.canPassWall = true;
                        else Player.canPassWall = false;
                    }
                });

                Label bombPassLabel = new Label("Bomb Pass");
                bombPassLabel.setLayoutX(40);
                bombPassLabel.setLayoutY(130);

                CheckBox yes2 = new CheckBox();
                yes2.setLayoutX(150);
                yes2.setLayoutY(130);
                if (!board.bombPass) yes2.setSelected(false);
                else yes2.setSelected(true);
                yes2.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (!board.bombPass) {
                            Explosion.flamePass = true;
                            board.bombPass = true;
                        }
                        else {
                            Explosion.flamePass = false;
                            board.bombPass = false;
                        }
                    }
                });

                Button okButton = new Button("Ok");
                okButton.setLayoutX(110);
                okButton.setLayoutY(200);

                root.getChildren().addAll(speedUpLabel, yes);
                root.getChildren().addAll(flameLabel, increaseFlame, decreaseFlame);
                root.getChildren().addAll(bombLabel, increaseBomb, decreaseBomb, okButton);
                root.getChildren().addAll(wallPassLabel, yes1);
                root.getChildren().addAll(bombPassLabel, yes2);
                Scene scene = new Scene(root, 240, 230);
                Stage stage = new Stage();
                okButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        stage.close();
                    }
                });
                stage.setScene(scene);
                stage.setTitle("Power-Ups");
                stage.show();
            }
        });

        // Thêm các MenuItem vào Menu.
        fileMenu.getItems().addAll(newItem, openFileItem, exitItem);
        editMenu.getItems().addAll(copyItem, pasteItem);

        // Thêm các Menu vào MenuBar
        menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu);
        anchorPane.getChildren().add(menuBar);
        Scene scene = new Scene(anchorPane);
        scene.setOnKeyPressed(board.keyPressed);
        scene.setOnKeyReleased(board.keyReleased);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void getImageWith(Image src, int scale) {
        PixelReader pr = src.getPixelReader();
        PixelWriter pw = gameScreen.getPixelWriter();
        for (int i = 0; i < src.getHeight(); i++) {
            for (int j = 0; j < src.getWidth(); j++) {
                for (int k = 0; k < scale; k++) {
                    for (int l = 0; l < scale; l++) {
                        pw.setArgb(j*scale + k, i*scale + l, pr.getArgb(j, i));
                    }
                }
            }
        }
    }
}
