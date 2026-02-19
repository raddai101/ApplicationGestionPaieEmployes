package com.gestionpaie.model;

public class RetenueIPR extends Retenue {

    public RetenueIPR(double taux) {
        super("IPR", taux);
    }

    @Override
    public double calculer(double salaireBrut) {
        return salaireBrut * taux / 100;
    }
}
