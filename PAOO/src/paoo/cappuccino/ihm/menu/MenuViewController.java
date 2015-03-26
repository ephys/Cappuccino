package paoo.cappuccino.ihm.menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.util.IhmConstants;
import paoo.cappuccino.ihm.util.JLabelFont;

public class MenuViewController extends MenuView {

  /**
   * Creates a new Menu View Controller.
   *
   * @param model The view model.
   * @param guiManager The manager which opened this gui.
   * @param viewFactory The factory used to create the main view.
   */
  public MenuViewController(IGuiManager guiManager, MenuModel model,
      ViewControllerFactory viewFactory) {
    super(model, guiManager, viewFactory);

    // buttons //
    JButton home = new JButton("Accueil");
    home.addActionListener(e -> model.setCurrentPage(MenuEntry.HOME));

    JButton participationButton = new JButton("Participations");
    participationButton
        .addActionListener(e -> model.setCurrentPage(MenuEntry.SEARCH_PARTICIPATION));

    JButton contactButton = new JButton("Contacts");
    contactButton.addActionListener(e -> model.setCurrentPage(MenuEntry.SEARCH_CONTACT));

    JButton companyButton = new JButton("Entreprises");
    companyButton.addActionListener(e -> model.setCurrentPage(MenuEntry.SEARCH_COMPANY));

    JButton companiesSelectButton = new JButton("Sélèc. entreprises");
    companiesSelectButton.addActionListener(e -> model.setCurrentPage(MenuEntry.SELECT_COMPANY));

    JButton newDayButton = new JButton("Créer journée");
    newDayButton.addActionListener(e -> model.setCurrentPage(MenuEntry.CREATE_BDAY));

    JButton registerAttendanceButton = new JButton("Enregistrer présence");
    registerAttendanceButton.addActionListener(e -> model.setCurrentPage(MenuEntry.ATTENDANCE));

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

    JPanel dayCenter = new JPanel(new GridLayout(3, 0));
    dayCenter.add(companiesSelectButton);
    dayCenter.add(newDayButton);
    dayCenter.add(registerAttendanceButton);

    JPanel day = new JPanel(new BorderLayout());
    day.add(dayCenter);
    day.add(dayLabelPanel, BorderLayout.NORTH);

    controls.add(home, gbc);
    controls.add(search, gbc);
    controls.add(day, gbc);

    // si admin
    // button admin
    JButton newEntrepriseButton = new JButton("Créer entreprise");
    newEntrepriseButton.addActionListener(e -> model.setCurrentPage(MenuEntry.CREATE_COMPANY));
    JButton newContactButton = new JButton("Créer contact");
    newContactButton.addActionListener(e -> model.setCurrentPage(MenuEntry.CREATE_CONTACT));

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
}
