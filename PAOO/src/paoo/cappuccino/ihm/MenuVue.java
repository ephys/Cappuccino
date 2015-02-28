/**
 *
 */
package paoo.cappuccino.ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * classe vue de l'ihm Menu
 * 
 * @author Opsomer Mathias
 *
 */
public class MenuVue extends JFrame implements ChangeListener {
  // private User utilisateur;
  private Font arial = new Font("Arial", Font.PLAIN, 28);
  private MenuModele modele;
  private JLabel titre = new JLabel("test");
  private JPanel principale;

  /**
   * le constructeur prend en paramètre l'utilisateur connecter
   * 
   * @param user l'utilisateur connecter
   *
   */
  // public MenuVue(User user){
  public MenuVue(String user) {
    this.modele = new MenuModele(user);
    this.setTitle("Cappuccino");
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setSize(1100, 700);
    this.setLayout(new BorderLayout());

    JPanel banniere = new JPanel(new BorderLayout());
    JLabel bienvenu = new JLabel("Bienvenue " + modele.getUser());
    bienvenu.setHorizontalAlignment(JLabel.CENTER);
    bienvenu.setFont(arial);
    banniere.add(bienvenu);
    banniere.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, Color.BLACK));

    // to move
    JButton deconnexion = new JButton("Déconnexion");
    deconnexion.addActionListener(e -> {
      this.dispose();
      new ConnexionVue();
    });
    banniere.add(deconnexion, BorderLayout.EAST);

    titre.setBackground(Color.BLUE);
    titre.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, Color.BLACK));

    principale.add(titre, BorderLayout.NORTH);

    this.setLocationRelativeTo(null);
    this.add(new MenuController(modele), BorderLayout.WEST);
    this.add(banniere, BorderLayout.NORTH);
    this.setVisible(true);
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
   */
  @Override
  public void stateChanged(ChangeEvent arg0) {


  }
}
