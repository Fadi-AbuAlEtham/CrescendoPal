package com.example.crescendopal.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crescendopal.InstrumentRecyclerAdapter;
import com.example.crescendopal.R;
import com.example.crescendopal.storage.RentedStorage;
import com.example.crescendopal.data.Instrument;

import java.util.List;

public class MyRentedInstrumentsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    InstrumentRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);

        recyclerView = findViewById(R.id.recyclerViewMyList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Instrument> rentedList = RentedStorage.load(this);
        adapter = new InstrumentRecyclerAdapter(this, rentedList, InstrumentRecyclerAdapter.MODE_MYHUB);
        recyclerView.setAdapter(adapter);
    }
}