package com.gestionpaie.dao;

import com.gestionpaie.model.Employe;
import com.gestionpaie.utils.CacheManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO pour les employés avec support du cache
 */
public class EmployeDAO {

    private static final String CACHE_EMPLOYES_KEY = "employes_all";
    private static final String CACHE_EMPLOYE_KEY = "employe_";

    public void insert(Employe e) {
        String sql = "INSERT INTO employe (matricule, nom, prenom, email, telephone, date_embauche, salaire_base) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.getMatricule());
            ps.setString(2, e.getNom());
            ps.setString(3, e.getPrenom());
            ps.setString(4, e.getEmail());
            ps.setString(5, e.getTelephone());
            ps.setDate(6, Date.valueOf(e.getDateEmbauche()));
            ps.setDouble(7, e.getSalaireBase());
            ps.executeUpdate();
            
            // Invalider le cache
            CacheManager.remove(CACHE_EMPLOYES_KEY);
        } catch (SQLException ex) {
            throw new DataAccessException("Erreur lors de l'ajout de l'employé", ex);
        }
    }

    public void update(Employe e) {
        String sql = "UPDATE employe SET nom=?, prenom=?, email=?, telephone=?, salaire_base=? WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.getNom());
            ps.setString(2, e.getPrenom());
            ps.setString(3, e.getEmail());
            ps.setString(4, e.getTelephone());
            ps.setDouble(5, e.getSalaireBase());
            ps.setInt(6, e.getId());
            ps.executeUpdate();
            
            // Invalider le cache
            CacheManager.remove(CACHE_EMPLOYES_KEY);
            CacheManager.remove(CACHE_EMPLOYE_KEY + e.getId());
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
            
            // Invalider le cache
            CacheManager.remove(CACHE_EMPLOYES_KEY);
            CacheManager.remove(CACHE_EMPLOYE_KEY + id);
        } catch (SQLException ex) {
            throw new DataAccessException("Erreur lors de la suppression de l'employé", ex);
        }
    }

    /**
     * Récupère tous les employés avec cache
     */
    public List<Employe> findAll() {
        // Vérifier le cache
        List<Employe> cached = CacheManager.get(CACHE_EMPLOYES_KEY, List.class);
        if (cached != null) {
            return cached;
        }

        List<Employe> list = new ArrayList<>();
        String sql = "SELECT * FROM employe";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSetToEmploye(rs));
            }
            
            // Mettre en cache le résultat (30 minutes)
            CacheManager.put(CACHE_EMPLOYES_KEY, list, 30);
        } catch (SQLException ex) {
            throw new DataAccessException("Erreur lors de la récupération des employés", ex);
        }
        return list;
    }

    /**
     * Récupère un employé par ID avec cache
     */
    public Employe findById(int id) {
        // Vérifier le cache
        Employe cached = CacheManager.get(CACHE_EMPLOYE_KEY + id, Employe.class);
        if (cached != null) {
            return cached;
        }

        String sql = "SELECT * FROM employe WHERE id=?";
        Employe e = null;
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                e = mapResultSetToEmploye(rs);
                // Mettre en cache l'employé (30 minutes)
                CacheManager.put(CACHE_EMPLOYE_KEY + id, e, 30);
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Erreur lors de la recherche de l'employé id=" + id, ex);
        }
        return e;
    }

    /**
     * Mappe un ResultSet vers un objet Employe
     */
    private Employe mapResultSetToEmploye(ResultSet rs) throws SQLException {
        Employe e = new Employe();
        e.setId(rs.getInt("id"));
        e.setMatricule(rs.getString("matricule"));
        e.setNom(rs.getString("nom"));
        e.setPrenom(rs.getString("prenom"));
        e.setEmail(rs.getString("email"));
        e.setTelephone(rs.getString("telephone"));
        e.setDateEmbauche(rs.getDate("date_embauche").toLocalDate());
        e.setSalaireBase(rs.getDouble("salaire_base"));
        return e;
    }
}
