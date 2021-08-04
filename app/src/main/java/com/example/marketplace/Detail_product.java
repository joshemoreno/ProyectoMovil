package com.example.marketplace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;

public class Detail_product extends AppCompatActivity {

    private TextView tvName, tvDescription, tvQuantity, tvPrice, tvLocation;
    private ImageView imageDetail;
    DecimalFormat formater = new DecimalFormat("###,###.##");
    private Activity mySelf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        mySelf = this;
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String description = intent.getStringExtra("description");
        String price = intent.getStringExtra("price");
        String quantity = intent.getStringExtra("quantity");
        String url = intent.getStringExtra("url");
        String latitude = intent.getStringExtra("latitude");
        String longitude = intent.getStringExtra("longitude");

        tvName= findViewById(R.id.tv_name);
        tvDescription= findViewById(R.id.tv_description);
        tvQuantity= findViewById(R.id.tv_quantity);
        tvPrice= findViewById(R.id.tv_price);
        imageDetail = findViewById(R.id.imageDetail);
        tvLocation = findViewById(R.id.tv_location);


        tvName.setText(name);
        tvDescription.setText(description);
        tvPrice.setText("$"+formater.format(Integer.parseInt(price)));
        tvQuantity.setText(quantity);
        Glide.with(this).load(url).into(imageDetail);

        tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent map = new Intent(mySelf, MapActivity2.class);
                map.putExtra("latitud", latitude);
                map.putExtra("longitud", longitude);
                startActivity(map);
            }
        });
    }
}