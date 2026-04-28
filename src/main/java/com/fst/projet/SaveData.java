package com.fst.projet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SaveData {

    public static void saveData(ImageData imageData) {
        ObjectMapper objectMapper = new ObjectMapper();
        File metadata = new File("src/main/resources/com/fst/projet/data.json");

        try {
            List<ImageData> liste;
            if (metadata.exists() && metadata.length() > 0) {
                liste = objectMapper.readValue(metadata, new TypeReference<List<ImageData>>() {});
            } else {
                liste = new ArrayList<>();
            }
            liste.add(imageData);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(metadata, liste);
            System.out.println("Metadata sauvegardée");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}