package paoo.cappuccino.ihm.companiessearch;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.ihm.menu.MenuEntry;
import paoo.cappuccino.ihm.menu.MenuModel;
import paoo.cappuccino.ucc.ICompanyUcc;
import paoo.cappuccino.ucc.IUserUcc;

@SuppressWarnings("serial")
public class CompaniesSearchViewController extends JPanel {

  private final CompaniesSearchModel model;

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
    this.model = model;


    // name
    JPanel panelName = new JPanel(new FlowLayout(FlowLayout.CENTER));
    panelName.add(new JLabel("Nom"));
    JTextField nameField = new JTextField(15);
    nameField.setText(model.getName());
    nameField.addKeyListener(new KeyAdapter() {
      @Override
      public void keyReleased(KeyEvent e) {
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
      public void keyReleased(KeyEvent e) {
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
      public void keyReleased(KeyEvent e) {
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
      public void keyReleased(KeyEvent e) {
        model.setStreet(streetField.getText());
      }
    });
    panelStreet.add(streetField);



    JPanel searchingPanel = new JPanel(new GridLayout(0, 2));
    searchingPanel.add(panelName);
    searchingPanel.add(panelPostCode);
    searchingPanel.add(panelTown);
    searchingPanel.add(panelStreet);

    CompaniesSearchView view = new CompaniesSearchView(model, companyUcc, userUcc);
    JTable table = view.getTable();
    table.setRowHeight(35);
    table.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent clickEvent) {
        if (clickEvent.getClickCount() == 2) {
          ICompanyDto company =
              (ICompanyDto) table.getModel().getValueAt(table.getSelectedRow(), 0);

          menu.setCurrentPage(MenuEntry.COMPANY_DETAILS, company);
        }
      }
    });

    JPanel panelWrapper = new JPanel(new BorderLayout());
    panelWrapper.add(view);
    panelWrapper.add(searchingPanel, BorderLayout.NORTH);
    this.add(panelWrapper);
  }
}
