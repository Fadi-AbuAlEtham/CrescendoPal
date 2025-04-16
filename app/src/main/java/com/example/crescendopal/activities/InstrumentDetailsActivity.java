package com.example.crescendopal.activities;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.crescendopal.CartManager;
import com.example.crescendopal.R;
import com.example.crescendopal.data.Instrument;
import com.example.crescendopal.storage.BoughtStorage;
import com.example.crescendopal.storage.InstrumentStorage;
import com.example.crescendopal.storage.RentedStorage;

import java.util.List;

public class InstrumentDetailsActivity extends AppCompatActivity {

    private ImageView detailImage;
    private TextView detailName, detailDescription, detailPrice, detailSeller, detailAvailability, txtAvailability;
    private Button btnRent, btnBuy;

    private String instrumentName;
    private boolean isAvailable = true;
    private boolean isForRent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_instrument_details);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // UI references
        detailImage = findViewById(R.id.detailImage);
        detailName = findViewById(R.id.detailName);
        detailDescription = findViewById(R.id.detailDescription);
        detailPrice = findViewById(R.id.detailPrice);
        detailSeller = findViewById(R.id.detailSeller);
        detailAvailability = findViewById(R.id.detailAvailability);
        btnRent = findViewById(R.id.btnRent);
        btnBuy = findViewById(R.id.btnBuy);
        txtAvailability = findViewById(R.id.txtAvailability);

        // Get intent data
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            instrumentName = extras.getString("name", "Unknown Instrument");
            String description = extras.getString("description", "");
            double price = extras.getDouble("price", 0.0);
            String seller = extras.getString("seller", "Unknown");
            isAvailable = extras.getBoolean("available", true);
            isForRent = extras.getBoolean("rent", false);
            String imageUriStr = extras.getString("imageUri");
            String source = extras.getString("source");

            if ("myhub".equals(source)) {
                btnRent.setVisibility(View.GONE);
                btnBuy.setVisibility(View.GONE);
                detailAvailability.setVisibility(View.GONE);
                txtAvailability.setVisibility(View.GONE);
            }

            // Set values
            detailName.setText(instrumentName);
            detailDescription.setText(description);
            detailPrice.setText("$" + String.format("%.2f", price));
            detailSeller.setText("Sold by: " + seller);

            // Set image
            if (imageUriStr != null) {
                Uri imageUri = Uri.parse(imageUriStr);
                detailImage.setImageURI(imageUri);
            } else {
                int imageRes = extras.getInt("image", R.drawable.ic_instrument);
                detailImage.setImageResource(imageRes);
            }

            // Set availability status
            if (!isAvailable) {
                detailAvailability.setText("Currently Unavailable");
                btnRent.setEnabled(false);
                btnBuy.setEnabled(false);
            } else {
                detailAvailability.setText(isForRent ? "Available for Rent" : "Available for Sale");

                // Button behaviors
                btnRent.setEnabled(isForRent);
                btnBuy.setEnabled(true);

                btnRent.setText("Rent Now");
                btnBuy.setText("Buy Now");
            }
        }

        // Handle Rent button click
        btnRent.setOnClickListener(v -> {
            markAsUnavailable();
            RentedStorage.addInstrument(this, getCurrentInstrument());
            Toast.makeText(this, "Rented successfully!", Toast.LENGTH_SHORT).show();
            removeFromInstruments();
            CartManager.removeFromCart(this, getCurrentInstrument());
            finish();
        });

        // Handle Buy button click
        btnBuy.setOnClickListener(v -> {
            markAsUnavailable();
            BoughtStorage.addInstrument(this, getCurrentInstrument());
            Toast.makeText(this, "Purchased successfully!", Toast.LENGTH_SHORT).show();
            removeFromInstruments();
            CartManager.removeFromCart(this, getCurrentInstrument());
            finish();
        });
    }

    private void markAsUnavailable() {
        List<Instrument> instrumentList = InstrumentStorage.loadInstruments(this);
        for (Instrument instrument : instrumentList) {
            if (instrument.getName().equals(instrumentName)) {
                instrument.setAvailable(false);
                break;
            }
        }
//        InstrumentStorage.saveInstruments(this, instrumentList);
//        finish(); // return to previous screen
    }

    private Instrument getCurrentInstrument() {
        return new Instrument(
                getIntent().getStringExtra("id"),
                getIntent().getStringExtra("name"),
                getIntent().getStringExtra("type"),
                getIntent().getStringExtra("condition"),
                getIntent().getDoubleExtra("price", 0.0),
                getIntent().getBooleanExtra("rent", false),
                getIntent().getStringExtra("seller"),
                getIntent().getIntExtra("image", R.drawable.ic_instrument),
                true,
                getIntent().getStringExtra("description"),
                getIntent().hasExtra("imageUri") ? Uri.parse(getIntent().getStringExtra("imageUri")) : null
        );
    }

    private void removeFromInstruments() {
        String id = getIntent().getStringExtra("id");
        List<Instrument> instrumentList = InstrumentStorage.loadInstruments(this);

        for (int i = 0; i < instrumentList.size(); i++) {
            if (instrumentList.get(i).getId().equals(id)) {
                instrumentList.remove(i);
                break;
            }
        }

        InstrumentStorage.saveInstruments(this, instrumentList);
    }
}