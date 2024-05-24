package Execution.AvecInterface;

import Execution.ExecProgrammeAbs;

/**
 * Classe principale du programme pour une exécution avec interface graphique.
 * Cette classe gère l'exécution du programme à l'aide de la méthode executer héritée.
 * Elle crée une instance de la classe ExecProgramme
 * et appelle sa méthode executer avec les paramètres d'entrée.
 * 
 * @see ExecProgrammeAbs
 * @author Christopher Bird, Quentin Rondeau, Matisse Senechal, Université de Caen Normandie, France
 */
public class ExecProgramme extends ExecProgrammeAbs {

    /**
     * Méthode principale du programme pour une exécution avec interface graphique.
     *
     * @param args Les arguments d'entrée du programme.
     */
    public static void main(String[] args) {
        new ExecProgramme().executer(args);
    }
}