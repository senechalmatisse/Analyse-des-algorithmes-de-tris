package Execution.SansInterface;

import ComparaisonTri.controleur.*;
import Execution.ExecProgrammeAbs;

/**
 * Classe exécutable du programme pour une exécution sans interface graphique.
 * Cette classe gère la vérification des paramètres d'entrée
 * et l'exécution du programme à l'aide de la méthode executer héritée.
 * En cas de paramètres invalides, un message d'erreur est affiché
 * et le programme se termine avec un code d'erreur 1.
 * 
 * @see ExecProgrammeAbs
 * @author Christopher Bird, Quentin Rondeau, Matisse Senechal, Université de Caen Normandie, France
 */
public class ExecProgramme extends ExecProgrammeAbs {

    /**
     * Méthode principale du programme pour une exécution sans interface graphique.
     *
     * @param args Les arguments d'entrée du programme.
     */
    public static void main(String[] args) {
        if (!VerifParams.parametresValides(args)) {
            System.err.println("Erreur : Paramètres invalides.");
            System.exit(1);
        }

        new ExecProgramme().executer(args);
    }
}