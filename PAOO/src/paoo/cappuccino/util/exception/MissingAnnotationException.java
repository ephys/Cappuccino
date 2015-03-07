package paoo.cappuccino.util.exception;

import java.util.NoSuchElementException;

/**
 * Thrown to indicate that a required annotation is missing.
 *
 * @author Guylian Cox
 */
public class MissingAnnotationException extends NoSuchElementException {
  public MissingAnnotationException(String s) {
    super(s);
  }
}
