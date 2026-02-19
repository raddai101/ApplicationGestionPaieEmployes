package com.gestionpaie.dao;

import com.gestionpaie.model.Utilisateur;
import java.sql.*;

public class UtilisateurDAO {

    public Utilisateur findByUsername(String username) {
        String sql = "SELECT * FROM utilisateur WHERE username=?";
        Utilisateur user = null;
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new Utilisateur();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPasswordHash(rs.getString("password"));
                user.setRole(rs.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
