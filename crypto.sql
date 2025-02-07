CREATE DATABASE crypto;
\c crypto;

CREATE TABLE cryptomonnaie(
   id_cryptomonnaie SERIAL,
   nom_crypto VARCHAR(50)  NOT NULL,
   val_initial NUMERIC(15,2)  ,
   PRIMARY KEY(id_cryptomonnaie)
);

CREATE TABLE mvt_fond(
   Id_mvt_fond SERIAL,
   id_user INTEGER NOT NULL,
   depot NUMERIC(15,2)   NOT NULL,
   retrait NUMERIC(15,2)   NOT NULL,
   dt_mvt TIMESTAMP NOT NULL,
   PRIMARY KEY(Id_mvt_fond)
);

CREATE TABLE mvt_transaction(
   id_mvt_transaction SERIAL,
   qtt NUMERIC(15,2)   NOT NULL,
   montant NUMERIC(15,2)   NOT NULL,
   isVente BOOLEAN NOT NULL,
   date_transaction TIMESTAMP NOT NULL,
   id_user INTEGER NOT NULL,
   id_cryptomonnaie INTEGER NOT NULL,
   PRIMARY KEY(id_mvt_transaction),
   UNIQUE(id_user),
   FOREIGN KEY(id_cryptomonnaie) REFERENCES cryptomonnaie(id_cryptomonnaie)
);


