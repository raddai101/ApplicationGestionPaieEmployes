package com.gestionpaie.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ConnectionFactory {

    private static String url;
    private static String user;
    private static String password;

    static {
        Properties prop = new Properties();
        InputStream input = null;
        
        try {
            // Try 1: Load from classpath
            input = ConnectionFactory.class.getClassLoader().getResourceAsStream("config.properties");
            
            // Try 2: If not found in classpath, load from file system (resources folder)
            if (input == null) {
                java.nio.file.Path resourcePath = Paths.get("resources", "config.properties");
                if (Files.exists(resourcePath)) {
                    input = new FileInputStream(resourcePath.toFile());
                }
            }
            
            // Try 3: If still not found, try from project root
            if (input == null) {
                java.nio.file.Path rootPath = Paths.get("config.properties");
                if (Files.exists(rootPath)) {
                    input = new FileInputStream(rootPath.toFile());
                }
            }
            
            if (input == null) {
                throw new DataAccessException("Impossible de charger le fichier de configuration de la base de données (config.properties): fichier non trouvé");
            }
            
            prop.load(input);
            url = prop.getProperty("db.url");
            user = prop.getProperty("db.user");
            password = prop.getProperty("db.password");
        } catch (Exception e) {
            throw new DataAccessException("Impossible de charger le fichier de configuration de la base de données (config.properties)", e);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (Exception e) {
                    // Ignore
                }
            }
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
