package ComparaisonTri.controleur;

import java.util.*;
import java.io.*;

import ComparaisonTri.modele.generateur.Generateur;
import ComparaisonTri.modele.generateur.intervalle.generateurDecroissant.*;
import ComparaisonTri.modele.generateur.intervalles.generateurDecroissant.*;
import ComparaisonTri.modele.generateur.intervalle.generateurMelange.*;
import ComparaisonTri.modele.generateur.intervalles.generateurMelange.*;
import ComparaisonTri.modele.generateur.intervalle.generateurAlterne.*;
import ComparaisonTri.modele.generateur.intervalles.generateurAlterne.*;

/**
 * Classe contenant des méthodes pour la vérification des paramètres et la création des générateurs.
 * 
 * @author Christopher Bird, Quentin Rondeau, Matisse Senechal, Université de Caen Normandie, France
 */
public class VerifParams {

    /**
     * Vérifie si le nombre de paramètres est valide.
     *
     * @param args Les arguments de la ligne de commande.
     * @return true si le nombre de paramètres est valide, sinon false.
     */
    public static boolean parametresValides(String[] args) {
        return (args.length == 4);
    }

    /**
     * Crée un générateur en fonction des paramètres donnés.
     *
     * @param args Les arguments de la ligne de commande.
     *             Les 4 arguments attendus sont : typeGenerateur, tailleTableau, desordre, repartition.
     * @return Le générateur créé.
     * @throws IllegalArgumentException Si le type de générateur n'est pas reconnu.
     */
    public static Generateur creerGenerateur(String[] args) throws IllegalArgumentException {
        String typeGenerateur = args[0].toLowerCase();
        int tailleTableau = Integer.parseInt(args[1]);
        int desordre = Integer.parseInt(args[2]);
        String repartition = args[3];

        Generateur generateur;

        switch (typeGenerateur) {
            case "desordreintervalledec":
                generateur = new GenerateurDesordreIntervalleDec(tailleTableau, desordre, repartition);
                break;
            case "desordreintervallesdec":
                generateur = new GenerateurDesordreIntervallesDec(tailleTableau, desordre, repartition);
                break;
            case "desordreintervallemel":
                generateur = new GenerateurDesordreIntervalleMel(tailleTableau, desordre, repartition);
                break;
            case "desordreintervallesmel":
                generateur = new GenerateurDesordreIntervallesMel(tailleTableau, desordre, repartition);
                break;
            case "desordreintervallealt":
                generateur = new GenerateurDesordreIntervalleAlt(tailleTableau, desordre, repartition);
                break;
            case "desordreintervallesalt":
                generateur = new GenerateurDesordreIntervallesAlt(tailleTableau, desordre, repartition);
                break;
            default:
                throw new IllegalArgumentException("Type de générateur non reconnu : " + args[0]);
        }

        generateur.generer();

        return generateur;
    }

    /**
     * Lit les paramètres à partir d'un fichier spécifié.
     *
     * @param cheminFichier Le chemin du fichier contenant les paramètres.
     * @return Un tableau de chaînes de caractères contenant les paramètres lus depuis le fichier.
     */
    public static String[] lireParametres(String cheminFichier) {
        try (Scanner scanner = new Scanner(new File(cheminFichier))) {
            String[] params = new String[4];
            for (int i = 0; (i < 4 && scanner.hasNextLine()); i++) {
                params[i] = scanner.nextLine();
            }
            return params;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}