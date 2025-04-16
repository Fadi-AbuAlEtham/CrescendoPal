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

import com.example.crescendopal.R;
import com.example.crescendopal.data.Book;
import com.example.crescendopal.storage.BookStorage;
import com.example.crescendopal.storage.BoughtStorage;

import java.util.List;

public class BookDetailsActivity extends AppCompatActivity {

    private ImageView detailImage;
    private TextView detailTitle, detailInstrument, detailDifficulty, detailPrice, detailUploader, detailQuantity;
    private Button btnBuy;

    private String bookId;
    private int bookQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_details);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        detailImage = findViewById(R.id.bookImage);
        detailTitle = findViewById(R.id.bookTitle);
        detailInstrument = findViewById(R.id.bookInstrument);
        detailDifficulty = findViewById(R.id.bookDifficulty);
        detailPrice = findViewById(R.id.bookPrice);
        detailUploader = findViewById(R.id.bookUploader);
        detailQuantity = findViewById(R.id.bookQuantity);
        btnBuy = findViewById(R.id.btnBuyBook);

        // Load data from intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            bookId = extras.getString("id");
            String title = extras.getString("title", "Untitled");
            String instrument = extras.getString("instrument", "");
            String difficulty = extras.getString("difficulty", "");
            double price = extras.getDouble("price", 0.0);
            String uploader = extras.getString("uploader", "Unknown");
            bookQuantity = extras.getInt("quantity", 1);
            String imageUriStr = extras.getString("imageUri");
            int imageRes = extras.getInt("image", R.drawable.ic_book);

            // Set data to views
            detailTitle.setText(title);
            detailInstrument.setText("Instrument: " + instrument);
            detailDifficulty.setText("Level: " + difficulty);
            detailPrice.setText("$" + String.format("%.2f", price));
            detailUploader.setText("Uploaded by: " + uploader);
            detailQuantity.setText("Available: " + bookQuantity);

            if (imageUriStr != null) {
                Uri imageUri = Uri.parse(imageUriStr);
                detailImage.setImageURI(imageUri);
            } else {
                detailImage.setImageResource(imageRes);
            }

            btnBuy.setEnabled(bookQuantity > 0);
        }

        btnBuy.setOnClickListener(v -> {
            if (bookQuantity <= 0) {
                Toast.makeText(this, "Out of stock", Toast.LENGTH_SHORT).show();
                return;
            }

            // Load and update book list
            List<Book> books = BookStorage.loadBooks(this);
            for (Book book : books) {
                if (book.getId().equals(bookId)) {
                    book.setQuantity(book.getQuantity() - 1);
                    bookQuantity = book.getQuantity();
                    break;
                }
            }

            // Save updated list
            BookStorage.saveBooks(this, books);

            // Add to bought list
            BoughtStorage.addBook(this, getCurrentBook());

            Toast.makeText(this, "Book purchased!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private Book getCurrentBook() {
        return new Book(
                getIntent().getStringExtra("id"),
                getIntent().getStringExtra("title"),
                getIntent().getStringExtra("instrument"),
                getIntent().getStringExtra("difficulty"),
                getIntent().getBooleanExtra("downloadable", false),
                getIntent().getDoubleExtra("price", 0.0),
                getIntent().getIntExtra("image", R.drawable.ic_book),
                getIntent().getStringExtra("uploader"),
                bookQuantity,
                getIntent().hasExtra("imageUri") ? Uri.parse(getIntent().getStringExtra("imageUri")) : null
        );
    }
}