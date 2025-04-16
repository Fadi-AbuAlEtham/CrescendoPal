package com.example.crescendopal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.crescendopal.R;

public class MyHubActivity extends AppCompatActivity {
    Button btnViewBought, btnViewRented;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_hub);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnViewBought = findViewById(R.id.btnViewBought);
        btnViewRented = findViewById(R.id.btnViewRented);

        btnViewBought.setOnClickListener(v ->
                startActivity(new Intent(MyHubActivity.this, MyBoughtInstrumentsActivity.class)));

        btnViewRented.setOnClickListener(v ->
                startActivity(new Intent(MyHubActivity.this, MyRentedInstrumentsActivity.class)));
    }
}