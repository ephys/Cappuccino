package paoo.cappuccino.ihm.companydetails;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.menu.MenuEntry;
import paoo.cappuccino.ihm.menu.MenuModel;
import paoo.cappuccino.ihm.participationsearching.ParticipationSearchingViewController;
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
    
    this.guiManager.getLogger().info("[CompanyDetailsFrame]");
    
    model.setCompanyDto((ICompanyDto) menu.getTransitionObject());
    model.setContactDto(contactUcc.getContactByCompany(model.getCompanyDto().getId()));
    this.view = new CompanyDetailsView(model);
    
    this.add(view);
    
    JTable table = view.getTable();
    table.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseClicked(MouseEvent e) {

        if (e.getClickCount() == 2) {
          
          menu.setTransitionObject(model.getContactDto()[table.getSelectedRow()]);
          menu.setCurrentPage(MenuEntry.CONTACT_DETAILS);
        }

      }
    });
  }

}
