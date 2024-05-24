package ComparaisonTri.modele.algosTris;

import java.util.*;

/**
 * Classe abstraite qui fournit l'implémentation de la méthode d'échange pour les algorithmes de tri.
 *
 * @see Tri
 * @author Christopher Bird, Quentin Rondeau, Matisse Senechal, Université de Caen Normandie, France
 */
public abstract class TriImplementation implements Tri {

    /** 
     * Le nombre total de comparaisons effectuées lors de l'exécution de l'algorithme.
     * Le nombre total d'assignations effectuées lors de l'exécution de l'algorithme.
     */
    private long nombreComparaisons, nombreAssignations;

    /**
     * Une carte associant chaque instant à une liste d'entiers.
     * Une Map associant chaque instant à une liste d'indices rouges (indices ayant été modifiées).
     */
    private Map<Integer, List<Integer>> listeChaqueInstant, listeIndiceRougeChaqueInstant;

    /**
     * Map associant un entier à une liste de longs pour représenter le nombre de comparaisons et d'assignations à chaque instant.
     * La clé représente l'instant, la valeur représente le nombre de comparaisons et d'assignations à cet instant.
     */
    private Map<Integer, List<Long>> listeComparaisonsAssignationsChaqueInstant;

    /** L'indice de l'instant actuel. */
    private Integer indiceInstant;

    /** Un indicateur pour vérifier si la liste d'états à chaque instant a été créée. */
    private boolean listeChaqueInstantCreated;

    public TriImplementation() {
        this.nombreComparaisons = 0;
        this.nombreAssignations = 0;
        this.indiceInstant = 0;
        this.listeChaqueInstantCreated = false;

        this.listeChaqueInstant = new HashMap<>();
        this.listeIndiceRougeChaqueInstant = new HashMap<>();
        this.listeComparaisonsAssignationsChaqueInstant = new HashMap<>();
    }

    @Override
    public long getNombreComparaisons() {
        return nombreComparaisons;
    }

    @Override
    public long getNombreAssignations() {
        return nombreAssignations;
    }

    @Override
    public Map<Integer, List<Integer>> getListeState() {
        return this.listeChaqueInstant;
    }

    @Override
    public Map<Integer, List<Integer>> getListeIndiceRougeState() {
        return this.listeIndiceRougeChaqueInstant;
    }

    @Override
    public Map<Integer, List<Long>> getListeComparaisonsAssignationsState() {
        return this.listeComparaisonsAssignationsChaqueInstant;
    }

    /**
     * Renvoie si la liste pour chaque instant du tri a été créée.
     *
     * @return Si la liste pour chaque instant du tri a été créée.
     */
    protected boolean isListeChaqueInstantCreated() {
        return this.listeChaqueInstantCreated;
    }

    /**
     * Modifie le nombre d'assignations par la nouvelle valeur.
     * 
     * @param assignations Le nouveau nombre d'assignations.
     */
    protected void setNombreAssignations(long assignations) {
        this.nombreAssignations = assignations;
    }

    /**
     * Modifie le nombre de comparaisons par la nouvelle valeur.
     *
     * @param comparaisons Le nouveau nombre de comparaisons.
     */
    protected void setNombreComparaisons(long comparaisons) {
        this.nombreComparaisons = comparaisons;
    }

    /**
     * Modifie l'indice par le nouvel indice à cet instant.
     * 
     * @param indiceInstant Le nouvel indice.
     */
    protected void setIndiceInstant(Integer indiceInstant) {
        this.indiceInstant = indiceInstant;
    }

    /**
     * Incrémente le nombre de comparaisons effectuées lors de l'exécution de l'algorithme de tri.
     */
    protected void incrementerComparaisons() {
        nombreComparaisons++;
    }

    /**
     * Incrémente le nombre d'assignations effectuées lors de l'exécution de l'algorithme de tri.
     */
    protected void incrementerAssignations() {
        nombreAssignations++;
    }

    /**
     * Échange les éléments aux positions i et j dans le tableau.
     *
     * @param tableau Le tableau d'entiers dans lequel effectuer l'échange.
     * @param i La position du premier élément à échanger.
     * @param j La position du deuxième élément à échanger.
     */
    protected void echanger(List<Integer> tableau, int i, int j) {
        int temp = tableau.get(i);

        nombreAssignations += 2;

        tableau.set(i, tableau.get(j));
        tableau.set(j, temp);

        if (listeChaqueInstantCreated) {
            List<Integer> tableauCopie = new ArrayList<>(tableau);

            this.addComparaisonsAssignationsState();
            this.addIndiceRougeState(i, j);
            this.addState(tableauCopie);
        }
    }

    /**
     * Ajoute les statistiques de comparaisons et d'assignations à la liste à chaque instant.
     */
    protected void addComparaisonsAssignationsState() {
        List<Long> stats = new ArrayList<>();

        stats.add(nombreComparaisons);
        stats.add(nombreAssignations);

        listeComparaisonsAssignationsChaqueInstant.put(indiceInstant, stats);
    }

    /**
     * Ajoute la liste des indices "i" et "j", de longueur 2 qui ont été modifiés à cet instant.
     *
     * @param i Première position.
     * @param j Deuxième position.
     */
    protected void addIndiceRougeState(Integer i, Integer j) {
        listeIndiceRougeChaqueInstant.put(indiceInstant, new ArrayList<>(Arrays.asList(i, j)));
    }

    /**
     * Ajoute la liste de l'indice "pos" qui a été modifié à cet instant.
     *
     * @param pos La position.
     */
    protected void addIndiceRougeState(Integer pos) {
        listeIndiceRougeChaqueInstant.put(indiceInstant, new ArrayList<>(Arrays.asList(pos)));
    }

    /**
     * Ajoute un état dans la liste des états qui serviront à afficher graphiquement
     * chaque état de la liste durant la transformation de la liste.
     *
     * @param state Le tableau d'entiers qui représente l'état.
     */
    protected void addState(List<Integer> state) {
        listeChaqueInstant.put(indiceInstant, state);
        indiceInstant++;
    }

    public void recupStates(Tri tri){
        Map<Integer,List<Integer>> tabTri = tri.getListeState();

        for (Integer cle : tabTri.keySet()){
            listeChaqueInstant.put(indiceInstant, tabTri.get(cle));
            indiceInstant++;
        }
    }

    /**
     * Active la création de la liste d'états.
     */
    public void activateStates() {
        listeChaqueInstantCreated = true;
    }

    /**
     * Réinitialise l'état de la liste.
     *
     * @param state Le tableau à copier pour la réinitialisation.
     */
    protected void resetState(List<Integer> state) {
        getListeState().clear();
        setIndiceInstant(0);

        List<Integer> copiedState = new ArrayList<>(state);

        addComparaisonsAssignationsState();
        addState(copiedState);
    }

    /**
     * Réinitialise l'état de la liste et ajoute une position à mettre en surbrillance.
     *
     * @param state Le tableau à copier pour la réinitialisation.
     * @param position La position à mettre en surbrillance.
     */
    protected void resetState(List<Integer> state, int position) {
        List<Integer> copiedState = new ArrayList<>(state);

        addIndiceRougeState(position);
        addComparaisonsAssignationsState();
        addState(copiedState);
    }
}