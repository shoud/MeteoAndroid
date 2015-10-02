package com.kiwinumba.uapv1301804.meteoandroid;

/**
 * Created by thomas on 03/10/2015.
 */
public class CityDAO //extends DAOBase https://openclassrooms.com/courses/creez-des-applications-pour-android/les-bases-de-donnees-5
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


    public void ajouter(City city) {
        // CODE
    }


    public void supprimer(long id) {
        // CODE
    }


    public void modifier(City city) {
        // CODE
    }


    public City selectionner(long id) {
        // CODE
        return null;
    }

}
