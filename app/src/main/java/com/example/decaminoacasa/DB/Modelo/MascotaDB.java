package com.example.decaminoacasa.DB.Modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.decaminoacasa.DB.DBconexion;
import com.example.decaminoacasa.DB.Clases.Mascota;

public class MascotaDB {
    private final DBconexion dbconexion;

    public MascotaDB(Context context) {
        dbconexion = new DBconexion(context);
    }

    public long insertarMascota(Mascota mascota) {
        SQLiteDatabase db = dbconexion.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBconexion.COLUMN_MASCOTA_NOMBRE, mascota.getNombre());
        values.put(DBconexion.COLUMN_MASCOTA_COLOR, mascota.getColor());
        values.put(DBconexion.COLUMN_MASCOTA_INFO, mascota.getInformacion());
        values.put(DBconexion.COLUMN_MASCOTA_GPS, mascota.getUbicacionGps());
        values.put(DBconexion.COLUMN_MASCOTA_CREADOR, mascota.getUsuarioCreador());

        long result = db.insert(DBconexion.TABLE_MASCOTAS, null, values);
        db.close();
        return result;
    }

    public Mascota obtenerMascotaPorId(int id) {
        SQLiteDatabase db = dbconexion.getReadableDatabase();
        String selection = DBconexion.COLUMN_MASCOTA_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(DBconexion.TABLE_MASCOTAS, null, selection, selectionArgs, null, null, null);

        Mascota mascota = null;
        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBconexion.COLUMN_MASCOTA_ID);
            int nombreIndex = cursor.getColumnIndex(DBconexion.COLUMN_MASCOTA_NOMBRE);
            int colorIndex = cursor.getColumnIndex(DBconexion.COLUMN_MASCOTA_COLOR);
            int infoIndex = cursor.getColumnIndex(DBconexion.COLUMN_MASCOTA_INFO);
            int gpsIndex = cursor.getColumnIndex(DBconexion.COLUMN_MASCOTA_GPS);
            int creadorIndex = cursor.getColumnIndex(DBconexion.COLUMN_MASCOTA_CREADOR);

            int petId = idIndex != -1 ? cursor.getInt(idIndex) : 0;
            String nom = nombreIndex != -1 ? cursor.getString(nombreIndex) : "";
            String color = colorIndex != -1 ? cursor.getString(colorIndex) : "";
            String info = infoIndex != -1 ? cursor.getString(infoIndex) : "";
            String gps = gpsIndex != -1 ? cursor.getString(gpsIndex) : "";
            String creador = creadorIndex != -1 ? cursor.getString(creadorIndex) : "";

            mascota = new Mascota(petId, nom, color, info, gps, creador);
            cursor.close();
        }
        db.close();
        return mascota;
    }
}
