package paoo.cappuccino.ihm.menu;

import java.awt.Component;

import paoo.cappuccino.ihm.accueil.AccueilModel;
import paoo.cappuccino.ihm.accueil.AccueilViewController;
import paoo.cappuccino.ihm.companySelection.CompanySelectionModel;
import paoo.cappuccino.ihm.companySelection.CompanySelectionViewController;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.newBusinessDay.NewBusinessDayController;
import paoo.cappuccino.ihm.newBusinessDay.NewBusinessDayModel;
import paoo.cappuccino.ihm.newCompany.NewCompanyModel;
import paoo.cappuccino.ihm.newCompany.NewCompanyViewController;
import paoo.cappuccino.ihm.newContact.NewContactModel;
import paoo.cappuccino.ihm.newContact.NewContactViewController;
import paoo.cappuccino.ucc.IBusinessDayUcc;
import paoo.cappuccino.ucc.ICompanyUcc;
import paoo.cappuccino.ucc.IContactUcc;
import paoo.cappuccino.ucc.IUserUcc;

/**
 * Dynamically creates views for the menu frame.
 */
public class ViewControllerFactory {

  private final IUserUcc userUcc;
  private final IBusinessDayUcc businessDayUcc;
  private final ICompanyUcc companyUcc;
  private final IContactUcc contactUcc;
  private final MenuModel menuModel;
  private final IGuiManager guiManager;

  // models
  private final NewCompanyModel modelNewCompany;
  private final NewContactModel modelNewContact;
  private final NewBusinessDayModel modelNewBusinessDay;


  /**
   * Creates the view factory with all the dependencies required by the views.
   */
  public ViewControllerFactory(IUserUcc userUcc, IBusinessDayUcc businessDayUcc,
      ICompanyUcc companyUcc, IContactUcc contactUcc, MenuModel menuModel, IGuiManager guiManager) {
    this.userUcc = userUcc;
    this.businessDayUcc = businessDayUcc;
    this.companyUcc = companyUcc;
    this.contactUcc = contactUcc;
    this.menuModel = menuModel;
    this.guiManager = guiManager;

    // initialiation Models
    modelNewCompany = new NewCompanyModel();
    modelNewContact = new NewContactModel();
    modelNewBusinessDay = new NewBusinessDayModel();
  }

  /**
   * Creates the view controller related to a given page.
   *
   * @param page The page to create a view controller for.
   * @return The new view controller.
   */
  public Component createViewController(MenuEntry page) {
    switch (page) {
      case HOME:
        return new AccueilViewController(new AccueilModel(), menuModel, guiManager, businessDayUcc);

      case SELECT_COMPANY:
        return new CompanySelectionViewController(new CompanySelectionModel(), guiManager,
            businessDayUcc);

      case CREATE_COMPANY:
        return new NewCompanyViewController(modelNewCompany, menuModel,
            guiManager, companyUcc);


      case CREATE_CONTACT:
        return new NewContactViewController(modelNewContact, menuModel, guiManager, contactUcc,
            companyUcc);

      case CREATE_BDAY:
        return new NewBusinessDayController(modelNewBusinessDay, menuModel, guiManager,
            businessDayUcc);
        // TODO: add ViewControllers.

      default:
        throw new UnsupportedOperationException("Could not open page \"" + page.getTitle()
            + "\": Not yet implemented.");
    }
  }
}
