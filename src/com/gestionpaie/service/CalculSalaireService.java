package com.gestionpaie.service;

import com.gestionpaie.model.*;

import java.util.List;

public class CalculSalaireService {

    /**
     * Calcule le salaire brut
     */
    public double calculSalaireBrut(double salaireBase, List<Prime> primes) {
        double totalPrimes = 0;
        for (Prime p : primes) {
            totalPrimes += p.getMontant();
        }
        return salaireBase + totalPrimes;
    }

    /**
     * Calcule le total des retenues
     */
    public double calculTotalRetenues(double salaireBrut, List<Retenue> retenues) {
        double total = 0;
        for (Retenue r : retenues) {
            total += salaireBrut * r.getTaux();
        }
        return total;
    }

    /**
     * Calcule le salaire net
     */
    public double calculSalaireNet(double salaireBrut, double totalRetenues) {
        return salaireBrut - totalRetenues;
    }
}
