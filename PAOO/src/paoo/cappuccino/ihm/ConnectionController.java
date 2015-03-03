package paoo.cappuccino.ihm;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import paoo.cappuccino.ihm.utils.Constantes;

/**
 * classe controller de l'ihm connexion
 *
 * @author mopsome
 *
 */
public class ConnectionController extends JPanel {
  private ConnectionView parent;

  public ConnectionController(ConnectionView ConnectionView) {
    this.parent = ConnectionView;
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
    // boolean complet = true;
    // if (parent.getUsername().equals("")) {
    // parent.setTextError("ce champ est obligatoire");
    // complet = false;
    // }
    //
    // if (parent.getPassword().length == 0) {
    // parent.setPasswordError("ce champ est obligatoire");
    // complet = false;
    // }
    //
    // // if(ucc.getUtilisateur(vue.getUsername(),vue.getPassword()){
    // if (complet && true) {
    // // new MenuVue(user);
    // new MenuView("george");
    // parent.dispose();
    // }
    // // gestion et affichage erreure possible rendu par l'ucc.
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
