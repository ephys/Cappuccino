package paoo.cappuccino.ihm.menu;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.ihm.accueil.AccueilModel;
import paoo.cappuccino.ihm.companySelection.CompanySelectionModel;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.util.BaseModel;
import paoo.cappuccino.ucc.IBusinessDayUcc;
import paoo.cappuccino.ucc.ICompanyUcc;

/**
 * Model used to.
 */
public class MenuModel extends BaseModel {

  private final MenuFrame containingFrame;
  private MenuEntry currentEntry;
  private JPanel currentViewController;

  public MenuModel(MenuFrame containingFrame) {

    // to change
    ICompanyUcc companyUcc = null;
    IBusinessDayUcc businessDayUcc = null;

    this.containingFrame = containingFrame;


    MenuEntry.HOME.setModel(new AccueilModel(companyUcc, businessDayUcc));
    MenuEntry.SELEC_COMPANY.setModel(new CompanySelectionModel());
    // TODO the other models;
  }

  /**
   * gets the MenuEntry of the currently opened page.
   */
  public MenuEntry getCurrentPageEntry() {
    return this.currentEntry;
  }

  /**
   * Returns the instance of the currently open view controller.
   */
  public JPanel getCurrentPageInstance() {
    return currentViewController;
  }

  /**
   * gets the connected user.
   */
  public IUserDto getLoggedUser() {
    return containingFrame.getLoggedUser();
  }

  public void setCurrentEntry(MenuEntry page) {
    this.currentEntry = page;
  }

  public void setCurrentViewController(JPanel controller) {
    this.currentViewController = controller;
    dispatchChangeEvent(new ChangeEvent(this));
  }
}
