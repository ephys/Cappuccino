package paoo.cappuccino.ihm.menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.util.IhmConstants;
import paoo.cappuccino.ihm.util.JLabelFont;
import paoo.cappuccino.ihm.util.exception.GuiException;
import paoo.cappuccino.ucc.IBusinessDayUcc;
import paoo.cappuccino.ucc.ICompanyUcc;
import paoo.cappuccino.ucc.IContactUcc;
import paoo.cappuccino.ucc.IUserUcc;


public class MenuViewController extends MenuView {
  private final MenuModel model;
  private final IGuiManager manager;
  private IBusinessDayUcc businessDayUcc;
  private ICompanyUcc companyUcc;

  public MenuViewController(IUserUcc userUcc, IBusinessDayUcc businessDayUcc,
      ICompanyUcc companyUcc, IContactUcc contactUcc, IGuiManager guiManager, MenuModel model,
      IGuiManager manager) {
    super(model, manager);
    this.model = model;
    this.manager = manager;

    // TODO mettre les autres ici (et enlever ceux de la frame) + les getters
    this.businessDayUcc = businessDayUcc;
    this.companyUcc = companyUcc;

    changePage(MenuEntry.HOME);
    // buttons //
    JButton home = new JButton("Accueil");
    home.addActionListener(e -> changePage(MenuEntry.HOME));

    JButton participationButton = new JButton("Participations");
    participationButton.addActionListener(e -> changePage(MenuEntry.SEARCH_PARTICIPATION));

    JButton contactButton = new JButton("Contacts");
    contactButton.addActionListener(e -> changePage(MenuEntry.SEARCH_CONTACT));

    JButton companyButton = new JButton("Entreprises");
    companyButton.addActionListener(e -> changePage(MenuEntry.SEARCH_COMPANY));

    JButton companiesSelectButton = new JButton("Sélèc. entreprises");
    companiesSelectButton.addActionListener(e -> changePage(MenuEntry.SELEC_COMPANY));

    JButton newDayButton = new JButton("Créer journée");
    newDayButton.addActionListener(e -> changePage(MenuEntry.CREATE_BDAY));

    // JPanel
    JPanel searchLabelPanel = new JPanel();
    JLabelFont searchLabel = new JLabelFont("Recherches", 16);
    searchLabelPanel.add(searchLabel);
    searchLabelPanel.setBackground(IhmConstants.LIGHT_BLUE);

    JPanel dayLabelPanel = new JPanel();
    JLabelFont dayLabel = new JLabelFont("Gérer journées", 16);
    dayLabelPanel.add(dayLabel);
    dayLabelPanel.setBackground(IhmConstants.LIGHT_BLUE);

    JPanel controls = new JPanel(new GridBagLayout());
    controls.setAlignmentY(Component.TOP_ALIGNMENT);
    GridBagConstraints gbc = new GridBagConstraints();

    gbc.gridx = 0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(0, 0, 13, 0);

    JPanel searchCenter = new JPanel(new GridLayout(3, 0));
    searchCenter.add(participationButton);
    searchCenter.add(contactButton);
    searchCenter.add(companyButton);

    JPanel search = new JPanel(new BorderLayout());
    search.add(searchLabelPanel, BorderLayout.NORTH);
    search.add(searchCenter);

    JPanel dayCenter = new JPanel(new GridLayout(2, 0));
    dayCenter.add(companiesSelectButton);
    dayCenter.add(newDayButton);

    JPanel day = new JPanel(new BorderLayout());
    day.add(dayCenter);
    day.add(dayLabelPanel, BorderLayout.NORTH);

    controls.add(home, gbc);
    controls.add(search, gbc);
    controls.add(day, gbc);

    // si admin
    // button admin
    JButton newEntrepriseButton = new JButton("Créer entreprise");
    newEntrepriseButton.addActionListener(e -> changePage(MenuEntry.CREATE_COMPANY));
    JButton newContactButton = new JButton("Créer contact");
    newContactButton.addActionListener(e -> changePage(MenuEntry.CREATE_CONTACT));

    // labels admin
    JPanel entrepriseLabelPanel = new JPanel();
    JLabelFont entrepriseLabel = new JLabelFont("Gérer entreprises", 16);
    entrepriseLabelPanel.add(entrepriseLabel);
    entrepriseLabelPanel.setBackground(IhmConstants.LIGHT_BLUE);

    JPanel companies = new JPanel(new BorderLayout());
    JPanel companiesCenter = new JPanel(new GridLayout(2, 0));
    companiesCenter.add(newEntrepriseButton);
    companiesCenter.add(newContactButton);
    controls.add(companies, gbc);
    companies.add(entrepriseLabelPanel, BorderLayout.NORTH);
    companies.add(companiesCenter);

    controls.add(companies, gbc);

    JPanel west = new JPanel(new BorderLayout());
    west.add(controls, BorderLayout.NORTH);
    west.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.BLACK));

    this.add(west, BorderLayout.WEST);
    // end buttons //

    // this.add(new MenuView(model, disconnection), BorderLayout.NORTH);
  }

  /**
   * Controls the main view to display. Don't switch view if same view demanded
   *
   * @param page the page to open.
   */
  public void changePage(MenuEntry page) {
    if (page == model.getCurrentPageEntry()) {
      return;
    }

    model.setCurrentEntry(page);

    if (page.getModel() == null) {
      throw new IllegalArgumentException(page.getTitle()
          + "'s model is null, either this is a bug or it hasn't been implemented yet.");
    }

    if (page.getViewController() == null) {
      throw new IllegalArgumentException(page.getTitle()
          + "'s view controller is null, either this is a bug or it hasn't been implemented yet.");
    }

    if (model.getCurrentPageInstance() != null)
      model.getCurrentPageInstance().setVisible(false);

    model.setCurrentViewController((JPanel) createViewController(page.getViewController(),
        page.getModel()));

  }

  private <A> A createViewController(Class<A> viewController, Object modelPage) {
    try {
      Constructor<A> constructor =
          viewController.getDeclaredConstructor(modelPage.getClass(), model.getClass(),
              IGuiManager.class, MenuViewController.class);
      return constructor.newInstance(modelPage, model, manager, this);
    } catch (NoSuchMethodException e) {
      throw new GuiException("The view controller '" + viewController.getCanonicalName()
          + "' does not have a constructor with the "
          + "arguments 'model, MenuModel, gui manager'.");
    } catch (InvocationTargetException | IllegalAccessException | InstantiationException e) {
      throw new GuiException("An exception occured while creating the view controller '"
          + viewController.getCanonicalName() + "'", e);
    }
  }

  public IBusinessDayUcc getBusinessDayUcc() {

    return this.businessDayUcc;
  }

  public ICompanyUcc getCompanyUcc() {

    return this.companyUcc;
  }
}
