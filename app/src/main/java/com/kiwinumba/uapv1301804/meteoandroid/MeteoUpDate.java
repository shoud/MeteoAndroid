package com.kiwinumba.uapv1301804.meteoandroid;

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
public class MeteoUpDate
{
    //L'adresse du service web
    public static final String URLServiceWeb = "http://www.webservicex.net/globalweather.asmx/GetWeather?CityName=%s&CountryName=%s";
    //L'encodage utilisé
    private static final String encodage = "UTF-8";
    private enum Donnee{VENT, TEMP, PRESSION, DATE}

    /**
     * La méthode permettant de récupérer la informations sur le service web
     * pour mettre à jour la liste de classe des villes
     * @param villes les villes à mettre à jour
     */
    public static void upDateInfo(List<City> villes)
    {
        //Utilisation de l'handler
        XMLResponseHandler xmlResponseHandler = new XMLResponseHandler();
        //On met à jour toutes les villes disponible dans la liste
        for (City city : villes)
        {
            URL url;
            URLConnection urlConnection;
            InputStream inputStream = null;

            try
            {
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
                    //Mise à jour de la température
                    city.setTempAir(infos.get(Donnee.TEMP.ordinal()));
                    //Mise à jour de la pression
                    city.setPression(infos.get(Donnee.PRESSION.ordinal()));
                    //Mise à jour de la date
                    city.setDate(infos.get(Donnee.DATE.ordinal()));
                }

            }
            catch (IOException e) {
                Log.e("Service Erreur : ", city + " : " + e.toString());
            }

            try {
                if (inputStream != null)
                    inputStream.close();
            }
            catch (IOException e) {
                Log.e("Service Erreur :", city + " : " + e.toString());
            }
        }
    }

}
