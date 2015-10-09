package com.kiwinumba.uapv1301804.meteoandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Classe permettant de rajouter une ville
 */
public class AddCityActivity extends Activity {

    //Le nom de la ville
    String ville = null;
    //Le nom du pays où réside la ville
    String pays = null;
    //Permet de faire des enregistrement dans la base de donnée
    CityBDD cityBDD;// = new CityDAO(getApplicationContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
    }

    /**
     * Méthode lancé lors de l'appui sur le bouton sauvegarder
     * @param view
     */
    public void sauvegarder(View view)
    {
        //Récupération de la ville
        EditText editText = (EditText)findViewById(R.id.eTVille);
        ville = editText.getText().toString();
        //Récupératiton du pays
        editText = (EditText)findViewById(R.id.eTPays);
        pays = editText.getText().toString();
        //Vérification d'un nom de ville présent
        if(ville.isEmpty())
            //Si pas de nom on affiche un message
            Toast.makeText(getApplicationContext(), "Pas de nom de ville", Toast.LENGTH_SHORT).show();
        //Vérification d'un nom de pays présent
        else if(pays.isEmpty())
            //Si pas de nom de pays on affiche un méssage
            Toast.makeText(getApplicationContext(), "Pas de Pays", Toast.LENGTH_SHORT).show();
        else
        {
            //Création du nouveau objet ville
            City city = new City(ville, pays);
            //Enregistrement du nouvelle objet dans la base de donnée
            cityBDD = new CityBDD(getApplicationContext());
            cityBDD.open();
            cityBDD.ajouter(city);
            cityBDD.close();
            //Création de l'objet à retourner
            Intent intent = new Intent();
            //On retourne l'objet city
            intent.putExtra("VilleAjouter", city);
            //On dit que la requet est ok
            setResult(Activity.RESULT_OK, intent);
            //Fermeture de l'activity
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_city, menu);
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
}
