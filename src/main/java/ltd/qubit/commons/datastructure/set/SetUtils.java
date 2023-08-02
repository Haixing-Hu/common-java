////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.set;

import java.util.Set;
import java.util.function.Predicate;

/**
 * Provides utility functions about the set.
 *
 * @author Haixing Hu
 */
public class SetUtils {

  public static <T> boolean containsAny(final Set<T> set, final T[] values) {
    for (final T value : values) {
      if (set.contains(value)) {
        return true;
      }
    }
    return false;
  }

  public static <T> boolean containsAny(final Set<T> set, final Predicate<T> predicate) {
    for (final T value : set) {
      if (predicate.test(value)) {
        return true;
      }
    }
    return false;
  }

  public static <T> boolean containsAll(final Set<T> set, final T[] values) {
    for (final T value : values) {
      if (!set.contains(value)) {
        return false;
      }
    }
    return true;
  }

  public static <T> boolean containsAll(final Set<T> set, final Predicate<T> predicate) {
    for (final T value : set) {
      if (!predicate.test(value)) {
        return false;
      }
    }
    return true;
  }
}
