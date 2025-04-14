package com.example.crescendopal;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crescendopal.data.Instrument;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView cartRecyclerView;
    private InstrumentRecyclerAdapter adapter;
    private Button btnCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Bind views
        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        btnCheckout = findViewById(R.id.btnCheckout);

        // Setup RecyclerView
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Instrument> freshCart = CartStorage.loadCart(this); // load from SharedPreferences
        CartManager.setCartList(freshCart); // keep static list updated
        adapter = new InstrumentRecyclerAdapter(
                this,
                freshCart,
                InstrumentRecyclerAdapter.MODE_CART
        );
        cartRecyclerView.setAdapter(adapter);


        // Checkout logic
        btnCheckout.setOnClickListener(v -> {
            CartManager.clearCart(this);
            adapter.notifyDataSetChanged();
            if(cartRecyclerView.getAdapter().getItemCount() > 0) {
                Toast.makeText(this, "Order placed!", Toast.LENGTH_SHORT).show();
                onResume();
            }else {
                Toast.makeText(this, "Your cart is empty!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        List<Instrument> refreshedCart = CartStorage.loadCart(this);
        CartManager.setCartList(refreshedCart);

        adapter = new InstrumentRecyclerAdapter(
                this,
                refreshedCart,
                InstrumentRecyclerAdapter.MODE_CART
        );
        cartRecyclerView.setAdapter(adapter);
    }
}