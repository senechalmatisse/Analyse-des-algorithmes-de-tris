#!/bin/bash

# Fonction pour afficher les résultats obtenus
afficher_experimentations() {
    python3 -m ComparaisonTri.vue.Experimentation
}

# Fonction pour exécuter les expérimentations
executer_experimentations() {
    # Chemin vers le dossier contenant les fichiers .class
    CLASS_PATH=../build

    # Chemin vers la bibliothèque json-simple
    JSON_SIMPLE_PATH=../lib/json-simple-1.1.1.jar

    # Chemin vers le répertoire des fichiers expérimentaux JSON
    JSON_DIR=ComparaisonTri/modele/donnees/experimentation

    # Supprimer les anciens fichiers JSON existants
    rm "$JSON_DIR"/*.json

    # Tableaux des générateurs pour un ou plusieurs intervalle(s)
    GENERATEURS_INTERVALLE=("desordreintervalledec" "desordreintervallemel"  "desordreintervallealt")
    GENERATEURS_INTERVALLES=("desordreintervallesdec" "desordreintervallesmel" "desordreintervallesalt")

    # Tableaux des tailles et répartitions d'un générateur
    TAILLES=(1000 5000 10000 50000)
    DESDORDRE=(20 50 80)
    REPARTITION=("debut" "milieu" "fin")

    # Itération sur les générateurs d'un seul intervalle
    for gen in "${GENERATEURS_INTERVALLE[@]}"; do
        for taille in "${TAILLES[@]}"; do
            for desordre in "${DESDORDRE[@]}"; do
                for rep in "${REPARTITION[@]}"; do
                    java -cp "$CLASS_PATH":"$JSON_SIMPLE_PATH" Execution.Experimentation.ExecProgramme "$gen" $taille $desordre "$rep"
                done
            done
        done
    done

    # Itération sur les générateurs de plusieurs intervalles
    for gen in "${GENERATEURS_INTERVALLES[@]}"; do
        for taille in "${TAILLES[@]}"; do
            for desordre in "${DESDORDRE[@]}"; do
                java -cp "$CLASS_PATH":"$JSON_SIMPLE_PATH" Execution.Experimentation.ExecProgramme "$gen" $taille $desordre "milieu"
            done
        done
    done
}

# Demande à l'utilisateur s'il veut effectuer les expérimentations ou les afficher
read -p "Voulez-vous effectuer les expérimentations (e) ou les afficher (a) ?" choix

# Vérifie le choix de l'utilisateur et exécute la fonction appropriée
case $choix in
    e) executer_experimentations ;;
    a) afficher_experimentations ;;
    *) echo "Choix invalide. Veuillez saisir 'e' pour réaliser les expérimentation ou 'a' pour afficher les résultats obtenus." ;;
esac
