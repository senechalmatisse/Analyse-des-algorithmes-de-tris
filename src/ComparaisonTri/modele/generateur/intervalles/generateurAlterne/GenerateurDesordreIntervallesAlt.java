package ComparaisonTri.modele.generateur.intervalles.generateurAlterne;

import ComparaisonTri.modele.generateur.intervalles.GenerateurDesordreIntervalles;

/**
 * <p>
 * Cette classe représente un générateur de tableau avec plusieurs intervalles de désordre.
 * Dans cette classe le désordre est ici définit comme :
 * <i>"le nombre de valeurs décroissantes dans un tableau croissant"</i>
 * Les valeurs désordonnées sont réparties dans des intervalles de longueurs variables.
 * </p>
 *
 * @see GenerateurDesordreIntervalles
 * @author Christopher Bird, Quentin Rondeau, Matisse Senechal, Université de Caen Normandie, France
 */
public class GenerateurDesordreIntervallesAlt extends GenerateurDesordreIntervalles {

    /**
     * Construit un tableau qui a pour taille : "tailleTab" avec un pourcentage de désordre : "desordre".
     * Comme nous possédons plusieurs intervalles la répartition n'est pas utilisée ici.
     *
     * @param tailleTab   La taille du tableau à générer.
     * @param desordre    Le niveau de désordre dans la répartition des nombres aléatoires.
     * @param repartition La répartition du désordre dans le tableau.
     */
    public GenerateurDesordreIntervallesAlt(int tailleTab, int desordre, String repartition) {
        super(tailleTab, desordre, repartition);
    }

    @Override
    public String getNomGen() {
        return "DesordreIntervallesAlt";
    }
}