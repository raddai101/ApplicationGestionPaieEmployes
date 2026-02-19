package com.gestionpaie.model;

public class Prime {

    private int id;
    private String libelle;
    private double montant;
    private boolean taxable;

    public Prime() {}

    // Getters
    public int getId() { return id; }
    public String getLibelle() { return libelle; }
    public double getMontant() { return montant; }
    public boolean isTaxable() { return taxable; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
    public void setMontant(double montant) { this.montant = montant; }
    public void setTaxable(boolean taxable) { this.taxable = taxable; }
}
