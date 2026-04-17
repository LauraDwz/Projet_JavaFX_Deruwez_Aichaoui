package com.fst.projet;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;

import java.io.File;

public class ImageAppController {
    @FXML
    private final ImageView imageView  = new ImageView();
    @FXML
    private final Label statusBar = new Label("Chargez une image pour commencer.");

    private WritableImage originalImage;
    private WritableImage currentImage;

    @FXML
    private void initialize() {


    }

    @FXML
    private void loadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Ouvrir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File file = fileChooser.showOpenDialog(imageView.getScene().getWindow());

        if (file != null) {
            try {
                Image img = new Image(file.toURI().toString());
                originalImage = toWritable(img);
                currentImage  = toWritable(img);
                imageView.setImage(currentImage);
                statusBar.setText("Image chargée : " + file.getName()
                        + "  (" + (int) img.getWidth() + " × " + (int) img.getHeight() + " px)");
                imageView.setImage(img);
            } catch (Exception ex) {
                statusBar.setText("Erreur : impossible de charger l'image.");
            }
        }
    }

    @FXML
    private void resetImage() {
        if (originalImage == null) return;
        currentImage = toWritable(originalImage);
        imageView.setImage(currentImage);
        statusBar.setText("Image réinitialisée.");
    }

    private WritableImage toWritable(Image src) {
        int w = (int) src.getWidth(), h = (int) src.getHeight();
        WritableImage wi = new WritableImage(w, h);
        wi.getPixelWriter().setPixels(0, 0, w, h,
                src.getPixelReader(), 0, 0);
        return wi;
    }
}
