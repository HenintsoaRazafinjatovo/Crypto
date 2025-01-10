# Utiliser l'image officielle PostgreSQL
FROM postgres:15-alpine

# Définir les variables d'environnement
ENV POSTGRES_PORT=
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=postgres
ENV POSTGRES_DB=crypto

# Copier le script SQL
COPY init.sql /docker-entrypoint-initdb.d/

# Exposer le port par défaut de PostgreSQL
EXPOSE 5432
