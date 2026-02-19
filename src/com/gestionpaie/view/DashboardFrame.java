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
        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Employés", new EmployePanel());
        tabbedPane.addTab("Paie", new PaiePanel());
        tabbedPane.addTab("Congés", new CongePanel());
        tabbedPane.addTab("Bulletins", new BulletinPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }
}
