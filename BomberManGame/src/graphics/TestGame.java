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

    private int mapLevel = 2;
    private WritableImage gameScreen;
    private Board board = new Board();
    private final long[] frameTimes = new long[100];
    private int frameTimeIndex = 0 ;
    private boolean arrayFilled = false ;
    private Label fpsLabel = new Label("FPS: ?");
    private Label livesLabel = new Label();
    private Label levelLabel = new Label();
    private Label gameInfoLabel = new Label();
    public static boolean isExit = false;
    private Image gameOver = new Image(TestGame.class.getClassLoader().getResourceAsStream("res/gameover.png"), 250, 100, false, true);
    private Image pause = new Image(TestGame.class.getClassLoader().getResourceAsStream("res/pausegame.jpg"), 80, 30, false, true);
    private Image nextLevel = new Image(TestGame.class.getClassLoader().getResourceAsStream("res/next_level.jpg"), 160, 50, false, true);
    private Image winGame = new Image(TestGame.class.getClassLoader().getResourceAsStream("res/wingame.jpg"), 224, 60, false, true);

    private int timer = 0, timer1 = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Entity.board = board;
        AnchorPane anchorPane = new AnchorPane();
        loadMap(".\\src\\res\\levels\\Level" + mapLevel + ".txt", board);
        levelLabel.setText("Level : " + mapLevel);
        gameScreen = new WritableImage( 48*mapCol, 48*mapRow);
        board.screen = new Screen(16*mapCol, 16*mapRow);

        board.bombs.add(new Bomb());

        fpsLabel.setLayoutX(570);
        fpsLabel.setLayoutY(10);
        livesLabel.setLayoutX(500);
        livesLabel.setLayoutY(10);
        levelLabel.setLayoutX(280);
        levelLabel.setLayoutY(10);
        gameInfoLabel.setLayoutX(350);
        gameInfoLabel.setLayoutY(10);
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
                    fpsLabel.setText(String.format("FPS: %.0f", frameRate));
                }

                board.update();

                if (board.player.x*3 > 0 && board.player.x*3 < 312) gc.drawImage( gameScreen, 0, 0 );
                else if (board.player.x*3 > 1176 && board.player.x*3 < 1488) gc.drawImage( gameScreen, -864, 0 );
                else {
                    gc.drawImage( gameScreen, -(board.player.x*3 - 312), 0 );
                }
                if (board.isPause) gc.drawImage(pause, 300, 250);
                if (board.player.lives == 0) {
                    timer++;
                    if (timer >= 60) gc.drawImage(gameOver, 200, 0);

                }
                if (board.player.isEnteredPortal && board.enemies.size() == 0) {
                    if (mapLevel < 7 && timer1 < 45) gc.drawImage(nextLevel, 240, 150);
                    if (mapLevel == 7) gc.drawImage(winGame, 200, 150);
                }

                gameInfoLabel.setText("Remaining enemy : " + board.enemies.size());
                livesLabel.setText("Lives : " + board.player.lives);
                levelLabel.setText("Level : " + mapLevel);
            }
        }.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isExit) {
//                    synchronized (board) {
                        if (!board.player.isEnteredPortal || board.enemies.size() != 0) {
//                            board.update();
                            board.render();
                        } else {
                            timer1++;
                            if (timer1 == 45 && mapLevel < 7) {
                                board.removeEntities();
                                mapLevel++;
                                loadMap(".\\src\\res\\levels\\Level" + mapLevel + ".txt", board);
                                board.screen = new Screen(16 * mapCol, 16 * mapRow);
                                board.player.isEnteredPortal = false;
                                timer1 = 0;
                            }
                        }
                        getImageWith(board.screen.screenImage, 3);
//                    }
                }
            }
        }).start();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                isExit = true;
            }
        });
        anchorPane.getChildren().addAll(fpsLabel, livesLabel, levelLabel, gameInfoLabel);
        MenuBar menuBar = new MenuBar();


        Menu optionsMenu = new Menu("Options");
        Menu editMenu = new Menu("Edit");
        Menu helpMenu = new Menu("Help");

        MenuItem newGameItem = new MenuItem("New game");
        MenuItem powerUpsItem = new MenuItem("Power-Ups");
        MenuItem helpItem = new MenuItem("Help");
        MenuItem exitItem = new MenuItem("Exit");

        MenuItem copyItem = new MenuItem("Copy");
        MenuItem pasteItem = new MenuItem("Paste");

        newGameItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mapLevel = 1;
                board.removeEntities();
                loadMap(".\\src\\res\\levels\\Level" + mapLevel + ".txt", board);
                board.screen = new Screen(16 * mapCol, 16 * mapRow);
            }
        });

        helpItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AnchorPane anchorPane1 = new AnchorPane();
                TextArea textArea = new TextArea();
                textArea.setWrapText(true);
                textArea.setPrefSize(400, 400);
                textArea.setText("               Game là dễ không có gì để 'Help' cả =))\n");
                textArea.appendText("W/Up, D/Right, S/Down, A/Left để di chuyển nhân vật đi lên, qua phải, đi xuống, qua trái. Space để đặt Bomb\n");
                textArea.appendText("Ngoài ra bạn còn có thể tăng sức mạnh một cách bá đạo cho nhân vật ở mục Power-Ups :D\n");
                textArea.appendText("Tiêu diệt hết Enemy và tìm ra Portal để qua màn chơi mới. Chúc may mắn!");
                textArea.setEditable(false);
                anchorPane1.getChildren().addAll(textArea);
                Stage stage = new Stage();
                stage.setTitle("Help");
                stage.setScene(new Scene(anchorPane1, 400, 400));
                stage.show();
            }
        });

        powerUpsItem.setOnAction(new EventHandler<ActionEvent>() {
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

        exitItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                isExit = true;
                primaryStage.close();
            }
        });
        optionsMenu.getItems().addAll(newGameItem, powerUpsItem, helpItem, exitItem);
        editMenu.getItems().addAll(copyItem, pasteItem);

        menuBar.getMenus().addAll(optionsMenu, editMenu, helpMenu);
        anchorPane.getChildren().add(menuBar);
        Scene scene = new Scene(anchorPane);
        scene.setOnKeyPressed(board.keyPressed);
        scene.setOnKeyReleased(board.keyReleased);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Bomberman Game");
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
