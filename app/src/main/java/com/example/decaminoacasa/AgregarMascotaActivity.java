package com.example.decaminoacasa;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class AgregarMascotaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_agregar_mascota);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText etName = findViewById(R.id.et_pet_name);
        EditText etColor = findViewById(R.id.et_pet_color);
        EditText etInfo = findViewById(R.id.et_pet_info);
        EditText etLocation = findViewById(R.id.et_pet_location);
        FrameLayout mapContainer = findViewById(R.id.map_picker_container);
        TextView tvMapStatus = findViewById(R.id.tv_map_status);

        String defaultLocation = "Lat: -33.4489, Lng: -70.6693 (Santiago Centro)";
        etLocation.setText(defaultLocation);

        mapContainer.setOnClickListener(v -> {
            Random rand = new Random();
            double lat = -33.4000 + (rand.nextDouble() * 0.1);
            double lng = -70.6000 - (rand.nextDouble() * 0.1);
            String newLoc = String.format("Lat: %.4f, Lng: %.4f (Ubicación marcada en mapa)", lat, lng);
            etLocation.setText(newLoc);
            tvMapStatus.setText("📍 Ubicación seleccionada:\n" + newLoc);
            Toast.makeText(this, "¡Ubicación GPS seleccionada en el mapa!", Toast.LENGTH_SHORT).show();
        });

        Button btnSave = findViewById(R.id.btn_save_pet);
        Button btnCancel = findViewById(R.id.btn_cancel_pet);

        btnCancel.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String color = etColor.getText().toString().trim();
            String info = etInfo.getText().toString().trim();
            String location = etLocation.getText().toString().trim();

            if (name.isEmpty() || color.isEmpty() || info.isEmpty() || location.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                Mascota mascota = new Mascota(name, color, info, location);
                MascotaRepository.agregarMascota(mascota);
                Toast.makeText(this, "¡Mascota agregada con éxito!", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}