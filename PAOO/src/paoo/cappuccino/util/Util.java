package paoo.cappuccino.util;

/**
 * 
 * Classe utilitaire permettant de tester la valider d'objets selon certains critéres
 * 
 */
public final class Util {
  /**
   * Constructeur vide privé
   */
  private Util() {

  }

  /**
   * Méthode permettant de vérifier si l'objet en paramètre est null
   * 
   * @param object : l'objet à vérifier
   * 
   */
  public static void checkObject(final Object object) {
    if (object == null) {
      throw new IllegalArgumentException("Erreur: le paramètre est null");
    }
  }

  /**
   * Verifie que le String passe en parametre ne soit pas null ni vide
   * 
   * @param s le string a verifier
   */
  public static void checkString(final String s) {
    checkObject(s);
    if (s.trim().equals("")) {
      throw new IllegalArgumentException("Erreur champ vide");
    }
  }

  /**
   * Verifie que le String passe en parametre ne contient pas de chiffre et qu'il ne soit pas sans
   * caractere ("")
   * 
   * @param s le string a verifier
   */
  public static void checkNumber(final String s) {
    checkObject(s);
    if (!s.matches(Constante.REGEX_NB)) {
      throw new IllegalArgumentException("Erreur, il ne peut pas avoir de chiffre");
    }
  }

  /**
   * Verifie que le String passe en parametre ne contient pas de lettre et qu'il ne sois pas sans
   * caractere ("")
   * 
   * @param s le string a verifier
   */
  public static void checkNotNumber(final String s) {
    checkString(s);
    if (!s.matches(Constante.REGEX_CHAMPS_LETTRES)) {
      throw new IllegalArgumentException("Erreur, il ne peut pas avoir de lettre");
    }
  }

  /**
   * Verifie que le String passe en parametre ne contient pas de lettre
   * 
   * @param s le string a verifier
   */
  public static void checkNotNumberWithSpace(final String s) {
    checkObject(s);
    if (!s.trim().equals("") && !s.matches(Constante.REGEX_CHAMPS_LETTRES)) {
      throw new IllegalArgumentException("Erreur, il ne peut pas avoir de lettre");
    }
  }

}
