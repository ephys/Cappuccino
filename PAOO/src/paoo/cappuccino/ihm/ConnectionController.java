package paoo.cappuccino.ihm;

import java.awt.*;

import javax.swing.*;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.ihm.utils.Constantes;

/**
 * Controller for the connection ihm
 *
 * @author mopsome
 *
 */
public class ConnectionController extends JPanel {

  private static final long serialVersionUID = -1367769157419064413L;
  private ConnectionView parent;

  /**
   * Constructeur
   *
   * @param ConnectionView the parent
   */
  public ConnectionController(ConnectionView ConnectionView) {
    parent = ConnectionView;
    this.setLayout(new FlowLayout(FlowLayout.RIGHT, Constantes.MGap, Constantes.MGap));

    JButton inscrire = new JButton("S'inscrire");
    inscrire.addActionListener(e -> {
      inscription();
    });
    JButton connecter = new JButton("Se connecter");
    connecter.addActionListener(e -> {
      connexion();
    });
    this.add(inscrire);
    this.add(connecter);

  }

  /**
   * Methode to call UCC.login and go to the menu
   *
   */
  private void connexion() {
    char[] password = parent.getPassword();
    String pseudo = parent.getUsername();
    if (password.length == 0 || pseudo.length() == 0) {
      if (password.length == 0)
        parent.SetErrorPassword(Constantes.ERROR_FIELD_EMPTY);
      else
        parent.SetErrorPassword("");
      if (pseudo.length() == 0)
        parent.SetErrorUsername(Constantes.ERROR_FIELD_EMPTY);
      else
        parent.SetErrorUsername("");
    } else {
      IUserDto user = null;
      // TODO user = IUserUcc.logIn(pseudo, password);
      if (user != null) {
        parent.SetErrorPassword(Constantes.ERROR_WRONG_LOGIN);
        parent.SetErrorUsername(Constantes.ERROR_WRONG_LOGIN);
      } else {
        parent.dispose();
        new MenuView(user);
      }
    }
  }

  /**
   * Method to go to the inscription
   *
   */
  private void inscription() {
    parent.dispose();
    new InscriptionView();
  }

}
