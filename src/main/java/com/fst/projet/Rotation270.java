package com.fst.projet;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class Rotation270 extends ImageTransform {
    public Rotation270() {
        super("rotation270", "Rotation -90°");
    }

    @Override
    protected WritableImage transform(WritableImage src) {
        int w = (int) src.getWidth(), h = (int) src.getHeight();
        WritableImage dst = new WritableImage(h, w);
        PixelReader r = src.getPixelReader();
        PixelWriter wr = dst.getPixelWriter();
        for (int y = 0; y < h; y++)
            for (int x = 0; x < w; x++)
                wr.setColor(y, w - 1 - x, r.getColor(x, y));
        return dst;
    }
}