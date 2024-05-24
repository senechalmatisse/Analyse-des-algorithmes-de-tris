package ComparaisonTri.modele.algosTris;

import java.util.*;

/**
 * Classe représentant l'algorithme de tri SmoothSort.
 * <i>C'est un algorithme de tri basé sur l'idée de tas Leonardo,
 * une structure de données arborescente définie par des nombres Leonardo.
 * L'objectif est de trier un tableau en utilisant une approche hybride combinant
 * la construction d'un tas Leonardo et des opérations de tri par insertion.</i>
 *
 * @see TriImplementation
 * @author Christopher Bird, Quentin Rondeau, Matisse Senechal, Université de Caen Normandie, France
 */
public class SmoothSort extends TriImplementation {

    @Override
    public String getNomAlgo() {
        return "SmoothSort";
    }

    @Override
    public List<Integer> trier(List<Integer> tableau) {
        List<Integer> tableauCopie = new ArrayList<>(tableau);

        if (isListeChaqueInstantCreated()){
            resetState(tableauCopie);
        }

        if(tableauCopie.size() > 1) {
            smoothSort(tableauCopie);
        }

        return tableauCopie;
    }

    /**
     * Ajuste un tas dans un tableau d'entiers entre les indices donnés.
     *
     * @param tableau Le tableau d'entiers.
     * @param debut   L'indice de début du tas.
     * @param fin     L'indice de fin du tas.
     */
    private void ajusterTas(List<Integer> tableau, int debut, int fin) {
        int indexParent = debut;
        int indexGauche = 0;
        int indexDroit = 0;

        calculerIndices(debut, fin, indexParent, indexGauche, indexDroit);
        descendreElement(tableau, fin, indexParent, indexGauche, indexDroit);
    }

    /**
     * Calcule les indices nécessaires pour ajuster un tas.
     *
     * @param debut       L'indice de début.
     * @param fin         L'indice de fin.
     * @param indexParent L'indice du parent.
     * @param indexGauche L'indice du fils gauche.
     * @param indexDroit  L'indice du fils droit.
     */
    private void calculerIndices(int debut, int fin, int indexParent, int indexGauche, int indexDroit) {
        while (indexDroit < fin - debut + 1) {
            incrementerComparaisons();

            // Utilisation d'un test bit à bit pour calculer les indices en fonction de l'indice du parent
            if ((indexDroit & 0xAAAAAAAA) != 0) {
                // Si l'indice du fils droit est pair, on ajuste l'indice du fils gauche et du parent
                indexGauche += indexParent;
                indexParent >>= 1;
            } else {
                // Sinon, on ajuste l'indice du parent et du fils gauche
                indexParent += indexGauche;
                indexGauche >>= 1;
            }

            indexDroit += 1;
        }
    }

    /**
     * Descend un élément dans le tas pour maintenir la propriété du tas.
     *
     * @param tableau     Le tableau d'entiers.
     * @param fin         L'indice de fin.
     * @param indexParent L'indice du parent.
     * @param indexGauche L'indice du fils gauche.
     * @param indexDroit  L'indice du fils droit.
     */
    private void descendreElement(List<Integer> tableau, int fin, int indexParent, int indexGauche, int indexDroit) {
        while (indexParent > 0) {
            incrementerComparaisons();

            // Décalage de l'indice du fils gauche
            indexGauche >>= 1;
            indexDroit = indexParent + indexGauche;

            // Tant que l'indice du fils droit est inférieur à la fin du sous-tableau
            while (indexDroit < fin) {
                incrementerComparaisons();

                // Si la valeur du fils droit est supérieure à la valeur du fils gauche
                if (tableau.get(indexDroit) > tableau.get(indexDroit - indexParent)) {
                    break; // On arrête la boucle
                }

                // Échange des éléments du tas
                echanger(tableau, indexDroit, indexDroit - indexParent);
                indexDroit += indexParent;
            }

            indexParent = indexGauche;
        }
    }

    /**
     * Convertit un tas en un tableau trié.
     *
     * @param tableau Le tableau d'entiers.
     */
    private void conversionTasVersTableau(List<Integer> tableau) {
        int longueur = tableau.size()-1;

        for (int i=0; i < longueur; i++) {
            int j = i+1;

            while (j > 0 && tableau.get(j) < tableau.get(j-1)) {
                incrementerComparaisons();
                echanger(tableau, j, j-1);

                j -= 1;
            }
        }
    }

    /**
     * Calcule les nombres Leonardo pour un indice donné.
     *
     * @param index L'indice pour lequel calculer les nombres Leonardo.
     * @return La valeur de Leonardo pour l'entier k.
     */
    private int leonardo(int index) {
        incrementerComparaisons();

        return (index < 2 ? 1 : (leonardo(index - 1) + leonardo(index - 2) + 1));
    }

    /**
     * Implémente l'algorithme SmoothSort pour trier un tableau d'entiers.
     *
     * @param tableau Le tableau d'entiers à trier.
     */
    private void smoothSort(List<Integer> tableau) {
        int dernierIndex = tableau.size()-1;
        int positionFinale = dernierIndex;
        int positionCourante = 0;

        // Tant qu'il reste des éléments à trier dans le tableau
        while (dernierIndex > 0) {
            incrementerComparaisons();

            // Si la position courante est un multiple de 4
            if ((positionCourante & 0x03) == 0) {
                // Ajustement du tas à la position courante
                ajusterTas(tableau, positionCourante, positionFinale);
            }

            // Si la longueur de la séquence de Leonardo à la position courante est égale au dernier index
            if (leonardo(positionCourante) == dernierIndex) {
                // Augmentation de la position courante
                positionCourante += 1;
            } else {
                // Sinon, réduction de la position courante et mise à jour de la position finale
                positionCourante -= 1;
                positionFinale -= leonardo(positionCourante);

                ajusterTas(tableau, positionCourante, positionFinale);

                positionFinale = positionCourante - 1;
                positionCourante += 1;
            }

           // Échange du premier élément avec le dernier
            echanger(tableau, 0, dernierIndex);
            dernierIndex -= 1;
        }

        // Conversion du tas en tableau trié
        conversionTasVersTableau(tableau);
    }
}