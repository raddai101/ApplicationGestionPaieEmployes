-- ===================================================================
-- Application Gestion de Paie - Base de Données MySQL
-- Version: 1.0 | Date: 19 Février 2026
-- ===================================================================

-- Supprimer la base de données si elle existe
DROP DATABASE IF EXISTS gestion_paie;

-- Créer la base de données
CREATE DATABASE gestion_paie 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- Utiliser la base de données
USE gestion_paie;

-- ===================================================================
-- TABLE: utilisateur
-- ===================================================================
CREATE TABLE utilisateur (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL DEFAULT 'utilisateur',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_utilisateur_username ON utilisateur(username);

-- ===================================================================
-- TABLE: employe
-- ===================================================================
CREATE TABLE employe (
    id INT PRIMARY KEY AUTO_INCREMENT,
    matricule VARCHAR(50) NOT NULL UNIQUE,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(150),
    telephone VARCHAR(20),
    date_embauche DATE NOT NULL,
    salaire_base DECIMAL(10, 2) DEFAULT 0.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_employe_matricule ON employe(matricule);

-- ===================================================================
-- TABLE: contrat
-- ===================================================================
CREATE TABLE contrat (
    id INT PRIMARY KEY AUTO_INCREMENT,
    employe_id INT NOT NULL,
    type_contrat VARCHAR(50) NOT NULL,
    salaire_base DECIMAL(10, 2) NOT NULL,
    date_debut DATE NOT NULL,
    date_fin DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (employe_id) REFERENCES employe(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_contrat_employe_id ON contrat(employe_id);

-- ===================================================================
-- TABLE: parametre
-- ===================================================================
CREATE TABLE parametre (
    id INT PRIMARY KEY AUTO_INCREMENT,
    taux_cnss DECIMAL(5, 2) DEFAULT 6.00,
    taux_ipr DECIMAL(5, 2) DEFAULT 10.00,
    nombre_jour_conge_annuel INT DEFAULT 18,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ===================================================================
-- TABLE: prime
-- ===================================================================
CREATE TABLE prime (
    id INT PRIMARY KEY AUTO_INCREMENT,
    libelle VARCHAR(150) NOT NULL,
    montant DECIMAL(10, 2) NOT NULL,
    taxable TINYINT(1) DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ===================================================================
-- TABLE: bulletin_paie
-- ===================================================================
CREATE TABLE bulletin_paie (
    id INT PRIMARY KEY AUTO_INCREMENT,
    employe_id INT NOT NULL,
    date_paie DATE NOT NULL,
    salaire_brut DECIMAL(10, 2) NOT NULL,
    total_primes DECIMAL(10, 2) DEFAULT 0.00,
    total_retenues DECIMAL(10, 2) DEFAULT 0.00,
    salaire_net DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (employe_id) REFERENCES employe(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_bulletin_paie_employe_id ON bulletin_paie(employe_id);
CREATE INDEX idx_bulletin_paie_date_paie ON bulletin_paie(date_paie);

-- ===================================================================
-- TABLE: conge
-- ===================================================================
CREATE TABLE conge (
    id INT PRIMARY KEY AUTO_INCREMENT,
    employe_id INT NOT NULL,
    date_debut DATE NOT NULL,
    date_fin DATE NOT NULL,
    type VARCHAR(50) NOT NULL,
    approuve TINYINT(1) DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (employe_id) REFERENCES employe(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_conge_employe_id ON conge(employe_id);

-- ===================================================================
-- TABLE: retenue
-- ===================================================================
CREATE TABLE retenue (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(50) NOT NULL UNIQUE,
    taux DECIMAL(5, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ===================================================================
-- INSERTION DONNÉES INITIALES
-- ===================================================================

-- Utilisateurs (passwords hashés en SHA-256)
-- admin: password='admin'
-- comptable: password='comptable'
-- rh: password='rh'
INSERT INTO utilisateur (username, password, role) VALUES 
('admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 'admin'),
('comptable', 'a91792fb90afc8fe8bc808d0f30aa37e028b49963893d0bf038de78e78b1e6fc', 'comptable'),
('rh', '064369b841ea15785385cdcc3b6a9564150abb1e663150cbb928e9080cf2d803', 'rh');

-- Paramètres de paie
INSERT INTO parametre (taux_cnss, taux_ipr, nombre_jour_conge_annuel) VALUES 
(6.00, 10.00, 18);

-- Types de retenues
INSERT INTO retenue (nom, taux) VALUES 
('CNSS', 6.00),
('IPR', 10.00);

-- Types de primes
INSERT INTO prime (libelle, montant, taxable) VALUES 
('Prime d''assiduité', 50000, 1),
('Prime de rendement', 75000, 1),
('Prime d''ancienneté', 100000, 1),
('Prime de responsabilité', 150000, 1),
('Indemnités de transport', 30000, 0);

-- ===================================================================
-- INSERTION DONNÉES DE TEST
-- ===================================================================

-- Employés
INSERT INTO employe (matricule, nom, prenom, email, telephone, date_embauche, salaire_base) VALUES 
('MAT001', 'Martin', 'Alice', 'alice.martin@company.com', '+212 6 12 34 56 78', '2020-01-15', 50000),
('MAT002', 'Dupont', 'Bob', 'bob.dupont@company.com', '+212 6 98 76 54 32', '2019-06-20', 55000),
('MAT003', 'Bernard', 'Carol', 'carol.bernard@company.com', '+212 6 55 44 33 22', '2021-03-10', 45000),
('MAT004', 'Leclerc', 'David', 'david.leclerc@company.com', '+212 6 77 88 99 00', '2022-09-01', 60000),
('MAT005', 'Moreau', 'Emma', 'emma.moreau@company.com', '+212 6 11 22 33 44', '2021-11-15', 48000);

-- Contrats
INSERT INTO contrat (employe_id, type_contrat, salaire_base, date_debut, date_fin) VALUES 
(1, 'CDI', 50000, '2020-01-15', NULL),
(2, 'CDI', 55000, '2019-06-20', NULL),
(3, 'CDD', 45000, '2023-03-10', '2026-03-09'),
(4, 'CDI', 60000, '2022-09-01', NULL),
(5, 'CDI', 48000, '2021-11-15', NULL);

-- Congés
INSERT INTO conge (employe_id, date_debut, date_fin, type, approuve) VALUES 
(1, '2026-01-01', '2026-01-10', 'annuel', 1),
(1, '2026-07-01', '2026-07-15', 'annuel', 0),
(2, '2026-02-10', '2026-02-12', 'maladie', 1),
(3, '2026-03-20', '2026-03-21', 'événement', 1),
(4, '2026-04-01', '2026-04-05', 'annuel', 0),
(5, '2026-01-15', '2026-01-17', 'congé sans solde', 1);

-- Bulletins de paie
INSERT INTO bulletin_paie (employe_id, date_paie, salaire_brut, total_primes, total_retenues, salaire_net) VALUES 
(1, '2026-02-28', 55000, 50000, 6650, 98350),
(2, '2026-02-28', 60000, 75000, 8100, 126900),
(3, '2026-02-28', 45000, 50000, 5100, 89900),
(4, '2026-02-28', 65000, 100000, 9900, 155100),
(5, '2026-02-28', 48000, 0, 5280, 42720),
(1, '2026-01-31', 50000, 50000, 6000, 94000),
(2, '2026-01-31', 55000, 75000, 7800, 122200),
(3, '2026-01-31', 45000, 0, 4950, 40050),
(4, '2026-01-31', 60000, 100000, 9600, 150400),
(5, '2026-01-31', 48000, 50000, 5880, 92120);

-- ===================================================================
-- FIN - Base de données prête!
-- ===================================================================
