package com.example.crescendopal.data;

import androidx.annotation.NonNull;

public class Instrument {
    private String id;
    private String name;
    private String type; // e.g., "Keyboard", "Guitar"
    private String condition; // "New" or "Used"
    private double price;
    private boolean isForRent;
    private String sellerName;
    private int imageResId;
    private boolean isAvailable;

    public Instrument() {
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
                '}';
    }
}
