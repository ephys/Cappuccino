package paoo.cappuccino.ihm.companydetails;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import paoo.cappuccino.business.dto.ICompanyDto;

@SuppressWarnings("serial")
public class CompanyDetailsView extends JPanel {

  private ICompanyDto companyDto;

  // test
  public CompanyDetailsView(ICompanyDto companyDto) {

    super(new GridLayout(3, 1));
    this.companyDto = companyDto;
    JPanel panel = new JPanel();
    panel.add(new JLabel("Nom : " + this.companyDto.getName()));
    panel.add(new JLabel("    "));
    // TODO getCreator retourne une string au lieu d'un int dans le companyDto
    panel.add(new JLabel("Enregisteur : "));

    this.add(panel);

    panel = new JPanel();
    panel.add(new JLabel("Date de l'enregistrement : "
        + this.companyDto.getRegisterDate().toString()));

    this.add(panel);

    panel = new JPanel(new GridLayout(1, 2));

    JPanel leftPanel = new JPanel(new BorderLayout());
    JPanel rightPanel = new JPanel(new BorderLayout());

    leftPanel.add(new JLabel("Adresse : "), BorderLayout.NORTH);

    JPanel leftInnerPanel = new JPanel(new GridLayout(3, 1));

    leftInnerPanel.add(new JLabel("Rue : " + this.companyDto.getAddressStreet()));
    leftInnerPanel.add(new JLabel("Ville : " + this.companyDto.getAddressTown()));
    leftInnerPanel.add(new JLabel("Boite : " + this.companyDto.getAddressMailbox()));

    leftPanel.add(leftInnerPanel);

    rightPanel.add(new JLabel("Numero : "), BorderLayout.NORTH);
    rightPanel.add(new JLabel("Code postal : " + this.companyDto.getAddressPostcode()));

    panel.add(leftPanel);
    panel.add(rightPanel);

    this.add(panel);

  }

}
