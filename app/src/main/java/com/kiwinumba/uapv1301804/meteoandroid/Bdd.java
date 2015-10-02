package com.kiwinumba.uapv1301804.meteoandroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Classe permettant de gérer la base de donnée de l'application
 * Created by thomas on 02/10/2015.
 */
public class Bdd extends SQLiteOpenHelper
{
    public static final String CITY_KEY = "id";
    public static final String CITY_NOM = "nom";
    public static final String CITY_PAYS = "pays";
    public static final String CITY_VENT = "vent";
    public static final String CITY_TEMP = "temperature";
    public static final String CITY_PRES = "pression";
    public static final String CITY_DATE = "date";

    public static final String CITY_TABLE_NAME = "City";
    public static final String CITY_TABLE_CREATE =
            "CREATE TABLE " + CITY_TABLE_NAME + " (" +
                    CITY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CITY_NOM + " TEXT, " +
                    CITY_PAYS + " TEXT, " +
                    CITY_VENT + " TEXT, " +
                    CITY_TEMP + " TEXT, " +
                    CITY_PRES + " TEXT, " +
                    CITY_DATE + " TEXT, " +
                    "UNIQUE (" +CITY_NOM +" "+ CITY_PAYS + " ));";
    public static final String CITY_TABLE_DROP = "DROP TABLE IF EXISTS " + CITY_TABLE_NAME + ";";

    public Bdd(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CITY_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(CITY_TABLE_DROP);
        onCreate(db);
    }

}
