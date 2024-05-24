package ComparaisonTri.vue;

import javax.swing.*;
import java.util.*;

import ComparaisonTri.modele.generateur.Generateur;
import ComparaisonTri.modele.algosTris.*;
import ComparaisonTri.controleur.*;

/**
 * Cette classe représente une fenêtre d'interface graphique pour visualiser un algorithme de tri
 * appliqué en temps réel à un tableau généré selon certains paramètres.
 * <p>
 * Pour utiliser cette classe, il est nécessaire de passer les arguments suivants en ligne de commande :
 * <ul>
 *     <li>type de générateur : le nom du générateur à utiliser pour créer le tableau</li>
 *     <li>taille du tableau : la taille du tableau à générer</li>
 *     <li>pourcentage de désordre : le pourcentage de désordre à appliquer au tableau</li>
 *     <li>repartition du désordre : la répartition du désordre dans le tableau (debut, milieu ou fin)</li>
 *     <li>type d'algorithme : le nom de l'algorithme de tri à appliquer sur le tableau</li>
 * </ul>
 * </p>
 * 
 * @author Christopher Bird, Quentin Rondeau, Matisse Senechal, Université de Caen Normandie, France
 */
public class FenetreVisualisation {

    /**
     * Méthode principale pour lancer la fenêtre d'interface graphique.
     *
     * @param args Les arguments de la ligne de commande.
     */
    public static void main(String[] args) {
      String [] paramsGenerateur = {
        args[0].toLowerCase(), args[1], args[2], args[3].toLowerCase()
      };
      String typeAlgorithme = args[4].toLowerCase();

      if (!VerifParams.parametresValides(paramsGenerateur)) {
        System.err.println("Nombre de paramètres invalide.");
        return;
      }

      Generateur generateurSelectionne = VerifParams.creerGenerateur(paramsGenerateur);
      Tri triSelectionne = creerTri(typeAlgorithme);

      afficherInterfaceGraphique(generateurSelectionne, triSelectionne);
    }

    /**
     * Crée une instance de l'algorithme de tri spécifié.
     *
     * @param typeAlgorithme Le nom de l'algorithme de tri.
     * @return Une instance de l'algorithme de tri correspondant au nom spécifié.
     */
    private static Tri creerTri(String typeAlgorithme) {
        Map<String, Tri> tris = new HashMap<>();
        tris.put("pigeonholesort", new PigeonholeSort());
        tris.put("quicksort", new Quicksort());
        tris.put("introsort", new Introsort());
        tris.put("triapeigne", new TriAPeigne());
        tris.put("tripairimpair", new TriPairImpair());
        tris.put("triparinsertion", new TriParInsertion());
        tris.put("timsort", new TimSort());
        tris.put("trideshell", new TriDeShell());
        tris.put("trifusion", new TriFusion());
        tris.put("triabulles", new TriABulles());
        tris.put("tricocktail", new TriCocktail());
        tris.put("tripartas", new TriParTas());
        tris.put("smoothsort", new SmoothSort());
        tris.put("triparselection", new TriParSelection());

        return tris.getOrDefault(typeAlgorithme, null);
    }

    /**
     * Affiche la fenêtre d'interface graphique avec le tableau et l'algorithme de tri spécifiés.
     *
     * @param generateur Le générateur de tableau.
     * @param tri L'algorithme de tri.
     */
    private static void afficherInterfaceGraphique(Generateur generateur, Tri tri) {
        JFrame interface_graphique = new JFrame("Algorithme de tri");
        AffichageTri panneau = new AffichageTri(generateur.getTab(), tri);
        interface_graphique.getContentPane().add(panneau);

        interface_graphique.pack();
        interface_graphique.setExtendedState(JFrame.MAXIMIZED_BOTH);
        interface_graphique.setVisible(true);
        interface_graphique.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}