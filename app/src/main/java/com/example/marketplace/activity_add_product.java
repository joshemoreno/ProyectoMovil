package com.example.marketplace;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class activity_add_product extends AppCompatActivity {

    private Button btnSave, btnCancel, btnUpload;
    private Activity mySelf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        btnSave = findViewById(R.id.btn_save);
        btnUpload = findViewById(R.id.btn_upLoad);
        btnCancel = findViewById(R.id.btn_cancel);
        mySelf = this;

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent act_goHome = new Intent(mySelf, MenuActivity.class);
                startActivity(act_goHome);
                finish();
            }
        });

    }
}