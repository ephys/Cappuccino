package paoo.cappuccino.core.injector;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import paoo.cappuccino.core.config.IAppConfig;
import paoo.cappuccino.util.exception.FatalException;
import paoo.cappuccino.util.exception.MissingAnnotationException;

/**
 * Class used to build and inject dependencies into instances.
 *
 * @author Guylian Cox
 */
@Singleton
public class DependencyInjector {

  private final IAppConfig config;
  private Map<Class<?>, Object> singletonCache = new HashMap<>();

  /**
   * Creates a new dependency injector.
   *
   * @param config The configuration holder contaning the class -> object mapping
   */
  public DependencyInjector(IAppConfig config) {
    this.config = config;

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
   * @param depClass    The dependency's class/interface
   * @param depInstance An instance of an implementation of the dependency.
   * @return true: the dependency has been set. false: the dependency was already set.
   */
  public <A> boolean setDependency(Class<A> depClass, A depInstance) {
    Class<?> singletonKey = redirectSingleton(depClass);

    if (singletonKey == null) {
      throw new IllegalArgumentException("Dependency class must be a singleton ");
    }

    if (singletonCache.containsKey(singletonKey)) {
      return false;
    }

    singletonCache.put(singletonKey, depInstance);
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
  @SuppressWarnings("unchecked")
  public <A> A buildDependency(final Class<A> dependency) {
    // If we're a singleton, redirect it to the actual singleton class
    // then fetch its instance or create it.
    final Class<?> singletonDep = redirectSingleton(dependency);
    final boolean isSingleton = singletonDep != null;

    if (isSingleton) {
      A instance = (A) singletonCache.get(singletonDep);

      if (instance != null) {
        return instance;
      }

      try {
        return (A) createDependency(singletonDep, true);
      } catch (ClassCastException e) {
        throw new FatalException(dependency.getCanonicalName()
                                 + " was redirected to "
                                 + singletonDep.getCanonicalName()
                                 + " but its implementation doesn't implement/extends"
                                 + " the dependency.", e);
      }
    } else {
      // otherwise create this dependency without redirection.

      try {
        return createDependency(dependency, false);
      } catch (ClassCastException e) {
        throw new FatalException("The defined implementation of interface "
                                 + dependency.getCanonicalName()
                                 + "does not implement said interface.", e);
      }
    }
  }

  /**
   * Creates an instance of an implementation of a dependency and caches it.
   *
   * @param abstractDependency The class or interface to instantiate.
   * @param cache              true: cache the instance.
   * @return An instance of the dependency.
   */
  private <A> A createDependency(final Class<A> abstractDependency, final boolean cache) {
    final Class<? extends A> implementation = abstractDependency.isInterface()
                                              ? getImplementation(abstractDependency)
                                              : abstractDependency;

    A depInstance = instantiateDependency(implementation);

    if (cache) {
      singletonCache.put(abstractDependency, depInstance);
    }

    populate(depInstance);

    return depInstance;
  }

  /**
   * Fetches an interface's implementation class.
   *
   * @param interfaze The interface to get an implementation for.
   * @return An implementation.
   */
  @SuppressWarnings("unchecked")
  private <A> Class<? extends A> getImplementation(Class<A> interfaze) {
    try {
      String depClassName = config.getString(interfaze.getCanonicalName());
      return (Class<? extends A>) Class.forName(depClassName);
    } catch (IllegalArgumentException | ClassNotFoundException e) {
      throw new FatalException("Could not fetch interface " + interfaze.getCanonicalName()
                               + "'s implementation.", e);
    }
  }

  /**
   * Gets the class acting as a key in the singleton cache if it is a singleton.
   *
   * @param clazz the clazz from which it should be checked
   * @return the singleton class or null if it isn't a singleton.
   * @throws paoo.cappuccino.util.exception.MissingAnnotationException The singleton redirected to a
   *                                                                   non-singleton dependency.
   * @see paoo.cappuccino.core.injector.DependencyInjector#redirectSingleton(Class, boolean)
   */
  private Class<?> redirectSingleton(Class<?> clazz) {
    return redirectSingleton(clazz, false);
  }

  /**
   * Checks if it is a singleton dependency. If it is and it redirects to another singleton (using
   * {@link Singleton#redirectTo()}), it will follow the redirection and return the final singleton
   * class.
   *
   * @param clazz            the clazz from which it should be checked
   * @param requireSingleton (internal) if true, it will throw an exception instead of returning
   *                         null. when the dependency is not a singleton.
   * @return the singleton class or null if it isn't a singleton.
   * @throws paoo.cappuccino.util.exception.MissingAnnotationException The class is not a singleton.
   */
  private Class<?> redirectSingleton(Class<?> clazz, boolean requireSingleton) {
    Singleton annotation = clazz.getAnnotation(Singleton.class);

    if (annotation == null) {
      if (requireSingleton) {
        throw new MissingAnnotationException(
            "A singleton dependency redirected to " + clazz.getCanonicalName()
            + " but the latter does not have a Singleton annotation. "
            + "This is likely to cause silent problems.");
      }

      return null;
    }

    if (annotation.redirectTo() != void.class) {
      // recursive loop until we get a singleton that does not redirect.
      // this could cause an infinite loop, should we add a check to change the exception ?
      return redirectSingleton(annotation.redirectTo(), true);
    }

    return clazz;
  }
}
