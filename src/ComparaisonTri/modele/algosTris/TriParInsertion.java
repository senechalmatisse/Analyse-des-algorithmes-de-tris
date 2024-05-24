package ComparaisonTri.modele.algosTris;

import java.util.*;

/**
 * Cette classe implémente l'algorithme de tri par insertion.
 * <i>Le tri par insertion consiste à insérer chaque élément à la bonne position
 * dans une partie déjà triée du tableau.</i>
 *
 * @see TriImplementation
 * @author Christopher Bird, Quentin Rondeau, Matisse Senechal, Université de Caen Normandie, France
 */
public class TriParInsertion extends TriImplementation {

    @Override
    public String getNomAlgo() {
        return "TriParInsertion";
    }

    @Override
    public List<Integer> trier(List<Integer> tab) {
        List<Integer> tableauCopie = new ArrayList<>(tab);

        if (isListeChaqueInstantCreated()){
            resetState(tableauCopie);
        }

        for (int position=1; position < tableauCopie.size(); position++) {
            int elementEnCours = tableauCopie.get(position);

            deplacerElementsPlusPetits(tableauCopie, elementEnCours, position);
        }

        return tableauCopie;
    }

    /**
     * Déplace les éléments plus petits que l'élément donné vers la gauche dans le tableau,
     * afin de maintenir l'ordre croissant.
     *
     * @param tab Le tableau d'entiers à trier.
     * @param element L'élément en cours de traitement qui doit être inséré à la bonne position.
     * @param position La position actuelle de l'élément en cours dans le tableau.
     */
    private void deplacerElementsPlusPetits(List<Integer> tab, int element, int position) {
        while ((position > 0) && (tab.get(position-1) > element)) {
            incrementerComparaisons();
            incrementerAssignations();

            // Déplace l'élément précédent vers la droite
            tab.set(position, tab.get(position-1));

            // Met à jour l'état si nécessaire
            if (isListeChaqueInstantCreated()){
                resetState(tab, position);
            }

            position -= 1;
        }

        // Insère l'élément à sa position correcte dans le tableau
        tab.set(position, element);

        // Met à jour l'état après l'insertion
        incrementerAssignations();
        resetState(tab, position);

        // Remet à jour l'état si nécessaire
        if (isListeChaqueInstantCreated()){
            resetState(tab, position);
        }
    }
}