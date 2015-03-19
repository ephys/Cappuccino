package paoo.cappuccino.ihm.util;

import java.awt.Color;

/**
 * Constants specifics to the ihm.
 *
 * @author Opsomer Mathias
 */
public interface IhmConstants {

  /**
   * medium gap.
   */
  static final int M_GAP = 15;

  /**
   * large gap.
   */
  static final int L_GAP = 30;

  /**
   * error if empty textarea.
   */
  static final String ERROR_FIELD_EMPTY = "Ce champ est obligatoire !";

  /**
   * error wrong login.
   */
  static final String ERROR_WRONG_LOGIN = "Mauvais identifiants !";

  /**
   * error passwords are not matching.
   */
  static final String ERROR_NOT_MATCHING_PASSWORD = "Les mots de passes ne correspondent pas !";
  /**
   * error invalid email.
   */
  static final String ERROR_INVALID_EMAIL = "l'email spécifié est invalide !";

  /**
   * error invalid password.
   */
  static final String ERROR_INVALID_PASSWORD = "Le mot de passe spécifié est invalide !";

  /**
   * error invalid input string.
   */
  static final String ERROR_ALPHANUM_INPUT =
      "Ce champ ne peut contenir que des caractères alphanumériques";

  static final Color LIGHT_BLUE = new Color(155, 178, 247);

  static final String PATH_LOGO = "lib/logo.png";
}
