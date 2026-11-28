package com.example.decaminoacasa.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBconexion extends SQLiteOpenHelper {

    private static final String DB_NAME = "DB_CaminoACasa.db";
    private static final int DB_VERSION = 8;

    public static final String TABLE_USUARIOS = "usuarios";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_TELEFONO = "telefono";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_ROL = "rol";
    public static final String COLUMN_ESTADO = "estado";

    public static final String TABLE_MASCOTAS = "mascotas";
    public static final String COLUMN_MASCOTA_ID = "id";
    public static final String COLUMN_MASCOTA_NOMBRE = "nombre";
    public static final String COLUMN_MASCOTA_COLOR = "color";
    public static final String COLUMN_MASCOTA_INFO = "informacion";
    public static final String COLUMN_MASCOTA_GPS = "ubicacion_gps";
    public static final String COLUMN_MASCOTA_CREADOR = "usuario_creador";

    public static final String TABLE_ANUNCIOS = "anuncios";
    public static final String COLUMN_ANUNCIO_ID = "id";
    public static final String COLUMN_ANUNCIO_USUARIO = "usuario_id";
    public static final String COLUMN_ANUNCIO_MASCOTA = "mascota_id";
    public static final String COLUMN_ANUNCIO_HORA = "hora";
    public static final String COLUMN_ANUNCIO_ESTADO = "estado";

    public DBconexion(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableUsuarios = "CREATE TABLE " + TABLE_USUARIOS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOMBRE + " TEXT, " +
                COLUMN_TELEFONO + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_ROL + " TEXT, " +
                COLUMN_ESTADO + " TEXT)";
        db.execSQL(createTableUsuarios);

        String createTableMascotas = "CREATE TABLE " + TABLE_MASCOTAS + " (" +
                COLUMN_MASCOTA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MASCOTA_NOMBRE + " TEXT, " +
                COLUMN_MASCOTA_COLOR + " TEXT, " +
                COLUMN_MASCOTA_INFO + " TEXT, " +
                COLUMN_MASCOTA_GPS + " TEXT, " +
                COLUMN_MASCOTA_CREADOR + " TEXT)";
        db.execSQL(createTableMascotas);

        String createTableAnuncios = "CREATE TABLE " + TABLE_ANUNCIOS + " (" +
                COLUMN_ANUNCIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ANUNCIO_USUARIO + " TEXT, " +
                COLUMN_ANUNCIO_MASCOTA + " INTEGER, " +
                COLUMN_ANUNCIO_HORA + " TEXT, " +
                COLUMN_ANUNCIO_ESTADO + " TEXT)";
        db.execSQL(createTableAnuncios);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MASCOTAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANUNCIOS);
        onCreate(db);
    }
}
