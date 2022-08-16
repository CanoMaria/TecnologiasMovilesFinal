package com.example.canomariaayelenfinal.ui.recomendadosRV;

public class Items {
    private String title;
    private String description;
    private int image;

    public Items(String title, String description, int image) {
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getImage() {
        return image;
    }
}
