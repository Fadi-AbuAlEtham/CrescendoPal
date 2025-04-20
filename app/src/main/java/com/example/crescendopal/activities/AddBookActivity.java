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
import com.example.crescendopal.data.Book;
import com.example.crescendopal.storage.BookStorage;

import java.util.List;

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

        editTitle = findViewById(R.id.editTitle);
        editPrice = findViewById(R.id.editPrice);
        editUploader = findViewById(R.id.editUploader);
        editQuantity = findViewById(R.id.editQuantity);
        spinnerInstrument = findViewById(R.id.spinnerInstrument);
        spinnerDifficulty = findViewById(R.id.spinnerDifficulty);
        switchDownloadable = findViewById(R.id.switchDownloadable);
        imagePreview = findViewById(R.id.imagePreview);
        btnAddBook = findViewById(R.id.btnAddBook);

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

        imagePreview.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE);
        });

        btnAddBook.setOnClickListener(v -> {
            String title = editTitle.getText().toString().trim();
            String uploader = editUploader.getText().toString().trim();
            String priceStr = editPrice.getText().toString().trim();
            String quantityStr = editQuantity.getText().toString().trim();
            String instrument = spinnerInstrument.getSelectedItem().toString();
            String difficulty = spinnerDifficulty.getSelectedItem().toString();
            boolean isDownloadable = switchDownloadable.isChecked();

            if (title.length() < 3) {
                Toast.makeText(this, "Title must be at least 3 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            if (uploader.length() < 3) {
                Toast.makeText(this, "Uploader name must be at least 3 characters", Toast.LENGTH_SHORT).show();
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

            int quantity;
            try {
                quantity = Integer.parseInt(quantityStr);
                if (quantity <= 0) {
                    Toast.makeText(this, "Quantity must be a positive number", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid quantity format", Toast.LENGTH_SHORT).show();
                return;
            }

            if (instrument.equals("Select Instrument")) {
                Toast.makeText(this, "Please select an instrument", Toast.LENGTH_SHORT).show();
                return;
            }

            if (difficulty.isEmpty()) {
                Toast.makeText(this, "Please select a difficulty level", Toast.LENGTH_SHORT).show();
                return;
            }

            if (imageUri == null) {
                Toast.makeText(this, "Consider selecting a cover image", Toast.LENGTH_SHORT).show();
            }

            Book newBook = new Book(
                    "book_" + System.currentTimeMillis(),
                    title,
                    instrument,
                    difficulty,
                    isDownloadable,
                    price,
                    R.drawable.ic_book,
                    uploader,
                    quantity,
                    imageUri
            );

            List<Book> books = BookStorage.loadBooks(this);
            books.add(newBook);
            BookStorage.saveBooks(this, books);

            Toast.makeText(this, "Book added!", Toast.LENGTH_SHORT).show();
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
