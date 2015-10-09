package com.kiwinumba.uapv1301804.meteoandroid;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.HashMap;

/**
 * Created by uapv1301804 on 09/10/15.
 */
public class CityContentProvider extends ContentProvider
{

    //Lien util http://www.tutos-android.com/contentprovider-android
    //private DatabaseHelper dbHelper;
    //Le nom du provider
    static final String PROVIDER_NAME = "com.kiwinumba.uapv1301804.provider.meteoandroid";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme(ContentResolver.SCHEME_CONTENT)
            .authority(PROVIDER_NAME)
            .appendEncodedPath(Bdd.CITY_TABLE_NAME)
            .build();

    private static final int WEATHER = 1;
    private static final int WEATHER_PAYS_VILLE = 2;

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static
    {
        sURIMatcher.addURI(PROVIDER_NAME, "weather", WEATHER);
        sURIMatcher.addURI(PROVIDER_NAME, "weather/*/*", WEATHER_PAYS_VILLE);
    }

    public String getCity(Uri url)
    {
        int match = sURIMatcher.match(url);
        switch (match)
        {
            case WEATHER:
                return "vnd.android.cursor.dir/weather";
            case WEATHER_PAYS_VILLE:
                return "vnd.android.cursor.item/weather";
            default:
                return null;
        }
    }


    @Override
    public boolean onCreate() {
        return false ;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
