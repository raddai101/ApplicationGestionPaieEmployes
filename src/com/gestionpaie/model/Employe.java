package com.gestionpaie.model;

import java.time.LocalDate;

public class Employe {

    private int id;
    private String matricule;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private LocalDate dateEmbauche;
    private double salaireBase;

    public Employe() {}

    // Getters
    public int getId() { return id; }
    public String getMatricule() { return matricule; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getEmail() { return email; }
    public String getTelephone() { return telephone; }
    public LocalDate getDateEmbauche() { return dateEmbauche; }
    public double getSalaireBase() { return salaireBase; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setMatricule(String matricule) { this.matricule = matricule; }
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setEmail(String email) { this.email = email; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public void setDateEmbauche(LocalDate dateEmbauche) { this.dateEmbauche = dateEmbauche; }
    public void setSalaireBase(double salaireBase) { this.salaireBase = salaireBase; }
}
