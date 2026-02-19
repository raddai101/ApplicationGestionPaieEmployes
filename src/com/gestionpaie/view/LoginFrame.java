package com.gestionpaie.view;

import com.gestionpaie.controller.AuthController;
import com.formdev.flatlaf.FlatLightLaf;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

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
        setSize(600, 420);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // couleurs
        Color blue = new Color(10, 102, 194);
        Color white = Color.WHITE;

        // fond de la fenêtre en blanc
        getContentPane().setBackground(white);

        // navbar en haut
        JPanel navbar = new JPanel(new BorderLayout());
        navbar.setBackground(blue);
        navbar.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        JLabel brand = new JLabel("Squad Brojex");
        brand.setForeground(white);
        brand.setFont(brand.getFont().deriveFont(Font.BOLD, 16f));
        navbar.add(brand, BorderLayout.WEST);

        // navbar (sans avatar)
        add(navbar, BorderLayout.NORTH);

        // conteneur central (blanc) pour séparer le reste
        JPanel centerContainer = new JPanel(new GridBagLayout());
        centerContainer.setBackground(white);
        centerContainer.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(blue);
        panel.setBorder(BorderFactory.createCompoundBorder(new LineBorder(blue.darker(), 4), BorderFactory.createEmptyBorder(12,12,12,12)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12,12,12,12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // charger l'icône utilisateur pour la zone formulaire
        BufferedImage avatarImg = loadImage("resources/icons/utilisateur.png");

        // avatar centré en haut du formulaire
        if (avatarImg != null) {
            AvatarPanel avatar = new AvatarPanel(avatarImg, 80);
            gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.CENTER;
            panel.add(avatar, gbc);
            // remettre contraintes pour champs
            gbc.gridwidth = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.anchor = GridBagConstraints.CENTER;
        }

        JLabel userLabel = new JLabel("Nom d'utilisateur:");
        userLabel.setForeground(white);
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(userLabel, gbc);

        usernameField = new JTextField();
        usernameField.setBackground(white);
        usernameField.setBorder(BorderFactory.createLineBorder(blue.darker(),1));
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(usernameField, gbc);

        JLabel passLabel = new JLabel("Mot de passe:");
        passLabel.setForeground(white);
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(passLabel, gbc);

        passwordField = new JPasswordField();
        passwordField.setBackground(white);
        passwordField.setBorder(BorderFactory.createLineBorder(blue.darker(),1));
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(passwordField, gbc);

        loginButton = new JButton("Se connecter");
        loginButton.setBackground(white);
        loginButton.setForeground(blue.darker());
        loginButton.setFocusPainted(false);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        centerContainer.add(panel);
        add(centerContainer, BorderLayout.CENTER);

        // Action login
        loginButton.addActionListener(this::loginAction);
    }

    private void loginAction(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            // Authentification retourne maintenant un Utilisateur (null si échec)
            com.gestionpaie.model.Utilisateur user = authController.login(username, password);
            if(user != null) {
                JOptionPane.showMessageDialog(this, "Connexion réussie !");
                new DashboardFrame().setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Nom d'utilisateur ou mot de passe incorrect", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } catch (com.gestionpaie.service.ServiceException se) {
            JOptionPane.showMessageDialog(this, se.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private BufferedImage loadImage(String relativePath) {
        try {
            // tenter via classpath (ressources packagées)
            URL res = getClass().getResource("/" + relativePath);
            if (res != null) {
                return ImageIO.read(res);
            }
            // fallback: chemin relatif depuis le répertoire de travail
            File f = new File(relativePath);
            if (f.exists()) {
                return ImageIO.read(f);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static class AvatarPanel extends JPanel {
        private final BufferedImage img;
        private final int size;

        AvatarPanel(BufferedImage img, int size) {
            this.img = img;
            this.size = size;
            setPreferredSize(new Dimension(size, size));
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (img == null) return;
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // dessiner un fond blanc circulaire (bordure) puis l'image à l'intérieur
            int border = 4;
            g2.setColor(Color.WHITE);
            g2.fillOval(0, 0, size, size);

            Ellipse2D inner = new Ellipse2D.Float(border/2f, border/2f, size - border, size - border);
            g2.setClip(inner);
            int innerSize = Math.max(1, size - border);
            Image scaled = img.getScaledInstance(innerSize, innerSize, Image.SCALE_SMOOTH);
            g2.drawImage(scaled, Math.round(border/2f), Math.round(border/2f), null);
            g2.dispose();
        }
    }
}
