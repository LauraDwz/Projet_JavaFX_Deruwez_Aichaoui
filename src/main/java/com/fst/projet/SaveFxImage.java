package com.fst.projet;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;


public class SaveFxImage {

    public static void saveFxImage(Image image, String name, String path) {
        try {
            BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
            File file = new File(path);
            ImageIO.write(bImage, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}