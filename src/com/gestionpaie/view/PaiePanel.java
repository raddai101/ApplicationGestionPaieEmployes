package com.gestionpaie.view;

import com.gestionpaie.controller.PaieController;
import com.gestionpaie.model.Employe;
import com.gestionpaie.model.BulletinPaie;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.List;

/**
 * Panneau de gestion de paie amélioré
 * Permet de générer des bulletins individuels ou en batch
 */
public class PaiePanel extends JPanel {

    private PaieController controller;
    private JList<Employe> employeList;
    private DefaultListModel<Employe> listModel;
    private JButton genererBulletinButton;
    private JButton genererTousButton;
    private JButton exporterButton;
    private JLabel statusLabel;
    private JProgressBar progressBar;
    private List<Employe> employes;

    public PaiePanel() {
        controller = new PaieController();
        employes = controller.getAllEmployes();
        
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);

        // Titre
        JLabel titleLabel = new JLabel("Gestion des Bulletins de Paie");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(10, 102, 194));
        add(titleLabel, BorderLayout.NORTH);

        // Contenu principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE);

        // Panneau gauche - Liste des employés
        mainPanel.add(createEmployeesPanel(), BorderLayout.CENTER);

        // Panneau droite - Actions
        mainPanel.add(createActionsPanel(), BorderLayout.EAST);

        add(mainPanel, BorderLayout.CENTER);

        // Panneau bas - Status
        add(createStatusPanel(), BorderLayout.SOUTH);
    }

    /**
     * Crée le panneau de liste des employés
     */
    private JPanel createEmployeesPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new TitledBorder(
                new LineBorder(new Color(10, 102, 194), 1),
                "Sélectionner un employé",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.PLAIN, 12),
                new Color(10, 102, 194)
        ));

        // Champ de recherche
        JPanel searchPanel = new JPanel(new BorderLayout(5, 0));
        searchPanel.setBackground(Color.WHITE);
        JTextField searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.PLAIN, 12));
        searchField.setText("Rechercher un employé...");
        searchField.setForeground(Color.GRAY);
        
        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (searchField.getText().equals("Rechercher un employé...")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Rechercher un employé...");
                    searchField.setForeground(Color.GRAY);
                }
            }
        });

        searchPanel.add(searchField, BorderLayout.CENTER);
        panel.add(searchPanel, BorderLayout.NORTH);

        // Liste des employés
        listModel = new DefaultListModel<>();
        for (Employe emp : employes) {
            listModel.addElement(emp);
        }

        employeList = new JList<>(listModel);
        employeList.setFont(new Font("Arial", Font.PLAIN, 12));
        employeList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        employeList.setCellRenderer(new EmployeeCellRenderer());
        
        // Filtre de recherche
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                String search = searchField.getText().toLowerCase();
                if (search.equals("rechercher un employé...") || search.isEmpty()) {
                    listModel.clear();
                    for (Employe emp : employes) {
                        listModel.addElement(emp);
                    }
                } else {
                    listModel.clear();
                    for (Employe emp : employes) {
                        String displayText = (emp.getNom() + " " + emp.getPrenom() + " (" + emp.getMatricule() + ")").toLowerCase();
                        if (displayText.contains(search)) {
                            listModel.addElement(emp);
                        }
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(employeList);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Crée le panneau des actions
     */
    private JPanel createActionsPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 0, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new TitledBorder(
                new LineBorder(new Color(10, 102, 194), 1),
                "Actions",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.PLAIN, 12),
                new Color(10, 102, 194)
        ));
        panel.setPreferredSize(new Dimension(180, 300));

        // Bouton générer bulletin
        genererBulletinButton = createActionButton("Générer Bulletin", new Color(40, 167, 69));
        genererBulletinButton.addActionListener(e -> genererBulletinSelectionne());
        panel.add(genererBulletinButton);

        // Bouton générer tous
        genererTousButton = createActionButton("Générer Tous", new Color(0, 123, 255));
        genererTousButton.addActionListener(e -> genererTousBulletins());
        panel.add(genererTousButton);

        // Bouton exporter
        exporterButton = createActionButton("Ouvrir Dossier", new Color(255, 193, 7));
        exporterButton.addActionListener(e -> ouvrirDossierSortie());
        panel.add(exporterButton);

        // Info
        JTextArea infoArea = new JTextArea();
        infoArea.setText("💡 Sélectionnez un ou plusieurs employés et cliquez sur 'Générer Bulletin' pour créer leurs bulletins.\n\nLes fichiers sont sauvegardés dans le dossier 'sorties/bulletins/'");
        infoArea.setEditable(false);
        infoArea.setFont(new Font("Arial", Font.PLAIN, 10));
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        infoArea.setBackground(new Color(240, 248, 255));
        infoArea.setForeground(new Color(10, 102, 194));
        panel.add(infoArea);

        return panel;
    }

    /**
     * Crée un bouton d'action stylisé
     */
    private JButton createActionButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
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
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(new EmptyBorder(10, 15, 10, 15));

        statusLabel = new JLabel("Prêt");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(statusLabel, BorderLayout.WEST);

        progressBar = new JProgressBar();
        progressBar.setPreferredSize(new Dimension(300, 20));
        progressBar.setVisible(false);
        panel.add(progressBar, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Génère un bulletin pour l'employé sélectionné
     */
    private void genererBulletinSelectionne() {
        List<Employe> selected = employeList.getSelectedValuesList();
        if (selected.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner au moins un employé", "Sélection vide", JOptionPane.WARNING_MESSAGE);
            return;
        }

        SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                progressBar.setVisible(true);
                progressBar.setMaximum(selected.size());
                int count = 0;

                for (Employe emp : selected) {
                    try {
                        BulletinPaie bulletin = controller.genererBulletin(emp);
                        controller.exporterBulletin(bulletin);
                        count++;
                        progressBar.setValue(count);
                        publish(emp.getMatricule() + " - OK");
                    } catch (Exception ex) {
                        publish(emp.getMatricule() + " - ERREUR: " + ex.getMessage());
                    }
                }
                return null;
            }

            @Override
            protected void done() {
                progressBar.setVisible(false);
                JOptionPane.showMessageDialog(PaiePanel.this,
                        "✓ " + selected.size() + " bulletin(s) généré(s) avec succès !",
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE);
                statusLabel.setText("✓ " + selected.size() + " bulletin(s) générés");
            }
        };

        worker.execute();
    }

    /**
     * Génère les bulletins pour tous les employés
     */
    private void genererTousBulletins() {
        int response = JOptionPane.showConfirmDialog(this,
                "Générer les bulletins pour les " + employes.size() + " employés ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (response != JOptionPane.YES_OPTION) return;

        SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                progressBar.setVisible(true);
                progressBar.setMaximum(employes.size());
                int count = 0;

                for (Employe emp : employes) {
                    try {
                        BulletinPaie bulletin = controller.genererBulletin(emp);
                        controller.exporterBulletin(bulletin);
                        count++;
                        progressBar.setValue(count);
                        publish(emp.getMatricule() + " ✓");
                    } catch (Exception ex) {
                        publish(emp.getMatricule() + " ✗ " + ex.getMessage());
                    }
                }
                return null;
            }

            @Override
            protected void done() {
                progressBar.setVisible(false);
                JOptionPane.showMessageDialog(PaiePanel.this,
                        "✓ Tous les bulletins ont été générés !",
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE);
                statusLabel.setText("✓ Tous les bulletins générés");
            }
        };

        worker.execute();
    }

    /**
     * Ouvre le dossier de sortie
     */
    private void ouvrirDossierSortie() {
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

    /**
     * Renderer personnalisé pour la liste des employés
     */
    private static class EmployeeCellRenderer extends JLabel implements ListCellRenderer<Employe> {
        @Override
        public Component getListCellRendererComponent(JList<? extends Employe> list, Employe value, int index,
                                                       boolean isSelected, boolean cellHasFocus) {
            if (value != null) {
                setText(value.getNom() + " " + value.getPrenom() + " (" + value.getMatricule() + ")");
            }
            setFont(new Font("Arial", Font.PLAIN, 12));
            setBorder(new EmptyBorder(5, 5, 5, 5));
            
            if (isSelected) {
                setBackground(new Color(10, 102, 194));
                setForeground(Color.WHITE);
                setOpaque(true);
            } else {
                setBackground(Color.WHITE);
                setForeground(Color.BLACK);
                setOpaque(false);
            }
            return this;
        }
    }
}
