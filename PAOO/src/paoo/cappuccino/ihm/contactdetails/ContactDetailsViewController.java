package paoo.cappuccino.ihm.contactdetails;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import paoo.cappuccino.business.dto.IContactDto;
import paoo.cappuccino.ihm.menu.MenuEntry;
import paoo.cappuccino.ihm.menu.MenuModel;
import paoo.cappuccino.ucc.ICompanyUcc;
import paoo.cappuccino.ucc.IContactUcc;

@SuppressWarnings("serial")
public class ContactDetailsViewController extends JPanel implements ChangeListener {

  private final JButton markInvalidButton = new JButton("Renseigner Invalide");

  private final ContactDetailsModel model;
  private final MenuModel menu;

  public ContactDetailsViewController(ContactDetailsModel model, MenuModel menu,
                                      IContactUcc contactUcc, ICompanyUcc companyUcc) {
    this.model = model;
    this.menu = menu;

    model.addChangeListener(this);
    this.add(new ContactDetailsView(model, markInvalidButton, companyUcc, menu));

    markInvalidButton.addActionListener(e -> {
      contactUcc.setMailInvalid(model.getContactDto());
      markInvalidButton.setEnabled(false);
    });

    stateChanged(null);
  }

  @Override
  public void stateChanged(ChangeEvent e) {
    IContactDto contact = model.getContactDto();

    if (contact == null) {
      menu.setCurrentPage(MenuEntry.HOME);
      return;
    }

    markInvalidButton.setEnabled(contact.isEmailValid());
  }
}
