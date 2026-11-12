package com.example.decaminoacasa.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * esto es la clase que se encarga de gestionar la conexión, la creación y la actualización de la base de datos SQLite local de la app.
 */
public class DBconexion extends SQLiteOpenHelper {

    private static final String DB_NAME = "DB_CaminoACasa.db";
    private static final int DB_VERSION = 5;

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
    public static final String COLUMN_MASCOTA_ESTADO = "estado";

    /**
     * Constructor para iniciar la conexión con la base de datos de SQLite.
     *
     * @param context Contexto de la aplicación o la activity actual.
     */
    public DBconexion(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Se ejecuta una unica vez al crear la base de datos, generando las tablas e insertando datos por defecto.
     *
     * @param db Instancia de la base de datos SQLite.
     */
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
                COLUMN_MASCOTA_CREADOR + " TEXT, " +
                COLUMN_MASCOTA_ESTADO + " TEXT)";
        db.execSQL(createTableMascotas);

        ContentValues cvJoaquin = new ContentValues();
        cvJoaquin.put(COLUMN_NOMBRE, "joaquin");
        cvJoaquin.put(COLUMN_TELEFONO, "+56912345678");
        cvJoaquin.put(COLUMN_PASSWORD, "12345");
        cvJoaquin.put(COLUMN_ROL, "user");
        cvJoaquin.put(COLUMN_ESTADO, "activo");
        db.insert(TABLE_USUARIOS, null, cvJoaquin);

        ContentValues cvAdmin = new ContentValues();
        cvAdmin.put(COLUMN_NOMBRE, "admin");
        cvAdmin.put(COLUMN_TELEFONO, "+56999999999");
        cvAdmin.put(COLUMN_PASSWORD, "admin123");
        cvAdmin.put(COLUMN_ROL, "admin");
        cvAdmin.put(COLUMN_ESTADO, "activo");
        db.insert(TABLE_USUARIOS, null, cvAdmin);
    }

    /**
     * Se ejecuta cuando se cambia la versión de la base de datos, se elimina las tablas anteriores y las vuelve a recrear.
     *
     * @param db Instancia de la base de datos SQLite.
     * @param oldVersion Versión anterior de la base de datos.
     * @param newVersion Nueva versión de la base de datos.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MASCOTAS);
        onCreate(db);
    }
}
