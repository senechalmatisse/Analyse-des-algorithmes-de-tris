package ComparaisonTri.modele.algosTris;

import java.util.*;

/**
 * Classe implémentant l'algorithme Quicksort.
 * <i>Le tri rapide utilise une stratégie de partitionnement pour diviser le tableau en sous-tableaux,
 * puis trie récursivement ces sous-tableaux.</i>
 *
 * @see TriImplementation
 * @author Christopher Bird, Quentin Rondeau, Matisse Senechal, Université de Caen Normandie, France
 */
public class Quicksort extends TriImplementation {

    @Override
    public String getNomAlgo() {
        return "Quicksort";
    }

    @Override
    public List<Integer> trier(List<Integer> tableau) {
        List<Integer> tableauCopie = new ArrayList<>(tableau);

        if (isListeChaqueInstantCreated()) {
            resetState(tableauCopie);
        }

        quicksort(tableauCopie);

        return tableauCopie;
    }

    /**
     * Trie le tableau en utilisant l'algorithme Quicksort.
     *
     * @param tableau Le tableau à trier.
     */
    private void quicksort(List<Integer> tableau) {
        // Création de deux piles pour stocker les indices de début et de fin des sous-tableaux
        Stack<Integer> pileDebuts = new Stack<>();
        Stack<Integer> pileFins = new Stack<>();

        // Initialisation de la pile avec les indices de début et de fin du tableau principal
        pileDebuts.push(0);
        pileFins.push(tableau.size()-1);

        while (!pileDebuts.isEmpty()) {
            // Récupération des indices de début et de fin du sous-tableau actuel
            int debut = pileDebuts.pop();
            int fin = pileFins.pop();

            // Vérification des indices pour déterminer s'il y a encore des éléments à trier
            if (debut < fin) {
                // Partitionnement du sous-tableau et récupération de l'indice du pivot
                int pivotIndex = partitionner(tableau, debut, fin);

                // Ajout des sous-tableaux de gauche et de droite dans les piles pour les trier
                if (pivotIndex-1 > debut) {
                    pileDebuts.push(debut);
                    pileFins.push(pivotIndex-1);
                }

                if (pivotIndex+1 < fin) {
                    pileDebuts.push(pivotIndex+1);
                    pileFins.push(fin);
                }
            }
        }
    }

    /**
     * Partitionne la sous-liste spécifiée du tableau autour d'un pivot.
     *
     * @param tableau Le tableau d'entiers à partitionner.
     * @param debut   L'indice de début de la sous-liste à partitionner.
     * @param fin     L'indice de fin de la sous-liste à partitionner.
     * @return L'indice du pivot après partitionnement.
     */
    private int partitionner(List<Integer> tableau, int debut, int fin) {
        // Sélection du dernier élément comme pivot
        int pivot = tableau.get(fin);
        // Initialisation de l'indice du pivot
        int i = debut-1;

        // Parcours du sous-tableau
        for (int j=debut; j < fin; j++) {
            incrementerComparaisons();

            // Si l'élément actuel est inférieur ou égal au pivot, on l'échange avec l'élément à l'indice i+1
            if (tableau.get(j) <= pivot) {
                i++;

                if (i != j) {
                    echanger(tableau, i, j);
                }
            }
        }

        // Échange du pivot avec l'élément à l'indice i+1
        if (i+1 != fin) {
            echanger(tableau, i+1, fin);
        }

        // Retourne l'indice du pivot après partitionnement
        return i+1;
    }
}