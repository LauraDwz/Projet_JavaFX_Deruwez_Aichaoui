package com.fst.projet;

import java.util.ArrayList;
import java.util.List;

public class ImageData {

    private String path;
    private String name;
    private final List<String> tags;
    private final List<String> transformations;

    public ImageData() {
        this.tags = new ArrayList<>();
        this.transformations = new ArrayList<>();
    }

    public ImageData(String path) {
        this();
        this.path = path;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // GETTERS / SETTERS
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public List<String> getTags() { return tags; }
    public void addTags(String tag) { this.tags.add(tag); }
    public List<String> getTransformations() { return transformations; }
    public void addTransformations(String transformation) { this.transformations.add(transformation); }
    public void resetTransformations() { this.transformations.clear(); }
}