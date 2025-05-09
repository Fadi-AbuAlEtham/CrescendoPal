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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crescendopal.BookRecyclerAdapter;
import com.example.crescendopal.R;
import com.example.crescendopal.data.Book;
import com.example.crescendopal.data.SeedData;
import com.example.crescendopal.storage.BookStorage;
import com.example.crescendopal.storage.InstrumentStorage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookRecyclerAdapter adapter;
    private List<Book> bookList = new ArrayList<>();
    private List<Book> filteredList = new ArrayList<>();
    private SearchView searchView;
    private FloatingActionButton btnAddBook;
    private ImageButton btnCart;
    private Spinner spinnerInstrument, spinnerDifficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_books);

//        SeedData.seedBooksIfEmpty(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.bookRecyclerView);
        searchView = findViewById(R.id.searchView);
        btnAddBook = findViewById(R.id.btnAddBook);
        btnCart = findViewById(R.id.btnCart);
        spinnerInstrument = findViewById(R.id.spinnerInstrument);
        spinnerDifficulty = findViewById(R.id.spinnerDifficulty);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        SeedData.seedInstrumentsIfEmpty(this);
        loadSavedBooks();

        ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                Arrays.asList("All", "Beginner", "Intermediate", "Advanced"));
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDifficulty.setAdapter(difficultyAdapter);

        setupFilters();

        adapter = new BookRecyclerAdapter(this, filteredList, BookRecyclerAdapter.MODE_NORMAL);
        recyclerView.setAdapter(adapter);

        setupSearchFilter();

        btnAddBook.setOnClickListener(v -> {
            Intent intent = new Intent(BookActivity.this, AddBookActivity.class);
            startActivityForResult(intent, 102);
        });

        btnCart.setOnClickListener(v -> {
            Intent intent = new Intent(BookActivity.this, CartActivity.class);
            startActivity(intent);
        });
    }

    private void loadSavedBooks() {
        bookList = BookStorage.loadBooks(this);
        if(bookList.isEmpty()) {
            bookList.add(new Book("b1", "Piano for Beginners", "Piano", "Beginner", true, 19.99, R.drawable.ic_book, "Harmony Press", 15, null));
            bookList.add(new Book("b2", "Advanced Guitar Tabs", "Guitar", "Advanced", false, 24.50, R.drawable.ic_book, "Strum & Co", 10, null));
            bookList.add(new Book("b3", "Violin Essentials Vol. 1", "Violin", "Intermediate", true, 14.75, R.drawable.ic_book, "Melody Studio", 8, null));
            bookList.add(new Book("b4", "Jazz Piano Chords", "Piano", "Advanced", false, 29.90, R.drawable.ic_sheet_music, "Jazzify", 6, null));
            bookList.add(new Book("b5", "Classical Sheet Music - Bach", "Piano", "Advanced", true, 12.00, R.drawable.ic_sheet_music, "Classical Notes", 12, null));
            bookList.add(new Book("b6", "Drumming 101", "Drums", "Beginner", true, 17.25, R.drawable.ic_book, "Beat Basics", 20, null));
            bookList.add(new Book("b7", "Flute Melodies Vol. 2", "Flute", "Intermediate", false, 16.00, R.drawable.ic_sheet_music, "Wind Music", 9, null));
            bookList.add(new Book("b8", "Oud Improvisation Guide", "Oud", "Advanced", false, 21.99, R.drawable.ic_book, "Oriental Sound", 7, null));
            bookList.add(new Book("b9", "Guitar Sheet Music - Pop Hits", "Guitar", "Intermediate", true, 18.75, R.drawable.ic_sheet_music, "Chordify", 5, null));
            bookList.add(new Book("b10", "Violin Duets for Students", "Violin", "Beginner", true, 15.00, R.drawable.ic_book, "String Harmony", 11, null));
            BookStorage.saveBooks(this, bookList);
        }
        filteredList.clear();
        filteredList.addAll(bookList);
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
        spinnerInstrument.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                applyAllFilters();
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinnerDifficulty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                applyAllFilters();
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void applyAllFilters() {
        String selectedInstrument = spinnerInstrument.getSelectedItem().toString();
        String selectedDifficulty = spinnerDifficulty.getSelectedItem().toString();
        String searchText = searchView.getQuery().toString().toLowerCase();

        filteredList.clear();

        for (Book b : bookList) {
            boolean matchesInstrument = selectedInstrument.equals("All") || b.getInstrument().equalsIgnoreCase(selectedInstrument);
            boolean matchesDifficulty = selectedDifficulty.equals("All") || b.getDifficulty().equalsIgnoreCase(selectedDifficulty);
            boolean matchesSearch = b.getTitle().toLowerCase().contains(searchText);

            if (matchesInstrument && matchesDifficulty && matchesSearch) {
                filteredList.add(b);
            }
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSavedBooks();
        applyAllFilters();
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public List<Book> getFilteredList() {
        return filteredList;
    }

    public void setFilteredList(List<Book> filteredList) {
        this.filteredList = filteredList;
    }
}
