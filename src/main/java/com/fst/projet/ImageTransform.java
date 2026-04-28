package com.fst.projet;

import javafx.scene.image.WritableImage;

public abstract class ImageTransform implements ImageOperation {
    protected final String label;
    protected final String id;

    public ImageTransform(String id, String label) {
        this.id = id;
        this.label = label;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public WritableImage apply(WritableImage src) {
        return transform(src);
    }

    protected abstract WritableImage transform(WritableImage src);

}