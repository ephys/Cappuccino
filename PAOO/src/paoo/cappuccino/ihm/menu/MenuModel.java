package paoo.cappuccino.ihm.menu;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JPanel;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.ihm.accueil.AccueilModel;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.util.BaseModel;
import paoo.cappuccino.ihm.util.exception.GuiException;
import paoo.cappuccino.ucc.IBusinessDayUcc;
import paoo.cappuccino.ucc.ICompanyUcc;
import paoo.cappuccino.ucc.IContactUcc;
import paoo.cappuccino.ucc.IUserUcc;

/**
 * Model used to.
 */
public class MenuModel extends BaseModel {

  private final MenuFrame containingFrame;
  private final IGuiManager guiManager;
  private MenuEntry currentEntry;
  private JPanel currentViewController;

  public MenuModel(MenuFrame containingFrame, IUserUcc userUcc,
                   IBusinessDayUcc businessDayUcc, ICompanyUcc companyUcc,
                   IContactUcc contactUcc, IGuiManager guiManager) {

    this.containingFrame = containingFrame;
    this.guiManager = guiManager;

    MenuEntry.HOME.setModel(new AccueilModel(companyUcc, businessDayUcc));
    //TODO the other models;
  }

  /**
   * Controls the main view to display. Don't switch view if same view demanded
   *
   * @param page the page to open.
   */
  public void changePage(MenuEntry page) {
    if (page == this.currentEntry) {
      return;
    }

    this.currentEntry = page;

    if (page.getModel() == null) {
      throw new IllegalArgumentException(
          page.getTitle()
          + "'s model is null, either this is a bug or it hasn't been implemented yet.");
    }

    if (page.getViewController() == null) {
      throw new IllegalArgumentException(
          page.getTitle()
          + "'s view controller is null, either this is a bug or it hasn't been implemented yet.");
    }

    this.currentViewController = (JPanel) createViewController(page.getViewController(),
                                                               page.getModel());

    dispatchChangeEvent();
  }

  private <A> A createViewController(Class<A> viewController, Object model) {
    try {
      Constructor<A> constructor = viewController.getDeclaredConstructor(model.getClass(),
                                                                         this.getClass(),
                                                                         IGuiManager.class);
      return constructor.newInstance(model, this, guiManager);
    } catch (NoSuchMethodException e) {
      throw new GuiException("The view controller '"
                             + viewController.getCanonicalName()
                             + "' does not have a constructor with the "
                             + "arguments 'model, menu model, gui manager'.");
    } catch (InvocationTargetException | IllegalAccessException | InstantiationException e) {
      throw new GuiException(
          "An exception occured while creating the view controller '" + viewController
              .getCanonicalName() + "'", e);
    }
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
}
