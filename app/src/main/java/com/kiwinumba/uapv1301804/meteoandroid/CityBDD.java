package com.kiwinumba.uapv1301804.meteoandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by thomas on 03/10/2015.
 */
public class CityBDD
{

    public static final String CITY_KEY = "id";
    private static final int NUM_COL_ID = 0;
    public static final String CITY_NOM = "nom";
    private static final int NUM_COL_NOM = 1;
    public static final String CITY_PAYS = "pays";
    private static final int NUM_COL_PAYS = 2;
    public static final String CITY_VENT = "vent";
    private static final int NUM_COL_VENT = 3;
    public static final String CITY_TEMP = "temperature";
    private static final int NUM_COL_TEMP = 4;
    public static final String CITY_PRES = "pression";
    private static final int NUM_COL_PRES = 5;
    public static final String CITY_DATE = "date";
    private static final int NUM_COL_DATE = 6;
    //Le nom de la table dans la base de donnée
    public static final String CITY_TABLE_NAME = "City";

    private static final String CITY_TABLE_CREATE =
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


    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(CITY_TABLE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(CityBDD.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + CITY_TABLE_NAME);
        onCreate(database);
    }

    /*
    public CityBDD(Context context)
    {
        //On crée la BDD et sa table
        maBaseSQLite = new Bdd(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open(){
        //on ouvre la BDD en écriture
        bdd = maBaseSQLite.getWritableDatabase();
    }

    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    */

    /**
     * La méthode ajouté permet de rajouter une ligna dans la base de donnée
     * @param city L'objet a enregistré dans la base de donnée
     */
    /*public long ajouter(City city)
    {
        ContentValues values = new ContentValues();
        values.put(CITY_NOM, city.getNom());
        values.put(CITY_PAYS, city.getPays());
        values.put(CITY_VENT, "Null");
        values.put(CITY_TEMP, "Null");
        values.put(CITY_PRES, "Null");
        values.put(CITY_DATE, "Null");
        return bdd.insert(CITY_TABLE_NAME, null, values);
    }
*/
    /**
     * La méthode supprimé permet de supprimer une ville enregistré dans la base de donnée
     * @param city L'objet que l'on doit supprimer dans la base de données.
     */
    /*
    public int supprimer(City city)
    {
        return bdd.delete(CITY_TABLE_NAME, CITY_KEY + " = " + city.getId(), null);
    }
*/
    /**
     * La méthode permettant de mettre à jour la différentes valeurs d'une ville
     * dans la base de donnée.
     * @param city L'objet que l'on doit mettre à jour dans la base de donnée
     */
  /*
    public int modifier(City city)
    {
        ContentValues values = new ContentValues();
        values.put(CITY_VENT, city.getVitesseVent());
        values.put(CITY_TEMP, city.getTempAir());
        values.put(CITY_PRES, city.getPression());
        values.put(CITY_DATE, city.getDate());
        return bdd.update(CITY_TABLE_NAME, values, CityBDD.CITY_KEY + " = " + city.getId(),null);

    }
*/
    /**
     * Méthode permettant de récupérer tout les informations enregistré dans la base
     * pour une ville.
     */

  /*  public City selectionner(City city)
    {
        Cursor c = bdd.query(CITY_TABLE_NAME, new String[] {CITY_KEY, CITY_NOM, CITY_PAYS, CITY_VENT, CITY_TEMP, CITY_PRES, CITY_DATE}, CITY_KEY + " = " + city.getId() , null, null, null, null);
        return cursorToCity(c);
    }

    private City cursorToCity(Cursor c)
    {
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé une ville
        City city = new City(c.getInt(NUM_COL_ID), c.getString(NUM_COL_NOM), c.getString(NUM_COL_PAYS), c.getString(NUM_COL_VENT), c.getString(NUM_COL_TEMP), c.getString(NUM_COL_DATE), c.getString(NUM_COL_DATE));
        //On ferme le cursor
        c.close();
        //On retourne la ville
        return city;
    }
*/
    /**
     * Permet de sélectionner toutes les villes présente dans la base de donnée
     * @return La liste des ville présente dans la base de donnée
     */
  /*  public ArrayList<City> selectionnerAll()
    {
        ArrayList<City> listCity = new ArrayList<City>();
        Cursor c = bdd.rawQuery("select * from " + CITY_TABLE_NAME,null);
        while (c.moveToNext())
        {
            listCity.add(new City(c.getLong(NUM_COL_ID), c.getString(NUM_COL_NOM), c.getString(NUM_COL_PAYS), c.getString(NUM_COL_VENT), c.getString(NUM_COL_TEMP), c.getString(NUM_COL_PRES), c.getString(NUM_COL_DATE)));
        }
        c.close();
        return listCity;
    }
*/
}
