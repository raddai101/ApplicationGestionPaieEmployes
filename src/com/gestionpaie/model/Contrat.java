package com.gestionpaie.model;

import java.time.LocalDate;

public class Contrat {

    private int id;
    private String typeContrat; // CDI, CDD
    private double salaireBase;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    public Contrat() {}

    public Contrat(int id, String typeContrat, double salaireBase,
                   LocalDate dateDebut, LocalDate dateFin) {
        this.id = id;
        this.typeContrat = typeContrat;
        this.salaireBase = salaireBase;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public boolean estActif() {
        return dateFin == null || dateFin.isAfter(LocalDate.now());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeContrat() {
        return typeContrat;
    }

    public void setTypeContrat(String typeContrat) {
        this.typeContrat = typeContrat;
    }

    public double getSalaireBase() {
        return salaireBase;
    }

    public void setSalaireBase(double salaireBase) {
        this.salaireBase = salaireBase;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }
}
