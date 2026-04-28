package com.fst.projet;

import javafx.scene.image.WritableImage;


public class TransformationData {

    private String type;
    public TransformationData() {}
    public TransformationData(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}