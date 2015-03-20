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

  private final IGuiManager guiManager;
  private final ViewControllerFactory guiFactory;
  private final MenuModel menuModel;
  private IUserDto loggedUser;

  /**
   * Creates a new frame for the login gui.
   */
  @Inject
  public MenuFrame(IUserUcc userUcc, IBusinessDayUcc businessDayUcc, ICompanyUcc companyUcc,
                   IContactUcc contactUcc, IGuiManager guiManager) {
    super("Cappuccino", 950, 600, guiManager);

    this.menuModel = new MenuModel(this);
    this.guiManager = guiManager;
    this.guiFactory = new ViewControllerFactory(userUcc, businessDayUcc, companyUcc,
                                                contactUcc, menuModel, guiManager);
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
    this.add(new MenuViewController(guiManager, menuModel, guiFactory));
    this.setVisible(true);
  }
}
