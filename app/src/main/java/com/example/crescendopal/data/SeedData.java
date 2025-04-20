package com.example.crescendopal.data;

import android.content.Context;

import com.example.crescendopal.R;
import com.example.crescendopal.storage.BookStorage;
import com.example.crescendopal.storage.InstrumentStorage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SeedData {

    public static void seedInstrumentsIfEmpty(Context context) {
        if (InstrumentStorage.loadInstruments(context).isEmpty()) {
            String json = loadJSONFromAsset(context, "InstrumentsData.json");
            if (json != null) {
                Type type = new TypeToken<List<Instrument>>() {}.getType();
                List<Instrument> instrumentList = new Gson().fromJson(json, type);
                InstrumentStorage.saveInstruments(context, instrumentList);
            }
        }
    }

    public static void seedBooksIfEmpty(Context context) {
        List<Book> current = BookStorage.loadBooks(context);
        if (current == null || current.isEmpty()) {
            BookStorage.saveBooks(context, getSampleBooks());
        }
    }


    private static String loadJSONFromAsset(Context context, String fileName) {
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static List<Book> getSampleBooks() {
        List<Book> books = new ArrayList<>();

        books.add(new Book("b1", "Piano for Beginners", "Piano", "Beginner", true, 19.99, R.drawable.ic_book, "Harmony Press", 15, null));
        books.add(new Book("b2", "Advanced Guitar Tabs", "Guitar", "Advanced", false, 24.50, R.drawable.ic_book, "Strum & Co", 10, null));
        books.add(new Book("b3", "Violin Essentials Vol. 1", "Violin", "Intermediate", true, 14.75, R.drawable.ic_book, "Melody Studio", 8, null));
        books.add(new Book("b4", "Jazz Piano Chords", "Piano", "Advanced", false, 29.90, R.drawable.ic_sheet_music, "Jazzify", 6, null));
        books.add(new Book("b5", "Classical Sheet Music - Bach", "Piano", "Advanced", true, 12.00, R.drawable.ic_sheet_music, "Classical Notes", 12, null));
        books.add(new Book("b6", "Drumming 101", "Drums", "Beginner", true, 17.25, R.drawable.ic_book, "Beat Basics", 20, null));
        books.add(new Book("b7", "Flute Melodies Vol. 2", "Flute", "Intermediate", false, 16.00, R.drawable.ic_sheet_music, "Wind Music", 9, null));
        books.add(new Book("b8", "Oud Improvisation Guide", "Oud", "Advanced", false, 21.99, R.drawable.ic_book, "Oriental Sound", 7, null));
        books.add(new Book("b9", "Guitar Sheet Music - Pop Hits", "Guitar", "Intermediate", true, 18.75, R.drawable.ic_sheet_music, "Chordify", 5, null));
        books.add(new Book("b10", "Violin Duets for Students", "Violin", "Beginner", true, 15.00, R.drawable.ic_book, "String Harmony", 11, null));

        return books;
    }

}
