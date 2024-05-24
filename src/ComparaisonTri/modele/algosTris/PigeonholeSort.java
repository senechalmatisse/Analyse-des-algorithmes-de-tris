package ComparaisonTri.modele.algosTris;

import java.util.*;

/**
 * Classe implémentant l'algorithme PigeonholeSort.
 * <i>Le tri PigeonholeSort est un algorithme de tri qui fonctionne sur des listes où le minimum et le maximum sont approximativement les mêmes</i>
 *
 * @see TriImplementation
 * @author Christopher Bird, Quentin Rondeau, Matisse Senechal, Université de Caen Normandie, France
 */

public class PigeonholeSort extends TriImplementation {

    @Override
    public String getNomAlgo() {
        return "PigeonholeSort";
    }

    @Override
    public List<Integer> trier(List<Integer> tableau) {
        List<Integer> tableauCopie = new ArrayList<>(tableau);

        // Réinitialisation de l'état si nécessaire
        if (isListeChaqueInstantCreated()) {
            resetState(tableauCopie);
        }

        // Recherche des valeurs minimale et maximale dans le tableau
        int min = tableauCopie.get(0);
        int max = tableauCopie.get(0);
        int range, i, j, index;

        for (i=0; i < tableauCopie.size(); i++){
            if (tableauCopie.get(i) > max) {
                incrementerComparaisons();
                max = tableauCopie.get(i);
            }

            if (tableauCopie.get(i) < min) {
                incrementerComparaisons();
                min = tableauCopie.get(i);
            }
        }

        // Calcul de la plage de valeurs dans le tableau
        range = max-min+1;
        int[] phole = new int[range];
        Arrays.fill(phole, 0);

        // Comptage des occurrences de chaque valeur dans le tableau
        for(i=0; i < tableauCopie.size(); i++) {
            phole[tableauCopie.get(i)-min]++;
        }

        index = 0;

        // Réorganisation des valeurs dans le tableau dans l'ordre croissant
        for(j=0; j < range; j++) {
            while(phole[j] > 0) {
                incrementerComparaisons();
                tableauCopie.set(index++,j+min);

                // Réinitialisation de l'état si nécessaire
                if (isListeChaqueInstantCreated()){
                    resetState(tableauCopie, index-1);
                }

                incrementerAssignations();
                phole[j]--;
            }
        }

        return tableauCopie;
    }
}