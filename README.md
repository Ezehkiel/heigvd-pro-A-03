[![Build Status](https://travis-ci.com/Ezehkiel/heigvd-pro-A-03.svg?token=mrqvjTBsNzx3cjaWsSWP&branch=master)](https://travis-ci.com/Ezehkiel/heigvd-pro-A-03)


# Tower Defense

This software was developed as semester project (PRO) at HEIG-VD,
academic year 2018/19.

Development team:

| Name                                 | Email                           | Github          |
|--------------------------------------|---------------------------------|-----------------|
| Andrés Moreno                        | andres.moreno@heig-vd.ch        | andresMoreno96  |
| Didier Page                          | didier.page@heig-vd.ch          | pagedidier      |
| Fabrice Arno Mbassi Nguema           | fabrice.mbassinguema@heig-vd.ch | fabano237       |
| Nicodème Stalder                     | nicodeme.stadler@heig-vd.ch     | ncdm-stldr      |
| Nohan Budry    (deputy project lead) | nohan.budry@heig-vd.ch          | Jack-Eri        |
| Rémi Poulard   (project lead)        | remi.poulard@heig-vd.ch         | Ezehkiel        |

## Documentations
Pour la documentation du type texte nous utilisons "Overleaf" qui est basé en LateX. Les liens de partage se trouve ci dessous
### Cahier des charges
Le cahier des charge se trouve à cette adresse[https://www.overleaf.com/8234464881dgzwhxhjhkgp](https://www.overleaf.com/8234464881dgzwhxhjhkgp)

### Rapport
Le rapport se trouve à cette adresse [https://www.overleaf.com/5393124825ytpdpvfjrnrh](https://www.overleaf.com/5393124825ytpdpvfjrnrh)


### Google Drive
Pour avoir la documentations centralisé nous utilisons un Google Drive [https://drive.google.com/drive/u/0/folders/1SrZOVZJR2Res6Vs69PFko1lcjIGXYnzS](https://drive.google.com/drive/u/0/folders/1SrZOVZJR2Res6Vs69PFko1lcjIGXYnzS)

## Projet Maven

Nous avons un projet maven parent qui contient trois modules.

- Tower Defense Core: Contient le code qui seras utilisé par le serveur et par le jeu.
- Tower Defence Game: Constitue l'application client.
- Tower Defence Server Constitue l'application Server.

## Installation pour le développement

Ouvrez le projet maven parent ("towerdefense/pom.xml"). Maven devrait détecter automatiquement les trois modules.

Les commandes maven (clean, install, package, etc.) peuvent être effectuées depuis le projet parent ("towerdefense/pom.xml"), elles seront automatiquement effectuées sur les différents modules.

Pour run l'application client, sélectionnez le main se trouvant dans la classe Game du module towerdefense-game. Modifiez le "Working Directory" vers "towerdefense-game/src/main/resources/" et sélectionnez java 8.

Pour run l'application server, sélectionnez le main se trouvant dans la classe Server du module towerdefense-server et sélectionnez java 8.

Lorsque vous faites "mvn install" ou "mvn package", deux jar exécutables sont créés.

- Celui de l'application client, se trouve dans "towerdefense-game/target/towerdefense-game-{version}-launcher.jar". Vous pouvez double-cliquer dessus pour l'exécuter. 
- Celui de l'application serveur se trouves dans "towerdefense-server/target/towerdefense-server-{version}-launcher.jar". Pour le lancer il faut utiliser la ligne de commande et l'exécuter avec "java -jar <filename>" (si vous double-cliquer dessus, le serveur se lance mais il n'y as pas d'interface alors ça peux poser problème pour l'arrêter ou pour verifier son fonctionnement).

**Avant de commit!** Les dossiers et fichiers propres à IntelliJ sont ignorés par le ".gitignore". Mais, si vous utilisé un autre IDE, merci de ne PAS commit les fichier unique à votre IDE. Ça nous permettra de ne garder que le code et les fichier maven dans le repo. Si besoin, il est possible d'ajouter les fichiers d'autres IDE dans le ".gitignore"



## Installation du serveur pour production

Afin de faire fonctionner le projet il est nécessaire d'installer un serveur. Une base de données est requise dans le cas de parties en ligne. Pour jouer en LAN (Local Area Network) il n'est pas nécessaire d'avoir une base de donnée et donc il est possible de sauter la partie 6.1.1.



### Base de données

Le script de création de la base de données est disponible à la racine du repository GitHub. Pour installer la base de donnée il suffit de se connecter à son service PostgreSQL fonctionnant sur sa machine, puis d'importer le script.

#### Changement de mot de passe

Dans le cas ou il faudrait changer le mot de passe de l'utilisateur `toweruser` il faut le changer dans le script `towerdefense.sql` et dans le code java. Il est renseigné dans le ficher de configuration du serveur qui se trouve à l'emplacement `towerdefense-server/deploy/config.properties`

### Serveur 

Le fichier `.jar` de base permet de lancer un serveur sans le thread qui gère la partie HTTP. Il permet donc de créer un serveur pour des parties local. Pour lancer le serveur avec l'API il faut ajouter `--http` lors du lancement du fichier `.jar` et il faut aussi renseigner les informations qui se trouvent dans le fichier de configuration du serveur `towerdefense-server/deploy/config.properties`.

Les champs ont cette significations:

- https: cette option permet d'informer si nous devons utiliser HTTPS ou HTTP.

- serverHTTP: adresse IP ou nom de domaine où se trouve le serveur(dans le cas ou HTTPS est utilisé il faut mettre un nom de domaine et il faut ajouté une entrée dans le fichier `/etc/hosts` du serveur avec comme données `nomDeDomain localhost`.
- serverPort: port sur le quel le serveur écoute pour l'API, 3000 par défaut.
       `keystorePassword`: mot de passe qui a été utilisé pour généré le fichier `keystore.jks` si HTTPS est activé.
       `databaseAddress`:  adresse pour joindre la base de données
       `databasePort`: port pour joindre la base de données, par default pour postgreSQL il s'agit du port 5432
       `databasePassword`: mot de passe du compte de la base de données.
       `databaseName`: nom de la base de données.
       `databaseUser`: nom d'utilisateur qui est lié à la base de données.
  Pour les champs en relation avec la base de données il faut évidemment qu'ils soient identique aux champs qui se trouve dans le script `towerdefense.sql`.

Si le champs `https` est à `false` il n'est pas nécessaire de renseigné le champs `keystorePassword`.
Lorsque le serveur est configuré pour accepté le HTTPS il est nécessaire de créer un fichier `keystore.jks` afin de chiffrer les données. Pour pouvoir faire cette opération il est nécessaire d'avoir un nom de domaine et de faire ces commandes:
`./letsencrypt-auto certonly --standalone -d MYDOMAIN.CH`

`openssl pkcs12 -export -in fullchain.pem -inkey privkey.pem -out pkcs.p12 -name MY\_NAME}`

`keytool -importkeystore -deststorepass MYPASSWORD -destkeypass MYPASSWORD -destkeystore keystore.jks -srckeystore pkcs.p12 -srcstoretype PKCS12 -srcstorepass MYPASSWORD -alias NAME`

Une instance du serveur est opérationnelle à l'adresse `ezehkiel.ch`. Pour ce serveur, HTTPS est activé. Pour accéder au serveur il faut utiliser ces informations de connexion:

Username: root
Password: OTkwNzY4OTQxYzc5YjFlNTIxZDYyMjU2

Le fichier jar est lancé dans un `screen` afin de le faire fonctionner en arrière plan. Nous avons créée plusieurs script afin de simplifier le déploiement des versions sur le serveur. Le premier est responsable de supprimer tout les processus `screen` et de lancer un nouveau processus qui va s'exécuter en arrière plan et qui va appeler notre deuxième script.



### Client
Pour installer le client il suffit d'avoir java 1.8 installé sur sa machine et de faire un double-clic sur le fichier `.jar` du client, le jeu va alors se lancer.