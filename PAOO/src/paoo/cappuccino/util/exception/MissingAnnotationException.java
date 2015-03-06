package paoo.cappuccino.util.exception;

import java.util.NoSuchElementException;

/**
 * Thrown to indicate that a required annotation is missing.
 */
public class MissingAnnotationException extends NoSuchElementException {
  public MissingAnnotationException(String s) {
    super(s);
  }
}
