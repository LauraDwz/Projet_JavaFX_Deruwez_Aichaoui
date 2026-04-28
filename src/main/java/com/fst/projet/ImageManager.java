package com.fst.projet;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import java.io.File;

public class ImageManager {
    private WritableImage originalImage;
    private WritableImage currentImage;
    private ImageData imageData;

    public ImageManager() {
        originalImage = null;
        currentImage = null;
        imageData = null;
    }
    public boolean noImage() {
        return currentImage == null;
    }

    public void loadImage(File file) {
        Image img = new Image(file.toURI().toString());
        originalImage = ImageFunctions.toWritable(img);
        currentImage = ImageFunctions.toWritable(img);
        imageData = new ImageData(file.getPath());
    }

    public void applyTransform(ImageTransform transform) {
        imageData.addTransformations(transform.getId());
        currentImage = transform.transform(currentImage);
    }

    public void applyFilter(ImageFilter filter) {
        imageData.addTransformations(filter.getId());
        currentImage = filter.apply(currentImage);
    }

    public void reloadCurrent() {
        currentImage = ImageFunctions.toWritable(originalImage);
    }

    public void reset() {
        imageData.resetTransformations();
        currentImage = ImageFunctions.toWritable(originalImage);
    }

    public void reloadSavedImage(ImageData data) {
        Image img = new Image(new File(data.getPath()).toURI().toString());
        originalImage = ImageFunctions.toWritable(img);
        currentImage = ImageFunctions.toWritable(img);
        imageData = data;
        reloadTransformations();
    }

    public void reloadTransformations() {
        currentImage = ImageFunctions.toWritable(originalImage);
        for(String id : imageData.getTransformations()) {
            ImageOperation op = OperationRegistry.getOperation(id);
            if(op != null) {
                currentImage = op.apply(currentImage);
            }
        }
    }

    public void replaceOriginal(WritableImage newImage) {
        originalImage = ImageFunctions.toWritable(newImage);
        currentImage = ImageFunctions.toWritable(originalImage);
    }
    public WritableImage getCurrentImage() {
        return currentImage;
    }
    public WritableImage getOriginalImage() {
        return originalImage;
    }
    public ImageData getImageData() {
        return imageData;
    }
    public void setNameImageData(String name) {
        this.imageData.setName(name);
    }
    public void addTagData(String tag) {
        this.imageData.addTags(tag);
    }
    public void setPathImageData(String path) {
        this.imageData.setPath(path);
    }
}
