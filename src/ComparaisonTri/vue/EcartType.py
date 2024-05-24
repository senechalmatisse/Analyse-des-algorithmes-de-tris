import json, sys

import matplotlib.pyplot as plt

from PyQt5.QtWidgets import QApplication, QWidget
from PyQt5.QtCore import Qt

class EcartType(QWidget):
    """
    Classe pour afficher un graphique de boîte à moustaches représentant l'écart-type des résultats obtenus.
    """

    def __init__(self, chemin_fichier_json, mesure):
        """
        Initialise la classe avec le chemin du fichier JSON contenant les résultats de comparaison des tris,
        et la mesure à comparer entre les différents algorithmes.
        Args:
            chemin_fichier_json (str): Le chemin vers le fichier JSON.
            mesure (str): La mesure à comparer entre les différents algorithmes.
        """
        super().__init__()

        self.setWindowTitle("Écart-type des algorithmes")
        self.resize(1000, 1000)

        self.chemin_fichier_json = chemin_fichier_json
        self.mesure = mesure

    def afficher_ecart_type(self):
        """Affiche le graphique de boîte à moustaches."""
        # Lecture des données depuis le fichier JSON
        donnees = self.lire_donnees()

        # Extraction des noms des algorithmes et des valeurs de la mesure choisie
        algorithmes = list(donnees['resultats'])

        # Création du graphique boîte à moustaches
        fig, ax = plt.subplots(figsize=(15, 7))

        valeurs = []
        for algo in algorithmes :

            for resultat in donnees['resultats'][algo]:
                valeurs.append(resultat[self.mesure])

            ax.boxplot(valeurs, vert=True, whis=1.5, positions=[algorithmes.index(algo)+1])

            valeurs.clear()

        # Configuration des axes et du titre
        ax.set_xticks(range(1, len(algorithmes) + 1))
        ax.set_xticklabels(algorithmes, rotation=45, ha='right')
        ax.set_title('Écart-type des résultats')
        ax.set_xlabel('Algorithmes de tri')
        ax.set_ylabel(self.mesure.capitalize())

        # Affichage du graphique
        plt.tight_layout()
        plt.show()

    def lire_donnees(self):
        """
        Lit les données à partir du fichier JSON.
        Returns:
            dict: Un dictionnaire contenant les données lues à partir du fichier JSON.
        """
        with open(self.chemin_fichier_json, 'r') as fichier:
            donnees = json.load(fichier)
        return donnees

# Sans interface graphique
if __name__ == "__main__":
    app = QApplication(sys.argv)
    chemin_fichier_json = sys.argv[1]

    mesures_valides = {"comparaisons", "tempsExecution", "assignations"}
    mesure = input(f"\nEntrez la mesure à afficher {mesures_valides} : ").strip()

    while mesure not in mesures_valides:
        print("Mesure invalide.")
        mesure = input(f"Entrez la mesure à afficher {mesures_valides} : ").strip()

    ecart_type = EcartType(chemin_fichier_json, mesure)
    ecart_type.afficher_ecart_type()