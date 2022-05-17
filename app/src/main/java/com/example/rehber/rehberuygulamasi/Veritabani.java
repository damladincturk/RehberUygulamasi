package com.example.rehber.rehberuygulamasi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hakan on 1.05.2017.
 */

public class Veritabani extends SQLiteOpenHelper {




    private static  final String VERİTABANİ_ADİ="kayitlar";
    private static  final int SURUM=1;

    public Veritabani(Context c) {
        super(c, VERİTABANİ_ADİ, null, SURUM);
    }

    public Veritabani(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

       db.execSQL("CREATE TABLE bilgiler(id INTEGER PRIMARY KEY  AUTOINCREMENT,ad TEXT,telefon TEXT,eposta TEXT,muzik TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXIST bilgiler");
        onCreate(db);

    }













}
