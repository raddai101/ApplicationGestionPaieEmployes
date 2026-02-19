package com.gestionpaie.controller;

import com.gestionpaie.dao.UtilisateurDAO;
import com.gestionpaie.model.Utilisateur;
import com.gestionpaie.service.AuthService;

public class AuthController {

    private UtilisateurDAO utilisateurDAO;
    private AuthService authService;

    public AuthController() {
        this.utilisateurDAO = new UtilisateurDAO();
        this.authService = new AuthService();
    }

    public Utilisateur login(String username, String password) {

        Utilisateur user = utilisateurDAO.findByUsername(username);

        if (user != null && authService.verifyPassword(password, user.getPasswordHash())) {
            return user;
        }

        return null;
    }
}
