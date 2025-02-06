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