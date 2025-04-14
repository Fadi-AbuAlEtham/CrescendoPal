package com.example.crescendopal;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class InstrumentDetailsActivity extends AppCompatActivity {
    private ImageView detailImage;
    private TextView detailName, detailDescription, detailPrice, detailSeller, detailAvailability;
    private Button btnRent, btnBuy;
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

        // Get intent data
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String name = extras.getString("name", "Unknown Instrument");
            String description = extras.getString("description", "");
            double price = extras.getDouble("price", 0.0);
            String seller = extras.getString("seller", "Unknown");
            boolean isAvailable = extras.getBoolean("available", true);
            boolean isForRent = extras.getBoolean("rent", false);
            String imageUriStr = extras.getString("imageUri");


            // Set values
            detailName.setText(name);
            detailDescription.setText(description);
            detailPrice.setText("$" + String.format("%.2f", price));
            detailSeller.setText("Sold by: " + seller);

            // Set image (from URI or fallback drawable)
            if (imageUriStr != null) {
                Uri imageUri = Uri.parse(imageUriStr);
                detailImage.setImageURI(imageUri);
            } else {
                int imageRes = extras.getInt("image", R.drawable.ic_instrument);
                detailImage.setImageResource(imageRes);
            }

            // Set availability + buttons
            if (!isAvailable) {
                detailAvailability.setText("Currently Unavailable");
                btnRent.setEnabled(false);
                btnBuy.setEnabled(false);
            } else {
                detailAvailability.setText(isForRent ? "Available for Rent" : "Available for Sale");
                btnRent.setText(isForRent ? "Rent Now" : "Buy Now");
                btnBuy.setText("Buy Now");
            }
        }
    }
}