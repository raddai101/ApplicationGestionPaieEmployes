package com.gestionpaie.service;

import com.gestionpaie.dao.PrimeDAO;
import com.gestionpaie.dao.RetenueDAO;
import com.gestionpaie.dao.ParametreDAO;
import com.gestionpaie.model.*;

import java.time.LocalDate;
import java.util.List;

public class PaieService {

    private CalculSalaireService calculSalaireService;
    private PrimeDAO primeDAO;
    private RetenueDAO retenueDAO;
    private ParametreDAO parametreDAO;

    public PaieService() {
        this.calculSalaireService = new CalculSalaireService();
        this.primeDAO = new PrimeDAO();
        this.retenueDAO = new RetenueDAO();
        this.parametreDAO = new ParametreDAO();
    }

    /**
     * Génère un bulletin complet pour un employé
     */
    public BulletinPaie genererBulletin(Employe employe) {
        try {
            // Récupération des primes et retenues
            List<Prime> primes = primeDAO.findAll();
            List<Retenue> retenues = retenueDAO.findAll();

            // Paramètres paie (CNSS, IPR, etc.)
            ParametrePaie params = parametreDAO.getParametres();

            // Calcul du salaire brut
            double salaireBrut = calculSalaireService.calculSalaireBrut(employe.getSalaireBase(), primes);

            // Calcul des retenues
            double totalRetenues = calculSalaireService.calculTotalRetenues(salaireBrut, retenues);

            // Salaire net
            double salaireNet = calculSalaireService.calculSalaireNet(salaireBrut, totalRetenues);

            // Création du bulletin
            BulletinPaie bulletin = new BulletinPaie();
            bulletin.setEmploye(employe);
            bulletin.setDatePaie(LocalDate.now());
            bulletin.setSalaireBrut(salaireBrut);
            bulletin.setTotalPrimes(primes.stream().mapToDouble(Prime::getMontant).sum());
            bulletin.setTotalRetenues(totalRetenues);
            bulletin.setSalaireNet(salaireNet);

            return bulletin;
        } catch (com.gestionpaie.dao.DataAccessException dae) {
            throw new ServiceException("Erreur lors de la génération du bulletin : " + dae.getMessage(), dae);
        }
    }
}
