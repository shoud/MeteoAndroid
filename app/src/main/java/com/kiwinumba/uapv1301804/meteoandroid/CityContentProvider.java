package com.kiwinumba.uapv1301804.meteoandroid;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


public class CityContentProvider extends ContentProvider
{
    //La base de donnnées
    private CityBDD cityBDD;
    //Utilisé pour le UriMacher
    private static final int WEATHER = 1;
    private static final int WEATHER_VILLE = 2;
    //L'autorité du provider
    private static final String PROVIDER_AUTHORITY = "com.kiwinumba.uapv1301804.provider.meteoandroid";
    private static final String BASE_PATH = CityBDD.CITY_TABLE_NAME;

    public static final Uri CONTENT_URI = new Uri.Builder().scheme(ContentResolver.SCHEME_CONTENT)
            .authority(PROVIDER_AUTHORITY)
            .appendEncodedPath(CityBDD.CITY_TABLE_NAME)
            .build();

    private static final UriMatcher uriMatcher  = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(PROVIDER_AUTHORITY, BASE_PATH, WEATHER);
        uriMatcher.addURI(PROVIDER_AUTHORITY, BASE_PATH + "/*/*", WEATHER_VILLE);
    }

    private static final int CITY_NOM = 1;
    private static final int CITY_PAYS = 2;

    @Override
    public boolean onCreate()
    {
        //Pour utiliser la base de donnée
        cityBDD = new CityBDD(getContext());
        return true ;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        Cursor cursor;
        int uriType = uriMatcher.match(uri);
        switch (uriType) {
            case WEATHER:
                cursor = cityBDD.selectionnerTout();
                break;
            case WEATHER_VILLE:
                List<String> pathSegments = uri.getPathSegments();
                String nom = pathSegments.get(CITY_NOM);
                String pays = pathSegments.get(CITY_PAYS);
                cursor = cityBDD.selectionnerVille(nom,pays);
                break;
            default:
                return null;
        }
        Context context = getContext();
        ContentResolver contentResolver = null;
        if(context != null)
            contentResolver = context.getContentResolver();
        if(contentResolver != null)
            cursor.setNotificationUri(contentResolver, uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri)
    {
        int uriType = uriMatcher.match(uri);
        switch(uriType)
        {
            case WEATHER:
                return ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + PROVIDER_AUTHORITY + ".weather";
            case WEATHER_VILLE:
                return ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + PROVIDER_AUTHORITY + ".weather";
            default:
                return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues)
    {
        if (uriMatcher.match(uri) == WEATHER_VILLE)
        {
            List<String> pathSegments = uri.getPathSegments();
            String nom = pathSegments.get(CITY_NOM);
            String pays = pathSegments.get(CITY_PAYS);
            return (cityBDD.ajouterVille(nom,pays) == -1) ? null :getUriVille(nom,pays);
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        if (uriMatcher.match(uri) == WEATHER_VILLE)
        {
            List<String> pathSegments = uri.getPathSegments();
            String nom = pathSegments.get(CITY_NOM);
            String pays = pathSegments.get(CITY_PAYS);
            return cityBDD.supprimerVille(nom, pays);
        }
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs)
    {
        if (uriMatcher.match(uri) == WEATHER_VILLE)
        {
            List<String> pathSegments = uri.getPathSegments();
            //Récupération du nom dans l'uri
            String nom = pathSegments.get(CITY_NOM);
            //Récupération du pays dans l'uri
            String pays = pathSegments.get(CITY_PAYS);

            Context context = getContext();
            ContentResolver contentResolver = null;
            if (context != null)
                contentResolver = context.getContentResolver();
            if (contentResolver != null)
                contentResolver.notifyChange(uri, null);
            return cityBDD.modifierVille(nom, pays, contentValues);
        }
            return 0;
    }

    /**
     * Méthode permettant de créer le lien rapidement pour un couple ville - pays
     * @param nom Le nom de la ville
     * @param pays Le nom du pays
     * @return L'uri créer pour utiliser le provider
     */
    public static Uri getUriVille(String nom, String pays) {
        return CityContentProvider.CONTENT_URI.buildUpon().appendPath(nom).appendPath(pays).build();
    }
}
