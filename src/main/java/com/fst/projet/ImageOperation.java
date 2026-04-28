package com.fst.projet;

import javafx.scene.image.WritableImage;

public interface ImageOperation {
    String getId();
    String getLabel();
    WritableImage apply(WritableImage image);
}