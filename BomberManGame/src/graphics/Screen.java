package graphics;

import entities.Entity;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class Screen {

    int width, height;
    public WritableImage screenImage;
    PixelWriter pw;

    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
        screenImage = new WritableImage(width, height);
        pw = screenImage.getPixelWriter();
    }

    public void renderEntity(int x, int y, Entity entity) {
//        if (entity.spriteImage != null) {
            PixelReader pr = entity.spriteImage.getPixelReader();
            int size = (int) entity.spriteImage.getWidth();
            for (int i = y; i < y + size; i++) {
                for (int j = x; j < x + size; j++) {
                    if (pr.getArgb(j - x, i - y) != -65281 && pr.getArgb(j - x, i - y) != 0) {
                        pw.setArgb(j, i, pr.getArgb(j - x, i - y));
                    }
                }
            }
//        }
    }

    public void renderGrass(int _x, int _y, Screen screen) {
        PixelReader pr = screen.screenImage.getPixelReader();
//        int x = 0, y = 0;
        for (int i = _y; i < _y + 50; i++) {
            for (int j = _x; j < _x + 50; j++) {
//                if (pr.getArgb(j - _x, i - _y) == -65281)
//                    pw.setArgb(j, i, -11493376);
//                x++;
//                if (x == 50) x = 0;
            }
//            y++;
//            if (y == 50) y = 0;
        }
    }

    public void reRenderGrass(int _x, int _y, Entity entity) {
        PixelReader pr = entity.spriteImage.getPixelReader();
//        int x = 0, y = 0;
        for (int i = _y; i < _y + 50; i++) {
            for (int j = _x; j < _x + 50; j++) {
//                if (pr.getArgb(j - _x, i - _y) == -65281) pw.setArgb(j, i, -11493376);
//                x++;
//                if (x == 50) x = 0;
            }
//            y++;
//            if (y == 50) y = 0;
        }
    }

    public void clear() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
//                pw.setArgb(j, i, 0);
            }
        }
    }

    public static void main(String[] args) {
        WritableImage image = new WritableImage(1, 1);
        int w = (int) image.getWidth();
        int h = (int) image.getHeight();
        System.out.println(w);
        image = null;
        w = (int) image.getWidth();
        System.out.println(w);
//        PixelWriter pw1 = image.getPixelWriter();
//        WritableImage image1 = new WritableImage(1, 1);
//        PixelWriter pw = image1.getPixelWriter();
//        pw.setArgb(0, 0, -100);
//        PixelReader pr = image.getPixelReader();
//        System.out.println(pr.getArgb(0, 0));
//        System.out.println(pr.getArgb(0, 0));
//        pw1.setArgb(0, 0, -100);
    }
}
