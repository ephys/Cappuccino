package paoo.cappuccino.ihm.login;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TestView extends JPanel implements ChangeListener {

  private LoginModel model;

  public TestView(LoginModel model) {
    model.addChangeListener(this);
    this.model = model;
  }

  @Override
  public void stateChanged(ChangeEvent e) {
    // Update view using model
  }
}
