package com.gestionpaie.view;

import com.gestionpaie.controller.AuthController;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private AuthController authController;

    public LoginFrame() {
        super("Gestion de Paie - Connexion");
        authController = new AuthController();

        // FlatLaf theme
        try { UIManager.setLookAndFeel(new FlatLightLaf()); } catch (Exception ex) { ex.printStackTrace(); }

        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel userLabel = new JLabel("Nom d'utilisateur:");
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(userLabel, gbc);

        usernameField = new JTextField();
        gbc.gridx = 1; gbc.gridy = 0;
        panel.add(usernameField, gbc);

        JLabel passLabel = new JLabel("Mot de passe:");
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(passLabel, gbc);

        passwordField = new JPasswordField();
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(passwordField, gbc);

        loginButton = new JButton("Se connecter");
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        add(panel, BorderLayout.CENTER);

        // Action login
        loginButton.addActionListener(this::loginAction);
    }

    private void loginAction(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Authentification retourne maintenant un Utilisateur (null si échec)
        com.gestionpaie.model.Utilisateur user = authController.login(username, password);
        if(user != null) {
            JOptionPane.showMessageDialog(this, "Connexion réussie !");
            new DashboardFrame().setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Nom d'utilisateur ou mot de passe incorrect", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
