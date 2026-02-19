package com.gestionpaie.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Configuration centralisée de l'application
 * Gestion des chemins, dossiers de sortie, et constantes
 */
public class AppConfig {

    // Dossiers de sortie
    public static final String OUTPUT_DIR = "sorties";
    public static final String BULLETINS_DIR = OUTPUT_DIR + File.separator + "bulletins";
    public static final String LOGS_DIR = OUTPUT_DIR + File.separator + "logs";
    
    // Formats
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String DATE_DISPLAY_PATTERN = "dd/MM/yyyy";
    
    // Configuration UI
    public static final int WINDOW_WIDTH = 1200;
    public static final int WINDOW_HEIGHT = 700;
    
    static {
        // Créer les dossiers de sortie au démarrage
        initializeDirectories();
    }
    
    /**
     * Initialise les répertoires nécessaires
     */
    public static void initializeDirectories() {
        try {
            Files.createDirectories(Paths.get(BULLETINS_DIR));
            Files.createDirectories(Paths.get(LOGS_DIR));
        } catch (Exception e) {
            System.err.println("Erreur lors de la création des répertoires: " + e.getMessage());
        }
    }
    
    /**
     * Retourne le chemin complet pour un fichier bulletin
     */
    public static String getBulletinFilePath(String filename) {
        return BULLETINS_DIR + File.separator + filename;
    }
    
    /**
     * Retourne le chemin complet pour un fichier log
     */
    public static String getLogFilePath(String filename) {
        return LOGS_DIR + File.separator + filename;
    }
}
