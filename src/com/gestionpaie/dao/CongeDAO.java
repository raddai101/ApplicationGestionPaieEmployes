package com.gestionpaie.dao;

import com.gestionpaie.model.Conge;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CongeDAO {

    public void insert(Conge c) throws SQLException {
        String sql = "INSERT INTO conge (employe_id, date_debut, date_fin, type, approuve) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, c.getEmployeId());
            ps.setDate(2, Date.valueOf(c.getDateDebut()));
            ps.setDate(3, Date.valueOf(c.getDateFin()));
            ps.setString(4, c.getType());
            ps.setBoolean(5, c.isApprouve());
            ps.executeUpdate();
        }
    }

    public List<Conge> findByEmploye(int employeId) {
        List<Conge> list = new ArrayList<>();
        String sql = "SELECT * FROM conge WHERE employe_id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, employeId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Conge c = new Conge();
                c.setId(rs.getInt("id"));
                c.setEmployeId(employeId);
                c.setDateDebut(rs.getDate("date_debut").toLocalDate());
                c.setDateFin(rs.getDate("date_fin").toLocalDate());
                c.setType(rs.getString("type"));
                c.setApprouve(rs.getBoolean("approuve"));
                list.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
