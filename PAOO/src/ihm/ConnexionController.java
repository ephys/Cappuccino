package ihm;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JOptionPane;
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
      JOptionPane.showMessageDialog(this, "not yet implemented.");
    });
    JButton connecter = new JButton("Se connecter");
    connecter.addActionListener(e -> {
      JOptionPane.showMessageDialog(this, "not yet implemented.");
    });
    this.add(inscrire);
    this.add(connecter);

  }


}
