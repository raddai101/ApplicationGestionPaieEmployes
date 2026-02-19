package com.gestionpaie.controller;

import com.gestionpaie.model.BulletinPaie;
import com.gestionpaie.model.Employe;
import com.gestionpaie.service.PaieService;
import com.gestionpaie.dao.BulletinDAO;
import com.gestionpaie.dao.EmployeDAO;
import com.gestionpaie.utils.PDFGenerator;
import java.util.List;

public class PaieController {

    private PaieService paieService;
    private BulletinDAO bulletinDAO;
    private EmployeDAO employeDAO;

    public PaieController() {
        this.paieService = new PaieService();
        this.bulletinDAO = new BulletinDAO();
        this.employeDAO = new EmployeDAO();
    }

    /**
     * Génère un bulletin pour un employé spécifique
     */
    public BulletinPaie genererBulletin(Employe employe) {
        try {
            BulletinPaie bulletin = paieService.genererBulletin(employe);
            bulletinDAO.insert(bulletin);
            return bulletin;
        } catch (com.gestionpaie.service.ServiceException | com.gestionpaie.dao.DataAccessException e) {
            throw new com.gestionpaie.service.ServiceException("Erreur lors de la génération/enregistrement du bulletin : " + e.getMessage(), e);
        }
    }

    /**
     * Exporte et génère les fichiers pour un bulletin
     */
    public String exporterBulletin(BulletinPaie bulletin) {
        try {
            // Génère le fichier HTML
            String cheminHTML = PDFGenerator.genererBulletinHTML(bulletin);
            // Génère aussi le fichier CSV
            PDFGenerator.genererBulletinCSV(bulletin);
            return cheminHTML;
        } catch (Exception e) {
            throw new com.gestionpaie.service.ServiceException("Erreur lors de l'export du bulletin : " + e.getMessage(), e);
        }
    }

    /**
     * Génère et exporte les bulletins pour plusieurs employés
     */
    public void gererMultipleBulletins(List<Employe> employes) {
        try {
            for (Employe employe : employes) {
                BulletinPaie bulletin = genererBulletin(employe);
                exporterBulletin(bulletin);
            }
        } catch (Exception e) {
            throw new com.gestionpaie.service.ServiceException("Erreur lors de la génération des bulletins : " + e.getMessage(), e);
        }
    }

    /**
     * Récupère tous les employés
     */
    public List<Employe> getAllEmployes() {
        try {
            return employeDAO.findAll();
        } catch (com.gestionpaie.dao.DataAccessException dae) {
            throw new com.gestionpaie.service.ServiceException("Erreur lors de la récupération des employés : " + dae.getMessage(), dae);
        }
    }

    /**
     * Exporte les bulletins en PDF pour tous les employés (hérité)
     */
    public void exporterBulletinsPDF() {
        try {
            List<BulletinPaie> list = bulletinDAO.findAll();
            for (BulletinPaie b : list) {
                exporterBulletin(b);
            }
        } catch (com.gestionpaie.dao.DataAccessException dae) {
            throw new com.gestionpaie.service.ServiceException("Erreur lors de l'export des bulletins : " + dae.getMessage(), dae);
        }
    }

    /**
     * Génère les bulletins pour tous les employés
     */
    public void genererBulletins() {
        try {
            List<Employe> list = employeDAO.findAll();
            for (Employe e : list) {
                genererBulletin(e);
            }
        } catch (com.gestionpaie.dao.DataAccessException dae) {
            throw new com.gestionpaie.service.ServiceException("Erreur lors de la génération des bulletins : " + dae.getMessage(), dae);
        }
    }
}
