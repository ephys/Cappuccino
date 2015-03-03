package paoo.cappuccino.ihm;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.ihm.utils.Constantes;

/**
 * classe controller de l'ihm connexion
 *
 * @author mopsome
 *
 */
public class ConnectionController extends JPanel {
  /**
   * Version
   */
  private static final long serialVersionUID = -1367769157419064413L;
  /**
   * the parent
   */
  private ConnectionView parent;

  /**
   * the constructeur
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
   * méthode pour gérer l'action du bouton connexion
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
   * méthode pour gérer l'action du bouon s'inscire
   *
   */
  private void inscription() {
    parent.dispose();
    new InscriptionView();
  }

}
