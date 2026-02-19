package com.gestionpaie.model;

public class Utilisateur {

    private int id;
    private String username;
    private String passwordHash;
    private String role;

    public Utilisateur() {}

    // Getters
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public String getRole() { return role; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public void setRole(String role) { this.role = role; }
}
