package ComparaisonTri.vue;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.util.List;

import ComparaisonTri.modele.algosTris.*;

/**
 * Cette classe représente un panneau graphique affichant un tableau avec différentes représentations visuelles.
 * Elle est utilisée pour visualiser le processus de tri d'un tableau.
 * 
 * @see JPanel
 * @author Christopher Bird, Quentin Rondeau, Matisse Senechal, Université de Caen Normandie, France
 */
public class AffichageTableau extends JPanel {

    /** 
     * Le tableau original.
     * Le tableau initial avant le tri.
     * Le tableau utilisé pour représenter graphiquement le tableau trié.
     */
    private List<Integer> tableau, tableauInit, tableauIndex;

    /** Le tri sélectionné pour visualisation. */
	private Tri triSelectionne;

    /**
     * L'écart utilisé pour afficher les éléments du tableau.
     * Le compteur pour suivre l'état actuel du tri.
     */
	private Integer ecart, compteur;

    /**
     * Constructeur de la classe AffichageTableau.
     *
     * @param tableau        Le tableau à afficher.
     * @param triSelectionne Le tri sélectionné.
     */
    public AffichageTableau(List<Integer> tableau, Tri triSelectionne) {
        super();
        this.tableau = tableau;
        this.tableauInit = tableau;
        this.triSelectionne = triSelectionne;

	    this.triSelectionne.activateStates();
        paintTableau();
        determinerEcart();

	    this.compteur=0;
    }
    
    /**
     * Obtient le compteur pour suivre l'état actuel du tri.
     * 
     * @return Le compteur pour suivre l'état actuel du tri.
     */
    public int getCompteur() {
        return compteur;
    }
    
    /**
     * Obtient le tri sélectionné.
     * 
     * @return Le tri sélectionné.
     */
    public Tri getTriSelectionne() {
        return triSelectionne;
    }
    
    /**
     * Obtient le tableau affiché.
     * 
     * @return Le tableau affiché.
     */
    public List<Integer> getTableau() {
        return tableau;
    }

    /**
     * Obtient le tableau initial.
     * 
     * @return Le tableau initial.
     */
    public List<Integer> getTableauInit() {
        return tableauInit;
    }

    /**
     * Définit le tableau affiché.
     *
     * @param tableau Le nouveau tableau à afficher.
     */
    public void setTableau(List<Integer> tableau) {
        this.tableau = tableau;
    }

    /**
     * Définit le tableauIndex avec une nouvelle liste d'index.
     *
     * @param tableau La nouvelle liste d'index.
     */
    public void setTableauIndex(List<Integer> tableau) {
        this.tableauIndex = tableau;
    }

    /**
     * Définit le compteur avec une nouvelle valeur.
     *
     * @param nouvelleValeur La nouvelle valeur du compteur.
     */
    public void setCompteur(int nouvelleValeur) {
        this.compteur = nouvelleValeur;
    }

    /**
     * Redessine le panneau d'affichage du tableau en mettant à jour les données.
     */
	protected void paintTableau() {
		this.tableauIndex = initialiseListeIndex();
		this.repaint();
	}

    /**
     * Obtient une liste d'index à partir du tableau.
     *
     * @return Une liste d'index.
     */
    private List<Integer> initialiseListeIndex() {
        List<Integer> res = new ArrayList<Integer>(tableau);
        List<Integer> listeIndexMin = new ArrayList<Integer>();
        Integer minMax = Integer.MIN_VALUE;
        Integer valeurIndex = 0;

        while (true) {
            Integer min = trouverMinimum(minMax);

            if (min == null || min == Integer.MAX_VALUE) {
                break;
            }

            miseAJourIndex(res, listeIndexMin, min, valeurIndex);
            valeurIndex++;
            listeIndexMin.clear();
            minMax = min;
        }

        return res;
    }

    /**
     * Trouve le minimum dans le tableau.
     *
     * @param minMax La valeur minimale actuelle.
     * @return Le minimum trouvé.
     */
    private Integer trouverMinimum(Integer minMax) {
        Integer min = Integer.MAX_VALUE;

        for (Integer val : tableau) {
            if ((val < min) && (val > minMax)) {
                min = val;
            }
        }

        return min == Integer.MAX_VALUE ? null : min;
    }

    /**
     * Met à jour les indexes dans la liste.
     *
     * @param res           La liste résultante.
     * @param listeIndexMin La liste des indexes minimums.
     * @param min           Le minimum actuel.
     * @param valeurIndex   La valeur de l'index.
     */
    private void miseAJourIndex(List<Integer> res, List<Integer> listeIndexMin, Integer min, Integer valeurIndex) {
        for (int i = 0; i < tableau.size(); i++) {
            if (tableau.get(i).equals(min)) {
                res.set(i, valeurIndex);
                listeIndexMin.add(i);
            }
        }
    }

    /**
     * Détermine la valeur de l'attribut ecart en fonction de la taille du tableauIndex.
     */
    private void determinerEcart() {
        if (tableauIndex.size() > 900) {
            ecart = 0;
        } else if (tableauIndex.size() > 600) {
            ecart = 1;
        } else if (tableauIndex.size() > 300) {
            ecart = 2;
        } else {
            ecart = 5;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension fram = this.getSize();
        int posX = 20;
        int posY = fram.height - 50;
        int LongueurXMax = fram.width - 20;
        int LongueurYMax = (int) (fram.height * ((float) 80 / (float) 100));

        dessinerRectangles(g, posX, posY, LongueurXMax, LongueurYMax);

        List<Long> informations = triSelectionne.getListeComparaisonsAssignationsState().get(compteur); 
        long nombreComparaisons = informations.get(0);
        long nombreAssignations = informations.get(1);

        afficherInformations(g, nombreComparaisons, nombreAssignations);
      
    }

    /**
     * Dessine des rectangles représentant les éléments du tableau avec différentes couleurs selon l'état actuel.
     * 
     * @param g Le contexte graphique où dessiner les rectangles.
     * @param posX La position en X du coin supérieur gauche du premier rectangle.
     * @param posY La position en Y du coin supérieur gauche du premier rectangle.
     * @param LongueurXMax La longueur maximale des rectangles en X.
     * @param LongueurYMax La longueur maximale des rectangles en Y.
     */
    private void dessinerRectangles(Graphics g, int posX, int posY, int LongueurXMax, int LongueurYMax) {
        for (int i = 0; i < tableauIndex.size(); i++) {
            int LongueurYElem = calculerLongueurYElem(LongueurYMax, i);
            Color couleur = determinerCouleurRectangle(i);

            g.setColor(couleur);
            dessinerRectangle(g, posX, posY, LongueurXMax, LongueurYElem);

            posX += LongueurXMax / tableauIndex.size();
        }

        g.setColor(Color.black);
    }

    /**
     * Affiche les informations sur le nombre de comparaisons et d'assignations.
     *
     * @param g Le contexte graphique où afficher les informations.
     * @param nombreComparaisons Le nombre de comparaisons jusqu'à cette instant.
     * @param nombreAssignations Le nombre d'assignations jusqu'à cette instant.
     */
    private void afficherInformations(Graphics g, long nombreComparaisons, long nombreAssignations) {
        g.drawString("Comparaisons jusqu'à cette instant : " + nombreComparaisons, 20, 20);
        g.drawString("Assignations jusqu'à cette instant : " + nombreAssignations, 20, 40);
    }

    /**
     * Calcule la longueur en Y d'un élément du tableau en fonction de sa valeur et de la taille maximale en Y.
     * 
     * @param LongueurYMax La longueur maximale des rectangles en Y.
     * @param i L'indice de l'élément dans le tableau.
     * @return La longueur en Y de l'élément.
     */
    private int calculerLongueurYElem(int LongueurYMax, int i) {
        int valeurElement = tableauIndex.get(i);
        // Calcul du ratio de la valeur de l'élément par rapport à la taille totale du tableau
        float ratioValeur = (float) valeurElement / (float) tableauIndex.size();
        // Calcul de la longueur verticale de l'élément en utilisant le ratio et la longueur maximale en Y
        int longueurYElem = (int) (LongueurYMax * ratioValeur) + 1;

        return longueurYElem;
    }

    /**
     * Détermine la couleur à utiliser pour dessiner un rectangle en fonction de l'état actuel.
     * 
     * @param i L'indice de l'élément dans le tableau.
     * @return La couleur du rectangle.
     */
    private Color determinerCouleurRectangle(int i) {
        Color couleur = Color.black;
        Map<Integer, List<Integer>> indiceRougeStates = triSelectionne.getListeIndiceRougeState();
        int nbEtats = triSelectionne.getListeState().size();

        // Vérifier si l'état actuel contient des indices rouges
        boolean indiceRougePresent = indiceRougeStates.containsKey(compteur) && (compteur != (nbEtats-1));

        // Vérifie s'il y a des indices rouges
        if (indiceRougePresent) {
            List<Integer> indiceRougeState = indiceRougeStates.get(compteur);

            // Vérifie si l'indice courant correspond à un indice rouge
            if (indiceRougeState.get(0) == i) {
                couleur = Color.red;
            }

            // Si la liste d'indices rouges a une taille de 2 
            // et que l'indice courant correspond au deuxième indice rouge
            if (indiceRougeState.size() == 2 && indiceRougeState.get(1) == i) {
                couleur = Color.blue;
            }
        }

        return couleur;
    }    

    /**
     * Dessine un rectangle sur le composant graphique.
     *
     * @param g Le contexte graphique où dessiner le rectangle.
     * @param posX La position en X du coin supérieur gauche du rectangle.
     * @param posY La position en Y du coin supérieur gauche du rectangle.
     * @param LongueurXMax La longueur maximale du rectangle en X.
     * @param LongueurYElem La longueur du rectangle en Y.
     */
    private void dessinerRectangle(Graphics g, int posX, int posY, int LongueurXMax, int LongueurYElem) {
        g.fillRect(posX, posY-LongueurYElem, LongueurXMax/tableauIndex.size()-ecart, LongueurYElem);
    }
}