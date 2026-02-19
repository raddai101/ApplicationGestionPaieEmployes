package com.gestionpaie.view;

import com.gestionpaie.controller.EmployeController;
import com.gestionpaie.model.Employe;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmployePanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private JButton addButton, editButton, deleteButton;
    private EmployeController controller;

    public EmployePanel() {
        controller = new EmployeController();
        setLayout(new BorderLayout());
        initComponents();
        loadTable();
    }

    private void initComponents() {
        model = new DefaultTableModel(new String[]{"ID", "Matricule", "Nom", "Prénom", "Email", "Téléphone"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        addButton = new JButton("Ajouter");
        editButton = new JButton("Modifier");
        deleteButton = new JButton("Supprimer");

        btnPanel.add(addButton);
        btnPanel.add(editButton);
        btnPanel.add(deleteButton);
        add(btnPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> onAjouter());
        editButton.addActionListener(e -> onModifier());
        deleteButton.addActionListener(e -> onSupprimer());
    }

    private void onAjouter() {
        String matricule = JOptionPane.showInputDialog(this, "Matricule:");
        if (matricule == null) return;
        String nom = JOptionPane.showInputDialog(this, "Nom:");
        if (nom == null) return;
        String prenom = JOptionPane.showInputDialog(this, "Prénom:");
        if (prenom == null) return;
        String email = JOptionPane.showInputDialog(this, "Email:");
        String telephone = JOptionPane.showInputDialog(this, "Téléphone:");

        com.gestionpaie.model.Employe e = new com.gestionpaie.model.Employe();
        e.setMatricule(matricule);
        e.setNom(nom);
        e.setPrenom(prenom);
        e.setEmail(email);
        e.setTelephone(telephone);

        if (controller.ajouterEmploye(e)) loadTable();
    }

    private void onModifier() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un employé à modifier");
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        String matricule = (String) model.getValueAt(row, 1);
        String nom = (String) model.getValueAt(row, 2);
        String prenom = (String) model.getValueAt(row, 3);
        String email = (String) model.getValueAt(row, 4);
        String telephone = (String) model.getValueAt(row, 5);

        com.gestionpaie.model.Employe e = new com.gestionpaie.model.Employe();
        e.setId(id);
        e.setMatricule(matricule);
        e.setNom(nom);
        e.setPrenom(prenom);
        e.setEmail(email);
        e.setTelephone(telephone);

        if (controller.modifierEmploye(e)) loadTable();
    }

    private void onSupprimer() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un employé à supprimer");
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        if (JOptionPane.showConfirmDialog(this, "Confirmer la suppression ?") == JOptionPane.YES_OPTION) {
            if (controller.supprimerEmploye(id)) loadTable();
        }
    }

    public void loadTable() {
        model.setRowCount(0);
        List<Employe> list = controller.getAllEmployes();
        for(Employe e : list) {
            model.addRow(new Object[]{e.getId(), e.getMatricule(), e.getNom(), e.getPrenom(), e.getEmail(), e.getTelephone()});
        }
    }
}
