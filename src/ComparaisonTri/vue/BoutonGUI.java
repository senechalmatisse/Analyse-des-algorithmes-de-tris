package ComparaisonTri.vue;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;

import ComparaisonTri.modele.algosTris.Tri;

/**
 * La classe BoutonGUI représente le panneau contenant les boutons d'interaction avec l'interface utilisateur.
 * Elle implémente l'interface ActionListener pour écouter les événements des boutons.
 * 
 * @see ActionListener, JPanel
 * @author Christopher Bird, Quentin Rondeau, Matisse Senechal, Université de Caen Normandie, France
 */
public class BoutonGUI extends JPanel implements ActionListener {

  /** Tableau des boutons d'interaction. */
  private JButton[] boutons;

  /** Indicateur de pause du tri en cours. */
  private volatile boolean pause;

  /** Vitesse du tri. */
  private int vitesse;

  /** Constantes pour les différentes vitesses de tri. */
  private final int VITESSE_LENT = 2000;
  private final int VITESSE_RAPIDE = 200;
  private final int VITESSE_TRES_RAPIDE = 20;

  /** Instance de la classe AffichageTableau pour la visualisation du tableau. */
  private AffichageTableau affichageTableau;

  /**
   * Constructeur de la classe BoutonGUI.
   *
   * @param affichageTableau Instance de la classe AffichageTableau pour la visualisation du tableau.
   */
	public BoutonGUI(AffichageTableau affichageTableau) {
    super();
    this.affichageTableau = affichageTableau;
    this.pause = false;
    this.vitesse = VITESSE_LENT;

    // Noms des boutons d'interaction
    String[] nomsBoutons = {"Resultat", "Ré-initialiser", "Lancement", "Vitesse Lente",
                            "Vitesse Rapide", "Vitesse Tres Rapide", "Pause / Reprendre",
                            "Lancement pas à pas", "Revenir en arrière"};

    boutons = new JButton[nomsBoutons.length];
    for (int i = 0; i < nomsBoutons.length; i++) {
      boutons[i] = new JButton(nomsBoutons[i]);
      boutons[i].addActionListener(this);
      add(boutons[i]);
    }

    affichageTableau.getTriSelectionne().trier(affichageTableau.getTableau());
	}

  @Override
  public void actionPerformed(ActionEvent testEvenement) {
    JButton source = (JButton) testEvenement.getSource();

    Tri triSelectionne = affichageTableau.getTriSelectionne();
    int compteur = affichageTableau.getCompteur();

    switch(source.getText()) {
        case "Resultat":
            actionResultat(triSelectionne);
            break;
        case "Ré-initialiser":
            actionReinitialisation();
            break;
        case "Vitesse Lente":
            actionModificationVitesse(1);
            break;
        case "Vitesse Rapide":
            actionModificationVitesse(2);
            break;
        case "Vitesse Tres Rapide":
            actionModificationVitesse(3);
            break;
        case "Pause / Reprendre":
            actionPauseReprendre();
            break;
        case "Lancement":
            actionLancement(triSelectionne);
            break;
        case "Lancement pas à pas":
            actionPasAPas(triSelectionne, compteur);
            break;
        case "Revenir en arrière":
            actionArriere(triSelectionne, compteur);
            break;
    }
  }

  /**
   * Applique l'action associée au bouton "Résultat".
   *
   * @param triSelectionne Le tri sélectionné.
   */
  private void actionResultat(Tri triSelectionne) {
    Map<Integer, List<Integer>> listeEtats = triSelectionne.getListeState();
    int nbEtats = listeEtats.size();

    affichageTableau.setCompteur(nbEtats-1);
    affichageTableau.setTableau(listeEtats.get(affichageTableau.getCompteur()));
    affichageTableau.paintTableau();
  }

  /**
   * Réinitialise le tableau à son état initial.
   */
  private void actionReinitialisation() {
    affichageTableau.setTableau(affichageTableau.getTableauInit());
    affichageTableau.setCompteur(0);
    affichageTableau.paintTableau();
  }

  /**
   * Modifie la vitesse du tri selon le choix de l'utilisateur.
   * 
   * @param choixVitesse La vitesse choisit.
   */
  private void actionModificationVitesse(int choixVitesse) {
    switch(choixVitesse) {
      case 1:
        vitesse = VITESSE_LENT;
        break;
      case 2:
        vitesse = VITESSE_RAPIDE;
        break;
      case 3:
        vitesse = VITESSE_TRES_RAPIDE;
        break;
    }
  }

  /**
   * Met en pause ou reprend le tri.
   */
  private void actionPauseReprendre() {
    pause = !pause;
  }

  /**
   * Lancement le tri.
   *
   * @param triSelectionne Le tri sélectionné.
   */
  private void actionLancement(Tri triSelectionne) {
    lancerTri(triSelectionne);
  }

  /**
   * Méthode pour lancer le tri en fonction de la vitesse sélectionnée.
   *
   * @param triSelectionne Le tri sélectionné.
   */
  private void lancerTri(Tri triSelectionne) {
    Map<Integer, List<Integer>> listeEtats = triSelectionne.getListeState();
    int nbEtats = listeEtats.size();
    pause = false;

    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        try {

          while (affichageTableau.getCompteur() < nbEtats-1) {

            if (!pause) {
              miseAJourTableau(listeEtats, affichageTableau.getCompteur(), 1);
              Thread.sleep(vitesse);
            }
          }
        } catch (Exception e) {
          System.out.println(e);
        }
      }
    });
    thread.start();
  }

  /**
   * Lance le tri pas à pas.
   *
   * @param triSelectionne Le tri sélectionné.
   * @param compteur       Le compteur actuel.
   */
  private void actionPasAPas(Tri triSelectionne, int compteur) {
    Map<Integer, List<Integer>> listeEtats = triSelectionne.getListeState();
    int nbEtats = listeEtats.size();

    if (compteur < nbEtats-1) {
      miseAJourTableau(listeEtats, compteur, 1);
    }
  }

  /**
   * Revient en arrière dans le tri pas à pas.
   *
   * @param triSelectionne Le tri sélectionné.
   * @param compteur       Le compteur actuel.
   */
  private void actionArriere(Tri triSelectionne, int compteur) {
    Map<Integer, List<Integer>> listeEtats = triSelectionne.getListeState();

    if (compteur > 0) {
      miseAJourTableau(listeEtats, compteur, -1);
    }
  }

  /**
   * Met à jour le tableau affiché en fonction du compteur et de l'incrément spécifié.
   *
   * @param listeEtats La liste des états du tri.
   * @param compteur   Le compteur actuel.
   * @param incr       L'incrément à ajouter au compteur.
   */
  private void miseAJourTableau(Map<Integer, List<Integer>> listeEtats, int compteur, int incr) {
    compteur = affichageTableau.getCompteur();

    affichageTableau.setCompteur(compteur + incr);
    affichageTableau.setTableau(listeEtats.get(compteur));
    affichageTableau.paintTableau();
  }
}