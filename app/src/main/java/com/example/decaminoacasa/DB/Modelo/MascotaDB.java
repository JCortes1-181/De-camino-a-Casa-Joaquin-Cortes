package com.example.decaminoacasa.DB.Modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.decaminoacasa.DB.DBconexion;
import com.example.decaminoacasa.DB.Clases.Mascota;

import java.util.ArrayList;
import java.util.List;

public class MascotaDB {
    private final DBconexion dbconexion;

    public MascotaDB(Context context) {
        dbconexion = new DBconexion(context);
    }

    public boolean insertarMascota(Mascota mascota) {
        SQLiteDatabase db = dbconexion.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBconexion.COLUMN_MASCOTA_NOMBRE, mascota.getNombre());
        values.put(DBconexion.COLUMN_MASCOTA_COLOR, mascota.getColor());
        values.put(DBconexion.COLUMN_MASCOTA_INFO, mascota.getInformacion());
        values.put(DBconexion.COLUMN_MASCOTA_GPS, mascota.getUbicacionGps());
        values.put(DBconexion.COLUMN_MASCOTA_CREADOR, mascota.getUsuarioCreador());
        values.put(DBconexion.COLUMN_MASCOTA_ESTADO, "activo");

        long result = db.insert(DBconexion.TABLE_MASCOTAS, null, values);
        db.close();
        return result != -1;
    }

    public List<Mascota> obtenerMascotasActivas() {
        List<Mascota> lista = new ArrayList<>();
        SQLiteDatabase db = dbconexion.getReadableDatabase();
        String selection = DBconexion.COLUMN_MASCOTA_ESTADO + " = ?";
        String[] selectionArgs = {"activo"};
        Cursor cursor = db.query(DBconexion.TABLE_MASCOTAS, null, selection, selectionArgs, null, null, "id DESC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(DBconexion.COLUMN_MASCOTA_ID);
                int nombreIndex = cursor.getColumnIndex(DBconexion.COLUMN_MASCOTA_NOMBRE);
                int colorIndex = cursor.getColumnIndex(DBconexion.COLUMN_MASCOTA_COLOR);
                int infoIndex = cursor.getColumnIndex(DBconexion.COLUMN_MASCOTA_INFO);
                int gpsIndex = cursor.getColumnIndex(DBconexion.COLUMN_MASCOTA_GPS);
                int creadorIndex = cursor.getColumnIndex(DBconexion.COLUMN_MASCOTA_CREADOR);
                int estadoIndex = cursor.getColumnIndex(DBconexion.COLUMN_MASCOTA_ESTADO);

                int id = idIndex != -1 ? cursor.getInt(idIndex) : 0;
                String nom = nombreIndex != -1 ? cursor.getString(nombreIndex) : "";
                String color = colorIndex != -1 ? cursor.getString(colorIndex) : "";
                String info = infoIndex != -1 ? cursor.getString(infoIndex) : "";
                String gps = gpsIndex != -1 ? cursor.getString(gpsIndex) : "";
                String creador = creadorIndex != -1 ? cursor.getString(creadorIndex) : "";
                String estado = estadoIndex != -1 ? cursor.getString(estadoIndex) : "activo";

                lista.add(new Mascota(id, nom, color, info, gps, creador, estado));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return lista;
    }

    public boolean desactivarMascota(int id) {
        SQLiteDatabase db = dbconexion.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBconexion.COLUMN_MASCOTA_ESTADO, "inactivo");

        int rows = db.update(DBconexion.TABLE_MASCOTAS, values, DBconexion.COLUMN_MASCOTA_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rows > 0;
    }
}
