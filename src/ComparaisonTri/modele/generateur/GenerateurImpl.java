package ComparaisonTri.modele.generateur;

import java.util.*;
import java.lang.Math;

/**
 * <p>
 * Cette classe abstraite fournit une base pour la génération de valeurs pour des tableaux d'entiers.
 * Le désordre peut être considéré comme :
 * <ul>
 *  <li>le nombre de valeurs qui se trouve à la mauvaise position dans un tableau croissant,</li>
 *  <li>le nombre de valeurs décroissantes dans un tableau croissant.</li>
 * </ul> 
 * <p>
 *
 * @see Generateur
 * @author Christopher Bird, Quentin Rondeau, Matisse Senechal, Université de Caen Normandie, France
 */
public abstract class GenerateurImpl implements Generateur {

    /** Tableau d'entiers généré par la classe. */
    protected List<Integer> tab;

    /** La taille du tableau. */
    protected final int TAILLE, DESORDRE, NB_ELEMENTS_A_DESORDONNER;

    /** Répartition du désordre dans le tableau. */
    protected final String REPARTITION;

    /**
     * Initialise un tableau avec une taille spécifiée et avec 
     * la possibilité de spécifier un niveau de désordre.
     *
     * @param tailleTab La taille du tableau à générer.
     * @param desordre  Le niveau de désordre dans la répartition des nombres aléatoires.
     */
    public GenerateurImpl(int tailleTab, int desordre, String repartition) {
        this.TAILLE = tailleTab;
        this.DESORDRE = desordre;
        this.REPARTITION = repartition;
        this.tab = new ArrayList<Integer>();
        this.NB_ELEMENTS_A_DESORDONNER = (int) (tailleTab * (desordre / 100.0));
    }

    public GenerateurImpl(int tailleTab, int desordre) {
        this(tailleTab, desordre, null);
    }

    @Override
    public List<Integer> getTab() {
        return this.tab;
    }

    @Override
    public int getTaille() {
        return TAILLE;
    }

    @Override
    public int getDesordre() {
        return DESORDRE;
    }

    @Override
    public String getRepartition() {
        return REPARTITION;
    }

    @Override
    public void generer() {
        List<Integer> intervallesDesordonnes = calculDesordre();

        // Si le désordre consiste en des valeurs alternées
        if(getNomGen().contains("Alt")) {
            genererTableauAlt(intervallesDesordonnes);
        } else {
            // Si le désordre consiste en des valeurs décroissantes
            genererTableauDec(intervallesDesordonnes);

            // Si le désordre consiste en des valeurs mélangées
            if(getNomGen().contains("Mel")) {
                do {
                    melangerIntervallesDesordonnes(intervallesDesordonnes);
                } while(melangeNecessaire(intervallesDesordonnes));
            }
        }
    }

    /**
     * Génère un tableau avec des valeurs alternées dans les intervalles de désordre.
     *
     * @param intervallesDesordonnes La liste des intervalles de désordre sous la forme : [début, fin].
     */
    private void genererTableauAlt(List<Integer> intervallesDesordonnes) {
        // Initialisation des valeurs maximales et minimales
        int valMax = TAILLE / 2;
        int valMinDes = (-TAILLE / 2) + 1;
        int valMinOrd = valMinDes;

        // Calcul de la longueur des intervalles et de l'index de la dernière valeur ordonnée
        int longueurIntervalle = intervallesDesordonnes.size();
        int indexDerniereValOrd = intervallesDesordonnes.get(longueurIntervalle - 2);
    
        for (int i = 0; i < TAILLE; i++) {
            // Récupération de la valeur précédente, initialisée à 0 pour le premier élément
            int precedenteValeur = (i > 0) ? tab.get(i - 1) : 0;

            // Vérification si l'index se trouve dans un intervalle de désordre
            if (estDansIntervalle(i, intervallesDesordonnes)) {
                if (precedenteValeur > 0) {
                    // Si la valeur précédente est positive, décrémente la valeur minimale
                    tab.add(--valMinDes);
                } else {
                    // Si un intervalle de désordre se trouve à la fin du tableau
                    if (intervalleFin(i, intervallesDesordonnes, longueurIntervalle)) {
                        gereFinIntervalleDesordre(valMax, indexDerniereValOrd);
                    } else {
                        tab.add(valMax++);
                    }
                }
            } else {
                tab.add(++valMinOrd);
            }
        }
    }

    /**
     * Vérifie si la position spécifiée se trouve dans le dernier intervalle et à la fin du tableau.
     *
     * @param pos                   La position à vérifier.
     * @param intervallesDesordre  La liste des intervalles de désordre.
     * @param longueurIntervalle   La longueur de la liste des intervalles de désordre.
     * @return                      True si la position se trouve dans le dernier intervalle et à la fin du tableau, sinon false.
     */
    private boolean intervalleFin(int pos, List<Integer> intervallesDesordre, int longueurIntervalle) {
        int finDernierIntervalle = intervallesDesordre.get(longueurIntervalle-1);
        int debutDernierIntervalle = intervallesDesordre.get(longueurIntervalle-2);

        boolean estDernierElemTab = (finDernierIntervalle == TAILLE-1);

        return estDansIntervalle(pos, debutDernierIntervalle, finDernierIntervalle) && estDernierElemTab;
    }

    /**
     * Exécute les opérations pour s'assurer que si un intervalle se trouve à la fin du tableau
     * alors toutes ses valeurs positives ne doivent pas déjà être à la bonne position.
     *
     * @param valMax               La valeur maximale.
     * @param indexDerniereValOrd  L'index de la dernière valeur ordonnée.
     */
    private void gereFinIntervalleDesordre(int valMax, int indexDerniereValOrd) {
        int difference = valMax - tab.get(indexDerniereValOrd - 2);

        tab.add(valMax - difference);

        valMax++;
        indexDerniereValOrd--;
    }

    /**
     * Génère un tableau d'entiers en suivant des intervalles de désordre spécifiés.
     *
     * @param intervallesDesordonnes La liste des intervalles de désordre sous la forme : [début, fin].
     */
    private void genererTableauDec(List<Integer> intervallesDesordonnes) {
        int valeurMax = Integer.MIN_VALUE;
        int valeurMin = Integer.MAX_VALUE;
    
        for (int i=0; i < TAILLE; i++) {
            int precedenteValeur = (i > 0) ? tab.get(i-1) : 0;
    
            if (estDansIntervalle(i, intervallesDesordonnes)) {
                tab.add(valeurMin = Math.min(precedenteValeur-1, valeurMin));
                valeurMax = Math.max(precedenteValeur, valeurMax);
            } else {
                tab.add(valeurMax = Math.max(precedenteValeur+1, valeurMax));
                valeurMin = Math.min(precedenteValeur, valeurMin);
            }
        }
    }

    /**
     * Vérifie si une valeur donnée se trouve dans l'un (ou les) intervalle(s) spécifié(s).
     *
     * @param val        La valeur à vérifier.
     * @param intervalle La liste des intervalles sous la forme : [début, fin].
     * @return {@code true} si la valeur se trouve dans l'un des intervalles, sinon {@code false}.
     */
    private boolean estDansIntervalle(int val, List<Integer> intervalle) {
        Iterator<Integer> iterator = intervalle.iterator();

        while (iterator.hasNext()) {
            int debutIntervalle = iterator.next();
            int finIntervalle = iterator.next();

            if (estDansIntervalle(val, debutIntervalle, finIntervalle)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Vérifie si une position donnée est comprise entre les bornes d'un intervalle.
     *
     * @param pos         La position à vérifier.
     * @param borneInf    Borne supérieure de l'intervalle.
     * @param borneSup    Borne inférieure de l'intervalle.
     * @return {@code true} si la position se trouve entre les deux bornes, sinon {@code false}.
     */
    private boolean estDansIntervalle(int pos, int borneInf, int borneSup) {
        return (pos >= borneInf) && (pos <= borneSup);
    }

    /**
     * Mélange les valeurs entre le début et la fin de chaque intervalle de désordre.
     * 
     * @param intervallesDesordonnes La liste des intervalles de désordre sous la forme : [début, fin].
     */
    private void melangerIntervallesDesordonnes(List<Integer> intervallesDesordonnes) {
        Random random = new Random();

        for (int i=0; i < intervallesDesordonnes.size(); i+=2) {
            int debutIntervalle = intervallesDesordonnes.get(i);
            int finIntervalle = intervallesDesordonnes.get(i + 1);

            // Mélange des valeurs
            for (int j=debutIntervalle; j <= finIntervalle; j++) {
                int indexAleatoire = random.nextInt(finIntervalle - debutIntervalle + 1) + debutIntervalle;
                Collections.swap(tab, j, indexAleatoire);
            }
        }
    }

    /**
     * Vérifie que la valeur la plus petite n'est pas en première position (car sinon la valeur est bien placée)
     * si c'est le cas on remélange les valeurs de l'intervalle jusqu'à que ce ne soit plus le cas.
     *
     * @param intervallesDesordonnes La liste des intervalles de désordre sous la forme : [début, fin].
     */
    private boolean melangeNecessaire(List<Integer> intervallesDesordonnes) {
        return (estDansIntervalle(0, intervallesDesordonnes)) && (tab.get(0) == -NB_ELEMENTS_A_DESORDONNER);
    }

    /**
     * Calcule les bornes du/des intervalle(s) des éléments à désordonner
     * en fonction du pourcentage de désordre et de la répartition spécifiés.
     *
     * <p>
     * Dans le cas où il n'y a qu'un seul intervalle de désordre le calcul
     * des bornes dépendra de la valeur de {@code REPARTITION} :
     * <ul>
     *      <li>"debut": L'intervalle est situé au début du tableau.</li>
     *      <li>"fin": L'intervalle est situé à la fin du tableau.</li>
     *      <li>"milieu": L'intervalle est situé au milieu du tableau.</li>
     * </ul>
     * </p>
     *
     * @return Un tableau d'entiers représentant les bornes de(s) intervalle(s) [debut, fin].
     */
    protected abstract List<Integer> calculDesordre();
}