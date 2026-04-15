package com.fst.projet;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ImageTransform {

    public enum Type {
        ROTATION_90   ("Rotation 90°"),
        ROTATION_180  ("Rotation 180°"),
        ROTATION_270  ("Rotation 270°"),
        FLIP_HORIZONTAL("Symétrie horizontale"),
        FLIP_VERTICAL  ("Symétrie verticale");

        private final String label;
        Type(String label) { this.label = label; }
        public String getLabel() { return label; }
    }
    public static WritableImage apply(WritableImage source, Type type) {
        return switch (type) {
            case ROTATION_90    -> rotate90(source);
            case ROTATION_180   -> rotate180(source);
            case ROTATION_270   -> rotate270(source);
            case FLIP_HORIZONTAL -> flipH(source);
            case FLIP_VERTICAL   -> flipV(source);
        };
    }
    private static WritableImage rotate90(WritableImage src) {
        int w = (int) src.getWidth(), h = (int) src.getHeight();
        WritableImage dst = new WritableImage(h, w);
        PixelReader r = src.getPixelReader();
        PixelWriter wr = dst.getPixelWriter();
        for (int y = 0; y < h; y++)
            for (int x = 0; x < w; x++)
                wr.setColor(h - 1 - y, x, r.getColor(x, y));
        return dst;
    }
    private static WritableImage rotate180(WritableImage src) {
        int w = (int) src.getWidth(), h = (int) src.getHeight();
        WritableImage dst = new WritableImage(w, h);
        PixelReader r = src.getPixelReader();
        PixelWriter wr = dst.getPixelWriter();
        for (int y = 0; y < h; y++)
            for (int x = 0; x < w; x++)
                wr.setColor(w - 1 - x, h - 1 - y, r.getColor(x, y));
        return dst;
    }
    private static WritableImage rotate270(WritableImage src) {
        int w = (int) src.getWidth(), h = (int) src.getHeight();
        WritableImage dst = new WritableImage(h, w);
        PixelReader r = src.getPixelReader();
        PixelWriter wr = dst.getPixelWriter();
        for (int y = 0; y < h; y++)
            for (int x = 0; x < w; x++)
                wr.setColor(y, w - 1 - x, r.getColor(x, y));
        return dst;
    }
    private static WritableImage flipH(WritableImage src) {
        int w = (int) src.getWidth(), h = (int) src.getHeight();
        WritableImage dst = new WritableImage(w, h);
        PixelReader r = src.getPixelReader();
        PixelWriter wr = dst.getPixelWriter();
        for (int y = 0; y < h; y++)
            for (int x = 0; x < w; x++)
                wr.setColor(w - 1 - x, y, r.getColor(x, y));
        return dst;
    }
    private static WritableImage flipV(WritableImage src) {
        int w = (int) src.getWidth(), h = (int) src.getHeight();
        WritableImage dst = new WritableImage(w, h);
        PixelReader r = src.getPixelReader();
        PixelWriter wr = dst.getPixelWriter();
        for (int y = 0; y < h; y++)
            for (int x = 0; x < w; x++)
                wr.setColor(x, h - 1 - y, r.getColor(x, y));
        return dst;
    }
}
