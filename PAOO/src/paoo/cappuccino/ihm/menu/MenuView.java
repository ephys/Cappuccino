package paoo.cappuccino.ihm.menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import paoo.cappuccino.ihm.util.JLabelFont;

public class MenuView extends JPanel {

  private MenuModel model;


  public MenuView(MenuModel model, JButton deconection) {
    super(new BorderLayout());
    this.model = model;


    JLabelFont bienvenu = new JLabelFont("Bienvenue TODO ", 28);// TODO
    bienvenu.setHorizontalAlignment(JLabel.CENTER);
    this.add(bienvenu);

    JPanel panelDeconnection = new JPanel();
    panelDeconnection.add(deconection);
    this.add(panelDeconnection, BorderLayout.EAST);

    this.setBorder(BorderFactory
        .createMatteBorder(0, 0, 3, 0, Color.BLACK));

    JPanel imagePanel = new JPanel();
    imagePanel.setPreferredSize(new Dimension(150, 90));
    imagePanel.setAlignmentX(CENTER_ALIGNMENT);
    try {
      imagePanel.add(new JLabel(new ImageIcon(ImageIO
          .read(new FileInputStream("lib/icon.png")))));
    } catch (IOException e) {
    };
    this.add(imagePanel, BorderLayout.WEST);


  }
}
