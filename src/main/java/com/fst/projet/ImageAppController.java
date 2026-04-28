package com.fst.projet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.util.List;
import java.util.Optional;

public class ImageAppController {
    private final ImageManager imageManager = new ImageManager();
    private final SecurityManager securityManager = new SecurityManager();

    @FXML
    private ImageView imageView;
    @FXML
    private Label statusBar;

    @FXML private Pane centerPane;

    @FXML
    private VBox menuVBox;

    private final List<ImageTransform> transforms = List.of(
            new Rotation90(),
            new Rotation180(),
            new Rotation270(),
            new FlipH(),
            new FlipV()
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
            btn.setOnAction(e ->
                    applyTransform(t));
            menuVBox.getChildren().add(btn);
        }

        menuVBox.getChildren().add(new Separator());
        menuVBox.getChildren().add(new Label("Filtres"));
        for (ImageFilter f : filters) {
            Button btn = new Button(f.getLabel());
            btn.setOnAction(e -> applyFilter(f));
            menuVBox.getChildren().add(btn);
        }
    } //Celle là est parfaite

    private void applyFilter(ImageFilter filter) {
        if (imageManager.noImage()) {
            statusBar.setText("Aucune image chargée.");
            return;
        }
        imageManager.applyFilter(filter);
        imageView.setImage(imageManager.getCurrentImage());
        statusBar.setText("Filtre appliqué : " + filter.getLabel());
    } //Celle là est parfaite

    private void applyTransform(ImageTransform type) {
        if (imageManager.noImage()) {
            statusBar.setText("Aucune image chargée.");
            return;
        }
        imageManager.applyTransform(type);
        imageView.setImage(imageManager.getCurrentImage());
        statusBar.setText("Transformation appliquée : " + type.getLabel());
    } //Celle là est parfaite

    @FXML
    private void loadImage(ActionEvent event) {
        File file = DialogManager.fileChooserImg("Ouvrir une image", event);
        if (file != null) {
            try {
                imageManager.loadImage(file);
                imageView.setImage(imageManager.getCurrentImage());
                statusBar.setText("Image chargée : " + file.getName()
                        + "  (" + (int) imageManager.getCurrentImage().getWidth() + " × " + (int) imageManager.getCurrentImage().getHeight() + " px)");
            } catch (Exception ex) {
                statusBar.setText("Erreur : impossible de charger l'image.");
            }
        }
    } //Celle là est parfaite

    @FXML
    private void resetImage() {
        if (imageManager.noImage()) return;
        imageManager.reset();
        imageView.setImage(imageManager.getCurrentImage());
        statusBar.setText("Image réinitialisée.");
    } //Celle là est parfaite

    @FXML
    private void openImageSaved() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gallery-view.fxml"));
            Scene scene = new Scene(loader.load());
            GalleryController galleryController = loader.getController();
            galleryController.setParentController(this);
            Stage stage = new Stage();
            stage.setTitle("Mes sauvegardes");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            statusBar.setText("Impossible d'ouvrir la galerie.");
            e.printStackTrace();
        }
    } //Celle là est parfaite

    public void reloadSavedImage(ImageData data) {
        imageManager.reloadSavedImage(data);
        imageView.setImage(imageManager.getCurrentImage());
        statusBar.setText("Image chargée : " + data.getName()
                + "  (" + (int) imageManager.getCurrentImage().getWidth() + " × " + (int) imageManager.getCurrentImage().getHeight() + " px)");
    } //Celle là est parfaite

    @FXML
    private void setTag() {
        if (imageManager.noImage()) {
            statusBar.setText("Aucune image chargée. Impossible d'y associer un tag");
            return;
        }

        Optional<String> result = DialogManager.askText("Ajouter un tag", "Ajout d'un tag", "Entrez un tag");

        if (result.isPresent()) {
            String tag = result.get().trim().toLowerCase();
            if (!tag.isEmpty()) {
                imageManager.addTagData(tag);
                statusBar.setText("Tag : " + tag + " ajouté !");
            }
        }
    } //Celle là est parfaite
    @FXML
    private void save() {
        if (imageManager.noImage()) {
            statusBar.setText("Aucune image chargée. Impossible de l'enregistrer");
            return;
        }

        Optional<String> result = DialogManager.askText("Enregistrer une image", "Nom de l'image", "Entrez un nom : ");

        if (result.isPresent()) {
            String name = result.get().trim().toLowerCase();
            if (!name.isEmpty()) {
                imageManager.setNameImageData(name);
                SaveFxImage.saveFxImage(imageManager.getOriginalImage(), name);
                SaveData.saveData(imageManager.getImageData());
                statusBar.setText("Image enregistrée sous le nom " + name + ".");
            }
        }
    } //Celle là est parfaite
    @FXML
    private void encrypt() {
        if (imageManager.noImage()) {
            statusBar.setText("Aucune image chargée. Impossible de la chiffrer");
            return;
        }
        Optional<String> result = DialogManager.askText("Chiffrer l'image", "Mot de passe", "Entrez le mot de passe pour chiffrer : ");
        if (result.isPresent()) {
            String password = result.get().trim().toLowerCase();
            if (!password.isEmpty()) {
                try {
                    imageManager.replaceOriginal(securityManager.encrypt(imageManager.getOriginalImage(), password));
                    imageView.setImage(imageManager.getOriginalImage());
                    statusBar.setText("Image chiffrée");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }  //Celle là est parfaite
    @FXML
    private void decrypt() {
        if (imageManager.noImage()) {
            statusBar.setText("Aucune image chargée. Impossible de la déchiffrer");
            return;
        }

        Optional<String> result = DialogManager.askText("Déchiffrer l'image", "Mot de passe", "Entrez le mot de passe pour déchiffrer : ");

        if (result.isPresent()) {
            String password = result.get().trim().toLowerCase();
            if (!password.isEmpty()) {
                try {
                    WritableImage decrypted = securityManager.decrypt(imageManager.getOriginalImage(), password);
                    imageView.setImage(decrypted);

                    boolean isOk = DialogManager.askConfirmation("Etait ce le bon mot de passe ? ");

                    if (isOk) {
                        imageManager.replaceOriginal(decrypted);
                        imageView.setImage(imageManager.getCurrentImage());
                        imageManager.reloadTransformations();
                        statusBar.setText("Image déchiffrée");
                    } else {
                        imageView.setImage(imageManager.getOriginalImage());
                        statusBar.setText("Annulation du déchiffrement");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    } //Celle là est parfaite
}

