package com.example.crescendopal.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crescendopal.CombinedRecyclerAdapter;
import com.example.crescendopal.InstrumentRecyclerAdapter;
import com.example.crescendopal.R;
import com.example.crescendopal.data.Book;
import com.example.crescendopal.data.Instrument;
import com.example.crescendopal.storage.BoughtStorage;
import com.example.crescendopal.storage.RentedStorage;

import java.util.ArrayList;
import java.util.List;

public class MyRentedInstrumentsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CombinedRecyclerAdapter combinedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);

        recyclerView = findViewById(R.id.recyclerViewMyList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Object> combinedList = new ArrayList<>();
        combinedList.addAll(RentedStorage.loadInstruments(this));
        combinedList.addAll(RentedStorage.loadBooks(this));

        combinedAdapter = new CombinedRecyclerAdapter(this, combinedList, CombinedRecyclerAdapter.MODE_MYHUB);
        recyclerView.setAdapter(combinedAdapter);
    }
}