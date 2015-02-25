package config;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import util.exception.FatalException;

/**
 * @author Guylian Cox
 */
public class DependencyInjector {
  /**
   * @param interfaze The interface to instantiate
   * @return An instance of a class implementing the interface
   *
   * @throws java.lang.IllegalArgumentException the interface is not found in the application's config file
   * @throws util.exception.FatalException the associated implementation was not found
   */
  public static Object fetchDependency(Class<?> interfaze) { /* The cappuccino team is now german */
    String className = Config.getString(interfaze.getCanonicalName());

    try {
      Class clazz = Class.forName(className);

      @SuppressWarnings("unchecked")
      Constructor<?> constructor = clazz.getDeclaredConstructor();
      constructor.setAccessible(true);

      return constructor.newInstance();
    } catch (ClassNotFoundException e) {
      throw new FatalException("Could not instantiate " + interfaze.getCanonicalName() + ", it's implementation (" + className + ") was not found.", e);
    } catch (NoSuchMethodException e) {
      throw new FatalException("Could not instantiate " + interfaze.getCanonicalName() + ", it's implementation (" + className + ") does not have a default constructor.", e);
    } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
      throw new FatalException("Could not instantiate " + interfaze.getCanonicalName(), e);
    }
  }
}
