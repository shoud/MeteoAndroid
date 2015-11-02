package com.kiwinumba.uapv1301804.meteoandroid;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Classe permettant d'afficher les informations météo d'une ville
 */
public class CityView extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {

    Uri uri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_view);

        Intent intent = getIntent();
        uri = intent.getParcelableExtra("InfoCity");
        getLoaderManager().initLoader(0, null, this);
        setInfo();

    }

    /**
     * Permet de mettre à jour les valeurs pour l'affichage
     * récupéré dans la liste de ville.
     */
    private void setInfo()
    {
        //Récupération des informations dans la base de donnée
        getLoaderManager().initLoader(0, null, this);
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if(cursor != null) {
            cursor.moveToFirst();
            //Récupération du TextView du nom de la ville
            TextView textView = (TextView)findViewById(R.id.tvVille);
            //Mise à jour du nom de la ville
            textView.setText(cursor.getString(cursor.getColumnIndex(CityBDD.CITY_NOM)));

            //Récupération du TextView du nom du pays
            textView = (TextView)findViewById(R.id.tvPays);
            //Mise à jour du nom du pays
            textView.setText(cursor.getString(cursor.getColumnIndex(CityBDD.CITY_PAYS)));

            //Récupération du TextView de la vitesse et du sens du vent
            textView = (TextView)findViewById(R.id.tvVent);
            //Mise à jour de la vitesse et de la direction du vent
            textView.setText(cursor.getString(cursor.getColumnIndex(CityBDD.CITY_VENT)));

            //Récupération du TextView de la pression
            textView = (TextView)findViewById(R.id.tvPression);
            //Mise à jour de la pression
            textView.setText(cursor.getString(cursor.getColumnIndex(CityBDD.CITY_PRES)));

            //Récupération du TextView de la température
            textView = (TextView)findViewById(R.id.tvTemp);
            //Mise à jour de la température
            textView.setText(cursor.getString(cursor.getColumnIndex(CityBDD.CITY_TEMP)));

            //Récupération du TextView de la date
            textView = (TextView)findViewById(R.id.tvDate);
            //Mise à jour de la date
            textView.setText(cursor.getString(cursor.getColumnIndex(CityBDD.CITY_DATE)));
            cursor.close();
        } else
            finish();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args)
    {
        return new CursorLoader(this, uri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data)
    {
        if (data != null) {
            data.moveToFirst();
            TextView textView;
            textView = (TextView) this.findViewById(R.id.tvVent);
            textView.setText(data.getString(data.getColumnIndex(CityBDD.CITY_VENT)));
            textView = (TextView) this.findViewById(R.id.tvPression);
            textView.setText(data.getString(data.getColumnIndex(CityBDD.CITY_PRES)));
            textView = (TextView) this.findViewById(R.id.tvTemp);
            textView.setText(data.getString(data.getColumnIndex(CityBDD.CITY_TEMP)));
            textView = (TextView) this.findViewById(R.id.tvDate);
            textView.setText(data.getString(data.getColumnIndex(CityBDD.CITY_DATE)));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
