package ComparaisonTri.modele.generateur;

import java.util.*;

/**
 * L'interface définit les méthodes nécessaires pour la génération de tableau croissant contenant des valeurs désordonnées,
 * dont le nombre est définit par les paramètres rentrés.
 *
 * @author Christopher Bird, Quentin Rondeau, Matisse Senechal, Université de Caen Normandie, France
 */
public interface Generateur {

    /**
     * Récupère le tableau généré.
     *
     * @return Le tableau d'entiers généré.
     */
    List<Integer> getTab();

    /**
     * Renvoie la taille du tableau.
     *
     * @return La taille du tableau.
     */
    int getTaille();

    /**
     * Renvoie le pourcentage de désordre dans le tableau.
     *
     * @return Le pourcentage de désordre dans le tableau.
     */
    int getDesordre();

    /**
     * Renvoie la répartition du désordre dans le tableau.
     *
     * @return La répartition du désordre dans le tableau.
     */
    String getRepartition();
    
    /**
     * Renvoie le nom du générateur.
     *
     * @return Le nom du générateur.
     */
    String getNomGen();

    /**
     * Génère des valeurs ordonnées et désordonnées et remplit un tableau avec.
     */
    void generer();
}