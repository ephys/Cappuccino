package paoo.cappuccino.ihm.menu;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.core.injector.NoCache;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.util.BaseFrame;
import paoo.cappuccino.ucc.IBusinessDayUcc;
import paoo.cappuccino.ucc.ICompanyUcc;
import paoo.cappuccino.ucc.IContactUcc;
import paoo.cappuccino.ucc.IUserUcc;

@NoCache
public class MenuFrame extends BaseFrame {

  private final IUserUcc userUcc;
  private final IBusinessDayUcc businessDayUcc;
  private final ICompanyUcc companyUcc;
  private final IContactUcc contactUcc;
  private final IGuiManager guiManager;
  private IUserDto loggedUser;

  /**
   * Creates a new frame for the login gui.
   */
  @Inject
  public MenuFrame(IUserUcc userUcc, IBusinessDayUcc businessDayUcc, ICompanyUcc companyUcc,
      IContactUcc contactUcc, IGuiManager guiManager) {
    super("Cappuccino", 950, 600, guiManager);
    this.userUcc = userUcc;
    this.businessDayUcc = businessDayUcc;
    this.companyUcc = companyUcc;
    this.contactUcc = contactUcc;
    this.guiManager = guiManager;
  }

  IUserDto getLoggedUser() {
    return loggedUser;
  }

  /**
   * Sets the user currently logged in.
   *
   * @param user the logged user.
   */
  public void setLoggedUser(IUserDto user) {
    this.loggedUser = user;

    // we need to have the user before rendering.
    setupDisplay();
  }

  private void setupDisplay() {

    MenuModel model = new MenuModel(this);
    MenuViewController controller =
        new MenuViewController(userUcc, businessDayUcc, companyUcc, contactUcc, guiManager, model,
            guiManager);

    this.add(controller);

    this.setVisible(true);
  }
}
