package com.kiwinumba.uapv1301804.meteoandroid;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

/**
 * Classe permettant de se connecter au service web de météo
 * pour mettre à jour les villes
 * Created by thomas on 01/10/2015.
 */
public class MeteoUpDate extends IntentService
{
    //L'adresse du service web avec le format de la requete
    public static final String URLServiceWeb = "http://www.webservicex.net/globalweather.asmx/GetWeather?CityName=%s&CountryName=%s";
    //L'encodage utilisé
    private static final String encodage = "UTF-8";
    //Enum pour rendre plus claire la lecture du tableau de données
    private enum Donnee{VENT, TEMP, PRESSION, DATE}
    private City city;

    public MeteoUpDate()
    {
        super("RafraichirService");
    }

    /**
     * La méthode permettant de récupérer la informations sur le service web
     * pour mettre à jour la liste de classe des villes
     */
    @Override
    protected void onHandleIntent(Intent intent)
    {
        //Utilisation de l'handler
        XMLResponseHandler xmlResponseHandler = new XMLResponseHandler();
        URL url;
        URLConnection urlConnection;
        InputStream inputStream = null;

        try
        {
            //Récupération de la ville choisie
            city = (City)intent.getSerializableExtra("Ville");
            Log.i("MeteoUpDate",city.toString());
            //Récupération du nom de la ville
            String nom = URLEncoder.encode(city.getNom(),encodage);
            //Récupération du nom du pays
            String pays = URLEncoder.encode(city.getPays(),encodage);
            //Création de le l'url avec la requet
            url = new URL(String.format(URLServiceWeb,nom,pays));
            //On lance la requet
            urlConnection = url.openConnection();
            //Récupération de la réponse
            inputStream = urlConnection.getInputStream();
            //Mise sous forme de tableau de la réponse du service web
            List<String> infos = xmlResponseHandler.handleResponse(inputStream,encodage);
            //Lecture des valeur du tableau
            if (infos.size() == 4)
            {
                //Mise à jour de la vitesse et de la direction du vent
                city.setVitesseVent(infos.get(Donnee.VENT.ordinal()));
                Log.i("MeteoUpDate", "Vent = " + infos.get(Donnee.VENT.ordinal()));
                //Mise à jour de la température
                city.setTempAir(infos.get(Donnee.TEMP.ordinal()));
                Log.i("MeteoUpDate", "Temp = " + city.getTempAir());
                //Mise à jour de la pression
                city.setPression(infos.get(Donnee.PRESSION.ordinal()));
                Log.i("MeteoUpDate", "Pression = " + infos.get(Donnee.PRESSION.ordinal()));
                //Mise à jour de la date
                city.setDate(infos.get(Donnee.DATE.ordinal()));
                Log.i("MeteoUpDate", "Date = " + infos.get(Donnee.DATE.ordinal()));
            }

        }
        catch (IOException e)
        {
            //Affichage de l'érreur dans le log
            Log.e("Service Erreur : ", city + " : " + e.toString());
        }

        try
        {
            if (inputStream != null)
                inputStream.close();
        }
        catch (IOException e)
        {
            //Affichage de l'érreur dans le log
            Log.e("Service Erreur :", city + " : " + e.toString());
        }
    }

}
