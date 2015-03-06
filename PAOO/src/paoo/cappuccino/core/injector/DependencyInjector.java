package paoo.cappuccino.core.injector;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import paoo.cappuccino.core.config.IAppConfig;
import paoo.cappuccino.util.exception.FatalException;

/**
 * Class used to build and inject dependencies into instances
 *
 * @author Guylian Cox
 */
public class DependencyInjector {

  private final IAppConfig config;
  private Map<Class<?>, Object> singletonCache = new HashMap<>();

  public DependencyInjector(IAppConfig config) {
    this.config = config;

    singletonCache.put(DependencyInjector.class, this);
  }

  /**
   * Fetches an injectable constructor if one is defined or the default constructor otherwise.
   *
   * @param clazz The class to fetch the constructor from.
   * @return the class' constructor.
   * @throws NoSuchMethodException there is no default nor injectable constructor.
   */
  private static Constructor<?> fetchConstructor(Class<?> clazz) throws NoSuchMethodException {
    Constructor<?>[] constructors = clazz.getDeclaredConstructors();
    Constructor<?> needle = null;

    for (Constructor<?> c : constructors) {
      if (c.getAnnotation(Inject.class) != null) {
        needle = c;
        break;
      }
    }

    if (needle == null) {
      needle = clazz.getDeclaredConstructor();
    }

    needle.setAccessible(true);
    return needle;
  }

  /**
   * Hardcodes dependencies for dependencies impossible to fetch dynamically.
   *
   * @param depClass    The dependency's class/interface
   * @param depInstance An instance of an implementation of the dependency.
   * @return true: the dependency has been set. false: the dependency was already set.
   */
  public boolean setDependency(Class<?> depClass, Object depInstance) {
    if (!isSingleton(depClass)) {
      throw new IllegalArgumentException("Dependency class must be a singleton ");
    }

    if (singletonCache.containsKey(depClass)) {
      return false;
    }

    singletonCache.put(depClass, depInstance);

    return true;
  }

  /**
   * Creates a new instance for a given class using the constructor annotated by {@link Inject
   *
   * @param dependency The class to instantiate.
   * @return The new instance.
   * @throws paoo.cappuccino.util.exception.FatalException The instance could not be created.
   * @Inject} or the default constructor if none are annotated.
   */
  private Object instantiateDependency(Class<?> dependency) {
    try {
      Constructor<?> constructor = fetchConstructor(dependency);

      Class<?>[] paramTypes = constructor.getParameterTypes();
      Object[] paramValues = new Object[paramTypes.length];
      for (int i = 0; i < paramTypes.length; i++) {
        paramValues[i] = buildDependency(paramTypes[i]);
      }

      return constructor.newInstance(paramValues);
    } catch (NoSuchMethodException e) {
      throw new FatalException("Could not instantiate " + dependency.getCanonicalName()
                               + ", it does not have a default constructor and no constructor has an @Inject annotation.",
                               e);
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
    Object instance = singletonCache.get(dependency);
    if (instance != null) {
      return instance;
    }

    if (dependency.isInterface()) {
      try {
        String depClassName = config.getString(dependency.getCanonicalName());
        dependency = Class.forName(depClassName);
      } catch (IllegalArgumentException | ClassNotFoundException e) {
        throw new FatalException("Could not fetch interface " + dependency.getCanonicalName()
                                 + "'s implementation.", e);
      }
    }

    instance = instantiateDependency(dependency);

    if (isSingleton(dependency)) {
      singletonCache.put(dependency, instance);
    }

    populate(instance);

    return instance;
  }

  private boolean isSingleton(Class<?> clazz) {
    return clazz.getAnnotation(Singleton.class) != null;

  }
}
