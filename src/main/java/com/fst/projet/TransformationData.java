package com.fst.projet;

import javafx.scene.image.WritableImage;

import java.util.HashMap;
import java.util.Map;

public class TransformationData {

    private String type;
    private WritableImage image;

    public TransformationData(String type, WritableImage image) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setParams(WritableImage image) {
        this.image = image;
    }
}