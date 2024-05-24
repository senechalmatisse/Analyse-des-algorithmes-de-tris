package ComparaisonTri.modele.algosTris;

import java.util.*;

/**
 * Cette classe implémente l'algorithme de tri cocktail (shaker sort).
 * <i>Le tri cocktail est une variante du tri à bulles qui effectue des passages de gauche à droite
 * et de droite à gauche alternativement pour déplacer les éléments plus petits
 * ou plus grands vers leur position finale.</i>
 *
 * @see TriImplementation
 * @author Christopher Bird, Quentin Rondeau, Matisse Senechal, Université de Caen Normandie, France
 */
public class TriCocktail extends TriImplementation {

    @Override
    public String getNomAlgo() {
        return "TriCocktail";
    }

    @Override
    public List<Integer> trier(List<Integer> tableau) {
        List<Integer> tableauCopie = new ArrayList<>(tableau);

        // Réinitialisation de l'état si nécessaire
        if (isListeChaqueInstantCreated()){
            resetState(tableauCopie);
        }

        int taille = tableauCopie.size();

        if(taille > 1) {
            boolean echange = true; // Variable pour suivre si un échange a eu lieu
            int debut = 0;  // Indice de début du sous-tableau
            int fin = taille-2; // Indice de fin du sous-tableau

            // Tant qu'il y a des échanges
            while (echange) {
                // Passage de gauche à droite
                echange = passageGaucheDroite(tableauCopie, debut, fin, false);

                // Si aucun échange n'a été effectué dans le dernier passage, sortir de la boucle
                if (!echange) {
                    break;
                }

                // Réduction du sous-tableau
                fin--;

                // Passage de droite à gauche
                echange = passageDroiteGauche(tableauCopie, fin, debut, false);

                // Réduction du sous-tableau
                debut++;
            }
        }

        return tableauCopie;
    }

    /**
     * Effectue un passage de gauche à droite dans le tableau et échange les éléments adjacents si nécessaire.
     *
     * @param tableau Le tableau d'entiers à trier.
     * @param debut L'indice de début du passage.
     * @param fin L'indice de fin du passage.
     * @param echange Un indicateur indiquant s'il y a eu des échanges pendant le passage précédent.
     * @return {@code true} s'il y a eu des échanges pendant ce passage, sinon {@code false}.
     */
    private boolean passageGaucheDroite(List<Integer> tableau, int debut, int fin, boolean echange) {
        for (int indiceGauche=debut; indiceGauche <= fin; indiceGauche++) {
            if (tableau.get(indiceGauche) > tableau.get(indiceGauche+1)) {
                incrementerComparaisons();

                echanger(tableau, indiceGauche, indiceGauche + 1);

                echange = true; // Un échange a eu lieu pendant ce passage
            }
        }

        return echange;
    }

    /**
     * Effectue un passage de droite à gauche dans le tableau et échange les éléments adjacents si nécessaire.
     *
     * @param tableau Le tableau d'entiers à trier.
     * @param fin L'indice de fin du passage.
     * @param debut L'indice de début du passage.
     * @param echange Un indicateur indiquant s'il y a eu des échanges pendant le passage précédent.
     * @return {@code true} s'il y a eu des échanges pendant ce passage, sinon {@code false}.
     */
    private boolean passageDroiteGauche(List<Integer> tableau, int fin, int debut, boolean echange) {
        for (int indiceDroite=fin; indiceDroite >= debut; indiceDroite--) {
            if (tableau.get(indiceDroite) > tableau.get(indiceDroite+1)) {
                incrementerComparaisons();

                echanger(tableau, indiceDroite, indiceDroite + 1);

                echange = true; // Un échange a eu lieu pendant ce passage
            }
        }

        return echange;
    }
}