package com.kiwinumba.uapv1301804.meteoandroid;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;


public class CityContentProvider extends ContentProvider
{

    //Lien util http://www.tutos-android.com/contentprovider-android
    //private DatabaseHelper dbHelper;

    //La base de donnnées
    private Bdd bdd;
    //Utilisé pour le UriMacher
    private static final int WEATHER = 10;
    private static final int WEATHER_PAYS_VILLE = 20;
    //L'autorité du provider
    private static final String PROVIDER_AUTHORITY = "com.kiwinumba.uapv1301804.provider.meteoandroid";
    private static final String BASE_PATH = "weather";

    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_AUTHORITY
            + "/" + BASE_PATH);


    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/weather";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/City";

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(PROVIDER_AUTHORITY, BASE_PATH, WEATHER);
        sURIMatcher.addURI(PROVIDER_AUTHORITY, BASE_PATH + "/*/*", WEATHER_PAYS_VILLE);
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
    public boolean onCreate()
    {
        bdd = new Bdd(getContext());
        return false ;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        checkColumns(projection);

        queryBuilder.setTables(Bdd.CITY_TABLE_NAME);

        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case WEATHER:
                break;
            case WEATHER_PAYS_VILLE:
                // adding the ID to the original query
                queryBuilder.appendWhere(Bdd.COLUMN_ID + "="
                        + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = bdd.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        // make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
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
