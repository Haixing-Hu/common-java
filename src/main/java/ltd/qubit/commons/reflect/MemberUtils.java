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
 * 包含处理方法/构造器的通用代码，从 Commons BeanUtils 导入时
 * 从 {@code MethodUtils} 中提取和重构而来。
 *
 * @author 胡海星
 */
abstract class MemberUtils {

  /**
   * 非包访问的修饰符。
   */
  private static final int NON_PACKAGE_ACCESS = Modifier.PUBLIC
                                              | Modifier.PROTECTED
                                              | Modifier.PRIVATE;

  /**
   * 按"可提升性"排序的基本数字类型数组。
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
   * 返回给定的修饰符集合是否表示包访问。
   *
   * @param modifiers
   *     要测试的修饰符
   * @return 除非检测到包/受保护/私有修饰符，否则返回true
   */
  static boolean isPackage(final int modifiers) {
    return (modifiers & NON_PACKAGE_ACCESS) == 0;
  }

  /**
   * 返回成员是否可访问。
   *
   * @param m
   *     要检查的成员
   * @return 如果 {@code m} 可访问则返回true
   */
  static boolean isAccessible(final Member m) {
    return (m != null)
        && Modifier.isPublic(m.getModifiers())
        && (!m.isSynthetic());
  }

  /**
   * 比较两组参数类型在匹配第三组运行时参数类型方面的相对适合度，
   * 这样通过比较结果排序的列表将返回最佳匹配（最小值）。
   *
   * @param left
   *     "左侧"参数集合
   * @param right
   *     "右侧"参数集合
   * @param actual
   *     要与 {@code left}/{@code right} 匹配的运行时参数类型
   * @return 与 {@code compare} 语义一致的int值
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
   * 返回源参数列表中每个类的对象转换成本的总和。
   *
   * @param srcArgs
   *     源参数
   * @param destArgs
   *     目标参数
   * @return 总转换成本
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
   * 获取将源类转换为目标类所需的步骤数。这表示对象层次结构图中的步骤数。
   *
   * @param srcClass
   *     源类
   * @param destClass
   *     目标类
   * @return 转换对象的成本
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
   * 获取将基本数字提升为另一种类型所需的步骤数。
   *
   * @param srcClass
   *     （基本类型）源类
   * @param destClass
   *     （基本类型）目标类
   * @return 提升基本类型的成本
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