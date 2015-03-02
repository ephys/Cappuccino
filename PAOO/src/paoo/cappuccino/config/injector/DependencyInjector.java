package paoo.cappuccino.config.injector;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import paoo.cappuccino.config.Config;
import paoo.cappuccino.util.exception.FatalException;

/**
 * @author Guylian Cox
 */
public class DependencyInjector {
  private static Map<Class<?>, Object> singletonCache = new HashMap<>();

  private static Object fetchDependency(Class<?> dependency) {
    try {
      Constructor<?> constructor = dependency.getDeclaredConstructor();
      constructor.setAccessible(true);

      return constructor.newInstance();
    } catch (NoSuchMethodException e) {
      throw new FatalException("Could not instantiate " + dependency.getCanonicalName()
          + ", it does not have a default constructor.", e);
    } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
      throw new FatalException("Could not instantiate " + dependency.getCanonicalName(), e);
    }
  }

  public static void populate(Object obj) {
    Class<?> clazz = obj.getClass();

    Field[] fields = clazz.getDeclaredFields();
    for (Field field : fields) {
      if (field.getAnnotation(Inject.class) != null) {
        field.setAccessible(true);

        try {
          field.set(obj, buildDependency(field.getType()));
        } catch (Exception e) {
          throw new FatalException("Populating " + obj.getClass().getCanonicalName() + " failed", e);
        }
      }
    }
  }

  public static Object buildDependency(Class<?> dependency) {
    if (dependency.isInterface()) {
      try {
        String depClassName = Config.getString(dependency.getCanonicalName());
        dependency = Class.forName(depClassName);
      } catch (IllegalArgumentException | ClassNotFoundException e) {
        throw new FatalException("Could not fetch interface " + dependency.getCanonicalName()
            + "'s implementation.", e);
      }
    }

    Object instance = singletonCache.get(dependency);

    if (instance == null) {
      instance = fetchDependency(dependency);
      populate(instance);

      if (dependency.getAnnotation(Singleton.class) != null) {
        singletonCache.put(dependency, instance);
      }
    }

    return instance;
  }
}
