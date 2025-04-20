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

public class CartStorage {
    private static final String PREFS_NAME = "CartPrefs";
    private static final String CART_INSTRUMENT_KEY = "cartInstruments";
    private static final String CART_BOOK_KEY = "cartBooks";

    public static void saveCartInstruments(Context context, List<Instrument> cart) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(CART_INSTRUMENT_KEY, new Gson().toJson(cart)).apply();
    }

    public static List<Instrument> loadCartInstruments(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(CART_INSTRUMENT_KEY, null);
        Type type = new TypeToken<List<Instrument>>() {}.getType();
        return json == null ? new ArrayList<>() : new Gson().fromJson(json, type);
    }

    public static void saveCartBooks(Context context, List<Book> books) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(CART_BOOK_KEY, new Gson().toJson(books)).apply();
    }

    public static List<Book> loadCartBooks(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(CART_BOOK_KEY, null);
        Type type = new TypeToken<List<Book>>() {}.getType();
        return json == null ? new ArrayList<>() : new Gson().fromJson(json, type);
    }

    public static void clearCart(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().remove(CART_INSTRUMENT_KEY).remove(CART_BOOK_KEY).apply();
    }

    public static void addToBoughtInstruments(Context context, List<Instrument> newItems) {
        List<Instrument> current = loadCartInstruments(context);
        current.addAll(newItems);
        saveCartInstruments(context, current);
    }

    public static void addToBoughtBooks(Context context, List<Book> newItems) {
        List<Book> current = loadCartBooks(context);
        current.addAll(newItems);
        saveCartBooks(context, current);
    }

}