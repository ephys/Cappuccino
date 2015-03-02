package paoo.cappuccino.business.exception;

import java.sql.SQLException;

/**
 * 
 * Classe d'exception pour la couche Biz-ness
 * 
 */
public class BizException extends RealException {

  /**
   * Exception de type BizException
   * 
   * @param message : le msg d'erreur
   */
  public BizException(final String message) {
    super(message);
  }

  /**
   * Exception de type BizException
   * 
   * @param message : le msg d'erreur bizException : la biz exception
   * @param bizE la biz exception
   */
  public BizException(final String message, final BizException bizE) {
    super(message, bizE);
  }

  /**
   * 
   * @param message : le message d'erreur
   * @param e : la SQLException
   */
  public BizException(final String message, final SQLException e) {
    super(message, e);
  }

}
