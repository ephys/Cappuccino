/**
 *
 */
package paoo.cappuccino.ihm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Opsomer Mathias
 *
 *         vue de l'ihm de l'inscription
 */
public class InscriptionVue extends JFrame {
  private Font arial = new Font("Arial", Font.PLAIN, 16);
  private JPanelTextError username, textNom, textPrenom, textEmail;
  private JPanelPasswordError password1, password2;


  /**
   * constructeur
   */
  InscriptionVue() {
    this.setTitle("Inscription");
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setSize(450, 600);
    this.setLocationRelativeTo(null);

    this.setLayout(new BorderLayout());
    JPanel texts = new JPanel(new GridLayout(0, 1));
    JPanel labels = new JPanel(new GridLayout(0, 1));
    /*
     * definition labels border west
     */
    JLabel user = new JLabel("Nom d'utilisateur : ", JLabel.RIGHT);
    user.setPreferredSize(new Dimension(200, 0));
    user.setFont(arial);
    labels.add(user);

    JLabel pwd1 = new JLabel("Mot de passe : ", JLabel.RIGHT);
    pwd1.setPreferredSize(new Dimension(200, 0));
    pwd1.setFont(arial);
    labels.add(pwd1);

    JLabel pwd2 = new JLabel("Confirmer Mot de passe : ", JLabel.RIGHT);
    pwd2.setPreferredSize(new Dimension(200, 0));
    pwd2.setFont(arial);
    labels.add(pwd2);

    JLabel nom = new JLabel("Nom : ", JLabel.RIGHT);
    nom.setPreferredSize(new Dimension(200, 0));
    nom.setFont(arial);
    labels.add(nom);

    JLabel prenom = new JLabel("Prénom : ", JLabel.RIGHT);
    prenom.setPreferredSize(new Dimension(200, 0));
    prenom.setFont(arial);
    labels.add(prenom);

    JLabel email = new JLabel("Email : ", JLabel.RIGHT);
    email.setPreferredSize(new Dimension(200, 0));
    email.setFont(arial);
    labels.add(email);

    /*
     * definition textarea center
     */

    username = new JPanelTextError(13);
    JPanel userNamePanel = new JPanel(new BorderLayout());
    userNamePanel.add(username);
    texts.add(userNamePanel);

    password1 = new JPanelPasswordError(13);
    JPanel password1Panel = new JPanel(new BorderLayout());
    password1Panel.add(password1);
    texts.add(password1Panel);

    password2 = new JPanelPasswordError(13);
    JPanel password2Panel = new JPanel(new BorderLayout());
    password2Panel.add(password2);
    texts.add(password2Panel);

    textNom = new JPanelTextError(13);
    JPanel textNomPanel = new JPanel(new BorderLayout());
    textNomPanel.add(textNom);
    texts.add(textNomPanel);

    textPrenom = new JPanelTextError(13);
    JPanel textPrenomPanel = new JPanel(new BorderLayout());
    textPrenomPanel.add(textPrenom);
    texts.add(textPrenomPanel);

    textEmail = new JPanelTextError(13);
    JPanel textEmailPanel = new JPanel(new BorderLayout());
    textEmailPanel.add(textEmail);
    texts.add(textEmailPanel);

    this.add(texts);
    this.add(labels, BorderLayout.WEST);

    this.add(new InscriptionController(this), BorderLayout.SOUTH);
    this.setVisible(true);
  }

  /**
   * get text du textfield nom d'utilisateur
   *
   * @return String le nom d'utilisateur introduit
   */
  public String getUsername() {
    return username.getText();
  }

  /**
   * get text du textfield nom
   *
   * @return String le nom introduit
   */
  public String getTextNom() {
    return textNom.getText();
  }

  /**
   * get text du textfield prenom
   *
   * @return String le prenom introduit
   */
  public String getTextPrenom() {
    return textPrenom.getText();
  }

  /**
   * get text du textfield email
   *
   * @return String l'adresse mail introduit
   */
  public String getTextEmail() {
    return textEmail.getText();
  }

  /**
   * get mot de passe du passwordField mot de passe
   *
   * @return char[] le mot de passe introduit
   */
  public char[] getPassword1() {
    return password1.getPassword();
  }

  /**
   * get mot de passe du passwordField mot de passe (confirmation)
   *
   * @return char[] le mot de passe(confirmation) introduit
   */
  public char[] getPassword2() {
    return password2.getPassword();
  }
}
