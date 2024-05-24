package ComparaisonTri.modele.algosTris;

import java.util.*;

/**
 * Classe implémentant l'algorithme de tri par sélection.
 * <i>Le tri de Shell, est une amélioration du tri par insertion @link{TriParInsertion}.
 * Il utilise une séquence d'intervalles prédéfinis pour effectuer plusieurs passes
 *  de tri par insertion sur des sous-séquences de l'ensemble de données.
 * À chaque passe, l'intervalle est réduit, ce qui permet de déplacer les éléments plus rapidement vers leur position finale.</i>
 *
 * @see TriImplementation
 * @author Christopher Bird, Quentin Rondeau, Matisse Senechal, Université de Caen Normandie, France
 */
public class TriDeShell extends TriImplementation {

    @Override
    public String getNomAlgo() {
        return "TriDeShell";
    }

    @Override
    public List<Integer> trier(List<Integer> tab) {
        List<Integer> tableauCopie = new ArrayList<>(tab);
        int taille = tableauCopie.size();

        // Mise à jour de l'état si nécessaire
        if (isListeChaqueInstantCreated()){
            resetState(tableauCopie);
        }

        // Calcul des intervalles
        for (int intervalle=taille/2; intervalle > 0; intervalle/=2) {

            // Parcours des sous-tableaux
            for (int posAct=intervalle; posAct < taille; posAct++) {
                int valAct = tableauCopie.get(posAct); // Valeur actuelle
                int posCmp = posAct; // Position de comparaison

                // Insertion de la valeur actuelle dans le sous-tableau trié
                while ((posCmp >= intervalle) && (tableauCopie.get(posCmp-intervalle) > valAct)) {
                    incrementerComparaisons();
                    tableauCopie.set(posCmp, tableauCopie.get(posCmp-intervalle));

                    // Mise à jour de l'état si nécessaire
                    if (isListeChaqueInstantCreated()){
                        resetState(tableauCopie, posCmp);
                    }

                    incrementerAssignations();
                    posCmp -= intervalle; // Déplacement vers la gauche
                }

                // Insertion de la valeur actuelle à la position correcte
                tableauCopie.set(posCmp, valAct);

                // Mise à jour de l'état si nécessaire
                if (isListeChaqueInstantCreated()){
                    resetState(tableauCopie, posCmp);
                }

                incrementerAssignations();
            }
        }

        return tableauCopie;
    }
}