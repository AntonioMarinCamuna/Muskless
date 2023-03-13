package com.example.muskless;

/*
 * Definimos los importes necesarios para el funcionamiento de la aplicación.
 * */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BaseDatosHelper extends SQLiteOpenHelper {

    //Definimos los datos relativos a la BD.
    private static final String DATABASE_NAME = "musklessDatabase";
    private static final int DATABASE_VERSION = 1;

    public BaseDatosHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Ejecutamos las querys de creación de las tablas.
        db.execSQL(EstructuraBBDD.SQL_CREATE_ENTRIES_USERS);
        db.execSQL(EstructuraBBDD.SQL_CREATE_ENTRIES_MESSAGES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Eliminamos las tablas existentes y creamos la BD nuevamente.
        db.execSQL(EstructuraBBDD.SQL_DELETE_ENTRIES);
        onCreate(db);

    }
}
