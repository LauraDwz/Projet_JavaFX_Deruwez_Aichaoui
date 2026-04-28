package com.fst.projet;

import javafx.scene.paint.Color;

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
    public String getId() {
        return "sepia";
    }

    @Override
    public String getLabel() {
        return "Sépia";
    }
}