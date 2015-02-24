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
  public ConnexionController() {
    this.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));



    JButton inscrire = new JButton("S'inscrire");
    inscrire.addActionListener(e -> {
      // appel a l'ihm insciption
      });
    JButton connecter = new JButton("Se connecter");
    connecter.addActionListener(e -> {
      // appel a l'ucc se connecter
      });
    this.add(inscrire);
    this.add(connecter);

  }


}
