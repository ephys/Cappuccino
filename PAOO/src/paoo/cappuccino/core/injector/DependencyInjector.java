package paoo.cappuccino.core.injector;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import paoo.cappuccino.core.Config;
import paoo.cappuccino.util.exception.FatalException;

/**
 * Class used to build and inject dependencies into instances
 *
 * @author Guylian Cox
 */
public class DependencyInjector {
  public static final DependencyInjector INSTANCE = new DependencyInjector();

  private Map<Class<?>, Object> singletonCache = new HashMap<>();

  private DependencyInjector() {
    singletonCache.put(DependencyInjector.class, this);
  }

  /**
   * Creates a new instance for a given class using the default constructor.
   *
   * @param dependency The class to instantiate.
   * @return The new instance.
   * @throws paoo.cappuccino.util.exception.FatalException The instance could not be created.
   */
  private Object instantiateDependency(Class<?> dependency) {
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

  /**
   * Scans an instance for {@link paoo.cappuccino.core.injector.Inject @Inject} annotated fields
   * ands injects a matching dependency there.
   *
   * @param obj The instance to populate.
   * @throws paoo.cappuccino.util.exception.FatalException Populating the instance failed.
   */
  public void populate(Object obj) {
    Class<?> clazz = obj.getClass();

    Field[] fields = clazz.getDeclaredFields();
    for (Field field : fields) {
      if (field.getAnnotation(Inject.class) != null) {
        field.setAccessible(true);

        try {
          field.set(obj, buildDependency(field.getType()));
        } catch (Exception e) {
          throw new FatalException("Populating " + obj.getClass().getCanonicalName() + " failed",
                                   e);
        }
      }
    }
  }

  /**
   * Creates a new instance of a class and populates it. If the class is an interface, it will
   * create an instance maching the application configuration. If the class or interface is
   * annotated by {@link paoo.cappuccino.core.injector.Singleton @Singleton}, it will not create
   * more than one instance and will always give out the same one.
   *
   * @param dependency The class or interface to instantiate.
   * @return An instance with the same type as the class given.
   * @throws paoo.cappuccino.util.exception.FatalException The instance could not be created or
   *                                                       populated.
   */
  public Object buildDependency(Class<?> dependency) {
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
      instance = instantiateDependency(dependency);
      populate(instance);

      if (dependency.getAnnotation(Singleton.class) != null) {
        singletonCache.put(dependency, instance);
      }
    }

    return instance;
  }
}
