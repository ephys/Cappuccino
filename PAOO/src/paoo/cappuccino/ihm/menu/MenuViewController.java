package paoo.cappuccino.ihm.menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.login.LoginFrame;
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
    JButton participation = new JButton("Participations");
    participation.addActionListener(e -> {
      model.setState(MenuState.RECH_PARTICIPATION);
    });
    JButton contact = new JButton("Pers. de Contact");
    contact.addActionListener(e -> {
      model.setState(MenuState.RECH_PERS_CONTACT);
    });
    JButton entreprise = new JButton("Entreprises");
    entreprise.addActionListener(e -> {
      model.setState(MenuState.RECH_ENTREPRISE);
    });
    JButton selecEntreprisee = new JButton("Sélèc. entreprises");
    selecEntreprisee.addActionListener(e -> {
      model.setState(MenuState.SELEC_ENTREPRISES);
    });
    JButton newDay = new JButton("Créer journée");
    newDay.addActionListener(e -> {
      model.setState(MenuState.CREER_JOURNEE);
    });

    // JPanel
    JPanel searchPanel = new JPanel();
    JLabelFont searchLabel = new JLabelFont("Recherches", 16);
    searchPanel.add(searchLabel);
    searchPanel.setBackground(Color.BLUE);

    JPanel dayPanel = new JPanel();
    JLabelFont dayLabel = new JLabelFont("Gérer journées", 16);
    dayPanel.add(dayLabel);
    dayPanel.setBackground(Color.BLUE);

    JPanel controls = new JPanel(new GridBagLayout());
    controls.setAlignmentY(Component.TOP_ALIGNMENT);
    GridBagConstraints gbc = new GridBagConstraints();

    gbc.gridx = 0;
    gbc.fill = GridBagConstraints.HORIZONTAL;

    controls.add(accueil, gbc);

    controls.add(searchPanel, gbc);
    controls.add(participation, gbc);
    controls.add(contact, gbc);
    controls.add(entreprise, gbc);

    controls.add(new JPanel(), gbc);// TODO find alternative to this montruosity
    controls.add(dayPanel, gbc);
    controls.add(selecEntreprisee, gbc);
    controls.add(newDay, gbc);

    // si admin
    // button admin
    JButton newEntreprise = new JButton("Créer entreprise");
    newEntreprise.addActionListener(e -> {
      model.setState(MenuState.CREER_ENTREPRISE);
    });
    JButton newContact = new JButton("Créer pers. de contact");
    newContact.addActionListener(e -> {
      model.setState(MenuState.CREER_PERS_CONTACT);
    });

    // labels admin
    JPanel entreprisePanel = new JPanel();
    JLabelFont entrepriseLabel = new JLabelFont("Gérer entreprises", 16);
    entreprisePanel.add(entrepriseLabel);
    entreprisePanel.setBackground(Color.BLUE);

    controls.add(new JPanel(), gbc);// TODO find alternative to this montruosity
    controls.add(entreprisePanel, gbc);
    controls.add(newEntreprise, gbc);
    controls.add(newContact, gbc);


    JPanel west = new JPanel();
    west.add(controls);
    west.setBorder(BorderFactory
        .createMatteBorder(0, 0, 0, 3, Color.BLACK));

    this.add(west, BorderLayout.WEST);
    // end buttons //

    this.add(new MenuView(model, deconection), BorderLayout.NORTH);
  }
}
