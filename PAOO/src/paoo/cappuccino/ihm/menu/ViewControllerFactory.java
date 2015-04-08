package paoo.cappuccino.ihm.menu;

import java.awt.Component;

import paoo.cappuccino.ihm.accueil.AccueilModel;
import paoo.cappuccino.ihm.accueil.AccueilViewController;
import paoo.cappuccino.ihm.attendence.AttendanceController;
import paoo.cappuccino.ihm.attendence.AttendanceModel;
import paoo.cappuccino.ihm.companydetails.CompanyDetailsModel;
import paoo.cappuccino.ihm.companydetails.CompanyDetailsViewController;
import paoo.cappuccino.ihm.companyselection.CompanySelectionModel;
import paoo.cappuccino.ihm.companyselection.CompanySelectionViewController;
import paoo.cappuccino.ihm.contactdetails.ContactDetailsModel;
import paoo.cappuccino.ihm.contactdetails.ContactDetailsViewController;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.newbusinessday.NewBusinessDayController;
import paoo.cappuccino.ihm.newbusinessday.NewBusinessDayModel;
import paoo.cappuccino.ihm.newcompany.NewCompanyModel;
import paoo.cappuccino.ihm.newcompany.NewCompanyViewController;
import paoo.cappuccino.ihm.newcontact.NewContactModel;
import paoo.cappuccino.ihm.newcontact.NewContactViewController;
import paoo.cappuccino.ihm.participationsearching.ParticipationSearchingModel;
import paoo.cappuccino.ihm.participationsearching.ParticipationSearchingViewController;
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
  private final AttendanceModel modelAttendance;
  private final CompanySelectionModel modelCompanySelection;
  private final ParticipationSearchingModel modelParticipationSearching;
  private final AccueilModel modelAccueil;
  private final CompanyDetailsModel modelCompanyDetails;
  private final ContactDetailsModel modelContactDetails;

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
    modelAttendance = new AttendanceModel();
    modelCompanySelection = new CompanySelectionModel();
    modelParticipationSearching = new ParticipationSearchingModel();
    modelAccueil = new AccueilModel();
    modelCompanyDetails = new CompanyDetailsModel();
    modelContactDetails = new ContactDetailsModel();
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
        return new AccueilViewController(modelAccueil, menuModel, guiManager, businessDayUcc,
                                         companyUcc);

      case SELECT_COMPANY:
        return new CompanySelectionViewController(modelCompanySelection, menuModel, guiManager,
            businessDayUcc, companyUcc,contactUcc);

      case CREATE_COMPANY:
        return new NewCompanyViewController(modelNewCompany, menuModel, guiManager, companyUcc);

      case CREATE_CONTACT:
        return new NewContactViewController(modelNewContact, menuModel, guiManager, contactUcc,
            companyUcc);

      case CREATE_BDAY:
        return new NewBusinessDayController(modelNewBusinessDay, menuModel, guiManager,
            businessDayUcc);

      case ATTENDANCE:
        return new AttendanceController(modelAttendance, menuModel, guiManager, companyUcc,
            businessDayUcc, contactUcc);

      case SEARCH_PARTICIPATION:
        return new ParticipationSearchingViewController(modelParticipationSearching, menuModel,
            guiManager, businessDayUcc,companyUcc);
        
      case COMPANY_DETAILS:
        return new CompanyDetailsViewController(modelCompanyDetails,menuModel,userUcc,contactUcc,guiManager);
        
      case CONTACT_DETAILS:
        return new ContactDetailsViewController(modelContactDetails,menuModel,contactUcc,guiManager);
        // TODO: add ViewControllers.

      default:
        throw new UnsupportedOperationException("Could not open page \"" + page.getTitle()
            + "\": Not yet implemented.");
    }
  }
}
