package com.example.crescendopal;

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

import com.example.crescendopal.activities.BookActivity;
import com.example.crescendopal.activities.BookDetailsActivity;
import com.example.crescendopal.activities.CartActivity;
import com.example.crescendopal.activities.InstrumentDetailsActivity;
import com.example.crescendopal.activities.InstrumentsActivity;
import com.example.crescendopal.data.Book;
import com.example.crescendopal.data.Instrument;
import com.example.crescendopal.storage.BookStorage;
import com.example.crescendopal.storage.CartStorage;
import com.example.crescendopal.storage.InstrumentStorage;

import java.util.List;

public class CombinedRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int MODE_NORMAL = 0;
    public static final int MODE_CART = 1;
    public static final int MODE_MYHUB = 2;

    private static final int TYPE_INSTRUMENT = 0;
    private static final int TYPE_BOOK = 1;

    private final Context context;
    private final List<Object> itemList;
    private final int mode;

    public CombinedRecyclerAdapter(Context context, List<Object> items, int mode) {
        this.context = context;
        this.itemList = items;
        this.mode = mode;
    }

    @Override
    public int getItemViewType(int position) {
        if (itemList.get(position) instanceof Instrument) return TYPE_INSTRUMENT;
        else return TYPE_BOOK;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        if (viewType == TYPE_INSTRUMENT) {
            View view = inflater.inflate(R.layout.instrument_item, parent, false);
            return new InstrumentViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.book_item, parent, false);
            return new BookViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Object item = itemList.get(position);

        if (holder instanceof InstrumentViewHolder) {
            Instrument instrument = (Instrument) item;
            InstrumentViewHolder ivh = (InstrumentViewHolder) holder;

            ivh.instrumentName.setText(instrument.getName());
            ivh.instrumentPrice.setText("$" + instrument.getPrice());
            ivh.instrumentDesc.setText(instrument.getDescription());
            ivh.sellerName.setText("Sold by: " + instrument.getSellerName());

            if (instrument.getImageUri() != null) {
                ivh.instrumentImage.setImageURI(instrument.getImageUri());
            } else {
                ivh.instrumentImage.setImageResource(instrument.getImageResId());
            }
            if (mode == MODE_CART) {
                ivh.btnAddToCart.setImageResource(R.drawable.ic_remove_item);
                ivh.btnAddToCart.setOnClickListener(v -> {
                    CartManager.removeInstrument(context, instrument);
                    itemList.remove(position);
                    notifyItemRemoved(position);
                    InstrumentStorage.addInstrumentBack(context, instrument);
                    Toast.makeText(context, "Removed from cart", Toast.LENGTH_SHORT).show();
                    if (context instanceof CartActivity) {
                        ((CartActivity) context).updateTotalPrice();
                    }
                });
                ivh.itemView.setOnClickListener(null);
            } else if (mode == MODE_MYHUB) {
                ivh.btnAddToCart.setVisibility(View.GONE);

            } else {
                ivh.btnAddToCart.setImageResource(R.drawable.ic_cart);
                ivh.btnAddToCart.setOnClickListener(v -> {
                    CartManager.addInstrument(context, instrument);
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

            ivh.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, InstrumentDetailsActivity.class);
                intent.putExtra("id", instrument.getId());
                intent.putExtra("name", instrument.getName());
                intent.putExtra("description", instrument.getDescription());
                intent.putExtra("price", instrument.getPrice());
                intent.putExtra("seller", instrument.getSellerName());
                intent.putExtra("image", instrument.getImageResId());
                if (instrument.getImageUri() != null)
                    intent.putExtra("imageUri", instrument.getImageUri().toString());
                intent.putExtra("available", instrument.isAvailable());
                intent.putExtra("rent", instrument.isForRent());
                intent.putExtra("source", "myhub");
                context.startActivity(intent);
            });

        } else if (holder instanceof BookViewHolder) {
            Book book = (Book) item;
            BookViewHolder bvh = (BookViewHolder) holder;

            bvh.txtTitle.setText(book.getTitle());
            bvh.txtInstrument.setText("For: " + book.getInstrument());
            bvh.txtDifficulty.setText("Level: " + book.getDifficulty());
            bvh.txtUploader.setText("Uploaded by: " + book.getUploaderName());
            bvh.txtPrice.setText("$" + book.getPrice());
            bvh.txtQty.setText("Qty: " + book.getQuantity());
            bvh.imgBook.setImageResource(book.getImageResId());

            if (book.getImageUri() != null) {
                bvh.imgBook.setImageURI(book.getImageUri());
            } else {
                bvh.imgBook.setImageResource(book.getImageResId());
            }

            if (mode == MODE_CART) {
                bvh.btnAddToCart.setImageResource(R.drawable.ic_remove_item);
                bvh.txtQty.setVisibility(View.GONE);
                bvh.btnAddToCart.setOnClickListener(v -> {
                    CartManager.removeBook(context, book);
                    itemList.remove(position);
                    notifyItemRemoved(position);
                    BookStorage.addBookBack(context, book);
                    Toast.makeText(context, "Removed from cart", Toast.LENGTH_SHORT).show();
                    if (context instanceof CartActivity) {
                        ((CartActivity) context).updateTotalPrice();
                    }
                });
                bvh.itemView.setOnClickListener(null);
            } else if (mode == MODE_MYHUB) {
                bvh.btnAddToCart.setVisibility(View.GONE);
                bvh.txtQty.setVisibility(View.GONE);
            } else {
                bvh.btnAddToCart.setImageResource(R.drawable.ic_cart);
                bvh.btnAddToCart.setOnClickListener(v -> {
                    CartManager.addBook(context, book);
                    if (context instanceof BookActivity) {
                        BookActivity activity = (BookActivity) context;
                        activity.getBookList().remove(book);
                        activity.getBookList().remove(position);
                        BookStorage.saveBooks(context, activity.getBookList());
                    }
                    notifyItemRemoved(position);
                    Toast.makeText(context, book.getTitle() + " added to cart", Toast.LENGTH_SHORT).show();
                });
            }

            bvh.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, BookDetailsActivity.class);
                intent.putExtra("id", book.getId());
                intent.putExtra("title", book.getTitle());
                intent.putExtra("instrument", book.getInstrument());
                intent.putExtra("difficulty", book.getDifficulty());
                intent.putExtra("isDownloadable", book.isDownloadable());
                intent.putExtra("price", book.getPrice());
                intent.putExtra("uploader", book.getUploaderName());
                intent.putExtra("quantity", book.getQuantity());
                intent.putExtra("imageResId", book.getImageResId());
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class InstrumentViewHolder extends RecyclerView.ViewHolder {
        ImageView instrumentImage;
        TextView instrumentName, instrumentPrice, instrumentDesc, sellerName;
        ImageButton btnAddToCart;

        public InstrumentViewHolder(@NonNull View itemView) {
            super(itemView);
            instrumentImage = itemView.findViewById(R.id.imgInst);
            instrumentName = itemView.findViewById(R.id.txtInstName);
            instrumentPrice = itemView.findViewById(R.id.txtInstPrice);
            instrumentDesc = itemView.findViewById(R.id.txtInstDesc);
            sellerName = itemView.findViewById(R.id.txtSeller);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBook;
        TextView txtTitle, txtInstrument, txtDifficulty, txtUploader, txtPrice, txtQty;
        ImageButton btnAddToCart;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBook = itemView.findViewById(R.id.imgBook);
            txtTitle = itemView.findViewById(R.id.txtBookTitle);
            txtInstrument = itemView.findViewById(R.id.txtBookInstrument);
            txtDifficulty = itemView.findViewById(R.id.txtBookDifficulty);
            txtUploader = itemView.findViewById(R.id.txtUploader);
            txtPrice = itemView.findViewById(R.id.txtBookPrice);
            txtQty = itemView.findViewById(R.id.txtBookQty);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }
    }

    public void updateData(List<Object> newList) {
        itemList.clear();
        itemList.addAll(newList);
        notifyDataSetChanged();
    }
}