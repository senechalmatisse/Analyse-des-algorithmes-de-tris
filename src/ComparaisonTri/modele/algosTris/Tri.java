package ComparaisonTri.modele.algosTris;

import java.util.*;

/**
 * L'interface Tri définit la méthode de tri utilisée par différentes implémentations.
 *
 * @author Christopher Bird, Quentin Rondeau, Matisse Senechal, Université de Caen Normandie, France
 */
public interface Tri {

    /**
     * Renvoie le nom de l'algorithme.
     *
     * @return Le nom de l'lagorithme.
     */
    String getNomAlgo();

    /**
     * Renvoie le nombre de comparaisons de l'algorithme.
     *
     * @return Le nombre de comparaisons.
     */
    long getNombreComparaisons();

    /**
     * Renvoie le nombre d'échanges de l'algorithme.
     *
     * @return Le nombre d'échanges.
     */

    long getNombreAssignations();

    /**
     * Renvoie un tableau trié.
     *
     * @param tab Le tableau à trier.
     * @return Le tableau trié.
     */
    List<Integer> trier(List<Integer> tab);

    /**
     * Renvoie un dictionnaire qui à pour clé un instant et pour valeur la liste à cet instant  
     *
     * @return Un dictionnaire contenant la liste des états à chaque instant
     */
    Map<Integer, List<Integer>> getListeState();

    /**
     * Renvoie un dictionnaire qui à pour clé un instant et pour valeur la liste des valeurs changés à cet instant  
     *
     * @return Un dictionnaire contenant la liste des indices à mettre en rouge à chaque instant
     */
    Map<Integer, List<Integer>> getListeIndiceRougeState();

    /**
     * Renvoie un dictionnaire qui à pour clé un instant et pour valeur la liste de taille 2 contenant le nombre de comparaisons et d'assignations effectués jusqu'à cet instant  
     *
     * @return Un dictionnaire contenant la liste des comparaisons et assignations à chaque instant
     */
    Map<Integer, List<Long>> getListeComparaisonsAssignationsState();

    /**
     * Récupere les états effectués dans le tri "tri" et les ajoute aux états du tri actuel
     */
    void recupStates(Tri tri);

    /**
     * Active la récuperation des états à chaque instants pour les afficher graphiquement dans la vue
     */
    void activateStates();
}