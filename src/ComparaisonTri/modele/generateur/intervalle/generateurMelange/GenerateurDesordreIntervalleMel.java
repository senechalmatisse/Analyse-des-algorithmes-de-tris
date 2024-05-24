package ComparaisonTri.modele.generateur.intervalle.generateurMelange;

import ComparaisonTri.modele.generateur.intervalle.GenerateurDesordreIntervalle;

/**
 * Dans cette classe le désordre est ici définit comme : <i>"le nombre de valeurs qui se trouve à la mauvaise position dans un tableau croissant."</i>
 *
 * @see GenerateurDesordreIntervalle
 * @author Christopher Bird, Quentin Rondeau, Matisse Senechal, Université de Caen Normandie, France
 */
public class GenerateurDesordreIntervalleMel extends GenerateurDesordreIntervalle {

    /**
     * Construit un tableau qui a pour taille : "tailleTab" avec un pourcentage de désordre : "desordre".
     * et une répartition : "repartition".
     *
     * @param tailleTab   La taille du tableau à générer.
     * @param desordre    Le pourcentage de désordre dans la répartition des nombres aléatoires.
     * @param repartition La répartition du désordre dans le tableau.
     */
    public GenerateurDesordreIntervalleMel(int tailleTab, int desordre, String repartition) {
        super(tailleTab, desordre, repartition);
    }

    @Override
    public String getNomGen() {
        return "DesordreIntervalleMel";
    }
}