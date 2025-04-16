package com.example.crescendopal.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.crescendopal.data.Instrument;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

// BoughtStorage.java
public class BoughtStorage {
    private static final String PREFS_NAME = "BoughtPrefs";
    private static final String BOUGHT_KEY = "boughtList";

    public static void save(Context context, List<Instrument> list) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(BOUGHT_KEY, new Gson().toJson(list)).apply();
    }

    public static List<Instrument> load(Context context) {
        String json = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getString(BOUGHT_KEY, null);
        Type type = new TypeToken<List<Instrument>>() {}.getType();
        return json == null ? new ArrayList<>() : new Gson().fromJson(json, type);
    }

    public static void add(Context context, Instrument instrument) {
        List<Instrument> list = load(context);
        list.add(instrument);
        save(context, list);
    }
}

