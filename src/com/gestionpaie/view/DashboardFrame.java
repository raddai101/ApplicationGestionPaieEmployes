package com.gestionpaie.view;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {

    private JTabbedPane tabbedPane;

    public DashboardFrame() {
        super("Gestion de Paie - Tableau de Bord");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        initComponents();
    }

    private void initComponents() {
        // Couleurs
        Color blue = new Color(10, 102, 194);
        Color white = Color.WHITE;

        // navbar
        JPanel navbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        navbar.setBackground(blue);
        navbar.setBorder(BorderFactory.createEmptyBorder(10,12,10,12));
        JLabel brand = new JLabel("Squad Brojex");
        brand.setForeground(white);
        brand.setFont(brand.getFont().deriveFont(Font.BOLD, 16f));
        navbar.add(brand);
        add(navbar, BorderLayout.NORTH);

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Employés", new EmployePanel());
        tabbedPane.addTab("Paie", new PaiePanel());
        tabbedPane.addTab("Congés", new CongePanel());
        tabbedPane.addTab("Bulletins", new BulletinPanel());

        getContentPane().setBackground(white);
        add(tabbedPane, BorderLayout.CENTER);
    }
}
