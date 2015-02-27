/**
 *
 */
package paoo.cappuccino.ihm;

import javax.swing.JFrame;

/**
 * @author Opsomer Mathias
 *
 *
 */
public class MenuVue extends JFrame {
  /**
   *
   */
  public MenuVue() {

    this.setTitle("Cappuccino");
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setSize(1280, 800);
    this.setLocationRelativeTo(null);
    this.add(new MenuController(this));
    this.setVisible(true);
  }
}
