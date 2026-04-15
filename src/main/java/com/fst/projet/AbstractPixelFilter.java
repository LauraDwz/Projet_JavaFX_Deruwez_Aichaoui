package com.fst.projet;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public abstract class AbstractPixelFilter implements ImageFilter {

    @Override
    public WritableImage apply(WritableImage source) {
        int width  = (int) source.getWidth();
        int height = (int) source.getHeight();

        WritableImage result = new WritableImage(width, height);
        PixelReader  reader  = source.getPixelReader();
        PixelWriter  writer  = result.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color original    = reader.getColor(x, y);
                Color transformed = transformColor(original);
                writer.setColor(x, y, transformed);
            }
        }
        return result;
    }
    protected abstract Color transformColor(Color c);
}
