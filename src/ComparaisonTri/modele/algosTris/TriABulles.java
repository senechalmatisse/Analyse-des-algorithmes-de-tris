package ComparaisonTri.modele.algosTris;

import java.util.*;

/**
 * Classe implémentant l'algorithme de tri à bulles.
 * <i>Le tri à bulles ou tri par propagation1 est un algorithme de tri.
 * Il consiste à comparer répétitivement les éléments consécutifs d'un tableau,
 * et à les permuter lorsqu'ils sont mal triés.</i>
 *
 * @see TriImplementation
 * @author Christopher Bird, Quentin Rondeau, Matisse Senechal, Université de Caen Normandie, France
 */
public class TriABulles extends TriImplementation {

    @Override
    public String getNomAlgo() {
        return "TriABulles";
    }

    @Override
    public List<Integer> trier(List<Integer> tableau) {
        List<Integer> tableauCopie = new ArrayList<>(tableau);

        // Réinitialisation de l'état si nécessaire
        if (isListeChaqueInstantCreated()){
            resetState(tableauCopie);
        }

        int taille = tableauCopie.size()-1;

        // Parcours du tableau en sens inverse
        for (int i=taille; i >= 1; i--) {
            boolean tableauTrie = true;

            for (int j=0; j < i; j++) {
                // Comparaison des éléments adjacents
                if (tableauCopie.get(j+1) < tableauCopie.get(j)) {
                    incrementerComparaisons();

                    echanger(tableauCopie, j, j+1);

                    // Marquage du tableau comme non trié
                    tableauTrie = false;
                }
            }

            // Si le tableau est trié, arrêter le tri
            if (tableauTrie) {
                break;
            }
        }

        return tableauCopie;
    }
}