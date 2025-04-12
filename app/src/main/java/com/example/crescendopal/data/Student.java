package com.example.crescendopal.data;

import androidx.annotation.NonNull;

import java.util.List;

public class Student {
    private List<String> bookedTeachers; // List of teacher IDs
    private List<String> bookedInstruments; // Optional
    private List<String> favoriteBooks; // Optional

    public Student() {
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
                ", bookedTeachers=" + bookedTeachers +
                ", bookedInstruments=" + bookedInstruments +
                ", favoriteBooks=" + favoriteBooks +
                '}';
    }
}
