package com.example.crescendopal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crescendopal.data.Book;
import com.example.crescendopal.storage.BookStorage;

import java.util.List;

public class BookRecyclerAdapter extends RecyclerView.Adapter<BookRecyclerAdapter.ViewHolder> {

    private final Context context;
    private final List<Book> bookList;

    public BookRecyclerAdapter(Context context, List<Book> list) {
        this.context = context;
        this.bookList = list;
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
    public BookRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookRecyclerAdapter.ViewHolder holder, int position) {
        Book book = bookList.get(position);

        holder.txtTitle.setText(book.getTitle());
        holder.txtInstrument.setText("For: " + book.getInstrument());
        holder.txtDifficulty.setText("Level: " + book.getDifficulty());
        holder.txtUploader.setText("Uploaded by: " + book.getUploaderName());
        holder.txtPrice.setText("$" + book.getPrice());
        holder.txtQty.setText("Qty: " + book.getQuantity());
        holder.imgBook.setImageResource(book.getImageResId());

        if (book.getQuantity() <= 0) {
            holder.btnAddToCart.setEnabled(false);
            holder.txtQty.setText("Out of stock");
        }

        holder.btnAddToCart.setOnClickListener(v -> {
            if (book.getQuantity() > 0) {
                book.setQuantity(book.getQuantity() - 1);
                holder.txtQty.setText("Qty: " + book.getQuantity());
                Toast.makeText(context, book.getTitle() + " added to cart", Toast.LENGTH_SHORT).show();

                BookStorage.saveBooks(context, bookList);

                if (book.getQuantity() == 0) {
                    holder.btnAddToCart.setEnabled(false);
                    holder.txtQty.setText("Out of stock");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }
}