package paoo.cappuccino.ihm;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import paoo.cappuccino.ihm.utils.MenuState;

/**
 * The Controller for the menu ( all the button to the left )
 *
 * @author Opsomer Mathias
 *
 *
 */
public class MenuController extends JPanel {

  private static final long serialVersionUID = -8247656394104598549L;
  private MenuModele modele;

  /**
   * Constructor
   *
   * @param MenuModele the modele the view is working with
   */
  public MenuController(MenuModele modele) {
    this.modele = modele;
    this.setLayout(new GridLayout(0, 1));
    this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 3, Color.BLACK));

    JButton accueil = new JButton("Accueil");
    accueil.addActionListener(e -> {
      modele.changeState(MenuState.ACCUEIL);
    });

    this.add(accueil);
    // TODO to move
    JLabel recherches = new JLabel("Recherches");
    JPanel recherchesPanel = new JPanel();
    recherchesPanel.setAlignmentX(CENTER_ALIGNMENT);
    recherchesPanel.setBorder(BorderFactory.createMatteBorder(3, 0, 3, 0, Color.BLACK));
    recherchesPanel.setBackground(Color.BLUE);
    recherchesPanel.add(recherches);
    this.add(recherchesPanel);

    JButton participation = new JButton("Participation");
    accueil.addActionListener(e -> {
      modele.changeState(MenuState.RECH_PARTICIPATION);
    });
    this.add(participation);

    JButton contact = new JButton("Pers. de contact");
    accueil.addActionListener(e -> {
      modele.changeState(MenuState.RECH_PERS_CONTACT);
    });
    this.add(contact);

    JButton entreprise = new JButton("Entreprises");
    accueil.addActionListener(e -> {
      modele.changeState(MenuState.RECH_ENTREPRISE);
    });
    this.add(entreprise);

    this.add(new JLabel());
    // TODO to move
    JLabel journees = new JLabel("Gérer journées");
    JPanel journeesPanel = new JPanel();
    journeesPanel.setAlignmentX(CENTER_ALIGNMENT);
    journeesPanel.setBorder(BorderFactory.createMatteBorder(3, 0, 3, 0, Color.BLACK));
    journeesPanel.setBackground(Color.BLUE);
    journeesPanel.add(journees);
    this.add(journeesPanel);

    JButton selection = new JButton("Sélec. entreprises");
    accueil.addActionListener(e -> {
      modele.changeState(MenuState.SELEC_ENTREPRISES);
    });
    this.add(selection);

    JButton creerJournee = new JButton("Créer journée");
    accueil.addActionListener(e -> {
      modele.changeState(MenuState.CREER_JOURNEE);
    });
    this.add(creerJournee);

    this.add(new JLabel());
    // TODO to move
    JLabel gererEntreprise = new JLabel("Gérer entreprises");
    JPanel entreprisePanel = new JPanel();
    entreprisePanel.setAlignmentX(CENTER_ALIGNMENT);
    entreprisePanel.setBorder(BorderFactory.createMatteBorder(3, 0, 3, 0, Color.BLACK));
    entreprisePanel.setBackground(Color.BLUE);
    entreprisePanel.add(gererEntreprise);
    this.add(entreprisePanel);

    JButton creerEntreprise = new JButton("Créer entreprise");
    accueil.addActionListener(e -> {
      modele.changeState(MenuState.CREER_ENTREPRISE);
    });
    this.add(creerEntreprise);

    JButton creerContact = new JButton("Créer pers. de contact");
    accueil.addActionListener(e -> {
      modele.changeState(MenuState.ACCUEIL);
    });
    this.add(creerContact);
  }

}
