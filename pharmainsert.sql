select * from utilisateur;

-- Désactiver le mode de mise à jour sécurisé (si nécessaire)
SET SQL_SAFE_UPDATES = 0;

-- Mettre à jour les numéros d'agrément pour qu'ils contiennent exactement 18 chiffres
UPDATE MEDECIN
SET med_num_agreement = LPAD(med_num_agreement, 18, '0')
WHERE LENGTH(med_num_agreement) < 18;

-- Réactiver le mode de mise à jour sécurisé (si nécessaire)
SET SQL_SAFE_UPDATES = 1;

-- Désactiver le mode de mise à jour sécurisé (si nécessaire)
SET SQL_SAFE_UPDATES = 0;

-- Mettre à jour les numéros d'agrément pour qu'ils contiennent exactement 18 chiffres
UPDATE MEDECIN
SET med_num_agreement = LPAD(med_num_agreement, 18, '0')
WHERE LENGTH(med_num_agreement) < 18;

-- Réactiver le mode de mise à jour sécurisé (si nécessaire)
SET SQL_SAFE_UPDATES = 1;

SELECT * FROM MEDECIN WHERE LENGTH(med_num_agreement) <> 18;


UPDATE MEDECIN
SET med_num_agreement = LPAD(med_num_agreement, 18, '0')
WHERE LENGTH(med_num_agreement) < 18 AND med_id IN (SELECT med_id FROM MEDECIN WHERE LENGTH(med_num_agreement) < 18);
