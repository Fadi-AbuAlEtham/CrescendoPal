package com.example.crescendopal.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.crescendopal.data.Book;
import com.example.crescendopal.data.Instrument;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RentedStorage {
    private static final String PREFS_NAME = "RentedPrefs";
    private static final String INSTRUMENT_KEY = "rentedInstruments";
    private static final String BOOK_KEY = "rentedBooks";

    // --------- Instruments ---------
    public static void saveInstruments(Context context, List<Instrument> list) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(INSTRUMENT_KEY, new Gson().toJson(list)).apply();
    }

    public static List<Instrument> loadInstruments(Context context) {
        String json = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getString(INSTRUMENT_KEY, null);
        Type type = new TypeToken<List<Instrument>>() {}.getType();
        return json == null ? new ArrayList<>() : new Gson().fromJson(json, type);
    }

    public static void addInstrument(Context context, Instrument instrument) {
        List<Instrument> list = loadInstruments(context);
        list.add(instrument);
        saveInstruments(context, list);
    }

    // --------- Books ---------
    public static void saveBooks(Context context, List<Book> list) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(BOOK_KEY, new Gson().toJson(list)).apply();
    }

    public static List<Book> loadBooks(Context context) {
        String json = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getString(BOOK_KEY, null);
        Type type = new TypeToken<List<Book>>() {}.getType();
        return json == null ? new ArrayList<>() : new Gson().fromJson(json, type);
    }

    public static void addBook(Context context, Book book) {
        List<Book> list = loadBooks(context);
        list.add(book);
        saveBooks(context, list);
    }
}