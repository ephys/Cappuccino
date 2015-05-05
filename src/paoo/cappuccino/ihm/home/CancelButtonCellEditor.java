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

  private static final long serialVersionUID = 6445724856534424146L;
  private IParticipationDto participation;
  private boolean isCancelled;

  /**
   * Creates a toggle cell editor.
   * 
   * @param button The button to display while editing, feel free to attach event listeners to it.
   */
  public CancelButtonCellEditor(JButton button) {
    super(new JCheckBox());

    editorComponent = button;
    delegate = new EditorDelegate() {

      private static final long serialVersionUID = 6350302015024530674L;

      public void setValue(Object value) {
        participation = (IParticipationDto) value;

        isCancelled = participation.isCancelled();

        button
            .setEnabled(participation.getState() != IParticipationDto.State.DECLINED
                && participation.getState() != IParticipationDto.State.INVITED
                && !isCancelled);

        button.setText(isCancelled ? "Annulée" : "Annuler");
      }

      public Object getCellEditorValue() {
        return participation;
      }
    };

    button.addActionListener(delegate);
  }
}
