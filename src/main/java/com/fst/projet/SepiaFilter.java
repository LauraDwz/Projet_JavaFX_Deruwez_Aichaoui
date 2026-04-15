package com.fst.projet;

import javafx.scene.paint.Color;

/**
 * Filtre sépia.
 * Formule issue de : http://stackoverflow.com/questions/1061093/
 *
 * outputRed   = (R * 0.393) + (G * 0.769) + (B * 0.189)
 * outputGreen = (R * 0.349) + (G * 0.686) + (B * 0.168)
 * outputBlue  = (R * 0.272) + (G * 0.534) + (B * 0.131)
 *
 * Les composantes JavaFX sont normalisées entre 0.0 et 1.0,
 * donc la formule s'applique directement sans conversion 0-255.
 */
public class SepiaFilter extends AbstractPixelFilter {

    @Override
    protected Color transformColor(Color c) {
        double r = c.getRed();
        double g = c.getGreen();
        double b = c.getBlue();

        double nr = Math.min(1.0, r * 0.393 + g * 0.769 + b * 0.189);
        double ng = Math.min(1.0, r * 0.349 + g * 0.686 + b * 0.168);
        double nb = Math.min(1.0, r * 0.272 + g * 0.534 + b * 0.131);

        return new Color(nr, ng, nb, c.getOpacity());
    }

    @Override
    public String getName() {
        return "Sépia";
    }
}