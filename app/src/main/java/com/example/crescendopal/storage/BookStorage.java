package com.example.crescendopal.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.crescendopal.data.Book;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BookStorage {
    private static final String PREFS_NAME = "BookPrefs";
    private static final String BOOK_LIST_KEY = "bookList";

    public static void saveBooks(Context context, List<Book> books) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = new Gson().toJson(books);
        prefs.edit().putString(BOOK_LIST_KEY, json).apply();
    }

    public static List<Book> loadBooks(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(BOOK_LIST_KEY, null);

        if (json == null) return new ArrayList<>();

        Type type = new TypeToken<List<Book>>() {}.getType();
        return new Gson().fromJson(json, type);
    }

    public static void updateBookQuantity(Context context, String bookId, int quantityPurchased) {
        List<Book> books = loadBooks(context);
        for (Book book : books) {
            if (book.getId().equals(bookId)) {
                int newQty = book.getQuantity() - quantityPurchased;
                book.setQuantity(Math.max(0, newQty));
                break;
            }
        }
        saveBooks(context, books);
    }

    public static void addBook(Context context, Book book) {
        List<Book> books = loadBooks(context);
        books.add(book);
        saveBooks(context, books);
    }
}
