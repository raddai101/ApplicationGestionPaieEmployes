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

        genererButton.addActionListener(e -> controller.genererBulletins());
    }
}
