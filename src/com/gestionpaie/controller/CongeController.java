package com.gestionpaie.controller;

import com.gestionpaie.dao.CongeDAO;
import com.gestionpaie.model.Conge;

import java.util.List;

public class CongeController {

    private CongeDAO congeDAO;

    public CongeController() {
        this.congeDAO = new CongeDAO();
    }

    public boolean ajouterConge(Conge conge) {
        try {
            congeDAO.insert(conge);
            return true;
        } catch (com.gestionpaie.dao.DataAccessException dae) {
            throw new com.gestionpaie.service.ServiceException("Impossible d'ajouter le congé : " + dae.getMessage(), dae);
        }
    }

    public List<Conge> getCongesEmploye(int employeId) {
        try {
            return congeDAO.findByEmploye(employeId);
        } catch (com.gestionpaie.dao.DataAccessException dae) {
            throw new com.gestionpaie.service.ServiceException("Impossible de récupérer les congés de l'employé : " + dae.getMessage(), dae);
        }
    }
}
