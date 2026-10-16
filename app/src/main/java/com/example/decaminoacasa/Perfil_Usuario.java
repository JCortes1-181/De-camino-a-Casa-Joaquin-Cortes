package com.example.decaminoacasa;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.decaminoacasa.DB.Clases.Usuario;
import com.example.decaminoacasa.DB.Modelo.UsuarioDB;

public class Perfil_Usuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil_usuario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton btnBack = findViewById(R.id.btn_profile_back);
        btnBack.setOnClickListener(v -> finish());

        Button btnLogout = findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(v -> {
            Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Perfil_Usuario.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        String username = getIntent().getStringExtra("username");
        if (username == null) {
            username = "Joaquín";
        }

        UsuarioDB usuarioDB = new UsuarioDB(this);
        Usuario usuario = usuarioDB.obtenerUsuarioPorNombre(username);

        TextView tvUserName = findViewById(R.id.tv_user_name);
        TextView tvDetailName = findViewById(R.id.tv_detail_name);
        TextView tvDetailPhone = findViewById(R.id.tv_detail_phone);

        if (usuario != null) {
            tvUserName.setText(usuario.getNombre());
            tvDetailName.setText(usuario.getNombre());
            tvDetailPhone.setText(usuario.getTelefono());
        } else {
            tvUserName.setText(username);
            tvDetailName.setText(username);
            tvDetailPhone.setText("+56 9 1234 5678");
        }
    }
}