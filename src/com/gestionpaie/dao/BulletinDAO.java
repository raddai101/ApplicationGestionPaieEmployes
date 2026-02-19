package com.gestionpaie.dao;

import com.gestionpaie.model.BulletinPaie;
import com.gestionpaie.model.Employe;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BulletinDAO {

    public void insert(BulletinPaie b) {
        String sql = "INSERT INTO bulletin_paie (employe_id, date_paie, salaire_brut, total_primes, total_retenues, salaire_net) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, b.getEmploye().getId());
            ps.setDate(2, Date.valueOf(b.getDatePaie()));
            ps.setDouble(3, b.getSalaireBrut());
            ps.setDouble(4, b.getTotalPrimes());
            ps.setDouble(5, b.getTotalRetenues());
            ps.setDouble(6, b.getSalaireNet());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Erreur lors de l'ajout du bulletin de paie", ex);
        }
    }

    public List<BulletinPaie> findAll() {
        List<BulletinPaie> list = new ArrayList<>();
        String sql = "SELECT b.id, b.date_paie, b.salaire_brut, b.total_primes, b.total_retenues, b.salaire_net, "
                + "e.id AS e_id, e.matricule, e.nom, e.prenom, e.email, e.telephone, e.date_embauche "
                + "FROM bulletin_paie b JOIN employe e ON b.employe_id = e.id";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                BulletinPaie b = new BulletinPaie();
                b.setId(rs.getInt("id"));
                b.setDatePaie(rs.getDate("date_paie").toLocalDate());
                b.setSalaireBrut(rs.getDouble("salaire_brut"));
                b.setTotalPrimes(rs.getDouble("total_primes"));
                b.setTotalRetenues(rs.getDouble("total_retenues"));
                b.setSalaireNet(rs.getDouble("salaire_net"));

                Employe e = new Employe();
                e.setId(rs.getInt("e_id"));
                e.setMatricule(rs.getString("matricule"));
                e.setNom(rs.getString("nom"));
                e.setPrenom(rs.getString("prenom"));
                e.setEmail(rs.getString("email"));
                e.setTelephone(rs.getString("telephone"));
                if (rs.getDate("date_embauche") != null)
                    e.setDateEmbauche(rs.getDate("date_embauche").toLocalDate());

                b.setEmploye(e);
                list.add(b);
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Erreur lors de la récupération des bulletins de paie", ex);
        }
        return list;
    }
}
