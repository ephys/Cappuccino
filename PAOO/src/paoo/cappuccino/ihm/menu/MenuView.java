package paoo.cappuccino.ihm.menu;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import paoo.cappuccino.ihm.util.JLabelFont;

public class MenuView extends JPanel {

  private MenuModel model;


  public MenuView(MenuModel model, JButton button) {
    super(new BorderLayout());

    JPanel banniere = new JPanel(new BorderLayout());
    JLabelFont bienvenu =
        new JLabelFont("Bienvenue " + model.getPseudoUser(), 28);
    bienvenu.setHorizontalAlignment(JLabel.CENTER);
    banniere.add(bienvenu);
    banniere.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0,
        Color.BLACK));
    this.add(banniere);
    JPanel panelDeconnection = new JPanel();
    panelDeconnection.add(button);
    this.add(panelDeconnection, BorderLayout.EAST);

    this.model = model;



  }
}
