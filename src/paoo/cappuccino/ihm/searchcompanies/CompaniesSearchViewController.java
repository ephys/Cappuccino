package paoo.cappuccino.ihm.searchcompanies;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import paoo.cappuccino.ihm.menu.MenuModel;
import paoo.cappuccino.ihm.util.JTableCompaniesViewController;
import paoo.cappuccino.ucc.ICompanyUcc;
import paoo.cappuccino.ucc.IUserUcc;

public class CompaniesSearchViewController extends JPanel {

  private static final long serialVersionUID = -7968991356894803138L;


  /**
   * Creates a view controller for the participation search view.
   *
   * @param model The model of the view.
   * @param menu The model of the menu.
   * @param companyUcc The app instance of the company ucc.
   */
  public CompaniesSearchViewController(CompaniesSearchModel model, MenuModel menu,
      ICompanyUcc companyUcc, IUserUcc userUcc) {
    super(new BorderLayout());
    // name
    JPanel panelName = new JPanel(new FlowLayout(FlowLayout.CENTER));
    panelName.add(new JLabel("Nom"));
    JTextField nameField = new JTextField(15);
    nameField.setText(model.getName());
    nameField.addKeyListener(new KeyAdapter() {
      @Override
      public void keyReleased(KeyEvent event) {
        model.setName(nameField.getText());
      }
    });
    panelName.add(nameField);

    // postCode
    JPanel panelPostCode = new JPanel(new FlowLayout(FlowLayout.CENTER));
    panelPostCode.add(new JLabel("Code postal"));
    JTextField postCodeField = new JTextField(15);
    postCodeField.setText(model.getPostCode());
    postCodeField.addKeyListener(new KeyAdapter() {
      @Override
      public void keyReleased(KeyEvent event) {
        model.setPostCode(postCodeField.getText());
      }
    });
    panelPostCode.add(postCodeField);

    // town
    JPanel panelTown = new JPanel(new FlowLayout(FlowLayout.CENTER));
    panelTown.add(new JLabel("Ville"));
    JTextField townField = new JTextField(15);
    townField.setText(model.getTown());
    townField.addKeyListener(new KeyAdapter() {
      @Override
      public void keyReleased(KeyEvent event) {
        model.setTown(townField.getText());
      }
    });
    panelTown.add(townField);

    // street
    JPanel panelStreet = new JPanel(new FlowLayout(FlowLayout.CENTER));
    panelStreet.add(new JLabel("Rue"));
    JTextField streetField = new JTextField(15);
    streetField.setText(model.getStreet());
    streetField.addKeyListener(new KeyAdapter() {
      @Override
      public void keyReleased(KeyEvent event) {
        model.setStreet(streetField.getText());
      }
    });
    panelStreet.add(streetField);

    JPanel searchingPanel = new JPanel(new GridLayout(0, 2));
    searchingPanel.add(panelName);
    searchingPanel.add(panelPostCode);
    searchingPanel.add(panelTown);
    searchingPanel.add(panelStreet);

    JTableCompaniesViewController viewController =
        new JTableCompaniesViewController(menu, model, companyUcc, userUcc);


    JPanel panelWrapper = new JPanel(new BorderLayout());
    panelWrapper.add(viewController);
    panelWrapper.add(searchingPanel, BorderLayout.NORTH);
    this.add(panelWrapper);
  }
}
