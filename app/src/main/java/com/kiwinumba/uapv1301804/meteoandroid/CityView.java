package com.kiwinumba.uapv1301804.meteoandroid;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class CityView extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_view);

        Intent intent = getIntent();
        City city = (City)intent.getSerializableExtra("InfoCity");
        setInfo(city);

    }

    /**
     * Permet de mettre à jour les valeurs pour l'affichage
     * récupéré dans la liste de ville.
     * @param city
     */
    private void setInfo(City city)
    {
        //Récupération du TextView du nom de la ville
        TextView textView = (TextView)findViewById(R.id.tvVille);
        //Mise à jour du nom de la ville
        textView.setText(city.getNom());

        //Récupération du TextView du nom du pays
        textView = (TextView)findViewById(R.id.tvPays);
        //Mise à jour du nom du pays
        textView.setText(city.getPays());

        //Récupération du TextView de la vitesse et du sens du vent
        textView = (TextView)findViewById(R.id.tvVent);
        //Mise à jour de la vitesse et de la direction du vent
        textView.setText(city.getVitesseVent());

        //Récupération du TextView de la pression
        textView = (TextView)findViewById(R.id.tvPression);
        //Mise à jour de la pression
        textView.setText(city.getPression());

        //Récupération du TextView de la température
        textView = (TextView)findViewById(R.id.tvTemp);
        //Mise à jour de la température
        textView.setText(city.getTempAir());

        //Récupération du TextView de la date
        textView = (TextView)findViewById(R.id.tvDate);
        //Mise à jour de la date
        textView.setText(city.getDate());
    }
}
