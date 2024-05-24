package ComparaisonTri.modele.algosTris;

import java.util.*;

/**
 * Cette classe implémente l'algorithme de tri pair-impair.
 * <i>Le tri pair-impair consiste à effectuer des comparaisons et des échanges alternés entre les éléments
 * aux positions impaires et paires du tableau jusqu'à ce que le tableau soit trié.</i>
 *
 * @see TriImplementation
 * @author Christopher Bird, Quentin Rondeau, Matisse Senechal, Université de Caen Normandie, France
 */
public class TriPairImpair extends TriImplementation {

    @Override
    public String getNomAlgo() {
        return "TriPairImpair";
    }

    @Override
    public List<Integer> trier(List<Integer> tableau) {
        List<Integer> tableauCopie = new ArrayList<>(tableau);

        if (isListeChaqueInstantCreated()){
            resetState(tableauCopie);
        }

        int taille = tableauCopie.size() - 1;
        boolean permutation;

        do {
            permutation = false;

            permutation = comparerEtEchanger(tableauCopie, 1, taille, 2, permutation); // Comparaisons impaires-paires
            permutation = comparerEtEchanger(tableauCopie, 0, taille, 2, permutation);
        } while (permutation);

        return tableauCopie;
    }

    /**
     * Compare et échange des éléments dans un tableau.
     * <p>
     * Cette méthode parcourt le tableau en comparant les éléments aux positions spécifiées
     * et les échange si nécessaire. La variable {@code permutation} est mise à true si au moins
     * un échange a été effectué pendant le parcours.
     * </p>
     *
     * @param tableau     Le tableau d'entiers à modifier.
     * @param debut       L'indice du premier élément à comparer.
     * @param fin         L'indice du dernier élément à comparer.
     * @param incrementation   L'incrémentation entre les indices des éléments à comparer.
     * @param permutation Variable indiquant si des échanges ont été effectués.
     * @return {@code true} si des échanges ont été effectués, {@code false} sinon.
     */
    private boolean comparerEtEchanger(List<Integer> tableau, int debut, int fin, int incrementation, boolean permutation) {
        for (int i = debut; i < fin; i += incrementation) {
            if (tableau.get(i) > tableau.get(i + 1)) {
                incrementerComparaisons();

                echanger(tableau, i, i + 1);

                permutation = true;
            }
        }

        return permutation;
    }
}