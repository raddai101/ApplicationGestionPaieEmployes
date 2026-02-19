package com.gestionpaie.view;

import com.gestionpaie.controller.PaieController;

import javax.swing.*;
import java.awt.*;

public class BulletinPanel extends JPanel {

    private JButton exporterPDFButton;
    private PaieController controller;

    public BulletinPanel() {
        controller = new PaieController();
        setLayout(new FlowLayout());

        exporterPDFButton = new JButton("Exporter les bulletins en PDF");
        add(exporterPDFButton);

        exporterPDFButton.addActionListener(e -> controller.exporterBulletinsPDF());
    }
}
