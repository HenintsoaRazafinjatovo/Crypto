select * from cryptomonnaie c join histo_crypto h on c.id_cryptomonnaie = h.id_cryptomonnaie;

CREATE OR REPLACE VIEW vue_crypto_par_utilisateur AS
SELECT 
    u.id_user,
    c.id_cryptomonnaie,
    c.nom_crypto,
    SUM(mt.qtt) AS total_quantite
FROM 
    mvt_transaction mt
JOIN 
    cryptomonnaie c ON mt.id_cryptomonnaie = c.id_cryptomonnaie
JOIN 
    (SELECT DISTINCT id_user FROM mvt_transaction) u ON mt.id_user = u.id_user
GROUP BY 
    u.id_user, c.id_cryptomonnaie, c.nom_crypto


CREATE OR REPLACE VIEW vue_fond_total_utilisateur AS
SELECT 
    id_user,
    SUM(depot) AS total_depot,
    SUM(retrait) AS total_retrait,
    SUM(depot) - SUM(retrait) AS fond_total
FROM 
    mvt_fond
GROUP BY 
    id_user