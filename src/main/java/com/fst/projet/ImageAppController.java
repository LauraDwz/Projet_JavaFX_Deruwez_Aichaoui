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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.util.List;

public class ImageAppController {
    @FXML
    private ImageView imageView;
    @FXML
    private Label statusBar;

    @FXML private Pane centerPane;

    @FXML
    private VBox menuVBox;

    private WritableImage originalImage;
    private WritableImage currentImage;

    private final List<ImageTransform> transforms = List.of(
            new Rotation90("Rotation 90°"),
            new Rotation180("Rotation 180°"),
            new Rotation270(("Rotation -90°")),
            new FlipH("Symétrie horizontale"),
            new FlipV("Symétrie verticale")
    );

    private final List<ImageFilter> filters = List.of(
            new RGBSwapFilter(),
            new GrayscaleFilter(),
            new SepiaFilter(),
            new PrewittFilter()
    );

    @FXML
    public void initialize() {
        imageView.fitWidthProperty().bind(centerPane.widthProperty());
        imageView.fitHeightProperty().bind(centerPane.heightProperty());
        statusBar.setText("Chargez une image pour commencer.");
        menuVBox.getChildren().add(new Label("Transformations"));
        for (ImageTransform t : transforms) {
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

    private void applyTransform(ImageTransform type) {
        if (currentImage == null) { statusBar.setText("Aucune image chargée."); return; }
        currentImage = type.transform(currentImage);
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
