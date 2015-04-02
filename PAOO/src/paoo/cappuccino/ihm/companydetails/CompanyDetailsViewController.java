package paoo.cappuccino.ihm.companydetails;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.menu.MenuModel;
import paoo.cappuccino.ucc.ICompanyUcc;
import paoo.cappuccino.ucc.IContactUcc;
import paoo.cappuccino.ucc.IUserUcc;

@SuppressWarnings("serial")
public class CompanyDetailsViewController extends JPanel {

  private CompanyDetailsModel model;
  private MenuModel menu;
  private IUserUcc userUcc;
  private IContactUcc contactUcc;
  private IGuiManager guiManager;
  private CompanyDetailsView view;

  public CompanyDetailsViewController(CompanyDetailsModel model, MenuModel menu,
      IUserUcc userUcc, IContactUcc contactUcc, IGuiManager guiManager) {

    this.model = model;
    this.menu = menu;
    this.userUcc = userUcc;
    this.contactUcc = contactUcc;
    this.guiManager = guiManager;
    
    model.setCompanyDto((ICompanyDto) menu.getTransitionObject());
    model.setContactDto(contactUcc.getContactByCompany(model.getCompanyDto().getId()));
    this.view = new CompanyDetailsView(model);
    
    model.fireDispatchChangeEvent();
    
    this.add(view);
  }

}
