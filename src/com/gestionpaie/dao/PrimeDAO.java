package com.gestionpaie.dao;

import com.gestionpaie.model.Prime;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrimeDAO {

    public void insert(Prime p) {
        String sql = "INSERT INTO prime (libelle, montant, taxable) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getLibelle());
            ps.setDouble(2, p.getMontant());
            ps.setBoolean(3, p.isTaxable());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Erreur lors de l'ajout de la prime", ex);
        }
    }

    public List<Prime> findAll() {
        List<Prime> list = new ArrayList<>();
        String sql = "SELECT * FROM prime";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Prime p = new Prime();
                p.setId(rs.getInt("id"));
                p.setLibelle(rs.getString("libelle"));
                p.setMontant(rs.getDouble("montant"));
                p.setTaxable(rs.getBoolean("taxable"));
                list.add(p);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Erreur lors de la récupération des primes", e);
        }
        return list;
    }
}
