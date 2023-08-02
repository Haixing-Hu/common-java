////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import java.lang.reflect.Method;
import java.util.Comparator;

/**
 * A method comparator comparing methods by their names.
 *
 * @author Haixing Hu
 */
public class MethodByNameComparator implements Comparator<Method> {

  @Override
  public int compare(final Method m1, final Method m2) {
    if (m1 == null) {
      return (m2 == null ? 0 : -1);
    } else if (m2 == null) {
      return +1;
    } else {
      return m1.getName().compareTo(m2.getName());
    }
  }
}
