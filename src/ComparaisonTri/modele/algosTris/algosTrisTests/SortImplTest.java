package ComparaisonTri.modele.algosTris.algosTrisTests;

import java.util.*;

import org.junit.Test;
import static org.junit.Assert.*;

import ComparaisonTri.modele.algosTris.*;
import ComparaisonTri.modele.algosTris.TimSort;

/**
 * Cette classe implémente les tests pour tous les algorithmes de tri disponibles.
 */
public class SortImplTest implements SortTest {

    /** Tableau contenant toutes les instances d'algorithmes de tri disponibles. */
    Tri[] algorithmes = {
        new Quicksort(), new TriPairImpair(), new TriParInsertion(), new TriCocktail(),
        new Introsort(), new TriAPeigne(), new TriParSelection(), new TriDeShell(),
        new SmoothSort(), new TimSort(), new PigeonholeSort(), new TriParTas(),
        new TriABulles(), new TriFusion()
    };

    /** Tableau contenant les noms de tous les algorithmes de tri disponibles. */
    String[] nomAlgorithmes = {
        "Quicksort", "TriPairImpair", "TriParInsertion", "TriCocktail", "Introsort",
        "TriAPeigne", "TriParSelection", "TriDeShell", "SmoothSort", "TimSort",
        "PigeonholeSort", "TriParTas", "TriABulles", "TriFusion"
    };

    /**
     * Méthode de test pour trier un tableau avec un algorithme de tri spécifié.
     *
     * @param tri L'algorithme de tri à tester.
     */
    @Test
    private void testTrier(Tri tri) {
        // Test avec un tableau non trié
        List<Integer> tableauNonTrie = Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5);
        List<Integer> resultatAttendu = Arrays.asList(1, 1, 2, 3, 3, 4, 5, 5, 5, 6, 9);
        List<Integer> tableauTrie = tri.trier(tableauNonTrie);
        assertEquals(resultatAttendu, tableauTrie);

        // Test avec un tableau déjà trié
        List<Integer> tableauDejaTrie = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> tableauDejaTrie2 = new ArrayList<>(tableauDejaTrie);
        tableauDejaTrie = tri.trier(tableauDejaTrie);
        assertEquals(tableauDejaTrie2, tableauDejaTrie);

        // Test avec un tableau contenant un seul élément
        List<Integer> tableauUnElement = Collections.singletonList(42);
        List<Integer> tableauUnElement2 = new ArrayList<>(tableauUnElement);
        tableauUnElement = tri.trier(tableauUnElement);
        assertEquals(tableauUnElement2, tableauUnElement);

        System.out.println("OK");
    }

    /**
     * Méthode de test pour vérifier le nom de l'algorithme de tri.
     *
     * @param tri L'algorithme de tri à tester.
     * @param nomAlgo Le nom attendu de l'algorithme de tri.
     */
    @Test
    private void testGetNomAlgo(Tri tri, String nomAlgo) {
        assertEquals(nomAlgo, tri.getNomAlgo());

        System.out.println("OK");
    }

    @Override
    public void test() {
        for (int i = 0; i < algorithmes.length; i++) {
            Tri tri = algorithmes[i];
            String nomTri = nomAlgorithmes[i];

            System.out.println("------- Début des tests pour : " + nomTri + " -------");

            testTrier(tri);
            testGetNomAlgo(tri, nomTri);

            System.out.println("------- Fin des tests pour : " + nomTri + " -------");
        }
    }
}
