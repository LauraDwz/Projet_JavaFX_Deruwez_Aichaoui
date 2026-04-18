package com.fst.projet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.util.List;

public class ImageAppController {
    @FXML
    private ImageView imageView;
    @FXML
    private StackPane imageContainer;
    @FXML
    private Label statusBar;

    @FXML
    private VBox menuVBox;

    private WritableImage originalImage;
    private WritableImage currentImage;

    private final List<ImageFilter> filters = List.of(
            new RGBSwapFilter(),
            new GrayscaleFilter(),
            new SepiaFilter(),
            new PrewittFilter()
    );

    @FXML
    public void initialize() {
        imageView.fitWidthProperty().bind(imageContainer.widthProperty());
        imageView.fitHeightProperty().bind(imageContainer.heightProperty());
        statusBar.setText("Chargez une image pour commencer.");
        menuVBox.getChildren().add(new Label("Transformations"));
        for (ImageTransform.Type t : ImageTransform.Type.values()) {
            Button btn = new Button(t.getLabel());
            btn.setOnAction(e ->  applyTransform(t));
            menuVBox.getChildren().add(btn);
        }

        menuVBox.getChildren().add(new Separator());

        menuVBox.getChildren().add(new Label("Filtres"));

        for (ImageFilter f : filters) {
            Button btn = new Button(f.getName());
            btn.setOnAction(e -> applyFilter(f));
            menuVBox.getChildren().add(btn);
        }
    }

    private void applyFilter(ImageFilter filter) {
        if (currentImage == null) { statusBar.setText("Aucune image chargée."); return; }
        currentImage = filter.apply(currentImage);
        imageView.setImage(currentImage);
        statusBar.setText("Filtre appliqué : " + filter.getName());
    }

    private void applyTransform(ImageTransform.Type type) {
        if (currentImage == null) { statusBar.setText("Aucune image chargée."); return; }
        currentImage = ImageTransform.apply(currentImage, type);
        imageView.setImage(currentImage);
        statusBar.setText("Transformation appliquée : " + type.getLabel());
    }

    @FXML
    private void loadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Ouvrir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );

        Window window = ((Node) event.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(window);

        if (file != null) {
            try {
                Image img = new Image(file.toURI().toString());
                originalImage = toWritable(img);
                currentImage  = toWritable(img);
                imageView.setImage(currentImage);
                statusBar.setText("Image chargée : " + file.getName()
                        + "  (" + (int) img.getWidth() + " × " + (int) img.getHeight() + " px)");
                System.out.println("IMAGE VIEW = " + imageView);
                System.out.println("IMAGE = " + img);
                System.out.println("WIDTH = " + img.getWidth());
                System.out.println("HEIGHT = " + img.getHeight());
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
