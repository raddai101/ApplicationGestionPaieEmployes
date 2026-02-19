package com.gestionpaie.model;

import java.time.LocalDate;

public class Conge {

    private int id;
    private int employeId;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String type;
    private boolean approuve;

    public Conge() {}

    // Getters
    public int getId() { return id; }
    public int getEmployeId() { return employeId; }
    public LocalDate getDateDebut() { return dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public String getType() { return type; }
    public boolean isApprouve() { return approuve; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setEmployeId(int employeId) { this.employeId = employeId; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
    public void setType(String type) { this.type = type; }
    public void setApprouve(boolean approuve) { this.approuve = approuve; }
}
