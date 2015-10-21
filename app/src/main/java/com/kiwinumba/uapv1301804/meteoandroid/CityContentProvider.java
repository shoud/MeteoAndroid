package com.kiwinumba.uapv1301804.meteoandroid;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.HashSet;


public class CityContentProvider extends ContentProvider
{

    //Lien util http://www.tutos-android.com/contentprovider-android
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

        queryBuilder.setTables(CityBDD.CITY_TABLE_NAME);

        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case WEATHER:
                break;
            case WEATHER_PAYS_VILLE:
                // adding the ID to the original query
                queryBuilder.appendWhere(CityBDD.CITY_KEY + "="
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
    public Uri insert(Uri uri, ContentValues values)
    {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = bdd.getWritableDatabase();
        int rowsDeleted = 0;
        long id = 0;
        switch (uriType) {
            case WEATHER:
                id = sqlDB.insert(CityBDD.CITY_TABLE_NAME, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = bdd.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriType) {
            case WEATHER:
                rowsDeleted = sqlDB.delete(CityBDD.CITY_TABLE_NAME, selection,
                        selectionArgs);
                break;
            case WEATHER_PAYS_VILLE:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(CityBDD.CITY_TABLE_NAME,
                            CityBDD.CITY_KEY + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(CityBDD.CITY_TABLE_NAME,
                            CityBDD.CITY_KEY + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = bdd.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType) {
            case WEATHER:
                rowsUpdated = sqlDB.update(CityBDD.CITY_TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            case WEATHER_PAYS_VILLE:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(CityBDD.CITY_TABLE_NAME,
                            values,
                            CityBDD.CITY_KEY + "=" + id,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(CityBDD.CITY_TABLE_NAME,
                            values,
                            CityBDD.CITY_KEY + "=" + id
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    private void checkColumns(String[] projection) {
        String[] available = { CityBDD.CITY_KEY,
                CityBDD.CITY_NOM,
                CityBDD.CITY_PAYS,
                CityBDD.CITY_VENT,
                CityBDD.CITY_TEMP,
                CityBDD.CITY_PRES,
                CityBDD.CITY_DATE};
        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
            // check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }
}
