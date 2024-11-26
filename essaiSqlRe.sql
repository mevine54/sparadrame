-- Exemple : Gestion des Adresses via Posseder
-- Structure de la table Posseder

CREATE TABLE Posseder (
    poss_id INT AUTO_INCREMENT PRIMARY KEY,
    uti_id INT, -- Référence vers UTILISATEUR
    adr_id INT, -- Référence vers ADRESSE
    type_possession ENUM('Résidentiel', 'Professionnel', 'Autre'),
    FOREIGN KEY (uti_id) REFERENCES UTILISATEUR(uti_id),
    FOREIGN KEY (adr_id) REFERENCES ADRESSE(adr_id)
);
-- Exemple d’insertion de données
-- Jean Dupont habite à Paris (résidentiel).
-- Marie Martin travaille à Lyon (professionnel).

INSERT INTO UTILISATEUR (uti_nom, uti_prenom, uti_tel, uti_email) VALUES
('Dupont', 'Jean', '0123456789', 'jean.dupont@example.com'),
('Martin', 'Marie', '0987654321', 'marie.martin@example.com');

INSERT INTO ADRESSE (adr_rue, adr_code_postal, adr_ville) VALUES
('1 rue de la Paix', '75000', 'Paris'),
('2 rue de la Liberté', '69000', 'Lyon');

INSERT INTO Posseder (uti_id, adr_id, type_possession) VALUES
(1, 1, 'Résidentiel'), -- Jean Dupont
(2, 2, 'Professionnel'); -- Marie Martin
-- Exemple de Requête SQL
-- Pour récupérer les adresses d’un utilisateur (avec type de possession) :


SELECT u.uti_nom, u.uti_prenom, a.adr_rue, a.adr_code_postal, a.adr_ville, p.type_possession
FROM UTILISATEUR u
JOIN Posseder p ON u.uti_id = p.uti_id
JOIN ADRESSE a ON p.adr_id = a.adr_id;
