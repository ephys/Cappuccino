package paoo.cappuccino.ihm.util;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Model implementing the methods shared by every other model.
 */
public abstract class BaseModel {
  private List<ChangeListener> changeListeners = new ArrayList<>(1);

  public boolean addChangeListener(ChangeListener listener) {
    return changeListeners.add(listener);
  }

  public boolean removeChangeListener(ChangeListener listener) {
    return changeListeners.remove(listener);
  }

  protected void dispatchChangeEvent() {
    dispatchChangeEvent(new ChangeEvent(this));
  }

  protected void dispatchChangeEvent(ChangeEvent event) {
    for (ChangeListener listener : changeListeners) {
      listener.stateChanged(event);
    }
  }
}
