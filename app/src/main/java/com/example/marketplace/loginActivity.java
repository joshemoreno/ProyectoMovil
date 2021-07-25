package com.example.marketplace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity {

    private EditText et_email, et_password;
    private Button logIn;
    private Activity mySelf;
    private FirebaseAuth mAuth;
    private TextView sign_up;
    LoadinDialog loadinDialog = new LoadinDialog(loginActivity.this);

    //variables
    private String email="";
    private String password="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        mySelf = this;
        sign_up=findViewById(R.id.tv_sign);
        et_email = findViewById(R.id.eTemail);
        et_password = findViewById(R.id.eTpassword);
        logIn = findViewById(R.id.btnLogin);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = et_email.getText().toString().trim();
                password = et_password.getText().toString().trim();

                AlertDialog.Builder builder = new AlertDialog.Builder(mySelf);
                builder.setTitle(R.string.warning);
                builder.setIcon(R.drawable.warning);
                builder.setPositiveButton(R.string.btn_ok,null);

                if (email.isEmpty()){
                    builder.setMessage(R.string.msg_email);
                    androidx.appcompat.app.AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else if (password.isEmpty()){
                    builder.setMessage(R.string.msg_password);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else{
                    loadinDialog.startLoading();
                    logIn(email,password);
                }

            }
        });
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sign = new Intent(mySelf, SignUpActivity.class);
                startActivity(sign);
            }
        });
    }

    private void logIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(mySelf);
                        builder.setTitle(R.string.warning);
                        builder.setIcon(R.drawable.warning);
                        builder.setPositiveButton(R.string.btn_ok,null);

                        if (task.isSuccessful()) {
                            loadinDialog.dismissDialog();
                            Intent act_goHome = new Intent(mySelf,MenuActivity.class);
                            startActivity(act_goHome);
                            finish();
                        } else {
                            builder.setMessage(R.string.msg_noExist);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });

    }
}