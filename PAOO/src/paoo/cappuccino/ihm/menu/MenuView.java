package paoo.cappuccino.ihm.menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.login.LoginFrame;
import paoo.cappuccino.ihm.util.IhmConstants;
import paoo.cappuccino.ihm.util.JLabelFont;

public class MenuView extends JPanel implements ChangeListener {

  private final MenuModel menuModel;
  private final ViewControllerFactory viewFactory;

  private JPanel mainPanel;
  private JLabel title = new JLabelFont("/aucune page chargée/", 20);

  public MenuView(MenuModel menuModel, IGuiManager guiManager, ViewControllerFactory viewFactory) {
    super(new BorderLayout());

    this.menuModel = menuModel;
    this.viewFactory = viewFactory;
    this.menuModel.addChangeListener(this);

    // header
    JPanel header = new JPanel(new BorderLayout());
    header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));

    JLabelFont welcome = new JLabelFont("Bienvenue " + menuModel.getLoggedUser().getFirstName()
                                        + " " + menuModel.getLoggedUser().getLastName(), 28);
    welcome.setHorizontalAlignment(JLabel.CENTER);
    header.add(welcome);

    JPanel disconnectionPanel = new JPanel();
    JButton disconnectionButton = new JButton("Déconnexion");
    disconnectionPanel.add(disconnectionButton);
    disconnectionButton.addActionListener(e -> guiManager.openFrame(LoginFrame.class));
    header.add(disconnectionPanel, BorderLayout.EAST);

    JPanel imagePanel = new JPanel();
    imagePanel.setPreferredSize(new Dimension(150, 90));
    imagePanel.setAlignmentX(CENTER_ALIGNMENT);

    try {
      imagePanel.add(new JLabel(new ImageIcon(ImageIO.read(new FileInputStream("lib/logo.png")))));
    } catch (IOException e) {
      guiManager.getLogger().log(Level.WARNING, "Could not load the application logo", e);
    }

    header.add(imagePanel, BorderLayout.WEST);
    this.add(header, BorderLayout.NORTH);
    // end header

    // body

    mainPanel = new JPanel(new BorderLayout());

    JPanel titlePanel = new JPanel();
    titlePanel.setBackground(IhmConstants.LIGHT_BLUE);
    titlePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
    titlePanel.add(title);

    mainPanel.add(titlePanel, BorderLayout.NORTH);

    JPanel body = new JPanel(new BorderLayout());
    body.add(mainPanel);

    this.add(body, BorderLayout.CENTER);
    // end body

    stateChanged(null);
  }

  @Override
  public void stateChanged(ChangeEvent event) {
    title.setText(menuModel.getCurrentPage().getTitle());
    mainPanel.add(viewFactory.createViewController(menuModel.getCurrentPage()),
                  BorderLayout.CENTER);
  }
}
