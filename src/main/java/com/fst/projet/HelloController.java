package com.fst.projet;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private ImageView vueImage;
    @FXML
    private FileChooser fileChooser;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    private void initialize() {
        String url;
        try {
            url = getClass()
                    .getResource("/com/fst/projet/images/test.jpg")
                    .toExternalForm();
            Image img = new Image(url);
            vueImage.setImage(img);
        } catch (NullPointerException e) {
            System.out.println("NullPointerException");
        }

    }

    @FXML
    private void chooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose your image");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(vueImage.getScene().getWindow());
        if (selectedFile != null) {
            Image img = new Image(selectedFile.toURI().toString());
            vueImage.setImage(img);
        }
    }
}


