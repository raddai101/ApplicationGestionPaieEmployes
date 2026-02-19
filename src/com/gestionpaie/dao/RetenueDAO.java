package com.gestionpaie.dao;

import com.gestionpaie.model.Retenue;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RetenueDAO {

    public List<Retenue> findAll() {
        List<Retenue> list = new ArrayList<>();
        String sql = "SELECT * FROM retenue";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String nom = rs.getString("nom");
                double taux = rs.getDouble("taux");
                Retenue r;
                if (nom.equalsIgnoreCase("CNSS")) {
                    r = new com.gestionpaie.model.RetenueCNSS(taux);
                } else {
                    r = new com.gestionpaie.model.RetenueIPR(taux);
                }
                list.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
