package com.fst.projet;

import java.util.ArrayList;
import java.util.List;

public class ImageData {

    private String path;
    private List<String> tags;
    private List<TransformationData> transformations;

    public ImageData() {
        this.tags = new ArrayList<>();
        this.transformations = new ArrayList<>();
    }

    public ImageData(String path) {
        this();
        this.path = path;
    }

    // GETTERS / SETTERS
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public List<String> getTags() { return tags; }
    public void addTags(String tag) { this.tags.add(tag); }
    public List<TransformationData> getTransformations() { return transformations; }
    public void addTransformations(TransformationData transformation) { this.transformations.add(transformation); }
    public void resetTransformations() { this.transformations.clear(); }
    public void printTransformations() {
        for (TransformationData transformation : this.transformations) {
            System.out.println(transformation.getType());
        }
    }
}