package ComparaisonTri.modele.donnees;

import java.io.*;
import java.util.*;

import ComparaisonTri.modele.algosTris.*;
import ComparaisonTri.modele.generateur.Generateur;

import org.json.simple.*;

/**
 * Classe représentant les résultats de la mesure d'exécution d'un tri.
 * Les résultats incluent le temps d'exécution, le nombre de comparaisons et le nombre d'assignations.
 * 
 * @author Christopher Bird, Quentin Rondeau, Matisse Senechal, Université de Caen Normandie, France
 */
public class ResultatMesure {

    /** 
     * Le temps d'exécution moyen de l'algorithme de tri en secondes.
     * Le nombre moyen de comparaisons effectuées lors de l'exécution de l'algorithme de tri.
     * Le nombre moyen d'assignations effectuées lors de l'exécution de l'algorithme de tri.
     */
    private final double tempsExecution, comparaisonsEffectuees, assignationsEffectuees;

    /** Le nombre d'exécutions pour calculer les résultats. */
    private static final int NOMBRE_EXECUTIONS = 50;

    /** Le chemin des fichiers JSON pour exporter les résultats. */
    private static final String FICHIER_RESULTATS_MOYENS_JSON = "ComparaisonTri/modele/donnees/resultatsMoyensTris.json";
    private static final String FICHIER_RESULTATS_JSON = "ComparaisonTri/modele/donnees/resultatsTris.json";

    /**
     * Constructeur de la classe ResultatMesure.
     *
     * @param tempsExecution         Le temps d'exécution moyen de l'algorithme de tri en secondes.
     * @param comparaisonsEffectuees Le nombre moyen de comparaisons effectuées lors de l'exécution de l'algorithme de tri.
     * @param assignationsEffectuees Le nombre moyen d'assignations effectuées lors de l'exécution de l'algorithme de tri.
     */
    public ResultatMesure(double tempsExecution, double comparaisonsEffectuees, double assignationsEffectuees) {
        this.tempsExecution = tempsExecution;
        this.comparaisonsEffectuees = comparaisonsEffectuees;
        this.assignationsEffectuees = assignationsEffectuees;
    }

    /**
     * Obtient le temps d'exécution obtenu par d'un algorithme de tri en secondes.
     *
     * @return Le temps d'exécution moyen.
     */
    public double getTempsExecution() {
        return tempsExecution;
    }

    /**
     * Obtient le nombre moyen de comparaisons effectuées lors de l'exécution d'un algorithme
     *
     * @return Le nombre moyen de comparaisons effectuées.
     */
    public double getNombreComparaisons() {
        return comparaisonsEffectuees;
    }

    /**
     * Obtient le nombre moyen d'assignations effectuées lors de l'exécution d'un algorithme.
     *
     * @return Le nombre moyen d'assignations effectuées.
     */
    public double getNombreAssignations() {
        return assignationsEffectuees;
    }

    /**
     * Effectue les mesures et sauvegarde les résultats des expérimentations dans des fichiers JSON.
     * Si le paramètre experimentation est true, les résultats sont sauvegardés pour l'expérimentation,
     * sinon, ils sont exportés dans des fichiers JSON distincts.
     *
     * @param tris Les algorithmes de tri à évaluer.
     * @param generateur Le générateur de tableaux aléatoires.
     * @param experimentation Boolean indiquant s'il s'agit d'une expérimentation (true) ou non (false).
     */
    public static void mesureSauvegardeResultats(List<Tri> tris, Generateur generateur, boolean experimentation) {
        JSONObject resultatsMoyensJSON = new JSONObject();
        JSONObject resultatsJSON = new JSONObject();

        for (Tri tri : tris) {
            String nomTri = tri.getNomAlgo();
            // Initialisation du tableau JSON pour stocker les résultats individuels de chaque mesure de l'algorithme de tri
            JSONArray resultatsTriJSON = new JSONArray();

            // Initialisation des variables pour calculer les totaux de temps, de comparaisons et d'assignations
            double tempsTotal = 0;
            double comparaisonsTotales = 0;
            double assignationsTotales = 0;

            for (int i = 0; i < NOMBRE_EXECUTIONS; i++) {
                ResultatMesure resultats = mesureResultats(tri, generateur.getTab());

                tempsTotal += resultats.getTempsExecution();
                comparaisonsTotales += resultats.getNombreComparaisons();
                assignationsTotales += resultats.getNombreAssignations();

                resultatsTriJSON.add(creationMesureJSON(resultats));
            }

            resultatsJSON.put(nomTri, resultatsTriJSON);
            resultatsMoyensJSON.put(nomTri, calculMoyennes(tempsTotal, comparaisonsTotales, assignationsTotales));
            
            if (!experimentation) {
                System.out.println(nomTri + " terminé");
            }
        }

        // Exportation des résultats au format JSON en vérifiant si l'on est en train
        // de réaliser des expérimentations ou non
        if (experimentation) {
            sauvegarderResultatsExperimentation(generateur, resultatsJSON, "resultatsTris");
            sauvegarderResultatsExperimentation(generateur, resultatsMoyensJSON, "resultatsMoyensTris");
        } else {
            exportResultatsJSON(generateur, resultatsJSON, FICHIER_RESULTATS_JSON);
            exportResultatsJSON(generateur, resultatsMoyensJSON, FICHIER_RESULTATS_MOYENS_JSON);
        }
    }

    /**
     * Mesure les résultats obtenus d'un algorithme de tri sur un tableau donné.
     *
     * @param tri      L'algorithme de tri à mesurer.
     * @param tableau  Le tableau à trier.
     * @return Un objet ResultatMesure contenant les résultats de la mesure.
     */
    private static ResultatMesure mesureResultats(Tri tri, List<Integer> tableau) {
        long tempsAvantTri = System.nanoTime();
        long comparaisonsAvantTri = tri.getNombreComparaisons();
        long assignationsAvantTri = tri.getNombreAssignations();

        tri.trier(tableau);

        long tempsApresTri = System.nanoTime();
        long comparaisonsApresTri = tri.getNombreComparaisons();
        long assignationsApresTri = tri.getNombreAssignations();

        double tempsExecutionAlgo = (tempsApresTri - tempsAvantTri) / 1_000_000_000.0; // Convertion en secondes
        double comparaisonsEffectueesAlgo = comparaisonsApresTri - comparaisonsAvantTri;
        double assignationsEffectueesAlgo = assignationsApresTri - assignationsAvantTri;

        return new ResultatMesure(tempsExecutionAlgo, comparaisonsEffectueesAlgo, assignationsEffectueesAlgo);
    }

    /**
     * Calcule les moyennes des résultats de mesure donnés.
     *
     * @param tempsTotal Le temps total d'exécution de l'algorithme de tri pour toutes les itérations.
     * @param comparaisonsTotales Le nombre total de comparaisons effectuées pour toutes les itérations.
     * @param assignationsTotales Le nombre total d'assignations effectuées pour toutes les itérations.
     * @return Un objet JSONObject contenant les moyennes calculées.
     */
    private static JSONObject calculMoyennes(double tempsTotal, double comparaisonsTotales, double assignationsTotales) {
        double tempsMoyen = tempsTotal / NOMBRE_EXECUTIONS;
        double comparaisonsMoyennes = comparaisonsTotales / NOMBRE_EXECUTIONS;
        double assignationsMoyennes = assignationsTotales / NOMBRE_EXECUTIONS;

        JSONObject moyennesJSON = new JSONObject();
        ajouteMesure(moyennesJSON, tempsMoyen, comparaisonsMoyennes, assignationsMoyennes);
        return moyennesJSON;
    }

    /**
     * Crée un objet JSON représentant les résultats obtenus pour chaque mesure lors du tri.
     *
     * @param resultats Les résultats obtenus pour chaque mesure.
     * @return Un objet JSONObject représentant les résultats pour chaque mesure lors du tri.
     */
    private static JSONObject creationMesureJSON(ResultatMesure resultats) {
        JSONObject mesureJSON = new JSONObject();
        ajouteMesure(mesureJSON, resultats.getTempsExecution(), resultats.getNombreComparaisons(), resultats.getNombreAssignations());
        return mesureJSON;
    }

    /**
     * Ajoute les mesures de temps d'exécution, de comparaisons et d'assignations à un objet JSON.
     *
     * @param objet L'objet JSON auquel ajouter les mesures.
     * @param temps Le temps d'exécution à ajouter.
     * @param comparaisons Le nombre de comparaisons à ajouter.
     * @param assignations Le nombre d'assignations à ajouter.
     */
    private static void ajouteMesure(JSONObject objet, double temps, double comparaisons, double assignations) {
        objet.put("tempsExecution", temps);
        objet.put("comparaisons", comparaisons);
        objet.put("assignations", assignations);
    }

    /**
     * Exporte les résultats au format JSON dans un fichier.
     * 
     * @param generateur Le générateur ayant produit les données.
     * @param resultatsJSON Les résultats à exporter.
     * @param FICHIER_JSON Le nom du fichier JSON de sortie.
     */
    public static void exportResultatsJSON(Generateur generateur, JSONObject resultatsJSON, String FICHIER_JSON) {
        JSONObject resultatJSON = new JSONObject();
        resultatJSON.put("nomGenerateur", generateur.getNomGen());
        resultatJSON.put("tailleTableau", generateur.getTaille());
        resultatJSON.put("desordre", generateur.getDesordre());
        resultatJSON.put("repartition", generateur.getRepartition());
        resultatJSON.put("resultats", resultatsJSON);

        try (FileWriter file = new FileWriter(FICHIER_JSON)) {
            file.write(resultatJSON.toJSONString());
            System.out.println("Résultats exportés avec succès dans le fichier " + FICHIER_JSON);
        } catch (IOException e) {
            System.out.println("Une erreur est survenue lors de la sauvegarde des résultats.");
            e.printStackTrace();
        }
    }

    public static void sauvegarderResultatsExperimentation(Generateur generateur, JSONObject resultatsJSON, String baseNomFichier) {
        JSONObject resultatJSON = new JSONObject();
        resultatJSON.put("nomGenerateur", generateur.getNomGen());
        resultatJSON.put("tailleTableau", generateur.getTaille());
        resultatJSON.put("desordre", generateur.getDesordre());
        resultatJSON.put("repartition", generateur.getRepartition());
        resultatJSON.put("resultats", resultatsJSON);

        // Définir le chemin du dossier de sauvegarde
        String dossierSauvegarde = "ComparaisonTri/modele/donnees/experimentation/";
        String nomFichier = baseNomFichier + "_" + generateur.getNomGen() + "_" + generateur.getTaille() + "_" + generateur.getDesordre();

        // Chercher un nom de fichier disponible en incrémentant le suffixe
        int i = 1;
        File fichier = new File(dossierSauvegarde + nomFichier + "_" + i + ".json");
        while (fichier.exists()) {
            i++;
            fichier = new File(dossierSauvegarde + nomFichier + "_" + i + ".json");
        }

        // Créer le fichier JSON et écrire les résultats
        try (FileWriter file = new FileWriter(fichier)) {
            file.write(resultatJSON.toString());
        } catch (IOException e) {
            System.out.println("Une erreur est survenue lors de la sauvegarde des résultats.");
            e.printStackTrace();
        }
    }
}