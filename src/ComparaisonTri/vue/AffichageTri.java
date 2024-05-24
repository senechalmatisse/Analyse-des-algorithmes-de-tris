package ComparaisonTri.vue;

import java.awt.*;
import javax.swing.*;
import java.util.List;

import ComparaisonTri.modele.algosTris.*;

/**
 * La classe AffichageTri représente un JPanel dans lequel on y retrouve trois JPanel :
 * <ul>
 *  <li>Panel haut : un JPanel dans lequel s'affiche le nom de l'algorithme utilisé,</li>
 *  <li>Panel centre : le JPanel où va s'afficher le tableau en train d'être trié,</li>
 *  <li>Panel bas : un JPanel dans lequel on a tous les boutons.</li>
 * </ul>
 * 
 * @see JPanel
 * @author Christopher Bird, Quentin Rondeau, Matisse Senechal, Université de Caen Normandie, France
 */
public class AffichageTri extends JPanel {

    /** Le tableau à trier. */
    private final List<Integer> tableau;

    /** L'algorithme de tri sélectionné. */
    private final Tri triSelectionne;

    /** Le label affichant le nom de l'algorithme de tri. */
    private final JLabel labelAlgo;

    /**
     * Le panel contenant les boutons.
     * Le panel contenant le tableau à trier.
     * Le panel contenant le label labelAlgo.
     */
    private final JPanel bas, centre, haut;

    /**
     * Constructeur de la classe AffichageTri.
     * 
     * @param tableau       Le tableau à trier.
     * @param triSelectionne L'algorithme de tri sélectionné.
     */
    public AffichageTri(List<Integer> tableau, Tri triSelectionne) {
        super(new BorderLayout());
        this.tableau = tableau;
        this.triSelectionne = triSelectionne;

        this.labelAlgo = creerLabel();
        this.centre = new AffichageTableau(this.tableau, this.triSelectionne);
        this.bas = new BoutonGUI((AffichageTableau) this.centre);
        this.haut = creerHautPanel();

        ajoutPanels();
        setBackground(Color.GRAY);
    }

    /**
     * Crée et configure le label affichant le nom de l'algorithme de tri.
     * 
     * @return Le label configuré.
     */
    private JLabel creerLabel() {
        JLabel label = new JLabel("Tri du tableau par : " + triSelectionne.getNomAlgo());

        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);

        return label;
    }

    /**
     * Crée et configure le panneau contenant le label.
     * 
     * @return Le panneau configuré.
     */
    private JPanel creerHautPanel() {
        JPanel panel = new JPanel();

        panel.setBackground(Color.GRAY);
        panel.setOpaque(true);
        panel.add(this.labelAlgo);

        return panel;
    }

    /**
     * Ajoute les panels et les composants au panel AffichageTri.
     */
    private void ajoutPanels() {
        add(this.haut, BorderLayout.NORTH);
        add(new JScrollPane(this.centre), BorderLayout.CENTER);
        add(this.bas, BorderLayout.SOUTH);
    }
}