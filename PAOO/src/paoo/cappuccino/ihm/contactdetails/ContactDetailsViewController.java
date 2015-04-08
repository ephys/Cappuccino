package paoo.cappuccino.ihm.contactdetails;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import paoo.cappuccino.business.dto.IContactDto;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.menu.MenuModel;
import paoo.cappuccino.ucc.IContactUcc;

@SuppressWarnings("serial")
public class ContactDetailsViewController extends JPanel {
  private ContactDetailsModel model;
  private MenuModel menu;
  private IContactUcc contactUcc;
  private IGuiManager guiManager;
  private ContactDetailsView view;

  public ContactDetailsViewController(ContactDetailsModel model, MenuModel menu,
      IContactUcc contactUcc, IGuiManager guiManager) {

    this.model = model;
    this.menu = menu;
    this.contactUcc = contactUcc;
    this.guiManager = guiManager;

    this.guiManager.getLogger().info("[ContactDetailsFrame]");
   
    IContactDto contactDto = (IContactDto)menu.getTransitionObject();
    model.setContactDto(contactDto);
    JButton button = new JButton("Renseigner invalide");
    
   
    if(!contactDto.isEmailValid()){
      
      model.setInvalidMail(true);
      button.setEnabled(false);
    }
    this.view = new ContactDetailsView(model,button);
    

   button.addActionListener(e->{
     
      contactUcc.setMailInvalid(contactDto);
      model.setInvalidMail(true);
      button.setEnabled(false);
   });
   
    this.add(view);
    
  }
}
