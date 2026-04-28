package com.fst.projet;

import java.util.List;
import java.util.stream.Collectors;

public class GalleryManager {

    public List<ImageData> loadAll() {
        return SaveData.loadAllData();
    }

    public List<ImageData> filterByTag(List<ImageData> liste, String recherche) {
        if (recherche == null || recherche.isEmpty()) {
            return liste;
        }

        return liste.stream().filter(data -> data.getTags().stream()
                        .anyMatch(tag -> tag.toLowerCase().contains(recherche.toLowerCase())))
                .collect(Collectors.toList());
    }
}