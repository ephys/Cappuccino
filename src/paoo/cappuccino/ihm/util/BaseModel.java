package paoo.cappuccino.ihm.util;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Model implementing the methods shared by every other model.
 *
 * @author Guylian Cox
 */
public abstract class BaseModel {

  private List<ChangeListener> changeListeners = new ArrayList<>(1);

  /**
   * Registers a listener to notify when the model changes.
   *
   * @param listener A change listener.
   * @return true: the listener was added.
   */
  public boolean addChangeListener(ChangeListener listener) {
    return changeListeners.add(listener);
  }

  /**
   * Unregisters a change listener.
   *
   * @param listener The change listener to unregister.
   * @return true: the listener was removed.
   */
  public boolean removeChangeListener(ChangeListener listener) {
    return changeListeners.remove(listener);
  }

  /**
   * Notifies every listener of a change in the model with the source of the change being set to the
   * model itself.
   */
  protected void dispatchChangeEvent() {
    dispatchChangeEvent(new ChangeEvent(this));
  }

  /**
   * Notifies every listener of a change in the model using a given change event.
   *
   * @param event The change event to dispatch.
   */
  protected void dispatchChangeEvent(ChangeEvent event) {
    for (ChangeListener listener : changeListeners) {
      listener.stateChanged(event);
    }
  }
}
