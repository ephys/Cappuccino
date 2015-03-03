package paoo.cappuccino.dal.exception;

/**
 * Exception used when it is impossible to insert an entity in the database because it already
 * exists there. (ie: primary key duplicate)
 *
 * @author Guylian Cox
 */
public class NonUniqueEntityException extends DalException {
  public NonUniqueEntityException(String message) {
    super(message);
  }

  public NonUniqueEntityException(String message, Throwable cause) {
    super(message, cause);
  }
}
