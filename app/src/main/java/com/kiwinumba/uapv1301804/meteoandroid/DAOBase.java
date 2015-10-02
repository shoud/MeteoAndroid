package com.kiwinumba.uapv1301804.meteoandroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by thomas on 03/10/2015.
 */
public abstract class DAOBase
{
    protected final static int VERSION = 1;
    // Le nom du fichier qui représente la base de données
    protected final static String NOM = "kiwinumba.db";

    protected SQLiteDatabase mDb = null;
    protected Bdd mHandler = null;

    public DAOBase(Context pContext) {
        this.mHandler = new Bdd(pContext, NOM, null, VERSION);
    }

    public SQLiteDatabase open() {
        // Pas besoin de fermer la dernière base puisque getWritableDatabase s'en charge
        mDb = mHandler.getWritableDatabase();
        return mDb;
    }

    public void close() {
        mDb.close();
    }

    public SQLiteDatabase getDb() {
        return mDb;
    }
}
