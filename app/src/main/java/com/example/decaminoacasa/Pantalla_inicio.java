package com.example.decaminoacasa;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.decaminoacasa.DB.Clases.Anuncio;
import com.example.decaminoacasa.DB.Clases.Mascota;
import com.example.decaminoacasa.DB.Modelo.AnuncioDB;
import com.example.decaminoacasa.DB.Modelo.MascotaDB;

import java.util.List;
import java.util.Locale;

public class Pantalla_inicio extends AppCompatActivity {

    private MascotaDB mascotaDB;
    private AnuncioDB anuncioDB;
    private String username;
    private EditText etSearch;

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

        username = getIntent().getStringExtra("username");
        if (username == null) username = "Joaquín";
        String finalUsername = username;

        mascotaDB = new MascotaDB(this);
        anuncioDB = new AnuncioDB(this);

        ImageView ivProfile = findViewById(R.id.iv_profile);
        ivProfile.setOnClickListener(v -> {
            Intent intent = new Intent(Pantalla_inicio.this, Perfil_Usuario.class);
            intent.putExtra("username", finalUsername);
            startActivity(intent);
        });

        etSearch = findViewById(R.id.et_search);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cargarMascotas(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        ImageButton btnHome = findViewById(R.id.btn_footer_home);
        ImageButton btnAdd = findViewById(R.id.btn_footer_add);

        btnHome.setOnClickListener(v -> {
            etSearch.setText("");
            cargarMascotas("");
            Toast.makeText(this, "Pantalla de inicio", Toast.LENGTH_SHORT).show();
        });

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(Pantalla_inicio.this, AgregarMascotaActivity.class);
            intent.putExtra("username", finalUsername);
            startActivityForResult(intent, 100);
        });

        cargarMascotas("");
    }

    @Override
    protected void onResume() {
        super.onResume();
        String query = etSearch != null ? etSearch.getText().toString() : "";
        cargarMascotas(query);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            String query = etSearch != null ? etSearch.getText().toString() : "";
            cargarMascotas(query);
        }
    }

    private void cargarMascotas(String query) {
        LinearLayout container = findViewById(R.id.container_pets);
        container.removeAllViews();

        List<Anuncio> anuncios = anuncioDB.obtenerAnunciosActivos();
        for (Anuncio anuncio : anuncios) {
            Mascota mascota = mascotaDB.obtenerMascotaPorId(anuncio.getMascotaId());
            if (mascota == null) continue;

            // Apply search filter (only by pet name)
            if (query != null && !query.trim().isEmpty()) {
                String q = query.toLowerCase(Locale.getDefault());
                boolean matches = mascota.getNombre().toLowerCase(Locale.getDefault()).contains(q);
                if (!matches) continue;
            }

            View card = getLayoutInflater().inflate(R.layout.item_mascota_card, container, false);
            TextView tvName = card.findViewById(R.id.tv_card_name);
            TextView tvColor = card.findViewById(R.id.tv_card_color);
            TextView tvLocation = card.findViewById(R.id.tv_card_location);
            TextView tvCreator = card.findViewById(R.id.tv_card_creator);
            Button btnDeleteAd = card.findViewById(R.id.btn_delete_ad);

            tvName.setText("🐾 " + mascota.getNombre());
            tvColor.setText(mascota.getColor());
            tvLocation.setText("📍 " + mascota.getUbicacionGps() + " (" + anuncio.getHora() + ")");
            tvCreator.setText("👤 Publicado por: " + mascota.getUsuarioCreador());

            card.setOnClickListener(v -> {
                Intent intent = new Intent(Pantalla_inicio.this, DetalleMascotaActivity.class);
                intent.putExtra("mascota", mascota);
                startActivity(intent);
            });

            if (username.equalsIgnoreCase(mascota.getUsuarioCreador()) || username.equalsIgnoreCase("admin")) {
                btnDeleteAd.setVisibility(View.VISIBLE);
                btnDeleteAd.setOnClickListener(v -> {
                    new AlertDialog.Builder(this)
                        .setTitle("Quitar Aviso")
                        .setMessage("¿Deseas quitar este anuncio de mascota?")
                        .setPositiveButton("Sí, quitar", (dialog, which) -> {
                            boolean des = anuncioDB.desactivarAnuncio(anuncio.getId());
                            if (des) {
                                Toast.makeText(this, "Anuncio quitado con éxito", Toast.LENGTH_SHORT).show();
                                String currentQuery = etSearch != null ? etSearch.getText().toString() : "";
                                cargarMascotas(currentQuery);
                            } else {
                                Toast.makeText(this, "Error al quitar el anuncio", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
                });
            } else {
                btnDeleteAd.setVisibility(View.GONE);
            }

            container.addView(card);
        }
    }
}