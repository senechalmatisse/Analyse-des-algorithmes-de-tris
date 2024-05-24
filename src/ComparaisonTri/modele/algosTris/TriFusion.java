package ComparaisonTri.modele.algosTris;

import java.util.*;

/**
 * Classe implémentant l'algorithme de tri fusion.
 * <i>Le tri fusion divise récursivement le tableau en deux moitiés,
 * trie chaque moitié séparément, puis fusionne les deux moitiés triées.</i>
 *
 * @see TriImplementation
 * @author Christopher Bird, Quentin Rondeau, Matisse Senechal, Université de Caen Normandie, France
 */
public class TriFusion extends TriImplementation {

    @Override
    public String getNomAlgo() {
        return "TriFusion";
    }

    @Override
    public List<Integer> trier(List<Integer> tab) {
        List<Integer> tableauCopie = new ArrayList<>(tab);

        // Mise à jour de l'état si nécessaire
        if (isListeChaqueInstantCreated()){
            resetState(tableauCopie);
        }

        int taille = tableauCopie.size();

        if (taille <= 1) {
            return tableauCopie; // Le tableau est déjà trié s'il a 0 ou 1 élément
        }

        int milieu = taille / 2;

        // Divise le tableau en deux moitiés
        List<Integer> partieGauche = new ArrayList<>(tableauCopie.subList(0, milieu));
        List<Integer> partieDroite = new ArrayList<>(tableauCopie.subList(milieu, taille));

        // Trie récursivement les deux moitiés
        partieGauche = trier(partieGauche);
        partieDroite = trier(partieDroite);

        // Fusionne les deux moitiés triées
        fusionner(tableauCopie, partieGauche, partieDroite);

        return tableauCopie;
    }

    /**
     * Fusionne deux sous-tableaux triés en un tableau trié.
     *
     * @param tableau Le tableau principal à modifier.
     * @param partieGauche La première moitié triée.
     * @param partieDroite La deuxième moitié triée.
     */
    private void fusionner(List<Integer> tableau, List<Integer> partieGauche, List<Integer> partieDroite) {
        int indiceGauche = 0, indiceDroite = 0, indiceTableau = 0;

        // Parcourt les deux moitiés jusqu'à ce que l'une d'elles soit entièrement parcourue
        while (indiceGauche < partieGauche.size() && indiceDroite < partieDroite.size()) {
            incrementerComparaisons();

            // Compare les éléments actuels des deux moitiés et les place dans le tableau final
            if (partieGauche.get(indiceGauche) <= partieDroite.get(indiceDroite)) {
                tableau.set(indiceTableau++, partieGauche.get(indiceGauche++));
            } else {
                tableau.set(indiceTableau++, partieDroite.get(indiceDroite++));
            }

            // Mise à jour de l'état si nécessaire
            if (isListeChaqueInstantCreated()) {
                resetState(tableau, indiceTableau-1);
            }

            incrementerAssignations();
        }

        // Copie les éléments restants de partieGauche (s'il y en a)
        while (indiceGauche < partieGauche.size()) {
            tableau.set(indiceTableau++, partieGauche.get(indiceGauche++));

            // Mise à jour de l'état si nécessaire
            if (isListeChaqueInstantCreated()) {
                resetState(tableau, indiceTableau-1);
            }

            incrementerAssignations();
        }

        // Copie les éléments restants de partieDroite (s'il y en a)
        while (indiceDroite < partieDroite.size()) {
            tableau.set(indiceTableau++, partieDroite.get(indiceDroite++));

            // Mise à jour de l'état si nécessaire
            if (isListeChaqueInstantCreated()) {
                resetState(tableau, indiceTableau-1);
            }

            incrementerAssignations();
        }
    }
}