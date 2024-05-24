package ComparaisonTri.modele.algosTris;

import java.util.*;

/**
 * Cette classe implémente l'algorithme de tri à peigne.
 * <i>Le tri à peigne est une variante du tri à bulles qui améliore les performances
 * en échangeant des éléments avec un intervalle fixe plutôt qu'un à un.</i>
 *
 * @see TriImplementation
 * @author Christopher Bird, Quentin Rondeau, Matisse Senechal, Université de Caen Normandie, France
 */
public class TriAPeigne extends TriImplementation {

    @Override
    public String getNomAlgo() {
        return "TriAPeigne";
    }

    @Override
    public List<Integer> trier(List<Integer> tab) {
        List<Integer> tableauCopie = new ArrayList<>(tab);

        if (isListeChaqueInstantCreated()){
            resetState(tableauCopie);
        }

        if(tableauCopie.size() > 1) {
            int intervalle = tableauCopie.size();
            boolean echange;

            do {
                intervalle = ajusterIntervalle(intervalle);
                echange = faireUnPassage(tableauCopie, intervalle);
            } while (intervalle > 1 || echange);
        }

        return tableauCopie;
    }

    /**
     * Ajuste la valeur de l'intervalle en le réduisant d'environ 30% à chaque itération.
     * Si la valeur devient inférieure à 1, elle est fixée à 1.
     *
     * @param intervalle L'intervalle actuel.
     * @return Le nouvel intervalle ajusté.
     */
    private int ajusterIntervalle(int intervalle) {
        // Réduction de l'intervalle d'environ 30%
        intervalle = (int) (intervalle / 1.3);

        incrementerComparaisons();

        // Vérification si la nouvelle valeur de l'intervalle est inférieure à 1
        // Si oui, retourne 1, sinon retourne la nouvelle valeur de l'intervalle
        return (intervalle < 1) ? 1 : intervalle;
    }

    /**
     * Effectue un passage sur le tableau en comparant les éléments distants d'un intervalle.
     *
     * @param tab        La liste d'entiers à trier.
     * @param intervalle L'intervalle entre les éléments à comparer.
     * @return {@code true} si au moins un échange a été effectué, sinon {@code false}.
     */
    private boolean faireUnPassage(List<Integer> tab, int intervalle) {
        // Variable pour indiquer si des échanges ont été effectués
        boolean echange = false;
        int i = 0;

        do {
            // Comparaison des éléments distants de l'intervalle
            if (tab.get(i) > tab.get(i+intervalle)) {
                incrementerComparaisons();

                echanger(tab, i, i + intervalle);

                // Indiquer qu'un échange a été effectué
                echange = true;
            }
            i++;
        } while (i < tab.size() - intervalle);

        return echange;
    }
}