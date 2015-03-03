package paoo.cappuccino.ihm;

import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.ihm.utils.MenuState;

/**
 * la classe du modele de l'ihm menu
 *
 * @author Opsomer Mathias
 *
 */
public class MenuModele {
  /**
   * the user connected
   */
  private IUserDto user;
  /**
   * the sate of the ihm
   */
  private MenuState state;
  /**
   * collection to keep all the view abonned to the model
   */
  private ArrayList<ChangeListener> listeners = new ArrayList<ChangeListener>();

  /**
   * constructeur avec l'utilisateur connecter (le rend donc immuable)
   */
  public MenuModele(IUserDto user2) {
    user = user2;
  }

  /**
   * abonne un change listener à la collection
   *
   * @param l le changelistener à ajouter
   */
  public void addChangeListener(ChangeListener l) {
    listeners.add(l);
  }

  /**
   * retire un change listener à la collection
   *
   * @param l le changelistener à retirer
   */
  public void removeChangeListener(ChangeListener l) {
    listeners.remove(l);
  }

  /**
   * méthode pour appeler toute les methode stateChanged sur les changelistener abonné
   *
   * @param e la source du changement
   */
  private void trigger(ChangeEvent e) {
    for (ChangeListener changeListener : listeners) {
      changeListener.stateChanged(e);
    }
  }


  /**
   * change l'état dans lequel le menu se trouve
   *
   * @param state le nouvel état
   */
  public void changeState(MenuState state) {
    this.state = state;
    trigger(new ChangeEvent(this));
  }


  /**
   * getUser l'utilisateur connecté
   *
   * @return IUserDto l'utilisateur connecté
   */
  public IUserDto getUser() {
    return user;
  }

  /**
   * getState l'etat courrant
   *
   * @return MenuState l'état courrant
   */
  public MenuState getState() {
    return state;
  }



}
