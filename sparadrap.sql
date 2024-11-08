SET FOREIGN_KEY_CHECKS = 0;

-- Table : UTILISATEUR
INSERT INTO UTILISATEUR (UTI_ID, ADR_ID, UTI_NOM, UTI_PRENOM, UTI_TEL, UTI_EMAIL) VALUES
(1, 1, 'Dupont', 'Jean', '0123456789', 'jean.dupont@example.com'),
(2, 2, 'Martin', 'Marie', '0987654321', 'marie.martin@example.com'),
(3, 3, 'Durand', 'Pierre', '0123456789', 'pierre.durand@example.com');

-- Table : ADRESSE
INSERT INTO ADRESSE (ADR_ID, ADR_RUE, ADR_CODE_POSTAL, ADR_VILLE) VALUES
(1, '1 rue de la Paix', '75000', 'Paris'),
(2, '2 rue de la Liberté', '69000', 'Lyon'),
(3, '3 rue de la République', '13000', 'Marseille');

-- Table : CLIENT
INSERT INTO CLIENT (UTI_ID, MUT_ID, ADR_ID, UTI_NOM, UTI_PRENOM, UTI_TEL, UTI_EMAIL, CLI_ID, CLI_NUM_SECU_SOCIAL, CLI_DATE_NAISSANCE, CLI_MUTUELLE, CLI_MEDECIN_TRAITANT) VALUES
(1, 1, 1, 'Dupont', 'Jean', '0123456789', 'jean.dupont@example.com', 1, '123456789012345', '1980-01-01', 'Mutuelle A', 'Dr. Smith'),
(2, 2, 2, 'Martin', 'Marie', '0987654321', 'marie.martin@example.com', 2, '543210987654321', '1990-02-02', 'Mutuelle B', 'Dr. Johnson'),
(3, 3, 3, 'Durand', 'Pierre', '0123456789', 'pierre.durand@example.com', 3, '987654321098765', '1970-03-03', 'Mutuelle C', 'Dr. Brown');

-- Table : MEDECIN
INSERT INTO MEDECIN (UTI_ID, ADR_ID, UTI_NOM, UTI_PRENOM, UTI_TEL, UTI_EMAIL, MED_ID, MED_NUM_AGREEMENT) VALUES
(4, 4, 'Smith', 'John', '0123456789', 'john.smith@example.com', 1, '123456789012345678'),
(5, 5, 'Johnson', 'Jane', '0987654321', 'jane.johnson@example.com', 2, '543210987654321098'),
(6, 6, 'Brown', 'Bob', '0123456789', 'bob.brown@example.com', 3, '987654321098765432');

-- Table : MEDICAMENT
INSERT INTO MEDICAMENT (MEDI_ID, MEDI_NOM, MEDI_PRIX, MEDI_DATE_MISE_SERVICE, MEDI_QUANTITE, ENUM_DOULEUR__ANTIINFLAMMATOIRE__ANTIBIO_) VALUES
(1, 'Aspirine', 5.00, '2020-01-01', 100, 'ANTIINFLAMMATOIRE'),
(2, 'Paracetamol', 3.50, '2021-02-02', 200, 'DOULEUR'),
(3, 'Amoxicilline', 7.00, '2022-03-03', 50, 'ANTIBIO');

-- Table : MUTUELLE
INSERT INTO MUTUELLE (MUT_ID, MUT_NOM, MUT_TEL, MUT_EMAIL, MUT_DEPARTEMENT, MUT_TAUX_PRISE_EN_CHARGE) VALUES
(1, 'Mutuelle A', 12345678901234, 'mutuelleA@example.com', '75', 80),
(2, 'Mutuelle B', 54321098765432, 'mutuelleB@example.com', '69', 70),
(3, 'Mutuelle C', 98765432109876, 'mutuelleC@example.com', '13', 90);

-- Table : ORDONNANCE
INSERT INTO ORDONNANCE (ORD_ID, SPE_UTI_ID, SPE_ID, UTI_ID, CLI_UTI_ID, ORD_DATE, ORD_NOM_CLIENT, ORD_NOM_MEDECIN, ORD_NOM_SPECIALISTE) VALUES
(1, 4, 1, 1, 1, '2023-01-01', 'Jean Dupont', 'Dr. Smith', 'Dr. Johnson'),
(2, 5, 2, 2, 2, '2023-02-02', 'Marie Martin', 'Dr. Johnson', 'Dr. Brown'),
(3, 6, 3, 3, 3, '2023-03-03', 'Pierre Durand', 'Dr. Brown', 'Dr. Smith');

-- Table : PROCEDER
INSERT INTO PROCEDER (ACH_ID, MEDI_ID) VALUES
(1, 1),
(2, 2),
(3, 3);

-- Table : SPECIALISTE
INSERT INTO SPECIALISTE (UTI_ID, SPE_ID, ADR_ID, UTI_NOM, UTI_PRENOM, UTI_TEL, UTI_EMAIL, MED_ID, MED_NUM_AGREEMENT, SPE_NOM, ENUM_CARDIOLOGUE__DERMATOLOGUE__OPHTALMOLOGUE__GENERALISTE__ORL_) VALUES
(7, 1, 7, 'Johnson', 'Jane', '0987654321', 'jane.johnson@example.com', 1, '543210987654321098', 'Dr. Johnson', 'CARDIOLOGUE'),
(8, 2, 8, 'Brown', 'Bob', '0123456789', 'bob.brown@example.com', 2, '987654321098765432', 'Dr. Brown', 'DERMATOLOGUE'),
(9, 3, 9, 'Smith', 'John', '0123456789', 'john.smith@example.com', 3, '123456789012345678', 'Dr. Smith', 'OPHTALMOLOGUE');

-- Table : ACHAT
INSERT INTO ACHAT (ACH_ID, UTI_ID) VALUES
(1, 1),
(2, 2),
(3, 3);

-- Réactiver les contraintes de clé étrangère
SET FOREIGN_KEY_CHECKS = 1;



SELECT a.ADR_RUE, a.ADR_CODE_POSTAL, a.ADR_VILLE, u.UTI_NOM, u.UTI_PRENOM, u.UTI_TEL, u.UTI_EMAIL
FROM adresse a
JOIN utilisateur u ON a.ADR_ID = u.ADR_ID;

SELECT *
FROM adresse a 
JOIN utilisateur u ON a.ADR_ID = u.ADR_ID;

SELECT *
FROM utilisateur;

SELECT *
from ADRESSE;

select *
from client;

select * 
from mutuelle;

select *
from specialiste;

select *
from ordonnance;

select * 
from medecin;

select * 
from proceder;

select * 
from medicament;









