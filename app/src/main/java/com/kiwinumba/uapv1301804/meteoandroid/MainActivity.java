package com.kiwinumba.uapv1301804.meteoandroid;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Permet de directement créer une activity qui fait une liste.
 */
public class MainActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor>
{
    //Permet de récupérer la liste de ville dans la base de donnée
    private SimpleCursorAdapter simpleCursorAdapter;

    //Le code de la requête d'ajouter un résultat
    static final int PICK_AJOUTER_REQUEST = 36;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new CityBDD(this);

       simpleCursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, null,
                new String[]{CityBDD.CITY_NOM, CityBDD.CITY_PAYS},
                new int[]{android.R.id.text1, android.R.id.text2}, 0);
        setListAdapter(simpleCursorAdapter);

        getLoaderManager().initLoader(0, null, this);

        //Permet de supprimer une ville et détectant un clique long
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Récupération de la ville choisie
                final String nom = ((TextView) view.findViewById(android.R.id.text1)).getText().toString();
                final String pays = ((TextView) view.findViewById(android.R.id.text2)).getText().toString();
                //On créer une boite de dialogue
                new AlertDialog.Builder(MainActivity.this).setTitle("Suppression")
                        //Le message de la boite de dialogue
                        .setMessage("Voulez vous supprimer " + nom + " ?")
                                //L'icone de la boite de dialogue
                        .setIcon(android.R.drawable.ic_dialog_alert)
                                //Le bouton ok
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //Suppression dans la base de donnée
                                Uri uri = CityContentProvider.getUriVille(nom, pays);
                                getContentResolver().delete(uri, null, null);
                                //Rafraichissement de la liste
                                getLoaderManager().restartLoader(0, null, MainActivity.this);
                                //Le bouton annuler on quite la boite de dialogue
                            }
                        }).setNegativeButton(android.R.string.no, null).show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id == R.id.ajouter)
        {
            //Préparation à la création d'une activity
            Intent intent = new Intent(this, AddCityActivity.class);
            //Lancement de l'activity en signifiant qu'elle donne un résulat
            startActivityForResult(intent, PICK_AJOUTER_REQUEST);
        }
        else if(id == R.id.rafraichir)
        {
            //Uri pour récupérer l'ensemble des villes de la base de donnée
            Uri uri = CityContentProvider.CONTENT_URI.buildUpon().build();
            //Récupétation du curseur
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            //On parcour tout les enregistrement pour mettre à jour toutes les villes
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
                rafarichir(cursor.getString(cursor.getColumnIndex(CityBDD.CITY_NOM)),cursor.getString(cursor.getColumnIndex(CityBDD.CITY_PAYS)));
            cursor.close();
        }
        return super.onOptionsItemSelected(item);
    }

    void rafarichir(String nom, String pays)
    {
        //On lance le téléchargement des donnée en tache de fond
        Intent mServiceIntent = new Intent(this, MeteoUpDate.class);
        //Le nom de la ville à mettre à jour
        mServiceIntent.putExtra("NOM",nom);
        //Le nom du pays à mettre à jour
        mServiceIntent.putExtra("PAYS",pays);
        //Lancement du service
        startService(mServiceIntent);
    }

    /**
     * La méthode permettant de récupérer le résulat d'une activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Si on récupérer les données de l'activity Ajouter.
        if (requestCode == PICK_AJOUTER_REQUEST)
        {
            //On vérifie si la requet c'est bien passé
            if (resultCode == RESULT_OK)
            {
                //Récupération du nom de la ville rajouté
                String nom = data.getStringExtra("VILLE");
                //Récupération du nom du pays de la ville rajouté
                String pays = data.getStringExtra("PAYS");
                //Lancement d'un service pour la nouvelle ville
                rafarichir(nom,pays);
                //On met à jour la liste de ville
                getLoaderManager().restartLoader(0, null, this);
            }
        }
    }

    /**
     * Détection d'un simple clique sur une ville
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        //Préparation à la création d'une activity
        Intent intent = new Intent(this, CityView.class);
        //Récupération de la ville choisie
        final String nom = ((TextView) v.findViewById(android.R.id.text1)).getText().toString();
        final String pays = ((TextView) v.findViewById(android.R.id.text2)).getText().toString();
        //Permet d'envoyer la ville à la nouvelle activity
        intent.putExtra("InfoCity", CityContentProvider.getUriVille(nom, pays));
        //Lancement de l'activity pour afficher les infos
        startActivity(intent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args)
    {
        return new CursorLoader(this, CityContentProvider.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data)
    {
        ((SimpleCursorAdapter) getListAdapter()).changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
