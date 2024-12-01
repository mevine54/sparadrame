INSERT INTO Adresse (rue, codePostal, ville) VALUES ('123 Rue de la Paix', '75000', 'Paris');
INSERT INTO Adresse (rue, codePostal, ville) VALUES ('456 Avenue des Champs-Élysées', '75008', 'Paris');


INSERT INTO Utilisateur (nom, prenom, email, motDePasse, role, adresse_id, telephone) VALUES ('Dupont', 'Jean', 'jean.dupont@example.com', 'password123', 'manager', 1, '0102030405');
INSERT INTO Utilisateur (nom, prenom, email, motDePasse, role, adresse_id, telephone) VALUES ('Martin', 'Marie', 'marie.martin@example.com', 'password456', 'utilisateur', 2, '0607080910');

INSERT INTO Mutuelle (nom, tauxRemboursement, adresse_id, departement) VALUES ('Mutuelle Santé', 70.00, 1, 'Paris');
INSERT INTO Mutuelle (nom, tauxRemboursement, adresse_id, departement) VALUES ('Mutuelle Familiale', 80.00, 2, 'Paris');


-- Ajout d'adresses pour les médecins
INSERT INTO Adresse (rue, codePostal, ville) VALUES ('789 Rue du Docteur', '75015', 'Paris');
INSERT INTO Adresse (rue, codePostal, ville) VALUES ('321 Rue des Médecins', '75016', 'Paris');

-- Utilisateurs associés aux médecins
INSERT INTO Utilisateur (nom, prenom, email, motDePasse, role, adresse_id, telephone) VALUES ('Durand', 'Pierre', 'pierre.durand@example.com', 'password789', 'utilisateur', 3, '0123456789');
INSERT INTO Utilisateur (nom, prenom, email, motDePasse, role, adresse_id, telephone) VALUES ('Leroy', 'Sophie', 'sophie.leroy@example.com', 'password101112', 'utilisateur', 4, '0987654321');

-- Insertion des médecins
INSERT INTO Medecin (numeroAgrement, utilisateur_id, telephone, email) VALUES ('AG123456', 3, '0123456789', 'pierre.durand@example.com');
INSERT INTO Medecin (numeroAgrement, utilisateur_id, telephone, email) VALUES ('AG654321', 4, '0987654321', 'sophie.leroy@example.com');


-- Ajout d'adresses pour les clients
INSERT INTO Adresse (rue, codePostal, ville) VALUES ('654 Rue des Clients', '75017', 'Paris');
INSERT INTO Adresse (rue, codePostal, ville) VALUES ('987 Rue des Clients', '75018', 'Paris');

-- Utilisateurs associés aux clients
INSERT INTO Utilisateur (nom, prenom, email, motDePasse, role, adresse_id, telephone) VALUES ('Bernard', 'Claire', 'claire.bernard@example.com', 'password131415', 'utilisateur', 5, '0112233445');
INSERT INTO Utilisateur (nom, prenom, email, motDePasse, role, adresse_id, telephone) VALUES ('Moreau', 'Antoine', 'antoine.moreau@example.com', 'password161718', 'utilisateur', 6, '0223344556');

-- Insertion des clients
INSERT INTO Client (securiteSociale, dateNaissance, utilisateur_id, mutuelle_id, medecinTraitantId) VALUES ('123456789012345', '1990-05-15', 5, 1, 1);
INSERT INTO Client (securiteSociale, dateNaissance, utilisateur_id, mutuelle_id, medecinTraitantId) VALUES ('987654321098765', '1985-08-20', 6, 2, 2);


-- Ajout d'adresses pour les spécialistes
INSERT INTO Adresse (rue, codePostal, ville) VALUES ('159 Rue des Spécialistes', '75019', 'Paris');
INSERT INTO Adresse (rue, codePostal, ville) VALUES ('753 Rue des Spécialistes', '75020', 'Paris');

-- Utilisateurs associés aux spécialistes
INSERT INTO Utilisateur (nom, prenom, email, motDePasse, role, adresse_id, telephone) VALUES ('Simon', 'Luc', 'luc.simon@example.com', 'password192021', 'utilisateur', 7, '0334455667');
INSERT INTO Utilisateur (nom, prenom, email, motDePasse, role, adresse_id, telephone) VALUES ('Petit', 'Julie', 'julie.petit@example.com', 'password222324', 'utilisateur', 8, '0445566778');

-- Insertion des spécialistes
INSERT INTO Specialiste (numeroAgrement, specialite, utilisateur_id, telephone, email) VALUES ('AG789012', 'cardiologie', 7, '0334455667', 'luc.simon@example.com');
INSERT INTO Specialiste (numeroAgrement, specialite, utilisateur_id, telephone, email) VALUES ('AG210987', 'dermatologie', 8, '0445566778', 'julie.petit@example.com');


INSERT INTO Medicament (nom, categorie, prix, dateMiseEnService, quantite) VALUES ('Paracétamol', 'antalgique', 5.00, '2010-01-01', 100);
INSERT INTO Medicament (nom, categorie, prix, dateMiseEnService, quantite) VALUES ('Amoxicilline', 'antibiotique', 12.50, '2012-06-15', 50);


-- Insertion des ordonnances (en respectant les contraintes)
INSERT INTO Ordonnance (date, medecin_id, specialiste_id, client_id) VALUES ('2023-10-01', 1, NULL, 5);
INSERT INTO Ordonnance (date, medecin_id, specialiste_id, client_id) VALUES ('2023-10-15', NULL, 1, 6);


INSERT INTO OrdonnanceMedicament (ordonnance_id, medicament_id, quantitePrescrite, prixTotal) VALUES (1, 1, 2, 10.00);
INSERT INTO OrdonnanceMedicament (ordonnance_id, medicament_id, quantitePrescrite, prixTotal) VALUES (2, 2, 1, 12.50);

INSERT INTO RelationMedecinClient (client_id, medecin_id, typeRelation, dateDebutRelation, dateFinRelation) VALUES (5, 1, 'traitant', '2020-01-01', NULL);
INSERT INTO RelationMedecinClient (client_id, medecin_id, typeRelation, dateDebutRelation, dateFinRelation) VALUES (6, 2, 'traitant', '2021-06-15', NULL);


INSERT INTO AffiliationMutuelle (client_id, mutuelle_id, dateAffiliation, dateFinAffiliation, tauxRemboursementPersonnalise) VALUES (5, 1, '2020-01-01', NULL, 75.00);
INSERT INTO AffiliationMutuelle (client_id, mutuelle_id, dateAffiliation, dateFinAffiliation, tauxRemboursementPersonnalise) VALUES (6, 2, '2021-07-01', NULL, 80.00);


INSERT INTO Achat (dateAchat, client_id, medicament_id, quantiteAchat, montantTotal) VALUES ('2023-10-05', 5, 1, 2, 10.00);
INSERT INTO Achat (dateAchat, client_id, medicament_id, quantiteAchat, montantTotal) VALUES ('2023-10-10', 6, 2, 1, 12.50);


ALTER TABLE Medecin DROP COLUMN telephone;
ALTER TABLE Medecin DROP COLUMN email;

SELECT
    Medecin.id,
    Medecin.numeroAgrement,
    Utilisateur.nom,
    Utilisateur.prenom,
    Utilisateur.email,
    Utilisateur.telephone
FROM
    Medecin
JOIN
    Utilisateur ON Medecin.utilisateur_id = Utilisateur.id;
    
    

ALTER TABLE Utilisateur MODIFY COLUMN role VARCHAR(20) NOT NULL;
ALTER TABLE Utilisateur MODIFY COLUMN role ENUM('admin', 'user', 'medecin', 'manager', 'superuser') NOT NULL;
SELECT DISTINCT role FROM Utilisateur;
ALTER TABLE Utilisateur MODIFY COLUMN role ENUM('admin', 'user', 'medecin') NOT NULL;
INSERT INTO Utilisateur (nom, prenom, email, motDePasse, role, adresse_id, telephone)
VALUES ('Durand', 'Pierre', 'pierre.durand@example.com', 'password123', 'medecin', 1, '0123456789');
INSERT INTO Utilisateur (nom, prenom, email, motDePasse, role, adresse_id, telephone)
VALUES ('Durant', 'Pierre', 'pierre.durant@example.com', 'password103', 'medecin', 2, '0123456788');

-- Supposons que l'ID généré est 3

-- Insertion dans Medecin
INSERT INTO Medecin (numeroAgrement, utilisateur_id) VALUES ('AG123456', 3);


SELECT * FROM Utilisateur WHERE email = 'pierre.durand@example.com';

select * from Utilisateur;
describe Utilisateur;
SET FOREIGN_KEY_CHECKS = 1;
