import subprocess, sys

from PyQt5.QtWidgets import QApplication, QWidget, QPushButton, QVBoxLayout, QLabel, QDesktopWidget, QMessageBox

from ComparaisonTri.vue.ComparaisonAlgorithmes import ComparaisonAlgorithmes
from ComparaisonTri.vue.EcartType import EcartType

# Définition du chemin vers les fichiers .class
CLASS_PATH = '../build'

# Définition du chemin vers la bibliothèque json-simple
CHEMIN_JSON_SIMPLE = '../lib/json-simple-1.1.1.jar'

# Définition des chemins vers les fichiers JSON des données
CHEMIN_RESULTATS_MOYENS_JSON = 'ComparaisonTri/modele/donnees/resultatsMoyensTris.json'
CHEMIN_RESULTATS_JSON = 'ComparaisonTri/modele/donnees/resultatsTris.json'

class FenetreResultats(QWidget):
    """
    Classe pour la fenêtre des résultats affichant les détails de la comparaison des tris.
    Attributes:
        generateur (str): Le type de générateur.
        taille (str): La taille du tableau.
        pourcentageDesordre (str): Le pourcentage de désordre.
        repartitionDesordre (str): La répartition du désordre.
    """

    def __init__(self, generateur, taille, pourcentageDesordre, repartitionDesordre):
        """
        Initialise la fenêtre des résultats avec les données fournies.
        Args:
            generateur (str): Le type de générateur.
            taille (str): La taille du tableau.
            pourcentageDesordre (str): Le pourcentage de désordre.
            repartitionDesordre (str): La répartition du désordre.
        """
        super().__init__()

        self.setWindowTitle("Résultats")
        self.resize(400, 300)
        self.centrer()

        self.generateur = generateur
        self.taille = taille
        self.pourcentageDesordre = pourcentageDesordre
        self.repartitionDesordre = repartitionDesordre

        self.init_widgets(self.generateur, self.taille, self.pourcentageDesordre, self.repartitionDesordre)
        self.init_layout()

        # Exécuter le programme Java
        self.executer_programme_java()

    def init_widgets(self, text1, text2, text3, text4):
        """Initialise les widgets de la fenêtre."""
        self.etiquette_resultats = QLabel(
            f"Type de générateur : {text1}\n" +
            f"Taille du tableau : {text2}\n" +
            f"Pourcentage de désordre : {text3}\n"+
            f"Répartition du désordre : {text4}"
        )

        self.bouton_afficher_tri = QPushButton("Afficher le tri")
        self.bouton_comparer_tris = QPushButton("Comparaison des tris")
        self.bouton_ecart_type = QPushButton("Ecart-type")
        self.bouton_afficher_tri.clicked.connect(self.afficher_menu_affichage)
        self.bouton_comparer_tris.clicked.connect(self.afficher_menu_comparaison)
        self.bouton_ecart_type.clicked.connect(self.afficher_menu_ecart_type)

    def init_layout(self):
        """Initialise le layout de la fenêtre."""
        layout = QVBoxLayout()
        layout.addWidget(self.etiquette_resultats)
        layout.addWidget(self.bouton_afficher_tri)
        layout.addWidget(self.bouton_comparer_tris)
        layout.addWidget(self.bouton_ecart_type)
        self.setLayout(layout)

    def executer_programme_java(self):
        """Exécute le programme Java."""
        java_command = ["java", "-cp", CLASS_PATH + ":" + CHEMIN_JSON_SIMPLE, "Execution.AvecInterface.ExecProgramme"] + sys.argv[1:]
        subprocess.run(java_command)

    def centrer(self):
        """Centre la fenêtre principale sur l'écran."""
        self.move(QDesktopWidget().availableGeometry().center() - self.rect().center())

    def afficher_menu_comparaison(self):
        """Affiche une pop-up avec les options de comparaison."""
        choix = ["Comparaisons", "Temps d'exécution", "Assignations"]

        choix_msgbox = QMessageBox()
        choix_msgbox.setWindowTitle("Choix de la mesure")
        choix_msgbox.setText("Choisissez une mesure pour la comparaison :")

        for option in choix:
            choix_msgbox.addButton(option.capitalize(), QMessageBox.ActionRole)

        choix_msgbox.buttonClicked.connect(self.afficher_comparaison_algos)
        choix_msgbox.exec()
    
    def afficher_menu_ecart_type(self):
        """Affiche une pop-up avec les options d'écart-type."""
        choix = ["Comparaisons", "Temps d'exécution", "Assignations"]

        choix_msgbox = QMessageBox()
        choix_msgbox.setWindowTitle("Choix de la mesure")
        choix_msgbox.setText("Choisissez une mesure pour l'écart-type :")

        for option in choix:
            choix_msgbox.addButton(option.capitalize(), QMessageBox.ActionRole)

        choix_msgbox.buttonClicked.connect(self.afficher_ecart_type)
        choix_msgbox.exec()

    def afficher_menu_affichage(self):
        """
        Affiche une pop-up avec les options d'affichage.
        """
        choix = ["Introsort","PigeonholeSort","Quicksort","TriAPeigne","TriPairImpair","TriParInsertion","TimSort","TriDeShell","TriFusion","TriABulles","TriCocktail","TriParTas","SmoothSort","TriParSelection"]

        choix_msgbox = QMessageBox()
        choix_msgbox.setWindowTitle("Choix de l'algorithme de tri'")
        choix_msgbox.setText("Choisissez un algorithme de tri :")
        for option in choix:
            choix_msgbox.addButton(option, QMessageBox.ActionRole)

        # Connexion des boutons à la fonction de lancement de l'affichage
        choix_msgbox.buttonClicked.connect(self.afficher_affichage_algos)

        choix_msgbox.exec()

    def afficher_ecart_type(self, bouton):
        """Fonction appelée lorsque le bouton "Ecart-type" est cliqué."""
        choix_traduits = {
            "Comparaisons": "comparaisons",
            "Temps d'exécution": "tempsExecution",
            "Assignations": "assignations"
        }
        mesure = choix_traduits.get(bouton.text(), bouton.text())
        self.lancer_ecart_type(mesure)

    def afficher_comparaison_algos(self, bouton):
        """Affiche la classe ComparaisonAlgorithmes avec la mesure correspondante."""
        choix_traduits = {
            "Comparaisons": "comparaisons",
            "Temps d'exécution": "tempsExecution",
            "Assignations": "assignations"
        }
        mesure = choix_traduits.get(bouton.text(), bouton.text())
        self.lancer_comparaison(mesure)

    def afficher_affichage_algos(self, bouton):
        """
        Affiche la classe ComparaisonAlgorithmes avec la mesure correspondante.
        """
        algo = bouton.text()
        self.lancer_affichage(algo)

    def lancer_comparaison(self, mesure):
        """Lance le script de comparaison des algorithmes."""
        ComparaisonAlgorithmes(CHEMIN_RESULTATS_MOYENS_JSON, mesure).afficher_histogramme(mesure)

    def lancer_ecart_type(self, mesure):
        """Lance le script de l'écart-type des algorithmes."""
        EcartType(CHEMIN_RESULTATS_JSON, mesure).afficher_ecart_type()

    def lancer_affichage(self, algorithme):
        """
        Lance le script d'affichage des algorithmes.
        """
        java_command = ["java", "-cp", CLASS_PATH + ":" + CHEMIN_JSON_SIMPLE, "ComparaisonTri.vue.FenetreVisualisation", self.generateur, self.taille, self.pourcentageDesordre, self.repartitionDesordre, algorithme] + sys.argv[1:]
        subprocess.run(java_command)