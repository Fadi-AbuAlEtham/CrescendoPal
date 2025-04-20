package com.example.crescendopal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.crescendopal.R;

public class DashboardActivity extends AppCompatActivity {
    CardView instrumentCard, bookCard, myHubCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        instrumentCard = findViewById(R.id.instrumentCard);
        bookCard = findViewById(R.id.booksCard);
        myHubCard = findViewById(R.id.myHubCard);

        instrumentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, InstrumentsActivity.class);
                startActivity(intent);
            }
        });

        bookCard.setOnClickListener(v -> {
            startActivity(new Intent(this, BookActivity.class));
        });

        myHubCard.setOnClickListener(v -> {
            startActivity(new Intent(this, MyHubActivity.class));
        });
    }
}