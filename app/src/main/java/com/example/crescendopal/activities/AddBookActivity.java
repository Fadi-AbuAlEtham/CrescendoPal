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

public class AddBookActivity extends AppCompatActivity {

    private EditText editTitle, editPrice, editUploader, editQuantity;
    private Spinner spinnerInstrument, spinnerDifficulty;
    private Switch switchDownloadable;
    private ImageView imagePreview;
    private Button btnAddBook;
    private Uri imageUri;
    private static final int PICK_IMAGE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_book);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Bind views
        editTitle = findViewById(R.id.editTitle);
        editPrice = findViewById(R.id.editPrice);
        editUploader = findViewById(R.id.editUploader);
        editQuantity = findViewById(R.id.editQuantity);
        spinnerInstrument = findViewById(R.id.spinnerInstrument);
        spinnerDifficulty = findViewById(R.id.spinnerDifficulty);
        switchDownloadable = findViewById(R.id.switchDownloadable);
        imagePreview = findViewById(R.id.imagePreview);
        btnAddBook = findViewById(R.id.btnAddBook);

        // Spinner data
        ArrayAdapter<String> instrumentAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.instrument_types));
        instrumentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerInstrument.setAdapter(instrumentAdapter);

        ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Beginner", "Intermediate", "Advanced"});
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDifficulty.setAdapter(difficultyAdapter);

        // Image picker
        imagePreview.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE);
        });

        // Add button click
        btnAddBook.setOnClickListener(v -> {
            String title = editTitle.getText().toString().trim();
            String uploader = editUploader.getText().toString().trim();
            String priceStr = editPrice.getText().toString().trim();
            String quantityStr = editQuantity.getText().toString().trim();
            String instrument = spinnerInstrument.getSelectedItem().toString();
            String difficulty = spinnerDifficulty.getSelectedItem().toString();
            boolean isDownloadable = switchDownloadable.isChecked();

            if (title.isEmpty() || uploader.isEmpty() || priceStr.isEmpty() || quantityStr.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            double price = Double.parseDouble(priceStr);
            int quantity = Integer.parseInt(quantityStr);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("id", "book_" + System.currentTimeMillis());
            resultIntent.putExtra("title", title);
            resultIntent.putExtra("instrument", instrument);
            resultIntent.putExtra("difficulty", difficulty);
            resultIntent.putExtra("downloadable", isDownloadable);
            resultIntent.putExtra("price", price);
            resultIntent.putExtra("uploader", uploader);
            resultIntent.putExtra("quantity", quantity);
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
