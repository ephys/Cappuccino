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
  int M_GAP = 15;

  /**
   * large gap.
   */
  int L_GAP = 30;

  int XL_GAP = 100;
  /**
   * error if empty textarea.
   */
  String ERROR_FIELD_EMPTY = "Ce champ est obligatoire.";

  /**
   * error wrong login.
   */
  String ERROR_WRONG_LOGIN = "Mauvais identifiants.";

  /**
   * error passwords are not matching.
   */
  String ERROR_NOT_MATCHING_PASSWORD = "Les mots de passes ne correspondent pas.";
  /**
   * error invalid email.
   */
  String ERROR_INVALID_EMAIL = "l'email spécifié est invalide.";

  /**
   * error invalid password.
   */
  String ERROR_INVALID_PASSWORD = "Le mot de passe spécifié est invalide.";

  String NAME_ALREADY_TAKEN = "Nom déjà pris";

  /**
   * error invalid input string.
   */
  String ERROR_ALPHA_INPUT = "Ce champ ne peut contenir que des lettres.";

  String ERROR_NUMERICAL_INPUT = "Ce champ ne peut contenir que des chiffres";

  Color LIGHT_BLUE = new Color(155, 178, 247);

  String PATH_LOGO = "lib/logo.png";




}
