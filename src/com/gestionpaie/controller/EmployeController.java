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
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean modifierEmploye(Employe employe) {
        try {
            employeDAO.update(employe);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean supprimerEmploye(int id) {
        try {
            employeDAO.delete(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Employe> getAllEmployes() {
        return employeDAO.findAll();
    }
}
