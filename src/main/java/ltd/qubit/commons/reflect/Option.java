////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Defines the options used in reflection operations.
 *
 * @author Haixing Hu
 */
public class Option {

  /**
   * Indicates that the operation is also applied to the members declared in the
   * ancestor class or ancestor interfaces of the specified class.
   */
  public static final int ANCESTOR = 0x0001;

  /**
   * Indicates that the operation is applied to the static members.
   */
  public static final int STATIC = 0x0002;

  /**
   * Indicates that the operation is applied to the non-static members.
   */
  public static final int NON_STATIC = 0x0004;

  /**
   * Indicates that the operation is applied to the private accessible members.
   *
   * <p>Note that a member is private accessible if it satisfies any of the
   * following conditions:
   * <ul>
   * <li>It is declared in the specified class as a private member.</li>
   * <li>It is declared in an ancestor class of the specified class as a private
   * member.</li>
   * </ul>
   */
  public static final int PRIVATE = 0x0008;

  /**
   * Indicates that the operation is applied to the package accessible members.
   *
   * <p>Note that a member is package accessible if it satisfies any of the
   * following conditions:
   * <ul>
   * <li>It is declared in the specified class as a member without access
   * modifier.</li>
   * <li>It is declared in an ancestor class of the specified class as a member
   * without access modifier.</li>
   * <li>It is declared in an ancestor class of the specified class as a
   * non-private member, and the ancestor class is declared without access
   * modifier.</li>
   * </ul>
   */
  public static final int PACKAGE = 0x0010;

  /**
   * Indicates that the operation is applied to the protected accessible
   * members.
   *
   * <p>Note that a member is protected accessible if it satisfies any of the
   * following conditions:
   * <ul>
   * <li>It is declared in the specified class as a protected member.</li>
   * <li>It is declared in an ancestor class of the specified class as a
   * protected member, and the ancestor class is declared with a public
   * modifier.</li>
   * </ul>
   */
  public static final int PROTECTED = 0x0020;

  /**
   * Indicates that the operation is applied to the public accessible members.
   *
   * <p>Note that a member is public accessible if it satisfies any of the
   * following conditions:
   * <ul>
   * <li>It is declared in the specified class as a public member.</li>
   * <li>It is declared in an ancestor class of the specified class as a public
   * member, and the ancestor class is declared with a public modifier.</li>
   * </ul>
   */
  public static final int PUBLIC = 0x0040;

  /**
   * Indicates that the operation will try to utility the serialization
   * mechanics of Java.
   *
   * <p>This option is only useful in the invoking of default constructors.
   */
  public static final int SERIALIZATION = 0x0080;

  /**
   * Indicates that the operation is applied to the bridge methods.
   *
   */
  public static final int BRIDGE = 0x0100;

  /**
   * Indicates that the operation is applied to the non-bridge methods.
   *
   */
  public static final int NON_BRIDGE = 0x0200;

  /**
   * Indicates that the operation is applied to the native methods.
   */
  public static final int NATIVE = 0x0400;

  /**
   * Indicates that the operation is applied to the non-native methods.
   */
  public static final int NON_NATIVE = 0x0800;

  /**
   * Indicates that the operation is applied to the private native methods.
   */
  public static final int PRIVATE_NATIVE = 0x1000;

  /**
   * Indicates that the operation is applied to the non-private-native methods.
   */
  public static final int NON_PRIVATE_NATIVE = 0x2000;

  /**
   * Indicates that the operation will exclude the overridden members.
   *
   * <p>If two or more members have the same signature, the operation will keep
   * the one with the shallower depth; if there are more than one member has
   * the specified signature in the same depth, the operation will returns
   * the one with a more precised returned type (for methods); otherwise, the
   * operation will throw an {@link AmbiguousMemberException}.
   */
  public static final int EXCLUDE_OVERRIDDEN = 0x4000;

  /**
   * Indicates that the operation is applied to private, package, protected,
   * and public members.
   */
  public static final int ALL_ACCESS = PRIVATE | PACKAGE | PROTECTED | PUBLIC;

  /**
   * Indicates that the operation is applied to all members, including static
   * members, non-public members, bridge methods, non-bridge methods, native
   * methods, non-native methods, and those members declared in the ancestor
   * class or ancestor interfaces of the specified class.
   */
  public static final int ALL = ANCESTOR
      | STATIC | NON_STATIC
      | ALL_ACCESS
      | SERIALIZATION
      | BRIDGE | NON_BRIDGE
      | NATIVE | NON_NATIVE
      | PRIVATE_NATIVE | NON_PRIVATE_NATIVE;

  /**
   * Indicates that the operation is applied to all members, excluding bridge
   * methods.
   */
  public static final int ALL_EXCLUDE_BRIDGE = ANCESTOR
      | STATIC | NON_STATIC
      | ALL_ACCESS
      | SERIALIZATION
      | NON_BRIDGE
      | NATIVE | NON_NATIVE
      | PRIVATE_NATIVE | NON_PRIVATE_NATIVE;

  /**
   * Indicates that the operation is applied to all members, excluding private
   * native methods.
   */
  public static final int ALL_EXCLUDE_PRIVATE_NATIVE = ANCESTOR
      | STATIC | NON_STATIC
      | ALL_ACCESS
      | SERIALIZATION
      | BRIDGE | NON_BRIDGE
      | NATIVE | NON_NATIVE
      | NON_PRIVATE_NATIVE;

  /**
   * Indicates that the operation is applied to all members, excluding bridge
   * methods and native methods.
   */
  public static final int ALL_EXCLUDE_BRIDGE_PRIVATE_NATIVE = ANCESTOR
      | STATIC | NON_STATIC
      | ALL_ACCESS
      | SERIALIZATION
      | NON_BRIDGE
      | NATIVE | NON_NATIVE
      | NON_PRIVATE_NATIVE;

  /**
   * The options for Java bean's getter/setter methods.
   */
  public static final int BEAN_METHOD = ANCESTOR
      | NON_STATIC
      | PUBLIC
      | NON_BRIDGE
      | NATIVE | NON_NATIVE
      | PRIVATE_NATIVE | NON_PRIVATE_NATIVE
      | EXCLUDE_OVERRIDDEN;

  /**
   * The options for Java bean's fields.
   */
  public static final int BEAN_FIELD = ANCESTOR | NON_STATIC | ALL_ACCESS;

  /**
   * Indicates that the options is applied to static or non-static public members of the
   * specified class or its super classes/interfaces.
   */
  public static final int ANCESTOR_PUBLIC = ANCESTOR
      | STATIC | NON_STATIC
      | BRIDGE | NON_BRIDGE
      | NATIVE | NON_NATIVE
      | PRIVATE_NATIVE | NON_PRIVATE_NATIVE
      | PUBLIC;

  /**
   * The default options, which is only applied to non-static members declared
   * in the specified class, with all possible accessibility.
   */
  public static final int DEFAULT = NON_STATIC
      | NON_BRIDGE
      | NATIVE | NON_NATIVE
      | NON_PRIVATE_NATIVE
      | ALL_ACCESS
      | SERIALIZATION;

  /**
   * The default options, which is only applied to non-static members declared
   * in the specified class, with all possible accessibility.
   */
  public static final int DEFAULT_PUBLIC = NON_STATIC
      | NON_BRIDGE
      | NATIVE | NON_NATIVE
      | NON_PRIVATE_NATIVE
      | ANCESTOR
      | PUBLIC;

  /**
   * Tests whether a member of a class satisfies the specified options.
   *
   * @param type
   *          a class.
   * @param member
   *          a member of the class.
   * @param options
   *          the options.
   * @return {@code true} if the member of the class satisfies the options;
   *         {@code false} otherwise.
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
   * Converts an options to a string representation.
   *
   * @param options the options.
   * @return the string representation of the options.
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
