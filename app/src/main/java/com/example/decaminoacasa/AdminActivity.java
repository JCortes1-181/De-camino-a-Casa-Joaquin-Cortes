package com.example.decaminoacasa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.decaminoacasa.DB.Clases.Usuario;
import com.example.decaminoacasa.DB.Modelo.UsuarioDB;

import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private UsuarioDB usuarioDB;
    private LinearLayout containerUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        usuarioDB = new UsuarioDB(this);
        containerUsers = findViewById(R.id.container_admin_users);

        Button btnLogout = findViewById(R.id.btn_admin_logout);
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        cargarUsuarios();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarUsuarios();
    }

    private void cargarUsuarios() {
        containerUsers.removeAllViews();
        List<Usuario> usuarios = usuarioDB.obtenerTodosLosUsuarios();

        for (Usuario u : usuarios) {
            View card = getLayoutInflater().inflate(android.R.layout.simple_list_item_2, containerUsers, false);
            TextView t1 = card.findViewById(android.R.id.text1);
            TextView t2 = card.findViewById(android.R.id.text2);

            t1.setText("👤 " + u.getNombre() + " (Rol: " + u.getRol() + ")");
            t2.setText("📞 " + u.getTelefono() + " | ID: " + u.getId());

            card.setBackgroundResource(android.R.drawable.dialog_holo_light_frame);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 16);
            card.setLayoutParams(params);
            card.setPadding(20, 20, 20, 20);

            card.setOnClickListener(v -> mostrarDialogoOpciones(u));

            containerUsers.addView(card);
        }
    }

    private void mostrarDialogoOpciones(Usuario usuario) {
        CharSequence[] opciones = {"Actualizar datos", "Borrar usuario", "Cancelar"};
        new AlertDialog.Builder(this)
            .setTitle("Gestionar usuario: " + usuario.getNombre())
            .setItems(opciones, (dialog, which) -> {
                if (which == 0) {
                    mostrarDialogoActualizar(usuario);
                } else if (which == 1) {
                    boolean desactivado = usuarioDB.desactivarUsuario(usuario.getId());
                    if (desactivado) {
                        Toast.makeText(this, "Usuario borrado (desactivado)", Toast.LENGTH_SHORT).show();
                        cargarUsuarios();
                    } else {
                        Toast.makeText(this, "Error al borrar usuario", Toast.LENGTH_SHORT).show();
                    }
                }
            })
            .show();
    }

    private void mostrarDialogoActualizar(Usuario usuario) {
        View view = getLayoutInflater().inflate(R.layout.dialog_actualizar_usuario, null);
        EditText etNombre = view.findViewById(R.id.et_edit_nombre);
        EditText etTelefono = view.findViewById(R.id.et_edit_telefono);
        EditText etPassword = view.findViewById(R.id.et_edit_password);

        etNombre.setText(usuario.getNombre());
        etTelefono.setText(usuario.getTelefono());
        etPassword.setText(usuario.getPassword());

        new AlertDialog.Builder(this)
            .setTitle("Actualizar Usuario")
            .setView(view)
            .setPositiveButton("Guardar", (dialog, which) -> {
                String nuevoNombre = etNombre.getText().toString().trim();
                String nuevoTelefono = etTelefono.getText().toString().trim();
                String nuevaPass = etPassword.getText().toString().trim();

                if (nuevoNombre.isEmpty() || nuevoTelefono.isEmpty() || nuevaPass.isEmpty()) {
                    Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    boolean actualizado = usuarioDB.actualizarUsuario(usuario.getId(), nuevoNombre, nuevoTelefono, nuevaPass, usuario.getRol());
                    if (actualizado) {
                        Toast.makeText(this, "Usuario actualizado con éxito", Toast.LENGTH_SHORT).show();
                        cargarUsuarios();
                    } else {
                        Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
                    }
                }
            })
            .setNegativeButton("Cancelar", null)
            .show();
    }
}
