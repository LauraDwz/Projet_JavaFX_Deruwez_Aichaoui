package com.fst.projet;

import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Security {
    public static BufferedImage encryptImage(BufferedImage img, String password)
            throws NoSuchAlgorithmException {

        int width = img.getWidth();
        int height = img.getHeight();

        int[] pixels = new int[width * height];

        int index = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[index++] = img.getRGB(x, y);
            }
        }

        int[] perm = generatePermutation(pixels.length, password);

        int[] shuffled = new int[pixels.length];

        for (int i = 0; i < pixels.length; i++) {
            shuffled[i] = pixels[perm[i]];
        }

        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        index = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                result.setRGB(x, y, shuffled[index++]);
            }
        }

        return result;
    }

    public static BufferedImage decryptImage(BufferedImage img, String password)
            throws NoSuchAlgorithmException {

        int width = img.getWidth();
        int height = img.getHeight();

        int[] pixels = new int[width * height];

        int index = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[index++] = img.getRGB(x, y);
            }
        }

        int[] perm = generatePermutation(pixels.length, password);

        int[] original = new int[pixels.length];

        for (int i = 0; i < pixels.length; i++) {
            original[perm[i]] = pixels[i];
        }

        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        index = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                result.setRGB(x, y, original[index++]);
            }
        }

        return result;
    }

    private static int[] generatePermutation(int size, String password)
            throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] seed = md.digest(password.getBytes(StandardCharsets.UTF_8));

        SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
        rnd.setSeed(seed);

        int[] perm = new int[size];

        for (int i = 0; i < size; i++) {
            perm[i] = i;
        }

        for (int i = size - 1; i > 0; i--) {
            int j = rnd.nextInt(i + 1);

            int tmp = perm[i];
            perm[i] = perm[j];
            perm[j] = tmp;
        }

        return perm;
    }
}
