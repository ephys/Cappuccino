package paoo.cappuccino.ihm;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Controller for the inscription ihm
 *
 * @author Opsomer Mathias
 *
 *
 */
public class InscriptionController extends JPanel {

  private static final long serialVersionUID = 8967022645980426577L;
  private InscriptionView parent;
  private ErrorModele modele;

  /**
   * Constructor
   *
   * @param inscriptionVue la vue sur laquelle il s'affiche
   * @param modele the modele he's working with
   */
  public InscriptionController(InscriptionView inscriptionVue, ErrorModele modele) {
    parent = inscriptionVue;
    this.modele = modele;
    this.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));

    JButton inscrire = new JButton("Valider");
    inscrire.addActionListener(e -> {
      valider();
    });
    JButton connecter = new JButton("Annuler");
    connecter.addActionListener(e -> {

    });
    this.add(inscrire);
    this.add(connecter);

  }

  /**
   * Call UCC.register and go to the menu
   */
  private void valider() {

  }
}
