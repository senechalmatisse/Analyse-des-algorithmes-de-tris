package ComparaisonTri.modele.generateur.generateurTests;

import java.util.*;

import static org.junit.Assert.*;
import org.junit.Test;

import ComparaisonTri.modele.generateur.Generateur;
import ComparaisonTri.modele.generateur.intervalle.generateurAlterne.*;
import ComparaisonTri.modele.generateur.intervalle.generateurDecroissant.*;
import ComparaisonTri.modele.generateur.intervalle.generateurMelange.*;
import ComparaisonTri.modele.generateur.intervalles.generateurAlterne.*;
import ComparaisonTri.modele.generateur.intervalles.generateurDecroissant.*;
import ComparaisonTri.modele.generateur.intervalles.generateurMelange.*;

/**
 * Cette classe implémente les tests pour les générateurs de tableaux.
 * 
 * @author Christopher Bird, Quentin Rondeau, Matisse Senechal, Université de Caen Normandie, France
 */
public class GenerateurImplTest implements GenerateurTest {

    private final List<String> NOMS = Collections.unmodifiableList(Arrays.asList(
        "DesordreIntervalleDec", "DesordreIntervallesDec", "DesordreIntervalleMel",
        "DesordreIntervallesMel", "DesordreIntervalleAlt", "DesordreIntervallesAlt"
    ));

    /** Liste de répartition de test pour les générateurs. */
    private final List<String> REPARTITIONS = Collections.unmodifiableList(Arrays.asList(
        "debut", "milieu", "fin", "debut", "milieu", "fin"
    ));

    /** Liste de tailles de test pour les générateurs. */
    private final List<Integer> TAILLES = Collections.unmodifiableList(Arrays.asList(
        1, 10, 100, 1000, 100000, 1000000
    ));

    /** Liste de désordres de test pour les générateurs. */
    private final List<Integer> DESORDRES = Collections.unmodifiableList(Arrays.asList(
        35, 89, 0, 100, 50, 25
    ));

    /** Liste de désordres de test pour les générateurs. */
    private final List<Generateur> GENERATEURS = Collections.unmodifiableList(Arrays.asList(
        new GenerateurDesordreIntervalleDec(1,35,"debut"),
        new GenerateurDesordreIntervallesDec(10,89,"milieu"),
        new GenerateurDesordreIntervalleMel(100,0,"fin"),
        new GenerateurDesordreIntervallesMel(1000,100,"debut"),
        new GenerateurDesordreIntervalleAlt(100000,50,"milieu"),
        new GenerateurDesordreIntervallesAlt(1000000,25,"fin")
    ));

    private final int NOMBRE_GENERATEURS = 6;

    /**
     * Méthode de test pour vérifier le nom du générateur.
     */
    @Test
    private void testGetNomGen() {
        for (int i=0; i < NOMBRE_GENERATEURS; i++) {
            String nomAttendu = NOMS.get(i);
            String nomGenerateurTest = GENERATEURS.get(i).getNomGen();

            assertEquals(nomAttendu, nomGenerateurTest); 
        }

        System.out.println("OK");
    }

    /**
     * Méthode de test pour vérifier la taille générée par les générateurs.
     */
    @Test
    private void testTaille() {
        for (int i=0; i < NOMBRE_GENERATEURS; i++) {
            int tailleAttendu = TAILLES.get(i); 
            int tailleGenerateurTest = GENERATEURS.get(i).getTaille();

            assertEquals(tailleAttendu, tailleGenerateurTest);
        }

        System.out.println("OK");
    }

    @Test
    private void testDesordre() {
        for (int i=0; i < NOMBRE_GENERATEURS; i++) {
            int desordreAttendu = DESORDRES.get(i); 
            int desordreGenerateurTest = GENERATEURS.get(i).getDesordre();

            assertEquals(desordreAttendu, desordreGenerateurTest);
        }

        System.out.println("OK");
    }

    @Test
    private void testRepartition() {
        for (int i = 0; i < NOMBRE_GENERATEURS; i++) {
            String repartitionAttendu = REPARTITIONS.get(i); 
            String desordreGenerateurTest = GENERATEURS.get(i).getRepartition();

            assertEquals(repartitionAttendu, desordreGenerateurTest);
        }

        System.out.println("OK");
    }

    @Test
    private void testCalculDesordre() {
        final List<Integer> desordreCalcule = Collections.unmodifiableList(Arrays.asList(
            0, 8, 0, 1000, 50000, 250000
        ));

        int i=0;

        while(i < NOMBRE_GENERATEURS) {
            Generateur generateur = GENERATEURS.get(i);

            int calculDesordreAttendu = desordreCalcule.get(i);
            int calculDesordreTest = calculDesordre(generateur);

            assertEquals(calculDesordreAttendu, calculDesordreTest);

            i += 1;
        }

        System.out.println("OK");
    }

    private int calculDesordre(Generateur generateur) {
        int desordreGenerateur = generateur.getDesordre();
        int tailleGenerateur = generateur.getTaille();

        return (int) (tailleGenerateur * (desordreGenerateur / 100.0));
    }

    @Override
    public void test() {
        System.out.println("------- Début test getNomGen() -------");
        testGetNomGen();
        System.out.println("------- Fin test getNomGen() -------");

        System.out.println("------- Début test getTaille() -------");
        testTaille();
        System.out.println("------- Fin test getTaille() -------");

        System.out.println("------- Début test getDesordre() -------");
        testDesordre();
        System.out.println("------- Fin test getDesordre() -------");

        System.out.println("------- Début test getRepartition() -------");
        testRepartition();
        System.out.println("------- Fin test getRepartition() -------");

        System.out.println("------- Début test calculDesordre() -------");
        testCalculDesordre();
        System.out.println("------- Fin test calculDesordre() -------");
    }
}