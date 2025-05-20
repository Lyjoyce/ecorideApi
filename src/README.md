# ecoRideApi

API Spring Boot pour une application de covoiturage écologique utilisant MySQL et MongoDB.

## Description

**ecoRideApi** est une API backend développée en Spring Boot (Java 17), qui propose des services pour une application de covoiturage écologique.  
Elle utilise :  
- Une base de données relationnelle MySQL pour les données principales.  
- MongoDB Atlas pour certains contenus NoSQL (ex : données non structurées, logs, etc.).  
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

- Le serveur tourne sur le port `8081` (modifiable dans `application.properties`).  
- Nom de l’application : `ecoRideApi`.

### Base de données MySQL

- URL de connexion :  
