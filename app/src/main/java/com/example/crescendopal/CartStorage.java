package com.example.crescendopal;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.crescendopal.data.Instrument;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CartStorage {
    private static final String PREFS_NAME = "CartPrefs";
    private static final String CART_KEY = "cartList";

    public static void saveCart(Context context, List<Instrument> cart) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        String json = new Gson().toJson(cart);
        editor.putString(CART_KEY, json);
        editor.apply();
    }

    public static List<Instrument> loadCart(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(CART_KEY, null);
        if (json == null) return new ArrayList<>();
        Type type = new TypeToken<List<Instrument>>() {}.getType();
        return new Gson().fromJson(json, type);
    }

    public static void clearCart(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().remove(CART_KEY).apply();
    }
}
