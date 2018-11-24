package entities.tile;

import entities.Entity;
import graphics.Screen;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class Grass extends Tile {
    public Grass(int x, int y, WritableImage tileImage) {
        super(x, y, tileImage);
    }

    @Override
    public boolean collide(int _x, int _y) {
        return false;
    }

    @Override
    public boolean collide(Entity entity) {
        return false;
    }

//    public static void render(int _x, int _y, Screen screen) {
//        PixelReader pr = screen.screenImage.getPixelReader();
//        PixelWriter pw = screen.screenImage.getPixelWriter();
//        for (int i = _y; i < _y + 50; i++) {
//            for (int j = _x; j < _x + 50; j++) {
//                if (pr.getArgb(j - _x, i - _y) == -65281)
//                    pw.setArgb(j, i, -11493376);
////                x++;
////                if (x == 50) x = 0;
//            }
////            y++;
////            if (y == 50) y = 0;
//        }
//    }
}
