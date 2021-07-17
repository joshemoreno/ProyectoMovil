package com.example.marketplace;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_logIn;
    private Button btn_signUp;
    private Activity mySelf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mySelf = this;
        btn_logIn = findViewById(R.id.btn_logIn);
        btn_signUp = findViewById(R.id.btn_signUp);

        btn_logIn.setOnClickListener(this);
        btn_signUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_signUp:
                Intent act_signUp = new Intent(mySelf,SignUpActivity.class);
                startActivity(act_signUp);
                break;
            case R.id.btn_logIn:
                Intent act_login = new Intent(mySelf,loginActivity.class);
                startActivity(act_login);
                break;
        }
    }


}