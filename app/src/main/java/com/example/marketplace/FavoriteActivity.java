package com.example.marketplace;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marketplace.adapter.ProductAdapter;
import com.example.marketplace.model.ManagementFavorites;
import com.example.marketplace.model.Product;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {

    private ArrayList<Product> list;
    private RecyclerView rv_list;
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        rv_list = findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        list = (ArrayList<Product>) ManagementFavorites.getFavorite(this).getFavorites();

        productAdapter = new ProductAdapter(list,this, false);

        productAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent act_goDetail = new Intent(FavoriteActivity.this, Detail_product.class);
                act_goDetail.putExtra("name",list.get(rv_list.getChildAdapterPosition(v)).getName());
                act_goDetail.putExtra("description",list.get(rv_list.getChildAdapterPosition(v)).getDescription());
                act_goDetail.putExtra("price",list.get(rv_list.getChildAdapterPosition(v)).getPrice());
                act_goDetail.putExtra("quantity",list.get(rv_list.getChildAdapterPosition(v)).getQuantity());
                act_goDetail.putExtra("url",list.get(rv_list.getChildAdapterPosition(v)).getUrl());
                act_goDetail.putExtra("latitude",list.get(rv_list.getChildAdapterPosition(v)).getLatitude());
                act_goDetail.putExtra("longitude", list.get(rv_list.getChildAdapterPosition(v)).getLongitude());
                startActivity(act_goDetail);
            }
        });
        rv_list.setAdapter(productAdapter);

    }

}