package com.fst.projet;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;


public class SaveFxImage {

    public static void saveFxImage(Image image, String name) {
        try {
            BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
            File file = new File("src/main/resources/com/fst/projet/images/" + name + ".png");
            ImageIO.write(bImage, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}