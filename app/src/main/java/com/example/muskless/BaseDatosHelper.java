package com.example.muskless;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BaseDatosHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "musklessDatabase";
    private static final int DATABASE_VERSION = 1;

    public BaseDatosHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(EstructuraBBDD.SQL_CREATE_ENTRIES_USERS);
        db.execSQL(EstructuraBBDD.SQL_CREATE_ENTRIES_MESSAGES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(EstructuraBBDD.SQL_DELETE_ENTRIES);
        onCreate(db);

    }
}
