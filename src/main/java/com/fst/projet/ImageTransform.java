package com.fst.projet;

import javafx.scene.image.WritableImage;

public abstract class ImageTransform {
    protected final String label;

    ImageTransform(String label) {
        this.label = label;
    }

    protected String getLabel() {
        return label;
    }

    protected abstract WritableImage transform(WritableImage src);

}