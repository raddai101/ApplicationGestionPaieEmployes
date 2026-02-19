package com.gestionpaie.view;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Tableau de bord principal de l'application
 * Interface moderne avec FlatLaf
 */
public class DashboardFrame extends JFrame {

    private JTabbedPane tabbedPane;
    private static final Color PRIMARY_COLOR = new Color(10, 102, 194);
    private static final Color SECONDARY_COLOR = new Color(240, 248, 255);

    public DashboardFrame() {
        super("Gestion de Paie - Tableau de Bord");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(com.gestionpaie.utils.AppConfig.WINDOW_WIDTH, 
                com.gestionpaie.utils.AppConfig.WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setIconImage(createAppIcon());

        initComponents();
    }

    private void initComponents() {
        // Barre de navigation supérieure
        add(createNavBar(), BorderLayout.NORTH);

        // Contenu principal avec onglets
        tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 12));
        tabbedPane.setForeground(Color.WHITE);
        tabbedPane.setBackground(PRIMARY_COLOR);

        // Ajouter les onglets
        tabbedPane.addTab("👥 Employés", new EmployePanel());
        tabbedPane.addTab("💰 Paie", new PaiePanel());
        tabbedPane.addTab("📅 Congés", new CongePanel());
        tabbedPane.addTab("📋 Bulletins", new BulletinPanel());

        // Styliser les onglets
        styleTabbedPane();

        getContentPane().setBackground(Color.WHITE);
        add(tabbedPane, BorderLayout.CENTER);

        // Barre de statut
        add(createStatusBar(), BorderLayout.SOUTH);
    }

    /**
     * Crée la barre de navigation supérieure
     */
    private JPanel createNavBar() {
        JPanel navbar = new JPanel(new BorderLayout());
        navbar.setBackground(PRIMARY_COLOR);
        navbar.setBorder(new EmptyBorder(12, 15, 12, 15));

        // Logo et titre
        JLabel brand = new JLabel("🏢 Squad Brojex - Gestion de Paie");
        brand.setForeground(Color.WHITE);
        brand.setFont(new Font("Arial", Font.BOLD, 16));
        navbar.add(brand, BorderLayout.WEST);

        // Bouton utilisateur
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        userPanel.setBackground(PRIMARY_COLOR);
        
        JLabel userIcon = new JLabel("👤");
        userIcon.setFont(new Font("Arial", Font.PLAIN, 14));
        JLabel userName = new JLabel("Administrateur");
        userName.setFont(new Font("Arial", Font.PLAIN, 12));
        userName.setForeground(Color.WHITE);

        userPanel.add(userIcon);
        userPanel.add(userName);

        navbar.add(userPanel, BorderLayout.EAST);

        return navbar;
    }

    /**
     * Crée la barre de statut
     */
    private JPanel createStatusBar() {
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBackground(SECONDARY_COLOR);
        statusBar.setBorder(new EmptyBorder(8, 15, 8, 15));

        JLabel statusLabel = new JLabel("✓ Application prête - " + new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date()));
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        statusLabel.setForeground(new Color(100, 100, 100));

        statusBar.add(statusLabel, BorderLayout.WEST);

        JLabel versionLabel = new JLabel("v1.0");
        versionLabel.setFont(new Font("Arial", Font.ITALIC, 10));
        versionLabel.setForeground(new Color(150, 150, 150));
        statusBar.add(versionLabel, BorderLayout.EAST);

        return statusBar;
    }

    /**
     * Style les onglets avec un look moderne
     */
    private void styleTabbedPane() {
        tabbedPane.setUI(new javax.swing.plaf.metal.MetalTabbedPaneUI() {
            @Override
            protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {
                super.paintTabArea(g, tabPlacement, selectedIndex);
            }
        });

        // Couleur des onglets
        UIManager.put("TabbedPane.selected", PRIMARY_COLOR);
        UIManager.put("TabbedPane.foreground", Color.WHITE);
        UIManager.put("TabbedPane.background", SECONDARY_COLOR);
    }

    /**
     * Crée une icône pour l'application
     */
    private Image createAppIcon() {
        BufferedImage image = new java.awt.image.BufferedImage(32, 32, java.awt.image.BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(PRIMARY_COLOR);
        g2d.fillRect(0, 0, 32, 32);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        g2d.drawString("➤", 4, 26);
        g2d.dispose();
        return image;
    }
}
