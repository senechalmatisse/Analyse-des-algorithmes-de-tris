package ComparaisonTri.modele.generateur.intervalles;

import java.util.*;

import ComparaisonTri.modele.generateur.GenerateurImpl;

/**
 * Cette classe abstraite permet de calculer les bornes des intervalles.
 * Toute ses sous-classes auront plusieurs intervalles de désordre.
 *
 * @see GenerateurImpl
 * @author Christopher Bird, Quentin Rondeau, Matisse Senechal, Université de Caen Normandie, France
 */
public abstract class GenerateurDesordreIntervalles extends GenerateurImpl {

    public GenerateurDesordreIntervalles(int tailleTab, int desordre, String repartition) {
        super(tailleTab, desordre, repartition);
    }

    public GenerateurDesordreIntervalles(int tailleTab, int desordre) {
        super(tailleTab, desordre, null);
    }

    @Override
    protected List<Integer> calculDesordre() {
        List<Integer> longueurIntervallesDesordonnes = calculLongueurIntervallesDesordonnes();
        int nbIntervallesDesordonnes = longueurIntervallesDesordonnes.size();
        int longueurIntervallesOrdonnes = (TAILLE - NB_ELEMENTS_A_DESORDONNER) / nbIntervallesDesordonnes;

        List<Integer> intervalles = new ArrayList<>(nbIntervallesDesordonnes);

        int debutIntervalle = longueurIntervallesOrdonnes;

        for (Integer longueur : longueurIntervallesDesordonnes) {
            int finIntervalle = debutIntervalle + longueur;

            ajouterDebutFinIntervalle(intervalles, debutIntervalle, finIntervalle);

            debutIntervalle = finIntervalle + longueurIntervallesOrdonnes;
        }

        return intervalles;
    }

    /**
     * Calcule la longueur des intervalles de désordre pour la génération pseudo-aléatoire du tableau.
     *
     * @return Une liste d'entiers représentant la longueur des intervalles de désordre.
     */
    private List<Integer> calculLongueurIntervallesDesordonnes() {
        List<Integer> intervalles = new ArrayList<>(NB_ELEMENTS_A_DESORDONNER / 2);
        int nb = NB_ELEMENTS_A_DESORDONNER;

        while (nb > 0) {
            int longueurIntervalle = calculLongueurIntervalle(nb);
            intervalles.add(longueurIntervalle);

            // Calculer le nombre d'éléments restants
            nb -= longueurIntervalle;
        }

        return intervalles;
    }

    /**
     * Calcule la longueur d'un intervalle de désordre.
     *
     * @param nb Le nombre d'éléments restants à répartir.
     * @return La longueur de l'intervalle de désordre.
     */
    private int calculLongueurIntervalle(int nb) {
        return (nb != 3) ? genererNombreAleatoire(nb) : 3;
    }

    /**
     * Génère un nombre aléatoire dans un intervalle spécifié.
     *
     * @param max La valeur maximale que peut prendre le nombre aléatoire.
     * @return Un nombre aléatoire compris entre 2 et {@code max}.
     */
    private int genererNombreAleatoire(int max) {
        return (int) (Math.random() * (max - 3)) + 2;
    }

    /**
     * Ajoute le début et la fin d'un intervalle à une liste d'intervalles.
     *
     * @param intervalles     La liste d'intervalles à laquelle ajouter le début et la fin de l'intervalle.
     * @param debutIntervalle Le début de l'intervalle à ajouter.
     * @param finIntervalle   La fin de l'intervalle à ajouter.
     */
    private void ajouterDebutFinIntervalle(List<Integer> intervalles, int debutIntervalle, int finIntervalle) {
        intervalles.add(debutIntervalle);
        intervalles.add(finIntervalle-1);
    }
}