CREATE DATABASE IF NOT EXISTS sparadrap;
USE sparadrap;

-- Suppression des contraintes existantes pour éviter les conflits lors des modifications
SET FOREIGN_KEY_CHECKS = 0;

-- Table : UTILISATEUR
CREATE TABLE UTILISATEUR (
    uti_id INT AUTO_INCREMENT PRIMARY KEY,
    uti_nom VARCHAR(30),
    uti_prenom VARCHAR(30),
    uti_tel VARCHAR(14),
    uti_email VARCHAR(50)
);

-- Table : ADRESSE
CREATE TABLE ADRESSE (
    adr_id INT AUTO_INCREMENT PRIMARY KEY,
    adr_rue VARCHAR(30),
    adr_code_postal VARCHAR(5),
    adr_ville VARCHAR(40)
);

-- Table : CLIENT (hérite de UTILISATEUR)
CREATE TABLE CLIENT (
    cli_id INT AUTO_INCREMENT PRIMARY KEY,
    cli_num_secu_social VARCHAR(15),
    cli_date_naissance DATE,
    uti_id INT UNIQUE,
    FOREIGN KEY (uti_id) REFERENCES UTILISATEUR(uti_id)
);

-- Table : MEDECIN (hérite de UTILISATEUR)
CREATE TABLE MEDECIN (
    med_id INT AUTO_INCREMENT PRIMARY KEY,
    med_num_agreement VARCHAR(18),
    uti_id INT UNIQUE,
    FOREIGN KEY (uti_id) REFERENCES UTILISATEUR(uti_id)
);

-- Table : SPECIALISTE (hérite de MEDECIN)
CREATE TABLE SPECIALISTE (
    spe_id INT AUTO_INCREMENT PRIMARY KEY,
    spe_nom VARCHAR(30),
    spe_specialite ENUM('Cardiologue', 'Dermatologue', 'Ophtalmologue', 'Generaliste', 'ORL'),
    med_id INT UNIQUE,
    FOREIGN KEY (med_id) REFERENCES MEDECIN(med_id)
);

-- Table : MUTUELLE
CREATE TABLE MUTUELLE (
    mut_id INT AUTO_INCREMENT PRIMARY KEY,
    mut_nom VARCHAR(30),
    mut_tel BIGINT,
    mut_email VARCHAR(50),
    mut_departement VARCHAR(3),
    mut_taux_prise_en_charge TINYINT
);

-- Table : TYPEMEDICAMENT
CREATE TABLE TYPEMEDICAMENT (
    type_med_id INT AUTO_INCREMENT PRIMARY KEY,
    type_med_nom VARCHAR(50) UNIQUE
);

-- Table : MEDICAMENT
CREATE TABLE MEDICAMENT (
    medi_id INT AUTO_INCREMENT PRIMARY KEY,
    medi_nom VARCHAR(30),
    medi_prix DECIMAL(4, 2),
    medi_date_mise_service DATE,
    medi_quantite INT,
    type_med_id INT,
    FOREIGN KEY (type_med_id) REFERENCES TYPEMEDICAMENT(type_med_id)
);

-- Table : ORDONNANCE
CREATE TABLE ORDONNANCE (
    ord_id INT AUTO_INCREMENT PRIMARY KEY,
    ord_date DATE,
    ord_nom_client VARCHAR(30),
    ord_nom_medecin VARCHAR(30),
    ord_nom_specialiste VARCHAR(30)
);

-- Table : ACHAT
CREATE TABLE ACHAT (
    ach_id INT AUTO_INCREMENT PRIMARY KEY,
    ach_type ENUM('Direct', 'Ordonnance')
);

-- Association : Posséder (Utilisateur - Adresse)
CREATE TABLE Posseder (
    poss_id INT AUTO_INCREMENT PRIMARY KEY,
    uti_id INT,
    adr_id INT,
    type_possession ENUM('Résidentiel', 'Professionnel', 'Autre'),
    FOREIGN KEY (uti_id) REFERENCES UTILISATEUR(uti_id),
    FOREIGN KEY (adr_id) REFERENCES ADRESSE(adr_id)
);

-- Association : Adhérer (Client - Mutuelle)
CREATE TABLE Adherer (
    adh_id INT AUTO_INCREMENT PRIMARY KEY,
    cli_id INT,
    mut_id INT,
    date_adhesion DATE,
    niveau_couverture VARCHAR(50),
    FOREIGN KEY (cli_id) REFERENCES CLIENT(cli_id),
    FOREIGN KEY (mut_id) REFERENCES MUTUELLE(mut_id)
);

-- Association : Fournir (Specialiste - TypeSpecialite)
CREATE TABLE TypeSpecialite (
    type_spe_id INT AUTO_INCREMENT PRIMARY KEY,
    type_spe_nom VARCHAR(50) UNIQUE
);

CREATE TABLE Fournir (
    fournir_id INT AUTO_INCREMENT PRIMARY KEY,
    spe_id INT,
    type_spe_id INT,
    date_debut DATE,
    date_fin DATE,
    FOREIGN KEY (spe_id) REFERENCES SPECIALISTE(spe_id),
    FOREIGN KEY (type_spe_id) REFERENCES TypeSpecialite(type_spe_id)
);

-- Association : Effectuer (Achat - Utilisateur)
CREATE TABLE Effectuer (
    effectuer_id INT AUTO_INCREMENT PRIMARY KEY,
    ach_id INT,
    uti_id INT,
    date_achat DATE,
    mode_paiement ENUM('Carte', 'Espèces', 'Virement'),
    FOREIGN KEY (ach_id) REFERENCES ACHAT(ach_id),
    FOREIGN KEY (uti_id) REFERENCES UTILISATEUR(uti_id)
);

-- Association : Delivrer (Ordonnance - Medicament)
CREATE TABLE Delivrer (
    delivrer_id INT AUTO_INCREMENT PRIMARY KEY,
    ord_id INT,
    medi_id INT,
    quantite_prescrite INT,
    duree_validite INT,
    FOREIGN KEY (ord_id) REFERENCES ORDONNANCE(ord_id),
    FOREIGN KEY (medi_id) REFERENCES MEDICAMENT(medi_id)
);

-- Ajout des données
INSERT INTO UTILISATEUR (uti_nom, uti_prenom, uti_tel, uti_email) VALUES
('Dupont', 'Jean', '0123456789', 'jean.dupont@example.com'),
('Martin', 'Marie', '0987654321', 'marie.martin@example.com'),
('Durand', 'Pierre', '0123456789', 'pierre.durand@example.com');

INSERT INTO ADRESSE (adr_rue, adr_code_postal, adr_ville) VALUES
('1 rue de la Paix', '75000', 'Paris'),
('2 rue de la Liberté', '69000', 'Lyon'),
('3 rue de la République', '13000', 'Marseille');

INSERT INTO CLIENT (cli_num_secu_social, cli_date_naissance, uti_id) VALUES
('123456789012345', '1980-01-01', 1),
('543210987654321', '1990-02-02', 2);

INSERT INTO MEDECIN (med_num_agreement, uti_id) VALUES
('123456789012345', 3);

INSERT INTO SPECIALISTE (spe_nom, spe_specialite, med_id) VALUES
('Dr. Smith', 'Cardiologue', 1);

INSERT INTO TypeSpecialite (type_spe_nom) VALUES
('Cardiologie'), ('Dermatologie');

INSERT INTO ORDONNANCE (ord_date, ord_nom_client, ord_nom_medecin, ord_nom_specialiste) VALUES
('2023-01-10', 'Jean Dupont', 'Dr. Durand', 'Dr. Bernard');


INSERT INTO Fournir (spe_id, type_spe_id, date_debut, date_fin) VALUES
(1, 1, '2020-01-01', NULL);

INSERT INTO Adherer (cli_id, mut_id, date_adhesion, niveau_couverture) VALUES
(1, 1, '2020-01-01', 'Standard'), -- Jean Dupont - Mutuelle Santé
(2, 2, '2021-06-15', 'Premium');  -- Marie Martin - Mutuelle Plus


INSERT INTO ACHAT (ach_type) VALUES
('Direct'), ('Ordonnance');

INSERT INTO Effectuer (ach_id, uti_id, date_achat, mode_paiement) VALUES
(1, 1, '2023-11-26', 'Carte');

INSERT INTO MEDICAMENT (medi_nom, medi_prix, medi_date_mise_service, medi_quantite, type_med_id) VALUES
('Paracétamol', 3.50, '2021-02-15', 200, 1);


INSERT INTO MUTUELLE (mut_nom, mut_tel, mut_email, mut_departement, mut_taux_prise_en_charge) VALUES
('Mutuelle Santé', 1234567890, 'contact@mutuellesante.com', '75', 80),
('Mutuelle Plus', 9876543210, 'contact@mutuelleplus.com', '69', 70),
('AssurVie', 1122334455, 'contact@assurvie.com', '13', 85);


INSERT INTO Delivrer (ord_id, medi_id, quantite_prescrite, duree_validite) VALUES
(1, 1, 2, 30);

SET FOREIGN_KEY_CHECKS = 1;
