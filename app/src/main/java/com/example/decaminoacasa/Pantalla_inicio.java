package com.example.decaminoacasa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

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

        btnLeft.setOnClickListener(v -> Toast.makeText(this, "Ubicación", Toast.LENGTH_SHORT).show());
        btnMiddle.setOnClickListener(v -> Toast.makeText(this, "Pantalla de inicio", Toast.LENGTH_SHORT).show());
        btnRight.setOnClickListener(v -> {
            Intent intent = new Intent(Pantalla_inicio.this, AgregarMascotaActivity.class);
            startActivityForResult(intent, 100);
        });

        cargarMascotas();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarMascotas();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            cargarMascotas();
        }
    }

    private void cargarMascotas() {
        LinearLayout container = findViewById(R.id.container_pets);
        container.removeAllViews();

        List<Mascota> mascotas = MascotaRepository.getListaMascotas();
        for (Mascota mascota : mascotas) {
            View card = getLayoutInflater().inflate(android.R.layout.simple_list_item_2, container, false);
            TextView text1 = card.findViewById(android.R.id.text1);
            TextView text2 = card.findViewById(android.R.id.text2);

            text1.setText("🐾 " + mascota.getNombre() + " (" + mascota.getColor() + ")");
            text2.setText("📍 " + mascota.getUbicacionGps());

            card.setBackgroundResource(android.R.drawable.dialog_holo_light_frame);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 16);
            card.setLayoutParams(params);
            card.setPadding(24, 24, 24, 24);

            card.setOnClickListener(v -> {
                Intent intent = new Intent(Pantalla_inicio.this, DetalleMascotaActivity.class);
                intent.putExtra("mascota", mascota);
                startActivity(intent);
            });

            container.addView(card);
        }
    }
}