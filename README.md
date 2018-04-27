# GLA - Projet : Equipe 2

## Tutoriel pour lancer le projet :

Pour lancer le projet il suffit de lancer `elasticsearch` sur le terminal.
Si les index ne sont pas créé référez vous au tuto pour faire fonctionner la
base de donnée [ici](readmedb.md)

Ensuite le serveur de JettyMain et aller dans `http://localhost:8088/` pour obtenir
l'interface web et naviguer comme bon vous semble

**ATTENTION :** si il y a un problème avec le nom du cluster veuillez lancer sur
le terminal `elasticsearch -Ecluster.name=elasticsearch`

## Arborescence du projet :

**Interface Web :** src/main/webapp

**DAO & Bdd :** src/main/java/com/jetty_jersey/persistance

**Web Service :** src/main/java/com/jetty_jersey/ws
