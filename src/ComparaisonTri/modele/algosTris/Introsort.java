package ComparaisonTri.modele.algosTris;

import java.util.*;

/**
 * Classe implémentant l'algorithme Introsort.
 * <i>
 * <p>Cet algorithme combine le tri par tas et le tri à bulles
 * pour optimiser les performances dans différents scénarios.
 * La profondeur limite est ajustée pour basculer entre les algorithmes
 * en fonction de la taille du tableau à trier.</p>
 * <p>La classe utilise les implémentations de {@link TriParTas} et {@link Quicksort}
 * pour effectuer le tri par tas et le tri à bulles, respectivement.</p>
 * </i>
 * @see TriImplementation
 * @author Christopher Bird, Quentin Rondeau, Matisse Senechal, Université de Caen Normandie, France
 */
public class Introsort extends TriImplementation {

    @Override
    public String getNomAlgo() {
        return "Introsort";
    }

    @Override
    public List<Integer> trier(List<Integer> tableau) {
        List<Integer> tableauCopie = new ArrayList<>(tableau);

        if (isListeChaqueInstantCreated()){
            resetState(tableau);
        }

        if(tableauCopie.size() > 1) {
            int profondeurLimite = 2 * (int) Math.floor(Math.log(tableau.size()));
            introsort(tableauCopie, 0, tableau.size()-1, profondeurLimite);
        }

        return tableauCopie;
    }

    /**
     * Implémente l'algorithme Introsort.
     *
     * @param tableau          Le tableau d'entiers à trier.
     * @param min              L'indice de début de la plage à trier.
     * @param max              L'indice de fin de la plage à trier.
     * @param profondeurLimite La profondeur limite pour basculer vers un autre algorithme de tri.
     */
    private void introsort(List<Integer> tableau, int min, int max, int profondeurLimite) {
        // Vérifie si la partie du tableau à trier est non vide
        if (min < max) {
            incrementerComparaisons();

            // Vérifie si la profondeur limite est atteinte
            if (profondeurLimite == 0) {
                incrementerComparaisons();

                // Si oui, utilise le tri par tas pour trier la partie du tableau
                TriParTas triParTas = new TriParTas();
                List<Integer> tableau2 = triParTas.trier(tableau.subList(min, max + 1));

                // Remplace la partie triée du tableau par les éléments triés
                for (int j=0; j < tableau2.size(); j++) {
                    tableau.set(min+j,tableau2.get(j));

                    // Met à jour l'état si nécessaire
                    if (isListeChaqueInstantCreated()) {
                        resetState(tableau, min+j);
                    }
                }

                // Met à jour le nombre d'assignations et de comparaisons
                setNombreAssignations(getNombreAssignations() + triParTas.getNombreAssignations());
                setNombreComparaisons(getNombreComparaisons() + triParTas.getNombreComparaisons());
            } else {
                // Sinon, partitionne le tableau autour d'un pivot et continue la récursion
                incrementerComparaisons();

                int pivotIndex = partitionner(tableau, min, max);

                introsort(tableau, min, pivotIndex, profondeurLimite-1);
                introsort(tableau, pivotIndex+1, max, profondeurLimite-1);
            }
        }
    }

    /**
     * Partitionne la sous-liste spécifiée du tableau autour d'un pivot.
     *
     * @param tableau Le tableau d'entiers à partitionner.
     * @param min     L'indice de début de la sous-liste à partitionner.
     * @param max     L'indice de fin de la sous-liste à partitionner.
     * @return L'indice du pivot après partitionnement.
     */
    private int partitionner(List<Integer> tableau, int min, int max) {
        // Choix du pivot au milieu de la partie du tableau à partitionner
        int pivot = tableau.get(min + (max - min) / 2);
        int curMin = min - 1;
        int curMax = max + 1;

        while (true) {
            // Déplacement vers la droite jusqu'à trouver un élément plus grand ou égal au pivot
            do {
                curMin++;
                incrementerComparaisons();
            } while (tableau.get(curMin) < pivot);

            // Déplacement vers la gauche jusqu'à trouver un élément plus petit ou égal au pivot
            do {
                curMax--;
                incrementerComparaisons();
            } while (tableau.get(curMax) > pivot);

            // Si les indices ne se sont pas croisés, échange les éléments
            if (curMin < curMax) {
                incrementerComparaisons();
                echanger(tableau, curMin, curMax);
            } else {
                // Si les indices se sont croisés, la partition est terminée
                return curMax;
            }
        }
    }

    /**
     * Trie une sous-liste spécifiée du tableau en utilisant l'algorithme du tri par tas.
     *
     * @param tableau Le tableau d'entiers à trier.
     * @param min     L'indice de début de la sous-liste à trier.
     * @param max     L'indice de fin de la sous-liste à trier.
     */
    protected void triParTas(List<Integer> tableau, int min, int max) {
        construireTasMax(tableau, min, max);

        for (int i=max; i > min; i--) {
            echanger(tableau, min, i);
            entasserMax(tableau, min, i-1, min);
        }
    }

    /**
     * Construit un tas maximal à partir d'une sous-liste spécifiée du tableau.
     *
     * @param tableau Le tableau d'entiers à traiter.
     * @param min     L'indice de début de la sous-liste à traiter.
     * @param max     L'indice de fin de la sous-liste à traiter.
     */

    private void construireTasMax(List<Integer> tableau, int min, int max) {
        for (int i=max/2; i >= min; i--) {
            entasserMax(tableau, i, max, min);
        }
    }

    /**
     * Réorganise les éléments dans le tas pour maintenir la propriété de tas max.
     *
     * @param tableau   Le tableau d'entiers à traiter.
     * @param i         L'indice de départ de l'opération d'entassement.
     * @param tailleTas La taille du tas.
     * @param min       L'indice minimal de la sous-liste.
     */
    private void entasserMax(List<Integer> tableau, int i, int tailleTas, int min) {
        // Calcul des indices des fils gauche et droit dans le tableau
        int filsGauche = 2 * i + 1;
        int filsDroit = 2 * i + 2;
        // Initialisation de l'indice du plus grand élément au nœud actuel
        int plusGrand = i;

        // Vérification et mise à jour de l'indice du plus grand élément parmi le nœud actuel et son fils gauche
        if (filsGauche <= tailleTas && tableau.get(filsGauche) > tableau.get(plusGrand)) {
            plusGrand = filsGauche;
            incrementerComparaisons();
        }

        // Vérification et mise à jour de l'indice du plus grand élément parmi le nœud actuel et son fils droit
        if (filsDroit <= tailleTas && tableau.get(filsDroit) > tableau.get(plusGrand)) {
            plusGrand = filsDroit;
            incrementerComparaisons();
        }

        // Si le plus grand élément n'est pas le nœud actuel, échanger les éléments et récursion
        if (plusGrand != i) {
            incrementerComparaisons();
            echanger(tableau, i, plusGrand);
            entasserMax(tableau, plusGrand, tailleTas, min);
        }
    }
}