package com.example.crescendopal.data;

import androidx.annotation.NonNull;

import java.util.List;

public class Student {
    private String id;
    private String name;
    private String email;
    private String phone;
    private List<String> bookedTeachers; // List of teacher IDs
    private List<String> bookedInstruments; // Optional
    private List<String> favoriteBooks; // Optional

    public Student() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getBookedTeachers() {
        return bookedTeachers;
    }

    public void setBookedTeachers(List<String> bookedTeachers) {
        this.bookedTeachers = bookedTeachers;
    }

    public List<String> getBookedInstruments() {
        return bookedInstruments;
    }

    public void setBookedInstruments(List<String> bookedInstruments) {
        this.bookedInstruments = bookedInstruments;
    }

    public List<String> getFavoriteBooks() {
        return favoriteBooks;
    }

    public void setFavoriteBooks(List<String> favoriteBooks) {
        this.favoriteBooks = favoriteBooks;
    }

    @NonNull
    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", bookedTeachers=" + bookedTeachers +
                ", bookedInstruments=" + bookedInstruments +
                ", favoriteBooks=" + favoriteBooks +
                '}';
    }
}
