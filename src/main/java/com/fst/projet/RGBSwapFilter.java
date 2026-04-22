package com.fst.projet;
import javafx.scene.paint.Color;

public class RGBSwapFilter extends AbstractPixelFilter {

    @Override
    protected Color transformColor(Color c) {
        return new Color(c.getGreen(), c.getBlue(), c.getRed(), c.getOpacity());
    }

    @Override
    public String getLabel() {
        return "Échange RGB → GBR";
    }
}
