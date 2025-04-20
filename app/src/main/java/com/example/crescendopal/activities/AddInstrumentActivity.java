package com.example.crescendopal.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.*;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.crescendopal.R;

public class AddInstrumentActivity extends AppCompatActivity {

    private EditText editName, editDescription, editPrice, editSeller, editPhone;
    private Spinner spinnerType;
    private RadioGroup radioGroupCondition;
    private Switch switchForRent;
    private Button btnAddInstrument;
    private ImageView imagePreview;
    private Uri imageUri;
    private static final int PICK_IMAGE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_instrument);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editName = findViewById(R.id.editName);
        editDescription = findViewById(R.id.editDescription);
        editPrice = findViewById(R.id.editPrice);
        editSeller = findViewById(R.id.editSeller);
        spinnerType = findViewById(R.id.spinnerType);
        radioGroupCondition = findViewById(R.id.radioGroupCondition);
        switchForRent = findViewById(R.id.switchForRent);
        btnAddInstrument = findViewById(R.id.btnAddInstrument);
        imagePreview = findViewById(R.id.imagePreview);
        editPhone = findViewById(R.id.editTextPhone2);

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{
                        // Western Instruments
                        "Guitar", "Electric Guitar", "Bass Guitar", "Ukulele", "Mandolin",
                        "Piano", "Keyboard", "Digital Piano", "Synthesizer",
                        "Drums", "Snare Drum", "Cymbals", "Percussion", "Cajón",
                        "Violin", "Viola", "Cello", "Double Bass",
                        "Flute", "Clarinet", "Saxophone", "Oboe", "Bassoon",
                        "Trumpet", "Trombone", "French Horn", "Tuba",
                        "Harp", "Accordion", "Harmonica",
                        "Recorder", "Bagpipes", "Banjo",
                        "Tabla", "Sitar", "Darbuka", "Bongo Drums",
                        "Marimba", "Xylophone", "Triangle", "Timpani",
                        "DJ Controller", "Voice / Vocals", "Studio Equipment",

                        // Oriental Instruments
                        "Oud", "Qanun", "Rababa", "Buzuq", "Tanbur", "Santoor", "Kamanjah", "Rebab",
                        "Tabla Baladi", "Riq", "Daf", "Bendir", "Naqareh", "Zills / Sagat",
                        "Ney", "Arghul", "Mizmar", "Zurna", "Kawala", "Duduk",
                        "Tarab Mic", "Mawwal Mic",
                        "Other"});
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(typeAdapter);

        imagePreview.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE);
        });

        btnAddInstrument.setOnClickListener(v -> {
            String name = editName.getText().toString().trim();
            String desc = editDescription.getText().toString().trim();
            String priceStr = editPrice.getText().toString().trim();
            String seller = editSeller.getText().toString().trim();
            String type = spinnerType.getSelectedItem().toString();
            String phone = editPhone.getText().toString().trim();
            boolean isForRent = switchForRent.isChecked();

            if (name.length() < 3) {
                Toast.makeText(this, "Name must be at least 3 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            if (desc.length() < 10) {
                Toast.makeText(this, "Description must be at least 10 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            double price;
            try {
                price = Double.parseDouble(priceStr);
                if (price <= 0) {
                    Toast.makeText(this, "Price must be greater than zero", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid price format", Toast.LENGTH_SHORT).show();
                return;
            }

            if (seller.length() < 2) {
                Toast.makeText(this, "Seller name must be at least 2 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            if (phone.isEmpty() || phone.length() < 6) {
                Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
                return;
            }

            int selectedRadioId = radioGroupCondition.getCheckedRadioButtonId();
            if (selectedRadioId == -1) {
                Toast.makeText(this, "Please select the condition (New or Used)", Toast.LENGTH_SHORT).show();
                return;
            }

            String condition = (selectedRadioId == R.id.radioNew) ? "New" : "Used";

            Intent resultIntent = new Intent();
            resultIntent.putExtra("id", "inst_" + System.currentTimeMillis());
            resultIntent.putExtra("name", name);
            resultIntent.putExtra("description", desc);
            resultIntent.putExtra("price", price);
            resultIntent.putExtra("seller", seller);
            resultIntent.putExtra("type", type);
            resultIntent.putExtra("condition", condition);
            resultIntent.putExtra("rent", isForRent);
            resultIntent.putExtra("available", true);
            resultIntent.putExtra("phone", phone);
            if (imageUri != null) {
                resultIntent.putExtra("imageUri", imageUri.toString());
            }

            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imagePreview.setImageURI(imageUri);
        }
    }
}