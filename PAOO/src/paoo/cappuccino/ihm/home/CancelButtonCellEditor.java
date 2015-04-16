package paoo.cappuccino.ihm.home;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import paoo.cappuccino.business.dto.IParticipationDto;

/**
 * Cell Editor for a toggle button.
 * @author Opsomer Mathias
 */
public class CancelButtonCellEditor extends DefaultCellEditor {

  private IParticipationDto participation;
  private boolean cancelled;

  public CancelButtonCellEditor(JButton button) {
    super(new JCheckBox());

    editorComponent = button;
    delegate = new EditorDelegate() {
      public void setValue(Object value) {
        participation = (IParticipationDto) value;

        cancelled = !participation.isCancelled();

        button.setEnabled(participation.getState() != IParticipationDto.State.DECLINED
                          && participation.getState() != IParticipationDto.State.INVITED
                          && !participation.isCancelled());

        button.setText(cancelled ? "Annuler" : "Annulé");
      }

      public Object getCellEditorValue() {
        return participation;
      }
    };

    button.addActionListener(delegate);
  }
}
