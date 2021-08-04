package com.example.marketplace.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.marketplace.MenuActivity;
import com.example.marketplace.R;
import com.example.marketplace.model.Product;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> implements  View.OnClickListener{

    ArrayList<Product> products;
    Context context;
    DecimalFormat formater = new DecimalFormat("###,###.##");
    private View.OnClickListener listener;
    private ProductAdapter.HomeEvents homeEvents;
    private boolean showFavorite;
    private int cant=0;
    private int posAnt=-1;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public ProductAdapter(ArrayList<Product> products, Context context) {
        this.products = products;
        this.context = context;
        showFavorite = true;
    }
    public ProductAdapter(ArrayList<Product> products, Context context, boolean showFavorite) {
        this.products = products;
        this.context = context;
        this.showFavorite = showFavorite;
    }

    @NonNull
    @NotNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductAdapter.ViewHolder holder, int position) {
        holder.favorite.setOnClickListener(null);
        holder.favorite.setVisibility(showFavorite ? View.VISIBLE : View.INVISIBLE);
        holder.favorite.setImageDrawable(products.get(position).isFavorite() ? context.getDrawable(R.drawable.ic_favorite) : context.getDrawable(R.drawable.ic_favorite_border));
        holder.name.setText(products.get(position).getName());
        holder.description.setText(products.get(position).getDescription());
        holder.price.setText("$" + formater.format(Integer.parseInt(products.get(position).getPrice())));
        Glide.with(context).load(products.get(position).getUrl()).into(holder.image);
        holder.favorite.setOnClickListener(v -> {
            products.get(position).setFavorite(!products.get(position).isFavorite());
            holder.favorite.setImageDrawable(products.get(position).isFavorite() ? context.getDrawable(R.drawable.ic_favorite) : context.getDrawable(R.drawable.ic_favorite_border));
            if (homeEvents != null)
                homeEvents.onClickFavorite(products.get(position), position);
        });
        holder.addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(posAnt!=position){
                    cant=1;
                    posAnt=position;
                    addProductCart(products,position,cant);
                }else{
                    cant++;
                    addProductCart(products,position,cant);
                }
            }
        });
    }

    private void addProductCart(ArrayList<Product> products, int position, int cant) {
        Integer price = Integer.parseInt(products.get(position).getPrice());
        Map<String, Object> product = new HashMap<>();
        product.put("name", products.get(position).getName());
        product.put("price", String.valueOf(cant*price));
        product.put("count", String.valueOf(cant));
        product.put("cantIni", products.get(position).getQuantity());
        product.put("id", products.get(position).getId());


        db.collection("shopping")
                .document(String.valueOf(position))
                .set(product)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, R.string.msg_addProduct, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setOnClickListener (View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, description, price;
        private ImageView image;
        private ImageButton favorite;
        private ImageView addCart;
        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.nameCart);
            description = v.findViewById(R.id.descriptionCart);
            price = v.findViewById(R.id.priceCart);
            image = v.findViewById(R.id.imageCart);
            favorite = v.findViewById(R.id.favoriteButton);
            addCart = v.findViewById(R.id.addCart);
        }
    }

    public void setListenerFavorite(HomeEvents homeEvents){
        this.homeEvents = homeEvents;
    }

    public interface HomeEvents{
        void onClickFavorite(Product product, int position );
    }

}
