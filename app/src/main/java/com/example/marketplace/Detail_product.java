package com.example.marketplace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;

public class Detail_product extends AppCompatActivity {

    private TextView tvName, tvDescription, tvQuantity, tvPrice;
    private ImageView imageDetail;
    DecimalFormat formater = new DecimalFormat("###,###.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String description = intent.getStringExtra("description");
        String price = intent.getStringExtra("price");
        String quantity = intent.getStringExtra("quantity");
        String url = intent.getStringExtra("url");

        tvName= findViewById(R.id.tv_name);
        tvDescription= findViewById(R.id.tv_description);
        tvQuantity= findViewById(R.id.tv_quantity);
        tvPrice= findViewById(R.id.tv_price);
        imageDetail = findViewById(R.id.imageDetail);

        tvName.setText(name);
        tvDescription.setText(description);
        tvPrice.setText("$"+formater.format(Integer.parseInt(price)));
        tvQuantity.setText(quantity);
        Glide.with(this).load(url).into(imageDetail);

    }
}