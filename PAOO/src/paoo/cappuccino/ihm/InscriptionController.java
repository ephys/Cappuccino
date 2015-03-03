package paoo.cappuccino.ihm;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author Opsomer Mathias
 *
 *
 */
public class InscriptionController extends JPanel {
  /**
   * Version
   */
  private static final long serialVersionUID = 8967022645980426577L;
  /**
   * the parent (the view that has call the controller)
   */
  private InscriptionView parent;

  /**
   * constructeur du controller
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
   * methode qui valide l'inscription
   */
  private void valider() {
    // valider input
    parent.dispose();
    // new MenuVue(new User);
    new MenuView(null);
  }
}
