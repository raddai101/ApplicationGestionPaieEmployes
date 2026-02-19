package com.gestionpaie.controller;

import com.gestionpaie.model.BulletinPaie;
import com.gestionpaie.model.Employe;
import com.gestionpaie.service.PaieService;
import com.gestionpaie.dao.BulletinDAO;
import com.gestionpaie.dao.EmployeDAO;
import java.util.List;

public class PaieController {

    private PaieService paieService;
    private BulletinDAO bulletinDAO;

    public PaieController() {
        this.paieService = new PaieService();
        this.bulletinDAO = new BulletinDAO();
    }

    public BulletinPaie genererBulletin(Employe employe) {
        try {
            BulletinPaie bulletin = paieService.genererBulletin(employe);
            bulletinDAO.insert(bulletin);
            return bulletin;
        } catch (com.gestionpaie.service.ServiceException | com.gestionpaie.dao.DataAccessException e) {
            throw new com.gestionpaie.service.ServiceException("Erreur lors de la génération/enregistrement du bulletin : " + e.getMessage(), e);
        }
    }

    public void exporterBulletinsPDF() {
        try {
            java.util.List<BulletinPaie> list = bulletinDAO.findAll();
            int i = 1;
            for (BulletinPaie b : list) {
                String chemin = "bulletin_" + (b.getId() <= 0 ? i++ : b.getId()) + ".csv";
                com.gestionpaie.utils.PDFGenerator.genererBulletin(b, chemin);
            }
        } catch (com.gestionpaie.dao.DataAccessException dae) {
            throw new com.gestionpaie.service.ServiceException("Erreur lors de l'export des bulletins : " + dae.getMessage(), dae);
        }
    }

    public void genererBulletins() {
        try {
            EmployeDAO employeDAO = new EmployeDAO();
            List<Employe> list = employeDAO.findAll();
            for (Employe e : list) {
                genererBulletin(e);
            }
        } catch (com.gestionpaie.dao.DataAccessException dae) {
            throw new com.gestionpaie.service.ServiceException("Erreur lors de la génération des bulletins : " + dae.getMessage(), dae);
        }
    }
}
