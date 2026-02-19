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
        } catch (Exception e) {
            return false;
        }
    }

    public List<Conge> getCongesEmploye(int employeId) {
        return congeDAO.findByEmploye(employeId);
    }
}
