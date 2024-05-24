import json, sys

import matplotlib.pyplot as plt

from PyQt5.QtWidgets import QApplication, QWidget, QPushButton, QVBoxLayout, QLabel, QComboBox, QDesktopWidget

class ComparaisonAlgorithmes(QWidget):
    """
    Classe pour la comparaison des algorithmes de tri à partir de résultats stockés dans un fichier JSON,
    et la mesure à comparer entre les différents algorithmes.
    Attributes:
        chemin_fichier_json (str): Le chemin vers le fichier JSON contenant les résultats de comparaison des tris.
        mesure (int): La mesure à comparer entre les différents algorithmes.
    """

    def __init__(self, chemin_fichier_json, mesure):
        """
        Initialise la classe avec le chemin du fichier JSON contenant les résultats de comparaison des tris,
        et la mesure à comparer entre les différents algorithmes.
        Args:
            chemin_fichier_json (str): Le chemin vers le fichier JSON.
            mesure (int): La mesure à comparer entre les différents algorithmes.
        """
        super().__init__()

        # Titre et dimension de la fenêtre
        self.setWindowTitle("Comparaisons des algorithmes")
        self.resize(1000, 1000)

        # Assignation des attributs
        self.chemin_fichier_json = chemin_fichier_json
        self.mesure = mesure

    def lire_resultats_comparaisons_tris(self):
        """
        Lit les résultats de comparaison des tris à partir d'un fichier JSON.
        Returns:
            dict: Un dictionnaire contenant les résultats de comparaison des tris.
        """
        # Ouverture et lecture du fichier JSON
        with open(self.chemin_fichier_json, 'r') as f:
            data = json.load(f)

        # Retourne les données lues
        return data

    def afficher_histogramme(self, mesure):
        """
        Affiche un histogramme pour une mesure spécifiée des résultats de comparaison des tris.
        Args:
            mesure (str): La mesure à afficher dans l'histogramme (tempsExecution, comparaisons, assignations).
        """
        # Lecture des résultats depuis le fichier JSON
        resultats = self.lire_resultats_comparaisons_tris()
    
        # Récupération des algorithmes et des valeurs de la mesure spécifiée
        algorithmes = list(resultats['resultats'])
        valeurs = [resultats['resultats'][algo][mesure] for algo in algorithmes]
        taille_tableau = resultats['tailleTableau']
    
        # Création de la figure et des axes
        fig, ax = plt.subplots(figsize=(10, 6))
    
        # Tracé des barres horizontales pour la mesure spécifiée
        ax.barh(algorithmes, valeurs, color='skyblue')
    
        # Configuration de l'axe des abscisses et des ordonnées, ainsi que du titre
        ax.set_xlabel(mesure.capitalize())
        ax.set_ylabel('Algorithmes de tri')
        ax.set_title(f'{mesure.capitalize()} du tri pour un tableau de {taille_tableau} éléments')
    
        # Affichage du graphique
        plt.tight_layout()
        plt.show()

if __name__ == "__main__":
    app = QApplication(sys.argv)
    chemin_fichier_json = sys.argv[1]

    mesures_valides = {"comparaisons", "tempsExecution", "assignations"}
    mesure = input(f"\nEntrez la mesure à afficher {mesures_valides} : ").strip()

    while mesure not in mesures_valides:
        print("Mesure invalide.")
        mesure = input(f"Entrez la mesure à afficher {mesures_valides} : ").strip()

    comparaison_algorithmes = ComparaisonAlgorithmes(chemin_fichier_json, mesure)
    comparaison_algorithmes.afficher_histogramme(mesure)