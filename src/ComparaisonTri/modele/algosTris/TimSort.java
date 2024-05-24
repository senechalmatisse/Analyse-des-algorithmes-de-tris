package ComparaisonTri.modele.algosTris;

import java.util.*;

/**
 * Classe implémentant l'algorithme Timsort.
 * <i>Timsort est un algorithme de tri hybride dérivé du tri fusion et
 * du tri par insertion, stable et conçu pour fonctionner de manière efficace sur des données réelles.
 * L'algorithme procède en cherchant des monotonies, c'est-à-dire des parties de l'entrée déjà correctement ordonnées, 
 * et peut de cette manière trier efficacement l'ensemble des données en procédant par fusions successives.
 * Pour des entrées de petites tailles, il revient à effectuer un tri fusion.</i>
 *
 * @see TriImplementation
 * @author Christopher Bird, Quentin Rondeau, Matisse Senechal, Université de Caen Normandie, France
 */
public class TimSort extends TriImplementation {
    private final int MIN_MERGE = 32; 

    @Override
    public String getNomAlgo() {
        return "TimSort";
    }

    private int minRunLength(int n) { 
        assert n >= 0; 
        int r = 0; 

        while (n >= MIN_MERGE) { 
            incrementerComparaisons();

            r |= (n & 1); 
            n >>= 1; 
        }

        return n + r; 
    } 

    /**
     * Coupe un tableau en deux et le fusionne en un tableau trié.
     *
     * @param tableau Le tableau principal à modifier.
     * @param l indice du premier élément de la sous liste à fusionner.
     * @param m indice du milieu de la sous liste à fusionner.
     * @param r indice du premier élément de la sous liste à fusionner.
     */
    private void merge(List<Integer> tableau, int l, int m, int r) { 
        int len1 = m - l + 1, len2 = r - m; 

        //on coupe le tableau en deux
        int[] left = new int[len1]; 
        int[] right = new int[len2]; 

        for (int x = 0; x < len1; x++) { 
            left[x] = tableau.get(l + x); 
        }

        for (int x = 0; x < len2; x++) { 
            right[x] = tableau.get(m + 1 + x); 
        } 

        int i = 0;
        int j = 0;
        int k = l;

        //on regroupe les tableaux avec un tri fusion
        while (i < len1 && j < len2) {
            incrementerComparaisons();

            if (left[i] <= right[j]) { 
                incrementerAssignations();

                tableau.set(k,left[i]);

                if (isListeChaqueInstantCreated()) {
                    resetState(tableau);
                }

                i++;
            } else { 
                incrementerAssignations();

                tableau.set(k,right[j]);

                if (isListeChaqueInstantCreated()) {
                    resetState(tableau);
                }

                j++;
            } 

            k++;
        }

        //copie des elements restants dans left
        while (i < len1) {
            incrementerComparaisons();
            incrementerAssignations();

            tableau.set(k,left[i]); 

            if (isListeChaqueInstantCreated()) {
                resetState(tableau);
            }

            k++; 
            i++; 
        } 

        //copie des elements restants dans right
        while (j < len2) {
            incrementerComparaisons();
            incrementerAssignations();

            tableau.set(k,right[j]);

            if (isListeChaqueInstantCreated()){
                resetState(tableau, k);
            }

            k++; 
            j++; 
        } 
    }

    @Override
    public List<Integer> trier(List<Integer> tableau) {
        List<Integer> tableauCopie = new ArrayList<>(tableau);

        if (isListeChaqueInstantCreated()){
            resetState(tableauCopie);
        }

        int n = tableauCopie.size();
        int minRun = minRunLength(MIN_MERGE); 
        Tri insertion = new TriParInsertion();

        //tri par insertion de sous liste du tableau
        for (int i = 0; i < n; i += minRun) { 
            List<Integer> tableau2 = insertion.trier(
                tableauCopie.subList(i, 
                Math.min((i + MIN_MERGE - 1), n)));

            for (int j=0;j<tableau2.size();j++){
                tableauCopie.set(i+j,tableau2.get(j));

                if (isListeChaqueInstantCreated()){
                    resetState(tableauCopie, i+j);
                }
            }
        }

        //fusion des sous listes en listes plus grandes (doublé à chaque itération) jusqu'à obtenir le tableau trié.
        for (int size=minRun; size < n; size=2*size) { 
            /* On prends le point de depart de la sous liste gauche,
               On fusionne tab[left..left+size-1] 
               et tab[left+size, left+2*size-1]
               Apres chaque fusion, on ajoute 2*size à left */ 

            for (int left = 0; left < n; left += 2 * size) { 
                incrementerComparaisons();

                //La fin de la sous liste gauche est le debut le la sous liste droite
                int mid = left + size - 1; 
                int right = Math.min((left + 2 * size - 1), (n - 1)); 

                // Fusion des sous listes
                if (mid < right) {
                    merge(tableauCopie, left, mid, right);
                }
            } 
        }     

        setNombreAssignations(getNombreAssignations() + insertion.getNombreAssignations());
        setNombreComparaisons(getNombreComparaisons() + insertion.getNombreComparaisons());

        return tableauCopie;
    }
}