package com.example.lostandfound;

// ImageItem.java
public class ImageItem {
    private String imageUrl;
    private String title;
    private String date;
    private String posterName;
    private String description;
    private String status;

    // Constructors, getters and setters
    public ImageItem(String imageUrl, String title, String date, String posterName, String description, String status) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.date = date;
        this.posterName = posterName;
        this.description = description;
        this.status = status;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getPosterName() {
        return posterName;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }
}
