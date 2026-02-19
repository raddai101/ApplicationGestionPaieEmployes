package com.gestionpaie.model;

public abstract class Retenue {

    protected String nom;
    protected double taux;

    public Retenue(String nom, double taux) {
        this.nom = nom;
        this.taux = taux;
    }

    public abstract double calculer(double salaireBrut);

    public String getNom() { return nom; }
    public double getTaux() { return taux; }
    public void setNom(String nom) { this.nom = nom; }
    public void setTaux(double taux) { this.taux = taux; }
}
