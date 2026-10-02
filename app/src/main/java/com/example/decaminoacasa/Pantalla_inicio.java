package com.example.decaminoacasa;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Pantalla_inicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pantalla_inicio);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView ivProfile = findViewById(R.id.iv_profile);
        ivProfile.setOnClickListener(v -> {
            Intent intent = new Intent(Pantalla_inicio.this, Perfil_Usuario.class);
            startActivity(intent);
        });

        ImageButton btnLeft = findViewById(R.id.btn_footer_left);
        ImageButton btnMiddle = findViewById(R.id.btn_footer_middle);
        ImageButton btnRight = findViewById(R.id.btn_footer_right);

        btnLeft.setOnClickListener(v -> Toast.makeText(this, "Ubicación (Próximamente)", Toast.LENGTH_SHORT).show());
        btnMiddle.setOnClickListener(v -> Toast.makeText(this, "Pantalla de inicio", Toast.LENGTH_SHORT).show());
        btnRight.setOnClickListener(v -> Toast.makeText(this, "Chat (Próximamente)", Toast.LENGTH_SHORT).show());
    }
}