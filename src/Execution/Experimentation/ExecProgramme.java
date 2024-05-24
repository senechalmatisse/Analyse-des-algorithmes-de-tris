package Execution.Experimentation;

import ComparaisonTri.controleur.VerifParams;
import ComparaisonTri.modele.donnees.ResultatMesure;
import ComparaisonTri.modele.generateur.Generateur;
import Execution.ExecProgrammeAbs;

public class ExecProgramme extends ExecProgrammeAbs {

    /**
     * Méthode principale du programme pour éxecuter les expérimentations.
     *
     * @param args Les arguments d'entrée du programme.
     */
    public static void main(String[] args) {
        new ExecProgramme().executer(args);
    }

    @Override
    protected void executer(String[] args) {
        try {
            // Création du générateur de tableaux aléatoires
            Generateur generateur = VerifParams.creerGenerateur(args);

            // Calcul des résultats pour chaque algorithme de tri
            // et les exportent dans deux fichiers JSON
            ResultatMesure.mesureSauvegardeResultats(TOUS_LES_TRIS, generateur, true);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}