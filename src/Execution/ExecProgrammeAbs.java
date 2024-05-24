package Execution;

import ComparaisonTri.modele.generateur.*;
import ComparaisonTri.modele.algosTris.*;
import ComparaisonTri.modele.algosTris.TimSort;
import ComparaisonTri.modele.donnees.*;
import ComparaisonTri.controleur.*;

import java.util.*;

/**
 * Cette classe contient une méthode permettant de démarrer l'exécution du programme en vérifiant les paramètres d'entrée,
 * en créant le générateur de tableaux aléatoires, en calculant les résultats pour chaque algorithme de tri et
 * en exportant les résultats au format JSON.
 * 
 * @author Christopher Bird, Quentin Rondeau, Matisse Senechal, Université de Caen Normandie, France
 */
public abstract class ExecProgrammeAbs {
    /** Chemin du fichier de configuration des paramètres d'exécution. */
    private static final String CHEMIN_PARAM = "Execution/Configuration/parametres.txt";

    /** Liste contenant tous les algorithmes de tri à évaluer. */
    protected static final List<Tri> TOUS_LES_TRIS = Collections.unmodifiableList(Arrays.asList(
            new PigeonholeSort(),
            new Quicksort(),
            new Introsort(),
            new TriAPeigne(),
            new TriPairImpair(),
            new TriParInsertion(),
            new TimSort(),
            new TriDeShell(),
            new TriFusion(),
            new TriABulles(),
            new TriCocktail(),
            new TriParTas(),
            new SmoothSort(),
            new TriParSelection()
    ));

    /**
     * Méthode pour exécuter le programme.
     *
     * @param args Les arguments de la ligne de commande.
     */
    protected void executer(String[] args) {
        // Lecture des paramètres depuis le fichier de configuration
        String[] params = VerifParams.lireParametres(CHEMIN_PARAM);

        // Vérification des arguments d'entrée et mise à jour des paramètres si nécessaire
        if (args.length > 0 && !Objects.equals(params[0], args[0])) {
            params = args;
        }

        try {
            // Création du générateur de tableaux aléatoires
            Generateur generateur = VerifParams.creerGenerateur(params);

            // Calcul des résultats pour chaque algorithme de tri
            // et les exportent dans deux fichiers JSON
            ResultatMesure.mesureSauvegardeResultats(TOUS_LES_TRIS, generateur, false);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}