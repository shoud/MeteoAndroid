package com.kiwinumba.uapv1301804.meteoandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by thomas on 03/10/2015.
 */
public class CityDAO extends DAOBase
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

    public CityDAO(Context pContext)
    {
        super(pContext);
    }

    /**
     * La méthode ajouté permet de rajouter une ligna dans la base de donnée
     * @param city L'objet a enregistré dans la base de donnée
     */
    public void ajouter(City city)
    {
        ContentValues value = new ContentValues();
        value.put(CityDAO.CITY_NOM, city.getNom());
        value.put(CityDAO.CITY_PAYS, city.getPays());
        value.put(CityDAO.CITY_VENT, "Null");
        value.put(CityDAO.CITY_TEMP, "Null");
        value.put(CityDAO.CITY_PRES, "Null");
        value.put(CityDAO.CITY_DATE, "Null");
        mDb.insert(CityDAO.CITY_TABLE_NAME, null, value);
    }

    /**
     * La méthode supprimé permet de supprimer une ville enregistré dans la base de donnée
     * @param city L'objet que l'on doit supprimer dans la base de données.
     */
    public void supprimer(City city)
    {
        mDb.delete(CityDAO.CITY_TABLE_NAME, CityDAO.CITY_KEY + " = ?", new String[]{String.valueOf(city.getId())});
    }

    /**
     * La méthode permettant de mettre à jour la différentes valeurs d'une ville
     * dans la base de donnée.
     * @param city L'objet que l'on doit mettre à jour dans la base de donnée
     */
    public void modifier(City city)
    {
        ContentValues value = new ContentValues();
        value.put(CityDAO.CITY_VENT, city.getVitesseVent());
        value.put(CityDAO.CITY_TEMP, city.getTempAir());
        value.put(CityDAO.CITY_PRES, city.getPression());
        value.put(CityDAO.CITY_DATE, city.getDate());
        mDb.update(CityDAO.CITY_TABLE_NAME, value, CityDAO.CITY_KEY + " = ?", new String[]{String.valueOf(city.getId())});

    }

    /**
     * Méthode permettant de récupérer tout les informations enregistré dans la base
     * pour une ville.
     */
    public City selectionner(City city)
    {
        Cursor c = mDb.rawQuery("select * from " + CITY_TABLE_NAME + " where id = ", new String[]{city.getIdString()});
        city.setVitesseVent(c.getString(3));
        city.setTempAir(c.getString(4));
        city.setPression(c.getString(5));
        city.setDate(c.getString(6));
        c.close();
        return city;
    }

    /**
     * Permet de sélectionner toutes les villes présente dans la base de donnée
     * @return La liste des ville présente dans la base de donnée
     */
    public ArrayList<City> selectionnerAll()
    {
        ArrayList<City> listCity = new ArrayList<City>();
        Cursor c = mDb.rawQuery("select * from " + CITY_TABLE_NAME,null);
        while (c.moveToNext())
        {
            listCity.add(new City(c.getLong(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6)));
        }
        c.close();
        return listCity;
    }

}
