////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

import ltd.qubit.commons.lang.ClassUtils;

/**
 * Contains common code for working with Methods/Constructors, extracted and
 * refactored from {@code MethodUtils} when it was imported from Commons
 * BeanUtils.
 *
 * @author Haixing Hu
 */
abstract class MemberUtils {

  private static final int NON_PACKAGE_ACCESS = Modifier.PUBLIC
                                              | Modifier.PROTECTED
                                              | Modifier.PRIVATE;

  /**
   * Array of primitive number types ordered by "promotability".
   */
  private static final Class<?>[] ORDERED_PRIMITIVE_TYPES = {
      Byte.TYPE,
      Short.TYPE,
      Character.TYPE,
      Integer.TYPE,
      Long.TYPE,
      Float.TYPE,
      Double.TYPE
  };

  /**
   * Returns whether a given set of modifiers implies package access.
   *
   * @param modifiers
   *     to test
   * @return true unless package/protected/private modifier detected
   */
  static boolean isPackage(final int modifiers) {
    return (modifiers & NON_PACKAGE_ACCESS) == 0;
  }

  /**
   * Returns whether a Member is accessible.
   *
   * @param m
   *     Member to check
   * @return true if {@code m} is accessible
   */
  static boolean isAccessible(final Member m) {
    return (m != null)
        && Modifier.isPublic(m.getModifiers())
        && (!m.isSynthetic());
  }

  /**
   * Compares the relative fitness of two sets of parameter types in terms of
   * matching a third set of runtime parameter types, such that a list ordered
   * by the results of the comparison would return the best match first
   * (least).
   *
   * @param left
   *     the "left" parameter set
   * @param right
   *     the "right" parameter set
   * @param actual
   *     the runtime parameter types to match against {@code left}/ {@code
   *     right}
   * @return int consistent with {@code compare} semantics
   */
  static int compareParameterTypes(final Class<?>[] left,
      final Class<?>[] right, final Class<?>[] actual) {
    final float leftCost = getTotalTransformationCost(actual, left);
    final float rightCost = getTotalTransformationCost(actual, right);
    if (leftCost < rightCost) {
      return -1;
    } else if (rightCost < leftCost) {
      return +1;
    } else {
      return 0;
    }
  }

  /**
   * Returns the sum of the object transformation cost for each class in the
   * source argument list.
   *
   * @param srcArgs
   *     The source arguments
   * @param destArgs
   *     The destination arguments
   * @return The total transformation cost
   */
  private static float getTotalTransformationCost(final Class<?>[] srcArgs,
      final Class<?>[] destArgs) {
    float totalCost = 0.0f;
    for (int i = 0; i < srcArgs.length; i++) {
      final Class<?> srcClass;
      final Class<?> destClass;
      srcClass = srcArgs[i];
      destClass = destArgs[i];
      totalCost += getObjectTransformationCost(srcClass, destClass);
    }
    return totalCost;
  }

  /**
   * Gets the number of steps required needed to turn the source class into the
   * destination class. This represents the number of steps in the object
   * hierarchy graph.
   *
   * @param srcClass
   *     The source class
   * @param destClass
   *     The destination class
   * @return The cost of transforming an object
   */
  private static float getObjectTransformationCost(final Class<?> srcClass,
      final Class<?> destClass) {
    if (destClass.isPrimitive()) {
      return getPrimitivePromotionCost(srcClass, destClass);
    }
    // stop checkstyle: MagicNumberCheck
    float cost = 0.0f;
    Class<?> src = srcClass;
    while ((src != null) && !destClass.equals(src)) {
      if (destClass.isInterface() && ClassUtils.isAssignable(src, destClass)) {
        // slight penalty for interface match.
        // we still want an exact match to override an interface match,
        // but an interface match should override anything where we have to
        // get a superclass.
        cost += 0.25f;
        break;
      }
      cost++;
      src = src.getSuperclass();
    }
    /*
     * If the destination class is null, we've traveled all the way up to an
     * Object match. We'll penalize this by adding 1.5 to the cost.
     */
    if (src == null) {
      cost += 1.5f;
    }
    // resume checkstyle: MagicNumberCheck
    return cost;
  }

  /**
   * Gets the number of steps required to promote a primitive number to another
   * type.
   *
   * @param srcClass
   *     the (primitive) source class
   * @param destClass
   *     the (primitive) destination class
   * @return The cost of promoting the primitive
   */
  private static float getPrimitivePromotionCost(final Class<?> srcClass,
      final Class<?> destClass) {
    // stop checkstyle: MagicNumberCheck
    float cost = 0.0f;
    Class<?> cls = srcClass;
    if (!cls.isPrimitive()) {
      // slight unwrapping penalty
      cost += 0.1f;
      cls = ClassUtils.wrapperToPrimitive(cls);
    }
    for (int i = 0; (cls != destClass) && (i < ORDERED_PRIMITIVE_TYPES.length); ++i) {
      if (cls == ORDERED_PRIMITIVE_TYPES[i]) {
        cost += 0.1f;
        if (i < (ORDERED_PRIMITIVE_TYPES.length - 1)) {
          cls = ORDERED_PRIMITIVE_TYPES[i + 1];
        }
      }
    }
    // resume checkstyle: MagicNumberCheck
    return cost;
  }

}