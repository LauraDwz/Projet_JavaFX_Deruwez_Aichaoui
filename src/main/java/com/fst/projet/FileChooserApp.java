
package com.fst.projet;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class FileChooserApp extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("File Chooser");

        Label label = new Label("Aucun fichier sélectionné.");

        Button btnOpen = new Button("Ouvrir un fichier…");
        Button btnSave = new Button("Enregistrer sous…");

        FileChooser fileChooser = new FileChooser();

        btnOpen.setOnAction(e -> {
            fileChooser.setTitle("Ouvrir un fichier");
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                label.setText(file.getAbsolutePath());
            }
        });

        btnSave.setOnAction(e -> {
            fileChooser.setTitle("Enregistrer sous");
            File file = fileChooser.showSaveDialog(stage);
            if (file != null) {
                label.setText(file.getAbsolutePath());
            }
        });

        HBox buttons = new HBox(8, btnOpen, btnSave);

        VBox root = new VBox(12, buttons, label);
        root.setPadding(new Insets(20));

        stage.setScene(new Scene(root, 480, 100));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}