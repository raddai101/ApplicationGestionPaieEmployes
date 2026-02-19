package com.gestionpaie.model;

public class ParametrePaie {

    private double tauxCNSS;
    private double tauxIPR;
    private int nombreJourCongeAnnuel;

    public ParametrePaie() {}

    public ParametrePaie(double tauxCNSS, double tauxIPR, int nombreJourCongeAnnuel) {
        setTauxCNSS(tauxCNSS);
        setTauxIPR(tauxIPR);
        setNombreJourCongeAnnuel(nombreJourCongeAnnuel);
    }

    // ===== Getters =====
    public double getTauxCNSS() {
        return tauxCNSS;
    }

    public double getTauxIPR() {
        return tauxIPR;
    }

    public int getNombreJourCongeAnnuel() {
        return nombreJourCongeAnnuel;
    }

    // ===== Setters avec validation =====
    public void setTauxCNSS(double tauxCNSS) {
        if (tauxCNSS < 0)
            throw new IllegalArgumentException("Le taux CNSS ne peut pas être négatif");
        this.tauxCNSS = tauxCNSS;
    }

    public void setTauxIPR(double tauxIPR) {
        if (tauxIPR < 0)
            throw new IllegalArgumentException("Le taux IPR ne peut pas être négatif");
        this.tauxIPR = tauxIPR;
    }

    public void setNombreJourCongeAnnuel(int nombreJourCongeAnnuel) {
        if (nombreJourCongeAnnuel < 0)
            throw new IllegalArgumentException("Le nombre de jours de congé ne peut pas être négatif");
        this.nombreJourCongeAnnuel = nombreJourCongeAnnuel;
    }

    @Override
    public String toString() {
        return "ParametrePaie{" +
                "tauxCNSS=" + tauxCNSS +
                ", tauxIPR=" + tauxIPR +
                ", nombreJourCongeAnnuel=" + nombreJourCongeAnnuel +
                '}';
    }
}
