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
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 定义在反射操作中使用的选项。
 *
 * @author 胡海星
 */
public class Option {

  /**
   * 表示该操作也应用于指定类的祖先类或祖先接口中声明的成员。
   */
  public static final int ANCESTOR = 0x0001;

  /**
   * 表示该操作应用于静态成员。
   */
  public static final int STATIC = 0x0002;

  /**
   * 表示该操作应用于非静态成员。
   */
  public static final int NON_STATIC = 0x0004;

  /**
   * 表示该操作应用于私有可访问的成员。
   *
   * <p>请注意，如果成员满足以下任何条件，则该成员是私有可访问的：
   * <ul>
   * <li>它在指定类中被声明为私有成员。</li>
   * <li>它在指定类的祖先类中被声明为私有成员。</li>
   * </ul>
   */
  public static final int PRIVATE = 0x0008;

  /**
   * 表示该操作应用于包可访问的成员。
   *
   * <p>请注意，如果成员满足以下任何条件，则该成员是包可访问的：
   * <ul>
   * <li>它在指定类中被声明为没有访问修饰符的成员。</li>
   * <li>它在指定类的祖先类中被声明为没有访问修饰符的成员。</li>
   * <li>它在指定类的祖先类中被声明为非私有成员，并且祖先类被声明为没有访问修饰符。</li>
   * </ul>
   */
  public static final int PACKAGE = 0x0010;

  /**
   * 表示该操作应用于受保护可访问的成员。
   *
   * <p>请注意，如果成员满足以下任何条件，则该成员是受保护可访问的：
   * <ul>
   * <li>它在指定类中被声明为受保护成员。</li>
   * <li>它在指定类的祖先类中被声明为受保护成员，并且祖先类被声明为公共修饰符。</li>
   * </ul>
   */
  public static final int PROTECTED = 0x0020;

  /**
   * 表示该操作应用于公共可访问的成员。
   *
   * <p>请注意，如果成员满足以下任何条件，则该成员是公共可访问的：
   * <ul>
   * <li>它在指定类中被声明为公共成员。</li>
   * <li>它在指定类的祖先类中被声明为公共成员，并且祖先类被声明为公共修饰符。</li>
   * </ul>
   */
  public static final int PUBLIC = 0x0040;

  /**
   * 表示该操作将尝试利用Java的序列化机制。
   *
   * <p>此选项仅在调用默认构造函数时有用。
   */
  public static final int SERIALIZATION = 0x0080;

  /**
   * 表示该操作应用于桥接方法。
   *
   */
  public static final int BRIDGE = 0x0100;

  /**
   * 表示该操作应用于非桥接方法。
   *
   */
  public static final int NON_BRIDGE = 0x0200;

  /**
   * 表示该操作应用于本地方法。
   */
  public static final int NATIVE = 0x0400;

  /**
   * 表示该操作应用于非本地方法。
   */
  public static final int NON_NATIVE = 0x0800;

  /**
   * 表示该操作应用于私有本地方法。
   */
  public static final int PRIVATE_NATIVE = 0x1000;

  /**
   * 表示该操作应用于非私有本地方法。
   */
  public static final int NON_PRIVATE_NATIVE = 0x2000;

  /**
   * 表示该操作将排除被重写的成员。
   *
   * <p>如果两个或更多成员具有相同的签名，操作将保留深度较浅的那个；
   * 如果在相同深度中有多个成员具有指定的签名，操作将返回具有更精确返回类型的那个（对于方法）；
   * 否则，操作将抛出 {@link AmbiguousMemberException}。
   */
  public static final int EXCLUDE_OVERRIDDEN = 0x4000;

  /**
   * 表示该操作应用于私有、包、受保护和公共成员。
   */
  public static final int ALL_ACCESS = PRIVATE | PACKAGE | PROTECTED | PUBLIC;

  /**
   * 表示该操作应用于所有成员，包括静态成员、非公共成员、桥接方法、非桥接方法、
   * 本地方法、非本地方法，以及在指定类的祖先类或祖先接口中声明的那些成员。
   */
  public static final int ALL = ANCESTOR
      | STATIC | NON_STATIC
      | ALL_ACCESS
      | SERIALIZATION
      | BRIDGE | NON_BRIDGE
      | NATIVE | NON_NATIVE
      | PRIVATE_NATIVE | NON_PRIVATE_NATIVE;

  /**
   * 表示该操作应用于所有成员，但排除桥接方法。
   */
  public static final int ALL_EXCLUDE_BRIDGE = ANCESTOR
      | STATIC | NON_STATIC
      | ALL_ACCESS
      | SERIALIZATION
      | NON_BRIDGE
      | NATIVE | NON_NATIVE
      | PRIVATE_NATIVE | NON_PRIVATE_NATIVE;

  /**
   * 表示该操作应用于所有成员，但排除私有本地方法。
   */
  public static final int ALL_EXCLUDE_PRIVATE_NATIVE = ANCESTOR
      | STATIC | NON_STATIC
      | ALL_ACCESS
      | SERIALIZATION
      | BRIDGE | NON_BRIDGE
      | NATIVE | NON_NATIVE
      | NON_PRIVATE_NATIVE;

  /**
   * 表示该操作应用于所有成员，但排除桥接方法和本地方法。
   */
  public static final int ALL_EXCLUDE_BRIDGE_PRIVATE_NATIVE = ANCESTOR
      | STATIC | NON_STATIC
      | ALL_ACCESS
      | SERIALIZATION
      | NON_BRIDGE
      | NATIVE | NON_NATIVE
      | NON_PRIVATE_NATIVE;

  /**
   * Java bean的getter/setter方法选项。
   */
  public static final int BEAN_METHOD = ANCESTOR
      | NON_STATIC
      | PUBLIC
      | NON_BRIDGE
      | NATIVE | NON_NATIVE
      | PRIVATE_NATIVE | NON_PRIVATE_NATIVE
      | EXCLUDE_OVERRIDDEN;

  /**
   * Java bean的字段选项。
   */
  public static final int BEAN_FIELD = ANCESTOR | NON_STATIC | ALL_ACCESS;

  /**
   * 表示该选项应用于指定类或其超类/接口的静态或非静态公共成员。
   */
  public static final int ANCESTOR_PUBLIC = ANCESTOR
      | STATIC | NON_STATIC
      | BRIDGE | NON_BRIDGE
      | NATIVE | NON_NATIVE
      | PRIVATE_NATIVE | NON_PRIVATE_NATIVE
      | PUBLIC;

  /**
   * 默认选项，仅应用于在指定类中声明的非静态成员，具有所有可能的可访问性。
   */
  public static final int DEFAULT = NON_STATIC
      | NON_BRIDGE
      | NATIVE | NON_NATIVE
      | NON_PRIVATE_NATIVE
      | ANCESTOR
      | ALL_ACCESS
      | SERIALIZATION;

  /**
   * 默认选项，仅应用于在指定类中声明的非静态成员，具有所有可能的可访问性。
   */
  public static final int DEFAULT_PUBLIC = NON_STATIC
      | NON_BRIDGE
      | NATIVE | NON_NATIVE
      | NON_PRIVATE_NATIVE
      | ANCESTOR
      | PUBLIC;

  /**
   * 测试类的成员是否满足指定的选项。
   *
   * @param type
   *          一个类。
   * @param member
   *          类的成员。
   * @param options
   *          选项。
   * @return 如果类的成员满足选项，则返回 {@code true}；否则返回 {@code false}。
   */
  public static boolean satisfy(final Class<?> type, final Member member,
      final int options) {
    if (options == ALL) {
      return true;
    }
    if ((options & ANCESTOR) == 0) {
      if (!type.equals(member.getDeclaringClass())) {
        return false;
      }
    }
    final int modifiers = member.getModifiers();
    final int declaringClassModifiers = member.getDeclaringClass().getModifiers();
    if ((options & STATIC) == 0) {
      if (Modifier.isStatic(modifiers)) {
        return false;
      }
    }
    if ((options & NON_STATIC) == 0) {
      if (!Modifier.isStatic(modifiers)) {
        return false;
      }
    }
    if ((options & PRIVATE) == 0) {
      if (Modifier.isPrivate(modifiers)) {
        return false;
      }
    }
    if ((options & PACKAGE) == 0) {
      if (MemberUtils.isPackage(modifiers)) {
        return false;
      } else if ((! Modifier.isPrivate(modifiers))
          && MemberUtils.isPackage(declaringClassModifiers)) {
        return false;
      }
    }
    if ((options & PROTECTED) == 0) {
      if (Modifier.isProtected(modifiers)
          && Modifier.isPublic(declaringClassModifiers)) {
        return false;
      }
    }
    if ((options & PUBLIC) == 0) {
      if (Modifier.isPublic(modifiers)
           && Modifier.isPublic(declaringClassModifiers)) {
        return false;
      }
    }
    if (member instanceof Method) {
      final Method method = (Method) member;
      if ((options & BRIDGE) == 0) {
        if (method.isBridge()) {
          return false;
        }
      }
      if ((options & NON_BRIDGE) == 0) {
        if (! method.isBridge()) {
          return false;
        }
      }
      if ((options & NATIVE) == 0) {
        if (Modifier.isNative(modifiers)) {
          return false;
        }
      }
      if ((options & NON_NATIVE) == 0) {
        if (! Modifier.isNative(modifiers)) {
          return false;
        }
      }
      if ((options & PRIVATE_NATIVE) == 0) {
        if (Modifier.isPrivate(modifiers) && Modifier.isNative(modifiers)) {
          return false;
        }
      }
      if ((options & NON_PRIVATE_NATIVE) == 0) {
        if (! (Modifier.isPrivate(modifiers) && Modifier.isNative(modifiers))) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * 将选项转换为字符串表示。
   *
   * @param options 选项。
   * @return 选项的字符串表示。
   */
  public static String toString(final int options) {
    final StringBuilder builder = new StringBuilder();
    boolean first = true;
    if ((options & ANCESTOR) != 0) {
      builder.append("ancestor");
      first = false;
    }
    if ((options & STATIC) != 0) {
      if (! first) {
        builder.append(',');
      }
      builder.append("static");
      first = false;
    }
    if ((options & NON_STATIC) != 0) {
      if (! first) {
        builder.append(',');
      }
      builder.append("non-static");
      first = false;
    }
    if ((options & PRIVATE) != 0) {
      if (! first) {
        builder.append(',');
      }
      builder.append("private");
      first = false;
    }
    if ((options & PACKAGE) != 0) {
      if (! first) {
        builder.append(',');
      }
      builder.append("package");
      first = false;
    }
    if ((options & PROTECTED) != 0) {
      if (! first) {
        builder.append(',');
      }
      builder.append("protected");
      first = false;
    }
    if ((options & PUBLIC) != 0) {
      if (! first) {
        builder.append(',');
      }
      builder.append("public");
      first = false;
    }
    return builder.toString();
  }
}