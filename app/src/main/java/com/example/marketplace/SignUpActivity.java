package com.example.marketplace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class SignUpActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "EmailPassword";
    private Button btn_signUp;
    private Activity mySelf;
    private EditText et_name, et_lastName, et_email, et_password;
    private CheckBox chk_terms;
    private FirebaseAuth mAuth;

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
        mySelf = this;

        et_name = findViewById(R.id.et_name);
        et_lastName = findViewById(R.id.et_lastName);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        btn_signUp = findViewById(R.id.btn_signUp2);
        chk_terms = findViewById(R.id.chk_terms);

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
                else if (!chk_terms.isChecked()){
                    Toast.makeText(SignUpActivity.this,"You must accept the terms and conditions",Toast.LENGTH_SHORT).show();
                }
                else{
                    createAccount(email,password);
                }
            }
        });

    }


    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Map<String, Object> user = new HashMap<>();
                            user.put("name", name);
                            user.put("lastName", lastName);
                            user.put("email", email);
                            String id = mAuth.getCurrentUser().getUid();

                            db.collection("users")
                                    .add(user)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Intent act_goHome = new Intent(mySelf, MenuActivity.class);
                                            startActivity(act_goHome);
                                            finish();
                                        }
                                    });
                        } else if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            Toast.makeText(SignUpActivity.this, "This user is already registered", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignUpActivity.this, "This user could not be registered", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}