INSERT INTO cryptomonnaie (nom_crypto, val_initial) VALUES
('Bitcoin', 50000.00),
('Ethereum', 4000.00),
('Ripple', 100.00),
('Litecoin', 200.00),
('Cardano', 2.50),
('Polkadot', 35.00),
('Dogecoin', 0.25),
('Solana', 150.00),
('Chainlink', 25.00),
('Stellar', 0.80);

INSERT INTO utilisateur (email, mot_de_passe, telephoneUser, username) 
VALUES ('user1@example.com', 'user1', '0123456789', 'user1');

INSERT INTO utilisateur (email, mot_de_passe, telephoneUser, username) 
VALUES ('user2@example.com', 'user2', '0123456790', 'user2');

INSERT INTO utilisateur (email, mot_de_passe, telephoneUser, username) 
VALUES ('user3@example.com', 'user3', '0123456791', 'user3');

INSERT INTO utilisateur (email, mot_de_passe, telephoneUser, username) 
VALUES ('user4@example.com', 'user4', '0123456792', 'user4');

INSERT INTO utilisateur (email, mot_de_passe, telephoneUser, username) 
VALUES ('user5@example.com', 'user5', '0123456793', 'user5');

INSERT INTO utilisateur (email, mot_de_passe, telephoneUser, username) 
VALUES ('user6@example.com', 'user6', '0123456794', 'user6');

INSERT INTO utilisateur (email, mot_de_passe, telephoneUser, username) 
VALUES ('user7@example.com', 'user7', '0123456795', 'user7');

INSERT INTO utilisateur (email, mot_de_passe, telephoneUser, username) 
VALUES ('user8@example.com', 'user8', '0123456796', 'user8');

INSERT INTO utilisateur (email, mot_de_passe, telephoneUser, username) 
VALUES ('user9@example.com', 'user9', '0123456797', 'user9');

INSERT INTO utilisateur (email, mot_de_passe, telephoneUser, username) 
VALUES ('user10@example.com', 'user10', '0123456798', 'user10');


-- Insertion des mouvements pour l'utilisateur 1
INSERT INTO mvt_transaction (qtt, isVente, date_transaction, id_user, id_cryptomonnaie) 
VALUES (1, FALSE, '2025-02-01 10:00:00', 1, 1);  -- Achat de 1 Bitcoin
INSERT INTO mvt_transaction (qtt, isVente, date_transaction, id_user, id_cryptomonnaie) 
VALUES (0.8, TRUE, '2025-02-05 14:00:00', 1, 2);  -- Vente de 0.8 Ethereum

-- Insertion des mouvements pour l'utilisateur 2
INSERT INTO mvt_transaction (qtt, isVente, date_transaction, id_user, id_cryptomonnaie) 
VALUES (50, FALSE, '2025-02-03 11:00:00', 2, 3);  -- Achat de 50 Ripple
INSERT INTO mvt_transaction (qtt, isVente, date_transaction, id_user, id_cryptomonnaie) 
VALUES (20, TRUE, '2025-02-04 13:00:00', 2, 4);  -- Vente de 20 Litecoin

-- Insertion des mouvements pour l'utilisateur 3
INSERT INTO mvt_transaction (qtt, isVente, date_transaction, id_user, id_cryptomonnaie) 
VALUES (5, FALSE, '2025-02-02 09:00:00', 3, 5);  -- Achat de 5 Cardano
INSERT INTO mvt_transaction (qtt, isVente, date_transaction, id_user, id_cryptomonnaie) 
VALUES (30, TRUE, '2025-02-06 15:00:00', 3, 6);  -- Vente de 30 Polkadot

-- Insertion des mouvements pour l'utilisateur 4
INSERT INTO mvt_transaction (qtt, isVente, date_transaction, id_user, id_cryptomonnaie) 
VALUES (10, 2500.00, FALSE, '2025-02-07 08:00:00', 4, 7);  -- Achat de 10 Dogecoin
INSERT INTO mvt_transaction (qtt, isVente, date_transaction, id_user, id_cryptomonnaie) 
VALUES (5, TRUE, '2025-02-08 17:00:00', 4, 8);  -- Vente de 5 Solana

-- Insertion des mouvements pour l'utilisateur 5
INSERT INTO mvt_transaction (qtt, isVente, date_transaction, id_user, id_cryptomonnaie) 
VALUES (2, FALSE, '2025-02-03 12:00:00', 5, 9);  -- Achat de 2 Chainlink
INSERT INTO mvt_transaction (qtt, isVente, date_transaction, id_user, id_cryptomonnaie) 
VALUES (10, TRUE, '2025-02-05 10:00:00', 5, 10);  -- Vente de 10 Stellar

