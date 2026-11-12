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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.decaminoacasa.DB.Clases.Mascota;
import com.example.decaminoacasa.DB.Modelo.MascotaDB;

import java.util.List;

/**
 * Pantalla principal de la aplicación donde se ven las alertas de mascotas perdidas,
 * permitiendo navegar al perfil, agregar nuevos reportes o ver los avisos existentes.
 */
public class Pantalla_inicio extends AppCompatActivity {

    private MascotaDB mascotaDB;
    private String username;

    /**
     * Se ejecuta al crear la activity, iniciando los componentes visuales, recuperando el usuario
     * en sesión, configurando los botones de la barra de navegación y cargando los reportes activos.
     *
     * @param savedInstanceState Estado guardado anterior de la activity.
     */
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

        ImageView ivProfile = findViewById(R.id.iv_profile);
        ivProfile.setOnClickListener(v -> {
            Intent intent = new Intent(Pantalla_inicio.this, Perfil_Usuario.class);
            intent.putExtra("username", finalUsername);
            startActivity(intent);
        });

        ImageButton btnLeft = findViewById(R.id.btn_footer_left);
        ImageButton btnMiddle = findViewById(R.id.btn_footer_middle);
        ImageButton btnRight = findViewById(R.id.btn_footer_right);

        btnLeft.setOnClickListener(v -> Toast.makeText(this, "Ubicación", Toast.LENGTH_SHORT).show());
        btnMiddle.setOnClickListener(v -> Toast.makeText(this, "Pantalla de inicio", Toast.LENGTH_SHORT).show());
        btnRight.setOnClickListener(v -> {
            Intent intent = new Intent(Pantalla_inicio.this, AgregarMascotaActivity.class);
            intent.putExtra("username", finalUsername);
            startActivityForResult(intent, 100);
        });

        cargarMascotas();
    }

    /**
     * Se ejecuta al reanudar la activity, asegurando que la lista de mascotas en el feed se mantenga actualizada en cualquier momento.
     */
    @Override
    protected void onResume() {
        super.onResume();
        cargarMascotas();
    }

    /**
     * Maneja el resultado devuelto por activities,
     * recargando el feed si el registro fue exitoso.
     *
     * @param requestCode Código de solicitud enviado originalmente.
     * @param resultCode Código de resultado devuelto por la activity.
     * @param data Intent que contiene los datos adicionales.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            cargarMascotas();
        }
    }

    /**
     * Carga de forma dinamica desde la base de datos todas los reportes con activos,
     * creando unas tarjetas visuales con sus datos e implementando clics para ver detalles o gestionar la eliminación.
     */
    private void cargarMascotas() {
        LinearLayout container = findViewById(R.id.container_pets);
        container.removeAllViews();

        List<Mascota> mascotas = mascotaDB.obtenerMascotasActivas();
        for (Mascota mascota : mascotas) {
            View card = getLayoutInflater().inflate(android.R.layout.simple_list_item_2, container, false);
            TextView text1 = card.findViewById(android.R.id.text1);
            TextView text2 = card.findViewById(android.R.id.text2);

            text1.setText(mascota.getNombre() + " (" + mascota.getColor() + ") - Creado por: " + mascota.getUsuarioCreador());
            text2.setText(mascota.getUbicacionGps());

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

            card.setOnLongClickListener(v -> {
                if (username.equalsIgnoreCase(mascota.getUsuarioCreador()) || username.equalsIgnoreCase("admin")) {
                    new AlertDialog.Builder(this)
                            .setTitle("Gestionar Aviso")
                            .setMessage("¿Deseas quitar este aviso de mascota?")
                            .setPositiveButton("Sí, quitar", (dialog, which) -> {
                                boolean des = mascotaDB.desactivarMascota(mascota.getId());
                                if (des) {
                                    Toast.makeText(this, "Que tu mascota se haya encontrado con exito", Toast.LENGTH_SHORT).show();
                                    cargarMascotas();
                                } else {
                                    Toast.makeText(this, "Error al desactivar el aviso", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("Cancelar", null)
                            .show();
                } else {
                    Toast.makeText(this, "opcion a quitar, solo el usuario que lo creo deberia poder acceder a esto", Toast.LENGTH_SHORT).show();
                }
                return true;
            });

            container.addView(card);
        }
    }
}