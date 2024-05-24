package ComparaisonTri.modele.algosTris;

import java.util.*;

/**
 * Classe implémentant l'algorithme de tri par tas.
 * <i>Le tri par tas utilise une structure d'arbre binaire pour maintenir
 * un tas, puis effectue des opérations de tamisage pour trier le tableau.</i>
 *
 * @see TriImplementation
 * @author Christopher Bird, Quentin Rondeau, Matisse Senechal, Université de Caen Normandie, France
 */
public class TriParTas extends TriImplementation {

    @Override
    public String getNomAlgo() {
        return "TriParTas";
    }

    @Override

    public List<Integer> trier(List<Integer> tab) {
        List<Integer> tableauCopie = new ArrayList<>(tab);

        if (isListeChaqueInstantCreated()){
            resetState(tableauCopie);
        }

        int taille = tableauCopie.size();

        if(taille > 1) {
            // Construit le tas
            for (int i = taille/2; i >= 1; i--) {
                tamiser(tableauCopie, i, taille);
            }

            // Trie le tableau en extrayant les éléments du tas
            for (int i = taille; i >= 2; i--) {
                echanger(tableauCopie, i-1, 0);
                tamiser(tableauCopie, 1, i-1);
            }
        }

        return tableauCopie;
    }

    /**
     * Descend un élément dans le tas pour maintenir la propriété du tas.
     *
     * @param tas Le tableau représentant le tas.
     * @param indiceNoeud L'indice du nœud à tamiser.
     * @param taille La taille du tas.
     */
    private void tamiser(List<Integer> tas, int indiceNoeud, int taille) {
        int indiceCourant = indiceNoeud;
        int indiceFils = 2 * indiceCourant;

        while (indiceFils <= taille) {
            if (indiceFils < taille && tas.get(indiceFils-1) < tas.get(indiceFils)) {
                incrementerComparaisons();

                indiceFils++;
            }

            if (tas.get(indiceCourant-1) < tas.get(indiceFils-1)) {
                echanger(tas, indiceCourant-1, indiceFils-1);

                indiceCourant = indiceFils;
                indiceFils = 2 * indiceCourant;
            } else {
                indiceFils = taille + 1;
            }

            incrementerComparaisons();
        }
    }
}