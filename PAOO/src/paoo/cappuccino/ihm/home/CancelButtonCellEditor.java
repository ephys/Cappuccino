package paoo.cappuccino.ihm.home;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import paoo.cappuccino.business.dto.IParticipationDto;

/**
 * Cell Editor for a toggle button.
 * 
 * @author Opsomer Mathias
 */
public class CancelButtonCellEditor extends DefaultCellEditor {

  private IParticipationDto participation;
  private boolean cancelled;

  /**
   * Creates a toggle cell editor.
   * 
   * @param button The button to display while editing, feel free to attach event listeners to it.
   */
  public CancelButtonCellEditor(JButton button) {
    super(new JCheckBox());

    editorComponent = button;
    delegate = new EditorDelegate() {
      public void setValue(Object value) {
        participation = (IParticipationDto) value;

        cancelled = !participation.isCancelled();

        button
            .setEnabled(participation.getState() != IParticipationDto.State.DECLINED
                && participation.getState() != IParticipationDto.State.INVITED
                && cancelled);

        button.setText(cancelled ? "Annuler" : "Annulée");
      }

      public Object getCellEditorValue() {
        return participation;
      }
    };

    button.addActionListener(delegate);
  }
}
