package com.fst.projet;

import javafx.scene.image.WritableImage;

public interface ImageFilter {
    WritableImage apply(WritableImage source);
    String getName();
}