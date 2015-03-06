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
 * View for the connection ihm
 *
 * @author Opsomer Mathias
 */
public class ConnectionView extends JFrame implements ChangeListener {

  private static final long serialVersionUID = -5349463202389478061L;
  private JPanelTextError panelUsername;
  private JPanelPasswordError panelPassword;
  private ErrorModele modele;

  /**
   * Constructeur
   */
  public ConnectionView(ErrorModele modele) {
    this.setTitle("Connexion");
    this.modele = modele;
    modele.addChangeListener(this);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setSize(400, 240);
    this.setResizable(false);
    this.setLocationRelativeTo(null);
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBorder(BorderFactory.createEmptyBorder(ConstantesIHM.LGap, ConstantesIHM.MGap, 0,
        ConstantesIHM.MGap));

    JPanel fields = new JPanel(new GridLayout(2, 0, 0, ConstantesIHM.MGap));

    panelUsername = new JPanelTextError("Nom d'utilisateur");
    fields.add(panelUsername);
    panelPassword = new JPanelPasswordError("Mot de passe");
    fields.add(panelPassword);

    mainPanel.add(fields);
    mainPanel.add(new ConnectionController(this, modele), BorderLayout.SOUTH);
    this.add(mainPanel);
    this.setVisible(true);
  }

  /**
   * Get the input from the JPanelTextError
   */
  public String getUsername() {
    return panelUsername.getInput();
  }

  /**
   * Get the input from the JPanelPasswordError
   */
  public char[] getPassword() {
    return panelPassword.getInput();// gerer le password ici
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
   */
  @Override
  public void stateChanged(ChangeEvent arg0) {
    panelPassword.setError(modele.getPasswordError());
    panelUsername.setError(modele.getUsernameError());
  }
}
