package com.gestionpaie;

import com.gestionpaie.view.LoginFrame;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {

    public static Properties CONFIG;

    public static void main(String[] args) {

        // Charger le fichier config.properties
        CONFIG = new Properties();
        try {
            CONFIG.load(new FileInputStream("resources/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Impossible de charger le fichier de configuration.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // Initialisation Look & Feel FlatLaf
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Lancer la fenêtre de connexion
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}
