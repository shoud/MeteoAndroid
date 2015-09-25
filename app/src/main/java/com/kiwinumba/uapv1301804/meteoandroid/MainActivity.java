package com.kiwinumba.uapv1301804.meteoandroid;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.util.ArrayList;

/**
 * Permet de directement créer une activity qui fait une liste.
 */
public class MainActivity extends ListActivity {

    private ArrayList<City> listCity = new ArrayList<City>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //On rajoute des villes dans la liste de ville
        listCity.add(new City("Brest", "France"));
        listCity.add(new City("Marseille", "France"));
        listCity.add(new City("Montreal", "Canada"));
        listCity.add(new City("Istanbul", "Turkey"));
        listCity.add(new City("Seoul", "Korea"));

        ArrayAdapter<City> cityArrayAdapter = new ArrayAdapter<City>(this,android.R.layout.simple_list_item_1,android.R.id.text1,listCity);
        setListAdapter(cityArrayAdapter);



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
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        Intent intent = new Intent(this, CityView.class);
        //Récupération de la ville choisie
        City city = listCity.get(position);
        //Permet d'envoyer la ville à la nouvelle activity
        intent.putExtra("InfoCity",city);
        //Lancement de l'activity pour afficher les infos
        startActivity(intent);
    }
}
