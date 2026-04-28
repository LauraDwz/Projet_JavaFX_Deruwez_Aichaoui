package com.fst.projet;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class GalleryController {
    @FXML
    private TextField searchField;

    @FXML
    private TilePane tilePane;

    private final GalleryManager galleryManager = new GalleryManager();
    private List<ImageData> liste;
    private ImageAppController parentController;

    public void setParentController(ImageAppController controller) {
        this.parentController = controller;
    }

    @FXML
    public void initialize() {
        liste = galleryManager.loadAll();
        updateGallery(liste);

        searchField.textProperty().addListener((obs, oldValue, newValue) -> {
            List<ImageData> filtered = galleryManager.filterByTag(liste, newValue);
            updateGallery(filtered);
        });
    }

    private void updateGallery(List<ImageData> images) {
        tilePane.getChildren().clear();
        for (ImageData data : images) {
            VBox box = createImageBox(data);
            tilePane.getChildren().add(box);
        }
    }

    private VBox createImageBox(ImageData data) {
        Image img = new Image(new File(data.getPath()).toURI().toString());

        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(120);
        imageView.setFitHeight(120);
        imageView.setPreserveRatio(true);

        Label label = new Label(data.getName());

        FlowPane tagPane = new FlowPane();
        tagPane.setHgap(5);
        tagPane.setVgap(5);
        tagPane.setAlignment(Pos.CENTER);
        tagPane.setPrefWrapLength(120);

        for (String tag : data.getTags()) {
            Label tagLabel = new Label("#" + tag);
            tagLabel.setStyle("-fx-background-color: lightgray; -fx-padding: 3 6 3 6; -fx-background-radius: 8;");
            tagPane.getChildren().add(tagLabel);
        }

        VBox box = new VBox(8, imageView, label, tagPane);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-padding:10; -fx-border-color: lightgray; -fx-border-radius: 10;");

        box.setOnMouseClicked(e -> {
            parentController.reloadSavedImage(data);
            Stage stage = (Stage) tilePane.getScene().getWindow();
            stage.close();
        });

        return box;
    }
}
