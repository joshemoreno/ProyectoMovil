package com.example.marketplace;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class loginActivity extends AppCompatActivity {

    EditText usuario, clave;
    Button iniciar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usuario = findViewById(R.id.txtUsuario);
        clave = findViewById(R.id.txtClave);
        iniciar = findViewById(R.id.btnLogin);

        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validar();
            }
        });
    }

    public void Validar(){
        String usu = usuario.getText().toString();
        String password = clave.getText().toString();
        if (usu.equals("Maritza22") && password.equals("M2021")){
            Toast.makeText(this, "Bienvenido a la App", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Usuario y/o Clave invalidos", Toast.LENGTH_SHORT).show();
        }
    }
}