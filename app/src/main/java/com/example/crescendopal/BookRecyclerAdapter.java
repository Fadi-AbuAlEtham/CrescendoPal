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

import com.example.crescendopal.activities.BookDetailsActivity;
import com.example.crescendopal.activities.CartActivity;
import com.example.crescendopal.data.Book;
import com.example.crescendopal.storage.BookStorage;

import java.util.List;

public class BookRecyclerAdapter extends RecyclerView.Adapter<BookRecyclerAdapter.ViewHolder> {

    public static final int MODE_NORMAL = 0;
    public static final int MODE_CART = 1;
    public static final int MODE_MYHUB = 2;
    private final Context context;
    private final List<Book> bookList;
    private final int adapterMode;

    public BookRecyclerAdapter(Context context, List<Book> list, int mode) {
        this.context = context;
        this.bookList = list;
        this.adapterMode = mode;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBook;
        TextView txtTitle, txtInstrument, txtDifficulty, txtUploader, txtPrice, txtQty;
        ImageButton btnAddToCart;

        public ViewHolder(@NonNull View itemView) {
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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = bookList.get(position);

        holder.txtTitle.setText(book.getTitle());
        holder.txtInstrument.setText("For: " + book.getInstrument());
        holder.txtDifficulty.setText("Level: " + book.getDifficulty());
        holder.txtUploader.setText("Uploaded by: " + book.getUploaderName());
        holder.txtPrice.setText("$" + book.getPrice());
        holder.txtQty.setText("Qty: " + book.getQuantity());

        if (book.getImageUri() != null) {
            holder.imgBook.setImageURI(book.getImageUri());
        } else {
            holder.imgBook.setImageResource(book.getImageResId());
        }

        if (book.getQuantity() <= 0 && adapterMode == MODE_NORMAL) {
            holder.btnAddToCart.setEnabled(false);
            holder.txtQty.setText("Out of stock");
        }

        if (adapterMode == MODE_CART) {
            holder.btnAddToCart.setImageResource(R.drawable.ic_remove_item);
            holder.txtQty.setVisibility(View.GONE);

            holder.btnAddToCart.setOnClickListener(v -> {
                CartManager.removeBook(context, book);
                bookList.remove(position);
                notifyItemRemoved(position);

                BookStorage.addBookBack(context, book);
                Toast.makeText(context, "Removed from cart", Toast.LENGTH_SHORT).show();
                if (context instanceof CartActivity) {
                    ((CartActivity) context).updateTotalPrice();
                }
            });

            holder.itemView.setOnClickListener(null);

        } else if (adapterMode == MODE_MYHUB) {
            holder.btnAddToCart.setVisibility(View.GONE);

        } else {
            holder.btnAddToCart.setImageResource(R.drawable.ic_cart);
            holder.btnAddToCart.setOnClickListener(v -> {
                if (book.getQuantity() > 0) {
                    book.setQuantity(book.getQuantity() - 1);
                    CartManager.addBook(context, book);
                    BookStorage.saveBooks(context, bookList);

                    Toast.makeText(context, book.getTitle() + " added to cart", Toast.LENGTH_SHORT).show();

                    if (book.getQuantity() == 0) {
                        bookList.remove(position);
                        notifyItemRemoved(position);
                    } else {
                        notifyItemChanged(position);
                    }
                }
            });

            holder.itemView.setOnClickListener(v -> {
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
                if (book.getImageUri() != null) {
                    intent.putExtra("imageUri", book.getImageUri().toString());
                }
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }
}