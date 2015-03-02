package paoo.cappuccino.business.exception;

import paoo.cappuccino.util.exception.FatalException;

/**
 * 
 * Exception de type fatal qui etend FatalException qui traite les erreurs
 * venant de tentatives de connexion à la Database
 * 
 *
 */
public class ConnectionException extends FatalException {

	private static final long serialVersionUID = 7285032042598723949L;

	/**
	 * Constructeur avec un Throwable un message
	 * 
	 * @param cause
	 *            exception à englober
	 * @param message
	 *            message de l'exception
	 */
	public ConnectionException(final String message, final Throwable cause) {
		super(message, cause);
	}

}