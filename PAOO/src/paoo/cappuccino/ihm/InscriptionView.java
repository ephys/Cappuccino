package paoo.cappuccino.ihm;

import javax.swing.JFrame;

/**
 * @author Opsomer Mathias
 *
 *         vue de l'ihm de l'inscription
 */
public class InscriptionView extends JFrame {


  private static final long serialVersionUID = -7334876606967058112L;

  /**
   * constructeur
   */
  InscriptionView() {
    this.setTitle("Inscription");
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setSize(450, 600);
    this.setLocationRelativeTo(null);
    this.setVisible(true);
  }
}
