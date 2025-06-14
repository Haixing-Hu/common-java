////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
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
import ltd.qubit.commons.reflect.impl.SetterMethod;

import static ltd.qubit.commons.lang.StringUtils.isEmpty;
import static ltd.qubit.commons.reflect.MethodUtils.getMethodByReference;
import static ltd.qubit.commons.reflect.PropertyUtils.getPropertyNameFromGetter;
import static ltd.qubit.commons.reflect.PropertyUtils.getPropertyNameFromSetter;

/**
 * 提供处理对象图的功能。
 *
 * @author 胡海星
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
   * 获取由getter方法指定的属性的路径。
   *
   * @param <T>
   *     对象的类型。
   * @param <R>
   *     属性的类型。
   * @param type
   *     对象的类。
   * @param getter
   *     属性的getter方法。
   * @return
   *     由getter方法指定的属性的路径。
   */
  @Nullable
  public static <T, R> String getPropertyPath(final Class<T> type,
      final GetterMethod<T, R> getter) {
    final Method m = getMethodByReference(type, getter);
    return getPropertyNameFromGetter(m);
  }

  /**
   * 获取由setter方法指定的属性的路径。
   *
   * @param <T>
   *     对象的类型。
   * @param <R>
   *     属性的类型。
   * @param type
   *     对象的类。
   * @param setter
   *     属性的setter方法。
   * @return
   *     由setter方法指定的属性的路径。
   */
  @Nullable
  public static <T, R> String getPropertyPath(final Class<T> type,
      final SetterMethod<T, R> setter) {
    final Method m = getMethodByReference(type, setter);
    return getPropertyNameFromSetter(m);
  }

  /**
   * 获取由一串getter方法指定的属性的路径。
   *
   * @param <T>
   *     对象的类型。
   * @param <P1>
   *     第一个getter的返回类型，即原始对象中由第一个getter指定的属性的类型。
   * @param <R>
   *     第二个getter的返回类型，即由第一个getter的结果中由第二个getter指定的属性的类型。
   * @param type
   *     对象的类。
   * @param g1
   *     第一个getter方法，即原始对象的getter。
   * @param g2
   *     第二个getter方法，即第一个getter结果的getter。
   * @return
   *     由一串getter方法指定的属性的路径。
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
   * 获取由一串getter方法指定的属性的路径。
   *
   * @param <T>
   *     对象的类型。
   * @param <P1>
   *     第一个getter的返回类型，即原始对象中由第一个getter指定的属性的类型。
   * @param <P2>
   *     第二个getter的返回类型，即由第一个getter的结果中由第二个getter指定的属性的类型。
   * @param <R>
   *     第三个getter的返回类型，即由第二个getter的结果中由第三个getter指定的属性的类型。
   * @param type
   *     对象的类。
   * @param g1
   *     第一个getter方法，即原始对象的getter。
   * @param g2
   *     第二个getter方法，即第一个getter结果的getter。
   * @param g3
   *     第三个getter方法，即第二个getter结果的getter。
   * @return
   *     由一串getter方法指定的属性的路径。
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
   * 获取由一串getter方法指定的属性的路径。
   *
   * @param <T>
   *     对象的类型。
   * @param <P1>
   *     第一个getter的返回类型，即原始对象中由第一个getter指定的属性的类型。
   * @param <P2>
   *     第二个getter的返回类型，即由第一个getter的结果中由第二个getter指定的属性的类型。
   * @param <P3>
   *     第三个getter的返回类型，即由第二个getter的结果中由第三个getter指定的属性的类型。
   * @param <R>
   *     第四个getter的返回类型，即由第三个getter的结果中由第四个getter指定的属性的类型。
   * @param type
   *     对象的类。
   * @param g1
   *     第一个getter方法，即原始对象的getter。
   * @param g2
   *     第二个getter方法，即第一个getter结果的getter。
   * @param g3
   *     第三个getter方法，即第二个getter结果的getter。
   * @param g4
   *     第四个getter方法，即第三个getter结果的getter。
   * @return
   *     由一串getter方法指定的属性的路径。
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
   * 获取由一串getter方法指定的属性的路径。
   *
   * @param <T>
   *     对象的类型。
   * @param <P1>
   *     第一个getter的返回类型，即原始对象中由第一个getter指定的属性的类型。
   * @param <P2>
   *     第二个getter的返回类型，即由第一个getter的结果中由第二个getter指定的属性的类型。
   * @param <P3>
   *     第三个getter的返回类型，即由第二个getter的结果中由第三个getter指定的属性的类型。
   * @param <P4>
   *     第四个getter的返回类型，即由第三个getter的结果中由第四个getter指定的属性的类型。
   * @param <R>
   *     第五个getter的返回类型，即由第四个getter的结果中由第五个getter指定的属性的类型。
   * @param type
   *     对象的类。
   * @param g1
   *     第一个getter方法，即原始对象的getter。
   * @param g2
   *     第二个getter方法，即第一个getter结果的getter。
   * @param g3
   *     第三个getter方法，即第二个getter结果的getter。
   * @param g4
   *     第四个getter方法，即第三个getter结果的getter。
   * @param g5
   *     第五个getter方法，即第四个getter结果的getter。
   * @return
   *     由一串getter方法指定的属性的路径。
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
   * 测试指定的类是否具有在对象图路径中指定的属性。
   *
   * @param cls
   *     指定的类。
   * @param path
   *     对象图中的指定路径。该路径<b>可以</b>包含计算属性。
   * @return
   *     如果指定的类具有在指定对象图路径中指定的属性，则返回{@code true}；否则返回{@code false}。
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
   * 获取由对象图路径指定的指定对象的属性值。
   *
   * @param obj
   *     指定的对象。
   * @param path
   *     对象图中的指定路径。该路径<b>可以</b>包含计算属性。
   * @return
   *     由对象图路径指定的指定对象的属性值，如果路径中的任何对象为{@code null}，则返回{@code null}。
   * @throws FieldNotExistException
   *     如果对象图中不存在这样的路径。
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
   * 获取由对象图路径指定的指定类的属性的类型。
   *
   * @param cls
   *     指定的类。
   * @param path
   *     对象图中的指定路径。该路径<b>可以</b>包含计算属性。
   * @return
   *     由对象图路径指定的指定类的属性的类型。
   * @throws FieldNotExistException
   *     如果对象图中不存在这样的路径。
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
   * 设置由对象图路径指定的指定对象的属性值。
   * <p>
   * 如果中间对象为{@code null}，此方法不会创建中间对象。
   * 相反，如果路径中的任何中间对象为{@code null}，它将抛出{@link NullPointerException}。
   *
   * @param obj
   *     指定的对象。
   * @param path
   *     对象图中的指定路径。该路径<b>不得</b>包含计算属性。
   * @param value
   *     要设置的属性值。
   * @throws FieldNotExistException
   *     如果对象图中不存在这样的路径。
   * @throws NullPointerException
   *     如果路径中的任何中间对象为{@code null}。
   * @see #setPropertyValue(Object, String, Object, boolean)
   */
  public static void setPropertyValue(final Object obj, final String path,
      @Nullable final Object value) {
    setPropertyValue(obj, path, value, false);
  }

  /**
   * 设置由对象图路径指定的指定对象的属性值。
   * <p>
   * 如果参数{@code createIntermediate}为{@code true}，此方法将在中间对象为{@code null}时创建它们。
   * 否则，如果路径中的任何中间对象为{@code null}，它将抛出{@link NullPointerException}。
   *
   * @param obj
   *     指定的对象。
   * @param path
   *     对象图中的指定路径。该路径<b>不得</b>包含计算属性。
   * @param value
   *     要设置的属性值。
   * @param createIntermediate
   *     是否在中间对象为{@code null}时创建它们。
   * @throws FieldNotExistException
   *     如果对象图中不存在这样的路径。
   * @throws NullPointerException
   *     如果{@code createIntermediate}参数为{@code false}且路径中的任何中间对象为{@code null}。
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