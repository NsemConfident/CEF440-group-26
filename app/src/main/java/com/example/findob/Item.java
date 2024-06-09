package com.example.findob;

public class Item {
    private String title;
    private String description;
    private int imageResId; // Assuming you are using local images, you can modify this to use URLs if necessary

    public Item(String title, String description, int imageResId) {
        this.title = title;
        this.description = description;
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResId() {
        return imageResId;
    }
}

