package com.kiwinumba.uapv1301804.meteoandroid;

import java.io.Serializable;

/**
 * Classe permettant de représenter des villes
 */
public class City implements Serializable
{
    //Le nom de la ville
    private String nom;
    //Le pays de la ville
    private String pays;
    //Date update
    private String date;
    //Vitesse du vent
    private String vitesseVent;
    //Direction du vent
    private String directionvent;
    //Pression (en hPa)
    private String pression;
    //Température de l'air c°
    private String tempAir;

    /**
     * Constructeur de la classe com.kiwinumba.uapv1301804.meteoandroid.City qui permet d'initialiser le nom
     * et le pays d'apartenance de la ville
     * @param nom
     * @param pays
     */
    public City(String nom, String pays)
    {
        this.nom = nom;
        this.pays = pays;
        this.date = null;
        this.vitesseVent = null;
        this.directionvent = null;
        this.pression = null;
        this.tempAir = null;
    }
    public String getNom(){return nom;}
    public String getPays(){return pays;}
    public String getDate(){return date;}
    public String getVitesseVent(){return vitesseVent;}
    public String getDirectionvent(){return directionvent;}
    public String getPression(){return pression;}
    public String getTempAir(){return tempAir;}

    public void setDate(String date){this.date = date;}
    public void setVitesseVent(String vitesseVent){this.vitesseVent = vitesseVent;}
    public void setDirectionvent(String directionvent){this.directionvent = directionvent;}
    public void setPression(String pression){this.pression = pression;}
    public void setTempAir(String tempAir){this.tempAir = tempAir;}

    /**
     * Permet de renvoyer un String
     * @return
     */
    @Override
    public String toString()
    {
        return this.nom + " (" + this.pays + " )";
    }
}
