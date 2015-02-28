/**
 *
 */
package paoo.cappuccino.ihm;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import paoo.cappuccino.util.MenuState;

/**
 * @author Opsomer Mathias
 *
 *
 */
public class MenuController extends JPanel {
  private MenuModele modele;

  /**
   * le controller menu
   * 
   * @param menuVue le parent
   */
  public MenuController(MenuModele modele) {
    this.modele = modele;
    this.setLayout(new GridLayout(0, 1));

    JButton accueil = new JButton("Accueil");
    accueil.addActionListener(e -> {
      modele.changeState(MenuState.ACCUEIL);
    });

    this.add(accueil);
    // to move
    JLabel recherches = new JLabel("Recherches");
    recherches.setBackground(Color.BLUE);
    this.add(recherches);

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
