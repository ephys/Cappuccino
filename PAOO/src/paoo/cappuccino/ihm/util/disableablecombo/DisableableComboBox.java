package paoo.cappuccino.ihm.util.disableablecombo;

import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

public class DisableableComboBox<A> extends JComboBox<A> {

  public DisableableComboBox(ComboBoxModel<A> aModel) {
    super(aModel);
  }

  public DisableableComboBox(A[] items) {
    super(items);
  }

  public DisableableComboBox(Vector<A> items) {
    super(items);
  }

  public DisableableComboBox() {
    super();
  }


}
