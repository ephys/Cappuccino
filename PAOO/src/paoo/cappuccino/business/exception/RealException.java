package paoo.cappuccino.business.exception;

/**
 * 
 * Classe d'exception pour les erreurs reel
 *
 */
public class RealException extends RuntimeException {

  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  /**
   * Constructeur contenant 1 paramètre :
   * 
   * @param message String correspondant au message d'erreur
   */
  public RealException(final String message) {
    super(message);
  }

  /**
   * Constructeur contenant 1 paramètre :
   * 
   * @param cause Throwable correspondant à la provenance de l'erreur
   */
  public RealException(final Throwable cause) {
    super(cause);
  }

  /**
   * Constructeur contenant 2 paramètres :
   * 
   * @param message String correspondant au message d'erreur
   * @param cause Throwable correspondant à la provenance de l'erreur
   */
  public RealException(final String message, final Throwable cause) {
    super(message, cause);
  }

}
