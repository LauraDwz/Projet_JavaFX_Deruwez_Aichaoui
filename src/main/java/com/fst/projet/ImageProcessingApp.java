import filters.*;
import transforms.ImageTransform;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;
public class ImageProcessingApp extends Application{
    private WritableImage originalImage;
    private WritableImage currentImage;

    private final ImageView imageView  = new ImageView();
    private final Label      statusBar = new Label("Chargez une image pour commencer.");

    private final List<ImageFilter> filters = List.of(
            new RGBSwapFilter(),
            new GrayscaleFilter(),
            new SepiaFilter(),
            new PrewittFilter()
    );
    @Override
    public void start(Stage stage) {
        stage.setTitle("Traitement d'image");
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(600);
        imageView.setFitHeight(500);

        ScrollPane imageScroll = new ScrollPane(imageView);
        imageScroll.setFitToWidth(true);
        imageScroll.setFitToHeight(true);
        imageScroll.setPrefSize(620, 520);
        VBox sidebar = buildSidebar(stage);
        sidebar.setPrefWidth(220);

        statusBar.setPadding(new Insets(4, 8, 4, 8));
        statusBar.setMaxWidth(Double.MAX_VALUE);
        statusBar.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-border-width: 1 0 0 0;");
        BorderPane root = new BorderPane();
        root.setCenter(imageScroll);
        root.setRight(sidebar);
        root.setBottom(statusBar);

        stage.setScene(new Scene(root, 860, 580));
        stage.show();
    }
    private VBox buildSidebar(Stage stage) {
        VBox box = new VBox(12);
        box.setPadding(new Insets(14));
        box.setStyle("-fx-background-color: #fafafa; -fx-border-color: #ddd; -fx-border-width: 0 0 0 1;");
        box.getChildren().add(sectionLabel("Fichier"));
        Button btnLoad = new Button("Charger une image…");
        btnLoad.setMaxWidth(Double.MAX_VALUE);
        btnLoad.setOnAction(e -> loadImage(stage));

        Button btnReset = new Button("Réinitialiser");
        btnReset.setMaxWidth(Double.MAX_VALUE);
        btnReset.setOnAction(e -> resetImage());

        box.getChildren().addAll(btnLoad, btnReset, new Separator());
        box.getChildren().add(sectionLabel("Transformations"));
        for (ImageTransform.Type t : ImageTransform.Type.values()) {
            Button btn = new Button(t.getLabel());
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setOnAction(e -> applyTransform(t));
            box.getChildren().add(btn);
        }
        box.getChildren().add(new Separator());
        box.getChildren().add(sectionLabel("Filtres"));
        for (ImageFilter f : filters) {
            Button btn = new Button(f.getName());
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setOnAction(e -> applyFilter(f));
            box.getChildren().add(btn);
        }
        return box;
    }
    private Label sectionLabel(String text) {
        Label lbl = new Label(text.toUpperCase());
        lbl.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #888;");
        return lbl;
    }
    private void loadImage(Stage stage) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Ouvrir une image");
        File file = fc.showOpenDialog(stage);
        if (file == null) return;

        try {
            Image img = new Image(file.toURI().toString());
            originalImage = toWritable(img);
            currentImage  = toWritable(img);
            imageView.setImage(currentImage);
            status("Image chargée : " + file.getName()
                    + "  (" + (int) img.getWidth() + " × " + (int) img.getHeight() + " px)");
        } catch (Exception ex) {
            status("Erreur : impossible de charger l'image.");
        }
    }

    private void resetImage() {
        if (originalImage == null) return;
        currentImage = toWritable(originalImage);
        imageView.setImage(currentImage);
        status("Image réinitialisée.");
    }

    private void applyFilter(ImageFilter filter) {
        if (currentImage == null) { status("Aucune image chargée."); return; }
        currentImage = filter.apply(currentImage);
        imageView.setImage(currentImage);
        status("Filtre appliqué : " + filter.getName());
    }

    private void applyTransform(ImageTransform.Type type) {
        if (currentImage == null) { status("Aucune image chargée."); return; }
        currentImage = ImageTransform.apply(currentImage, type);
        imageView.setImage(currentImage);
        status("Transformation appliquée : " + type.getLabel());
    }
    // Convertit une Image (potentiellement non éditable) en WritableImage.
    private WritableImage toWritable(Image src) {
        int w = (int) src.getWidth(), h = (int) src.getHeight();
        WritableImage wi = new WritableImage(w, h);
        wi.getPixelWriter().setPixels(0, 0, w, h,
                src.getPixelReader(), 0, 0);
        return wi;
    }

    private void status(String msg) {
        statusBar.setText(msg);
    }

    public static void main(String[] args) {
        launch(args);
    }

}

