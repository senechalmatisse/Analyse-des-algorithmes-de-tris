## Description du Projet
Il existe tout un bestiaires d'algorithmes de tri, certains peu efficaces, d'autres plus performant, d'autres encore dont la performance dépend de la structure des données à trier. L'objectif de ce projet est d'implémenter toute une collection de ces algorithmes et de visualiser leur exécution. Au-delà de cet aspect, nous pouvons nous demander quelle est l'efficacité (nombre de comparaisons, accès aux données, temps d'exécution total) de ces algorithmes en fonction du désordre des données en entrée (quantité et répartition) ? Il s'agira alors d'implémenter un générateur de données paramétré par un niveau de désordre et d'expérimenter les algorithmes pour en tirer une analyse comparative.
 
## Compilation du Projet

Pour compiler tous les fichiers, exécutez simplement le script `compil.sh` :

```
./compil.sh
```

---

## Exécution du Projet

### Avec Interface Graphique

Pour exécuter le projet avec une interface graphique, utilisez le script `runAvecInterface.sh`.

---

### Sans Interface Graphique

Pour exécuter le projet sans interface graphique, utilisez le script `runSansInterface.sh`. Vous devez fournir au moins 4 arguments :

- Le nom du générateur (une chaîne de caractères), avec 6 choix possibles :
    - [desordreintervalledec](docs/api/ComparaisonTri/modele/generateur/intervalle/generateurDecroissant/GenerateurDesordreIntervalleDec.html),
    - [desordreintervallesdec](docs/api/ComparaisonTri/modele/generateur/intervalles/generateurDecroissant/GenerateurDesordreIntervallesDec.html),
    - [desordreintervallemel](docs/api/ComparaisonTri/modele/generateur/intervalle/generateurMelange/GenerateurDesordreIntervalleMel.html).
    - [desordreintervallesmel](docs/api/ComparaisonTri/modele/generateur/intervalles/generateurMelange/GenerateurDesordreIntervallesMel.html).
    - [desordreintervallealt](docs/api/ComparaisonTri/modele/generateur/intervalle/generateurAlterne/GenerateurDesordreIntervalleAlt.html).
    - [desordreintervallesalt](docs/api/ComparaisonTri/modele/generateur/intervalles/generateurAlterne/GenerateurDesordreIntervalleAlt.html).
- La taille du tableau à générer (un entier).
- Le pourcentage de désordre (un entier).
- La répartition du désordre (une chaîne de caractères), avec trois choix possibles :
    - "debut",
    - "milieu",
    - "fin".

Exemple :

```
./runSansInterface.sh desordreintervalledec 1000 30 milieu
```

---

## Affichage Graphique des tris

Utilité des differents boutons : 

- Resultat : affiche le tri terminé
- Ré-initialiser : Revenir à la liste non trié
- Lancement : lancement de l'affichage du tri avec une pause entre chaque affichage
- Vitesse Lente : met le temps de pause à 2 secondes
- Vitesse Rapide : met le temps de pause à 0,2 secondes
- Vitesse Très Rapide : met le temps de pause à 0,02 secondes
- Pause / Reprendre : met en pause ou reprends l'affichage du tri
- Lancement pas à pas : affiche l'instant du tri suivant
- Revenir en arrière : affiche l'instant du tri precedent
    
---

## Tests

Pour exécuter les tests, utilisez le script `runTest.sh`.

---

## Experimentations

Pour les expérimentations, utilisez le script `runExperimentation.sh`.
Il y a ensuite deux choix possibles :

- taper `e` pour réaliser les expérimentations sur tous les générateurs,
- taper `a` pour affciher les résultats obtenus.

Les résultats seront des fichiers sous la forme JSON et se trouveront dans le dossier : `src/ComparaisonTri/modele/donnees/experimentation/`.

---

N'hésitez pas à consulter les fichiers de scripts pour plus de détails sur les commandes et les options disponibles.