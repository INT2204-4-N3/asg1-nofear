package sprites;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class SpritesImage {

    private static final Image spriteSheet = new Image(SpritesImage.class.getClassLoader().getResourceAsStream("res/textures/classic.png"), 256, 256, false, true);
    private static final PixelReader pr = spriteSheet.getPixelReader();
    public WritableImage image;
//    public PixelWriter pw = image.getPixelWriter();
//    public PixelReader pixelReader;

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

    public static SpritesImage onealLeft = new SpritesImage(11, 0);
    public static SpritesImage onealLeft1 = new SpritesImage(11, 1);
    public static SpritesImage onealLeft2 = new SpritesImage(11, 2);
    public static SpritesImage onealDead = new SpritesImage(11, 3);
    public static SpritesImage onealRight = new SpritesImage(12, 0);
    public static SpritesImage onealRight1 = new SpritesImage(12, 1);
    public static SpritesImage onealRight2 = new SpritesImage(12, 2);

    public static SpritesImage dollLeft = new SpritesImage(13, 0);
    public static SpritesImage dollLeft1 = new SpritesImage(13, 1);
    public static SpritesImage dollLeft2 = new SpritesImage(13, 2);
    public static SpritesImage dollDead = new SpritesImage(13, 3);
    public static SpritesImage dollRight = new SpritesImage(14, 0);
    public static SpritesImage dollRight1 = new SpritesImage(14, 1);
    public static SpritesImage dollRight2 = new SpritesImage(14, 2);

    public static SpritesImage mobDead = new SpritesImage(15, 0);

    public static SpritesImage ghostLeft = new SpritesImage(6, 5);
    public static SpritesImage ghostLeft1 = new SpritesImage(6, 6);
    public static SpritesImage ghostLeft2 = new SpritesImage(6, 7);
    public static SpritesImage ghostDead = new SpritesImage(6, 8);
    public static SpritesImage ghostRight = new SpritesImage(7, 5);
    public static SpritesImage ghostRight1 = new SpritesImage(7, 6);
    public static SpritesImage ghostRight2 = new SpritesImage(7, 7);

    public SpritesImage(int x, int y) {
        image = new WritableImage(spriteSheet.getPixelReader(), x*16, y*16, 16, 16);
//        getSpriteImage(x, y);
    }

    private void getSpriteImage(int x, int y) {

        int _x = 0, _y = 0;
        for (int i=y*16; i<y*16+16; i++) {
            for (int j=x*16; j<x*16+16; j++) {
                if (pr.getArgb(j, i) != -65281) {
//                    pw.setArgb(_x, _y, pr.getArgb(j, i));
                }
                _x++;
                if (_x == 16) _x = 0;

            }
            _y++;
            if (_y == 16) _y = 0;
        }

    }
}
