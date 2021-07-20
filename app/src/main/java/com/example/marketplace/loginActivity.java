package com.example.marketplace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginActivity extends AppCompatActivity {

    private EditText et_email, et_password;
    private Button logIn;
    private Activity mySelf;
    private FirebaseAuth mAuth;

    //variables
    private String email="";
    private String password="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        mySelf = this;

        et_email = findViewById(R.id.eTemail);
        et_password = findViewById(R.id.eTpassword);
        logIn = findViewById(R.id.btnLogin);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = et_email.getText().toString().trim();
                password = et_password.getText().toString().trim();

                logIn(email,password);
            }
        });
    }

    private void logIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent act_goHome = new Intent(mySelf,MenuActivity.class);
                            startActivity(act_goHome);
                            finish();
                        } else {
                            Toast.makeText(loginActivity.this, "This user is not registered", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}