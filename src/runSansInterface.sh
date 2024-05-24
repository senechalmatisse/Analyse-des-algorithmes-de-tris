#!/bin/bash

# Chemin vers le dossier contenant les fichiers .class
CLASS_PATH=../build

# Chemin vers la bibliothèque json-simple
JSON_SIMPLE_PATH=../lib/json-simple-1.1.1.jar

# Vérification du nombre d'arguments
if [ "$#" -ne 4 ]; then
    printf "Erreur : 4 arguments sont nécessaires.\n" >&2
    exit 1
fi

# Vérification du deuxième et troisième argument
if ! [[ $2 =~ ^[[:digit:]]+$ ]] || ! [[ $3 =~ ^[[:digit:]]+$ ]]; then
    printf "Erreur : Le deuxième et le troisième argument doivent être des nombres positifs.\n" >&2
    exit 1
fi

# Vérification du dernier argument
if ! [[ $4 =~ ^[[:alpha:]]+$ ]]; then
    printf "Erreur : Le dernier argument doit être une chaîne de caractères.\n" >&2
    exit 1
fi

# Affichage du menu pour choisir une option
printf "Choisissez une option (1, 2 ou 3) :\n1. Afficher le tri\n2. Comparaison entre les algorithmes\n3. Afficher l'écart-type\n"
read -p "Votre choix : " option

# Exécution du programme Java avec les arguments
java -cp "$CLASS_PATH":"$JSON_SIMPLE_PATH" Execution.SansInterface.ExecProgramme "$@"

# Vérification de l'exécution du programme Java
if [ $? -ne 0 ]; then
    printf "Erreur lors de l'exécution du programme Java.\n" >&2
    exit 1
fi

# Noms d'algorithme valides
algorithmes_valides=("PigeonholeSort" "Quicksort" "Introsort" "TriAPeigne" "TriPairImpair" "TriParInsertion" "TimSort" "TriDeShell" "TriFusion" "TriABulles" "TriCocktail" "TriParTas" "SmoothSort" "TriParSelection")

# Vérification de l'option choisie
case $option in
    1)
        # Affiche la visualisation tri en temps réel

        # Demande à l'utilisateur de saisir le nom de l'algorithme à observer
        while true; do
            echo -e "\nVoici le nom des algorithmes possibles à observer :\n${algorithmes_valides[@]}\n"
            read -p "Entrez le nom de l'algorithme à observer : " nom_algo

            # Supprimer les espaces et les accents du nom d'algorithme
            nom_algo_formate=$(echo "$nom_algo" | iconv -f utf-8 -t ascii//TRANSLIT | tr -d '[:space:]')
            nom_algo_minuscule=$(echo "$nom_algo_formate" | tr '[:upper:]' '[:lower:]')

            # Vérifie que le nom rentré correspond bien à ceux disponibles
            if [[ " ${algorithmes_valides[@],,} " =~ " $nom_algo_minuscule " ]]; then
                break
            else
                printf "Nom d'algorithme invalide.\n"
            fi

        done

        # Récupère les arguments et ajoute le nom de l'algorithme à la liste des arguments
        args="${@:1} $nom_algo_minuscule"

        java -cp "$CLASS_PATH":"$JSON_SIMPLE_PATH" ComparaisonTri.vue.FenetreVisualisation $args
        ;;
    2)
        # Affiche l'histogramme de la comparaison entre tous les algorithmes pour une mesure choisie

        python3 -m ComparaisonTri.vue.ComparaisonAlgorithmes ComparaisonTri/modele/donnees/resultatsMoyensTris.json
        ;;
    3)
        # Affiche l'écart-type pour tous les algorithmes pour une mesure choisie

        python3 -m ComparaisonTri.vue.EcartType ComparaisonTri/modele/donnees/resultatsTris.json
        ;;
    *)
        printf "Option invalide. Veuillez choisir 1, 2 ou 3.\n" >&2
        exit 1
        ;;
esac
