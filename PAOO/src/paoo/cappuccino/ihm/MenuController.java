/**
 *
 */
package paoo.cappuccino.ihm;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Opsomer Mathias
 *
 *
 */
public class MenuController extends JPanel {
  private JFrame parent;

  /**
   * @param menuVue
   */
  public MenuController(JFrame parent) {
    this.parent = parent;
    JButton deconnexion = new JButton("Déconnexion");
    deconnexion.addActionListener(e -> {
      parent.dispose();
      new ConnexionVue();
    });
    this.add(deconnexion, BorderLayout.NORTH);
  }

}
