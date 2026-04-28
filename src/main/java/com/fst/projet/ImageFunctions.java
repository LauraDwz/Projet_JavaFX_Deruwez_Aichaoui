package com.fst.projet;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import java.awt.image.BufferedImage;

public class ImageFunctions {

    public static WritableImage toWritable(Image src) {
        int w = (int) src.getWidth();
        int h = (int) src.getHeight();
        WritableImage wi = new WritableImage(w, h);
        wi.getPixelWriter().setPixels(0, 0, w, h,
                src.getPixelReader(), 0, 0);
        return wi;
    }

    public static BufferedImage toBufferedImage(WritableImage fxImage) {
        return SwingFXUtils.fromFXImage(fxImage, null);
    }

    public static WritableImage toFXImage(BufferedImage bufferedImage) {
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }
}
