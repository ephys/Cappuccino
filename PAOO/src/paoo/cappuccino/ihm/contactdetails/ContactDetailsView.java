package paoo.cappuccino.ihm.contactdetails;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class ContactDetailsView extends JPanel implements ChangeListener{

  private ContactDetailsModel model;
  private JLabel invalidMessagePanel;
  
  public ContactDetailsView(ContactDetailsModel model,JButton button){
    
    super(new BorderLayout());
    
    this.model = model;
    this.invalidMessagePanel = new JLabel();
    this.invalidMessagePanel.setForeground(Color.RED);
    
    JPanel gridLayoutPanel = new JPanel(new GridLayout(6,1));
    
    JPanel panel = new JPanel(new GridLayout(2,2));
    
    panel.add(new JLabel("Nom : "));
    panel.add(new JLabel(model.getContactDto().getLastName()));
    panel.add(new JLabel());
    panel.add(new JLabel());
    gridLayoutPanel.add(panel);
    
    panel = new JPanel(new GridLayout(2,2));
    
    
    panel.add(new JLabel("Prénom : "));
    panel.add(new JLabel(model.getContactDto().getFirstName()));
    panel.add(new JLabel());
    panel.add(new JLabel());
    gridLayoutPanel.add(panel);
   
    panel = new JPanel(new GridLayout(2,3));
    
    panel.add(new JLabel("Mail : "));

    panel.add(new JLabel(model.getContactDto().getEmail()+" "));
    panel.add(button);
    panel.add(new JLabel());
    panel.add(invalidMessagePanel);
    gridLayoutPanel.add(panel);

    panel = new JPanel(new GridLayout(2,2));
    
    panel.add(new JLabel("Téléphone : "));
    panel.add(new JLabel(model.getContactDto().getPhone()));
    panel.add(new JLabel());
    panel.add(new JLabel());
    gridLayoutPanel.add(panel);
    
    panel = new JPanel(new GridLayout(2,2));
    
    panel.add(new JLabel("Entreprise : "));
    panel.add(new JLabel(""+model.getContactDto().getCompany()));
    panel.add(new JLabel());
    panel.add(new JLabel());
    gridLayoutPanel.add(panel);
    
    this.add(new JPanel(),BorderLayout.NORTH);
    this.add(gridLayoutPanel);
    
    this.model.addChangeListener(this);
    
  }
  
  @Override
  public void stateChanged(ChangeEvent event) {
    
    if(model.isInvalidMail()) this.invalidMessagePanel.setText("Invalide !");
    
  }

}
