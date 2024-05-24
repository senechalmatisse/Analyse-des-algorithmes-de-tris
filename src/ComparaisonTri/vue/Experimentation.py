import os
import json
import matplotlib.pyplot as plt
from PyQt5.QtWidgets import QApplication, QWidget, QVBoxLayout, QLabel, QComboBox, QPushButton, QDesktopWidget

class Experimentation:
    """
    Classe permettant de charger les données d'expérimentation à partir de fichiers JSON
    et de tracer un graphique comparatif des performances des algorithmes.
    """

    def __init__(self):
        """
        Initialise une instance de la classe Experimentation.
        """
        self.mesure = None

    def charger_donnees(self):
        """
        Charge les données d'expérimentation à partir des fichiers JSON.
    
        Returns:
            dict: Un dictionnaire contenant les données d'expérimentation.
                  La clé est une chaîne de caractères au format "nom_generateur_desordre_repartition",
                  et la valeur est un dictionnaire des résultats.
        """
        chemin_fichier = "ComparaisonTri/modele/donnees/experimentation"
        donnees = {}
        for nom_fichier in os.listdir(chemin_fichier):
            if "resultatsMoyensTris" in nom_fichier and nom_fichier.endswith('.json'):
                with open(os.path.join(chemin_fichier, nom_fichier), 'r') as file:
                    donnees_experimentation = json.load(file)
                    nom_generateur = donnees_experimentation["nomGenerateur"]
                    desordre = donnees_experimentation["desordre"]
                    repartition = donnees_experimentation["repartition"]
                    cle = f"{nom_generateur}_{desordre}_{repartition}"
                    donnees[cle] = donnees_experimentation["resultats"]
        return donnees

    def tracer_graphique(self):
        """
        Trace un graphique comparatif des performances des algorithmes.
        """
        donnees = self.charger_donnees()
        noms_algorithmes, generateur_couleurs = self.tracer_preparer(donnees)
        self.tracer_courbes(donnees, noms_algorithmes, generateur_couleurs)
        self.proprietes_graphique()
        figManager = plt.get_current_fig_manager()
        figManager.window.showMaximized()
        plt.show()

    def tracer_preparer(self, donnees):
        """
        Prépare les données nécessaires au tracé du graphique.

        Args:
            donnees (dict): Un dictionnaire contenant les données d'expérimentation.

        Returns:
            tuple: Un tuple contenant une liste des noms d'algorithmes et un dictionnaire des couleurs des générateurs.
        """
        noms_algorithmes = []
        generateur_couleurs = {}
        for cle, resultats in donnees.items():
            nom_generateur, desordre, _ = cle.split('_')
            if nom_generateur not in generateur_couleurs:
                color = plt.cm.tab10(len(generateur_couleurs))
                generateur_couleurs[nom_generateur] = color
            for algo in resultats:
                if algo not in noms_algorithmes:
                    noms_algorithmes.append(algo)
        return noms_algorithmes, generateur_couleurs

    def tracer_courbes(self, donnees, noms_algorithmes, generateur_couleurs):
        """
        Trace les courbes du graphique comparatif.
    
        Args:
            donnees (dict): Un dictionnaire contenant les données d'expérimentation.
            noms_algorithmes (list): Une liste des noms d'algorithmes.
            generateur_couleurs (dict): Un dictionnaire des couleurs des générateurs.
        """
        markers = {'20': 'o', '50': '^', '80': 's'}
        linestyle = {'debut': 'solid', 'milieu': 'dashed', 'fin': 'dotted'}
        
        for cle, resultats in donnees.items():
            nom_generateur, desordre, repartition = cle.split('_')
            valeurs_y = [resultats.get(algo, {}).get(self.mesure, 0) for algo in noms_algorithmes]
            marker = markers.get(desordre, 'o')
            color = generateur_couleurs[nom_generateur]
            
            plt.plot(noms_algorithmes, valeurs_y, label=f"{nom_generateur} (Desordre: {desordre}, Repartition: {repartition})", marker=marker, linestyle=linestyle.get(repartition, 'solid'), color=color)

    def proprietes_graphique(self):
        """
        Définit les propriétés du graphique.
        """
        plt.xlabel('Algorithmes')
        plt.ylabel(self.mesure.capitalize())
        plt.title('Comparaison des algorithmes pour {}'.format(self.mesure))
        plt.xticks(rotation=90)
        plt.legend()

    def choisir_mesure(self):
        """
        Permet à l'utilisateur de choisir d'afficher les résultats obtenus pour la mesure choisit.
        """
        app = QApplication([])
        fenetre = QWidget()
        layout = QVBoxLayout()

        label_mesure = QLabel("Choisissez la mesure à afficher :")
        combo_mesure = QComboBox()
        combo_mesure.addItem("tempsExecution")
        combo_mesure.addItem("assignations")
        combo_mesure.addItem("comparaisons")
        bouton_afficher = QPushButton("Afficher")

        def afficher_mesure():
            self.mesure = combo_mesure.currentText()
            self.tracer_graphique()
            # Ne ferme pas la fenêtre ici

        bouton_afficher.clicked.connect(afficher_mesure)

        layout.addWidget(label_mesure)
        layout.addWidget(combo_mesure)
        layout.addWidget(bouton_afficher)

        fenetre.setLayout(layout)
        fenetre.setWindowTitle("Choix de la mesure")

        # Centrer la fenêtre sur l'écran
        fenetre_geometry = fenetre.frameGeometry()
        centre_point = QDesktopWidget().availableGeometry().center()
        fenetre_geometry.moveCenter(centre_point)
        fenetre.move(fenetre_geometry.topLeft())

        fenetre.show()

        app.exec_()


if __name__ == "__main__":
    experimentation = Experimentation()
    experimentation.choisir_mesure()
