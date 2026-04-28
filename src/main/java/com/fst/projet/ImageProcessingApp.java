package com.fst.projet;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.IOException;

public class ImageProcessingApp extends Application{
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ImageProcessingApp.class.getResource("image-app.fxml"));
        stage.setTitle("Traitement d'image");
        Scene scene = new Scene(fxmlLoader.load(), 860, 650);
        stage.setScene(scene);
        stage.show();
    }
}

