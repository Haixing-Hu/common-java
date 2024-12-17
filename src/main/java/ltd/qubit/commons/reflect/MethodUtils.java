////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

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
import static ltd.qubit.commons.reflect.Option.ALL_EXCLUDE_BRIDGE;
import static ltd.qubit.commons.reflect.Option.DEFAULT;
import static ltd.qubit.commons.reflect.impl.GetMethodByReferenceImpl.GETTER_METHOD_CACHES;
import static ltd.qubit.commons.reflect.impl.GetMethodByReferenceImpl.findMethod;
import static ltd.qubit.commons.reflect.impl.GetRecordMethodByReferenceImpl.findRecordMethod;

/**
 * Provides utility reflection methods focused on methods, originally from
 * Commons BeanUtils. Differences from the BeanUtils version may be noted,
 * especially where similar functionality already existed within Lang.
 * <h2>Known Limitations</h2>
 * <h3>Accessing Public Methods In A Default Access Superclass</h3>
 *
 * <p>There is an issue when invoking public methods contained in a default
 * access superclass on JREs prior to 1.4. Reflection locates these methods fine
 * and correctly assigns them as public. However, an
 * {@code IllegalAccessException} is thrown if the method is invoked.
 *
 * <p>{@code MethodUtils} contains a workaround for this situation. It will
 * attempt to call {@code setAccessible} on this method. If this call succeeds,
 * then the method can be invoked as normal. This call will only succeed when
 * the application has sufficient security privileges. If this call fails then
 * the method may fail.
 *
 * @author Haixing Hu
 * @since 1.0.0
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
   * Gets all methods of a class.
   *
   * @param type
   *     The class on which to get the methods.
   * @param options
   *     A bitwise combination of reflection options defined in the {@link
   *     Option} class. The default value could be {@link Option#DEFAULT}.
   * @return the array of all specified methods; or an empty array if no such
   *     method.
   * @throws ReflectionException
   *     if any error occurred.
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
   * Gets a method of a class that matches the given name and the given
   * parameter types.
   *
   * <p>NOTE: if the {@code options} argument contains {@link Option#ANCESTOR}
   * , and there is more than one method with the specified name and parameter
   * types declared in the specified class or its ancestor class or its ancestor
   * interfaces, the function will try to return the method with the shallower
   * depth; if there are more than one method has the specified name in the same
   * depth, the function will returns the one with a more precise returned type;
   * otherwise, the function will throw an {@link AmbiguousMemberException}.
   *
   * @param type
   *     The class on which to get the method.
   * @param options
   *     A bitwise combination of reflection options defined in the {@link
   *     Option} class. The default value could be {@link Option#DEFAULT}.
   * @param name
   *     The name of the method to be get.
   * @param paramTypes
   *     The types of the parameters of the method to be get, or {@code null} or
   *     an empty array if the method to be get has no parameter.
   * @return the specified method, or {@code null} if no such field.
   * @throws AmbiguousMemberException
   *     if tow or more matching methods found.
   * @throws ReflectionException
   *     if any error occurred.
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
   * Gets a method of a class that matches the given name and the compatible
   * parameter types.
   *
   * <p>NOTE: if the {@code options} argument contains {@link Option#ANCESTOR}
   * , and there is more than one method with the specified name and parameter
   * types declared in the specified class or its ancestor class or its ancestor
   * interfaces, the function will try to return the method with the shallower
   * depth; if there are more than one method has the specified name in the same
   * depth, the function will returns the one with a more precise returned type;
   * otherwise, the function will throw an {@link AmbiguousMemberException}.
   *
   * @param type
   *     The class on which to get the method.
   * @param options
   *     A bitwise combination of reflection options defined in the {@link
   *     Option} class. The default value could be {@link Option#DEFAULT}.
   * @param name
   *     The name of the method to be get.
   * @param paramTypes
   *     The types of the compatible parameters of the method to be get, or an
   *     empty array if the method to be get has no parameter.
   * @return the specified method, or {@code null} if no such field.
   * @throws AmbiguousMemberException
   *     if tow or more matching methods found.
   * @throws ReflectionException
   *     if any error occurred.
   */
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
          final int rc = MemberUtils.compareParameterTypes(current.getActualParameterType(),
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
   * Gets a method of a class that matches the given name.
   *
   * <p>NOTE: if the {@code options} argument contains {@link Option#ANCESTOR}
   * , and there is more than one method with the specified name declared in the
   * specified class or its ancestor class or its ancestor interfaces, the
   * function will try to return the method with the shallower depth; if there
   * are more than one method has the specified name in the same depth, the
   * function will return the one with a more precise returned type; otherwise,
   * the function will throw an {@link AmbiguousMemberException}.
   *
   * @param type
   *     The class on which to get the method.
   * @param options
   *     A bitwise combination of reflection options defined in the {@link
   *     Option} class. The default value could be {@link Option#DEFAULT}.
   * @param name
   *     The name of the method to be get.
   * @return the specified method, or {@code null} if no such field.
   * @throws AmbiguousMemberException
   *     if tow or more matching methods found.
   * @throws ReflectionException
   *     if any error occurred.
   */
  public static Method getMethodByName(final Class<?> type, final int options,
      final String name) throws ReflectionException {
    requireNonNull("name", name);
    final List<MethodInfo> methodInfos = getAllMethodInfos(type);
    MethodInfo result = null;
    boolean ambiguous = false;
    for (final MethodInfo m : methodInfos) {
      final Method method = m.getMethod();
      if (Option.satisfy(type, method, options)
          && name.equals(method.getName())) {
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
   * Gets a method of a class that matches the given name.
   *
   * <p>NOTE: if the {@code options} argument contains {@link Option#ANCESTOR}
   * , and there is more than one method with the specified name declared in the
   * specified class or its ancestor class or its ancestor interfaces, the
   * function will try to return the method with the shallower depth; if there
   * are more than one method has the specified name in the same depth, the
   * function will return the one with a more precise returned type; otherwise,
   * the function will throw an {@link AmbiguousMemberException}.
   *
   * @param type
   *     The class on which to get the method.
   * @param name
   *     The name of the method to be get.
   * @return the specified method, or {@code null} if no such field.
   * @throws AmbiguousMemberException
   *     if tow or more matching methods found.
   * @throws ReflectionException
   *     if any error occurred.
   */
  public static Method getMethodByName(final Class<?> type, final String name)
      throws ReflectionException {
    return getMethodByName(type, DEFAULT, name);
  }

  /**
   * Invoke the specified method of a specified object with the specified
   * arguments.
   *
   * <p>This function simply wrap the IllegalArgumentException to make the
   * message more instructive.
   *
   * @param method
   *     The method to be invoked.
   * @param object
   *     An object whose method should be invoked.
   * @param arguments
   *     The arguments used to invoke the method, which may be {@code null} or
   *     empty if the method has no arguments.
   * @return The value returned by the invoked method.
   * @throws InvokingMethodFailedException
   *     if any error occurred.
   */
  public static Object invokeMethod(final Method method, final Object object,
      @Nullable final Object ... arguments) throws InvokingMethodFailedException {
    requireNonNull("object", object);
    requireNonNull("method", method);
    final Object[] theArguments = defaultIfNull(arguments, EMPTY_OBJECT_ARRAY);
    try {
      return method.invoke(object, theArguments);
    } catch (final Throwable e) {
      throw new InvokingMethodFailedException(object, method, theArguments, e);
    }
  }

  /**
   * Invokes a named method whose parameter types match the given arguments
   * types.
   *
   * <p>This method delegates the method search to
   * {@link #getMatchingMethod(Class, int, String, Class[])}.
   *
   * <p>This method supports calls to methods taking primitive parameters via
   * passing in wrapping classes. So, for example, a {@code Boolean} object
   * would match a {@code boolean} primitive.
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
   * Invokes a named method whose parameter types match the given parameter
   * types.
   *
   * <p>This method delegates the method search to
   * {@link #getMatchingMethod(Class, int, String, Class[])}.
   *
   * <p>This method supports calls to methods taking primitive parameters via
   * passing in wrapping classes. So, for example, a {@code Boolean} object
   * would match a {@code boolean} primitive.
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
  public static Object invokeMethod(final Class<?> type, final int options,
      final String name, @Nullable final Class<?>[] paramTypes,
      @Nullable final Object object, @Nullable final Object[] arguments)
      throws ReflectionException {
    final Class<?>[] theParamTypes = defaultIfNull(paramTypes, EMPTY_CLASS_ARRAY);
    final Method method = getMatchingMethod(type, options, name, theParamTypes);
    if (method == null) {
      throw new MethodNotExistException(type, options, name, theParamTypes);
    }
    try {
      return method.invoke(object, arguments);
    } catch (final Throwable e) {
      throw new InvokingMethodFailedException(type, options, name, theParamTypes, e);
    }
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
    try {
      return method.invoke(object, arguments);
    } catch (final Throwable e) {
      throw new InvokingMethodFailedException(type, options, name, theParamTypes, e);
    }
  }

  /**
   * Gets the fully qualified name of a method.
   *
   * <p>The fully qualified method name has the following syntax:
   * <pre><code>
   * [fully qualified class name]#[methodName](parameter type list)
   * </code></pre>
   *
   * <p>For example,
   * <pre><code>
   * java.lang.String#substring(int, int)
   * </code></pre>
   *
   * @param method
   *     the method.
   * @return the fully qualified name of a method.
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
   * Gets the URI of a method.
   *
   * <p>The URI of a method has the following syntax:
   * <pre><code>
   * method:[fully qualified method name]
   * </code></pre>
   *
   * <p>For example:
   * <pre><code>
   * method:java.lang.String#substring(int, int)
   * </code></pre>
   *
   * @param method
   *     the method.
   * @return the URI of the method.
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
  public static <T, R> Method getMethodByReference(final Class<T> clazz,
      final NonVoidMethod0<T, R> ref) {
    final ReferenceToMethodCache<T> cache = (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref, (g) -> {
      if (clazz.isRecord()) {
        return findRecordMethod(clazz, (NonVoidMethod0<T, R>) g);
      } else {
        return findMethod(clazz, (NonVoidMethod0<T, R>) g);
      }
    });
  }

  @SuppressWarnings("unchecked")
  public static <T, R, P1> Method getMethodByReference(final Class<T> clazz,
      final NonVoidMethod1<T, R, P1> ref) {
    final ReferenceToMethodCache<T> cache =
        (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref,
        (g) -> findMethod(clazz, (NonVoidMethod1<T, R, P1>) g));
  }

  @SuppressWarnings("unchecked")
  public static <T, R> Method getMethodByReference(final Class<T> clazz,
      final NonVoidMethod1Boolean<T, R> ref) {
    final ReferenceToMethodCache<T> cache =
        (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref,
        (g) -> findMethod(clazz, (NonVoidMethod1Boolean<T, R>) g));
  }

  // @SuppressWarnings("unchecked")
  // public static <T, R> Method getMethodByReference(final Class<T> clazz,
  //     final NonVoidMethod1Char<T, R> ref) {
  //   final ReferenceToMethodCache<T> cache =
  //       (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
  //   return cache.computeIfAbsent(ref,
  //       (g) -> findMethod(clazz, (NonVoidMethod1Char<T, R>) g));
  // }

  @SuppressWarnings("unchecked")
  public static <T, R> Method getMethodByReference(final Class<T> clazz,
      final NonVoidMethod1Byte<T, R> ref) {
    final ReferenceToMethodCache<T> cache =
        (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref,
        (g) -> findMethod(clazz, (NonVoidMethod1Byte<T, R>) g));
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

  @SuppressWarnings("unchecked")
  public static <T, R, P1, P2> Method getMethodByReference(final Class<T> clazz,
      final NonVoidMethod2<T, R, P1, P2> ref) {
    final ReferenceToMethodCache<T> cache =
        (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref,
        (g) -> findMethod(clazz, (NonVoidMethod2<T, R, P1, P2>) g));
  }

  @SuppressWarnings("unchecked")
  public static <T, R, P1, P2, P3> Method getMethodByReference(final Class<T> clazz,
      final NonVoidMethod3<T, R, P1, P2, P3> ref) {
    final ReferenceToMethodCache<T> cache =
        (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref,
        (g) -> findMethod(clazz, (NonVoidMethod3<T, R, P1, P2, P3>) g));
  }

  @SuppressWarnings("unchecked")
  public static <T, R, P1, P2, P3, P4> Method getMethodByReference(final Class<T> clazz,
      final NonVoidMethod4<T, R, P1, P2, P3, P4> ref) {
    final ReferenceToMethodCache<T> cache =
        (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref,
        (g) -> findMethod(clazz, (NonVoidMethod4<T, R, P1, P2, P3, P4>) g));
  }

  @SuppressWarnings("unchecked")
  public static <T, R, P1, P2, P3, P4, P5> Method getMethodByReference(final Class<T> clazz,
      final NonVoidMethod5<T, R, P1, P2, P3, P4, P5> ref) {
    final ReferenceToMethodCache<T> cache =
        (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref,
        (g) -> findMethod(clazz, (NonVoidMethod5<T, R, P1, P2, P3, P4, P5>) g));
  }

  @SuppressWarnings("unchecked")
  public static <T, R, P1, P2, P3, P4, P5, P6> Method getMethodByReference(final Class<T> clazz,
      final NonVoidMethod6<T, R, P1, P2, P3, P4, P5, P6> ref) {
    final ReferenceToMethodCache<T> cache =
        (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref,
        (g) -> findMethod(clazz, (NonVoidMethod6<T, R, P1, P2, P3, P4, P5, P6>) g));
  }

  @SuppressWarnings("unchecked")
  public static <T, R, P1, P2, P3, P4, P5, P6, P7> Method getMethodByReference(final Class<T> clazz,
      final NonVoidMethod7<T, R, P1, P2, P3, P4, P5, P6, P7> ref) {
    final ReferenceToMethodCache<T> cache =
        (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref,
        (g) -> findMethod(clazz, (NonVoidMethod7<T, R, P1, P2, P3, P4, P5, P6, P7>) g));
  }

  @SuppressWarnings("unchecked")
  public static <T, R, P1, P2, P3, P4, P5, P6, P7, P8> Method getMethodByReference(
      final Class<T> clazz, final NonVoidMethod8<T, R, P1, P2, P3, P4, P5, P6, P7, P8> ref) {
    final ReferenceToMethodCache<T> cache =
        (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref,
        (g) -> findMethod(clazz, (NonVoidMethod8<T, R, P1, P2, P3, P4, P5, P6, P7, P8>) g));
  }

  @SuppressWarnings("unchecked")
  public static <T, R, P1, P2, P3, P4, P5, P6, P7, P8, P9> Method getMethodByReference(
      final Class<T> clazz, final NonVoidMethod9<T, R, P1, P2, P3, P4, P5, P6, P7, P8, P9> ref) {
    final ReferenceToMethodCache<T> cache =
        (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref,
        (g) -> findMethod(clazz, (NonVoidMethod9<T, R, P1, P2, P3, P4, P5, P6, P7, P8, P9>) g));
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
    return cache.computeIfAbsent(ref,
        (g) -> findMethod(clazz, (VoidMethod0<T>) g));
  }

  @SuppressWarnings("unchecked")
  public static <T, P1> Method getMethodByReference(final Class<T> clazz,
      final VoidMethod1<T, P1> ref) {
    final ReferenceToMethodCache<T> cache =
        (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref,
        (g) -> findMethod(clazz, (VoidMethod1<T, P1>) g));
  }

  @SuppressWarnings("unchecked")
  public static <T> Method getMethodByReference(final Class<T> clazz,
      final VoidMethod1Boolean<T> ref) {
    final ReferenceToMethodCache<T> cache =
        (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref,
        (g) -> findMethod(clazz, (VoidMethod1Boolean<T>) g));
  }

  // @SuppressWarnings("unchecked")
  // public static <T> Method getMethodByReference(final Class<T> clazz,
  //     final VoidMethod1Char<T> ref) {
  //   final ReferenceToMethodCache<T> cache =
  //       (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
  //   return cache.computeIfAbsent(ref,
  //       (g) -> findMethod(clazz, (VoidMethod1Char<T>) g));
  // }

  @SuppressWarnings("unchecked")
  public static <T> Method getMethodByReference(final Class<T> clazz,
      final VoidMethod1Byte<T> ref) {
    final ReferenceToMethodCache<T> cache =
        (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref,
        (g) -> findMethod(clazz, (VoidMethod1Byte<T>) g));
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

  @SuppressWarnings("unchecked")
  public static <T, P1, P2> Method getMethodByReference(final Class<T> clazz,
      final VoidMethod2<T, P1, P2> ref) {
    final ReferenceToMethodCache<T> cache =
        (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref,
        (g) -> findMethod(clazz, (VoidMethod2<T, P1, P2>) g));
  }

  @SuppressWarnings("unchecked")
  public static <T, P1, P2, P3> Method getMethodByReference(final Class<T> clazz,
      final VoidMethod3<T, P1, P2, P3> ref) {
    final ReferenceToMethodCache<T> cache =
        (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref,
        (g) -> findMethod(clazz, (VoidMethod3<T, P1, P2, P3>) g));
  }

  @SuppressWarnings("unchecked")
  public static <T, P1, P2, P3, P4> Method getMethodByReference(final Class<T> clazz,
      final VoidMethod4<T, P1, P2, P3, P4> ref) {
    final ReferenceToMethodCache<T> cache =
        (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref,
        (g) -> findMethod(clazz, (VoidMethod4<T, P1, P2, P3, P4>) g));
  }

  @SuppressWarnings("unchecked")
  public static <T, P1, P2, P3, P4, P5> Method getMethodByReference(final Class<T> clazz,
      final VoidMethod5<T, P1, P2, P3, P4, P5> ref) {
    final ReferenceToMethodCache<T> cache =
        (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref,
        (g) -> findMethod(clazz, (VoidMethod5<T, P1, P2, P3, P4, P5>) g));
  }

  @SuppressWarnings("unchecked")
  public static <T, P1, P2, P3, P4, P5, P6> Method getMethodByReference(final Class<T> clazz,
      final VoidMethod6<T, P1, P2, P3, P4, P5, P6> ref) {
    final ReferenceToMethodCache<T> cache =
        (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref,
        (g) -> findMethod(clazz, (VoidMethod6<T, P1, P2, P3, P4, P5, P6>) g));
  }

  @SuppressWarnings("unchecked")
  public static <T, P1, P2, P3, P4, P5, P6, P7> Method getMethodByReference(final Class<T> clazz,
      final VoidMethod7<T, P1, P2, P3, P4, P5, P6, P7> ref) {
    final ReferenceToMethodCache<T> cache =
        (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref,
        (g) -> findMethod(clazz, (VoidMethod7<T, P1, P2, P3, P4, P5, P6, P7>) g));
  }

  @SuppressWarnings("unchecked")
  public static <T, P1, P2, P3, P4, P5, P6, P7, P8> Method getMethodByReference(
      final Class<T> clazz, final VoidMethod8<T, P1, P2, P3, P4, P5, P6, P7, P8> ref) {
    final ReferenceToMethodCache<T> cache =
        (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref,
        (g) -> findMethod(clazz, (VoidMethod8<T, P1, P2, P3, P4, P5, P6, P7, P8>) g));
  }

  @SuppressWarnings("unchecked")
  public static <T, P1, P2, P3, P4, P5, P6, P7, P8, P9> Method getMethodByReference(
      final Class<T> clazz, final VoidMethod9<T, P1, P2, P3, P4, P5, P6, P7, P8, P9> ref) {
    final ReferenceToMethodCache<T> cache =
        (ReferenceToMethodCache<T>) GETTER_METHOD_CACHES.get(clazz);
    return cache.computeIfAbsent(ref,
        (g) -> findMethod(clazz, (VoidMethod9<T, P1, P2, P3, P4, P5, P6, P7, P8, P9>) g));
  }
}
