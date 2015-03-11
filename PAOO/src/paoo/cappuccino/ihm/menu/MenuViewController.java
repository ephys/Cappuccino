package paoo.cappuccino.ihm.menu;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.util.IhmConstants;

public class MenuViewController extends JPanel {

  public MenuViewController(MenuModel model, IGuiManager manager) {
    super(new BorderLayout());
    this.setBorder(BorderFactory.createEmptyBorder(IhmConstants.L_GAP,
        IhmConstants.M_GAP, 0, IhmConstants.M_GAP));



    // buttons //
    JButton deconection = new JButton("déconexion");
    deconection.addActionListener(e -> {
      // TODO
      });
    JPanel controls =
        new JPanel(new GridLayout(2, 0, 0, IhmConstants.M_GAP));

    controls.setLayout(new FlowLayout(FlowLayout.RIGHT,
        IhmConstants.M_GAP, IhmConstants.M_GAP));

    this.add(controls, BorderLayout.SOUTH);
    // end buttons //

    this.add(new MenuView(model, deconection), BorderLayout.CENTER);
  }
}
