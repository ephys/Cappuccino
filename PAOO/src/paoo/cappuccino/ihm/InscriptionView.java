package paoo.cappuccino.ihm;

import javax.swing.JFrame;

/**
 * View for the inscription ihm
 * 
 * @author Opsomer Mathias
 *
 */
public class InscriptionView extends JFrame {


  private static final long serialVersionUID = -7334876606967058112L;

  /**
   * Constructeur
   */
  InscriptionView() {
    this.setTitle("Inscription");
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setSize(450, 600);
    this.setLocationRelativeTo(null);
    this.setVisible(true);
  }
}
