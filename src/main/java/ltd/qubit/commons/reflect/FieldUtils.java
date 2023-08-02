////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
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

import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.lang.StringUtils.isEmpty;
import static ltd.qubit.commons.lang.StringUtils.uppercaseFirstChar;
import static ltd.qubit.commons.reflect.AccessibleUtils.withAccessibleObject;
import static ltd.qubit.commons.reflect.MethodUtils.getMethod;
import static ltd.qubit.commons.reflect.Option.BEAN_METHOD;
import static ltd.qubit.commons.reflect.PropertyUtils.GET_PREFIX;
import static ltd.qubit.commons.reflect.PropertyUtils.IS_PREFIX;
import static ltd.qubit.commons.reflect.PropertyUtils.SET_PREFIX;

/**
 * Provides utilities for working with fields by reflection.
 *
 * <p>The ability is provided to break the scoping restrictions coded by the
 * programmer. This can allow fields to be changed that shouldn't be. This
 * facility should be used with care.
 *
 * @author Haixing Hu
 * @since 1.0.0
 */
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

  private static synchronized List<FieldInfo> getAllFields(final Class<?> type) {
    final TypeInfo info = new TypeInfo(type);
    buildFieldCache(info);
    return FIELD_CACHE.get(info);
  }

  /**
   * Gets all fields on a class.
   *
   * @param cls
   *     The class on which to get the fields.
   * @param options
   *     A bitwise combination of reflection options defined in the {@link
   *     Option} class. The default value could be {@link Option#DEFAULT}.
   * @return the array of all specified fields; or an empty array if no such
   *     field.
   * @throws ReflectionException
   *     if any error occurred.
   */
  public static List<Field> getAllFields(final Class<?> cls, final int options)
      throws ReflectionException {
    final List<FieldInfo> members = getAllFields(cls);
    final List<Field> result = new ArrayList<>();
    for (final FieldInfo m : members) {
      if (Option.satisfy(cls, m.getField(), options)) {
        result.add(m.getField());
      }
    }
    return result;
  }

  /**
   * Gets all field names on a class.
   *
   * @param cls
   *     The class on which to get the fields.
   * @param options
   *     A bitwise combination of reflection options defined in the {@link
   *     Option} class. The default value could be {@link Option#DEFAULT}.
   * @return the array of all specified field names; or an empty array if no such
   *     field.
   * @throws ReflectionException
   *     if any error occurred.
   */
  public static List<String> getAllFieldNames(final Class<?> cls,
      final int options) throws ReflectionException {
    final List<FieldInfo> members = getAllFields(cls);
    final List<String> result = new ArrayList<>();
    for (final FieldInfo m : members) {
      if (Option.satisfy(cls, m.getField(), options)) {
        result.add(m.getField().getName());
      }
    }
    return result;
  }

  /**
   * Gets the meta-information of the field with a specified name on a class.
   *
   * <p>NOTE: if the {@code options} argument contains {@link Option#ANCESTOR}
   * , and there is more than one field with the specified name declared in the
   * specified class or its ancestor class or its ancestor interfaces, the
   * function will try to return the field with the shallower depth; if there
   * are more than one field has the specified name in the same depth, the
   * function will throw an {@link AmbiguousMemberException}.
   *
   * @param cls
   *     The class on which to get the field.
   * @param options
   *     A bitwise combination of reflection options defined in the {@link
   *     Option} class. The default value could be {@link Option#DEFAULT}.
   * @param name
   *     The name of the field to be getten.
   * @return the meta-information of the specified field, or {@code null} if
   *     no such field.
   * @throws ReflectionException
   *     if any error occurred.
   */
  @Nullable
  public static FieldInfo getFieldInfo(final Class<?> cls, final int options,
      final String name) throws ReflectionException {
    requireNonNull("name", name);
    final List<FieldInfo> members = getAllFields(cls);
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
      return getFieldInfo(cls, Option.BEAN_FIELD, name);
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
      return getFieldInfo(cls, Option.BEAN_FIELD, name);
    }
  }

  /**
   * Gets a field with a specified name on a class.
   *
   * <p>NOTE: if the {@code options} argument contains {@link Option#ANCESTOR}
   * , and there is more than one field with the specified name declared in the
   * specified class or its ancestor class or its ancestor interfaces, the
   * function will try to return the field with the shallower depth; if there
   * are more than one field has the specified name in the same depth, the
   * function will throw an {@link AmbiguousMemberException}.
   *
   * @param cls
   *     The class on which to get the field.
   * @param options
   *     A bitwise combination of reflection options defined in the {@link
   *     Option} class. The default value could be {@link Option#DEFAULT}.
   * @param name
   *     The name of the field to be getten.
   * @return the specified field, or {@code null} if no such field.
   * @throws ReflectionException
   *     if any error occurred.
   */
  @Nullable
  public static Field getField(final Class<?> cls, final int options,
      final String name) throws ReflectionException {
    final FieldInfo result = getFieldInfo(cls, options, name);
    return (result == null ? null : result.getField());
  }

  /**
   * Gets a field with a specified name on a class.
   *
   * <p>NOTE: if the {@code options} argument contains {@link Option#ANCESTOR}
   * , and there is more than one field with the specified name declared in the
   * specified class or its ancestor class or its ancestor interfaces, the
   * function will try to return the field with the shallower depth; if there
   * are more than one field has the specified name in the same depth, the
   * function will throw an {@link AmbiguousMemberException}.
   *
   * @param cls
   *     The class on which to get the field.
   * @param name
   *     The name of the field to be getten.
   * @return the specified field, or {@code null} if no such field.
   * @throws ReflectionException
   *     if any error occurred.
   */
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
  public static <T, R> Field getField(final Class<T> cls,
      final GetterMethod<T, R> getterRef) {
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
  public static <T, P> Field getField(final Class<T> cls,
      final SetterMethod<T, P> setterRef) {
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
  public static <T, R> String getFieldName(final Class<T> cls,
      final GetterMethod<T, R> getterRef) {
    final Method getter = MethodUtils.getMethodByReference(cls, getterRef);
    return PropertyUtils.getPropertyNameFromGetter(getter);
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
  public static <T, R> String getFieldName(final Class<T> cls,
      final SetterMethod<T, R> setterRef) {
    final Method setter = MethodUtils.getMethodByReference(cls, setterRef);
    return PropertyUtils.getPropertyNameFromSetter(setter);
  }

  /**
   * Gets the value of a field on the specified object.
   *
   * <p>The value is automatically wrapped in an object if it has a primitive
   * type.
   *
   * <p>NOTE: if the {@code options} argument contains {@link Option#ANCESTOR}
   * , and there is more than one field with the specified name declared in the
   * specified class or its ancestor class or its ancestor interfaces, the
   * function will try to read the field with the shallower depth; if there are
   * more than one field has the specified name in the same depth, the function
   * will throw an {@link AmbiguousMemberException}.
   *
   * @param cls
   *     The class on which to get the field value.
   * @param options
   *     A bitwise combination of reflection options defined in the {@link
   *     Option} class. The default value could be {@link Option#DEFAULT}.
   * @param name
   *     The name of the field.
   * @param object
   *     The object of the specified class. If the field whose value is to be
   *     get is a static field, this argument may be {@code null}.
   * @return the value of the specified field.
   * @throws ReflectionException
   *     if any error occurred.
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
   * Gets the value of a field on the specified object.
   *
   * <p>The value is automatically wrapped in an object if it has a primitive
   * type.
   *
   * @param field
   *     The field object of the field to be read.
   * @param object
   *     The object of the specified class. If the field whose value is to be
   *     get is a static field, this argument may be {@code null}.
   * @return the value of the specified field.
   * @throws ReflectionException
   *     if any error occurred.
   */
  public static Object readField(final Field field, @Nullable final Object object) {
    return withAccessibleObject(field, (f) -> f.get(object), true);
  }

  /**
   * Sets a field on the specified object to the specified new value.
   *
   * <p>The new value is automatically unwrapped if the underlying field has a
   * primitive type.
   *
   * <p>NOTE: if the {@code options} argument contains {@link Option#ANCESTOR}
   * , and there is more than one field with the specified name declared in the
   * specified class or its ancestor class or its ancestor interfaces, the
   * function will try to write the field with the shallower depth; if there are
   * more than one field has the specified name in the same depth, the function
   * will throw an {@link AmbiguousMemberException}.
   *
   * @param cls
   *     The class on which to set the field value.
   * @param options
   *     A bitwise combination of reflection options defined in the {@link
   *     Option} class. The default value could be {@link Option#DEFAULT}.
   * @param name
   *     The name of the field.
   * @param object
   *     The object of the specified class.
   * @param value
   *     The value to be set.
   * @throws ReflectionException
   *     if any error occurred.
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
   * Sets a field on the specified object to the specified new value.
   *
   * <p>The new value is automatically unwrapped if the underlying field has a
   * primitive type.
   *
   * @param field
   *     The field to be written.
   * @param object
   *     The object instance.
   * @param value
   *     The value to be set.
   * @throws ReflectionException
   *     if any error occurred.
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
   * Get the read method for given field.
   *
   * @param field
   *     the field to get the read method for.
   * @return the read method or {@code null} if field has no read method.
   */
  public static Method getReadMethod(final Field field) {
    final Optional<Method> result = READ_METHOD_CACHE.computeIfAbsent(field,
        (f) -> Optional.ofNullable(getReadMethod(f.getDeclaringClass(), f.getName()))
    );
    return result.orElse(null);
  }

  /**
   * Get the read method for given field.
   *
   * @param ownerClass
   *     the class owns the field.
   * @param fieldName
   *     the name of the field to get the read method for.
   * @return the read method or {@code null} if field has no read method.
   */
  public static Method getReadMethod(final Class<?> ownerClass,
      final String fieldName) {
    final String capitalizedFieldName = uppercaseFirstChar(fieldName);
    // try to find getProperty
    final String getterName = GET_PREFIX + capitalizedFieldName;
    final Method getter = getMethod(ownerClass, BEAN_METHOD, getterName, null);
    if (getter != null) {
      return getter;
    }
    // try to find isProperty for boolean properties
    final String testerName = IS_PREFIX + capitalizedFieldName;
    final Method tester = getMethod(ownerClass, BEAN_METHOD, testerName, null);
    return tester;
  }

  /**
   * Get the write method for given field.
   *
   * @param field
   *     field to get the read method for.
   * @return the write method or {@code null} if field has no read method.
   */
  public static Method getWriteMethod(final Field field) {
    final Optional<Method> result = WRITE_METHOD_CACHE.computeIfAbsent(field,
        (f) -> Optional.ofNullable(getWriteMethod(f.getDeclaringClass(),
            f.getName(), f.getType()))
    );
    return result.orElse(null);
  }

  /**
   * Get the write method for the specified field.
   *
   * @param ownerClass
   *     the class owns the field.
   * @param fieldName
   *     the name of the specified field.
   * @param fieldClass
   *     the class of the specified field.
   * @return the write method or {@code null} if field has no read method.
   */
  public static Method getWriteMethod(final Class<?> ownerClass,
      final String fieldName,
      final Class<?> fieldClass) {
    final String capitalizedFieldName = uppercaseFirstChar(fieldName);
    // try to find getProperty
    final String setterName = SET_PREFIX + capitalizedFieldName;
    return getMethod(ownerClass, BEAN_METHOD, setterName,
        new Class<?>[]{fieldClass});
  }

  /**
   * Checks if field or corresponding read method is annotated with given
   * annotationType.
   *
   * @param field
   *     Field to check
   * @param annotationType
   *     Annotation you're looking for.
   * @return true if field or read method it annotated with given annotationType
   *     or false.
   */
  public static boolean isAnnotationPresent(final Field field,
      final Class<? extends Annotation> annotationType) {
    return getAnnotation(field, annotationType) != null;
  }

  private static final Map<AnnotationSignature, Optional<Annotation>>
      ANNOTATION_CACHE = new ConcurrentHashMap<>();

  /**
   * Looks for given annotationType on the specified field or the read method for
   * the field.
   *
   * @param field
   *     the specified field.
   * @param annotationType
   *     Type of annotation you're looking for.
   * @param <T>
   *     the actual type of annotation
   * @return given annotation if field or read method has this annotation or
   *     null.
   */
  @SuppressWarnings("unchecked")
  public static <T extends Annotation> T getAnnotation(final Field field,
      final Class<T> annotationType) {
    final AnnotationSignature key = new AnnotationSignature(field,
        annotationType);
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
   * Gets all annotations of the specified field or the read method for the
   * field.
   *
   * @param field
   *     the specified field.
   * @return
   *     the list of all annotations of the specified field or the read method
   *     for the field.
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
   * Check if a field is static.
   *
   * @param field
   *     the field to check
   * @return true if the field is static, false otherwise
   */
  public static boolean isStatic(final Field field) {
    return Modifier.isStatic(field.getModifiers());
  }

  /**
   * Check if a field is final.
   *
   * @param field
   *     the field to check
   * @return true if the field is final, false otherwise
   */
  public static boolean isFinal(final Field field) {
    return Modifier.isFinal(field.getModifiers());
  }

  /**
   * Check if a field is transient.
   *
   * @param field
   *     the field to check
   * @return true if the field is transient, false otherwise
   */
  public static boolean isTransient(final Field field) {
    return Modifier.isTransient(field.getModifiers());
  }

  /**
   * Tests whether the specified class has the specified field.
   *
   * @param cls
   *     the specified class.
   * @param fieldName
   *     the specified field name.
   * @return
   *     {@code true} if the specified class has the specified field;
   *     {@code false} otherwise.
   */
  public static boolean hasField(final Class<?> cls, final String fieldName) {
    if (isEmpty(fieldName)) {
      return false;
    }
    final List<FieldInfo> fields = getAllFields(cls);
    for (final FieldInfo info : fields) {
      if (fieldName.equals(info.getName())) {
        return true;
      }
    }
    return false;
  }

  /**
   * Tests whether the specified class has the specified field.
   *
   * @param cls
   *     the specified class.
   * @param options
   *     A bitwise combination of reflection options defined in the {@link
   *     Option} class. The default value could be {@link Option#DEFAULT}.
   * @param fieldName
   *     the specified field name.
   * @return
   *     {@code true} if the specified class has the specified field;
   *     {@code false} otherwise.
   */
  public static boolean hasField(final Class<?> cls, final int options,
      final String fieldName) {
    if (isEmpty(fieldName)) {
      return false;
    }
    final List<FieldInfo> fields = getAllFields(cls);
    for (final FieldInfo info : fields) {
      if (Option.satisfy(cls, info.getField(), options)
            && fieldName.equals(info.getName())) {
        return true;
      }
    }
    return false;
  }

  /**
   * Gets the actual type of a specified field.
   *
   * <p>If the type of the field is declared as a generic class, this function
   * will try to  infer the actual type of the field if possible.</p>
   *
   * @param ownerClass
   *     the class object of the owner object of the specified field.
   * @param field
   *     the specified field object.
   * @return
   *     the class object of the actual type of the specified field. If the type
   *     of the field is declared as a generic class, this function will try to
   *     infer the actual type of the field if possible.
   * @see <a href="https://www.artima.com/weblogs/viewpost.jsp?thread=208860">Reflecting generics</a>
   * @see <a href="https://stackoverflow.com/questions/3437897/how-do-i-get-a-class-instance-of-generic-type-t/5684761#5684761">How do I get a class instance of generic type T?</a>
   */
  public static Class<?> getActualType(final Class<?> ownerClass, final Field field) {
    if (!(field.getGenericType() instanceof TypeVariable)) {
      // 若field的类型不是泛型，则直接返回其类型
      return field.getType();
    }
    final TypeVariable<?> typeVar = (TypeVariable<?>) field.getGenericType();
    final Type result = resolveActualType(ownerClass, typeVar);
    if (result instanceof Class<?>) {
      return (Class<?>) result;
    } else {
      return field.getType();   // 若找不到对应，直接返回此字段的类型
    }
  }

  /**
   * Resolve the generic type variable occurs in a owner class.
   *
   * @param ownerClass
   *     the owner class where the type variable occurs.
   * @param typeVar
   *     the type variable to be resolved.
   * @return
   *     the actual class of the type variable, or the type variable itself
   *     if it cannot be resolved.
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
    if (!(child.getGenericSuperclass() instanceof ParameterizedType)) {
      // 此情况应该不会出现
      return typeVar;
    }
    // 依次检查 parent 的泛型参数名称，并且确定其所对应的实际参数类型
    final ParameterizedType parameterizedType = (ParameterizedType) child.getGenericSuperclass();
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
   * Gets the actual type arguments of a parameterized type field.
   *
   * @param ownerClass
   *     the owner class of the field.
   * @param field
   *     the field object.
   * @return
   *     the array of actual type arguments of the parameterized type field.
   *     If the specified field is not of a parameterized type, returns an empty
   *     array. If any type argument of the parameterized type field cannot be
   *     resolved to a concrete class, the corresponding element in the returned
   *     array is {@code null}.
   */
  public static Class<?>[] getActualTypeArguments(final Class<?> ownerClass, final Field field) {
    if (!(field.getGenericType() instanceof ParameterizedType)) {
      // 若field的类型不是带参数类型，返回空数组
      return ArrayUtils.EMPTY_CLASS_ARRAY;
    }
    final ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
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
