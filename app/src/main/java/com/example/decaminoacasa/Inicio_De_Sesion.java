package com.example.decaminoacasa;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Inicio_De_Sesion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inicio_de_sesion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText etUsername = findViewById(R.id.et_login_username);
        EditText etPassword = findViewById(R.id.et_login_password);

        Button btnBack = findViewById(R.id.btn_login_back);
        btnBack.setOnClickListener(v -> finish());

        Button btnSubmit = findViewById(R.id.btn_login_submit);
        btnSubmit.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(Inicio_De_Sesion.this, "Por favor ingresa un usuario y una contraseña", Toast.LENGTH_SHORT).show();
            } else if ("joaquin".equalsIgnoreCase(username) && "12345".equals(password)) {
                Toast.makeText(Inicio_De_Sesion.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Inicio_De_Sesion.this, Pantalla_inicio.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(Inicio_De_Sesion.this, "Usuario o contraseña incorrecta", Toast.LENGTH_SHORT).show();
            }
        });
    }
}