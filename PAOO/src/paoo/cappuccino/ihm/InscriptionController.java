package paoo.cappuccino.ihm;

import java.awt.*;

import javax.swing.*;

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

  /**
   * Constructor
   *
   * @param inscriptionVue la vue sur laquelle il s'affiche
   */
  public InscriptionController(InscriptionView inscriptionVue) {
    parent = inscriptionVue;
    this.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));

    JButton inscrire = new JButton("Valider");
    inscrire.addActionListener(e -> {
      valider();
    });
    JButton connecter = new JButton("Annuler");
    connecter.addActionListener(e -> {
      parent.dispose();
      new ConnectionView();
    });
    this.add(inscrire);
    this.add(connecter);

  }

  /**
   * Call UCC.register and go to the menu
   */
  private void valider() {
    // valider input
    parent.dispose();
    // new MenuVue(new User);
    new MenuView(null);
  }
}
