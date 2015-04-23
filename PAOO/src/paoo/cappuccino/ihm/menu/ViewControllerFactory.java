package paoo.cappuccino.ihm.menu;

import java.awt.Component;

import paoo.cappuccino.ihm.attendance.AttendanceModel;
import paoo.cappuccino.ihm.attendance.AttendanceViewController;
import paoo.cappuccino.ihm.companydetails.CompanyDetailsModel;
import paoo.cappuccino.ihm.companydetails.CompanyDetailsViewController;
import paoo.cappuccino.ihm.companyselection.CompanySelectionModel;
import paoo.cappuccino.ihm.companyselection.CompanySelectionViewController;
import paoo.cappuccino.ihm.contactdetails.ContactDetailsModel;
import paoo.cappuccino.ihm.contactdetails.ContactDetailsViewController;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.home.HomeModel;
import paoo.cappuccino.ihm.home.HomeViewController;
import paoo.cappuccino.ihm.newbusinessday.NewBusinessDayViewController;
import paoo.cappuccino.ihm.newcompany.NewCompanyModel;
import paoo.cappuccino.ihm.newcompany.NewCompanyViewController;
import paoo.cappuccino.ihm.newcontact.NewContactModel;
import paoo.cappuccino.ihm.newcontact.NewContactViewController;
import paoo.cappuccino.ihm.searchcompanies.CompaniesSearchModel;
import paoo.cappuccino.ihm.searchcompanies.CompaniesSearchViewController;
import paoo.cappuccino.ihm.searchcontacts.ContactSearchModel;
import paoo.cappuccino.ihm.searchcontacts.ContactSearchViewController;
import paoo.cappuccino.ihm.searchparticipations.ParticipationSearchModel;
import paoo.cappuccino.ihm.searchparticipations.ParticipationSearchViewController;
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

  private final NewCompanyModel modelNewCompany = new NewCompanyModel();
  private final NewContactModel modelNewContact = new NewContactModel();
  private final AttendanceModel modelAttendance = new AttendanceModel();
  private final CompanySelectionModel modelCompanySelection =
      new CompanySelectionModel();
  private final ParticipationSearchModel modelParticipationSearching =
      new ParticipationSearchModel();
  private final HomeModel modelHome = new HomeModel();
  private final CompanyDetailsModel modelCompanyDetails =
      new CompanyDetailsModel();
  private final ContactDetailsModel modelContactDetails =
      new ContactDetailsModel();
  private final CompaniesSearchModel modelSearchCompanies =
      new CompaniesSearchModel();
  private final ContactSearchModel modelSearchContact =
      new ContactSearchModel();

  /**
   * Creates the view factory with all the dependencies required by the views.
   */
  public ViewControllerFactory(IUserUcc userUcc,
      IBusinessDayUcc businessDayUcc, ICompanyUcc companyUcc,
      IContactUcc contactUcc, MenuModel menuModel, IGuiManager guiManager) {
    this.userUcc = userUcc;
    this.businessDayUcc = businessDayUcc;
    this.companyUcc = companyUcc;
    this.contactUcc = contactUcc;
    this.menuModel = menuModel;
    this.guiManager = guiManager;
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
        modelHome.init(menuModel.getTransitionObjects());
        return new HomeViewController(modelHome, menuModel, guiManager,
            businessDayUcc, companyUcc);

      case SELECT_COMPANY:
        modelCompanySelection.init(menuModel.getTransitionObjects());
        return new CompanySelectionViewController(modelCompanySelection,
            menuModel, businessDayUcc, companyUcc, guiManager, contactUcc);

      case CREATE_COMPANY:
        return new NewCompanyViewController(modelNewCompany, menuModel,
            guiManager, companyUcc);

      case CREATE_CONTACT:
        return new NewContactViewController(modelNewContact, guiManager,
            contactUcc, companyUcc, menuModel);

      case CREATE_BDAY:
        return new NewBusinessDayViewController(menuModel, guiManager,
            businessDayUcc);

      case ATTENDANCE:
        return new AttendanceViewController(modelAttendance, menuModel,
            companyUcc, businessDayUcc, contactUcc);

      case SEARCH_PARTICIPATION:
        return new ParticipationSearchViewController(
            modelParticipationSearching, menuModel, businessDayUcc,
            companyUcc);

      case COMPANY_DETAILS:
        modelCompanyDetails.init(menuModel.getTransitionObjects());
        return new CompanyDetailsViewController(modelCompanyDetails,
            menuModel, contactUcc, userUcc, businessDayUcc, companyUcc);

      case CONTACT_DETAILS:
        modelContactDetails.init(menuModel.getTransitionObjects());
        return new ContactDetailsViewController(modelContactDetails,
            menuModel, contactUcc, companyUcc);
      case SEARCH_COMPANY:
        return new CompaniesSearchViewController(modelSearchCompanies,
            menuModel, companyUcc, userUcc);

      case SEARCH_CONTACT:
        return new ContactSearchViewController(modelSearchContact,
            menuModel, companyUcc, contactUcc);

      default:
        throw new UnsupportedOperationException("Could not open page \""
            + page.getTitle() + "\": Not yet implemented.");
    }
  }
}
