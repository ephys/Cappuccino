package ihm;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * classe controller de l'ihm connexion
 * 
 * @author mopsome
 *
 */
public class ConnexionController extends JPanel {
  private ConnexionVue vue;

  public ConnexionController(ConnexionVue vue) {
    this.vue = vue;
    this.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));

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
   * @author Opsomer Mathias
   *
   */
  private void connexion() {
    vue.removePasswordError();
    vue.removeTextError();
  }

  /**
   * méthode pour gérer l'action du bouon s'inscire
   * 
   * @author Opsomer Mathias
   *
   */
  private void inscription() {
    vue.setTextError("invalide");
    vue.setPasswordError("invalide");
  }


}
