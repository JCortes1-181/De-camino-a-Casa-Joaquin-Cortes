package com.example.decaminoacasa;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetalleMascotaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle_mascota);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton btnBack = findViewById(R.id.btn_detail_back);
        btnBack.setOnClickListener(v -> finish());

        TextView tvName = findViewById(R.id.tv_detail_pet_name);
        TextView tvColor = findViewById(R.id.tv_detail_pet_color);
        TextView tvInfo = findViewById(R.id.tv_detail_info_text);
        TextView tvGps = findViewById(R.id.tv_detail_gps_text);

        Mascota mascota = (Mascota) getIntent().getSerializableExtra("mascota");
        if (mascota != null) {
            tvName.setText(mascota.getNombre());
            tvColor.setText("Color: " + mascota.getColor());
            tvInfo.setText(mascota.getInformacion());
            tvGps.setText(mascota.getUbicacionGps());
        }
    }
}