package com.example.crescendopal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crescendopal.data.Instrument;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class InstrumentsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private InstrumentRecyclerAdapter adapter;
    private List<Instrument> instrumentList = new ArrayList<>();
    private List<Instrument> filteredList = new ArrayList<>();
    private SearchView searchView;
    private FloatingActionButton btnAddInst;
    private ImageButton btnCart;

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

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fill with data
        loadSavedInstruments();

        // Initialize adapter with normal mode
        adapter = new InstrumentRecyclerAdapter(this, filteredList, InstrumentRecyclerAdapter.MODE_NORMAL);
        recyclerView.setAdapter(adapter);

        setupSearchFilter();

        btnAddInst.setOnClickListener(v -> {
            Intent intent = new Intent(InstrumentsActivity.this, AddInstrument.class);
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

    private void loadSavedInstruments() {
        instrumentList = InstrumentStorage.loadInstruments(this);
        filteredList.clear();
        filteredList.addAll(instrumentList);
    }

    private void setupSearchFilter() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false; // we handle it on text change
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filteredList.clear();
                for (Instrument i : instrumentList) {
                    if (i.getName().toLowerCase().contains(newText.toLowerCase())) {
                        filteredList.add(i);
                    }
                }
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }
}
