package paoo.cappuccino.ihm;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.ihm.utils.JLabelFont;

/**
 * view of the menu ihm
 *
 * @author Opsomer Mathias
 *
 */
public class MenuView extends JFrame implements ChangeListener {
  /**
   * version
   */
  private static final long serialVersionUID = -8074586111584676162L;
  // private User utilisateur;
  /**
   * the modele
   */
  private MenuModele modele;
  /**
   * the title of the state
   */
  private JLabelFont titre = new JLabelFont("test", 22);
  /**
   * the panel that will change with the sate
   */
  private JPanel principale;

  /**
   * constructor
   *
   * @param user the connected user
   *
   */
  public MenuView(IUserDto user) {
    modele = new MenuModele(user);
    this.setTitle("Cappuccino");
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setSize(1100, 700);
    this.setLayout(new BorderLayout());
    principale = new JPanel(new BorderLayout());

    JPanel banniere = new JPanel(new BorderLayout());
    JLabelFont bienvenu = new JLabelFont("Bienvenue " + modele.getUser(), 28);
    bienvenu.setHorizontalAlignment(JLabel.CENTER);
    banniere.add(bienvenu);
    banniere.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.BLACK));

    // TODO to move
    JButton deconnexion = new JButton("Déconnexion");
    deconnexion.addActionListener(e -> {
      this.dispose();
      new ConnectionView();
    });
    banniere.add(deconnexion, BorderLayout.EAST);

    // icone
    // TODO changer le path
    JLabel icon =
        new JLabel(new ImageIcon("D:\\HAUTE_ECOLE\\workspace\\workspace2bis\\PAOO\\lib\\icon.png"));
    banniere.add(icon, BorderLayout.WEST);

    JPanel titrePanel = new JPanel();
    titrePanel.setBackground(Color.BLUE);
    titrePanel.setAlignmentX(CENTER_ALIGNMENT);
    titrePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.BLACK));
    titrePanel.add(titre);

    principale.add(titrePanel, BorderLayout.NORTH);

    this.add(principale, BorderLayout.CENTER);
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
    // TODO

  }
}
