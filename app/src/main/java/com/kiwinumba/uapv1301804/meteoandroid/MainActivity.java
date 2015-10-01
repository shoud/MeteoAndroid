package com.kiwinumba.uapv1301804.meteoandroid;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;

/**
 * Permet de directement créer une activity qui fait une liste.
 */
public class MainActivity extends ListActivity {

    //Liste des villes
    private ArrayList<City> listCity = new ArrayList<City>();
    //Le code de la requête d'ajouter un résulta
    static final int PICK_AJOUTER_REQUEST = 36;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //On rajoute des villes dans la liste de ville
        listCity.add(new City("Brest", "France"));
        listCity.add(new City("Marseille", "France"));
        listCity.add(new City("Montreal", "Canada"));
        listCity.add(new City("Istanbul", "Turkey"));
        listCity.add(new City("Seoul", "Korea"));

        //Création de l'affichage des villes
        ArrayAdapter<City> cityArrayAdapter = new ArrayAdapter<City>(this,android.R.layout.simple_list_item_1,android.R.id.text1,listCity);
        setListAdapter(cityArrayAdapter);

        //Permet de supprimer une ville et détectant un clique long
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                //Récupération de la ville choisie
                final City city = listCity.get(i);
                //On créer une boite de dialogue
                new AlertDialog.Builder(MainActivity.this).setTitle("Suppression")
                        //Le message de la boite de dialogue
                        .setMessage("Voulez vous supprimer " + city.getNom() + " ?")
                        //L'icone de la boite de dialogue
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        //Le bouton ok
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton)
                            {
                                //Suppression de la ville dans la liste
                                listCity.remove(city);
                                //Rafraichissement de la liste
                                ((ArrayAdapter)getListAdapter()).notifyDataSetChanged();
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
        return super.onOptionsItemSelected(item);
    }

    /**
     * La méthode permettant de récupérer le résulat d'une activity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Si on récupérer les données de l'activity Ajouter.
        if (requestCode == PICK_AJOUTER_REQUEST) {
            //On vérifie si la requet c'est bien passé
            if (resultCode == RESULT_OK) {
                City city = (City)data.getSerializableExtra("VilleAjouter");
                //On vérifie si la ville n'a pas déjà été ajouté
                for(int i=0; i<listCity.size(); i++)
                    //Si la ville est déjà dans la liste
                    if(listCity.get(i).getNom().toLowerCase().equals(city.getNom().toLowerCase()))
                    {
                        //Si le pays est le même
                        if(listCity.get(i).getPays().toLowerCase().equals(city.getPays().toLowerCase()))
                        {
                            //Message signifiant à l'utilisateur que la ville est déjà présente
                            Toast.makeText(getApplicationContext(), "La ville existe déjà", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                //On rajoute la nouvelle ville à la liste de ville
                listCity.add(city);
                //On rafraichie la liste de ville
                ((ArrayAdapter)getListAdapter()).notifyDataSetChanged();
                //Affichage à l'utilisateur que la ville a bien été ajouté
                Toast.makeText(getApplicationContext(), city.getNom() + " de " + city.getPays() + " ajouté", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Détection d'un simple clique sur une ville
     * @param l
     * @param v
     * @param position
     * @param id
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        //Préparation à la création d'une activity
        Intent intent = new Intent(this, CityView.class);
        //Récupération de la ville choisie
        City city = listCity.get(position);
        //Permet d'envoyer la ville à la nouvelle activity
        intent.putExtra("InfoCity",city);
        //Lancement de l'activity pour afficher les infos
        startActivity(intent);
    }
}
