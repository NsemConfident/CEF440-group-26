package com.example.lostandfound;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
    private String id;
    private String imageUrl;
    private String name;
    private String status;
    private String time;
    private String description;
    private String address;
    private String posterName;

    public Item() {
        // Default constructor required for calls to DataSnapshot.getValue(Item.class)
    }

    public Item(String id, String imageUrl, String name, String status, String time, String description, String address, String posterName) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.status = status;
        this.time = time;
        this.description = description;
        this.address = address;
        this.posterName = posterName;
    }

    protected Item(Parcel in) {
        id = in.readString();
        imageUrl = in.readString();
        name = in.readString();
        status = in.readString();
        time = in.readString();
        description = in.readString();
        address = in.readString();
        posterName = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public String getPosterName() {
        return posterName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(imageUrl);
        dest.writeString(name);
        dest.writeString(status);
        dest.writeString(time);
        dest.writeString(description);
        dest.writeString(address);
        dest.writeString(posterName);
    }
}
