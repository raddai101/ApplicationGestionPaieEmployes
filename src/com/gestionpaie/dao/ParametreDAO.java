package com.gestionpaie.dao;

import com.gestionpaie.model.ParametrePaie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ParametreDAO {

	/**
	 * Récupère les paramètres de paie depuis la base.
	 * Si la table n'existe pas ou en cas d'erreur, renvoie des valeurs par défaut.
	 */
	public ParametrePaie getParametres() {
		String sql = "SELECT taux_cnss, taux_ipr, nombre_jour_conge_annuel FROM parametre LIMIT 1";
		try (Connection conn = ConnectionFactory.getConnection();
			 PreparedStatement ps = conn.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				double tauxCNSS = rs.getDouble("taux_cnss");
				double tauxIPR = rs.getDouble("taux_ipr");
				int jours = rs.getInt("nombre_jour_conge_annuel");
				return new ParametrePaie(tauxCNSS, tauxIPR, jours);
			}
		} catch (SQLException e) {
			// retourner des valeurs par défaut mais loguer l'erreur via exception encapsulée
			throw new DataAccessException("Erreur lors de la lecture des paramètres de paie", e);
		}
		// Valeurs par défaut si aucune ligne trouvée
		return new ParametrePaie(6.0, 10.0, 18);
	}
}
