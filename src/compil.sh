#!/bin/bash

# Chemin vers le dossier contenant les fichiers .class
CLASS_PATH=../build

# Chemin vers la bibliothèque json-simple
JSON_SIMPLE_PATH=../lib/json-simple-1.1.1.jar

# Chemin vers la bibliothèque junit
JUNIT_PATH=../lib/junit-4.9.jar

# Fonction pour compiler les fichiers Java (sans les tests)
compile_java() {
    find ComparaisonTri Execution -name '*.java' ! \( -path '*/*Tests/*' -o -name '*Test*' \) -exec javac -cp $JSON_SIMPLE_PATH -d $CLASS_PATH {} +
}

# Fonction pour compiler les fichiers de tests Java
compile_tests() {
    javac -cp $JUNIT_PATH -d $CLASS_PATH Execution/*Test.java ComparaisonTri/modele/generateur/*.java ComparaisonTri/modele/generateur/*/*.java  ComparaisonTri/modele/generateur/*/*/*.java ComparaisonTri/modele/algosTris/*.java ComparaisonTri/modele/algosTris/algosTrisTests/*.java
}

# Compile les fichiers
compile_java
compile_tests

# Vérifie le code de retour de la compilation
if [ $? -eq 0 ]; then
    echo "Compilation réussie."
else
    echo "Erreur de compilation."
fi