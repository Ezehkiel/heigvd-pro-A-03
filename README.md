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

##Projet Maven

Nous avons un projet maven parent qui contient trois modules.

- Tower Defense Core: Contient le code qui seras utilisé par le serveur et par le jeu.
- Tower Defence Game: Constitue l'application client.
- Tower Defence Server Constitue l'application Server.

## Installation

Ouvrez le projet maven parent ("towerdefense/pom.xml"). Maven devrait détecter automatiquement les trois modules.

Les commandes maven (clean, install, package, etc.) peuvent être effectuées depuis le projet parent ("towerdefense/pom.xml"), elles seront automatiquement effectuées sur les différents modules.

Pour run l'application client, sélectionnez le main se trouvant dans la classe Game du module towerdefense-game. Modifiez le "Working Directory" vers "towerdefense-game/src/resources/" et sélectionnez java 8.

Pour run l'application server, sélectionnez le main se trouvant dans la classe Server du module towerdefense-server et sélectionnez java 8.

Lorsque vous faites "mvn install" ou "mvn package", deux jar exécutables sont créés.

- Celui de l'application client, se trouve dans "towerdefense-game/src/resources/towerdefense-game-{version}-launcher.jar". Vous pouvez double-cliquer dessus pour l'exécuter. Les dossiers et fichiers se trouvant dans le même répertoire sont utilisé par l'application.
- Celui de l'application serveur se trouves dans "towerdefense-server/target/towerdefense-server-{version}-launcher.jar". Pour le lancer il faut utiliser la ligne de commande et l'exécuter avec "java -jar <filename>" (si vous double-cliquer dessus, le serveur se lance mais il n'y as pas d'interface alors ça peux poser problème pour l'arrêter ou pour verifier son fonctionnement).

**Avant de commit!** Les dossiers et fichiers propres à IntelliJ sont ignorés par le ".gitignore". Mais, si vous utilisé un autre IDE, merci de ne PAS commit les fichier unique à votre IDE. Ça nous permettra de ne garder que le code et les fichier maven dans le repo. Si besoin on peut regarder pour ajouter les fichiers d'autres IDE dans le ".gitignore".