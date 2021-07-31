package com.example.marketplace.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.marketplace.R;
import com.example.marketplace.model.Product;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> implements  View.OnClickListener{

    ArrayList<Product> products;
    Context context;
    DecimalFormat formater = new DecimalFormat("###,###.##");
    private View.OnClickListener listener;
    private ProductAdapter.HomeEvents homeEvents;
    private boolean showFavorite;

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
        holder.price.setText("$"+formater.format(Integer.parseInt(products.get(position).getPrice())));
        Glide.with(context).load(products.get(position).getUrl()).into(holder.image);
        holder.favorite.setOnClickListener(v->{
            products.get(position).setFavorite(!products.get(position).isFavorite());
            holder.favorite.setImageDrawable(products.get(position).isFavorite() ? context.getDrawable(R.drawable.ic_favorite) : context.getDrawable(R.drawable.ic_favorite_border));
            if(homeEvents != null)
                homeEvents.onClickFavorite(products.get(position), position);
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
        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.nameCart);
            description = v.findViewById(R.id.descriptionCart);
            price = v.findViewById(R.id.priceCart);
            image = v.findViewById(R.id.imageCart);
            favorite = v.findViewById(R.id.favoriteButton);
        }
    }

    public void setListenerFavorite(HomeEvents homeEvents){
        this.homeEvents = homeEvents;
    }

    public interface HomeEvents{
        void onClickFavorite(Product product, int position );
    }

}
