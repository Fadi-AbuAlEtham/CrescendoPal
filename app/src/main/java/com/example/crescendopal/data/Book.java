package com.example.crescendopal.data;

import androidx.annotation.NonNull;

public class Book {
    private String id;
    private String title;
    private String instrument;
    private String difficulty; // e.g., "Beginner", "Intermediate"
    private boolean isDownloadable;
    private double price;
    private int imageResId;
    private String uploaderName;
    private int quantity;

    public Book() {
    }

    public Book(String id, String title, String instrument, String difficulty, boolean isDownloadable, double price, int imageResId, String uploaderName, int quantity) {
        this.id = id;
        this.title = title;
        this.instrument = instrument;
        this.difficulty = difficulty;
        this.isDownloadable = isDownloadable;
        this.price = price;
        this.imageResId = imageResId;
        this.uploaderName = uploaderName;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isDownloadable() {
        return isDownloadable;
    }

    public void setDownloadable(boolean downloadable) {
        isDownloadable = downloadable;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getUploaderName() {
        return uploaderName;
    }

    public void setUploaderName(String uploaderName) {
        this.uploaderName = uploaderName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @NonNull
    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", instrument='" + instrument + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", isDownloadable=" + isDownloadable +
                ", price=" + price +
                ", imageResId=" + imageResId +
                ", uploaderName='" + uploaderName + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}

