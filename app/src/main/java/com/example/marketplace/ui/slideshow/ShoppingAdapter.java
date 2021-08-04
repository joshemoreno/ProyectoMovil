package com.example.marketplace.ui.slideshow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marketplace.R;
import com.example.marketplace.adapter.ProductAdapter;
import com.example.marketplace.model.Product;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ViewHolder>{
    ArrayList<Product> products;
    Context context;
    DecimalFormat formater = new DecimalFormat("###,###.##");

    public ShoppingAdapter(ArrayList<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ShoppingAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ShoppingAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ShoppingAdapter.ViewHolder holder, int position) {
        Integer count = Integer.parseInt(products.get(position).getCount());
        Integer price = Integer.parseInt(products.get(position).getPrice());
        holder.tv_name.setText(products.get(position).getName());
        holder.tv_cant.setText("X "+count);
        holder.tv_price.setText("$" + formater.format(price));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name, tv_cant, tv_price;

        public ViewHolder(View v) {
            super(v);
            tv_name = v.findViewById(R.id.item_name);
            tv_cant = v.findViewById(R.id.item_cant);
            tv_price = v.findViewById(R.id.item_price);
        }
    }
}