SET FOREIGN_KEY_CHECKS = 0;

-- Table : UTILISATEUR
INSERT INTO UTILISATEUR (UTI_ID, ADR_ID, UTI_NOM, UTI_PRENOM, UTI_TEL, UTI_EMAIL) VALUES
(1, 1, 'Dupont', 'Jean', '0123456789', 'jean.dupont@example.com'),
(2, 2, 'Martin', 'Marie', '0987654321', 'marie.martin@example.com'),
(3, 3, 'Durand', 'Pierre', '0123456789', 'pierre.durand@example.com');

SELECT u.uti_id AS utilisateur_id, uti_nom AS utilisateur_nom, uti_prenom AS utilisateur_prenom, uti_tel AS utilisateur_tel, uti_email AS utilisateur_email, a.adr_id AS adresse_id
FROM utilisateur u
JOIN adresse a ON u.adr_id = a.adr_id;

-- Table : ADRESSE
INSERT INTO ADRESSE (ADR_ID, ADR_RUE, ADR_CODE_POSTAL, ADR_VILLE) VALUES
(1, '1 rue de la Paix', '75000', 'Paris'),
(2, '2 rue de la Liberté', '69000', 'Lyon'),
(3, '3 rue de la République', '13000', 'Marseille');

-- ALTER TABLE adresse MODIFY COLUMN ADR_ID INT AUTO_INCREMENT;
-- ALTER TABLE adresse MODIFY COLUMN ADR_ID INT AUTO_INCREMENT PRIMARY KEY;



select * from adresse;

-- Table : CLIENT
-- ALTER TABLE client MODIFY CLI_DATE_NAISSANCE DATE;

-- ALTER TABLE CLIENT
-- ADD CONSTRAINT fk_client_med FOREIGN KEY (med_id) REFERENCES MEDECIN(med_id);

-- ALTER TABLE CLIENT
-- ADD CONSTRAINT fk_client_uti FOREIGN KEY (UTI_ID) REFERENCES UTILISATEUR(UTI_ID),
-- ADD CONSTRAINT fk_client_mut FOREIGN KEY (MUT_ID) REFERENCES MUTUELLE(MUT_ID),
-- ADD CONSTRAINT fk_client_adr FOREIGN KEY (ADR_ID) REFERENCES ADRESSE(ADR_ID);


INSERT INTO CLIENT (UTI_ID, MUT_ID, ADR_ID, UTI_NOM, UTI_PRENOM, UTI_TEL, UTI_EMAIL, CLI_ID, CLI_DATE_NAISSANCE, CLI_MEDECIN_TRAITANT) VALUES
(1, 1, 1, 'Dupont', 'Jean', '0123456789', 'jean.dupont@example.com', 1, '123456789012345', '1980-01-01', 'Mutuelle A', 'Dr. Smith'),
(2, 2, 2, 'Martin', 'Marie', '0987654321', 'marie.martin@example.com', 2, '543210987654321', '1990-02-02', 'Mutuelle B', 'Dr. Johnson'),
(3, 3, 3, 'Durand', 'Pierre', '0123456789', 'pierre.durand@example.com', 3, '987654321098765', '1970-03-03', 'Mutuelle C', 'Dr. Brown'),
(4, 4, 4, 'Dorand', 'Pierre', '0123456789', 'pierre.dorand@example.com', 3, '987654321098765', '1970-03-03', 'Mutuelle C', 'Dr. Brown');

-- Table : MEDECIN
-- CREATE INDEX idx_med_id ON MEDECIN(med_id);
-- ALTER TABLE MEDECIN MODIFY MED_NUM_AGREEMENT VARCHAR(50);

-- ALTER TABLE MEDECIN
-- ADD CONSTRAINT fk_medecin_uti FOREIGN KEY (UTI_ID) REFERENCES UTILISATEUR(UTI_ID),
-- ADD CONSTRAINT fk_medecin_adr FOREIGN KEY (ADR_ID) REFERENCES ADRESSE(ADR_ID);

INSERT INTO MEDECIN (UTI_ID, ADR_ID, UTI_NOM, UTI_PRENOM, UTI_TEL, UTI_EMAIL, MED_ID, MED_NUM_AGREEMENT) VALUES
(4, 4, 'Smith', 'John', '0123456789', 'john.smith@example.com', 1, '123456789012345678'),
(5, 5, 'Johnson', 'Jane', '0987654321', 'jane.johnson@example.com', 2, '543210987654321098'),
(6, 6, 'Brown', 'Bob', '0123456789', 'bob.brown@example.com', 3, '987654321098765432');

SELECT m.med_id, m.uti_nom AS medecin_nom, m.uti_prenom AS medecin_prenom, m.uti_tel AS medecin_tel, m.uti_email AS medecin_email, m.med_num_agreement AS medecin_numero_agrement, a.adr_id AS adresse_id
FROM medecin m
JOIN adresse a ON m.adr_id = a.adr_id;

-- Table : MEDICAMENT

DROP TABLE medicament;
CREATE TABLE medicament (
    medi_id INT PRIMARY KEY,
    medi_nom VARCHAR(30),
    medi_prix DECIMAL(4, 2),
    medi_date_mise_service DATE,
    medi_quantite INT,
    type_id INT,
    CONSTRAINT fk_type_medicament
    FOREIGN KEY (type_id) REFERENCES typemedicament(tm_type_id)
);
INSERT INTO MEDICAMENT (MEDI_ID, MEDI_NOM, MEDI_PRIX, MEDI_DATE_MISE_SERVICE, MEDI_QUANTITE, tm_type_id) VALUES
(1, 'Aspirine', 5.00, '2020-01-01', 100, '1'),
(2, 'Paracetamol', 3.50, '2021-02-02', 200, '2'),
(3, 'Amoxicilline', 7.00, '2022-03-03', 50, '3');

select * from medicament;
SELECT m.medi_id, m.medi_nom, m.medi_prix, m.medi_date_mise_service, m.medi_quantite, t.tm_type_nom
FROM medicament m
JOIN typemedicament t ON m.type_id = t.tm_type_id;

ALTER TABLE medicament
CHANGE COLUMN type_id tm_type_id INT;

ALTER TABLE medicament
ADD CONSTRAINT fk_type_medicament
FOREIGN KEY (tm_type_id) REFERENCES typemedicament(tm_type_id);

-- Table : MUTUELLE
INSERT INTO MUTUELLE (MUT_ID, MUT_NOM, MUT_TEL, MUT_EMAIL, MUT_DEPARTEMENT, MUT_TAUX_PRISE_EN_CHARGE) VALUES
(1, 'Mutuelle A', 12345678901234, 'mutuelleA@example.com', '75', 80),
(2, 'Mutuelle B', 54321098765432, 'mutuelleB@example.com', '69', 70),
(3, 'Mutuelle C', 98765432109876, 'mutuelleC@example.com', '13', 90);

SELECT m.mut_id AS mutuelle_id, m.mut_nom AS mutuelle_nom, m.mut_tel AS mutuelle_id, m.mut_email AS mutuelle_email, m.mut_departement AS mutuelle_departement, m.mut_taux_prise_en_charge AS mutuelle_tauxPriseEnCharge, a.adr_id
FROM mutuelle m
JOIN client c ON m.mut_id = c.mut_id
JOIN utilisateur u ON c.uti_id = u.uti_id
JOIN adresse a ON u.adr_id = a.adr_id;

-- ALTER TABLE mutuelle ADD ADR_ID INT;
-- ALTER TABLE mutuelle
-- ADD CONSTRAINT fk_mutuelle_adr FOREIGN KEY (ADR_ID) REFERENCES ADRESSE (ADR_ID);


-- Table : ORDONNANCE
INSERT INTO ORDONNANCE (ORD_ID, SPE_UTI_ID, SPE_ID, UTI_ID, CLI_UTI_ID, ORD_DATE, ORD_NOM_CLIENT, ORD_NOM_MEDECIN, ORD_NOM_SPECIALISTE) VALUES
(1, 4, 1, 1, 1, '2023-01-01', 'Jean Dupont', 'Dr. Smith', 'Dr. Johnson'),
(2, 5, 2, 2, 2, '2023-02-02', 'Marie Martin', 'Dr. Johnson', 'Dr. Brown'),
(3, 6, 3, 3, 3, '2023-03-03', 'Pierre Durand', 'Dr. Brown', 'Dr. Smith');

-- ALTER TABLE ordonnance MODIFY ORD_DATE DATE;

-- Table : PROCEDER
INSERT INTO PROCEDER (ACH_ID, MEDI_ID) VALUES
(1, 1),
(2, 2),
(3, 3);

-- Table : SPECIALISTE
CREATE TABLE SPECIALISTE (
    spe_ID INT PRIMARY KEY,
    spe_nom VARCHAR(30) NOT NULL,
    type_id INT NOT NULL,
    FOREIGN KEY (type_id) REFERENCES TypeSpecialiste(type_id)
);
-- CREATE INDEX idx_spe_id ON SPECIALISTE(med_id);
-- ALTER TABLE specialiste ADD MED_ID INT;
-- ALTER TABLE specialiste ADD ADR_ID INT;

-- ALTER TABLE specialiste ADD CONSTRAINT fk_specialiste_adresse FOREIGN KEY (ADR_ID) REFERENCES ADRESSE (ADR_ID);

INSERT INTO SPECIALISTE (UTI_ID, SPE_ID, ADR_ID, UTI_NOM, UTI_PRENOM, UTI_TEL, UTI_EMAIL, MED_ID, MED_NUM_AGREEMENT, SPE_NOM, ENUM_CARDIOLOGUE__DERMATOLOGUE__OPHTALMOLOGUE__GENERALISTE__ORL_) VALUES
(7, 1, 7, 'Johnson', 'Jane', '0987654321', 'jane.johnson@example.com', 1, '543210987654321098', 'Dr. Johnson', 'CARDIOLOGUE'),
(8, 2, 8, 'Brown', 'Bob', '0123456789', 'bob.brown@example.com', 2, '987654321098765432', 'Dr. Brown', 'DERMATOLOGUE'),
(9, 3, 9, 'Smith', 'John', '0123456789', 'john.smith@example.com', 3, '123456789012345678', 'Dr. Smith', 'OPHTALMOLOGUE');

SELECT TABLE_NAME, CONSTRAINT_NAME, COLUMN_NAME, REFERENCED_TABLE_NAME, REFERENCED_COLUMN_NAME
FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE
WHERE REFERENCED_TABLE_NAME = 'specialiste';

SELECT s.spe_id AS specialiste_id, s.spe_nom AS specialiste_nom, s.type_id AS specialite_type_id, med_id AS medecin_id, adr_id AS adresse_id
FROM specialiste s
JOIN typespecialiste ts ON s.type_id = ts.type_id;


-- Types de spécialistes
CREATE TABLE TypeSpecialiste (
    type_id INT AUTO_INCREMENT PRIMARY KEY,
    type_nom VARCHAR(50) UNIQUE NOT NULL
);

-- Ajout de la clé étrangère dans Specialiste
-- ALTER TABLE SPECIALISTE 
-- ADD COLUMN type_id INT,
-- ADD CONSTRAINT fk_specialiste_type FOREIGN KEY (type_id) REFERENCES TypeSpecialiste (type_id);

SELECT o.ORD_DATE, u.UTI_NOM AS client_nom, m.UTI_NOM AS medecin_nom
FROM ORDONNANCE o
JOIN CLIENT c ON o.CLI_UTI_ID = c.CLI_ID
JOIN UTILISATEUR u ON c.UTI_ID = u.UTI_ID
JOIN MEDECIN m ON o.SPE_UTI_ID = m.UTI_ID;

-- Table : ACHAT
INSERT INTO ACHAT (ACH_ID, UTI_ID) VALUES
(1, 1),
(2, 2),
(3, 3);

SELECT *
FROM client c 
JOIN utilisateur u ON c.UTI_ID = u.UTI_ID
JOIN mutuelle m ON c.MUT_ID = m.MUT_ID
JOIN medecin med ON u.UTI_ID = med.UTI_ID;

SELECT a.ADR_RUE, a.ADR_CODE_POSTAL, a.ADR_VILLE, u.UTI_NOM, u.UTI_PRENOM, u.UTI_TEL, u.UTI_EMAIL
FROM adresse a
JOIN utilisateur u ON a.ADR_ID = u.ADR_ID;


-- Types de médicaments
CREATE TABLE TypeMedicament (
    type_id INT AUTO_INCREMENT PRIMARY KEY,
    type_nom VARCHAR(50) UNIQUE NOT NULL
);
-- ALTER TABLE medicament DROP FOREIGN KEY medicament_ibfk_1;
-- SHOW CREATE TABLE typemedicament;
-- ALTER TABLE typemedicament CHANGE type_nom tm_type_nom VARCHAR(50), ALGORITHM=COPY;
-- ALTER TABLE medicament ADD CONSTRAINT medicament_ibfk_1 FOREIGN KEY (type_id) REFERENCES typemedicament(tm_type_id); 


-- Ajout de la clé étrangère dans Medicament
-- ALTER TABLE MEDICAMENT 
-- ADD COLUMN type_id INT,
-- ADD CONSTRAINT fk_medicament_type FOREIGN KEY (type_id) REFERENCES TypeMedicament (type_id);



select * from ordonnance;

SELECT
    TABLE_NAME,
    COLUMN_NAME,
    CONSTRAINT_NAME,
    REFERENCED_TABLE_NAME,
    REFERENCED_COLUMN_NAME
FROM
    INFORMATION_SCHEMA.KEY_COLUMN_USAGE
WHERE
    REFERENCED_TABLE_NAME = 'typemedicament' AND
    TABLE_SCHEMA = 'sparadrap';




