package com.example.decaminoacasa.DB.Modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.decaminoacasa.DB.Clases.Anuncio;
import com.example.decaminoacasa.DB.DBconexion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AnuncioDB {
    private final DBconexion dbconexion;

    public AnuncioDB(Context context) {
        dbconexion = new DBconexion(context);
    }

    public boolean insertarAnuncio(String usuarioId, int mascotaId) {
        SQLiteDatabase db = dbconexion.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBconexion.COLUMN_ANUNCIO_USUARIO, usuarioId);
        values.put(DBconexion.COLUMN_ANUNCIO_MASCOTA, mascotaId);

        String horaActual = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        values.put(DBconexion.COLUMN_ANUNCIO_HORA, horaActual);
        values.put(DBconexion.COLUMN_ANUNCIO_ESTADO, "activo");

        long result = db.insert(DBconexion.TABLE_ANUNCIOS, null, values);
        db.close();
        return result != -1;
    }

    public List<Anuncio> obtenerAnunciosActivos() {
        List<Anuncio> lista = new ArrayList<>();
        SQLiteDatabase db = dbconexion.getReadableDatabase();
        String selection = DBconexion.COLUMN_ANUNCIO_ESTADO + " = ?";
        String[] selectionArgs = {"activo"};
        Cursor cursor = db.query(DBconexion.TABLE_ANUNCIOS, null, selection, selectionArgs, null, null, "id DESC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(DBconexion.COLUMN_ANUNCIO_ID);
                int usuarioIndex = cursor.getColumnIndex(DBconexion.COLUMN_ANUNCIO_USUARIO);
                int mascotaIndex = cursor.getColumnIndex(DBconexion.COLUMN_ANUNCIO_MASCOTA);
                int horaIndex = cursor.getColumnIndex(DBconexion.COLUMN_ANUNCIO_HORA);
                int estadoIndex = cursor.getColumnIndex(DBconexion.COLUMN_ANUNCIO_ESTADO);

                int id = idIndex != -1 ? cursor.getInt(idIndex) : 0;
                String usuarioId = usuarioIndex != -1 ? cursor.getString(usuarioIndex) : "";
                int mascotaId = mascotaIndex != -1 ? cursor.getInt(mascotaIndex) : 0;
                String hora = horaIndex != -1 ? cursor.getString(horaIndex) : "";
                String estado = estadoIndex != -1 ? cursor.getString(estadoIndex) : "activo";

                lista.add(new Anuncio(id, usuarioId, mascotaId, hora, estado));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return lista;
    }

    public boolean desactivarAnuncio(int id) {
        SQLiteDatabase db = dbconexion.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBconexion.COLUMN_ANUNCIO_ESTADO, "inactivo");

        int rows = db.update(DBconexion.TABLE_ANUNCIOS, values, DBconexion.COLUMN_ANUNCIO_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rows > 0;
    }
}
