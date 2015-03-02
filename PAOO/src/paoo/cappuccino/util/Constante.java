package paoo.cappuccino.util;

public class Constante {
  public static final String REGEX_NB = "^[0-9]{1,}$";
  public static final String REGEX_MDP = "^[a-zA-Z0-9]{6,}$";
  public static final String REGEX_CHAMPS_LETTRES = "^[a-zA-Zéèàêâãçàùäëîï ]+$";
  public static final String REGEX_EMAIL =
      "^([a-zA-Z0-9][+a-zA-Z0-9_.-]*)+\\@([a-zA-Z0-9][a-zA-Z0-9_.-]*)+\\.[a-zA-Z]{2,3}$";
  public static final String REGEX_NO_GSM =
      "^(([0-9]{4})[ -_/.]((([0-9]{2}){3})|([0-9]{3}[ -_/.]?[0-9]{3})|([0-9][0-9][ -_/.]?[0-9][0-9][ -_/.]?[0-9][0-9])))||([0-9]{10})$";
}
