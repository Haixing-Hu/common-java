////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.annotation.Nullable;

import ltd.qubit.commons.reflect.impl.GetterMethod;

import static ltd.qubit.commons.lang.StringUtils.isEmpty;
import static ltd.qubit.commons.reflect.MethodUtils.getMethodByReference;
import static ltd.qubit.commons.reflect.PropertyUtils.getPropertyNameFromGetter;

/**
 * Provides functions to handle object graphs.
 *
 * @author Haixing Hu
 */
public class ObjectGraphUtils {

  private static <T, R> void ensureFieldExists(@Nullable final Field field,
      final Class<T> type, final GetterMethod<T, R> getter) {
    if (field == null) {
      final Method m = getMethodByReference(type, getter);
      throw new IllegalArgumentException("No field found for the getter: " + m.getName());
    }
  }

  private static <T, R> void ensureMethodExists(final String name, @Nullable final Method method,
      final Class<T> type, final GetterMethod<T, R> getter) {
    if (method == null) {
      throw new IllegalArgumentException("No field found for the getter: " + type.getName() + "." + name);
    }
  }

  /**
   * Gets the path of a property specified by a getter method.
   *
   * @param <T>
   *     the type of the object.
   * @param <R>
   *     the type of the property.
   * @param type
   *     the class of the object.
   * @param getter
   *     the getter method of the property.
   * @return
   *     the path of the property specified by the getter method.
   */
  public static <T, R> String getPropertyPath(final Class<T> type,
      final GetterMethod<T, R> getter) {
    // final Field field = getField(type, getter);
    // ensureFieldExists(field, type, getter);
    // assert field != null;
    // return field.getName();
    final Method m = getMethodByReference(type, getter);
    return getPropertyNameFromGetter(m);
  }

  /**
   * Gets the path of a property specified by a chain of getter methods.
   *
   * @param <T>
   *     the type of the object.
   * @param <P1>
   *     the return type of the first getter, which is the type of the property
   *     of the original object specified by the first getter.
   * @param <R>
   *     the return type of the second getter, which is the type of the property
   *     of the result of the first getter specified by the second getter.
   * @param type
   *     the class of the object.
   * @param g1
   *     the first getter method, which is the getter of the original object.
   * @param g2
   *     the second getter method, which is the getter of the result of the first getter.
   * @return
   *     the path of the property specified by the chain of getter methods.
   */
  public static <T, P1, R> String getPropertyPath(final Class<T> type,
      final GetterMethod<T, P1> g1, final GetterMethod<P1, R> g2) {
    // final Field f1 = getField(type, g1);
    // ensureFieldExists(f1, type, g1);
    // assert f1 != null;
    // @SuppressWarnings("unchecked")
    // final Class<P1> c1 = (Class<P1>) f1.getType();
    // final Field f2 = getField(c1, g2);
    // ensureFieldExists(f2, c1, g2);
    // assert f2 != null;
    // return f1.getName() + '.' + f2.getName();
    final Method m1 = getMethodByReference(type, g1);
    final String n1 = getPropertyNameFromGetter(m1);
    @SuppressWarnings("unchecked")
    final Class<P1> r1 = (Class<P1>) m1.getReturnType();
    final Method m2 = getMethodByReference(r1, g2);
    final String n2 = getPropertyNameFromGetter(m2);
    return n1 + '.' + n2;
  }

  /**
   * Gets the path of a property specified by a chain of getter methods.
   *
   * @param <T>
   *     the type of the object.
   * @param <P1>
   *     the return type of the first getter, which is the type of the property
   *     of the original object specified by the first getter.
   * @param <P2>
   *     the return type of the second getter, which is the type of the property
   *     of the result of the first getter specified by the second getter.
   * @param <R>
   *     the return type of the third getter, which is the type of the property
   *     of the result of the second getter specified by the third getter.
   * @param type
   *     the class of the object.
   * @param g1
   *     the first getter method, which is the getter of the original object.
   * @param g2
   *     the second getter method, which is the getter of the result of the
   *     first getter.
   * @param g3
   *     the third getter method, which is the getter of the result of the
   *     second getter.
   * @return
   *     the path of the property specified by the chain of getter methods.
   */
  public static <T, P1, P2, R> String getPropertyPath(final Class<T> type,
      final GetterMethod<T, P1> g1, final GetterMethod<P1, P2> g2,
      final GetterMethod<P2, R> g3) {
    // final Field f1 = getField(type, g1);
    // ensureFieldExists(f1, type, g1);
    // assert f1 != null;
    // @SuppressWarnings("unchecked")
    // final Class<P1> c1 = (Class<P1>) f1.getType();
    // final Field f2 = getField(c1, g2);
    // ensureFieldExists(f2, c1, g2);
    // assert f2 != null;
    // @SuppressWarnings("unchecked")
    // final Class<P2> c2 = (Class<P2>) f2.getType();
    // final Field f3 = getField(c2, g3);
    // assert f3 != null;
    // ensureFieldExists(f3, c2, g3);
    // return f1.getName() + '.' + f2.getName() + '.' + f3.getName();
    final Method m1 = getMethodByReference(type, g1);
    final String n1 = getPropertyNameFromGetter(m1);
    @SuppressWarnings("unchecked")
    final Class<P1> r1 = (Class<P1>) m1.getReturnType();
    final Method m2 = getMethodByReference(r1, g2);
    final String n2 = getPropertyNameFromGetter(m2);
    @SuppressWarnings("unchecked")
    final Class<P2> r2 = (Class<P2>) m2.getReturnType();
    final Method m3 = getMethodByReference(r2, g3);
    final String n3 = getPropertyNameFromGetter(m3);
    return n1 + '.' + n2 + "." + n3;
  }

  /**
   * Gets the path of a property specified by a chain of getter methods.
   *
   * @param <T>
   *     the type of the object.
   * @param <P1>
   *     the return type of the first getter, which is the type of the property
   *     of the original object specified by the first getter.
   * @param <P2>
   *     the return type of the second getter, which is the type of the property
   *     of the result of the first getter specified by the second getter.
   * @param <P3>
   *     the return type of the third getter, which is the type of the property
   *     of the result of the second getter specified by the third getter.
   * @param <R>
   *     the return type of the fourth getter, which is the type of the property
   *     of the result of the third getter specified by the fourth getter.
   * @param type
   *     the class of the object.
   * @param g1
   *     the first getter method, which is the getter of the original object.
   * @param g2
   *     the second getter method, which is the getter of the result of the
   *     first getter.
   * @param g3
   *     the third getter method, which is the getter of the result of the
   *     second getter.
   * @param g4
   *     the fourth getter method, which is the getter of the result of the
   *     third getter.
   * @return
   *     the path of the property specified by the chain of getter methods.
   */
  public static <T, P1, P2, P3, R> String getPropertyPath(final Class<T> type,
      final GetterMethod<T, P1> g1, final GetterMethod<P1, P2> g2,
      final GetterMethod<P2, P3> g3, final GetterMethod<P3, R> g4) {
    // final Field f1 = getField(type, g1);
    // ensureFieldExists(f1, type, g1);
    // assert f1 != null;
    // @SuppressWarnings("unchecked")
    // final Class<P1> c1 = (Class<P1>) f1.getType();
    // final Field f2 = getField(c1, g2);
    // ensureFieldExists(f2, c1, g2);
    // assert f2 != null;
    // @SuppressWarnings("unchecked")
    // final Class<P2> c2 = (Class<P2>) f2.getType();
    // final Field f3 = getField(c2, g3);
    // ensureFieldExists(f3, c2, g3);
    // assert f3 != null;
    // @SuppressWarnings("unchecked")
    // final Class<P3> c3 = (Class<P3>) f3.getType();
    // final Field f4 = getField(c3, g4);
    // ensureFieldExists(f4, c3, g4);
    // assert f4 != null;
    // return f1.getName() + '.' + f2.getName() + '.' + f3.getName() + '.' + f4.getName();
    final Method m1 = getMethodByReference(type, g1);
    final String n1 = getPropertyNameFromGetter(m1);
    @SuppressWarnings("unchecked")
    final Class<P1> r1 = (Class<P1>) m1.getReturnType();
    final Method m2 = getMethodByReference(r1, g2);
    final String n2 = getPropertyNameFromGetter(m2);
    @SuppressWarnings("unchecked")
    final Class<P2> r2 = (Class<P2>) m2.getReturnType();
    final Method m3 = getMethodByReference(r2, g3);
    final String n3 = getPropertyNameFromGetter(m3);
    @SuppressWarnings("unchecked")
    final Class<P3> r3 = (Class<P3>) m3.getReturnType();
    final Method m4 = getMethodByReference(r3, g4);
    final String n4 = getPropertyNameFromGetter(m4);
    return n1 + '.' + n2 + "." + n3 + "." + n4;
  }

  /**
   * Gets the path of a property specified by a chain of getter methods.
   *
   * @param <T>
   *     the type of the object.
   * @param <P1>
   *     the return type of the first getter, which is the type of the property
   *     of the original object specified by the first getter.
   * @param <P2>
   *     the return type of the second getter, which is the type of the property
   *     of the result of the first getter specified by the second getter.
   * @param <P3>
   *     the return type of the third getter, which is the type of the property
   *     of the result of the second getter specified by the third getter.
   * @param <P4>
   *     the return type of the fourth getter, which is the type of the property
   *     of the result of the third getter specified by the fourth getter.
   * @param <R>
   *     the return type of the fifth getter, which is the type of the property
   *     of the result of the third getter specified by the fifth getter.
   * @param type
   *     the class of the object.
   * @param g1
   *     the first getter method, which is the getter of the original object.
   * @param g2
   *     the second getter method, which is the getter of the result of the
   *     first getter.
   * @param g3
   *     the third getter method, which is the getter of the result of the
   *     second getter.
   * @param g4
   *     the fourth getter method, which is the getter of the result of the
   *     third getter.
   * @param g5
   *     the fifth getter method, which is the getter of the result of the
   *     fourth getter.
   * @return
   *     the path of the property specified by the chain of getter methods.
   */
  public static <T, P1, P2, P3, P4, R> String getPropertyPath(final Class<T> type,
      final GetterMethod<T, P1> g1, final GetterMethod<P1, P2> g2,
      final GetterMethod<P2, P3> g3, final GetterMethod<P3, P4> g4,
      final GetterMethod<P4, R> g5) {
    // final Field f1 = getField(type, g1);
    // ensureFieldExists(f1, type, g1);
    // assert f1 != null;
    // @SuppressWarnings("unchecked")
    // final Class<P1> c1 = (Class<P1>) f1.getType();
    // final Field f2 = getField(c1, g2);
    // ensureFieldExists(f2, c1, g2);
    // assert f2 != null;
    // @SuppressWarnings("unchecked")
    // final Class<P2> c2 = (Class<P2>) f2.getType();
    // final Field f3 = getField(c2, g3);
    // ensureFieldExists(f3, c2, g3);
    // assert f3 != null;
    // @SuppressWarnings("unchecked")
    // final Class<P3> c3 = (Class<P3>) f3.getType();
    // final Field f4 = getField(c3, g4);
    // ensureFieldExists(f4, c3, g4);
    // assert f4 != null;
    // @SuppressWarnings("unchecked")
    // final Class<P4> c4 = (Class<P4>) f4.getType();
    // final Field f5 = getField(c4, g5);
    // ensureFieldExists(f5, c4, g5);
    // assert f5 != null;
    // return f1.getName() + '.' + f2.getName() + '.' + f3.getName()
    //     + '.' + f4.getName() + '.' + f5.getName();
    final Method m1 = getMethodByReference(type, g1);
    final String n1 = getPropertyNameFromGetter(m1);
    @SuppressWarnings("unchecked")
    final Class<P1> r1 = (Class<P1>) m1.getReturnType();
    final Method m2 = getMethodByReference(r1, g2);
    final String n2 = getPropertyNameFromGetter(m2);
    @SuppressWarnings("unchecked")
    final Class<P2> r2 = (Class<P2>) m2.getReturnType();
    final Method m3 = getMethodByReference(r2, g3);
    final String n3 = getPropertyNameFromGetter(m3);
    @SuppressWarnings("unchecked")
    final Class<P3> r3 = (Class<P3>) m3.getReturnType();
    final Method m4 = getMethodByReference(r3, g4);
    final String n4 = getPropertyNameFromGetter(m4);
    @SuppressWarnings("unchecked")
    final Class<P4> r4 = (Class<P4>) m4.getReturnType();
    final Method m5 = getMethodByReference(r4, g5);
    final String n5 = getPropertyNameFromGetter(m5);
    return n1 + '.' + n2 + "." + n3 + "." + n4 + "." + n5;
  }

  /**
   * Tests whether the specified class has the property specified in a object
   * graph path.
   *
   * @param cls
   *     the specified class.
   * @param path
   *     the specified path in the object graph. The path <b>may</b> contain
   *     computed properties.
   * @return
   *     {@code true} if the specified class has the property specified in the
   *     specified object graph path; {@code false} otherwise.
   */
  public static boolean hasProperty(final Class<?> cls, final String path) {
    if (isEmpty(path)) {
      return false;
    }
    int index = path.indexOf('.');
    Class<?> c = cls;
    String p = path;
    while (index >= 0) {
      final String name = p.substring(0, index);
      // 使用BeanInfo，从而支持计算属性
      final BeanInfo info = BeanInfo.of(c);
      final Property prop = info.getProperty(name);
      if (prop == null) {
        return false;
      }
      c = prop.getType();
      p = p.substring(index + 1);
      index = p.indexOf('.');
    }
    return BeanInfo.of(c).hasProperty(p);
  }

  /**
   * Gets the value of a property of the specified object specified by an object
   * graph path.
   *
   * @param obj
   *     the specified object.
   * @param path
   *     the specified path in the object graph. The path <b>may</b> contain
   *     computed properties.
   * @return
   *     the value of a property of the specified object specified by the object
   *     graph path, or {@code null} if any object in the path is {@code null}.
   * @throws FieldNotExistException
   *     if there is no such path exist in the object graph.
   */
  @Nullable
  public static Object getPropertyValue(@Nullable final Object obj, final String path) {
    if (isEmpty(path)) {
      return obj;
    }
    if (obj == null) {
      return null;
    }
    final Class<?> originalClass = obj.getClass();
    final StringBuilder pathBuilder = new StringBuilder();
    Object currentObject = obj;
    String currentPath = path;
    Class<?> currentClass = originalClass;
    int index = currentPath.indexOf('.');
    while (index >= 0) {
      final String fieldName = currentPath.substring(0, index);
      if (pathBuilder.length() > 0) {
        pathBuilder.append('.');
      }
      pathBuilder.append(fieldName);
      // 使用 BeanInfo 来获取 Property，从而支持可计算属性
      final BeanInfo info = BeanInfo.of(currentClass);
      final Property prop = info.getProperty(fieldName);
      if (prop == null) {
        throw new FieldNotExistException(originalClass, pathBuilder.toString());
      }
      final Object fieldValue = prop.getValue(currentObject);
      if (fieldValue == null) {
        return null;
      }
      currentClass = prop.getType();
      currentObject = fieldValue;
      currentPath = currentPath.substring(index + 1);
      index = currentPath.indexOf('.');
    }
    // 使用 BeanInfo 来获取 Property，从而支持可计算属性
    final BeanInfo info = BeanInfo.of(currentClass);
    final Property prop = info.getProperty(currentPath);
    if (prop == null) {
      throw new FieldNotExistException(originalClass, path);
    }
    return info.get(currentObject, prop);
  }

  /**
   * Gets the type of the property of the specified class specified by an object
   * graph path.
   *
   * @param cls
   *     the specified class.
   * @param path
   *     the specified path in the object graph. The path <b>may</b> contain
   *     computed properties.
   * @return
   *     the type of the property of the specified class specified by the object
   *     graph path.
   * @throws FieldNotExistException
   *     if there is no such path exist in the object graph.
   */
  public static Class<?> getPropertyType(final Class<?> cls, final String path) {
    if (isEmpty(path)) {
      return cls;
    }
    final StringBuilder pathBuilder = new StringBuilder();
    String currentPath = path;
    Class<?> currentClass = cls;
    int index = currentPath.indexOf('.');
    while (index >= 0) {
      final String fieldName = currentPath.substring(0, index);
      if (pathBuilder.length() > 0) {
        pathBuilder.append('.');
      }
      pathBuilder.append(fieldName);
      // 使用 BeanInfo 来获取 Property，从而支持可计算属性
      final BeanInfo info = BeanInfo.of(currentClass);
      final Property prop = info.getProperty(fieldName);
      if (prop == null) {
        throw new FieldNotExistException(cls, pathBuilder.toString());
      }
      currentClass = prop.getType();
      currentPath = currentPath.substring(index + 1);
      index = currentPath.indexOf('.');
    }
    // 使用 BeanInfo 来获取 Property，从而支持可计算属性
    final BeanInfo info = BeanInfo.of(currentClass);
    final Property prop = info.getProperty(currentPath);
    if (prop == null) {
      throw new FieldNotExistException(cls, path);
    }
    return prop.getType();
  }

  /**
   * Sets the value of a property of the specified object specified by an object
   * graph path.
   * <p>
   * This method will not create intermediate objects if they are {@code null}.
   * Instead, it will throw a {@link NullPointerException} if any intermediate
   * object in the path is {@code null}.
   *
   * @param obj
   *     the specified object.
   * @param path
   *     the specified path in the object graph. The path <b>must NOT</b> contain
   *     computed properties.
   * @param value
   *     the value of a property to be set.
   * @throws FieldNotExistException
   *     if there is no such path exist in the object graph.
   * @throws NullPointerException
   *     if any intermediate object in the path is {@code null}.
   * @see #setPropertyValue(Object, String, Object, boolean)
   */
  public static void setPropertyValue(final Object obj, final String path,
      @Nullable final Object value) {
    setPropertyValue(obj, path, value, false);
  }

  /**
   * Sets the value of a property of the specified object specified by an object
   * graph path.
   * <p>
   * If the argument {@code createIntermediate} is {@code true}, this method will
   * create intermediate objects if they are {@code null}. Otherwise, it will
   * throw a {@link NullPointerException} if any intermediate object in the path
   * is {@code null}.
   *
   * @param obj
   *     the specified object.
   * @param path
   *     the specified path in the object graph. The path <b>must NOT</b> contain
   *     computed properties.
   * @param value
   *     the value of a property to be set.
   * @param createIntermediate
   *     whether to create intermediate objects if they are {@code null}.
   * @throws FieldNotExistException
   *     if there is no such path exist in the object graph.
   * @throws NullPointerException
   *     if the {@code createIntermediate} argument is {@code false} and any
   *     intermediate object in the path is {@code null}.
   * @see #setPropertyValue(Object, String, Object)
   */
  public static void setPropertyValue(final Object obj, final String path,
      @Nullable final Object value, final boolean createIntermediate) {
    if (obj == null) {
      throw new NullPointerException("object cannot be null.");
    }
    final Class<?> originalClass = obj.getClass();
    final StringBuilder pathBuilder = new StringBuilder();
    Class<?> currentClass = originalClass;
    String currentPath = path;
    Object currentObject = obj;
    int index = currentPath.indexOf('.');
    while (index >= 0) {
      final String fieldName = currentPath.substring(0, index);
      if (pathBuilder.length() > 0) {
        pathBuilder.append('.');
      }
      pathBuilder.append(fieldName);
      // 使用 BeanInfo 来获取 Property
      final BeanInfo info = BeanInfo.of(currentClass);
      final Property prop = info.getProperty(fieldName);
      if (prop == null) {
        throw new FieldNotExistException(originalClass, pathBuilder.toString());
      }
      if (prop.isReadonly() || prop.isComputed()) {
        throw new ReflectionException("Cannot write a read-only or computed property:" + pathBuilder);
      }
      Object fieldValue = prop.getValue(currentObject);
      if (fieldValue == null) {
        if (createIntermediate) {
          fieldValue = ConstructorUtils.newInstance(prop.getType());
          prop.setValue(currentObject, fieldValue);
        } else {
          throw new NullPointerException("The " + pathBuilder + " of the specified object is null.");
        }
      }
      currentClass = prop.getType();
      currentObject = fieldValue;
      currentPath = currentPath.substring(index + 1);
      index = currentPath.indexOf('.');
    }
    final BeanInfo info = BeanInfo.of(currentClass);
    final Property prop = info.getProperty(currentPath);
    if (prop == null) {
      throw new FieldNotExistException(originalClass, path);
    }
    prop.setValue(currentObject, value);
  }
}
