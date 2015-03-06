package paoo.cappuccino.core.injector;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used by the dependency injector to mark a dependency as being a singleton
 *
 * @author Guylian Cox
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Singleton {

  /**
   * Redirects the dependency injector to a given dependency. An implementation of that
   * dependency is going to be returned instead.
   *
   * Used for when a singleton implements multiple interfaces that are not linked to each other
   * but need to share the same implementation.
   */
  Class<?> redirectTo() default void.class;
}
