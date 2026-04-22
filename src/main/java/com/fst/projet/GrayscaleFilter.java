package com.fst.projet;

import javafx.scene.paint.Color;

public class GrayscaleFilter extends AbstractPixelFilter {

    @Override
    protected Color transformColor(Color c) {
        double avg = (c.getRed() + c.getGreen() + c.getBlue()) / 3.0;
        return new Color(avg, avg, avg, c.getOpacity());
    }

    @Override
    public String getLabel() {
        return "Noir & Blanc";
    }
}
