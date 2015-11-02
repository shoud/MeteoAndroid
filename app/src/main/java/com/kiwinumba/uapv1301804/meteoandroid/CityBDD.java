package com.kiwinumba.uapv1301804.meteoandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Classe permettant de gérer la base de donnée de villes
 */
public class CityBDD extends SQLiteOpenHelper
{
    //Le nom de la base de donnée
    private static final String CITY_BASE_NAME = "kiwinumba.db";
    //Le nom de la table dans la base de donnée
    public static final String CITY_TABLE_NAME = "City";
    //Le nom des champs  de la base de donnée
    public static final String CITY_KEY = "_id";
    public static final String CITY_NOM = "nom";
    public static final String CITY_PAYS = "pays";
    public static final String CITY_VENT = "vent";
    public static final String CITY_TEMP = "temperature";
    public static final String CITY_PRES = "pression";
    public static final String CITY_DATE = "date";

    private static final String CITY_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + CITY_TABLE_NAME + " (" +
                    CITY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CITY_NOM + " TEXT, " +
                    CITY_PAYS + " TEXT, " +
                    CITY_VENT + " TEXT, " +
                    CITY_TEMP + " TEXT, " +
                    CITY_PRES + " TEXT, " +
                    CITY_DATE + " TEXT, " +
                    "UNIQUE (" +CITY_NOM +","+ CITY_PAYS + " ));";
    public static final String CITY_TABLE_DROP = "DROP TABLE IF EXISTS " + CITY_TABLE_NAME + ";";

    public CityBDD(Context context) {
        super(context, CITY_BASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(CITY_TABLE_CREATE);
    }

    /**
     * Méthode permettant de rajouter une ville dans la base de donnée
     * @param nom Le nom de la ville à rajouter
     * @param pays Le nom du pays à rajouter
     * @return le résultat de la requete
     */
    public long ajouterVille(String nom, String pays)
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CITY_NOM, nom);
        values.put(CITY_PAYS, pays);
        return sqLiteDatabase.insert(CITY_TABLE_NAME, null, values);
    }

    /**
     * Méthode permetttant de supprimer une ville de la base de donnée
     * @param nom Le nom de la ville a supprimer
     * @param pays Le nom du pays à supprimer
     * @return Le resultat de la requete
     */
    public int supprimerVille(String nom, String pays)
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete(CITY_TABLE_NAME, CITY_NOM + "=? AND " + CITY_PAYS + "=?", new String[]{nom, pays});
    }

    /**
     * Méthode permettant de mettre à jour une ville dans la base de donnée
     * @param nom Le nom de la ville à mettre à jour
     * @param pays Le nom du pays à mettre à jour
     * @param contentValues Les nouvelles valeurs
     * @return Le resultat de la requete
     */
    public int modifierVille(String nom, String pays, ContentValues contentValues)
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.update(CITY_TABLE_NAME, contentValues, CITY_NOM + "=? AND " + CITY_PAYS + "=?",
                new String[]{nom, pays});
    }

    /**
     * Méthode permettant de renvoyer un curseur sur la ville que l'on veut lire
     * @param nom Le nom de la ville pour laquelle on veut lire les valeurs
     * @param pays Le nom du pays de la ville pour laquelle on veut lire les valeurs
     * @return Le curseur sur la ville voulue dans la base de donnée
     */
    public Cursor selectionnerVille(String nom, String pays)
    {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.query(CITY_TABLE_NAME, null, CITY_NOM + "=? AND " + CITY_PAYS + "=?",
                new String[]{nom, pays}, null, null, null, null);
    }

    /**
     * M"thode permettant de récupérer toutes les villes de la base de donnée
     * @return Un curseur sur tout les villes de la base de donnée
     */
    public Cursor selectionnerTout()
    {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        final String selectQuery = "SELECT * FROM " + CITY_TABLE_NAME;
        return sqLiteDatabase.rawQuery(selectQuery, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {
        if (oldVersion != newVersion)
        {
            sqLiteDatabase.execSQL(CITY_TABLE_DROP);
            onCreate(sqLiteDatabase);
        }
    }
}
