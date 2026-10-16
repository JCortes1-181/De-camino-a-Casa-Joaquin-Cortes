package com.example.decaminoacasa.DB.Modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.decaminoacasa.DB.Clases.Usuario;
import com.example.decaminoacasa.DB.DBconexion;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDB {
    private final DBconexion dbconexion;

    public UsuarioDB(Context context) {
        dbconexion = new DBconexion(context);
    }

    public boolean insertarUsuario(Usuario usuario) {
        SQLiteDatabase db = dbconexion.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBconexion.COLUMN_NOMBRE, usuario.getNombre());
        values.put(DBconexion.COLUMN_TELEFONO, usuario.getTelefono());
        values.put(DBconexion.COLUMN_PASSWORD, usuario.getPassword());
        values.put(DBconexion.COLUMN_ROL, usuario.getRol() != null ? usuario.getRol() : "user");
        values.put(DBconexion.COLUMN_ESTADO, "activo");

        long result = db.insert(DBconexion.TABLE_USUARIOS, null, values);
        db.close();
        return result != -1;
    }

    public boolean validarUsuario(String nombre, String password) {
        SQLiteDatabase db = dbconexion.getReadableDatabase();
        String selection = DBconexion.COLUMN_NOMBRE + " = ? AND " + DBconexion.COLUMN_PASSWORD + " = ? AND " + DBconexion.COLUMN_ESTADO + " = ?";
        String[] selectionArgs = {nombre, password, "activo"};

        Cursor cursor = db.query(DBconexion.TABLE_USUARIOS, null, selection, selectionArgs, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    public Usuario obtenerUsuarioPorNombre(String nombre) {
        SQLiteDatabase db = dbconexion.getReadableDatabase();
        String selection = DBconexion.COLUMN_NOMBRE + " = ? AND " + DBconexion.COLUMN_ESTADO + " = ?";
        String[] selectionArgs = {nombre, "activo"};

        Cursor cursor = db.query(DBconexion.TABLE_USUARIOS, null, selection, selectionArgs, null, null, null);
        Usuario usuario = null;
        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBconexion.COLUMN_ID);
            int nombreIndex = cursor.getColumnIndex(DBconexion.COLUMN_NOMBRE);
            int telefonoIndex = cursor.getColumnIndex(DBconexion.COLUMN_TELEFONO);
            int passwordIndex = cursor.getColumnIndex(DBconexion.COLUMN_PASSWORD);
            int rolIndex = cursor.getColumnIndex(DBconexion.COLUMN_ROL);
            int estadoIndex = cursor.getColumnIndex(DBconexion.COLUMN_ESTADO);

            int id = idIndex != -1 ? cursor.getInt(idIndex) : 0;
            String nom = nombreIndex != -1 ? cursor.getString(nombreIndex) : nombre;
            String tel = telefonoIndex != -1 ? cursor.getString(telefonoIndex) : "Sin teléfono";
            String pass = passwordIndex != -1 ? cursor.getString(passwordIndex) : "";
            String rol = rolIndex != -1 ? cursor.getString(rolIndex) : "user";
            String estado = estadoIndex != -1 ? cursor.getString(estadoIndex) : "activo";

            usuario = new Usuario(id, nom, tel, pass, rol, estado);
            cursor.close();
        }
        db.close();
        return usuario;
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        SQLiteDatabase db = dbconexion.getReadableDatabase();
        String selection = DBconexion.COLUMN_ESTADO + " = ?";
        String[] selectionArgs = {"activo"};
        Cursor cursor = db.query(DBconexion.TABLE_USUARIOS, null, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(DBconexion.COLUMN_ID);
                int nombreIndex = cursor.getColumnIndex(DBconexion.COLUMN_NOMBRE);
                int telefonoIndex = cursor.getColumnIndex(DBconexion.COLUMN_TELEFONO);
                int passwordIndex = cursor.getColumnIndex(DBconexion.COLUMN_PASSWORD);
                int rolIndex = cursor.getColumnIndex(DBconexion.COLUMN_ROL);
                int estadoIndex = cursor.getColumnIndex(DBconexion.COLUMN_ESTADO);

                int id = idIndex != -1 ? cursor.getInt(idIndex) : 0;
                String nom = nombreIndex != -1 ? cursor.getString(nombreIndex) : "";
                String tel = telefonoIndex != -1 ? cursor.getString(telefonoIndex) : "";
                String pass = passwordIndex != -1 ? cursor.getString(passwordIndex) : "";
                String rol = rolIndex != -1 ? cursor.getString(rolIndex) : "user";
                String estado = estadoIndex != -1 ? cursor.getString(estadoIndex) : "activo";

                lista.add(new Usuario(id, nom, tel, pass, rol, estado));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return lista;
    }

    public boolean actualizarUsuario(int id, String nombre, String telefono, String password, String rol) {
        SQLiteDatabase db = dbconexion.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBconexion.COLUMN_NOMBRE, nombre);
        values.put(DBconexion.COLUMN_TELEFONO, telefono);
        values.put(DBconexion.COLUMN_PASSWORD, password);
        values.put(DBconexion.COLUMN_ROL, rol);

        int rows = db.update(DBconexion.TABLE_USUARIOS, values, DBconexion.COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rows > 0;
    }

    public boolean desactivarUsuario(int id) {
        SQLiteDatabase db = dbconexion.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBconexion.COLUMN_ESTADO, "inactivo");

        int rows = db.update(DBconexion.TABLE_USUARIOS, values, DBconexion.COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rows > 0;
    }
}
