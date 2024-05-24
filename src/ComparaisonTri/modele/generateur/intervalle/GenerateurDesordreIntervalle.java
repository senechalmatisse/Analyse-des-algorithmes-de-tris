package ComparaisonTri.modele.generateur.intervalle;

import java.util.*;

import ComparaisonTri.modele.generateur.GenerateurImpl;

/**
 * Cette classe abstraite permet de calculer les bornes d'un intervalle.
 * Toute ses sous-classes n'auront qu'un seul intervalle de désordre.
 *
 * @see GenerateurImpl
 * @author Christopher Bird, Quentin Rondeau, Matisse Senechal, Université de Caen Normandie, France
 */
public abstract class GenerateurDesordreIntervalle extends GenerateurImpl {

    public GenerateurDesordreIntervalle(int tailleTab, int desordre, String repartition) {
        super(tailleTab, desordre, repartition);
    }

    @Override
    protected List<Integer> calculDesordre() {
        int debutDesordre, finDesordre;

        switch (REPARTITION) {
            case "debut":
                debutDesordre = 0;
                finDesordre = NB_ELEMENTS_A_DESORDONNER - 1;
                break;
            case "milieu":
                debutDesordre = (TAILLE - NB_ELEMENTS_A_DESORDONNER) / 2;
                finDesordre = debutDesordre + NB_ELEMENTS_A_DESORDONNER - 1;
                break;
            case "fin":
                debutDesordre = TAILLE - NB_ELEMENTS_A_DESORDONNER;
                finDesordre = TAILLE - 1;
                break;
            default:
                throw new IllegalArgumentException("Repartition invalide : " + REPARTITION);
        }

        return new ArrayList<Integer>(Arrays.asList(debutDesordre, finDesordre));
    }
}