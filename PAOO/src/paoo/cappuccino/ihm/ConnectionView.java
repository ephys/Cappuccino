package paoo.cappuccino.ihm;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.ihm.utils.Constantes;
import paoo.cappuccino.ihm.utils.JPanelPasswordError;
import paoo.cappuccino.ihm.utils.JPanelTextError;

/**
 * classe vue de la connection
 *
 * @author Opsomer Mathias
 *
 */
public class ConnectionView extends JFrame {
  JPanelPasswordError panelPassword;
  JPanelTextError panelUsername;

  /**
   * constructeur
   */
  public ConnectionView() {
    this.setTitle("connexion");
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setSize(400, 240);
    this.setResizable(false);
    this.setLocationRelativeTo(null);
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBorder(BorderFactory.createEmptyBorder(Constantes.LGap, Constantes.MGap, 0,
        Constantes.MGap));

    JPanel fields = new JPanel(new GridLayout(2, 0, 0, Constantes.MGap));

    panelUsername = new JPanelTextError("Nom d'utilisateur");
    fields.add(panelUsername);
    panelPassword = new JPanelPasswordError("Mot de passe");
    fields.add(panelPassword);

    mainPanel.add(fields);
    mainPanel.add(new ConnectionController(this), BorderLayout.SOUTH);
    this.add(mainPanel);
    this.setVisible(true);
  }

  /**
   * returns the identification into a new userDto
   *
   * @return IUserDto the user created with the login keyed in
   */
  public IUserDto getIdentifiants() {
    return null;

  }

  /**
   * Set the JLabel error from the JPanelTextError for the Username
   *
   * @param error the error to display
   */
  public void SetErrorUsername(String error) {

  }

  /**
   * Set the JLabel error from the JPanelPasswordError for the Password
   *
   * @param error the error to display
   */
  public void SetErrorPassword(String error) {

  }
}
