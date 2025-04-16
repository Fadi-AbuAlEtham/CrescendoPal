package com.example.crescendopal.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.crescendopal.data.Instrument;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class InstrumentStorage {
    private static final String PREFS_NAME = "InstrumentPrefs";
    private static final String KEY_LIST = "instrumentList";

    public static void saveInstruments(Context context, List<Instrument> list) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(KEY_LIST, json);
        editor.apply();
    }

    public static List<Instrument> loadInstruments(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_LIST, null);

        if (json == null) {
            return new ArrayList<>();
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<Instrument>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public static void addInstrumentBack(Context context, Instrument instrument) {
        List<Instrument> currentList = loadInstruments(context);

        // Prevent duplicates
        for (Instrument i : currentList) {
            if (i.getId().equals(instrument.getId()))
                return;
        }

        currentList.add(instrument);
        saveInstruments(context, currentList);
    }

}
