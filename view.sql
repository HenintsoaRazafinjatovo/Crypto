create or replace view v_mvt_fond_complet as
select mf.id_mvt_fond,mf.id_user,
case 
	when mf.depot =0 and mf.retrait !=0 then 'Retrait'
	when mf.depot!=0 and mf.retrait =0 then 'Depot' 
	else 'Type impossible' end as type,
case 
	when mf.depot =0 then mf.retrait
	when mf.depot!=0 then mf.depot 
	else -1 end as montant,
mf.dt_mvt,
mf.etat,
case 
    when mf.etat is NULL then 'Pending'
	when mf.etat=false then 'Refused'
	else 'Validated'
end as etatText
from mvt_fond mf 


CREATE OR REPLACE VIEW vue_crypto_par_utilisateur AS
SELECT 
    mt.id_user,
    c.id_cryptomonnaie,
    c.nom_crypto,
    SUM(mt.qtt) AS total_quantite,
    SUM(mt.qtt * hc.valeur) AS total_valeur
FROM 
    mvt_transaction mt
JOIN 
    cryptomonnaie c ON mt.id_cryptomonnaie = c.id_cryptomonnaie
LEFT JOIN 
    (
        SELECT hc1.id_cryptomonnaie, hc1.valeur
        FROM Histo_crypto hc1
        WHERE hc1.date_historique = (
            SELECT MAX(hc2.date_historique) 
            FROM Histo_crypto hc2 
            WHERE hc2.id_cryptomonnaie = hc1.id_cryptomonnaie
        )
    ) hc 
    ON c.id_cryptomonnaie = hc.id_cryptomonnaie
WHERE 
    mt.isVente = FALSE  
GROUP BY 
    mt.id_user, c.id_cryptomonnaie, c.nom_crypto;




CREATE OR REPLACE VIEW vue_fond_total_utilisateur AS
SELECT 
    id_user,
    SUM(depot) AS total_depot,
    SUM(retrait) AS total_retrait,
    SUM(depot) - SUM(retrait) AS fond_total
FROM 
    mvt_fond
GROUP BY 
    id_user;


-- CREATE OR REPLACE VIEW vue_liste_cryptos_utilisateurs AS
-- SELECT 
--     u.id_user,
--     c.id_cryptomonnaie,
--     c.nom_crypto,
--     m.qtt,
--     m.montant,
--     m.isVente,
--     m.date_transaction
-- FROM 
--     mvt_transaction m
-- JOIN 
--     cryptomonnaie c ON m.id_cryptomonnaie = c.id_cryptomonnaie
-- JOIN 
--     utilisateur u ON m.id_user = u.id_user;

-- CREATE OR REPLACE FUNCTION calculer_et_inserer_montant()
-- RETURNS TRIGGER AS $$
-- DECLARE
--     dernier_prix NUMERIC(15,2);
-- BEGIN
--     -- Récupérer le prix le plus récent de la cryptomonnaie dans histo_crypto
--     SELECT valeur INTO dernier_prix
--     FROM histo_crypto
--     WHERE id_cryptomonnaie = NEW.id_cryptomonnaie
--     ORDER BY date_historique DESC
--     LIMIT 1;

--     -- Si aucun prix n'est trouvé, lever une exception
--     IF dernier_prix IS NULL THEN
--         RAISE EXCEPTION 'Aucun prix trouvé pour la cryptomonnaie %', NEW.id_cryptomonnaie;
--     END IF;

--     -- Calculer le montant et l'assigner à NEW.montant
--     NEW.montant := NEW.qtt * dernier_prix;

--     -- Retourner la nouvelle ligne à insérer
--     RETURN NEW;
-- END;
-- $$ LANGUAGE plpgsql;

-- SELECT 
--     m.id_user,
--     SUM(CASE WHEN m.isVente = FALSE THEN m.montant ELSE 0 END) AS total_achat_montant,
--     SUM(CASE WHEN m.isVente = TRUE THEN m.montant ELSE 0 END) AS total_vente_montant,
--     (SUM(CASE WHEN m.isVente = FALSE THEN m.montant ELSE 0 END) -
--      SUM(CASE WHEN m.isVente = TRUE THEN m.montant ELSE 0 END)) AS valeur_portefeuille 
-- FROM 
--     mvt_transaction m
-- JOIN 
--     cryptomonnaie c ON m.id_cryptomonnaie = c.id_cryptomonnaie
-- GROUP BY 
--     m.id_user
-- ORDER BY 
--     m.id_user;

CREATE OR REPLACE VIEW vue_valeur_portefeuille_utilisateur AS
SELECT 
    m.id_user,
    SUM(CASE WHEN m.isVente = FALSE THEN m.montant ELSE 0 END) AS total_achat_montant,
    SUM(CASE WHEN m.isVente = TRUE THEN m.montant ELSE 0 END) AS total_vente_montant,
    (SUM(CASE WHEN m.isVente = FALSE THEN m.montant ELSE 0 END) - 
     SUM(CASE WHEN m.isVente = TRUE THEN m.montant ELSE 0 END)) AS valeur_portefeuille
FROM 
    mvt_transaction m
JOIN 
    cryptomonnaie c ON m.id_cryptomonnaie = c.id_cryptomonnaie
GROUP BY 
    m.id_user
ORDER BY 
    m.id_user;



CREATE OR REPLACE FUNCTION calculer_montant()
RETURNS TRIGGER AS $$
DECLARE
    dernier_prix NUMERIC(15,2);
BEGIN
    SELECT valeur INTO dernier_prix
    FROM histo_crypto
    WHERE id_cryptomonnaie = NEW.id_cryptomonnaie
    AND date_historique <= NEW.date_transaction
    ORDER BY date_historique DESC
    LIMIT 1;

    IF dernier_prix IS NULL THEN
        RAISE EXCEPTION 'Aucun prix trouvé pour la cryptomonnaie % avant ou à la date %', 
                        NEW.id_cryptomonnaie, NEW.date_transaction;
    END IF;

    NEW.montant := NEW.qtt * dernier_prix;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_calculer_montant
BEFORE INSERT ON mvt_transaction
FOR EACH ROW
EXECUTE FUNCTION calculer_montant();
