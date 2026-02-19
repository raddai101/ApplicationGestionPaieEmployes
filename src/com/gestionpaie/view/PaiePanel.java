package com.gestionpaie.view;

import com.gestionpaie.controller.PaieController;

import javax.swing.*;
import java.awt.*;

public class PaiePanel extends JPanel {

    private JButton genererButton;
    private PaieController controller;

    public PaiePanel() {
        controller = new PaieController();
        setLayout(new FlowLayout());
        genererButton = new JButton("Générer les bulletins");
        add(genererButton);

        genererButton.addActionListener(e -> {
            try {
                controller.genererBulletins();
                JOptionPane.showMessageDialog(this, "Bulletins générés avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } catch (com.gestionpaie.service.ServiceException se) {
                JOptionPane.showMessageDialog(this, se.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
