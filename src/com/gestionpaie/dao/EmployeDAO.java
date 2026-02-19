package com.gestionpaie.dao;

import com.gestionpaie.model.Employe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeDAO {

    public void insert(Employe e) {
        String sql = "INSERT INTO employe (matricule, nom, prenom, email, telephone, date_embauche) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.getMatricule());
            ps.setString(2, e.getNom());
            ps.setString(3, e.getPrenom());
            ps.setString(4, e.getEmail());
            ps.setString(5, e.getTelephone());
            ps.setDate(6, Date.valueOf(e.getDateEmbauche()));
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Erreur lors de l'ajout de l'employé", ex);
        }
    }

    public void update(Employe e) {
        String sql = "UPDATE employe SET nom=?, prenom=?, email=?, telephone=? WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.getNom());
            ps.setString(2, e.getPrenom());
            ps.setString(3, e.getEmail());
            ps.setString(4, e.getTelephone());
            ps.setInt(5, e.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Erreur lors de la modification de l'employé", ex);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM employe WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Erreur lors de la suppression de l'employé", ex);
        }
    }

    public List<Employe> findAll() {
        List<Employe> list = new ArrayList<>();
        String sql = "SELECT * FROM employe";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Employe e = new Employe();
                e.setId(rs.getInt("id"));
                e.setMatricule(rs.getString("matricule"));
                e.setNom(rs.getString("nom"));
                e.setPrenom(rs.getString("prenom"));
                e.setEmail(rs.getString("email"));
                e.setTelephone(rs.getString("telephone"));
                e.setDateEmbauche(rs.getDate("date_embauche").toLocalDate());
                list.add(e);
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Erreur lors de la récupération des employés", ex);
        }
        return list;
    }

    public Employe findById(int id) {
        String sql = "SELECT * FROM employe WHERE id=?";
        Employe e = null;
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                e = new Employe();
                e.setId(rs.getInt("id"));
                e.setMatricule(rs.getString("matricule"));
                e.setNom(rs.getString("nom"));
                e.setPrenom(rs.getString("prenom"));
                e.setEmail(rs.getString("email"));
                e.setTelephone(rs.getString("telephone"));
                e.setDateEmbauche(rs.getDate("date_embauche").toLocalDate());
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Erreur lors de la recherche de l'employé id=" + id, ex);
        }
        return e;
    }
}
