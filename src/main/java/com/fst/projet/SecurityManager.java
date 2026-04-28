package com.fst.projet;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;

import java.awt.image.BufferedImage;

public class SecurityManager {

    public WritableImage encrypt(WritableImage image, String password) throws Exception {
        BufferedImage buffered = ImageFunctions.toBufferedImage(image);
        BufferedImage encrypted = Security.encryptImage(buffered, password);
        return ImageFunctions.toFXImage(encrypted);
    }

    public WritableImage decrypt(WritableImage image, String password) throws Exception {
        BufferedImage buffered = ImageFunctions.toBufferedImage(image);
        BufferedImage decrypted = Security.decryptImage(buffered, password);
        return ImageFunctions.toFXImage(decrypted);
    }
}