package paoo.cappuccino.ihm.menu;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.util.BaseFrame;
import paoo.cappuccino.ucc.IBusinessDayUcc;
import paoo.cappuccino.ucc.ICompanyUcc;
import paoo.cappuccino.ucc.IContactUcc;
import paoo.cappuccino.ucc.IUserUcc;

public class MenuFrame extends BaseFrame {


  private IUserDto connectedUser = null;

  /**
   * Creates a new frame for the login gui.
   */
  @Inject
  public MenuFrame(IUserUcc userUcc, IBusinessDayUcc businessDayUcc,
      ICompanyUcc companyUcc, IContactUcc contactUcc,
      IGuiManager guiManager) {
    super("Cappuccino", 950, 600, guiManager);
    this.setResizable(false);

    this.add(new MenuViewController(new MenuModel(connectedUser, userUcc,
        businessDayUcc, companyUcc, contactUcc), guiManager));
    this.setVisible(true);
  }

  /**
   * set the connected user
   *
   * @param user the connected user
   */

  public void setConnectedUser(IUserDto user) {
    this.connectedUser = user;
  }
}
