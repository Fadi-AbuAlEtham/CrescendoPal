package com.example.crescendopal.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crescendopal.InstrumentRecyclerAdapter;
import com.example.crescendopal.R;
import com.example.crescendopal.data.Instrument;
import com.example.crescendopal.storage.InstrumentStorage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InstrumentsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private InstrumentRecyclerAdapter adapter;
    private List<Instrument> instrumentList = new ArrayList<>();
    private List<Instrument> filteredList = new ArrayList<>();
    private SearchView searchView;
    private FloatingActionButton btnAddInst;
    private ImageButton btnCart;
    private Spinner spinnerType, spinnerCondition;
    private Switch switchRentOnly;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_instruments);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        btnAddInst = findViewById(R.id.btnAddInst);
        btnCart = findViewById(R.id.btnCart);
        spinnerType = findViewById(R.id.spinnerType);
        spinnerCondition = findViewById(R.id.spinnerCondition);
        switchRentOnly = findViewById(R.id.switchRentOnly);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fill with data
        loadSavedInstruments();

        ArrayAdapter<String> conditionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                Arrays.asList("All", "New", "Used"));
        conditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCondition.setAdapter(conditionAdapter);

        setupFilters();


        // Initialize adapter with normal mode
        adapter = new InstrumentRecyclerAdapter(this, filteredList, InstrumentRecyclerAdapter.MODE_NORMAL);
        recyclerView.setAdapter(adapter);

        setupSearchFilter();

        btnAddInst.setOnClickListener(v -> {
            Intent intent = new Intent(InstrumentsActivity.this, AddInstrumentActivity.class);
            startActivityForResult(intent, 101);
        });

        btnCart.setOnClickListener(v -> {
            Intent intent = new Intent(InstrumentsActivity.this, CartActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {

            Instrument newInstrument = new Instrument();
            newInstrument.setId(data.getStringExtra("id"));
            newInstrument.setName(data.getStringExtra("name"));
            newInstrument.setDescription(data.getStringExtra("description"));
            newInstrument.setType(data.getStringExtra("type"));
            newInstrument.setCondition(data.getStringExtra("condition"));
            newInstrument.setPrice(data.getDoubleExtra("price", 0));
            newInstrument.setSellerName(data.getStringExtra("seller"));
            newInstrument.setForRent(data.getBooleanExtra("rent", false));
            newInstrument.setAvailable(data.getBooleanExtra("available", true));

            String imageUriStr = data.getStringExtra("imageUri");
            if (imageUriStr != null) {
                newInstrument.setImageUri(Uri.parse(imageUriStr));
            } else {
                newInstrument.setImageResId(R.drawable.ic_instrument); // fallback
            }

            // Add to both lists
            instrumentList.add(newInstrument);
            filteredList.add(newInstrument);
            adapter.notifyItemInserted(filteredList.size() - 1);
            recyclerView.scrollToPosition(filteredList.size() - 1); // scroll to new item
            InstrumentStorage.saveInstruments(this, instrumentList);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSavedInstruments();
        applyAllFilters();
    }

    public List<Instrument> getInstrumentList() {
        return instrumentList;
    }

    public void setInstrumentList(List<Instrument> instrumentList) {
        this.instrumentList = instrumentList;
    }

    public List<Instrument> getFilteredList() {
        return filteredList;
    }

    private void loadSavedInstruments() {
        instrumentList = InstrumentStorage.loadInstruments(this);
        if (instrumentList == null || instrumentList.isEmpty()) {
            instrumentList.add(new Instrument(
                    "1", "Yamaha P-125 Digital Piano", "Piano", "New",
                    599.99, false, "Alice Music Store",
                    R.drawable.ic_instrument, true, "88-key compact digital piano.", null
            ));

            instrumentList.add(new Instrument(
                    "2", "Fender Stratocaster", "Guitar", "Used",
                    450.00, true, "Bob’s Guitars",
                    R.drawable.ic_instrument, true, "Classic electric guitar, great tone.", null
            ));

            instrumentList.add(new Instrument(
                    "3", "Pearl Roadshow Drum Kit", "Drums", "New",
                    749.50, false, "BeatMasters",
                    R.drawable.ic_instrument, true, "5-piece beginner-friendly drum kit.", null
            ));

            instrumentList.add(new Instrument(
                    "4", "Korg EK-50 Arranger Keyboard", "Keyboard", "Used",
                    399.99, true, "Sami Music Gear",
                    R.drawable.ic_instrument, true, "Great for performers and composers.", null
            ));

            instrumentList.add(new Instrument(
                    "5", "Oud Arabic Luth", "Oud", "New",
                    299.00, false, "Oriental Sound Shop",
                    R.drawable.ic_instrument, true, "Traditional oriental instrument.", null
            ));

            instrumentList.add(new Instrument(
                    "6", "Darabuka Drum", "Percussion", "Used",
                    89.99, true, "Rami’s Rhythms",
                    R.drawable.ic_instrument, true, "Egyptian-style darbuka drum.", null
            ));

            instrumentList.add(new Instrument(
                    "7", "Violin 4/4 Beginner Model", "Violin", "New",
                    120.00, false, "Classic Strings",
                    R.drawable.ic_instrument, true, "Perfect for students and practice.", null
            ));

            InstrumentStorage.saveInstruments(this, instrumentList);
        }

        filteredList.clear();
        filteredList.addAll(instrumentList);
    }

    private void setupSearchFilter() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override public boolean onQueryTextChange(String newText) {
                applyAllFilters();
                return true;
            }
        });
    }

    private void setupFilters() {
        // Trigger filter when spinners or switch change
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                applyAllFilters();
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinnerCondition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                applyAllFilters();
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        switchRentOnly.setOnCheckedChangeListener((buttonView, isChecked) -> applyAllFilters());
    }

    private void applyAllFilters() {
        String selectedType = spinnerType.getSelectedItem().toString();
        String selectedCondition = spinnerCondition.getSelectedItem().toString();
        boolean rentOnly = switchRentOnly.isChecked();
        String searchText = searchView.getQuery().toString().toLowerCase();

        filteredList.clear();

        for (Instrument i : instrumentList) {
            boolean matchesType = selectedType.equals("All") || i.getType().equalsIgnoreCase(selectedType);
            boolean matchesCondition = selectedCondition.equals("All") || i.getCondition().equalsIgnoreCase(selectedCondition);
            boolean matchesRent = !rentOnly || i.isForRent();
            boolean matchesSearch = i.getName().toLowerCase().contains(searchText);

            if (matchesType && matchesCondition && matchesRent && matchesSearch) {
                filteredList.add(i);
            }
        }

        adapter.notifyDataSetChanged();
    }
}
