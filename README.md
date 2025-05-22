# ecoRideApi

API Spring Boot pour une application de covoiturage écologique utilisant MySQL et MongoDB.

## Description

**ecoRideApi** est une API backend développée en Spring Boot (Java 17), qui propose des services pour une application de covoiturage écologique.  
Elle utilise :  
- Une base de données relationnelle MySQL pour les données principales, entities.  
- MongoDB Atlas pour certains contenus NoSQL (ex :contact.java).  
- Une authentification sécurisée avec JWT.  
- Plusieurs profils de configuration pour gérer le développement et la production.

## Fonctionnalités principales

- Gestion des utilisateurs et de leurs profils  
- Gestion des trajets, réservations, et offres de covoiturage  
- Authentification et autorisation via JWT  
- Support des bases MySQL (relationnelle) et MongoDB (NoSQL)  
- Mode développement et production via profils Spring

## Configuration

### Ports & Application

- Le serveur tourne sur le port `8082` (modifiable dans `application.properties`).  
- Nom de l’application : `ecoRideApi`.

### Base de données MySQL

- URL de connexion : configurée via Docker Compose, accessible sur le port `3306`.  
- Base de données, utilisateur et mot de passe définis dans le fichier `docker-compose.yml`.

### MongoDB Atlas

- Utilisé en cloud (pas dans Docker), connexion paramétrée dans les fichiers Spring via URI.

## Lancement avec Docker

L'application utilise Docker Compose pour orchestrer :

- Un conteneur MySQL  
- Un conteneur pour l’application ecoRideApi

### Prérequis

- Docker installé  
- Docker Compose installé  
- Cloner ce dépôt et se placer dans le dossier racine contenant `docker-compose.yml`

### Étapes pour lancer l'application : 

# Se placer dans le dossier du projet : 
cd /chemin/vers/ton/projet/ecorideApi
# Construire les images Docker :
docker-compose build
# Démarrer les conteneurs en arrière-plan :
docker-compose up -d
# Voir les logs en temps réel (optionnel) :
docker-compose logs -f
# Arrêter les conteneurs :
docker-compose down
