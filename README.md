# GLA - Projet : Equipe 2

## Tutoriel pour lancer le projet :

For running this project you need to run `elasticsearch` in the terminal
command. If there's no index follow the database tutorial [here](readmedb.md)

Then run JettyMain in eclipse and go to `http://localhost:8088/`. You should
the web interface and you can navigate as you wish.

**ATTENTION :** if there's a problem with the cluster's name please use
 `elasticsearch -Ecluster.name=elasticsearch` in the terminal instead of
 `elasticsearch`.

## Project tree :

**Web Interface :** src/main/webapp

**DAO :** src/main/java/com/jetty_jersey/persistance

**Database :** src/main/java/com/jetty_jersey/db

**Web Service :** src/main/java/com/jetty_jersey/ws
