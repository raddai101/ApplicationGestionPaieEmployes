package com.gestionpaie.service;

import com.gestionpaie.utils.PasswordUtils;

public class AuthService {

    /**
     * Vérifie si le mot de passe saisi correspond au hash stocké
     */
    public boolean verifyPassword(String password, String hash) {
        return PasswordUtils.verifyPassword(password, hash);
    }

    /**
     * Génère le hash d'un mot de passe pour le stockage
     */
    public String hashPassword(String password) {
        return PasswordUtils.hashPassword(password);
    }
}
