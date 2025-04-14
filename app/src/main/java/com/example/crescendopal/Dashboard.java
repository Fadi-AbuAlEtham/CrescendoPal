package com.example.crescendopal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Dashboard extends AppCompatActivity {
    CardView instrumentCard, classesCard;
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

        // Find the cards
        instrumentCard = findViewById(R.id.instrumentCard);
        classesCard = findViewById(R.id.classesCard);

        // Set click listeners
        instrumentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go to InstrumentsActivity
                Intent intent = new Intent(Dashboard.this, InstrumentsActivity.class);
                startActivity(intent);
            }
        });

        classesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go to ClassesActivity
                Intent intent = new Intent(Dashboard.this, ClassesActivity.class);
                startActivity(intent);
            }
        });
    }
}