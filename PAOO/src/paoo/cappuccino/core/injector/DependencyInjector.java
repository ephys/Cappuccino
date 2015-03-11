package paoo.cappuccino.core.injector;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import paoo.cappuccino.core.AppContext;
import paoo.cappuccino.core.config.IAppConfig;
import paoo.cappuccino.util.exception.FatalException;

/**
 * Class used to build and inject dependencies into instances.
 *
 * @author Guylian Cox
 */
public class DependencyInjector {

  private final IAppConfig config;
  private final AppContext app;
  private final Logger logger;
  private Map<Class<?>, Object> instanceCache = new HashMap<>();

  /**
   * Creates a new dependency injector.
   *
   * @param config The configuration holder contaning the class -> object mapping
   */
  public DependencyInjector(IAppConfig config, AppContext app) {
    this.config = config;
    this.app = app;
    this.logger = app.getLogger("Injector");

    setDependency(DependencyInjector.class, this);
  }

  /**
   * Fetches an injectable constructor if one is defined or the default constructor otherwise.
   *
   * @param clazz The class to fetch the constructor from.
   * @return the class' constructor.
   * @throws NoSuchMethodException there is no default nor injectable constructor.
   */
  @SuppressWarnings("unchecked")
  private <A> Constructor<A> fetchConstructor(Class<A> clazz) throws NoSuchMethodException {
    Constructor<?>[] constructors = clazz.getDeclaredConstructors();
    Constructor<A> needle = null;

    for (Constructor<?> c : constructors) {
      if (c.getAnnotation(Inject.class) != null) {
        needle = (Constructor<A>) c;
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
   * @param dependency  The dependency's class/interface
   * @param depInstance An instance of an implementation of the dependency.
   * @return true: the dependency has been set. false: the dependency was already set.
   */
  public <A> boolean setDependency(Class<A> dependency, A depInstance) {
    final Class<? extends A> implementation = getImplementation(dependency);

    if (instanceCache.containsKey(implementation)) {
      return false;
    }

    instanceCache.put(implementation, depInstance);
    return true;
  }

  /**
   * Creates a new instance for a given class using the constructor annotated by {@link Inject} or
   * the default constructor if none are annotated.
   *
   * @param dependency The class to instantiate.
   * @return The new instance.
   * @throws paoo.cappuccino.util.exception.FatalException The instance could not be created.
   */
  private <A> A instantiateDependency(Class<A> dependency) {
    try {
      Constructor<A> constructor = fetchConstructor(dependency);

      Class<?>[] paramTypes = constructor.getParameterTypes();
      Object[] paramValues = new Object[paramTypes.length];
      for (int i = 0; i < paramTypes.length; i++) {
        paramValues[i] = buildDependency(paramTypes[i]);
      }

      return constructor.newInstance(paramValues);
    } catch (NoSuchMethodException e) {
      throw new FatalException("Could not instantiate " + dependency.getCanonicalName()
                               + ", it does not have a default constructor and "
                               + "no constructor has an @Inject annotation.",
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
        if (app.getProfileType() != AppContext.Profile.TEST) {
          logger.warning("Injecting directly into field '" + field.getName() + "' in class '"
                         + clazz.getCanonicalName() + "'. This behavior is deprecated and should "
                         + "only be used for populating JUnit tests.");
        }

        field.setAccessible(true);

        try {
          field.set(obj, buildDependency(field.getType()));
        } catch (Exception e) {
          throw new FatalException("Populating " + obj.getClass().getCanonicalName()
                                   + " failed", e);
        }
      }
    }
  }

  /**
   * Creates a new instance of a class and populates it. If the class is an interface, it will
   * create an instance matching the application configuration.
   *
   * @param dependency The class or interface to instantiate.
   * @return An instance with the same type as the class given.
   * @throws paoo.cappuccino.util.exception.FatalException The instance could not be created or
   *                                                       populated.
   */
  @SuppressWarnings("unchecked")
  public <A> A buildDependency(final Class<A> dependency) {
    final Class<? extends A> implementation = getImplementation(dependency);

    try {
      A instance = (A) instanceCache.get(implementation);
      if (instance != null) {
        return instance;
      }

      A depInstance = instantiateDependency(implementation);
      instanceCache.put(implementation, depInstance);
      populate(depInstance);

      return depInstance;
    } catch (ClassCastException e) {
      throw new FatalException(dependency.getCanonicalName()
                               + " has for declared instanciable class '"
                               + implementation.getCanonicalName()
                               + "' but that class doesn't implement/extends"
                               + " the dependency.", e);
    }
  }

  /**
   * Fetches an interface's implementation class.
   *
   * @param interfaze The interface to get an implementation for.
   * @return An implementation.
   */
  @SuppressWarnings("unchecked")
  private <A> Class<? extends A> getImplementation(Class<A> interfaze) {
    if (!interfaze.isInterface()) {
      return interfaze;
    }

    try {
      String depClassName = config.getString(interfaze.getCanonicalName());
      return (Class<? extends A>) Class.forName(depClassName);
    } catch (IllegalArgumentException | ClassNotFoundException e) {
      throw new FatalException("Could not fetch interface " + interfaze.getCanonicalName()
                               + "'s implementation.", e);
    }
  }
}
