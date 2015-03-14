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
import paoo.cappuccino.ihm.login.LoginFrame;
import paoo.cappuccino.ihm.util.IhmConstants;
import paoo.cappuccino.ihm.util.JLabelFont;
import paoo.cappuccino.ihm.util.MenuState;

public class MenuViewController extends JPanel {

  public MenuViewController(MenuModel model, IGuiManager manager) {
    super(new BorderLayout());

    // buttons //
    JButton deconection = new JButton("déconexion");
    deconection.addActionListener(e -> {
      manager.openFrame(LoginFrame.class);
    });
    JButton accueil = new JButton("Accueil");
    accueil.addActionListener(e -> {
      model.setState(MenuState.ACCUEIL);
    });
    JButton participationButton = new JButton("Participations");
    participationButton.addActionListener(e -> {
      model.setState(MenuState.RECH_PARTICIPATION);
    });
    JButton contactButton = new JButton("Pers. de Contact");
    contactButton.addActionListener(e -> {
      model.setState(MenuState.RECH_PERS_CONTACT);
    });
    JButton entrepriseButton = new JButton("Entreprises");
    entrepriseButton.addActionListener(e -> {
      model.setState(MenuState.RECH_ENTREPRISE);
    });
    JButton companiesSelectButton = new JButton("Sélèc. entreprises");
    companiesSelectButton.addActionListener(e -> {
      model.setState(MenuState.SELEC_ENTREPRISES);
    });
    JButton newDayButton = new JButton("Créer journée");
    newDayButton.addActionListener(e -> {
      model.setState(MenuState.CREER_JOURNEE);
    });

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


    JPanel search = new JPanel(new BorderLayout());
    JPanel searchCenter = new JPanel(new GridLayout(3, 0));
    searchCenter.add(participationButton);
    searchCenter.add(contactButton);
    searchCenter.add(entrepriseButton);
    search.add(searchLabelPanel, BorderLayout.NORTH);
    search.add(searchCenter);

    JPanel day = new JPanel(new BorderLayout());
    JPanel dayCenter = new JPanel(new GridLayout(2, 0));
    dayCenter.add(companiesSelectButton);
    dayCenter.add(newDayButton);
    day.add(dayCenter);
    day.add(dayLabelPanel, BorderLayout.NORTH);

    controls.add(accueil, gbc);

    controls.add(search, gbc);
    controls.add(day, gbc);



    // si admin
    // button admin
    JButton newEntrepriseButton = new JButton("Créer entreprise");
    newEntrepriseButton.addActionListener(e -> {
      model.setState(MenuState.CREER_ENTREPRISE);
    });
    JButton newContactButton = new JButton("Créer pers. de contact");
    newContactButton.addActionListener(e -> {
      model.setState(MenuState.CREER_PERS_CONTACT);
    });

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
    west.setBorder(BorderFactory
        .createMatteBorder(0, 0, 0, 2, Color.BLACK));

    this.add(west, BorderLayout.WEST);
    // end buttons //

    JPanel titreEnbedFrame = new JPanel();
    this.add(new MenuView(model, deconection, titreEnbedFrame),
        BorderLayout.NORTH);
    JPanel enbedFrame = new JPanel(new BorderLayout());
    enbedFrame.add(titreEnbedFrame, BorderLayout.NORTH);
    this.add(enbedFrame);
  }
}
