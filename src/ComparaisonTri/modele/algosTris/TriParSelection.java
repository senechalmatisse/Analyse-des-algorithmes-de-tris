package ComparaisonTri.modele.algosTris;

import java.util.*;

/**
 * Classe implémentant l'algorithme de tri par sélection.
 * <i>Le principe du tri par sélection est d'aller chercher le plus petit élément du vecteur pour le mettre en premier,
 * puis de repartir du second élément et d'aller chercher le plus petit élément du vecteur pour le mettre en second, etc...</i>
 *
 * @see TriImplementation
 * @author Christopher Bird, Quentin Rondeau, Matisse Senechal, Université de Caen Normandie, France
 */
public class TriParSelection extends TriImplementation {

    @Override
    public String getNomAlgo() {
        return "TriParSelection";
    }

    @Override
    public List<Integer> trier(List<Integer> tableau) {
        List<Integer> tableauCopie = new ArrayList<>(tableau);

        if (isListeChaqueInstantCreated()) {
            resetState(tableauCopie);
        }

        int taille = tableauCopie.size();

        for (int i=0; i < taille-1; i++) {
            int indiceMin = i;

            for (int j=i+1; j < taille; j++) {
                if (tableauCopie.get(j) < tableauCopie.get(indiceMin)) {
                    incrementerComparaisons();

                    indiceMin = j;
                }
            }

            if (indiceMin != i) {
                incrementerComparaisons();

                echanger(tableauCopie, i, indiceMin);
            }
        }

        return tableauCopie;
    }
}