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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {


    private Button btn_signUp;
    private Activity mySelf;
    private EditText et_name, et_lastName, et_email, et_password;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    //    Variables
    private String name="";
    private String lastName="";
    private String email="";
    private String password="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        et_name = findViewById(R.id.et_name);
        et_lastName = findViewById(R.id.et_lastName);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        btn_signUp = findViewById(R.id.btn_signUp2);

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = et_name.getText().toString().trim();
                lastName = et_lastName.getText().toString().trim();
                email = et_email.getText().toString().trim();
                password = et_password.getText().toString().trim();

                if (name.isEmpty()){
                    Toast.makeText(SignUpActivity.this,"Please enter a name",Toast.LENGTH_SHORT).show();
                }
                else if (lastName.isEmpty()){
                    Toast.makeText(SignUpActivity.this,"Please enter a last name",Toast.LENGTH_SHORT).show();
                }
                else if (email.isEmpty()){
                    Toast.makeText(SignUpActivity.this,"Please enter a email",Toast.LENGTH_SHORT).show();
                }
                else if (password.isEmpty()){
                    Toast.makeText(SignUpActivity.this,"Please enter a password",Toast.LENGTH_SHORT).show();
                }
                else if (password.length()<6){
                    Toast.makeText(SignUpActivity.this,"The password must be at least 6 characters long",Toast.LENGTH_SHORT).show();
                }
                else{
                    registerUser();
                }
            }
        });

    }

    private void registerUser(){
        Log.i("text",""+email+password);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Map<String, Object> map = new HashMap<>();
                    map.put("name",name);
                    map.put("lastName",lastName);
                    map.put("email",email);

                    String id = mAuth.getCurrentUser().getUid();

                    mDatabase.child("user").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()){
                                Intent act_goHome = new Intent(mySelf,HomeActivity.class);
                                startActivity(act_goHome);
                                finish();
                            }else{
                                Toast.makeText(SignUpActivity.this,"This user could not be registered_2",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(SignUpActivity.this,"This user could not be registered_1",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void showTerms(android.view.View view) {
        Log.e("click","terms");

    }


}