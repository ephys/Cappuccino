package paoo.cappuccino.ihm;

import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.ihm.utils.MenuState;

/**
 * Modele for the main application
 *
 * @author Opsomer Mathias
 *
 */
public class MenuModele {

  private IUserDto user;
  private MenuState state;
  private ArrayList<ChangeListener> listeners = new ArrayList<ChangeListener>();

  /**
   * Constructor
   * 
   * @param user the user connected
   */
  public MenuModele(IUserDto user) {
    this.user = user;
  }

  /**
   * Add a changeListener to the modele's collection of listeners
   *
   * @param l the changeListener to add
   */
  public void addChangeListener(ChangeListener l) {
    listeners.add(l);
  }

  /**
   * Remove a changeListener to the modele's collection of listeners
   *
   * @param l the changeListener to Remove
   */
  public void removeChangeListener(ChangeListener l) {
    listeners.remove(l);
  }

  /**
   * Methode to call all the listeners registred
   *
   * @param e origin of the event
   */
  private void trigger(ChangeEvent e) {
    for (ChangeListener changeListener : listeners) {
      changeListener.stateChanged(e);
    }
  }


  /**
   * Change menu state
   *
   * @param MenuState the new State
   */
  public void changeState(MenuState state) {
    this.state = state;
    trigger(new ChangeEvent(this));
  }


  /**
   * Get the connected user
   *
   * @return IUserDto the connected user
   */
  public IUserDto getUser() {
    return user;
  }

  /**
   * Get current menu state
   *
   * @return MenuState the current menu state
   */
  public MenuState getState() {
    return state;
  }



}
