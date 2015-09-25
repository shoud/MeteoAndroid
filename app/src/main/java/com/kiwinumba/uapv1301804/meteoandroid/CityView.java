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
        TextView textView = (TextView)findViewById(R.id.tvVille);
        textView.setText(city.getNom());

        textView = (TextView)findViewById(R.id.tvPays);
        textView.setText(city.getPays());

        textView = (TextView)findViewById(R.id.tvVent);
        textView.setText(city.getVitesseVent());

        textView = (TextView)findViewById(R.id.tvPression);
        textView.setText(city.getPression() + " hPa");

        textView = (TextView)findViewById(R.id.tvTemp);
        textView.setText(city.getTempAir() + " °C");

        textView = (TextView)findViewById(R.id.tvDate);
        textView.setText(city.getDate());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_city_view, menu);
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
