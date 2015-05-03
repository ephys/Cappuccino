package paoo.cappuccino.ihm.util;

import java.awt.Color;

/**
 * Constants specifics to the ihm.
 *
 * @author Opsomer Mathias
 */
public interface IhmConstants {

  /**
   * gaps.
   */
  int M_GAP = 15;
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
  String ERROR_NOT_MATCHING_PASSWORD =
      "Les mots de passes ne correspondent pas.";
  /**
   * error invalid email.
   */
  String ERROR_INVALID_EMAIL = "l'email spécifié est invalide.";

  /**
   * error invalid password.
   */
  String ERROR_INVALID_PASSWORD = "Le mot de passe spécifié est invalide (min 6 chars).";

  String NAME_ALREADY_TAKEN = "Nom déjà pris";

  /**
   * error invalid input string.
   */
  String ERROR_ALPHA_INPUT = "Ce champ ne peut contenir que des lettres.";

  String ERROR_ALPHANUM_INPUT =
      "Ce champ ne peut contenir que des charactères alphanumériques.";

  String ERROR_NUMERICAL_INPUT =
      "Ce champ ne peut contenir que des chiffres";

  Color MAIN_COLOR = new Color(212, 82, 82);

  String PATH_LOGO = "lib/logo.png";
  String ERROR_NO_COMPANY = "Aucune entreprise disponible";

  String ERROR_NO_BUSINESS_DAY =
      "Aucune journée des entreprises disponible.";

  String ERROR_NO_CONTACTS =
      "Il n'y a aucune personne de contact disponible";

  String ERROR_NO_PARTICIPATIONS =
      "Il n'y a aucune participations disponible";

  String SELECT_A_DAY = "Sélectionnez une journée.";


  String ERROR_INVALID_DAY = "La date entrée n'est pas valide.";

  String ERROR_YEAR_ALREADY_HAVE_A_DAY =
      "Une journée des entreprise existe déjà pour cette année.";

  String ERROR_TOO_LONG = "Nom trop long.";
}
