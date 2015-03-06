package paoo.cappuccino.ihm;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.ihm.utils.ConstantesIHM;

/**
 * Controller for the connection ihm
 *
 * @author mopsome
 *
 */
public class ConnectionController extends JPanel {

  private static final long serialVersionUID = -1367769157419064413L;
  private ConnectionView parent;
  private ErrorModele modele;

  /**
   * Constructeur
   *
   * @param ConnectionView the parent
   * @param modele the modele he's working with
   */
  public ConnectionController(ConnectionView ConnectionView, ErrorModele modele) {
    parent = ConnectionView;
    this.modele = modele;
    this.setLayout(new FlowLayout(FlowLayout.RIGHT, ConstantesIHM.MGap, ConstantesIHM.MGap));

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
    String passwordError = null, usernameError = null;
    if (password.length == 0 || pseudo.length() == 0) {

      if (password.length == 0)
        passwordError = ConstantesIHM.ERROR_FIELD_EMPTY;

      if (pseudo.length() == 0)
        usernameError = ConstantesIHM.ERROR_FIELD_EMPTY;

    } else {
      IUserDto user = null;
      // TODO user = IUserUcc.logIn(pseudo, password);
      if (user == null) {
        passwordError = ConstantesIHM.ERROR_WRONG_LOGIN;
        usernameError = ConstantesIHM.ERROR_WRONG_LOGIN;
      } else {
        parent.dispose();
        new MenuView(user);
      }
    }
    modele.setErrors(passwordError, usernameError);
  }

  /**
   * Method to go to the inscription
   *
   */
  private void inscription() {
    new InscriptionView(new ErrorModele());
  }

}
