package com.example.crescendopal.data;

import android.net.Uri;

import androidx.annotation.NonNull;

public class Instrument {
    private String id;
    private String name;
    private String type; // e.g., "Keyboard", "Guitar"
    private String condition; // "New" or "Used"
    private double price;
    private String description;
    private boolean isForRent;
    private String sellerName;
    private int imageResId;
    private boolean isAvailable;
    private Uri imageUri;

    public Instrument() {
    }

    public Instrument(String id, String name, String type, String condition, double price, boolean isForRent,
                      String sellerName, int imageResId, boolean isAvailable, String description, Uri imageUri) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.condition = condition;
        this.price = price;
        this.isForRent = isForRent;
        this.sellerName = sellerName;
        this.imageResId = imageResId;
        this.isAvailable = isAvailable;
        this.description = description;
        this.imageUri = imageUri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isForRent() {
        return isForRent;
    }

    public void setForRent(boolean forRent) {
        isForRent = forRent;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    @NonNull
    @Override
    public String toString() {
        return "Instrument{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", condition='" + condition + '\'' +
                ", price=" + price +
                ", isForRent=" + isForRent +
                ", sellerName='" + sellerName + '\'' +
                ", imageResId=" + imageResId +
                ", isAvailable=" + isAvailable +
                ", description='" + description + '\'' +
                ", imageUri=" + imageUri +
                '}';
    }
}
