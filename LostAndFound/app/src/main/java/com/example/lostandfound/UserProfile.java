package com.example.lostandfound;

public class UserProfile {
    private String userId;
    private String imageUrl;
    private String name;
    private String address;
    private String country;
    private String email;

    public UserProfile() {
        // Default constructor required for calls to DataSnapshot.getValue(UserProfile.class)
    }

    public UserProfile(String userId, String imageUrl, String name, String address, String country, String email) {
        this.userId = userId;
        this.imageUrl = imageUrl;
        this.name = name;
        this.address = address;
        this.country = country;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCountry() {
        return country;
    }

    public String getEmail() {
        return email;
    }
}
