package sprites;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class SpritesImage {

    private final Image spriteSheet = new Image(SpritesImage.class.getClassLoader().getResourceAsStream("res/textures/classic.png"), 256, 256, false, true);
    private final PixelReader pr = spriteSheet.getPixelReader();
    public WritableImage image;
//    public PixelWriter pw = image.getPixelWriter();

    public static SpritesImage portal = new SpritesImage(4, 0);
    public static SpritesImage wall = new SpritesImage(5, 0);
    public static SpritesImage grass = new SpritesImage(6, 0);
    public static SpritesImage brick = new SpritesImage(7, 0);

    public static SpritesImage bombItem = new SpritesImage(0, 10);
    public static SpritesImage flameItem = new SpritesImage(1, 10);
    public static SpritesImage speedItem = new SpritesImage(2, 10);
    public static SpritesImage wallPassItem = new SpritesImage(3, 10);
    public static SpritesImage detonatorItem = new SpritesImage(4, 10);
    public static SpritesImage bombPassItem = new SpritesImage(5, 10);
    public static SpritesImage flamePassItem = new SpritesImage(6, 10);

    public static SpritesImage brickExploded = new SpritesImage(7, 1);
    public static SpritesImage brickExploded1 = new SpritesImage(7, 2);
    public static SpritesImage brickExploded2 = new SpritesImage(7, 3);

    public static SpritesImage playerUp = new SpritesImage(0, 0);
    public static SpritesImage playerRight = new SpritesImage(1, 0);
    public static SpritesImage playerDown = new SpritesImage(2, 0);
    public static SpritesImage playerLeft = new SpritesImage(3, 0);

    public static SpritesImage playerUp1 = new SpritesImage(0, 1);
    public static SpritesImage playerUp2 = new SpritesImage(0, 2);

    public static SpritesImage playerRight1 = new SpritesImage(1, 1);
    public static SpritesImage playerRight2 = new SpritesImage(1, 2);

    public static SpritesImage playerDown1 = new SpritesImage(2, 1);
    public static SpritesImage playerDown2 = new SpritesImage(2, 2);

    public static SpritesImage playerLeft1 = new SpritesImage(3, 1);
    public static SpritesImage playerLeft2 = new SpritesImage(3, 2);

    public static SpritesImage playerDead = new SpritesImage(4, 2);
    public static SpritesImage playerDead1 = new SpritesImage(5, 2);
    public static SpritesImage playerDead2 = new SpritesImage(6, 2);

    public static SpritesImage bomb = new SpritesImage(0, 3);
    public static SpritesImage bomb1 = new SpritesImage(1, 3);
    public static SpritesImage bomb2 = new SpritesImage(2, 3);

    public static SpritesImage bombExploded = new SpritesImage(0, 4);
    public static SpritesImage bombExploded1 = new SpritesImage(0, 5);
    public static SpritesImage bombExploded2 = new SpritesImage(0, 6);

    public static SpritesImage explosionHorizontal = new SpritesImage(1, 7);
    public static SpritesImage explosionHorizontal1 = new SpritesImage(1, 8);
    public static SpritesImage explosionHorizontal2 = new SpritesImage(1, 9);

    public static SpritesImage explosionRight = new SpritesImage(2, 7);
    public static SpritesImage explosionRight1 = new SpritesImage(2, 8);
    public static SpritesImage explosionRight2 = new SpritesImage(2, 9);

    public static SpritesImage explosionLeft = new SpritesImage(0, 7);
    public static SpritesImage explosionLeft1 = new SpritesImage(0, 8);
    public static SpritesImage explosionLeft2 = new SpritesImage(0, 9);

    public static SpritesImage explosionVertical = new SpritesImage(1, 5);
    public static SpritesImage explosionVertical1 = new SpritesImage(2, 5);
    public static SpritesImage explosionVertical2 = new SpritesImage(3, 5);

    public static SpritesImage explosionBelow = new SpritesImage(1, 6);
    public static SpritesImage explosionBelow1 = new SpritesImage(2, 6);
    public static SpritesImage explosionBelow2 = new SpritesImage(3, 6);

    public static SpritesImage explosionAbove = new SpritesImage(1, 4);
    public static SpritesImage explosionAbove1 = new SpritesImage(2, 4);
    public static SpritesImage explosionAbove2 = new SpritesImage(3, 4);

    public static SpritesImage balloomLeft = new SpritesImage(9, 0);
    public static SpritesImage balloomLeft1 = new SpritesImage(9, 1);
    public static SpritesImage balloomLeft2 = new SpritesImage(9, 2);
    public static SpritesImage balloomDead = new SpritesImage(9, 3);
    public static SpritesImage balloomRight = new SpritesImage(10, 0);
    public static SpritesImage balloomRight1 = new SpritesImage(10, 1);
    public static SpritesImage balloomRight2 = new SpritesImage(10, 2);

    public SpritesImage(int x, int y) {
        image = new WritableImage(spriteSheet.getPixelReader(), x*16, y*16, 16, 16);
//        getSpriteImage(x, y);
    }

    private void getSpriteImage(int x, int y) {

        int _x = 0, _y = 0;
        for (int i=y*50; i<y*50+50; i++) {
            for (int j=x*50; j<x*50+50; j++) {
                if (pr.getArgb(j, i) != -65281) {
//                    pw.setArgb(_x, _y, pr.getArgb(j, i));
                }
                _x++;
                if (_x == 50) _x = 0;

            }
            _y++;
            if (_y == 50) _y = 0;
        }
    }
}
