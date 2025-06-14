////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import ltd.qubit.commons.lang.ClassUtils;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.reflect.impl.NonVoidMethod0;
import ltd.qubit.commons.reflect.impl.NonVoidMethod1;
import ltd.qubit.commons.reflect.impl.NonVoidMethod1Boolean;
import ltd.qubit.commons.reflect.impl.NonVoidMethod1Byte;
import ltd.qubit.commons.reflect.impl.NonVoidMethod2;
import ltd.qubit.commons.reflect.impl.NonVoidMethod3;
import ltd.qubit.commons.reflect.impl.NonVoidMethod4;
import ltd.qubit.commons.reflect.impl.NonVoidMethod5;
import ltd.qubit.commons.reflect.impl.NonVoidMethod6;
import ltd.qubit.commons.reflect.impl.NonVoidMethod7;
import ltd.qubit.commons.reflect.impl.NonVoidMethod8;
import ltd.qubit.commons.reflect.impl.NonVoidMethod9;
import ltd.qubit.commons.reflect.impl.ReferenceToMethodCache;
import ltd.qubit.commons.reflect.impl.VoidMethod0;
import ltd.qubit.commons.reflect.impl.VoidMethod1;
import ltd.qubit.commons.reflect.impl.VoidMethod1Boolean;
import ltd.qubit.commons.reflect.impl.VoidMethod1Byte;
import ltd.qubit.commons.reflect.impl.VoidMethod2;
import ltd.qubit.commons.reflect.impl.VoidMethod3;
import ltd.qubit.commons.reflect.impl.VoidMethod4;
import ltd.qubit.commons.reflect.impl.VoidMethod5;
import ltd.qubit.commons.reflect.impl.VoidMethod6;
import ltd.qubit.commons.reflect.impl.VoidMethod7;
import ltd.qubit.commons.reflect.impl.VoidMethod8;
import ltd.qubit.commons.reflect.impl.VoidMethod9;

import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.lang.ArrayUtils.EMPTY_CLASS_ARRAY;
import static ltd.qubit.commons.lang.ArrayUtils.EMPTY_OBJECT_ARRAY;
import static ltd.qubit.commons.lang.ClassUtils.isAssignable;
import static ltd.qubit.commons.lang.ObjectUtils.defaultIfNull;
import static ltd.qubit.commons.reflect.AccessibleUtils.withAccessibleObject;
import static ltd.qubit.commons.reflect.MemberUtils.compareParameterTypes;
import static ltd.qubit.commons.reflect.Option.ALL_EXCLUDE_BRIDGE;
import static ltd.qubit.commons.reflect.Option.DEFAULT;
import static ltd.qubit.commons.reflect.impl.GetMethodByReferenceImpl.GETTER_METHOD_CACHES;
import static ltd.qubit.commons.reflect.impl.GetMethodByReferenceImpl.findMethod;
import static ltd.qubit.commons.reflect.impl.GetMethodByReferenceThroughSerialization.findMethodBySerialization;
import static ltd.qubit.commons.reflect.impl.GetRecordMethodByReferenceImpl.findRecordMethod;

/**
 * 提供专注于方法的反射工具方法，最初来自 Commons BeanUtils。
 * 与 BeanUtils 版本的差异可能会被注明，特别是在 Lang 中已经存在类似功能的地方。
 *
 * <h2>已知限制</h2>
 * <h3>在默认访问超类中访问公共方法</h3>
 *
 * <p>在 JRE 1.4 之前的版本中，当调用包含在默认访问超类中的公共方法时存在问题。
 * 反射可以正确地定位这些方法并正确地将它们分配为公共方法。但是，如果调用该方法，
 * 则会抛出 {@code IllegalAccessException}。
 *
 * <p>{@code MethodUtils} 包含针对这种情况的解决方法。它将尝试在此方法上调用
 * {@code setAccessible}。如果此调用成功，则可以正常调用该方法。此调用仅在应用程序
 * 具有足够的安全权限时才会成功。如果此调用失败，则该方法可能会失败。
 *
 * @author 胡海星
 */
@SuppressWarnings("overloads")
@ThreadSafe
public class MethodUtils {

  public static final String[] IGNORED_METHOD_PREFIXES = {
      "$jacoco",    //  the prefix of methods instrumented by JaCoCo
      "__CLR",      //  the prefix of methods instrumented by OpenClover
  };

  private static final Map<TypeInfo, List<MethodInfo>> METHOD_CACHE = new HashMap<>();

  private static boolean shouldIgnore(final Method method) {
    final String name = method.getName();
    for (final String prefix : IGNORED_METHOD_PREFIXES) {
      if (name.startsWith(prefix)) {
        return true;       // ignore the method with the specified prefix
      }
    }
    return false;
  }

  private static void buildMethodCache(final TypeInfo info) {
    if (METHOD_CACHE.containsKey(info)) {
      return;
    }
    final Class<?> type = info.getType();
    final List<MethodInfo> methods = new ArrayList<>();
    final Type genericSuperclass = type.getGenericSuperclass();
    if (genericSuperclass != null) {
      final TypeInfo superInfo = new TypeInfo(genericSuperclass, info);
      buildMethodCache(superInfo); // recursively
      final List<MethodInfo> superMethods = METHOD_CACHE.get(superInfo);
      for (final MethodInfo m : superMethods) {
        methods.add(new MethodInfo(m, m.getDepth() + 1));
      }
    }
    for (final Type genericInterface : type.getGenericInterfaces()) {
      final TypeInfo superInfo = new TypeInfo(genericInterface, info);
      buildMethodCache(superInfo); // recursively
      final List<MethodInfo> superMethods = METHOD_CACHE.get(superInfo);
      for (final MethodInfo m : superMethods) {
        methods.add(new MethodInfo(m, m.getDepth() + 1));
      }
    }
    for (final Method method : type.getDeclaredMethods()) {
      if (shouldIgnore(method)) {
        continue;       // ignore the method with the specified prefix
      }
      methods.add(new MethodInfo(info, method, 0));
    }
    // sort the methods
    Collections.sort(methods);
    METHOD_CACHE.put(info, methods);
  }

  private static synchronized List<MethodInfo> getAllMethodInfos(final Class<?> type) {
    final TypeInfo info = new TypeInfo(type);
    buildMethodCache(info);
    return METHOD_CACHE.get(info);
  }

  /**
   * 获取类的所有方法。
   *
   * @param type
   *     要获取方法的类。
   * @param options
   *     在 {@link Option} 类中定义的反射选项的按位组合。
   *     默认值可以是 {@link Option#DEFAULT}。
   * @return 所有指定方法的数组；如果没有此类方法，则返回空数组。
   * @throws ReflectionException
   *     如果发生任何错误。
   */
  public static List<Method> getAllMethods(final Class<?> type, final int options)
      throws ReflectionException {
    final List<MethodInfo> members = getAllMethodInfos(type);
    if ((options & Option.EXCLUDE_OVERRIDDEN) == 0) {
      return getAllMethodsKeepOverridden(type, members, options);
    } else {
      return getAllMethodsExcludeOverridden(type, members, options);
    }
  }

  private static List<Method> getAllMethodsKeepOverridden(final Class<?> type,
      final List<MethodInfo> members, final int options) {
    final List<Method> result = new ArrayList<>();
    for (final MethodInfo m : members) {
      final Method method = m.getMethod();
      if (Option.satisfy(type, method, options)) {
        result.add(method);
      }
    }
    return result;
  }

  private static List<Method> getAllMethodsExcludeOverridden(final Class<?> type,
      final List<MethodInfo> members, final int options) {
    final Map<MethodSignature, MethodInfo> methodMap = new HashMap<>();
    boolean ambiguous = false;
    String ambiguousName = "";
    for (final MethodInfo m : members) {
      final Method method = m.getMethod();
      if (Option.satisfy(type, method, options)) {
        final MethodSignature signature = m.getSignature();
        if (methodMap.containsKey(signature)) {
          final MethodInfo exist = methodMap.get(signature);
          if (exist.getDepth() > m.getDepth()) {
            // keep the shallow one
            methodMap.put(signature, m);
          } else if (exist.getDepth() == m.getDepth()) {
            final Class<?> existReturn = exist.getActualReturnType();
            final Class<?> currentReturn = m.getActualReturnType();
            if (existReturn.isAssignableFrom(currentReturn)) {
              // keep the method with more precise returned type
              methodMap.put(signature, m);
            } else {
              ambiguous = true;
              ambiguousName = exist.getName();
            }
          }
        } else {
          methodMap.put(signature, m);
        }
      }
    }
    if (ambiguous) {
      throw new AmbiguousMemberException(type, ambiguousName);
    }
    final List<MethodInfo> infos = new ArrayList<>(methodMap.values());
    Collections.sort(infos);
    final List<Method> result = new ArrayList<>();
    for (final MethodInfo info : infos) {
      result.add(info.getMethod());
    }
    return result;
  }

  /**
   * 获取类的匹配给定名称和给定参数类型的方法。
   *
   * <p>注意：如果 {@code options} 参数包含 {@link Option#ANCESTOR}，
   * 并且在指定类或其祖先类或其祖先接口中声明了多个具有指定名称和参数类型的方法，
   * 该函数将尝试返回深度较浅的方法；如果有多个方法在同一深度具有指定名称，
   * 该函数将返回具有更精确返回类型的方法；否则，该函数将抛出 {@link AmbiguousMemberException}。
   *
   * @param type
   *     要获取方法的类。
   * @param options
   *     在 {@link Option} 类中定义的反射选项的按位组合。
   *     默认值可以是 {@link Option#DEFAULT}。
   * @param name
   *     要获取的方法的名称。
   * @param paramTypes
   *     要获取的方法的参数类型，如果要获取的方法没有参数，则为 {@code null} 或空数组。
   * @return 指定的方法，如果没有此类字段，则返回 {@code null}。
   * @throws AmbiguousMemberException
   *     如果找到两个或更多匹配的方法。
   * @throws ReflectionException
   *     如果发生任何错误。
   */
  public static Method getMethod(final Class<?> type, final int options, final String name,
      @Nullable final Class<?>[] paramTypes) throws ReflectionException {
    requireNonNull("name", name);
    final List<MethodInfo> methodInfos = getAllMethodInfos(type);
    MethodInfo result = null;
    boolean ambiguous = false;
    final Class<?>[] theParamTypes = defaultIfNull(paramTypes, EMPTY_CLASS_ARRAY);
    for (final MethodInfo info : methodInfos) {
      final Method method = info.getMethod();
      if (Option.satisfy(type, method, options)
          && name.equals(method.getName())
          && Equality.equals(theParamTypes, method.getParameterTypes())) {
        if (result == null) {
          result = info;
          ambiguous = false;
        } else if (result.getDepth() > info.getDepth()) {
          result = info; // keep the method with shallower depth
          ambiguous = false;
        } else if (result.getDepth() == info.getDepth()) {
          final Class<?> resultReturn = result.getActualReturnType();
          final Class<?> currentReturn = info.getActualReturnType();
          if (resultReturn.isAssignableFrom(currentReturn)) {
            result = info; // keep the method with more precise returned type
            ambiguous = false;
          } else {
            ambiguous = true;
          }
        }
      }
    }
    if (ambiguous) {
      throw new AmbiguousMemberException(type, name);
    }
    return (result == null ? null : result.getMethod());
  }

  /**
   * 判定指定的类是否含有指定的方法。
   *
   * @param type
   *     指定的类。
   * @param options
   *     指定的方法的属性。
   * @param name
   *     指定的方法的名称。
   * @param paramTypes
   *     指定的方法的参数类型列表。
   * @return
   *     指定的类是否含有指定的方法。
   * @throws ReflectionException
   *     若发生任何反射操作错误。
   */
  public static boolean hasMethod(final Class<?> type, final int options,
      final String name, @Nullable final Class<?>[] paramTypes)
      throws ReflectionException {
    requireNonNull("name", name);
    final List<MethodInfo> methodInfos = getAllMethodInfos(type);
    final Class<?>[] theParamTypes = defaultIfNull(paramTypes, EMPTY_CLASS_ARRAY);
    for (final MethodInfo info : methodInfos) {
      final Method m = info.getMethod();
      if (Option.satisfy(type, m, options)
          && name.equals(m.getName())
          && Equality.equals(theParamTypes, m.getParameterTypes())) {
        return true;
      }
    }
    return false;
  }

  /**
   * 获取类的匹配给定名称和兼容参数类型的方法。
   *
   * <p>注意：如果 {@code options} 参数包含 {@link Option#ANCESTOR}，
   * 并且在指定类或其祖先类或其祖先接口中声明了多个具有指定名称和参数类型的方法，
   * 该函数将尝试返回深度较浅的方法；如果有多个方法在同一深度具有指定名称，
   * 该函数将返回具有更精确返回类型的方法；否则，该函数将抛出 {@link AmbiguousMemberException}。
   *
   * @param type
   *     要获取方法的类。
   * @param name
   *     要获取的方法的名称。
   * @param paramTypes
   *     要获取的方法的兼容参数类型，如果要获取的方法没有参数，则为空数组。
   * @return 指定的方法，如果没有此类方法，则返回 {@code null}。
   * @throws AmbiguousMemberException
   *     如果找到两个或更多匹配的方法。
   * @throws ReflectionException
   *     如果发生任何错误。
   */
  @Nullable
  public static Method getMatchingMethod(final Class<?> type, final String name,
      @Nullable final Class<?>[] paramTypes) throws ReflectionException {
    return getMatchingMethod(type, DEFAULT, name, paramTypes);
  }

  /**
   * 获取类的匹配给定名称和兼容参数类型的方法。
   *
   * <p>注意：如果 {@code options} 参数包含 {@link Option#ANCESTOR}，
   * 并且在指定类或其祖先类或其祖先接口中声明了多个具有指定名称和参数类型的方法，
   * 该函数将尝试返回深度较浅的方法；如果有多个方法在同一深度具有指定名称，
   * 该函数将返回具有更精确返回类型的方法；否则，该函数将抛出 {@link AmbiguousMemberException}。
   *
   * @param type
   *     要获取方法的类。
   * @param options
   *     在 {@link Option} 类中定义的反射选项的按位组合。
   *     默认值可以是 {@link Option#DEFAULT}。
   * @param name
   *     要获取的方法的名称。
   * @param paramTypes
   *     要获取的方法的兼容参数类型，如果要获取的方法没有参数，则为空数组。
   * @return 指定的方法，如果没有此类方法，则返回 {@code null}。
   * @throws AmbiguousMemberException
   *     如果找到两个或更多匹配的方法。
   * @throws ReflectionException
   *     如果发生任何错误。
   */
  @Nullable
  public static Method getMatchingMethod(final Class<?> type, final int options,
      final String name, @Nullable final Class<?>[] paramTypes)
      throws ReflectionException {
    requireNonNull("name", name);
    final List<MethodInfo> methodInfos = getAllMethodInfos(type);
    MethodInfo result = null;
    boolean ambiguous = false;
    final Class<?>[] theParamTypes = defaultIfNull(paramTypes, EMPTY_CLASS_ARRAY);
    for (final MethodInfo current : methodInfos) {
      final Method method = current.getMethod();
      if (Option.satisfy(type, method, options)
          && name.equals(method.getName())
          && isAssignable(theParamTypes, method.getParameterTypes())) {
        if (result == null) {
          result = current;
          ambiguous = false;
        } else {
          final int rc = compareParameterTypes(current.getActualParameterType(),
              result.getActualParameterType(), theParamTypes);
          if (rc < 0) {
            // the parameter types of new method is better than the result
            result = current;
            ambiguous = false;
          } else if (rc == 0) {
            if (result.getDepth() > current.getDepth()) {
              result = current; // keep the method with shallower depth
              ambiguous = false;
            } else if (result.getDepth() == current.getDepth()) {
              ambiguous = true;
            }
          }
        }
      }
    }
    if (ambiguous) {
      throw new AmbiguousMemberException(type, name);
    }
    return (result == null ? null : result.getMethod());
  }

  /**
   * 获取类的匹配给定名称的方法。
   *
   * <p>注意：如果 {@code options} 参数包含 {@link Option#ANCESTOR}，
   * 并且在指定类或其祖先类或其祖先接口中声明了多个具有指定名称的方法，
   * 该函数将尝试返回深度较浅的方法；如果有多个方法在同一深度具有指定名称，
   * 该函数将返回具有更精确返回类型的方法；否则，该函数将抛出 {@link AmbiguousMemberException}。
   *
   * @param type
   *     要获取方法的类。
   * @param name
   *     要获取的方法的名称。
   * @return 指定的方法，如果没有此类方法，则返回 {@code null}。
   * @throws AmbiguousMemberException
   *     如果找到两个或更多匹配的方法。
   * @throws ReflectionException
   *     如果发生任何错误。
   */
  @Nullable
  public static Method getMethodByName(final Class<?> type, final String name)
      throws ReflectionException {
    return getMethodByName(type, DEFAULT, name);
  }

  /**
   * 获取类的匹配给定名称的方法。
   *
   * <p>注意：如果 {@code options} 参数包含 {@link Option#ANCESTOR}，
   * 并且在指定类或其祖先类或其祖先接口中声明了多个具有指定名称的方法，
   * 该函数将尝试返回深度较浅的方法；如果有多个方法在同一深度具有指定名称，
   * 该函数将返回具有更精确返回类型的方法；否则，该函数将抛出 {@link AmbiguousMemberException}。
   *
   * @param type
   *     要获取方法的类。
   * @param options
   *     在 {@link Option} 类中定义的反射选项的按位组合。
   *     默认值可以是 {@link Option#DEFAULT}。
   * @param name
   *     要获取的方法的名称。
   * @return 指定的方法，如果没有此类方法，则返回 {@code null}。
   * @throws AmbiguousMemberException
   *     如果找到两个或更多匹配的方法。
   * @throws ReflectionException
   *     如果发生任何错误。
   */
  @Nullable
  public static Method getMethodByName(final Class<?> type, final int options,
      final String name) throws ReflectionException {
    requireNonNull("name", name);
    final List<MethodInfo> methodInfos = getAllMethodInfos(type);
    MethodInfo result = null;
    boolean ambiguous = false;
    for (final MethodInfo m : methodInfos) {
      final Method method = m.getMethod();
      if (Option.satisfy(type, method, options) && name.equals(method.getName())) {
        if (result == null) {
          result = m;
          ambiguous = false;
        } else if (result.getDepth() > m.getDepth()) {
          result = m; // keep the method with shallower depth
          ambiguous = false;
        } else if (result.getDepth() == m.getDepth()) {
          final Class<?> resultReturn = result.getActualReturnType();
          final Class<?> currentReturn = m.getActualReturnType();
          if (resultReturn.isAssignableFrom(currentReturn)) {
            result = m; // keep the method with more precise returned type
            ambiguous = false;
          } else {
            ambiguous = true;
          }
        }
      }
    }
    if (ambiguous) {
      throw new AmbiguousMemberException(type, name);
    }
    return (result == null ? null : result.getMethod());
  }

  /**
   * 使用指定参数调用指定对象的指定方法。
   *
   * <p>此函数只是包装 IllegalArgumentException 以使消息更具指导性。
   *
   * @param method
   *     要调用的方法。
   * @param object
   *     应调用其方法的对象。
   * @param arguments
   *     用于调用方法的参数，如果方法没有参数，则可能为 {@code null} 或空。
   * @return 被调用方法返回的值。
   * @throws InvokingMethodFailedException
   *     如果发生任何错误。
   */
  public static Object invokeMethod(final Method method, final Object object,
      @Nullable final Object ... arguments) throws InvokingMethodFailedException {
    requireNonNull("object", object);
    requireNonNull("method", method);
    final Object[] theArguments = defaultIfNull(arguments, EMPTY_OBJECT_ARRAY);
    return withAccessibleObject(method, (m) -> {
      try {
        return m.invoke(object, theArguments);
      } catch (final Throwable e) {
        throw new InvokingMethodFailedException(object, method, theArguments, e);
      }
    }, true);
  }

  /**
   * 调用参数类型与给定参数类型匹配的命名方法。
   *
   * <p>此方法将方法搜索委托给 {@link #getMatchingMethod(Class, int, String, Class[])}。
   *
   * <p>此方法支持通过传入包装类来调用采用原始参数的方法。
   * 例如，{@code Boolean} 对象将匹配 {@code boolean} 原始类型。
   *
   * @param type
   *     要获取方法的类。
   * @param options
   *     在 {@link Option} 类中定义的反射选项的按位组合。
   *     默认值可以是 {@link Option#DEFAULT}。
   * @param name
   *     要获取的方法的名称。
   * @param object
   *     应调用方法的对象。如果方法是静态方法，此参数可以为 {@code null}。
   * @param arguments
   *     用于调用方法的参数。如果方法没有参数，此参数可以为 {@code null} 或空数组。
   * @return 被调用方法返回的值。
   * @throws ReflectionException
   *     如果发生任何错误。
   */
  public static Object invokeMethod(final Class<?> type, final int options,
      final String name, final Object object, @Nullable final Object[] arguments)
      throws ReflectionException {
    if (arguments == null) {
      return invokeMethod(type, options, name, null, object, null);
    } else {
      final Class<?>[] paramTypes = new Class<?>[arguments.length];
      for (int i = 0; i < arguments.length; ++i) {
        paramTypes[i] = arguments[i].getClass();
      }
      return invokeMethod(type, options, name, paramTypes, object, arguments);
    }
  }

  /**
   * 调用参数类型与给定参数类型匹配的命名方法。
   *
   * <p>此方法将方法搜索委托给 {@link #getMatchingMethod(Class, int, String, Class[])}。
   *
   * <p>此方法支持通过传入包装类来调用采用原始参数的方法。
   * 例如，{@code Boolean} 对象将匹配 {@code boolean} 原始类型。
   *
   * @param type
   *     要获取方法的类。
   * @param options
   *     在 {@link Option} 类中定义的反射选项的按位组合。
   *     默认值可以是 {@link Option#DEFAULT}。
   * @param name
   *     要获取的方法的名称。
   * @param paramTypes
   *     要获取的方法的参数类型，如果方法没有参数，则可能为 {@code null} 或空数组。
   * @param object
   *     应调用方法的对象。如果方法是静态方法，此参数可以为 {@code null}。
   * @param arguments
   *     用于调用方法的参数。如果方法没有参数，此参数可以为 {@code null} 或空数组。
   * @return 被调用方法返回的值。
   * @throws ReflectionException
   *     如果发生任何错误。
   */
  public static Object invokeMethod(final Class<?> type, final int options,
      final String name, @Nullable final Class<?>[] paramTypes,
      @Nullable final Object object, @Nullable final Object[] arguments)
      throws ReflectionException {
    final Class<?>[] theParamTypes = defaultIfNull(paramTypes, EMPTY_CLASS_ARRAY);
    final Method method = getMatchingMethod(type, options, name, theParamTypes);
    if (method == null) {
      throw new MethodNotExistException(type, options, name, theParamTypes);
    }
    return withAccessibleObject(method, (m) -> {
      try {
        return m.invoke(object, arguments);
      } catch (final Throwable e) {
        throw new InvokingMethodFailedException(type, options, name, theParamTypes, e);
      }
    }, true);
  }

  /**
   * Invokes a method whose parameter types match exactly the given argument
   * types.
   *
   * <p>This method delegates the method search to
   * {@link #getMethod(Class, int, String, Class[])}.
   *
   * @param type
   *     The class on which to get the method.
   * @param options
   *     A bitwise combination of reflection options defined in the {@link
   *     Option} class. The default value could be {@link Option#DEFAULT}.
   * @param name
   *     The name of the method to be get.
   * @param object
   *     The object on which the method should be called. If the method is a
   *     static method, this argument could be {@code null}.
   * @param arguments
   *     The arguments used to invoke the method. If the method has no argument,
   *     this argument could be {@code null} or an empty array.
   * @return The value returned by the invoked method.
   * @throws ReflectionException
   *     if any error occurred.
   */
  public static Object invokeExactMethod(final Class<?> type, final int options,
      final String name, final Object object, @Nullable final Object[] arguments)
      throws ReflectionException {
    if (arguments == null) {
      return invokeExactMethod(type, options, name, null, object, null);
    } else {
      final Class<?>[] paramTypes = new Class<?>[arguments.length];
      for (int i = 0; i < arguments.length; ++i) {
        paramTypes[i] = arguments[i].getClass();
      }
      return invokeExactMethod(type, options, name, paramTypes, object, arguments);
    }
  }

  /**
   * Invokes a method whose parameter types match exactly the given parameter
   * types.
   *
   * <p>This method delegates the method search to
   * {@link #getMethod(Class, int, String, Class[])}.
   *
   * @param type
   *     The class on which to get the method.
   * @param options
   *     A bitwise combination of reflection options defined in the {@link
   *     Option} class. The default value could be {@link Option#DEFAULT}.
   * @param name
   *     The name of the method to be get.
   * @param paramTypes
   *     The types of the parameters of the method to be get, which could be
   *     {@code null} or an empty array if the method has no argument.
   * @param object
   *     The object on which the method should be called. If the method is a
   *     static method, this argument could be {@code null}.
   * @param arguments
   *     The arguments used to invoke the method. If the method has no argument,
   *     this argument could be {@code null} or an empty array.
   * @return The value returned by the invoked method.
   * @throws ReflectionException
   *     if any error occurred.
   */
  public static Object invokeExactMethod(final Class<?> type, final int options,
      final String name, @Nullable final Class<?>[] paramTypes,
      @Nullable final Object object, @Nullable final Object[] arguments)
      throws ReflectionException {
    final Class<?>[] theParamTypes = defaultIfNull(paramTypes, EMPTY_CLASS_ARRAY);
    final Method method = getMethod(type, options, name, theParamTypes);
    if (method == null) {
      throw new MethodNotExistException(type, options, name, theParamTypes);
    }
    return withAccessibleObject(method, (m) -> {
      try {
        return m.invoke(object, arguments);
      } catch (final Throwable e) {
        throw new InvokingMethodFailedException(type, options, name, theParamTypes, e);
      }
    }, true);
  }

  /**
   * 获取方法的完全限定名称。
   *
   * <p>完全限定的方法名称具有以下语法：
   * <pre><code>
   * [完全限定类名]#[方法名](参数类型列表)
   * </code></pre>
   *
   * <p>例如：
   * <pre><code>
   * java.lang.String#substring(int, int)
   * </code></pre>
   *
   * @param method
   *     方法。
   * @return 方法的完全限定名称。
   */
  public static String getFullyQualifiedMethodName(final Method method) {
    final StringBuilder builder = new StringBuilder();
    final Class<?> declaringClass = method.getDeclaringClass();
    builder.append(declaringClass.getCanonicalName())
           .append('#')
           .append(method.getName())
           .append('(');
    final Class<?>[] parameterTypes = method.getParameterTypes();
    for (int i = 0; i < parameterTypes.length; ++i) {
      if (i > 0) {
        builder.append(",");
      }
      builder.append(parameterTypes[i].getCanonicalName());
    }
    builder.append(')');
    return builder.toString();
  }

  /**
   * 获取方法的URI。
   *
   * <p>方法的URI具有以下语法：
   * <pre><code>
   * method:[完全限定方法名]
   * </code></pre>
   *
   * <p>例如：
   * <pre><code>
   * method:java.lang.String#substring(int, int)
   * </code></pre>
   *
   * @param method
   *     方法。
   * @return 方法的URI。
   */
  public static URI getMethodUri(final Method method) {
    final String fullyQualifiedName = getFullyQualifiedMethodName(method);
    return URI.create("method:" + fullyQualifiedName);
  }

  /**
   * 判定给定的方法是否是重载了父类或接口的方法。
   *
   * @param method
   *     给定的方法。
   * @return
   *     给定的方法是否是重载了父类或接口的方法。
   */
  public static boolean isMethodOverridden(final Method method) {
    final Class<?> declaringClass = method.getDeclaringClass();
    if (declaringClass.equals(Object.class)) {
      return false;
    }
    final String methodName = method.getName();
    final Class<?>[] methodParameterTypes = method.getParameterTypes();
    final Class<?> superClass = declaringClass.getSuperclass();
    // 注意我们不能用 Class.getMethod() 获取方法，因为它会递归地在所有祖先中找方法
    if (hasMethod(superClass, DEFAULT, methodName, methodParameterTypes)) {
      return true;
    } else {
      for (final Class<?> face : declaringClass.getInterfaces()) {
        // 注意我们不能用 Class.getMethod() 获取方法，因为它会递归地在所有祖先中找方法
        if (hasMethod(face, DEFAULT, methodName, methodParameterTypes)) {
          return true;
        }
      }
      return false;
    }
  }

  /**
   * 检查指定的方法是否在声明类或接口中直接被标注了指定的注解。
   *
   * @param <T>
   *      注解的类型。
   * @param method
   *     指定的方法。
   * @param annotationClass
   *     指定的注解的类。
   * @return
   *     给定的方法是否在声明类或接口中直接被标注了指定的注解。
   * @see #isAnnotationPresent(Method, Class)
   */
  public static <T extends Annotation> boolean isAnnotationDirectlyPresent(
      final Method method, final Class<T> annotationClass) {
    return method.getDeclaredAnnotation(annotationClass) != null;
  }

  /**
   * 检查指定的方法是否被标注了指定的注解，此方法将在该方法的声明类/接口及其父类/父接口
   * 中检查所有重载方法的注解。
   *
   * @param <T>
   *      注解的类型。
   * @param method
   *     指定的方法。
   * @param annotationClass
   *     指定的注解的类。
   * @return
   *     给定的方法是否在其声明类/声明接口或其父类/父接口中被标注了指定的注解。
   * @see #isAnnotationDirectlyPresent(Method, Class)
   */
  public static <T extends Annotation> boolean isAnnotationPresent(
      final Method method, final Class<T> annotationClass) {
    if (method.getDeclaredAnnotation(annotationClass) != null) {
      return true;
    } else {
      final String name = method.getName();
      final Class<?>[] paramTypes = method.getParameterTypes();
      final Class<?> cls = method.getDeclaringClass();
      final List<MethodInfo> methodInfos = getAllMethodInfos(cls);
      for (final MethodInfo current : methodInfos) {
        final Method m = current.getMethod();
        if (Option.satisfy(cls, m, ALL_EXCLUDE_BRIDGE)
            && name.equals(m.getName())
            && Equality.equals(paramTypes, m.getParameterTypes())
            && (m.getDeclaredAnnotation(annotationClass) != null)) {
          return true;
        }
      }
      return false;
    }
  }

  /**
   * 检查指定的方法的某个参数是否被标注了指定的注解。
   *
   * @param <T>
   *      注解的类型。
   * @param method
   *     指定的方法。
   * @param annotationClass
   *     指定的注解的类。
   * @return
   *     给定的方法的某个参数是否被标注了指定的注解。
   * @see #isAnnotationPresentInParameters(Method, Class)
   */
  public static <T extends Annotation> boolean isAnnotationDirectlyPresentInParameters(
      final Method method, final Class<T> annotationClass) {
    final Annotation[][] annotations = method.getParameterAnnotations();
    for (final Annotation[] annotationList : annotations) {
      for (final Annotation ann : annotationList) {
        if (annotationClass.isAssignableFrom(ann.getClass())) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * 检查指定的方法的某个参数是否被标注了指定的注解，此方法将在该方法的声明类/接口及其父类
   * /父接口中检查所有重载方法的所有参数的注解。
   *
   * @param <T>
   *      注解的类型。
   * @param method
   *     指定的方法。
   * @param annotationClass
   *     指定的注解的类。
   * @return
   *     给定的方法的某个参数是否在其声明类/声明接口或其父类/父接口中被标注了指定的注解。
   * @see #isAnnotationDirectlyPresentInParameters(Method, Class)
   */
  public static <T extends Annotation> boolean isAnnotationPresentInParameters(
      final Method method, final Class<T> annotationClass) {
    if (method.getParameterCount() == 0) {
      return false;
    } else {
      final String name = method.getName();
      final Class<?>[] paramTypes = method.getParameterTypes();
      final Class<?> cls = method.getDeclaringClass();
      final List<MethodInfo> methodInfos = getAllMethodInfos(cls);
      for (final MethodInfo current : methodInfos) {
        final Method m = current.getMethod();
        if (Option.satisfy(cls, m, ALL_EXCLUDE_BRIDGE)
            && name.equals(m.getName())
            && Equality.equals(paramTypes, m.getParameterTypes())
            && isAnnotationDirectlyPresentInParameters(m, annotationClass)) {
          return true;
        }
      }
      return false;
    }
  }

  /**
   * 根据某个类的指定的方法引用，获取其 {@link Method} 对象。
   *
   * <p><b>注意：</b>指定的方法不能是 non-public 的！！！</p>
   *
   * <p>例如：</p>
   * <pre><code>
   *
   * public class User {
   *
   *   private String username;
   *   private String password;
   *
   *   public String getUsername() {
   *     return username;
   *   }
   *
   *   public void setUsername(String username) {
   *     this.username = username;
   *   }
   *
   *   public String getPassword() {
   *     return password;
   *   }
   *
   *   public void setPassword(String password) {
   *     this.password = password;
   *   }
   * }
   *
   * final Method usernameGetter = MethodUtils.getMethodByReference(User.class, User::getUsername);
   * assertEquals(User.class.getDeclaredMethod("getUsername"), usernameGetter);
   *
   * final Method passwordGetter = MethodUtils.getMethodByReference(User.class, User::getPassword);
   * assertEquals(User.class.getDeclaredMethod("getPassword"), passwordGetter);
   * </code></pre>
   *
   * @param <T>
   *     指定的类的类型。
   * @param clazz
   *     指定的类的类对象。
   * @param ref
   *     指定的类的某个方法引用。
   * @return
   *     该方法引用对应的方法的{@link Method}对象。
   */
  @SuppressWarnings("unchecked")
  public static <T, R> Method getMethodByReference(final Class<T> clazz, final NonVoidMethod0<T, R> ref) {
    final ReferenceToMethodCache<T> cache = (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref, (g) -> {
      if (clazz.isRecord()) {
        return findRecordMethod(clazz, (NonVoidMethod0<T, R>) g);
      } else if (ClassUtils.isEnumType(clazz)) {
        return findMethodBySerialization(clazz, (NonVoidMethod0<T, R>) g);
      } else if (Modifier.isFinal(clazz.getModifiers())) {
        return findMethodBySerialization(clazz, (NonVoidMethod0<T, R>) g);
      } else {
        try {
          return findMethod(clazz, (NonVoidMethod0<T, R>) g);
        } catch (final Exception e) {
          return findMethodBySerialization(clazz, (NonVoidMethod0<T, R>) g);
        }
      }
    });
  }

  /**
   * 通过方法引用获取有返回值且有1个参数的方法。
   *
   * @param <T>
   *     类的类型。
   * @param <R>
   *     方法返回值的类型。
   * @param <P1>
   *     第一个参数的类型。
   * @param clazz
   *     要在其中查找方法的类。
   * @param ref
   *     方法引用。
   * @return
   *     该方法引用对应的方法的{@link Method}对象。
   */
  @SuppressWarnings("unchecked")
  public static <T, R, P1> Method getMethodByReference(final Class<T> clazz,
      final NonVoidMethod1<T, R, P1> ref) {
    final ReferenceToMethodCache<T> cache = (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref, (g) -> {
          if (ClassUtils.isEnumType(clazz)) {
            return findMethodBySerialization(clazz, (NonVoidMethod1<T, R, P1>) g);
          } else if (Modifier.isFinal(clazz.getModifiers())) {
            return findMethodBySerialization(clazz, (NonVoidMethod1<T, R, P1>) g);
          } else {
            try {
              return findMethod(clazz, (NonVoidMethod1<T, R, P1>) g);
            } catch (final Exception e) {
              return findMethodBySerialization(clazz, (NonVoidMethod1<T, R, P1>) g);
            }
          }
        });
  }

  /**
   * 通过方法引用获取有返回值且有1个boolean参数的方法。
   *
   * @param <T>
   *     类的类型。
   * @param <R>
   *     方法返回值的类型。
   * @param clazz
   *     要在其中查找方法的类。
   * @param ref
   *     方法引用。
   * @return
   *     该方法引用对应的方法的{@link Method}对象。
   */
  @SuppressWarnings("unchecked")
  public static <T, R> Method getMethodByReference(final Class<T> clazz,
      final NonVoidMethod1Boolean<T, R> ref) {
    final ReferenceToMethodCache<T> cache = (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref, (g) -> {
      if (ClassUtils.isEnumType(clazz)) {
        return findMethodBySerialization(clazz, (NonVoidMethod1Boolean<T, R>) g);
      } else if (Modifier.isFinal(clazz.getModifiers())) {
        return findMethodBySerialization(clazz, (NonVoidMethod1Boolean<T, R>) g);
      } else {
        try {
          return findMethod(clazz, (NonVoidMethod1Boolean<T, R>) g);
        } catch (final Exception e) {
          return findMethodBySerialization(clazz, (NonVoidMethod1Boolean<T, R>) g);
        }
      }
    });
  }

  // @SuppressWarnings("unchecked")
  // public static <T, R> Method getMethodByReference(final Class<T> clazz,
  //     final NonVoidMethod1Char<T, R> ref) {
  //   final ReferenceToMethodCache<T> cache =
  //       (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
  //   return cache.computeIfAbsent(ref,
  //       (g) -> findMethod(clazz, (NonVoidMethod1Char<T, R>) g));
  // }

  /**
   * 通过方法引用获取有返回值且有1个byte参数的方法。
   *
   * @param <T>
   *     类的类型。
   * @param <R>
   *     方法返回值的类型。
   * @param clazz
   *     要在其中查找方法的类。
   * @param ref
   *     方法引用。
   * @return
   *     该方法引用对应的方法的{@link Method}对象。
   */
  @SuppressWarnings("unchecked")
  public static <T, R> Method getMethodByReference(final Class<T> clazz,
      final NonVoidMethod1Byte<T, R> ref) {
    final ReferenceToMethodCache<T> cache = (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref, (g) -> {
      if (ClassUtils.isEnumType(clazz)) {
        return findMethodBySerialization(clazz, (NonVoidMethod1Byte<T, R>) g);
      } else if (Modifier.isFinal(clazz.getModifiers())) {
        return findMethodBySerialization(clazz, (NonVoidMethod1Byte<T, R>) g);
      } else {
        try {
          return findMethod(clazz, (NonVoidMethod1Byte<T, R>) g);
        } catch (final Exception e) {
          return findMethodBySerialization(clazz, (NonVoidMethod1Byte<T, R>) g);
        }
      }
    });
  }

  // @SuppressWarnings("unchecked")
  // public static <T, R> Method getMethodByReference(final Class<T> clazz,
  //     final NonVoidMethod1Short<T, R> ref) {
  //   final ReferenceToMethodCache<T> cache =
  //       (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
  //   return cache.computeIfAbsent(ref,
  //       (g) -> findMethod(clazz, (NonVoidMethod1Short<T, R>) g));
  // }
  //
  // @SuppressWarnings("unchecked")
  // public static <T, R> Method getMethodByReference(final Class<T> clazz,
  //     final NonVoidMethod1Int<T, R> ref) {
  //   final ReferenceToMethodCache<T> cache =
  //       (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
  //   return cache.computeIfAbsent(ref,
  //       (g) -> findMethod(clazz, (NonVoidMethod1Int<T, R>) g));
  // }
  //
  // @SuppressWarnings("unchecked")
  // public static <T, R> Method getMethodByReference(final Class<T> clazz,
  //     final NonVoidMethod1Long<T, R> ref) {
  //   final ReferenceToMethodCache<T> cache =
  //       (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
  //   return cache.computeIfAbsent(ref,
  //       (g) -> findMethod(clazz, (NonVoidMethod1Long<T, R>) g));
  // }
  //
  // @SuppressWarnings("unchecked")
  // public static <T, R> Method getMethodByReference(final Class<T> clazz,
  //     final NonVoidMethod1Float<T, R> ref) {
  //   final ReferenceToMethodCache<T> cache =
  //       (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
  //   return cache.computeIfAbsent(ref,
  //       (g) -> findMethod(clazz, (NonVoidMethod1Float<T, R>) g));
  // }
  //
  // @SuppressWarnings("unchecked")
  // public static <T, R> Method getMethodByReference(final Class<T> clazz,
  //     final NonVoidMethod1Double<T, R> ref) {
  //   final ReferenceToMethodCache<T> cache =
  //       (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
  //   return cache.computeIfAbsent(ref,
  //       (g) -> findMethod(clazz, (NonVoidMethod1Double<T, R>) g));
  // }

  /**
   * 通过方法引用获取有返回值且有2个参数的方法。
   *
   * @param <T>
   *     类的类型。
   * @param <R>
   *     方法返回值的类型。
   * @param <P1>
   *     第一个参数的类型。
   * @param <P2>
   *     第二个参数的类型。
   * @param clazz
   *     要在其中查找方法的类。
   * @param ref
   *     方法引用。
   * @return
   *     该方法引用对应的方法的{@link Method}对象。
   */
  @SuppressWarnings("unchecked")
  public static <T, R, P1, P2> Method getMethodByReference(final Class<T> clazz,
      final NonVoidMethod2<T, R, P1, P2> ref) {
    final ReferenceToMethodCache<T> cache = (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref, (g) -> {
      if (ClassUtils.isEnumType(clazz)) {
        return findMethodBySerialization(clazz, (NonVoidMethod2<T, R, P1, P2>) g);
      } else if (Modifier.isFinal(clazz.getModifiers())) {
        return findMethodBySerialization(clazz, (NonVoidMethod2<T, R, P1, P2>) g);
      } else {
        try {
          return findMethod(clazz, (NonVoidMethod2<T, R, P1, P2>) g);
        } catch (final Exception e) {
          return findMethodBySerialization(clazz, (NonVoidMethod2<T, R, P1, P2>) g);
        }
      }
    });
  }

  /**
   * 通过方法引用获取有返回值且有3个参数的方法。
   *
   * @param <T>
   *     类的类型。
   * @param <R>
   *     方法返回值的类型。
   * @param <P1>
   *     第一个参数的类型。
   * @param <P2>
   *     第二个参数的类型。
   * @param <P3>
   *     第三个参数的类型。
   * @param clazz
   *     要在其中查找方法的类。
   * @param ref
   *     方法引用。
   * @return
   *     该方法引用对应的方法的{@link Method}对象。
   */
  @SuppressWarnings("unchecked")
  public static <T, R, P1, P2, P3> Method getMethodByReference(final Class<T> clazz,
      final NonVoidMethod3<T, R, P1, P2, P3> ref) {
    final ReferenceToMethodCache<T> cache = (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref, (g) -> {
      if (ClassUtils.isEnumType(clazz)) {
        return findMethodBySerialization(clazz, (NonVoidMethod3<T, R, P1, P2, P3>) g);
      } else if (Modifier.isFinal(clazz.getModifiers())) {
        return findMethodBySerialization(clazz, (NonVoidMethod3<T, R, P1, P2, P3>) g);
      } else {
        try {
          return findMethod(clazz, (NonVoidMethod3<T, R, P1, P2, P3>) g);
        } catch (final Exception e) {
          return findMethodBySerialization(clazz, (NonVoidMethod3<T, R, P1, P2, P3>) g);
        }
      }
    });
  }

  /**
   * 通过方法引用获取有返回值且有4个参数的方法。
   *
   * @param <T>
   *     类的类型。
   * @param <R>
   *     方法返回值的类型。
   * @param <P1>
   *     第一个参数的类型。
   * @param <P2>
   *     第二个参数的类型。
   * @param <P3>
   *     第三个参数的类型。
   * @param <P4>
   *     第四个参数的类型。
   * @param clazz
   *     要在其中查找方法的类。
   * @param ref
   *     方法引用。
   * @return
   *     该方法引用对应的方法的{@link Method}对象。
   */
  @SuppressWarnings("unchecked")
  public static <T, R, P1, P2, P3, P4> Method getMethodByReference(final Class<T> clazz,
      final NonVoidMethod4<T, R, P1, P2, P3, P4> ref) {
    final ReferenceToMethodCache<T> cache = (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref, (g) -> {
      if (ClassUtils.isEnumType(clazz)) {
        return findMethodBySerialization(clazz, (NonVoidMethod4<T, R, P1, P2, P3, P4>) g);
      } else if (Modifier.isFinal(clazz.getModifiers())) {
        return findMethodBySerialization(clazz, (NonVoidMethod4<T, R, P1, P2, P3, P4>) g);
      } else {
        try {
          return findMethod(clazz, (NonVoidMethod4<T, R, P1, P2, P3, P4>) g);
        } catch (final Exception e) {
          return findMethodBySerialization(clazz, (NonVoidMethod4<T, R, P1, P2, P3, P4>) g);
        }
      }
    });
  }

  /**
   * 通过方法引用获取有返回值且有5个参数的方法。
   *
   * @param <T>
   *     类的类型。
   * @param <R>
   *     方法返回值的类型。
   * @param <P1>
   *     第一个参数的类型。
   * @param <P2>
   *     第二个参数的类型。
   * @param <P3>
   *     第三个参数的类型。
   * @param <P4>
   *     第四个参数的类型。
   * @param <P5>
   *     第五个参数的类型。
   * @param clazz
   *     要在其中查找方法的类。
   * @param ref
   *     方法引用。
   * @return
   *     该方法引用对应的方法的{@link Method}对象。
   */
  @SuppressWarnings("unchecked")
  public static <T, R, P1, P2, P3, P4, P5> Method getMethodByReference(final Class<T> clazz,
      final NonVoidMethod5<T, R, P1, P2, P3, P4, P5> ref) {
    final ReferenceToMethodCache<T> cache = (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref, (g) -> {
      if (ClassUtils.isEnumType(clazz)) {
        return findMethodBySerialization(clazz, (NonVoidMethod5<T, R, P1, P2, P3, P4, P5>) g);
      } else if (Modifier.isFinal(clazz.getModifiers())) {
        return findMethodBySerialization(clazz, (NonVoidMethod5<T, R, P1, P2, P3, P4, P5>) g);
      } else {
        try {
          return findMethod(clazz, (NonVoidMethod5<T, R, P1, P2, P3, P4, P5>) g);
        } catch (final Exception e) {
          return findMethodBySerialization(clazz, (NonVoidMethod5<T, R, P1, P2, P3, P4, P5>) g);
        }
      }
    });
  }

  /**
   * 通过方法引用获取有返回值且有6个参数的方法。
   *
   * @param <T>
   *     类的类型。
   * @param <R>
   *     方法返回值的类型。
   * @param <P1>
   *     第一个参数的类型。
   * @param <P2>
   *     第二个参数的类型。
   * @param <P3>
   *     第三个参数的类型。
   * @param <P4>
   *     第四个参数的类型。
   * @param <P5>
   *     第五个参数的类型。
   * @param <P6>
   *     第六个参数的类型。
   * @param clazz
   *     要在其中查找方法的类。
   * @param ref
   *     方法引用。
   * @return
   *     该方法引用对应的方法的{@link Method}对象。
   */
  @SuppressWarnings("unchecked")
  public static <T, R, P1, P2, P3, P4, P5, P6> Method getMethodByReference(final Class<T> clazz,
      final NonVoidMethod6<T, R, P1, P2, P3, P4, P5, P6> ref) {
    final ReferenceToMethodCache<T> cache = (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref, (g) -> {
      if (ClassUtils.isEnumType(clazz)) {
        return findMethodBySerialization(clazz, (NonVoidMethod6<T, R, P1, P2, P3, P4, P5, P6>) g);
      } else if (Modifier.isFinal(clazz.getModifiers())) {
        return findMethodBySerialization(clazz, (NonVoidMethod6<T, R, P1, P2, P3, P4, P5, P6>) g);
      } else {
        try {
          return findMethod(clazz, (NonVoidMethod6<T, R, P1, P2, P3, P4, P5, P6>) g);
        } catch (final Exception e) {
          return findMethodBySerialization(clazz, (NonVoidMethod6<T, R, P1, P2, P3, P4, P5, P6>) g);
        }
      }
    });
  }

  /**
   * 通过方法引用获取有返回值且有7个参数的方法。
   *
   * @param <T>
   *     类的类型。
   * @param <R>
   *     方法返回值的类型。
   * @param <P1>
   *     第一个参数的类型。
   * @param <P2>
   *     第二个参数的类型。
   * @param <P3>
   *     第三个参数的类型。
   * @param <P4>
   *     第四个参数的类型。
   * @param <P5>
   *     第五个参数的类型。
   * @param <P6>
   *     第六个参数的类型。
   * @param <P7>
   *     第七个参数的类型。
   * @param clazz
   *     要在其中查找方法的类。
   * @param ref
   *     方法引用。
   * @return
   *     该方法引用对应的方法的{@link Method}对象。
   */
  @SuppressWarnings("unchecked")
  public static <T, R, P1, P2, P3, P4, P5, P6, P7> Method getMethodByReference(final Class<T> clazz,
      final NonVoidMethod7<T, R, P1, P2, P3, P4, P5, P6, P7> ref) {
    final ReferenceToMethodCache<T> cache = (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref, (g) -> {
      if (ClassUtils.isEnumType(clazz)) {
        return findMethodBySerialization(clazz, (NonVoidMethod7<T, R, P1, P2, P3, P4, P5, P6, P7>) g);
      } else if (Modifier.isFinal(clazz.getModifiers())) {
        return findMethodBySerialization(clazz, (NonVoidMethod7<T, R, P1, P2, P3, P4, P5, P6, P7>) g);
      } else {
        try {
          return findMethod(clazz, (NonVoidMethod7<T, R, P1, P2, P3, P4, P5, P6, P7>) g);
        } catch (final Exception e) {
          return findMethodBySerialization(clazz, (NonVoidMethod7<T, R, P1, P2, P3, P4, P5, P6, P7>) g);
        }
      }
    });
  }

  /**
   * 通过方法引用获取有返回值且有8个参数的方法。
   *
   * @param <T>
   *     类的类型。
   * @param <R>
   *     方法返回值的类型。
   * @param <P1>
   *     第一个参数的类型。
   * @param <P2>
   *     第二个参数的类型。
   * @param <P3>
   *     第三个参数的类型。
   * @param <P4>
   *     第四个参数的类型。
   * @param <P5>
   *     第五个参数的类型。
   * @param <P6>
   *     第六个参数的类型。
   * @param <P7>
   *     第七个参数的类型。
   * @param <P8>
   *     第八个参数的类型。
   * @param clazz
   *     要在其中查找方法的类。
   * @param ref
   *     方法引用。
   * @return
   *     该方法引用对应的方法的{@link Method}对象。
   */
  @SuppressWarnings("unchecked")
  public static <T, R, P1, P2, P3, P4, P5, P6, P7, P8> Method getMethodByReference(
      final Class<T> clazz, final NonVoidMethod8<T, R, P1, P2, P3, P4, P5, P6, P7, P8> ref) {
    final ReferenceToMethodCache<T> cache = (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref, (g) -> {
      if (ClassUtils.isEnumType(clazz)) {
        return findMethodBySerialization(clazz, (NonVoidMethod8<T, R, P1, P2, P3, P4, P5, P6, P7, P8>) g);
      } else if (Modifier.isFinal(clazz.getModifiers())) {
        return findMethodBySerialization(clazz, (NonVoidMethod8<T, R, P1, P2, P3, P4, P5, P6, P7, P8>) g);
      } else {
        try {
          return findMethod(clazz, (NonVoidMethod8<T, R, P1, P2, P3, P4, P5, P6, P7, P8>) g);
        } catch (final Exception e) {
          return findMethodBySerialization(clazz, (NonVoidMethod8<T, R, P1, P2, P3, P4, P5, P6, P7, P8>) g);
        }
      }
    });
  }

  /**
   * 通过方法引用获取有返回值且有9个参数的方法。
   *
   * @param <T>
   *     类的类型。
   * @param <R>
   *     方法返回值的类型。
   * @param <P1>
   *     第一个参数的类型。
   * @param <P2>
   *     第二个参数的类型。
   * @param <P3>
   *     第三个参数的类型。
   * @param <P4>
   *     第四个参数的类型。
   * @param <P5>
   *     第五个参数的类型。
   * @param <P6>
   *     第六个参数的类型。
   * @param <P7>
   *     第七个参数的类型。
   * @param <P8>
   *     第八个参数的类型。
   * @param <P9>
   *     第九个参数的类型。
   * @param clazz
   *     要在其中查找方法的类。
   * @param ref
   *     方法引用。
   * @return
   *     该方法引用对应的方法的{@link Method}对象。
   */
  @SuppressWarnings("unchecked")
  public static <T, R, P1, P2, P3, P4, P5, P6, P7, P8, P9> Method getMethodByReference(
      final Class<T> clazz, final NonVoidMethod9<T, R, P1, P2, P3, P4, P5, P6, P7, P8, P9> ref) {
    final ReferenceToMethodCache<T> cache = (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref, (g) -> {
      if (ClassUtils.isEnumType(clazz)) {
        return findMethodBySerialization(clazz, (NonVoidMethod9<T, R, P1, P2, P3, P4, P5, P6, P7, P8, P9>) g);
      } else if (Modifier.isFinal(clazz.getModifiers())) {
        return findMethodBySerialization(clazz, (NonVoidMethod9<T, R, P1, P2, P3, P4, P5, P6, P7, P8, P9>) g);
      } else {
        try {
          return findMethod(clazz, (NonVoidMethod9<T, R, P1, P2, P3, P4, P5, P6, P7, P8, P9>) g);
        } catch (final Exception e) {
          return findMethodBySerialization(clazz, (NonVoidMethod9<T, R, P1, P2, P3, P4, P5, P6, P7, P8, P9>) g);
        }
      }
    });
  }

  /**
   * 根据某个类的指定的方法引用，获取其 {@link Method} 对象。
   *
   * <p><b>注意：</b>指定的方法不能是 final 的或 non-public 的！！！</p>
   *
   * <p>例如：</p>
   * <pre><code>
   *
   * public class User {
   *
   *   private String username;
   *   private String password;
   *
   *   public String getUsername() {
   *     return username;
   *   }
   *
   *   public void setUsername(String username) {
   *     this.username = username;
   *   }
   *
   *   public String getPassword() {
   *     return password;
   *   }
   *
   *   public void setPassword(String password) {
   *     this.password = password;
   *   }
   *
   *   public void sayHello() {
   *     System.out.println("Hello from user");
   *   }
   * }
   *
   * final Method hello = getMethodByReference(User.class, User::sayHello);
   * assertEquals(User.class.getDeclaredMethod("sayHello"), hello);
   * </code></pre>
   *
   *
   * @param <T>
   *     指定的类的类型。
   * @param clazz
   *     指定的类的类对象。
   * @param ref
   *     指定的类的某个方法引用。
   * @return
   *     该方法引用对应的方法的{@link Method}对象。
   */
  @SuppressWarnings("unchecked")
  public static <T> Method getMethodByReference(final Class<T> clazz, final VoidMethod0<T> ref) {
    final ReferenceToMethodCache<T> cache =
        (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref, (g) -> {
      if (clazz.isRecord()) {
        return findMethodBySerialization(clazz, (VoidMethod0<T>) g);
      } else if (ClassUtils.isEnumType(clazz)) {
        return findMethodBySerialization(clazz, (VoidMethod0<T>) g);
      } else if (Modifier.isFinal(clazz.getModifiers())) {
        return findMethodBySerialization(clazz, (VoidMethod0<T>) g);
      } else {
        try {
          return findMethod(clazz, (VoidMethod0<T>) g);
        } catch (final Exception e) {
          return findMethodBySerialization(clazz, (VoidMethod0<T>) g);
        }
      }
    });
  }

  /**
   * 通过方法引用获取无返回值且有1个参数的方法。
   *
   * @param <T>
   *     类的类型。
   * @param <P1>
   *     第一个参数的类型。
   * @param clazz
   *     要在其中查找方法的类。
   * @param ref
   *     方法引用。
   * @return
   *     该方法引用对应的方法的{@link Method}对象。
   */
  @SuppressWarnings("unchecked")
  public static <T, P1> Method getMethodByReference(final Class<T> clazz,
      final VoidMethod1<T, P1> ref) {
    final ReferenceToMethodCache<T> cache =
        (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref, (g) -> {
      if (clazz.isRecord()) {
        return findMethodBySerialization(clazz, (VoidMethod1<T, P1>) g);
      } else if (ClassUtils.isEnumType(clazz)) {
        return findMethodBySerialization(clazz, (VoidMethod1<T, P1>) g);
      } else if (Modifier.isFinal(clazz.getModifiers())) {
        return findMethodBySerialization(clazz, (VoidMethod1<T, P1>) g);
      } else {
        try {
          return findMethod(clazz, (VoidMethod1<T, P1>) g);
        } catch (final Exception e) {
          return findMethodBySerialization(clazz, (VoidMethod1<T, P1>) g);
        }
      }
    });
  }

  /**
   * 通过方法引用获取无返回值且有1个boolean参数的方法。
   *
   * @param <T>
   *     类的类型。
   * @param clazz
   *     要在其中查找方法的类。
   * @param ref
   *     方法引用。
   * @return
   *     该方法引用对应的方法的{@link Method}对象。
   */
  @SuppressWarnings("unchecked")
  public static <T> Method getMethodByReference(final Class<T> clazz,
      final VoidMethod1Boolean<T> ref) {
    final ReferenceToMethodCache<T> cache =
        (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref, (g) -> {
      if (clazz.isRecord()) {
        return findMethodBySerialization(clazz, (VoidMethod1Boolean<T>) g);
      } else if (ClassUtils.isEnumType(clazz)) {
        return findMethodBySerialization(clazz, (VoidMethod1Boolean<T>) g);
      } else if (Modifier.isFinal(clazz.getModifiers())) {
        return findMethodBySerialization(clazz, (VoidMethod1Boolean<T>) g);
      } else {
        try {
          return findMethod(clazz, (VoidMethod1Boolean<T>) g);
        } catch (final Exception e) {
          return findMethodBySerialization(clazz, (VoidMethod1Boolean<T>) g);
        }
      }
    });
  }

  // @SuppressWarnings("unchecked")
  // public static <T> Method getMethodByReference(final Class<T> clazz,
  //     final VoidMethod1Char<T> ref) {
  //   final ReferenceToMethodCache<T> cache =
  //       (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
  //   return cache.computeIfAbsent(ref,
  //       (g) -> findMethod(clazz, (VoidMethod1Char<T>) g));
  // }

  /**
   * 通过方法引用获取无返回值且有1个byte参数的方法。
   *
   * @param <T>
   *     类的类型。
   * @param clazz
   *     要在其中查找方法的类。
   * @param ref
   *     方法引用。
   * @return
   *     该方法引用对应的方法的{@link Method}对象。
   */
  @SuppressWarnings("unchecked")
  public static <T> Method getMethodByReference(final Class<T> clazz,
      final VoidMethod1Byte<T> ref) {
    final ReferenceToMethodCache<T> cache =
        (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref, (g) -> {
      if (clazz.isRecord()) {
        return findMethodBySerialization(clazz, (VoidMethod1Byte<T>) g);
      } else if (ClassUtils.isEnumType(clazz)) {
        return findMethodBySerialization(clazz, (VoidMethod1Byte<T>) g);
      } else if (Modifier.isFinal(clazz.getModifiers())) {
        return findMethodBySerialization(clazz, (VoidMethod1Byte<T>) g);
      } else {
        try {
          return findMethod(clazz, (VoidMethod1Byte<T>) g);
        } catch (final Exception e) {
          return findMethodBySerialization(clazz, (VoidMethod1Byte<T>) g);
        }
      }
    });
  }

  // @SuppressWarnings("unchecked")
  // public static <T> Method getMethodByReference(final Class<T> clazz,
  //     final VoidMethod1Short<T> ref) {
  //   final ReferenceToMethodCache<T> cache =
  //       (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
  //   return cache.computeIfAbsent(ref,
  //       (g) -> findMethod(clazz, (VoidMethod1Short<T>) g));
  // }
  //
  // @SuppressWarnings("unchecked")
  // public static <T> Method getMethodByReference(final Class<T> clazz,
  //     final VoidMethod1Int<T> ref) {
  //   final ReferenceToMethodCache<T> cache =
  //       (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
  //   return cache.computeIfAbsent(ref,
  //       (g) -> findMethod(clazz, (VoidMethod1Int<T>) g));
  // }
  //
  // @SuppressWarnings("unchecked")
  // public static <T> Method getMethodByReference(final Class<T> clazz,
  //     final VoidMethod1Long<T> ref) {
  //   final ReferenceToMethodCache<T> cache =
  //       (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
  //   return cache.computeIfAbsent(ref,
  //       (g) -> findMethod(clazz, (VoidMethod1Long<T>) g));
  // }
  //
  // @SuppressWarnings("unchecked")
  // public static <T> Method getMethodByReference(final Class<T> clazz,
  //     final VoidMethod1Float<T> ref) {
  //   final ReferenceToMethodCache<T> cache =
  //       (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
  //   return cache.computeIfAbsent(ref,
  //       (g) -> findMethod(clazz, (VoidMethod1Float<T>) g));
  // }
  //
  // @SuppressWarnings("unchecked")
  // public static <T> Method getMethodByReference(final Class<T> clazz,
  //     final VoidMethod1Double<T> ref) {
  //   final ReferenceToMethodCache<T> cache =
  //       (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
  //   return cache.computeIfAbsent(ref,
  //       (g) -> findMethod(clazz, (VoidMethod1Double<T>) g));
  // }

  /**
   * 通过方法引用获取无返回值且有2个参数的方法。
   *
   * @param <T>
   *     类的类型。
   * @param <P1>
   *     第一个参数的类型。
   * @param <P2>
   *     第二个参数的类型。
   * @param clazz
   *     要在其中查找方法的类。
   * @param ref
   *     方法引用。
   * @return
   *     该方法引用对应的方法的{@link Method}对象。
   */
  @SuppressWarnings("unchecked")
  public static <T, P1, P2> Method getMethodByReference(final Class<T> clazz,
      final VoidMethod2<T, P1, P2> ref) {
    final ReferenceToMethodCache<T> cache =
        (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref, (g) -> {
      if (clazz.isRecord()) {
        return findMethodBySerialization(clazz, (VoidMethod2<T, P1, P2>) g);
      } else if (ClassUtils.isEnumType(clazz)) {
        return findMethodBySerialization(clazz, (VoidMethod2<T, P1, P2>) g);
      } else if (Modifier.isFinal(clazz.getModifiers())) {
        return findMethodBySerialization(clazz, (VoidMethod2<T, P1, P2>) g);
      } else {
        try {
          return findMethod(clazz, (VoidMethod2<T, P1, P2>) g);
        } catch (final Exception e) {
          return findMethodBySerialization(clazz, (VoidMethod2<T, P1, P2>) g);
        }
      }
    });
  }

  /**
   * 通过方法引用获取无返回值且有3个参数的方法。
   *
   * @param <T>
   *     类的类型。
   * @param <P1>
   *     第一个参数的类型。
   * @param <P2>
   *     第二个参数的类型。
   * @param <P3>
   *     第三个参数的类型。
   * @param clazz
   *     要在其中查找方法的类。
   * @param ref
   *     方法引用。
   * @return
   *     该方法引用对应的方法的{@link Method}对象。
   */
  @SuppressWarnings("unchecked")
  public static <T, P1, P2, P3> Method getMethodByReference(final Class<T> clazz,
      final VoidMethod3<T, P1, P2, P3> ref) {
    final ReferenceToMethodCache<T> cache =
        (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref, (g) -> {
      if (clazz.isRecord()) {
        return findMethodBySerialization(clazz, (VoidMethod3<T, P1, P2, P3>) g);
      } else if (ClassUtils.isEnumType(clazz)) {
        return findMethodBySerialization(clazz, (VoidMethod3<T, P1, P2, P3>) g);
      } else if (Modifier.isFinal(clazz.getModifiers())) {
        return findMethodBySerialization(clazz, (VoidMethod3<T, P1, P2, P3>) g);
      } else {
        try {
          return findMethod(clazz, (VoidMethod3<T, P1, P2, P3>) g);
        } catch (final Exception e) {
          return findMethodBySerialization(clazz, (VoidMethod3<T, P1, P2, P3>) g);
        }
      }
    });
  }

  /**
   * 通过方法引用获取无返回值且有4个参数的方法。
   *
   * @param <T>
   *     类的类型。
   * @param <P1>
   *     第一个参数的类型。
   * @param <P2>
   *     第二个参数的类型。
   * @param <P3>
   *     第三个参数的类型。
   * @param <P4>
   *     第四个参数的类型。
   * @param clazz
   *     要在其中查找方法的类。
   * @param ref
   *     方法引用。
   * @return
   *     该方法引用对应的方法的{@link Method}对象。
   */
  @SuppressWarnings("unchecked")
  public static <T, P1, P2, P3, P4> Method getMethodByReference(final Class<T> clazz,
      final VoidMethod4<T, P1, P2, P3, P4> ref) {
    final ReferenceToMethodCache<T> cache =
        (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref, (g) -> {
      if (clazz.isRecord()) {
        return findMethodBySerialization(clazz, (VoidMethod4<T, P1, P2, P3, P4>) g);
      } else if (ClassUtils.isEnumType(clazz)) {
        return findMethodBySerialization(clazz, (VoidMethod4<T, P1, P2, P3, P4>) g);
      } else if (Modifier.isFinal(clazz.getModifiers())) {
        return findMethodBySerialization(clazz, (VoidMethod4<T, P1, P2, P3, P4>) g);
      } else {
        try {
          return findMethod(clazz, (VoidMethod4<T, P1, P2, P3, P4>) g);
        } catch (final Exception e) {
          return findMethodBySerialization(clazz, (VoidMethod4<T, P1, P2, P3, P4>) g);
        }
      }
    });
  }

  /**
   * 通过方法引用获取无返回值且有5个参数的方法。
   *
   * @param <T>
   *     类的类型。
   * @param <P1>
   *     第一个参数的类型。
   * @param <P2>
   *     第二个参数的类型。
   * @param <P3>
   *     第三个参数的类型。
   * @param <P4>
   *     第四个参数的类型。
   * @param <P5>
   *     第五个参数的类型。
   * @param clazz
   *     要在其中查找方法的类。
   * @param ref
   *     方法引用。
   * @return
   *     该方法引用对应的方法的{@link Method}对象。
   */
  @SuppressWarnings("unchecked")
  public static <T, P1, P2, P3, P4, P5> Method getMethodByReference(final Class<T> clazz,
      final VoidMethod5<T, P1, P2, P3, P4, P5> ref) {
    final ReferenceToMethodCache<T> cache =
        (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref, (g) -> {
      if (clazz.isRecord()) {
        return findMethodBySerialization(clazz, (VoidMethod5<T, P1, P2, P3, P4, P5>) g);
      } else if (ClassUtils.isEnumType(clazz)) {
        return findMethodBySerialization(clazz, (VoidMethod5<T, P1, P2, P3, P4, P5>) g);
      } else if (Modifier.isFinal(clazz.getModifiers())) {
        return findMethodBySerialization(clazz, (VoidMethod5<T, P1, P2, P3, P4, P5>) g);
      } else {
        try {
          return findMethod(clazz, (VoidMethod5<T, P1, P2, P3, P4, P5>) g);
        } catch (final Exception e) {
          return findMethodBySerialization(clazz, (VoidMethod5<T, P1, P2, P3, P4, P5>) g);
        }
      }
    });
  }

  /**
   * 通过方法引用获取无返回值且有6个参数的方法。
   *
   * @param <T>
   *     类的类型。
   * @param <P1>
   *     第一个参数的类型。
   * @param <P2>
   *     第二个参数的类型。
   * @param <P3>
   *     第三个参数的类型。
   * @param <P4>
   *     第四个参数的类型。
   * @param <P5>
   *     第五个参数的类型。
   * @param <P6>
   *     第六个参数的类型。
   * @param clazz
   *     要在其中查找方法的类。
   * @param ref
   *     方法引用。
   * @return
   *     该方法引用对应的方法的{@link Method}对象。
   */
  @SuppressWarnings("unchecked")
  public static <T, P1, P2, P3, P4, P5, P6> Method getMethodByReference(final Class<T> clazz,
      final VoidMethod6<T, P1, P2, P3, P4, P5, P6> ref) {
    final ReferenceToMethodCache<T> cache =
        (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref, (g) -> {
      if (clazz.isRecord()) {
        return findMethodBySerialization(clazz, (VoidMethod6<T, P1, P2, P3, P4, P5, P6>) g);
      } else if (ClassUtils.isEnumType(clazz)) {
        return findMethodBySerialization(clazz, (VoidMethod6<T, P1, P2, P3, P4, P5, P6>) g);
      } else if (Modifier.isFinal(clazz.getModifiers())) {
        return findMethodBySerialization(clazz, (VoidMethod6<T, P1, P2, P3, P4, P5, P6>) g);
      } else {
        try {
          return findMethod(clazz, (VoidMethod6<T, P1, P2, P3, P4, P5, P6>) g);
        } catch (final Exception e) {
          return findMethodBySerialization(clazz, (VoidMethod6<T, P1, P2, P3, P4, P5, P6>) g);
        }
      }
    });
  }

  /**
   * 通过方法引用获取无返回值且有7个参数的方法。
   *
   * @param <T>
   *     类的类型。
   * @param <P1>
   *     第一个参数的类型。
   * @param <P2>
   *     第二个参数的类型。
   * @param <P3>
   *     第三个参数的类型。
   * @param <P4>
   *     第四个参数的类型。
   * @param <P5>
   *     第五个参数的类型。
   * @param <P6>
   *     第六个参数的类型。
   * @param <P7>
   *     第七个参数的类型。
   * @param clazz
   *     要在其中查找方法的类。
   * @param ref
   *     方法引用。
   * @return
   *     该方法引用对应的方法的{@link Method}对象。
   */
  @SuppressWarnings("unchecked")
  public static <T, P1, P2, P3, P4, P5, P6, P7> Method getMethodByReference(final Class<T> clazz,
      final VoidMethod7<T, P1, P2, P3, P4, P5, P6, P7> ref) {
    final ReferenceToMethodCache<T> cache =
        (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref, (g) -> {
      if (clazz.isRecord()) {
        return findMethodBySerialization(clazz, (VoidMethod7<T, P1, P2, P3, P4, P5, P6, P7>) g);
      } else if (ClassUtils.isEnumType(clazz)) {
        return findMethodBySerialization(clazz, (VoidMethod7<T, P1, P2, P3, P4, P5, P6, P7>) g);
      } else if (Modifier.isFinal(clazz.getModifiers())) {
        return findMethodBySerialization(clazz, (VoidMethod7<T, P1, P2, P3, P4, P5, P6, P7>) g);
      } else {
        try {
          return findMethod(clazz, (VoidMethod7<T, P1, P2, P3, P4, P5, P6, P7>) g);
        } catch (final Exception e) {
          return findMethodBySerialization(clazz, (VoidMethod7<T, P1, P2, P3, P4, P5, P6, P7>) g);
        }
      }
    });
  }

  /**
   * 通过方法引用获取无返回值且有8个参数的方法。
   *
   * @param <T>
   *     类的类型。
   * @param <P1>
   *     第一个参数的类型。
   * @param <P2>
   *     第二个参数的类型。
   * @param <P3>
   *     第三个参数的类型。
   * @param <P4>
   *     第四个参数的类型。
   * @param <P5>
   *     第五个参数的类型。
   * @param <P6>
   *     第六个参数的类型。
   * @param <P7>
   *     第七个参数的类型。
   * @param <P8>
   *     第八个参数的类型。
   * @param clazz
   *     要在其中查找方法的类。
   * @param ref
   *     方法引用。
   * @return
   *     该方法引用对应的方法的{@link Method}对象。
   */
  @SuppressWarnings("unchecked")
  public static <T, P1, P2, P3, P4, P5, P6, P7, P8> Method getMethodByReference(
      final Class<T> clazz, final VoidMethod8<T, P1, P2, P3, P4, P5, P6, P7, P8> ref) {
    final ReferenceToMethodCache<T> cache =
        (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref, (g) -> {
      if (clazz.isRecord()) {
        return findMethodBySerialization(clazz, (VoidMethod8<T, P1, P2, P3, P4, P5, P6, P7, P8>) g);
      } else if (ClassUtils.isEnumType(clazz)) {
        return findMethodBySerialization(clazz, (VoidMethod8<T, P1, P2, P3, P4, P5, P6, P7, P8>) g);
      } else if (Modifier.isFinal(clazz.getModifiers())) {
        return findMethodBySerialization(clazz, (VoidMethod8<T, P1, P2, P3, P4, P5, P6, P7, P8>) g);
      } else {
        try {
          return findMethod(clazz, (VoidMethod8<T, P1, P2, P3, P4, P5, P6, P7, P8>) g);
        } catch (final Exception e) {
          return findMethodBySerialization(clazz, (VoidMethod8<T, P1, P2, P3, P4, P5, P6, P7, P8>) g);
        }
      }
    });
  }

  /**
   * 通过方法引用获取无返回值且有9个参数的方法。
   *
   * @param <T>
   *     类的类型。
   * @param <P1>
   *     第一个参数的类型。
   * @param <P2>
   *     第二个参数的类型。
   * @param <P3>
   *     第三个参数的类型。
   * @param <P4>
   *     第四个参数的类型。
   * @param <P5>
   *     第五个参数的类型。
   * @param <P6>
   *     第六个参数的类型。
   * @param <P7>
   *     第七个参数的类型。
   * @param <P8>
   *     第八个参数的类型。
   * @param <P9>
   *     第九个参数的类型。
   * @param clazz
   *     要在其中查找方法的类。
   * @param ref
   *     方法引用。
   * @return
   *     该方法引用对应的方法的{@link Method}对象。
   */
  @SuppressWarnings("unchecked")
  public static <T, P1, P2, P3, P4, P5, P6, P7, P8, P9> Method getMethodByReference(
      final Class<T> clazz, final VoidMethod9<T, P1, P2, P3, P4, P5, P6, P7, P8, P9> ref) {
    final ReferenceToMethodCache<T> cache =
        (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref, (g) -> {
      if (clazz.isRecord()) {
        return findMethodBySerialization(clazz, (VoidMethod9<T, P1, P2, P3, P4, P5, P6, P7, P8, P9>) g);
      } else if (ClassUtils.isEnumType(clazz)) {
        return findMethodBySerialization(clazz, (VoidMethod9<T, P1, P2, P3, P4, P5, P6, P7, P8, P9>) g);
      } else if (Modifier.isFinal(clazz.getModifiers())) {
        return findMethodBySerialization(clazz, (VoidMethod9<T, P1, P2, P3, P4, P5, P6, P7, P8, P9>) g);
      } else {
        try {
          return findMethod(clazz, (VoidMethod9<T, P1, P2, P3, P4, P5, P6, P7, P8, P9>) g);
        } catch (final Exception e) {
          return findMethodBySerialization(clazz, (VoidMethod9<T, P1, P2, P3, P4, P5, P6, P7, P8, P9>) g);
        }
      }
    });
  }
}