package com.gestionpaie.controller;

import com.gestionpaie.model.Employe;
import com.gestionpaie.dao.EmployeDAO;

import java.util.List;

public class EmployeController {

    private EmployeDAO employeDAO;

    public EmployeController() {
        this.employeDAO = new EmployeDAO();
    }

    public boolean ajouterEmploye(Employe employe) {
        try {
            employeDAO.insert(employe);
            return true;
        } catch (com.gestionpaie.dao.DataAccessException dae) {
            throw new com.gestionpaie.service.ServiceException("Impossible d'ajouter l'employé : " + dae.getMessage(), dae);
        }
    }

    public boolean modifierEmploye(Employe employe) {
        try {
            employeDAO.update(employe);
            return true;
        } catch (com.gestionpaie.dao.DataAccessException dae) {
            throw new com.gestionpaie.service.ServiceException("Impossible de modifier l'employé : " + dae.getMessage(), dae);
        }
    }

    public boolean supprimerEmploye(int id) {
        try {
            employeDAO.delete(id);
            return true;
        } catch (com.gestionpaie.dao.DataAccessException dae) {
            throw new com.gestionpaie.service.ServiceException("Impossible de supprimer l'employé : " + dae.getMessage(), dae);
        }
    }

    public List<Employe> getAllEmployes() {
        try {
            return employeDAO.findAll();
        } catch (com.gestionpaie.dao.DataAccessException dae) {
            throw new com.gestionpaie.service.ServiceException("Impossible de récupérer la liste des employés : " + dae.getMessage(), dae);
        }
    }
}
