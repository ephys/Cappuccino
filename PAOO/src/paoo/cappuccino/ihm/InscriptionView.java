package paoo.cappuccino.ihm;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import paoo.cappuccino.ihm.utils.ConstantesIHM;
import paoo.cappuccino.ihm.utils.JPanelPasswordError;
import paoo.cappuccino.ihm.utils.JPanelTextError;

/**
 * View for the inscription ihm
 *
 * @author Opsomer Mathias
 *
 */
public class InscriptionView extends JFrame implements ChangeListener {


  private static final long serialVersionUID = -7334876606967058112L;
  private ErrorModele modele;
  private JPanelTextError username, lastName, firstName, mail;
  private JPanelPasswordError pwd1, pwd2;

  /**
   * Constructeur
   */
  InscriptionView(ErrorModele modele) {
    this.setTitle("Inscription");
    this.modele = modele;
    modele.addChangeListener(this);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setSize(450, 600);
    this.setLocationRelativeTo(null);
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBorder(BorderFactory.createEmptyBorder(ConstantesIHM.LGap, ConstantesIHM.MGap, 0,
        ConstantesIHM.MGap));

    JPanel fields = new JPanel(new GridLayout(2, 0, 0, ConstantesIHM.MGap));

    username = new JPanelTextError("Nom d'utilisateur");
    fields.add(username);
    pwd1 = new JPanelPasswordError("Mot de passe");
    fields.add(pwd1);
    pwd2 = new JPanelPasswordError("Confirmer mot de passe");
    fields.add(pwd2);
    firstName = new JPanelTextError("Nom");
    fields.add(firstName);
    lastName = new JPanelTextError("Prenom");
    fields.add(lastName);
    mail = new JPanelTextError("Email");
    fields.add(mail);


    mainPanel.add(fields);
    mainPanel.add(new InscriptionController(this, modele), BorderLayout.SOUTH);
    this.add(mainPanel);
    this.setVisible(true);
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
   */
  @Override
  public void stateChanged(ChangeEvent arg0) {
    // TODO Auto-generated method stub

  }
}
