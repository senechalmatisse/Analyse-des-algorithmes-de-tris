import sys

from PyQt5.QtWidgets import QApplication, QWidget, QPushButton, QVBoxLayout, QMessageBox, QLabel, QLineEdit, QDesktopWidget, QComboBox
from PyQt5.QtCore import Qt

from ComparaisonTri.controleur.VerifParamsVue import VerifParamsVue
from ComparaisonTri.vue.FenetreResultats import FenetreResultats

class FenetrePrincipale(QWidget):
    """
    Classe pour la fenêtre principale de l'application.
    """

    def __init__(self):
        """
        Initialise la fenêtre principale avec des champs de formulaire pour entrer les paramètres de l'analyse.
        """
        super().__init__()
    
        self.setWindowTitle("Analyse des algorithmes de tri")
        self.resize(400, 300)
        self.centrer()
    
        self.initWidgets()
        self.initLayout()
    
    def initWidgets(self):
        """Initialise les widgets de la fenêtre."""
        self.etiquette_generateur = QLabel("Type de générateur:")
        self.combo_generateur = QComboBox()
        self.combo_generateur.addItems(["DesordreIntervalleDec", "DesordreIntervallesDec",
                                        "DesordreIntervalleMel", "DesordreIntervallesMel",
                                        "DesordreIntervalleAlt", "DesordreIntervallesAlt",])
        self.combo_generateur.currentTextChanged.connect(self.visibilite_champ_repartition)

        self.etiquette_taille = QLabel("Taille du tableau :")
        self.taille = QLineEdit("1000")

        self.etiquette_pourcentage = QLabel("Pourcentage de désordre :")
        self.pourcentage = QLineEdit("30")

        self.etiquette_repartition = QLabel("Répartition du désordre :")
        self.combo_repartition = QComboBox()
        self.combo_repartition.addItems(["Début", "Milieu", "Fin"])

        self.entrees = [self.etiquette_taille, self.taille, self.etiquette_pourcentage,
                        self.pourcentage, self.etiquette_repartition, self.combo_repartition]
    
        self.bouton = QPushButton("Afficher résultats")
        self.bouton.clicked.connect(self.afficher_resultats)
    
    def initLayout(self):
        """Initialise le layout de la fenêtre."""
        layout = QVBoxLayout()
        layout.addWidget(self.etiquette_generateur)
        layout.addWidget(self.combo_generateur)
        for widget in self.entrees:
            layout.addWidget(widget)
        layout.addWidget(self.bouton)
    
        self.setLayout(layout)

    def centrer(self):
        """Centre la fenêtre principale sur l'écran."""
        self.move(QDesktopWidget().availableGeometry().center() - self.rect().center())

    def visibilite_champ_repartition(self, texte):
        """Gère la visibilité du champ de formulaire pour la répartition du désordre."""
        visibilite = (texte != "DesordreIntervallesDec") and (texte != "DesordreIntervallesMel") and (texte != "DesordreIntervallesAlt")
        self.etiquette_repartition.setVisible(visibilite)
        self.combo_repartition.setVisible(visibilite)

    def afficher_resultats(self):
        """Affiche la fenêtre des résultats avec les paramètres entrés."""
        # Récupération des valeurs des champs en supprimant les espaces
        generateur = self.combo_generateur.currentText()
        taille = self.taille.text().replace(" ", "")
        pourcentage = self.pourcentage.text().replace(" ", "")
        repartition = self.combo_repartition.currentText().lower().replace("é", "e")

        # Vérification des paramètres
        if not VerifParamsVue.est_valide(taille, pourcentage):
            return

        # Sauvegarde des paramètres dans un fichier texte
        self.sauvegarde_parametres(generateur, taille, pourcentage, repartition)

        # Affichage de la fenêtre des résultats
        self.fenetre_resultats = FenetreResultats(generateur, taille, pourcentage, repartition)
        self.fenetre_resultats.show()

    def sauvegarde_parametres(self, generateur, taille, pourcentage, repartition):
        """Sauvegarde les paramètres dans un fichier texte."""
        # Liste contenant les paramètres à sauvegarder
        parametres = [generateur, taille, pourcentage, repartition]

        # Écrit les paramètres dans un fichier texte
        with open("Execution/Configuration/parametres.txt", "w") as file:
            for parametre in parametres:
                file.write(f"{parametre}\n")

    def closeEvent(self, event):
        """Gère l'événement de fermeture de la fenêtre principale."""
        # Ferme toutes les fenêtres
        QApplication.closeAllWindows()

if __name__ == "__main__":
    app = QApplication(sys.argv)
    fenetrePrincipale = FenetrePrincipale()
    fenetrePrincipale.show()
    sys.exit(app.exec_())