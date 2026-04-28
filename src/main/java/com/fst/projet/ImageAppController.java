package com.fst.projet;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ImageAppController {
    @FXML
    private ImageView imageView;
    @FXML
    private Label statusBar;
    @FXML Button saveButton;

    @FXML private Pane centerPane;

    @FXML
    private VBox menuVBox;

    private WritableImage originalImage;
    private WritableImage currentImage;
    private ImageData imageData;

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
            Button btn = new Button(f.getLabel());
            btn.setOnAction(e -> applyFilter(f));
            menuVBox.getChildren().add(btn);
        }

        imageData = new ImageData();
    }

    private void applyFilter(ImageFilter filter) {
        if (currentImage == null) { statusBar.setText("Aucune image chargée."); return; }
        imageData.addTransformations(filter.getLabel());
        currentImage = filter.apply(currentImage);
        imageView.setImage(currentImage);
        statusBar.setText("Filtre appliqué : " + filter.getLabel());
    }

    private void applyTransform(ImageTransform type) {
        if (currentImage == null) { statusBar.setText("Aucune image chargée."); return; }
        imageData.addTransformations(type.getLabel());
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
                imageData= new ImageData(file.getPath());
            } catch (Exception ex) {
                statusBar.setText("Erreur : impossible de charger l'image.");
            }
        }
    }

    @FXML
    private void resetImage() {
        if (originalImage == null) return;
        imageData.resetTransformations();
        currentImage = toWritable(originalImage);
        imageView.setImage(currentImage);
        statusBar.setText("Image réinitialisée.");
    }

    @FXML
    private void openImageSaved() {
        openSavedGallery();
    }

    public void openSavedGallery() {
        List<ImageData> liste = SaveData.loadAllData();

        Stage stage = new Stage();

        TilePane tilePane = new TilePane();
        tilePane.setHgap(20);
        tilePane.setVgap(20);
        tilePane.setPrefColumns(3);

        // barre de recherche
        TextField searchField = new TextField();
        searchField.setPromptText("Rechercher par tag...");

        // affichage initial
        updateGallery(tilePane, liste, "", stage);

        // filtrage dynamique quand on tape
        searchField.textProperty().addListener((obs, oldValue, newValue) -> updateGallery(tilePane, liste, newValue, stage));

        ScrollPane scroll = new ScrollPane(tilePane);
        scroll.setFitToWidth(true);

        VBox root = new VBox(15, searchField, scroll);

        Scene scene = new Scene(root, 600, 450);
        stage.setScene(scene);
        stage.setTitle("Mes sauvegardes");
        stage.show();
    }

    public void updateGallery(TilePane tilePane, List<ImageData> liste, String recherche, Stage stage) {
        tilePane.getChildren().clear();

        for (ImageData data : liste) {

            boolean correspond = false;

            if (recherche == null || recherche.isEmpty()) {
                correspond = true;
            } else {
                for (String tag : data.getTags()) {
                    if (tag.toLowerCase().contains(recherche.toLowerCase())) {
                        correspond = true;
                        break;
                    }
                }
            }

            if (correspond) {
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
                tagPane.setPrefWidth(120);
                tagPane.setMaxWidth(120);

                for (String tag : data.getTags()) {
                    Label tagLabel = new Label("#" + tag);
                    tagLabel.setStyle("-fx-background-color: lightgray; -fx-padding: 3 6 3 6; -fx-background-radius: 8;");
                    tagPane.setAlignment(Pos.CENTER);
                    tagPane.getChildren().add(tagLabel);
                }

                VBox box = new VBox(8, imageView, label, tagPane);
                box.setAlignment(Pos.CENTER);
                box.setStyle("-fx-padding:10; -fx-border-color: lightgray; -fx-border-radius: 10;");

                box.setOnMouseClicked(e -> {
                    reloadSavedImage(data);
                    stage.close();
                });

                tilePane.getChildren().add(box);
            }
        }
    }
    public void reloadTransformations(ImageData data) {

        currentImage = toWritable(originalImage);

        for (String transformation : data.getTransformations()) {
            switch (transformation) {
                case "Rotation 90°":
                    currentImage = new Rotation90("Rotation 90°").transform(currentImage);
                    break;
                case "Rotation 180°":
                    currentImage = new Rotation180("Rotation 180°").transform(currentImage);
                    break;
                case "Rotation -90°":
                    currentImage = new Rotation270("Rotation -90°").transform(currentImage);
                    break;
                case "Symétrie horizontale":
                    currentImage = new FlipH("Symétrie horizontale").transform(currentImage);
                    break;
                case "Symétrie verticale":
                    currentImage = new FlipV("Symétrie verticale").transform(currentImage);
                    break;
                case "Sépia":
                    currentImage = new SepiaFilter().apply(currentImage);
                    break;
                case "Noir & Blanc":
                    currentImage = new GrayscaleFilter().apply(currentImage);
                    break;
                case "Échange RGB → GBR":
                    currentImage = new RGBSwapFilter().apply(currentImage);
                    break;
                case "Prewitt (contours)":
                    currentImage = new PrewittFilter().apply(currentImage);
                    break;
            }
        }

        imageView.setImage(currentImage);
    }

    public void reloadSavedImage(ImageData data) {
        Image img = new Image(new File(data.getPath()).toURI().toString());
        originalImage = toWritable(img);
        currentImage  = toWritable(img);
        imageView.setImage(currentImage);
        statusBar.setText("Image chargée : " + data.getName()
                + "  (" + (int) img.getWidth() + " × " + (int) img.getHeight() + " px)");
        //imageData= new ImageData(data.getPath());
        imageData = data;
        reloadTransformations(data);
    }


    private WritableImage toWritable(Image src) {
        int w = (int) src.getWidth(), h = (int) src.getHeight();
        WritableImage wi = new WritableImage(w, h);
        wi.getPixelWriter().setPixels(0, 0, w, h,
                src.getPixelReader(), 0, 0);
        return wi;
    }

    @FXML
    private void setTag() {
        if (currentImage == null) {
            statusBar.setText("Aucune image chargée. Impossible d'y associer un tag");
            return;
        }
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Ajouter un tag");
        dialog.setHeaderText("Ajout d'un tag");
        dialog.setContentText("Entrez un tag :");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            String tag = result.get().trim().toLowerCase();
            if (!tag.isEmpty()) {
                imageData.addTags(tag);
                statusBar.setText("Tag : " + tag + " ajouté !");
            }
        }
    }
    @FXML
    private void save() {
        if (currentImage == null) {
            statusBar.setText("Aucune image chargée. Impossible de l'enregistrer");
            return;
        }

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Enregistrer une image");
        dialog.setHeaderText("Nom de l'image");
        dialog.setContentText("Entrez un nom : ");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            String name = result.get().trim().toLowerCase();
            if (!name.isEmpty()) {
                imageData.setName(name);
                SaveFxImage.saveFxImage(originalImage , name);
                SaveData.saveData(imageData);
                statusBar.setText("Image enregistrée sous le nom " + name + ".");
            }
        }
    }
    @FXML
    private void encrypt() {
        if (originalImage == null) {
            statusBar.setText("Aucune image chargée. Impossible de la chiffrer");
            return;
        }
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Chiffrer l'image");
        dialog.setHeaderText("Mot de passe");
        dialog.setContentText("Entrez le mot de passe pour chiffrer : ");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            String password = result.get().trim().toLowerCase();
            if (!password.isEmpty()) {
                try {
                    BufferedImage image = Security.encryptImage(toBufferedImage(originalImage), password);
                    originalImage = SwingFXUtils.toFXImage(image, null);
                    imageView.setImage(originalImage);
                    statusBar.setText("Image chiffrée");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static BufferedImage toBufferedImage(WritableImage fxImage) {
        return SwingFXUtils.fromFXImage(fxImage, null);
    }

    @FXML
    private void decrypt() {
        if (originalImage == null) {
            statusBar.setText("Aucune image chargée. Impossible de la déchiffrer");
            return;
        }
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Déchiffrer l'image");
        dialog.setHeaderText("Mot de passe");
        dialog.setContentText("Entrez le mot de passe pour déchiffrer : ");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            String password = result.get().trim().toLowerCase();
            if (!password.isEmpty()) {
                try {
                    BufferedImage image = Security.decryptImage(toBufferedImage(originalImage), password);

                    WritableImage decrypted = SwingFXUtils.toFXImage(image, null);
                    imageView.setImage(decrypted);

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Mot de passe");
                    alert.setHeaderText(null);
                    alert.setContentText("Etait ce le bon mot de passe ? ");

                    Optional<ButtonType> result2 = alert.showAndWait();
                    boolean isOk = result2.isPresent() && result2.get() == ButtonType.OK;
                    if (isOk) {
                        originalImage = toWritable(decrypted);
                        currentImage = toWritable(originalImage);
                        System.out.println(imageData.getTransformations());
                        imageView.setImage(currentImage);

                        reloadTransformations(imageData);
                        statusBar.setText("Image déchiffrée");
                    } else {
                        imageView.setImage(originalImage);
                        statusBar.setText("Annulation du déchiffrement");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

