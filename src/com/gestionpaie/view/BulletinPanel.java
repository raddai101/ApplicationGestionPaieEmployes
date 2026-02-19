package com.gestionpaie.view;

import com.gestionpaie.controller.PaieController;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
 * Panneau d'affichage et d'export des bulletins
 */
public class BulletinPanel extends JPanel {

    private JButton exporterPDFButton;
    private JButton ouvrirDossierButton;
    private JTextArea infoArea;
    private PaieController controller;

    public BulletinPanel() {
        controller = new PaieController();
        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);

        // Titre
        JLabel titleLabel = new JLabel("Gestion des Bulletins Archivés");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(10, 102, 194));
        add(titleLabel, BorderLayout.NORTH);

        // Contenu central
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 20, 20));
        centerPanel.setBackground(Color.WHITE);

        // Panneau info
        centerPanel.add(createInfoPanel());

        // Panneau actions
        centerPanel.add(createActionsPanel());

        add(centerPanel, BorderLayout.CENTER);

        // Panneau bas
        add(createStatusPanel(), BorderLayout.SOUTH);
    }

    /**
     * Crée le panneau d'informations
     */
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new TitledBorder(
                new LineBorder(new Color(10, 102, 194), 2),
                "Informations",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13),
                new Color(10, 102, 194)
        ));

        infoArea = new JTextArea();
        infoArea.setText(
                "📋 GESTION DES BULLETINS DE PAIE\n\n" +
                "Ce panneau permet de:\n" +
                "• Exporter tous les bulletins au format HTML et CSV\n" +
                "• Accéder au dossier d'archivage des bulletins\n" +
                "• Consulter l'historique des générations\n\n" +
                "📁 Dossier de sortie: sorties/bulletins/\n" +
                "📄 Format: HTML (pour visualisation) + CSV (pour archivage)\n\n" +
                "Généralement appelé après la génération depuis l'onglet PAIE."
        );
        infoArea.setEditable(false);
        infoArea.setFont(new Font("Arial", Font.PLAIN, 12));
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        infoArea.setBackground(new Color(240, 248, 255));
        infoArea.setForeground(new Color(50, 50, 50));
        
        panel.add(new JScrollPane(infoArea), BorderLayout.CENTER);
        return panel;
    }

    /**
     * Crée le panneau des actions
     */
    private JPanel createActionsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new TitledBorder(
                new LineBorder(new Color(10, 102, 194), 2),
                "Actions",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13),
                new Color(10, 102, 194)
        ));

        // Boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        buttonPanel.setBackground(Color.WHITE);

        exporterPDFButton = createStyledButton("Exporter Bulletins", new Color(0, 123, 255));
        exporterPDFButton.addActionListener(e -> exporterBulletins());
        buttonPanel.add(exporterPDFButton);

        ouvrirDossierButton = createStyledButton("Ouvrir Dossier", new Color(255, 193, 7));
        ouvrirDossierButton.addActionListener(e -> ouvrirDossier());
        buttonPanel.add(ouvrirDossierButton);

        panel.add(buttonPanel, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Crée un bouton stylisé
     */
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(180, 50));
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(darkerColor(bgColor));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    /**
     * Crée le panneau de status
     */
    private JPanel createStatusPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(new EmptyBorder(10, 15, 10, 15));
        JLabel statusLabel = new JLabel("✓ Application prête");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        statusLabel.setForeground(new Color(40, 167, 69));
        panel.add(statusLabel, BorderLayout.WEST);
        return panel;
    }

    /**
     * Exporte les bulletins
     */
    private void exporterBulletins() {
        try {
            controller.exporterBulletinsPDF();
            JOptionPane.showMessageDialog(this,
                    "✓ Bulletins exportés avec succès !\n\nDossier: sorties/bulletins/",
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (com.gestionpaie.service.ServiceException se) {
            JOptionPane.showMessageDialog(this,
                    "Erreur: " + se.getMessage(),
                    "Erreur d'export",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Ouvre le dossier de sortie
     */
    private void ouvrirDossier() {
        try {
            java.nio.file.Path path = java.nio.file.Paths.get(
                    com.gestionpaie.utils.AppConfig.BULLETINS_DIR).toAbsolutePath();
            java.io.File file = path.toFile();

            if (!file.exists()) {
                file.mkdirs();
            }

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Impossible d'ouvrir le dossier: " + ex.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Retourne une couleur plus sombre
     */
    private Color darkerColor(Color c) {
        return new Color(
                Math.max(0, c.getRed() - 40),
                Math.max(0, c.getGreen() - 40),
                Math.max(0, c.getBlue() - 40)
        );
    }
}
