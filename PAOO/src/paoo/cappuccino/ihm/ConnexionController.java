package paoo.cappuccino.ihm;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * classe controller de l'ihm connexion
 *
 * @author mopsome
 *
 */
public class ConnexionController extends JPanel {
  private ConnexionVue parent;

  public ConnexionController(ConnexionVue connexionVue) {
    this.parent = connexionVue;
    this.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));

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
    boolean complet = true;
    if (parent.getUsername().equals("")) {
      parent.setTextError("ce champ est obligatoire");
      complet = false;
    }

    if (parent.getPassword().length == 0) {
      parent.setPasswordError("ce champ est obligatoire");
      complet = false;
    }

    // if(ucc.getUtilisateur(vue.getUsername(),vue.getPassword()){
    if (complet && true) {

      new MenuVue();
      parent.dispose();
    }
    // gestion et affichage erreure possible rendu par l'ucc.
  }

  /**
   * méthode pour gérer l'action du bouon s'inscire
   *
   */
  private void inscription() {
    parent.dispose();
    new InscriptionVue();
  }


}
