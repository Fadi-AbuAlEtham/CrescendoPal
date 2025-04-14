package com.example.crescendopal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crescendopal.data.Instrument;

import java.util.List;

public class InstrumentRecyclerAdapter extends RecyclerView.Adapter<InstrumentRecyclerAdapter.ViewHolder> {

    public static final int MODE_NORMAL = 0;
    public static final int MODE_CART = 1;
    private final Context context;
    private final List<Instrument> instrumentList;
    private final int adapterMode;

    public InstrumentRecyclerAdapter(Context context, List<Instrument> list, int mode) {
        this.context = context;
        this.instrumentList = list;
        this.adapterMode = mode;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView instrumentImage;
        TextView instrumentName, instrumentPrice, instrumentDesc, sellerName;
        ImageButton btnAddToCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            instrumentImage = itemView.findViewById(R.id.imgInst);
            instrumentName = itemView.findViewById(R.id.txtInstName);
            instrumentPrice = itemView.findViewById(R.id.txtInstPrice);
            instrumentDesc = itemView.findViewById(R.id.txtInstDesc);
            sellerName = itemView.findViewById(R.id.txtSeller);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }
    }

    @NonNull
    @Override
    public InstrumentRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.instrument_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InstrumentRecyclerAdapter.ViewHolder holder, int position) {
        Instrument instrument = instrumentList.get(position);
        holder.instrumentName.setText(instrument.getName());
        holder.instrumentPrice.setText("$" + instrument.getPrice());
        holder.instrumentDesc.setText(instrument.getDescription());
        holder.sellerName.setText("Sold by: " + instrument.getSellerName());

        // Set image
        if (instrument.getImageUri() != null) {
            holder.instrumentImage.setImageURI(instrument.getImageUri());
        } else {
            holder.instrumentImage.setImageResource(instrument.getImageResId());
        }

        // Dynamic behavior based on mode
        if (adapterMode == MODE_CART) {
            holder.btnAddToCart.setImageResource(R.drawable.ic_remove_item);
            holder.btnAddToCart.setOnClickListener(v -> {
                CartManager.removeFromCart(context, instrument);
                instrumentList.remove(position);
                notifyItemRemoved(position);

                // Return item to main inventory
                InstrumentStorage.addInstrumentBack(context, instrument);

                Toast.makeText(context, "Removed from cart", Toast.LENGTH_SHORT).show();
            });

            // Prevent going to details screen when in cart
            holder.itemView.setOnClickListener(null);
        } else {
            holder.btnAddToCart.setImageResource(R.drawable.ic_cart);
            holder.btnAddToCart.setOnClickListener(v -> {
                CartManager.addToCart(context, instrument);

                if (context instanceof InstrumentsActivity) {
                    InstrumentsActivity activity = (InstrumentsActivity) context;

                    activity.getInstrumentList().remove(instrument);
                    activity.getFilteredList().remove(position);

                    InstrumentStorage.saveInstruments(context, activity.getInstrumentList());
                }

                notifyItemRemoved(position);
                Toast.makeText(context, instrument.getName() + " added to cart", Toast.LENGTH_SHORT).show();
            });
        }

        // Item click → Details
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, InstrumentDetailsActivity.class);
            intent.putExtra("name", instrument.getName());
            intent.putExtra("description", instrument.getDescription());
            intent.putExtra("price", instrument.getPrice());
            intent.putExtra("seller", instrument.getSellerName());
            intent.putExtra("image", instrument.getImageResId());
            if (instrument.getImageUri() != null)
                intent.putExtra("imageUri", instrument.getImageUri().toString());
            intent.putExtra("available", instrument.isAvailable());
            intent.putExtra("rent", instrument.isForRent());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return instrumentList.size();
    }
}
