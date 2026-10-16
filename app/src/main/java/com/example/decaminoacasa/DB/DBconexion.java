package com.example.decaminoacasa.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBconexion extends SQLiteOpenHelper {

    private static final String DB_NAME = "DB_CaminoACasa.db";
    private static final int DB_VERSION = 4;

    public static final String TABLE_USUARIOS = "usuarios";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_TELEFONO = "telefono";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_ROL = "rol";
    public static final String COLUMN_ESTADO = "estado";

    public DBconexion(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_USUARIOS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOMBRE + " TEXT, " +
                COLUMN_TELEFONO + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_ROL + " TEXT, " +
                COLUMN_ESTADO + " TEXT)";
        db.execSQL(createTable);

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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
        onCreate(db);
    }
}
