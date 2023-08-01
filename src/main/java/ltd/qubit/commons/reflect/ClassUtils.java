////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.concurrent.Immutable;

/**
 * Provide utility functions for Class objects.
 *
 * @author Haixing Hu
 */
public class ClassUtils {

  /**
   * Check if a type is an interface.
   *
   * @param type
   *     the type to check
   * @return true if the type is an interface, false otherwise
   */
  public static boolean isInterface(final Class<?> type) {
    return type.isInterface();
  }

  /**
   * Check if the type is abstract (either an interface or an abstract class).
   *
   * @param type
   *     the type to check
   * @param <T>
   *     the actual type to check
   * @return true if the type is abstract, false otherwise
   */
  public static <T> boolean isAbstract(final Class<T> type) {
    return Modifier.isAbstract(type.getModifiers());
  }

  /**
   * Check if the type is public.
   *
   * @param type
   *     the type to check
   * @param <T>
   *     the actual type to check
   * @return true if the type is public, false otherwise
   */
  public static <T> boolean isPublic(final Class<T> type) {
    return Modifier.isPublic(type.getModifiers());
  }

  /**
   * Check if the type is protected.
   *
   * @param type
   *     the type to check
   * @param <T>
   *     the actual type to check
   * @return true if the type is protected, false otherwise
   */
  public static <T> boolean isProtected(final Class<T> type) {
    return Modifier.isProtected(type.getModifiers());
  }

  /**
   * Check if the type is private.
   *
   * @param type
   *     the type to check
   * @param <T>
   *     the actual type to check
   * @return true if the type is private, false otherwise
   */
  public static <T> boolean isPrivate(final Class<T> type) {
    return Modifier.isPrivate(type.getModifiers());
  }

  /**
   * Check whether a class object is a proxy class.
   *
   * @param type
   *     the class object be checked.
   * @return
   *     true if the class object is a proxy class; false otherwise.
   */
  public static boolean isProxy(final Class<?> type) {
    if (type == null) {
      return false;
    } else if (Proxy.isProxyClass(type)) {
      return true;
    } else {
      return matchesWellKnownProxyClassNamePattern(type.getName());
    }
  }

  static boolean matchesWellKnownProxyClassNamePattern(final String className) {
    return className.contains(BYTE_BUDDY_CLASS_SEPARATOR)
        || className.contains(CGLIB_JAVASSIST_CLASS_SEPARATOR)
        || className.contains(HIBERNATE_PROXY_CLASS_SEPARATOR);
  }

  private static final String CGLIB_JAVASSIST_CLASS_SEPARATOR = "$$";
  private static final String BYTE_BUDDY_CLASS_SEPARATOR = "$ByteBuddy$";
  private static final String HIBERNATE_PROXY_CLASS_SEPARATOR = "$HibernateProxy$";

  /**
   * Check if a type is a primitive type.
   *
   * @param type
   *     the type to check.
   * @return true if the type is a primitive type, false otherwise.
   */
  public static boolean isPrimitiveType(final Class<?> type) {
    return type.isPrimitive();
  }

  /**
   * Check if a type is an array type.
   *
   * @param type
   *     the type to check.
   * @return true if the type is an array type, false otherwise.
   */
  public static boolean isArrayType(final Class<?> type) {
    return type.isArray();
  }

  /**
   * Check if a type is an enum type.
   *
   * @param type
   *     the type to check.
   * @return true if the type is an enum type, false otherwise.
   */
  public static boolean isEnumType(final Class<?> type) {
    return type.isEnum() || Enum.class.isAssignableFrom(type);
  }

  /**
   * Check if a type is a collection type.
   *
   * @param type
   *     the type to check.
   * @return true if the type is a collection type, false otherwise
   */
  public static boolean isCollectionType(final Class<?> type) {
    return Collection.class.isAssignableFrom(type);
  }

  /**
   * Check if a type is a collection type.
   *
   * @param type
   *     the type to check.
   * @return true if the type is a collection type, false otherwise
   */
  public static boolean isCollectionType(final Type type) {
    return isParameterizedType(type)
        && isCollectionType((Class<?>) ((ParameterizedType) type).getRawType());
  }

  /**
   * Check if a type is a list type.
   *
   * @param type
   *     the type to check.
   * @return true if the type is a list type, false otherwise
   */
  public static boolean isListType(final Class<?> type) {
    return List.class.isAssignableFrom(type);
  }

  /**
   * Check if a type is a list type.
   *
   * @param type
   *     the type to check.
   * @return true if the type is a list type, false otherwise
   */
  public static boolean isListType(final Type type) {
    return isParameterizedType(type)
        && isListType((Class<?>) ((ParameterizedType) type).getRawType());
  }

  /**
   * Check if a type is a set type.
   *
   * @param type
   *     the type to check.
   * @return true if the type is a set type, false otherwise
   */
  public static boolean isSetType(final Class<?> type) {
    return Set.class.isAssignableFrom(type);
  }

  /**
   * Check if a type is a set type.
   *
   * @param type
   *     the type to check.
   * @return true if the type is a set type, false otherwise
   */
  public static boolean isSetType(final Type type) {
    return isParameterizedType(type)
        && isSetType((Class<?>) ((ParameterizedType) type).getRawType());
  }

  /**
   * Check if a type is a map type.
   *
   * @param type
   *     the type to check
   * @return true if the type is a map type, false otherwise.
   */
  public static boolean isMapType(final Class<?> type) {
    return Map.class.isAssignableFrom(type);
  }

  /**
   * Check if a type is a map type.
   *
   * @param type
   *     the type to check.
   * @return true if the type is a map type, false otherwise
   */
  public static boolean isMapType(final Type type) {
    return isParameterizedType(type)
        && isMapType((Class<?>) ((ParameterizedType) type).getRawType());
  }

  /**
   * Check if a type is a JDK built-in class.
   *
   * @param type
   *     the type to check
   * @return true if the type is a built-in class, false otherwise.
   */
  public static boolean isJdkBuiltIn(final Class<?> type) {
    final String name = type.getName();
    return name.startsWith("java.") || name.startsWith("javax.");
  }

  /**
   * Check whether a type is the boxing type of primitive type.
   *
   * @param type
   *     the class object of the type to check.
   * @return
   *     {@code true} if the type is the boxing type of primitive type,
   *     {@code false} otherwise.
   */
  public static boolean isBoxingType(final Class<?> type) {
    return (type == Character.class)
        || (type == Boolean.class)
        || (type == Byte.class)
        || (type == Short.class)
        || (type == Integer.class)
        || (type == Long.class)
        || (type == Float.class)
        || (type == Double.class);
  }

  /**
   * Check whether a type is the type representing a permission.
   *
   * @param type
   *     the class object of the type to check.
   * @return
   *     {@code true} if the type is the type representing a permission, i.e.,
   *     the subclass of {@link java.security.Permission}; {@code false}
   *     otherwise.
   */
  public static boolean isPermissionType(final Class<?> type) {
    return java.security.Permission.class.isAssignableFrom(type);
  }

  private static final Set<Class<?>> IMMUTABLE_JDK_CLASSES = new HashSet<>();

  static {
    IMMUTABLE_JDK_CLASSES.add(Character.class);
    IMMUTABLE_JDK_CLASSES.add(Boolean.class);
    IMMUTABLE_JDK_CLASSES.add(Byte.class);
    IMMUTABLE_JDK_CLASSES.add(Short.class);
    IMMUTABLE_JDK_CLASSES.add(Integer.class);
    IMMUTABLE_JDK_CLASSES.add(Long.class);
    IMMUTABLE_JDK_CLASSES.add(Float.class);
    IMMUTABLE_JDK_CLASSES.add(Double.class);
    IMMUTABLE_JDK_CLASSES.add(Void.class);
    IMMUTABLE_JDK_CLASSES.add(BigInteger.class);
    IMMUTABLE_JDK_CLASSES.add(BigDecimal.class);
    IMMUTABLE_JDK_CLASSES.add(String.class);
    IMMUTABLE_JDK_CLASSES.add(Class.class);
    IMMUTABLE_JDK_CLASSES.add(StackTraceElement.class);
    IMMUTABLE_JDK_CLASSES.add(java.net.IDN.class);
    IMMUTABLE_JDK_CLASSES.add(java.net.Inet4Address.class);
    IMMUTABLE_JDK_CLASSES.add(java.net.Inet6Address.class);
    IMMUTABLE_JDK_CLASSES.add(java.net.InetSocketAddress.class);
    IMMUTABLE_JDK_CLASSES.add(java.net.URL.class);
    IMMUTABLE_JDK_CLASSES.add(java.net.URI.class);
    IMMUTABLE_JDK_CLASSES.add(java.security.Permission.class);
    IMMUTABLE_JDK_CLASSES.add(java.io.File.class);
    IMMUTABLE_JDK_CLASSES.add(java.awt.Font.class);
    IMMUTABLE_JDK_CLASSES.add(java.awt.BasicStroke.class);
    IMMUTABLE_JDK_CLASSES.add(java.awt.Color.class);
    IMMUTABLE_JDK_CLASSES.add(java.awt.GradientPaint.class);
    IMMUTABLE_JDK_CLASSES.add(java.awt.LinearGradientPaint.class);
    IMMUTABLE_JDK_CLASSES.add(java.awt.RadialGradientPaint.class);
    IMMUTABLE_JDK_CLASSES.add(java.awt.Cursor.class);
    IMMUTABLE_JDK_CLASSES.add(java.util.Locale.class);
    IMMUTABLE_JDK_CLASSES.add(java.util.UUID.class);
    IMMUTABLE_JDK_CLASSES.add(java.util.Currency.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.Clock.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.Duration.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.Instant.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.LocalDate.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.LocalDateTime.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.LocalTime.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.MonthDay.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.OffsetDateTime.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.OffsetTime.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.Period.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.Year.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.YearMonth.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.ZonedDateTime.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.ZoneId.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.ZoneOffset.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.chrono.HijrahChronology.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.chrono.HijrahDate.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.chrono.IsoChronology.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.chrono.JapaneseChronology.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.chrono.JapaneseDate.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.chrono.JapaneseEra.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.chrono.MinguoChronology.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.chrono.MinguoDate.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.chrono.ThaiBuddhistChronology.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.chrono.ThaiBuddhistDate.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.format.DateTimeFormatter.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.format.DateTimeFormatterBuilder.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.format.DecimalStyle.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.temporal.IsoFields.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.temporal.JulianFields.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.temporal.TemporalAdjusters.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.temporal.TemporalQueries.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.temporal.ValueRange.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.temporal.WeekFields.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.zone.ZoneOffsetTransition.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.zone.ZoneOffsetTransitionRule.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.zone.ZoneRules.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.zone.ZoneRulesProvider.class);
  }

  /**
   * Check whether a type is a type of immutable objects.
   *
   * @param type
   *     the class object of the type to check.
   * @return
   *     {@code true} if the type is a type of immutable objects, {@code false}
   *     otherwise.
   */
  public static boolean isImmutableType(final Class<?> type) {
    return IMMUTABLE_JDK_CLASSES.contains(type)
        || isEnumType(type)
        || isPermissionType(type)
        || type.isAnnotationPresent(Immutable.class);
  }

  /**
   * Check whether a type is a type of mutable objects.
   *
   * @param type
   *     the class object of the type to check.
   * @return
   *     {@code true} if the type is a type of mutable objects, {@code false}
   *     otherwise.
   */
  public static boolean isMutableType(final Class<?> type) {
    return !isImmutableType(type);
  }

  /**
   * Check if a type should be introspected for internal fields.
   *
   * FIXME: this method is only used by common-random, and it should be moved to
   *   the common-random library.
   *
   * @param type
   *     the type to check
   * @return true if the type should be introspected, false otherwise
   */
  public static boolean isIntrospectable(final Class<?> type) {
    return !isEnumType(type)
        && !isArrayType(type)
        && !(isCollectionType(type) && isJdkBuiltIn(type))
        && !(isMapType(type) && isJdkBuiltIn(type));
  }

  /**
   * Check if a type is populatable.
   *
   * FIXME: this method is only used by common-random, and it should be moved to
   *   the common-random library.
   *
   * @param type
   *     the type to check
   * @return true if the type is populatable, false otherwise
   */
  public static boolean isPopulatable(final Type type) {
    return !isWildcardType(type)
        && !isTypeVariable(type)
        && !isCollectionType(type)
        && !isParameterizedType(type);
  }

  /**
   * Check if a type is a parameterized type.
   *
   * @param type
   *     the type to check
   * @return true if the type is parameterized, false otherwise
   */
  public static boolean isParameterizedType(final Type type) {
    return (type instanceof ParameterizedType)
        && ((ParameterizedType) type).getActualTypeArguments().length > 0;
  }

  /**
   * Check if a type is a wildcard type.
   *
   * @param type
   *     the type to check
   * @return true if the type is a wildcard type, false otherwise
   */
  public static boolean isWildcardType(final Type type) {
    return (type instanceof WildcardType);
  }

  /**
   * Check if a type is a type variable.
   *
   * @param type
   *     the type to check
   * @return true if the type is a type variable, false otherwise
   */
  public static boolean isTypeVariable(final Type type) {
    return (type instanceof TypeVariable<?>);
  }

  /**
   * Searches the specified interface of a class.
   *
   * @param type
   *     the specified class.
   * @param simpleName
   *     the simple name of the interface to be searched.
   * @return the interface of the class with the specified simple name; or
   *     {@code null} if no such interface.
   */
  public static Class<?> getInterface(final Class<?> type,
      final String simpleName) {
    for (final Class<?> cls : type.getInterfaces()) {
      if (cls.getSimpleName().equals(simpleName)) {
        return cls;
      }
    }
    return null;
  }

  /**
   * Gets the real class object of a possible proxy class object.
   *
   * @param <T>
   *     the type parameter of the class object.
   * @param clazz
   *     a class object, possibly a proxy class object.
   * @return
   *     the real class object of the specified possible proxy class object.
   */
  @SuppressWarnings("unchecked")
  public static <T> Class<T> getRealClass(final Class<T> clazz) {
    if (clazz == null) {
      return null;
    } else if (Proxy.isProxyClass(clazz)) {
      final Class<?>[] interfaces = clazz.getInterfaces();
      if (interfaces.length != 1) {
        throw new IllegalArgumentException("Unexpected number of interfaces: "
          + interfaces.length);
      }
      return (Class<T>) interfaces[0];
    } else if (matchesWellKnownProxyClassNamePattern(clazz.getName())) {
      final Class<T> superclass = (Class<T>) clazz.getSuperclass();
      return getRealClass(superclass);
    } else {
      return clazz;
    }
  }

  /**
   * Gets the real class object of a possible proxy object.
   *
   * @param <T>
   *     the type of the object.
   * @param object
   *     the object, possibly a proxy object.
   * @return
   *     the real class object of the specified possible proxy object.
   */
  public static <T> Class<T> getRealClass(final T object) {
    if (object instanceof Class<?>) {
      throw new IllegalArgumentException("The provided object is already a class: "
          + object + ". " + "You probably want to call ClassUtils.getRealClass(Class) instead.");
    }
    @SuppressWarnings("unchecked")
    final Class<T> entityClass = (Class<T>) object.getClass();
    return getRealClass(entityClass);
  }
}
