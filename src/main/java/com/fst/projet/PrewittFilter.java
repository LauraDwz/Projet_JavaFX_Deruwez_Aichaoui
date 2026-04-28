package com.fst.projet;


import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;


public class PrewittFilter implements ImageFilter {

    private static final int[][] KX = {
            {-1, 0, 1},
            {-1, 0, 1},
            {-1, 0, 1}
    };

    private static final int[][] KY = {
            {-1, -1, -1},
            { 0,  0,  0},
            { 1,  1,  1}
    };

    @Override
    public WritableImage apply(WritableImage source) {
        int width  = (int) source.getWidth();
        int height = (int) source.getHeight();

        WritableImage result = new WritableImage(width, height);
        PixelReader   reader = source.getPixelReader();
        PixelWriter   writer = result.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                double gx = 0, gy = 0;

                // Convolution 3×3
                for (int ky = -1; ky <= 1; ky++) {
                    for (int kx = -1; kx <= 1; kx++) {
                        int nx = clamp(x + kx, 0, width  - 1);
                        int ny = clamp(y + ky, 0, height - 1);

                        double lum = luminance(reader.getColor(nx, ny));
                        gx += lum * KX[ky + 1][kx + 1];
                        gy += lum * KY[ky + 1][kx + 1];
                    }
                }

                double magnitude = Math.min(1.0, Math.sqrt(gx * gx + gy * gy));
                writer.setColor(x, y, new Color(magnitude, magnitude, magnitude, 1.0));
            }
        }
        return result;
    }

    /** Luminance perceptuelle d'une couleur (niveaux de gris standard). */
    private double luminance(Color c) {
        return 0.299 * c.getRed() + 0.587 * c.getGreen() + 0.114 * c.getBlue();
    }

    private int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }

    @Override
    public String getLabel() {
        return "Prewitt (contours)";
    }

    @Override
    public String getId() {
        return "prewitt";
    }
}
