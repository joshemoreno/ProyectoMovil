package com.example.marketplace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
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
    private TextView tv_terms;

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
        tv_terms = findViewById(R.id.terms);

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = et_name.getText().toString().trim();
                lastName = et_lastName.getText().toString().trim();
                email = et_email.getText().toString().trim();
                password = et_password.getText().toString().trim();

                AlertDialog.Builder builder = new AlertDialog.Builder(mySelf);
                builder.setTitle(R.string.warning);
                builder.setIcon(R.drawable.warning);
                builder.setPositiveButton(R.string.btn_ok,null);

                if (name.isEmpty()){
                    builder.setMessage(R.string.msg_name);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else if (lastName.isEmpty()){
                    builder.setMessage(R.string.msg_lastname);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else if (email.isEmpty()){
                    builder.setMessage(R.string.msg_email);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else if (password.isEmpty()){
                    builder.setMessage(R.string.msg_password);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else if (password.length()<6){
                    builder.setMessage(R.string.msg_password2);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else if (!chk_terms.isChecked()){
                    builder.setMessage(R.string.msg_check);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else{
                    createAccount(email,password);
                }

            }
        });

        tv_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent act_terms = new Intent(mySelf,HomeActivity.class);
                startActivity(act_terms);
            }
        });

    }


    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(mySelf);
                        builder.setTitle(R.string.warning);
                        builder.setIcon(R.drawable.warning);
                        builder.setPositiveButton(R.string.btn_ok,null);

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
                            builder.setMessage(R.string.msg_exist);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        } else {
                            builder.setMessage(R.string.msg_error);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }

                    }
                });
    }
}