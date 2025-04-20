package com.example.crescendopal.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crescendopal.CartManager;
import com.example.crescendopal.CombinedRecyclerAdapter;
import com.example.crescendopal.R;
import com.example.crescendopal.data.Book;
import com.example.crescendopal.data.Instrument;
import com.example.crescendopal.storage.BoughtStorage;
import com.example.crescendopal.storage.CartStorage;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView cartRecyclerView;
    private CombinedRecyclerAdapter adapter;
    private Button btnCheckout;
    private TextView txtTotal;

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

        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        btnCheckout = findViewById(R.id.btnCheckout);
        txtTotal = findViewById(R.id.txtTotal);

        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        CartManager.setCartInstruments(CartStorage.loadCartInstruments(this));
        CartManager.setCartBooks(CartStorage.loadCartBooks(this));

        List<Object> cartItems = new ArrayList<>();
        cartItems.addAll(CartManager.getCartInstruments(this));
        cartItems.addAll(CartManager.getCartBooks(this));
        System.out.println(cartItems);

        adapter = new CombinedRecyclerAdapter(this, cartItems, CombinedRecyclerAdapter.MODE_CART);
        cartRecyclerView.setAdapter(adapter);

        txtTotal.setText("Total: $" + CartManager.getTotalPrice(this));

        btnCheckout.setOnClickListener(v -> {
            if (CartManager.getCartInstruments(this).isEmpty() && CartManager.getCartBooks(this).isEmpty()) {
                Toast.makeText(this, "Your cart is empty!", Toast.LENGTH_SHORT).show();
                return;
            }
            for (Instrument instrument : CartManager.getCartInstruments(this)) {
                BoughtStorage.addInstrument(this, instrument);
            }
            for (Book book : CartManager.getCartBooks(this)) {
                BoughtStorage.addBook(this, book);
            }

            CartManager.clearCart(this);
            adapter.updateData(new ArrayList<>());

            txtTotal.setText("Total: $0.00");
            Toast.makeText(this, "Order placed!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        CartManager.setCartInstruments(CartStorage.loadCartInstruments(this));
        CartManager.setCartBooks(CartStorage.loadCartBooks(this));

        List<Object> refreshedCart = new ArrayList<>();
        refreshedCart.addAll(CartManager.getCartInstruments(this));
        refreshedCart.addAll(CartManager.getCartBooks(this));

        if (adapter == null) {
            adapter = new CombinedRecyclerAdapter(this, refreshedCart, CombinedRecyclerAdapter.MODE_CART);
            cartRecyclerView.setAdapter(adapter);
        } else {
            adapter.updateData(refreshedCart);
        }

        txtTotal.setText("Total: $" + CartManager.getTotalPrice(this));
    }

    public void updateTotalPrice() {
        txtTotal.setText("Total: $" + CartManager.getTotalPrice());
    }
}