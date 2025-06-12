////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import java.lang.reflect.Method;
import java.util.Comparator;

/**
 * 按方法名称比较方法的方法比较器。
 *
 * @author 胡海星
 */
public class MethodByNameComparator implements Comparator<Method> {

  /**
   * 比较两个方法的名称。
   *
   * @param m1
   *     第一个方法
   * @param m2
   *     第二个方法
   * @return
   *     如果m1小于m2，则返回一个负整数；如果m1大于m2，则返回一个正整数；如果m1和m2相等，则返回0。
   */
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