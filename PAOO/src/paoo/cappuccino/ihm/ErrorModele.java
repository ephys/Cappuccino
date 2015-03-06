package paoo.cappuccino.ihm;

import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Modele of the connection
 * 
 * @author Opsomer Mathias
 */
public class ErrorModele {

  private ArrayList<ChangeListener> listeners = new ArrayList<ChangeListener>();
  private String passwordError, usernameError, password2Error, mailError, firstNameError,
      lastNameError;

  /**
   * add a listener to the modele
   * 
   * @param l the listeners to add
   */
  public void addChangeListener(ChangeListener l) {
    listeners.add(l);

  }

  /**
   * remove a listener from the modele
   *
   * @param l the listeners to remove
   */
  public void removeChangeListener(ChangeListener l) {
    listeners.remove(l);
  }

  /**
   * get the error for the password input
   * 
   * @return String the error to display
   */
  public String getPasswordError() {
    return this.passwordError;
  }

  /**
   * get the error for the unsername input
   * 
   * @return String the error to display
   */
  public String getUsernameError() {
    return this.usernameError;
  }


  /**
   * @return the password2Error
   */
  public String getPassword2Error() {
    return password2Error;
  }

  /**
   * @return the mailError
   */
  public String getMailError() {
    return mailError;
  }

  /**
   * @return the firstNameError
   */
  public String getFirstNameError() {
    return firstNameError;
  }

  /**
   * @return the lastNameError
   */
  public String getLastNameError() {
    return lastNameError;
  }

  /**
   * set both the error of the connection possible input
   *
   * @param passwordError the new error for the password
   * @param usernameError the new error for the username
   */
  public void setErrors(String passwordError, String usernameError) {
    this.passwordError = passwordError;
    this.usernameError = usernameError;
    trigger(new ChangeEvent(this));
  }

  /**
   * set all the error of the inscription possible input
   *
   * @param passwordError the new error for the password
   * @param usernameError the new error for the username
   */
  public void setErrors(String usernameError, String pwd1Error, String pwd2Error,
      String firstNameError, String lastNameError, String mailError) {
    this.passwordError = pwd1Error;
    this.usernameError = usernameError;
    this.trigger(new ChangeEvent(this));
  }



  /**
   * called when the modele is changed. call the abonned changlisteners
   * 
   * @param changeEvent origin
   */
  private void trigger(ChangeEvent changeEvent) {
    for (ChangeListener changeListener : listeners) {
      changeListener.stateChanged(changeEvent);
    }

  }

}
