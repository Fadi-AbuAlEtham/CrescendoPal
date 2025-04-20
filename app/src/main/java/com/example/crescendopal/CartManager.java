package com.example.crescendopal;

import android.content.Context;

import com.example.crescendopal.data.Book;
import com.example.crescendopal.data.Instrument;
import com.example.crescendopal.storage.CartStorage;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static final List<Instrument> cartInstruments = new ArrayList<>();
    private static final List<Book> cartBooks = new ArrayList<>();

    public static void addInstrument(Context context, Instrument instrument) {
        cartInstruments.add(instrument);
        CartStorage.saveCartInstruments(context, cartInstruments);
    }

    public static void addBook(Context context, Book book) {
        cartBooks.add(book);
        CartStorage.saveCartBooks(context, cartBooks);
    }

    public static void removeInstrument(Context context, Instrument instrument) {
        cartInstruments.remove(instrument);
        CartStorage.saveCartInstruments(context, cartInstruments);
    }

    public static void removeBook(Context context, Book book) {
        cartBooks.remove(book);
        CartStorage.saveCartBooks(context, cartBooks);
    }

    public static List<Instrument> getCartInstruments(Context context) {
        if (cartInstruments.isEmpty()) {
            cartInstruments.addAll(CartStorage.loadCartInstruments(context));
        }
        return cartInstruments;
    }

    public static List<Book> getCartBooks(Context context) {
        if (cartBooks.isEmpty()) {
            cartBooks.addAll(CartStorage.loadCartBooks(context));
        }
        return cartBooks;
    }

    public static void clearCart(Context context) {
        cartInstruments.clear();
        cartBooks.clear();
        CartStorage.clearCart(context);
    }

    public static void setCartInstruments(List<Instrument> list) {
        cartInstruments.clear();
        cartInstruments.addAll(list);
    }

    public static void setCartBooks(List<Book> list) {
        cartBooks.clear();
        cartBooks.addAll(list);
    }

    public static String getTotalPrice(Context context) {
        double total = 0.0;
        for (Instrument i : getCartInstruments(context)) total += i.getPrice();
        for (Book b : getCartBooks(context)) total += b.getPrice();
        return String.format("%.2f", total);
    }

    public static String getTotalPrice() {
        double total = 0.0;
        for (Instrument i : cartInstruments) total += i.getPrice();
        for (Book b : cartBooks) total += b.getPrice();
        return String.format("%.2f", total);
    }
}