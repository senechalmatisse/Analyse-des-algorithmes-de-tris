#!/bin/bash

# Chemin vers le dossier contenant les fichiers .class
CLASS_PATH=../build

# Chemin vers la bibliothèque json-simple
JSON_SIMPLE_PATH=../lib/junit-4.9.jar

# Vérification de la compilation réussie
if [ $? -eq 0 ]; then
    # Exécution du programme Java avec des arguments
    java -cp $CLASS_PATH:$JSON_SIMPLE_PATH Execution.ExecTest "$@"
else
    echo "Erreur de compilation. Veuillez corriger les erreurs avant l'exécution."
fi