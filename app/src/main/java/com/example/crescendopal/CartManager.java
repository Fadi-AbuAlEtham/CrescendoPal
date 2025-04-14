package com.example.crescendopal;

import android.content.Context;

import com.example.crescendopal.data.Instrument;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static final List<Instrument> cartList = new ArrayList<>();

    public static void addToCart(Context context, Instrument instrument) {
        cartList.add(instrument);
        CartStorage.saveCart(context, cartList); // also save to SharedPreferences
    }

    public static void removeFromCart(Context context, Instrument instrument) {
        cartList.remove(instrument);
        CartStorage.saveCart(context, cartList);
    }

    public static List<Instrument> getCartList(Context context) {
        if (cartList.isEmpty()) {
            cartList.addAll(CartStorage.loadCart(context)); // load from SharedPreferences on first use
        }
        return cartList;
    }

    public static void clearCart(Context context) {
        cartList.clear();
        CartStorage.clearCart(context);
    }

    public static void setCartList(List<Instrument> newList) {
        cartList.clear();
        cartList.addAll(newList);
    }
}

