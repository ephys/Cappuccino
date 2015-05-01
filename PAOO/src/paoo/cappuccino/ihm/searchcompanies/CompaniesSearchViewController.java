package paoo.cappuccino.ihm.searchcompanies;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.ihm.menu.MenuEntry;
import paoo.cappuccino.ihm.menu.MenuModel;
import paoo.cappuccino.ihm.util.LocalizationUtil;
import paoo.cappuccino.ucc.ICompanyUcc;
import paoo.cappuccino.ucc.IUserUcc;

public class CompaniesSearchViewController extends JPanel implements ChangeListener {

  private static final long serialVersionUID = -7968991356894803138L;
  private final CompaniesSearchModel model;
  private final CompaniesSearchView view;
  private final ICompanyUcc companyUcc;
  private final IUserUcc userUcc;

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
    this.companyUcc = companyUcc;
    this.userUcc = userUcc;
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

    view = new CompaniesSearchView();
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

    model.addChangeListener(this);
    stateChanged(null);
  }

  @Override
  public void stateChanged(ChangeEvent e) {
    List<ICompanyDto> companies =
        companyUcc.searchCompanies(model.getName(), model.getPostCode(), model.getTown(),
            model.getStreet());
    buildTable(companies);
    view.stateChanged(null);
  }

  private void buildTable(List<ICompanyDto> companies) {
    DefaultTableModel tableModel = (DefaultTableModel) view.getTable().getModel();
    tableModel.setRowCount(companies.size());

    for (int i = 0; i < companies.size(); i++) {
      ICompanyDto company = companies.get(i);
      IUserDto creator = userUcc.getUserById(company.getCreator());
      String creatorName = "inconnu";
      if (creator != null) {
        creatorName = creator.getUsername();
      }

      tableModel.setValueAt(company, i, 0);
      tableModel.setValueAt(LocalizationUtil.localizeAddress(company), i, 1);
      tableModel.setValueAt(company.getRegisterDate(), i, 2);
      tableModel.setValueAt(creatorName, i, 3);
    }
  }


}
