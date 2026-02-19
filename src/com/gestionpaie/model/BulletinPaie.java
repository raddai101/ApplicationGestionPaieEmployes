package com.gestionpaie.model;

import java.time.LocalDate;

public class BulletinPaie {

    private int id;
    private Employe employe;
    private LocalDate datePaie;
    private double salaireBrut;
    private double totalPrimes;
    private double totalRetenues;
    private double salaireNet;

    public BulletinPaie() {}

    // Getters
    public int getId() { return id; }
    public Employe getEmploye() { return employe; }
    public LocalDate getDatePaie() { return datePaie; }
    public double getSalaireBrut() { return salaireBrut; }
    public double getTotalPrimes() { return totalPrimes; }
    public double getTotalRetenues() { return totalRetenues; }
    public double getSalaireNet() { return salaireNet; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setEmploye(Employe employe) { this.employe = employe; }
    public void setDatePaie(LocalDate datePaie) { this.datePaie = datePaie; }
    public void setSalaireBrut(double salaireBrut) { this.salaireBrut = salaireBrut; }
    public void setTotalPrimes(double totalPrimes) { this.totalPrimes = totalPrimes; }
    public void setTotalRetenues(double totalRetenues) { this.totalRetenues = totalRetenues; }
    public void setSalaireNet(double salaireNet) { this.salaireNet = salaireNet; }
}
