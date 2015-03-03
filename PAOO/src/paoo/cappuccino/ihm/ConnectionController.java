package paoo.cappuccino.ihm;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import paoo.cappuccino.ihm.utils.Constantes;

/**
 * classe controller de l'ihm connexion
 *
 * @author mopsome
 *
 */
public class ConnectionController extends JPanel {

  private static final long serialVersionUID = -1367769157419064413L;
  private ConnectionView parent;

  public ConnectionController(ConnectionView ConnectionView) {
    parent = ConnectionView;
    this.setLayout(new FlowLayout(FlowLayout.RIGHT, Constantes.MGap, Constantes.MGap));

    JButton inscrire = new JButton("S'inscrire");
    inscrire.addActionListener(e -> {
      inscription();
    });
    JButton connecter = new JButton("Se connecter");
    connecter.addActionListener(e -> {
      connexion();
    });
    this.add(inscrire);
    this.add(connecter);

  }

  /**
   * méthode pour gérer l'action du bouton connexion
   *
   */
  private void connexion() {

  }

  /**
   * méthode pour gérer l'action du bouon s'inscire
   *
   */
  private void inscription() {
    parent.dispose();
    new InscriptionView();
  }

}
