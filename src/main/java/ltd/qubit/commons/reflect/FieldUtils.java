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
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import ltd.qubit.commons.lang.ArrayUtils;
import ltd.qubit.commons.reflect.impl.GetterMethod;
import ltd.qubit.commons.reflect.impl.SetterMethod;
import ltd.qubit.commons.reflect.impl.SetterMethodBoolean;
import ltd.qubit.commons.reflect.impl.SetterMethodByte;

import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.lang.StringUtils.isEmpty;
import static ltd.qubit.commons.lang.StringUtils.uppercaseFirstChar;
import static ltd.qubit.commons.reflect.AccessibleUtils.withAccessibleObject;
import static ltd.qubit.commons.reflect.MethodUtils.getMethod;
import static ltd.qubit.commons.reflect.Option.BEAN_FIELD;
import static ltd.qubit.commons.reflect.Option.BEAN_METHOD;
import static ltd.qubit.commons.reflect.PropertyUtils.GET_PREFIX;
import static ltd.qubit.commons.reflect.PropertyUtils.IS_PREFIX;
import static ltd.qubit.commons.reflect.PropertyUtils.SET_PREFIX;

/**
 * 提供通过反射处理字段的实用功能。
 *
 * <p>提供了打破程序员编码的作用域限制的能力。这可以允许更改不应该更改的字段。
 * 应谨慎使用此功能。
 *
 * @author 胡海星
 */
@SuppressWarnings("overloads")
@ThreadSafe
public class FieldUtils {

  public static final String[] IGNORED_FIELD_PREFIXES = {
    "$jacoco",    //  the prefix of fields instrumented by JaCoCo
    "__CLR",      //  the prefix of fields instrumented by OpenClover
  };

  private static final Map<TypeInfo, List<FieldInfo>> FIELD_CACHE = new HashMap<>();

  private static boolean shouldIgnore(final Field field) {
    final String name = field.getName();
    for (final String prefix : IGNORED_FIELD_PREFIXES) {
      if (name.startsWith(prefix)) {
        return true;       // ignore the field with the specified prefix
      }
    }
    return false;
  }

  private static void buildFieldCache(final TypeInfo info) {
    if (FIELD_CACHE.containsKey(info)) {
      return;
    }
    final Class<?> type = info.getType();
    final List<FieldInfo> fields = new ArrayList<>();
    final Type genericSuperclass = type.getGenericSuperclass();
    if (genericSuperclass != null) { // the interface and Object class has no superclass
      final TypeInfo superInfo = new TypeInfo(genericSuperclass, info);
      buildFieldCache(superInfo);    // recursively
      final List<FieldInfo> superFields = FIELD_CACHE.get(superInfo);
      for (final FieldInfo m : superFields) {
        fields.add(new FieldInfo(m, m.getDepth() + 1));
      }
    }
    for (final Type genericInterface : type.getGenericInterfaces()) {
      final TypeInfo superInfo = new TypeInfo(genericInterface, info);
      buildFieldCache(superInfo);    // recursively
      final List<FieldInfo> superFields = FIELD_CACHE.get(superInfo);
      for (final FieldInfo m : superFields) {
        fields.add(new FieldInfo(m, m.getDepth() + 1));
      }
    }
    for (final Field field : type.getDeclaredFields()) {
      if (shouldIgnore(field)) {
        continue;                   // ignore the field with the specified prefix
      }
      fields.add(new FieldInfo(info, field, 0));
    }
    // sort the fields by their depth, but still keep the declaring order of fields
    Collections.sort(fields);
    FIELD_CACHE.put(info, fields);
  }

  private static synchronized List<FieldInfo> getAllFieldsImpl(final Class<?> type) {
    final TypeInfo info = new TypeInfo(type);
    buildFieldCache(info);
    return FIELD_CACHE.get(info);
  }

  /**
   * 获取类的所有字段。
   *
   * @param cls
   *     要获取字段的类。
   * @return
   *     所有指定字段的列表；如果没有此类字段则返回空列表。
   * @throws ReflectionException
   *     如果发生任何错误。
   */
  public static List<Field> getAllFields(final Class<?> cls)
      throws ReflectionException {
    return getAllFields(cls, Option.DEFAULT);
  }

  /**
   * 获取类的所有字段。
   *
   * @param cls
   *     要获取字段的类。
   * @param options
   *     在 {@link Option} 类中定义的反射选项的按位组合。默认值可以是 {@link Option#DEFAULT}。
   * @return
   *     所有指定字段的列表；如果没有此类字段则返回空列表。
   * @throws ReflectionException
   *     如果发生任何错误。
   */
  public static List<Field> getAllFields(final Class<?> cls, final int options)
      throws ReflectionException {
    final List<FieldInfo> members = getAllFieldsImpl(cls);
    final List<Field> result = new ArrayList<>();
    for (final FieldInfo m : members) {
      if (Option.satisfy(cls, m.getField(), options)) {
        result.add(m.getField());
      }
    }
    return result;
  }

  /**
   * 获取类的所有字段名称。
   *
   * @param cls
   *     要获取字段的类。
   * @return 所有指定字段名称的列表；如果没有此类字段则返回空列表。
   * @throws ReflectionException
   *     如果发生任何错误。
   */
  public static List<String> getAllFieldNames(final Class<?> cls)
      throws ReflectionException {
    return getAllFieldNames(cls, Option.DEFAULT);
  }

  /**
   * 获取类的所有字段名称。
   *
   * @param cls
   *     要获取字段的类。
   * @param options
   *     在 {@link Option} 类中定义的反射选项的按位组合。默认值可以是 {@link Option#DEFAULT}。
   * @return 所有指定字段名称的列表；如果没有此类字段则返回空列表。
   * @throws ReflectionException
   *     如果发生任何错误。
   */
  public static List<String> getAllFieldNames(final Class<?> cls,
      final int options) throws ReflectionException {
    final List<FieldInfo> members = getAllFieldsImpl(cls);
    final List<String> result = new ArrayList<>();
    for (final FieldInfo m : members) {
      if (Option.satisfy(cls, m.getField(), options)) {
        result.add(m.getField().getName());
      }
    }
    return result;
  }

  /**
   * 获取类上指定名称字段的元信息。
   *
   * <p>注意：如果 {@code options} 参数包含 {@link Option#ANCESTOR}，
   * 并且在指定类或其祖先类或祖先接口中声明了多个具有指定名称的字段，
   * 该函数将尝试返回深度较浅的字段；如果在相同深度中有多个具有指定名称的字段，
   * 该函数将抛出 {@link AmbiguousMemberException}。
   *
   * @param cls
   *     要获取字段的类。
   * @param name
   *     要获取的字段的名称。
   * @return 指定字段的元信息，如果没有此类字段则返回 {@code null}。
   * @throws ReflectionException
   *     如果发生任何错误。
   */
  @Nullable
  public static FieldInfo getFieldInfo(final Class<?> cls, final String name)
      throws ReflectionException {
    return getFieldInfo(cls, Option.DEFAULT, name);
  }

  /**
   * 获取类上指定名称字段的元信息。
   *
   * <p>注意：如果 {@code options} 参数包含 {@link Option#ANCESTOR}，
   * 并且在指定类或其祖先类或祖先接口中声明了多个具有指定名称的字段，
   * 该函数将尝试返回深度较浅的字段；如果在相同深度中有多个具有指定名称的字段，
   * 该函数将抛出 {@link AmbiguousMemberException}。
   *
   * @param cls
   *     要获取字段的类。
   * @param options
   *     在 {@link Option} 类中定义的反射选项的按位组合。默认值可以是 {@link Option#DEFAULT}。
   * @param name
   *     要获取的字段的名称。
   * @return 指定字段的元信息，如果没有此类字段则返回 {@code null}。
   * @throws ReflectionException
   *     如果发生任何错误。
   */
  @Nullable
  public static FieldInfo getFieldInfo(final Class<?> cls, final int options,
      final String name) throws ReflectionException {
    requireNonNull("name", name);
    final List<FieldInfo> members = getAllFieldsImpl(cls);
    FieldInfo result = null;
    boolean ambiguous = false;
    for (final FieldInfo m : members) {
      if (name.equals(m.getName()) && Option.satisfy(cls, m.getField(), options)) {
        if (result == null) {
          result = m;
        } else if (result.getDepth() > m.getDepth()) {
          result = m;       // keep the field with shallower depth
          ambiguous = false;
        } else if (result.getDepth() == m.getDepth()) {
          ambiguous = true; // more than one field with the same name in the same depth
        }
      }
    }
    if (ambiguous) {
      throw new AmbiguousMemberException(cls, name);
    }
    return result;
  }

  /**
   * 根据指定的  Java Bean 的 getter 的方法引用，获取其对应的属性对象的元信息。
   *
   * @param <T>
   *     指定的 Java Bean的类型。
   * @param <R>
   *     指定的 getter 的返回类型，即对应的属性的类型。
   * @param cls
   *     指定的 Java Bean的类对象。
   * @param getterRef
   *     指定的 getter 的方法引用。
   * @return
   *     指定的 getter 对应的属性对象的元信息；若指定的方法引用不是合法的 Java Bean getter，
   *     或者指定的 getter 没有对应的属性，返回{@code null}。
   */
  public static <T, R> FieldInfo getFieldInfo(final Class<T> cls,
      final GetterMethod<T, R> getterRef) {
    final Method getter = MethodUtils.getMethodByReference(cls, getterRef);
    final String name = PropertyUtils.getPropertyNameFromGetter(getter);
    if (name == null) {
      return null;
    } else {
      return getFieldInfo(cls, BEAN_FIELD, name);
    }
  }

  /**
   * 根据指定的  Java Bean 的 setter 的方法引用，获取其对应的属性对象的元信息。
   *
   * @param <T>
   *     指定的 Java Bean的类型。
   * @param <P>
   *     指定的 setter 的参数类型，即对应的属性的类型。
   * @param cls
   *     指定的 Java Bean的类对象。
   * @param setterRef
   *     指定的 setter 的方法引用。
   * @return
   *     指定的 setter 对应的属性对象的元信息；若指定的方法引用不是合法的 Java Bean getter，
   *     或者指定的 setter 没有对应的属性，返回{@code null}。
   */
  public static <T, P> FieldInfo getFieldInfo(final Class<T> cls,
      final SetterMethod<T, P> setterRef) {
    final Method setter = MethodUtils.getMethodByReference(cls, setterRef);
    final String name = PropertyUtils.getPropertyNameFromSetter(setter);
    if (name == null) {
      return null;
    } else {
      return getFieldInfo(cls, BEAN_FIELD, name);
    }
  }


  /**
   * 根据指定的  Java Bean 的 setter 的方法引用，获取其对应的属性对象的元信息。
   *
   * @param <T>
   *     指定的 Java Bean的类型。
   * @param cls
   *     指定的 Java Bean的类对象。
   * @param setterRef
   *     指定的 setter 的方法引用。
   * @return
   *     指定的 setter 对应的属性对象的元信息；若指定的方法引用不是合法的 Java Bean getter，
   *     或者指定的 setter 没有对应的属性，返回{@code null}。
   */
  public static <T> FieldInfo getFieldInfo(final Class<T> cls,
      final SetterMethodBoolean<T> setterRef) {
    final Method setter = MethodUtils.getMethodByReference(cls, setterRef);
    final String name = PropertyUtils.getPropertyNameFromSetter(setter);
    if (name == null) {
      return null;
    } else {
      return getFieldInfo(cls, BEAN_FIELD, name);
    }
  }


  /**
   * 根据指定的  Java Bean 的 setter 的方法引用，获取其对应的属性对象的元信息。
   *
   * @param <T>
   *     指定的 Java Bean的类型。
   * @param cls
   *     指定的 Java Bean的类对象。
   * @param setterRef
   *     指定的 setter 的方法引用。
   * @return
   *     指定的 setter 对应的属性对象的元信息；若指定的方法引用不是合法的 Java Bean getter，
   *     或者指定的 setter 没有对应的属性，返回{@code null}。
   */
  public static <T> FieldInfo getFieldInfo(final Class<T> cls,
      final SetterMethodByte<T> setterRef) {
    final Method setter = MethodUtils.getMethodByReference(cls, setterRef);
    final String name = PropertyUtils.getPropertyNameFromSetter(setter);
    if (name == null) {
      return null;
    } else {
      return getFieldInfo(cls, BEAN_FIELD, name);
    }
  }

  /**
   * 获取类上指定名称的字段。
   *
   * <p>注意：如果 {@code options} 参数包含 {@link Option#ANCESTOR}，
   * 并且在指定类或其祖先类或祖先接口中声明了多个具有指定名称的字段，
   * 该函数将尝试返回深度较浅的字段；如果在相同深度中有多个具有指定名称的字段，
   * 该函数将抛出 {@link AmbiguousMemberException}。
   *
   * @param cls
   *     要获取字段的类。
   * @param options
   *     在 {@link Option} 类中定义的反射选项的按位组合。默认值可以是 {@link Option#DEFAULT}。
   * @param name
   *     要获取的字段的名称。
   * @return 指定的字段，如果没有此类字段则返回 {@code null}。
   * @throws ReflectionException
   *     如果发生任何错误。
   */
  @Nullable
  public static Field getField(final Class<?> cls, final int options,
      final String name) throws ReflectionException {
    final FieldInfo result = getFieldInfo(cls, options, name);
    return (result == null ? null : result.getField());
  }

  /**
   * 获取类上指定名称的字段。
   *
   * <p>注意：如果 {@code options} 参数包含 {@link Option#ANCESTOR}，
   * 并且在指定类或其祖先类或祖先接口中声明了多个具有指定名称的字段，
   * 该函数将尝试返回深度较浅的字段；如果在相同深度中有多个具有指定名称的字段，
   * 该函数将抛出 {@link AmbiguousMemberException}。
   *
   * @param cls
   *     要获取字段的类。
   * @param name
   *     要获取的字段的名称。
   * @return 指定的字段，如果没有此类字段则返回 {@code null}。
   * @throws ReflectionException
   *     如果发生任何错误。
   */
  @Nullable
  public static Field getField(final Class<?> cls, final String name)
      throws ReflectionException {
    return getField(cls, Option.DEFAULT, name);
  }

  /**
   * 根据指定的  Java Bean 的 getter 的方法引用，获取其对应的属性对象。
   *
   * @param <T>
   *     指定的 Java Bean的类型。
   * @param <R>
   *     指定的 getter 的返回类型，即对应的属性的类型。
   * @param cls
   *     指定的 Java Bean的类对象。
   * @param getterRef
   *     指定的 getter 的方法引用。
   * @return
   *     指定的 getter 对应的属性对象；若指定的方法引用不是合法的 Java Bean getter，或者
   *     指定的 getter 没有对应的属性，返回{@code null}。
   */
  @Nullable
  public static <T, R> Field getField(final Class<T> cls, final GetterMethod<T, R> getterRef) {
    final FieldInfo info = getFieldInfo(cls, getterRef);
    return (info == null ? null : info.getField());
  }

  /**
   * 根据指定的  Java Bean 的 setter 的方法引用，获取其对应的属性对象。
   *
   * @param <T>
   *     指定的 Java Bean的类型。
   * @param <P>
   *     指定的 setter 的参数类型，即对应的属性的类型。
   * @param cls
   *     指定的 Java Bean的类对象。
   * @param setterRef
   *     指定的 setter 的方法引用。
   * @return
   *     指定的 setter 对应的属性对象；若指定的方法引用不是合法的 Java Bean getter，或者
   *     指定的 setter 没有对应的属性，返回{@code null}。
   */
  @Nullable
  public static <T, P> Field getField(final Class<T> cls, final SetterMethod<T, P> setterRef) {
    final FieldInfo info = getFieldInfo(cls, setterRef);
    return (info == null ? null : info.getField());
  }

  /**
   * 根据指定的  Java Bean 的 setter 的方法引用，获取其对应的属性对象。
   *
   * @param <T>
   *     指定的 Java Bean的类型。
   * @param cls
   *     指定的 Java Bean的类对象。
   * @param setterRef
   *     指定的 setter 的方法引用。
   * @return
   *     指定的 setter 对应的属性对象；若指定的方法引用不是合法的 Java Bean getter，或者
   *     指定的 setter 没有对应的属性，返回{@code null}。
   */
  @Nullable
  public static <T> Field getField(final Class<T> cls, final SetterMethodBoolean<T> setterRef) {
    final FieldInfo info = getFieldInfo(cls, setterRef);
    return (info == null ? null : info.getField());
  }

  /**
   * 根据指定的  Java Bean 的 setter 的方法引用，获取其对应的属性对象。
   *
   * @param <T>
   *     指定的 Java Bean的类型。
   * @param cls
   *     指定的 Java Bean的类对象。
   * @param setterRef
   *     指定的 setter 的方法引用。
   * @return
   *     指定的 setter 对应的属性对象；若指定的方法引用不是合法的 Java Bean getter，或者
   *     指定的 setter 没有对应的属性，返回{@code null}。
   */
  @Nullable
  public static <T> Field getField(final Class<T> cls, final SetterMethodByte<T> setterRef) {
    final FieldInfo info = getFieldInfo(cls, setterRef);
    return (info == null ? null : info.getField());
  }

  /**
   * 根据指定的  Java Bean 的 getter 的方法引用，获取其对应的属性的名称。
   *
   * @param <T>
   *     指定的 Java Bean的类型。
   * @param <R>
   *     指定的 getter 的返回类型，即对应的属性的类型。
   * @param cls
   *     指定的 Java Bean的类对象。
   * @param getterRef
   *     指定的 getter 的方法引用。
   * @return
   *     指定的 getter 对应的属性的名称；若指定的方法引用不是合法的 Java Bean getter，返回
   *     {@code null}。
   */
  @Nullable
  public static <T, R> String getFieldName(final Class<T> cls, final GetterMethod<T, R> getterRef) {
    final Method getter = MethodUtils.getMethodByReference(cls, getterRef);
    return PropertyUtils.getPropertyNameFromGetter(getter);
  }

  /**
   * 根据指定的  Java Bean 的 getter 的方法引用，获取其对应的属性的名称。
   *
   * @param cls
   *     指定的 Java Bean的类对象。
   * @param getterRef
   *     指定的 getter 的方法引用。
   * @return
   *     指定的 getter 对应的属性的名称。
   * @throws IllegalArgumentException
   *     若指定的方法引用不是合法的 Java Bean getter.
   */
  @SuppressWarnings("unchecked")
  public static <T> String getFieldNameGeneric(final Class<?> cls,
      final GetterMethod<T, ?> getterRef) {
    final String name = getFieldName((Class<T>) cls, (GetterMethod<T, ?>) getterRef);
    if (name == null) {
      throw new IllegalArgumentException("The getter method reference is not a valid getter of the specified class.");
    }
    return name;
  }

  /**
   * 根据指定的  Java Bean 的 getter 的方法引用数组，获取其对应的属性的名称列表。
   *
   * @param <T>
   *     指定的 Java Bean的类型。
   * @param cls
   *     指定的 Java Bean的类对象。
   * @param getterRefs
   *     指定的 getter 的方法引用数组。
   * @return
   *     指定的 getter 对应的属性的名称列表。
   */
  @SafeVarargs
  public static <T> List<String> getFieldNames(final Class<T> cls, final GetterMethod<T, ?> ... getterRefs ) {
    final List<String> result = new ArrayList<>();
    for (final GetterMethod<T, ?> getterRef : getterRefs) {
      final Method getter = MethodUtils.getMethodByReference(cls, getterRef);
      final String name = PropertyUtils.getPropertyNameFromGetter(getter);
      if (name != null) {
        result.add(name);
      }
    }
    return result;
  }

  /**
   * 根据指定的  Java Bean 的 setter 的方法引用，获取其对应的属性的名称。
   *
   * @param <T>
   *     指定的 Java Bean的类型。
   * @param <R>
   *     指定的 setter 的返回类型，即对应的属性的类型。
   * @param cls
   *     指定的 Java Bean的类对象。
   * @param setterRef
   *     指定的 setter 的方法引用。
   * @return
   *     指定的 setter 对应的属性的名称；若指定的方法引用不是合法的 Java Bean setter，返回
   *     {@code null}。
   */
  @Nullable
  public static <T, R> String getFieldName(final Class<T> cls, final SetterMethod<T, R> setterRef) {
    final Method setter = MethodUtils.getMethodByReference(cls, setterRef);
    return PropertyUtils.getPropertyNameFromSetter(setter);
  }

  /**
   * 根据指定的  Java Bean 的 setter 的方法引用，获取其对应的属性的名称。
   *
   * @param cls
   *     指定的 Java Bean的类对象。
   * @param setterRef
   *     指定的 setter 的方法引用。
   * @return
   *     指定的 setter 对应的属性的名称。
   * @throws IllegalArgumentException
   *     若指定的方法引用不是合法的 Java Bean getter.
   */
  @SuppressWarnings("unchecked")
  public static <T> String getFieldNameGeneric(final Class<?> cls, final SetterMethod<T, ?> setterRef) {
    final String name = getFieldName((Class<Object>) cls, (SetterMethod<Object, Object>) setterRef);
    if (name == null) {
      throw new IllegalArgumentException("The getter method reference is not a valid getter of the specified class.");
    }
    return name;
  }


  /**
   * 根据指定的  Java Bean 的 setter 的方法引用，获取其对应的属性的名称。
   *
   * @param <T>
   *     指定的 Java Bean的类型。
   * @param cls
   *     指定的 Java Bean的类对象。
   * @param setterRef
   *     指定的 setter 的方法引用。
   * @return
   *     指定的 setter 对应的属性的名称；若指定的方法引用不是合法的 Java Bean setter，返回
   *     {@code null}。
   */
  public static <T> String getFieldName(final Class<T> cls,
      final SetterMethodBoolean<T> setterRef) {
    final Method setter = MethodUtils.getMethodByReference(cls, setterRef);
    return PropertyUtils.getPropertyNameFromSetter(setter);
  }

  /**
   * 根据指定的  Java Bean 的 setter 的方法引用，获取其对应的属性的名称。
   *
   * @param <T>
   *     指定的 Java Bean的类型。
   * @param cls
   *     指定的 Java Bean的类对象。
   * @param setterRef
   *     指定的 setter 的方法引用。
   * @return
   *     指定的 setter 对应的属性的名称；若指定的方法引用不是合法的 Java Bean setter，返回
   *     {@code null}。
   */
  public static <T> String getFieldName(final Class<T> cls,
      final SetterMethodByte<T> setterRef) {
    final Method setter = MethodUtils.getMethodByReference(cls, setterRef);
    return PropertyUtils.getPropertyNameFromSetter(setter);
  }

  /**
   * 获取指定对象上字段的值。
   * <p>
   * 如果字段具有原始类型，则值会自动包装为对象。
   *
   * @param getter
   *     要读取的字段的getter方法。
   * @param object
   *     对象，可以为 {@code null}。
   * @return 指定对象的指定字段的值，如果对象为null则返回 {@code null}。
   */
  public static <T, R> R getFieldValue(final GetterMethod<T, R> getter,
      @Nullable final T object) {
    if (object == null) {
      return null;
    } else {
      return getter.invoke(object);
    }
  }

  /**
   * 获取指定对象上字段的值。
   *
   * <p>如果字段具有原始类型，则值会自动包装为对象。
   *
   * <p>注意：如果 {@code options} 参数包含 {@link Option#ANCESTOR}，
   * 并且在指定类或其祖先类或祖先接口中声明了多个具有指定名称的字段，
   * 该函数将尝试读取深度较浅的字段；如果在相同深度中有多个具有指定名称的字段，
   * 该函数将抛出 {@link AmbiguousMemberException}。
   *
   * @param cls
   *     要获取字段值的类。
   * @param options
   *     在 {@link Option} 类中定义的反射选项的按位组合。默认值可以是 {@link Option#DEFAULT}。
   * @param name
   *     字段的名称。
   * @param object
   *     指定类的对象。如果要获取值的字段是静态字段，此参数可以为 {@code null}。
   * @return 指定字段的值。
   * @throws ReflectionException
   *     如果发生任何错误。
   */
  public static Object readField(final Class<?> cls, final int options,
      final String name, @Nullable final Object object)
      throws ReflectionException {
    final Field field = getField(cls, options, name);
    if (field == null) {
      throw new FieldNotExistException(cls, options, name);
    }
    return readField(field, object);
  }

  /**
   * 获取指定对象上Bean字段的值。
   *
   * <p>如果字段具有原始类型，则值会自动包装为对象。
   *
   * <p>注意：如果 {@code options} 参数包含 {@link Option#ANCESTOR}，
   * 并且在指定类或其祖先类或祖先接口中声明了多个具有指定名称的字段，
   * 该函数将尝试读取深度较浅的字段；如果在相同深度中有多个具有指定名称的字段，
   * 该函数将抛出 {@link AmbiguousMemberException}。
   *
   * @param cls
   *     要获取字段值的类。
   * @param name
   *     Bean字段的名称。
   * @param object
   *     指定类的对象。
   * @return 指定字段的值。
   * @throws ReflectionException
   *     如果发生任何错误。
   */
  public static Object readField(final Class<?> cls,final String name,
      final Object object) throws ReflectionException {
    return readField(cls, BEAN_FIELD, name, object);
  }

  /**
   * 获取指定对象上字段的值。
   *
   * <p>如果字段具有原始类型，则值会自动包装为对象。
   *
   * @param field
   *     要读取的字段对象。
   * @param object
   *     指定类的对象。如果要获取值的字段是静态字段，此参数可以为 {@code null}。
   * @return 指定字段的值。
   * @throws ReflectionException
   *     如果发生任何错误。
   */
  public static Object readField(final Field field, @Nullable final Object object) {
    return withAccessibleObject(field, (f) -> f.get(object), true);
  }

  /**
   * 将指定对象的字段设置为指定的新值。
   *
   * <p>如果底层字段具有原始类型，则新值会自动解包。
   *
   * <p>注意：如果 {@code options} 参数包含 {@link Option#ANCESTOR}，
   * 并且在指定类或其祖先类或祖先接口中声明了多个具有指定名称的字段，
   * 该函数将尝试写入深度较浅的字段；如果在相同深度中有多个具有指定名称的字段，
   * 该函数将抛出 {@link AmbiguousMemberException}。
   *
   * @param cls
   *     要设置字段值的类。
   * @param options
   *     在 {@link Option} 类中定义的反射选项的按位组合。默认值可以是 {@link Option#DEFAULT}。
   * @param name
   *     字段的名称。
   * @param object
   *     指定类的对象。
   * @param value
   *     要设置的值。
   * @throws ReflectionException
   *     如果发生任何错误。
   */
  public static void writeField(final Class<?> cls, final int options,
      final String name, final Object object, @Nullable final Object value)
      throws ReflectionException {
    requireNonNull("object", object);
    final Field field = getField(cls, options, name);
    if (field == null) {
      throw new FieldNotExistException(cls, options, name);
    }
    writeField(field, object, value);
  }

  /**
   * 将指定对象的Bean字段设置为指定的新值。
   *
   * <p>如果底层字段具有原始类型，则新值会自动解包。
   *
   * <p>注意：如果 {@code options} 参数包含 {@link Option#ANCESTOR}，
   * 并且在指定类或其祖先类或祖先接口中声明了多个具有指定名称的字段，
   * 该函数将尝试写入深度较浅的字段；如果在相同深度中有多个具有指定名称的字段，
   * 该函数将抛出 {@link AmbiguousMemberException}。
   *
   * @param cls
   *     要设置字段值的类。
   * @param name
   *     Bean字段的名称。
   * @param object
   *     指定类的对象。
   * @param value
   *     要设置的值。
   * @throws ReflectionException
   *     如果发生任何错误。
   */
  public static void writeField(final Class<?> cls, final String name,
      final Object object, @Nullable final Object value)
      throws ReflectionException {
    writeField(cls, BEAN_FIELD, name, object, value);
  }

  /**
   * 将指定对象的字段设置为指定的新值。
   *
   * <p>如果底层字段具有原始类型，则新值会自动解包。
   *
   * @param field
   *     要写入的字段。
   * @param object
   *     对象实例。
   * @param value
   *     要设置的值。
   * @throws ReflectionException
   *     如果发生任何错误。
   */
  public static void writeField(final Field field, final Object object,
      @Nullable final Object value) {
    withAccessibleObject(field, (f) -> f.set(object, value));
  }

  private static final ConcurrentHashMap<Field, Optional<Method>>
      READ_METHOD_CACHE = new ConcurrentHashMap<>();

  private static final ConcurrentHashMap<Field, Optional<Method>>
      WRITE_METHOD_CACHE = new ConcurrentHashMap<>();

  /**
   * 获取给定字段的读取方法。
   *
   * @param field
   *     要获取读取方法的字段。
   * @return 读取方法，如果字段没有读取方法则返回 {@code null}。
   */
  public static Method getReadMethod(final Field field) {
    final Optional<Method> result = READ_METHOD_CACHE.computeIfAbsent(field,
        (f) -> Optional.ofNullable(getReadMethod(f.getDeclaringClass(), f.getName()))
    );
    return result.orElse(null);
  }

  /**
   * 获取给定字段的读取方法。
   *
   * @param ownerClass
   *     拥有字段的类。
   * @param fieldName
   *     要获取读取方法的字段名称。
   * @return 读取方法，如果字段没有读取方法则返回 {@code null}。
   */
  public static Method getReadMethod(final Class<?> ownerClass,
      final String fieldName) {
    final String capitalizedFieldName = uppercaseFirstChar(fieldName);
    // try to find "get${FieldName}"
    final String getterName = GET_PREFIX + capitalizedFieldName;
    final Method getter = getMethod(ownerClass, BEAN_METHOD, getterName, null);
    if (getter != null) {
      return getter;
    }
    // try to find "is${FieldName}" for boolean properties
    final String testerName = IS_PREFIX + capitalizedFieldName;
    final Method tester = getMethod(ownerClass, BEAN_METHOD, testerName, null);
    if (tester != null) {
      return tester;
    }
    // try to find "${fieldName} for properties
    final Method prop = getMethod(ownerClass, BEAN_METHOD, fieldName, null);
    return prop;
  }

  /**
   * 获取给定字段的写入方法。
   *
   * @param field
   *     要获取写入方法的字段。
   * @return 写入方法，如果字段没有写入方法则返回 {@code null}。
   */
  public static Method getWriteMethod(final Field field) {
    final Optional<Method> result = WRITE_METHOD_CACHE.computeIfAbsent(field,
        (f) -> Optional.ofNullable(getWriteMethod(f.getDeclaringClass(),
            f.getName(), f.getType()))
    );
    return result.orElse(null);
  }

  /**
   * 获取指定字段的写入方法。
   *
   * @param ownerClass
   *     拥有字段的类。
   * @param fieldName
   *     指定字段的名称。
   * @param fieldClass
   *     指定字段的类。
   * @return 写入方法，如果字段没有写入方法则返回 {@code null}。
   */
  public static Method getWriteMethod(final Class<?> ownerClass,
      final String fieldName,
      final Class<?> fieldClass) {
    final String capitalizedFieldName = uppercaseFirstChar(fieldName);
    // try to find getProperty
    final String setterName = SET_PREFIX + capitalizedFieldName;
    return getMethod(ownerClass, BEAN_METHOD, setterName, new Class<?>[]{fieldClass});
  }

  /**
   * 检查字段或对应的读取方法是否标注了给定的注解类型。
   *
   * @param field
   *     要检查的字段
   * @param annotationType
   *     要查找的注解类型。
   * @return 如果字段或读取方法标注了给定注解类型则返回true，否则返回false。
   */
  public static boolean isAnnotationPresent(final Field field,
      final Class<? extends Annotation> annotationType) {
    return getAnnotation(field, annotationType) != null;
  }

  private static final Map<AnnotationSignature, Optional<Annotation>>
      ANNOTATION_CACHE = new ConcurrentHashMap<>();

  /**
   * 在指定字段或字段的读取方法上查找给定的注解类型。
   *
   * @param field
   *     指定的字段。
   * @param annotationType
   *     要查找的注解类型。
   * @param <T>
   *     注解的实际类型
   * @return 如果字段或读取方法具有此注解则返回给定注解，否则返回null。
   */
  @SuppressWarnings("unchecked")
  @Nullable
  public static <T extends Annotation> T getAnnotation(final Field field,
      final Class<T> annotationType) {
    final AnnotationSignature key = new AnnotationSignature(field, annotationType);
    final Optional<Annotation> result = ANNOTATION_CACHE.computeIfAbsent(key,
        FieldUtils::getAnnotationImpl);
    return (T) result.orElse(null);
  }

  private static Optional<Annotation> getAnnotationImpl(
      final AnnotationSignature key) {
    final Annotation annotation = key.field.getAnnotation(key.type);
    if (annotation != null) {
      return Optional.of(annotation);
    }
    final Method readMethod = getReadMethod(key.field);
    if (readMethod == null) {
      return Optional.empty();
    } else {
      return Optional.ofNullable(readMethod.getAnnotation(key.type));
    }
  }

  /**
   * 获取指定字段或字段的读取方法的所有注解。
   *
   * @param field
   *     指定的字段。
   * @return
   *     指定字段或字段的读取方法的所有注解列表。
   */
  public static List<Annotation> getAllAnnotations(final Field field) {
    final List<Annotation> result = new ArrayList<>();
    Collections.addAll(result, field.getAnnotations());
    final Method readMethod = getReadMethod(field);
    if (readMethod != null) {
      Collections.addAll(result, readMethod.getAnnotations());
    }
    return result;
  }

  /**
   * 检查字段是否为静态的。
   *
   * @param field
   *     要检查的字段
   * @return 如果字段是静态的则返回true，否则返回false
   */
  public static boolean isStatic(final Field field) {
    return Modifier.isStatic(field.getModifiers());
  }

  /**
   * 检查字段是否为final的。
   *
   * @param field
   *     要检查的字段
   * @return 如果字段是final的则返回true，否则返回false
   */
  public static boolean isFinal(final Field field) {
    return Modifier.isFinal(field.getModifiers());
  }

  /**
   * 检查字段是否为transient的。
   *
   * @param field
   *     要检查的字段
   * @return 如果字段是transient的则返回true，否则返回false
   */
  public static boolean isTransient(final Field field) {
    return Modifier.isTransient(field.getModifiers());
  }

  /**
   * 测试指定类是否具有指定字段。
   *
   * @param cls
   *     指定的类。
   * @param fieldName
   *     指定的字段名称。
   * @return
   *     如果指定类具有指定字段则返回 {@code true}；否则返回 {@code false}。
   */
  public static boolean hasField(final Class<?> cls, final String fieldName) {
    if (isEmpty(fieldName)) {
      return false;
    }
    final List<FieldInfo> fields = getAllFieldsImpl(cls);
    for (final FieldInfo info : fields) {
      if (fieldName.equals(info.getName())) {
        return true;
      }
    }
    return false;
  }

  /**
   * 测试指定类是否具有指定字段。
   *
   * @param cls
   *     指定的类。
   * @param options
   *     在 {@link Option} 类中定义的反射选项的按位组合。默认值可以是 {@link Option#DEFAULT}。
   * @param fieldName
   *     指定的字段名称。
   * @return
   *     如果指定类具有指定字段则返回 {@code true}；否则返回 {@code false}。
   */
  public static boolean hasField(final Class<?> cls, final int options,
      final String fieldName) {
    if (isEmpty(fieldName)) {
      return false;
    }
    final List<FieldInfo> fields = getAllFieldsImpl(cls);
    for (final FieldInfo info : fields) {
      if (Option.satisfy(cls, info.getField(), options)
            && fieldName.equals(info.getName())) {
        return true;
      }
    }
    return false;
  }

  /**
   * 获取指定字段的实际类型。
   *
   * <p>如果字段的类型声明为泛型类，此函数将尝试在可能的情况下推断字段的实际类型。</p>
   *
   * @param ownerClass
   *     指定字段拥有者对象的类对象。
   * @param field
   *     指定的字段对象。
   * @return
   *     指定字段实际类型的类对象。如果字段的类型声明为泛型类，此函数将尝试在可能的情况下推断字段的实际类型。
   * @see <a href="https://www.artima.com/weblogs/viewpost.jsp?thread=208860">Reflecting generics</a>
   * @see <a href="https://stackoverflow.com/questions/3437897/how-do-i-get-a-class-instance-of-generic-type-t/5684761#5684761">How do I get a class instance of generic type T?</a>
   */
  public static Class<?> getActualType(final Class<?> ownerClass, final Field field) {
    if (!(field.getGenericType() instanceof final TypeVariable<?> typeVar)) {
      // 若field的类型不是泛型，则直接返回其类型
      return field.getType();
    }
    final Type result = resolveActualType(ownerClass, typeVar);
    if (result instanceof Class<?>) {
      return (Class<?>) result;
    } else {
      return field.getType();   // 若找不到对应，直接返回此字段的类型
    }
  }

  /**
   * 解析在拥有者类中出现的泛型类型变量。
   *
   * @param ownerClass
   *     类型变量出现的拥有者类。
   * @param typeVar
   *     要解析的类型变量。
   * @return
   *     类型变量的实际类，如果无法解析则返回类型变量本身。
   */
  private static Type resolveActualType(final Class<?> ownerClass,
      final TypeVariable<?> typeVar) {
    if (ownerClass == typeVar.getGenericDeclaration()) {
      // 若泛型field直接声明在其所属类中，换句话说objectClass是一个泛型类；无法获取field的实际类型
      return typeVar;
    }
    // 获取声明泛型类型 genericType 的带参数类
    Class<?> child = ownerClass;
    Class<?> parent = child.getSuperclass();
    while (parent != null && parent != typeVar.getGenericDeclaration()) {
      child = parent;
      parent = parent.getSuperclass();
    }
    if (parent == null) {   // 无法找到field所声明的泛型类，此情况应该不会出现
      return typeVar;
    }
    // 现在 parent 是泛型类 genericType 的声明类，它也应该是个泛型类，child是其子类
    if (!(child.getGenericSuperclass() instanceof final ParameterizedType parameterizedType)) {
      // 此情况应该不会出现
      return typeVar;
    }
    // 依次检查 parent 的泛型参数名称，并且确定其所对应的实际参数类型
    final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
    final TypeVariable<?>[] typeParameters = parent.getTypeParameters();
    for (int i = 0; i < actualTypeArguments.length; ++i) {
      if (typeParameters[i].getName().equals(typeVar.getName())
          && (actualTypeArguments[i] instanceof Class<?>)) {
        return actualTypeArguments[i];
      }
    }
    // 找不到对应
    return typeVar;
  }

  /**
   * 获取参数化类型字段的实际类型参数。
   *
   * @param ownerClass
   *     字段的拥有者类。
   * @param field
   *     字段对象。
   * @return
   *     参数化类型字段的实际类型参数数组。
   *     如果指定字段不是参数化类型，则返回空数组。如果参数化类型字段的任何类型参数
   *     无法解析为具体类，则返回数组中的相应元素为 {@code null}。
   */
  public static Class<?>[] getActualTypeArguments(final Class<?> ownerClass, final Field field) {
    if (!(field.getGenericType() instanceof final ParameterizedType parameterizedType)) {
      // 若field的类型不是带参数类型，返回空数组
      return ArrayUtils.EMPTY_CLASS_ARRAY;
    }
    final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
    final Class<?>[] result = new Class<?>[actualTypeArguments.length];
    for (int i = 0; i < actualTypeArguments.length; ++i) {
      final Type arg = actualTypeArguments[i];
      if (arg instanceof Class<?>) {
        result[i] = (Class<?>) arg;
      } else if (arg instanceof TypeVariable) {
        final Type resolvedArg = resolveActualType(ownerClass, (TypeVariable<?>) arg);
        if (resolvedArg instanceof Class<?>) {
          result[i] = (Class<?>) resolvedArg;
        } else {
          result[i] = null; // 无法确定该参数实际类型
        }
      }
    }
    return result;
  }
}