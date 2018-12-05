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

    public void clear() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
//                pw.setArgb(j, i, 0);
            }
        }
    }


}
