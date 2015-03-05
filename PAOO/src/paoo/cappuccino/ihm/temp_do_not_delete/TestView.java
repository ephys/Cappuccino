package paoo.cappuccino.ihm.temp_do_not_delete;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TestView extends JPanel implements ChangeListener {

  private TestModel model;

  public TestView(TestModel model) {
    model.addChangeListener(this);
    this.model = model;
  }

  @Override
  public void stateChanged(ChangeEvent e) {
    // Update view using model
  }
}
