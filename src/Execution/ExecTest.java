package Execution;

import ComparaisonTri.modele.algosTris.algosTrisTests.*;
import ComparaisonTri.modele.generateur.generateurTests.*;

/**
 * Classe principale pour l'exécution des tests sur les algorithmes de tri.
 * Cette classe lance les tests pour chaque algorithme de tri implémenté dans le programme.
 * 
 * @author Christopher Bird, Quentin Rondeau, Matisse Senechal, Université de Caen Normandie, France
 */
public class ExecTest {

    /**
     * Méthode principale du programme.
     * Lance les tests pour chaque algorithme de tri implémenté dans le programme.
     *
     * @param args Les arguments d'entrée du programme.
     */
    public static void main(String[] args) {
        System.out.println("------- Début des tests pour les algorithmes de tri -------");
        new SortImplTest().test();
        System.out.println("------- Fin des tests pour les algorithmes de tri -------");

        System.out.println("------- Début des tests pour les générateurs -------");
        new GenerateurImplTest().test();
        System.out.println("------- Fin des tests pour les générateurs -------");
    }
}