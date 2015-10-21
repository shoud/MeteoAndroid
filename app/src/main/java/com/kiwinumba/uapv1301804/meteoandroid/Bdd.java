package com.kiwinumba.uapv1301804.meteoandroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Classe permettant de gérer la base de donnée de l'application
 * Created by thomas on 02/10/2015.
 */
public class Bdd extends SQLiteOpenHelper
{

    //Le nom de la base de données
    private static final String DATABASE_NAME = "kiwinumba.db";
    //La version de la base
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructeur pour la base de donnée
     * @param context le context de l'application
     */
    public Bdd(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        CityBDD.onCreate(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
    {
        CityBDD.onUpgrade(database, oldVersion, newVersion);
    }
}
