////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import java.lang.reflect.Field;
import javax.annotation.Nullable;

import static ltd.qubit.commons.lang.StringUtils.isEmpty;
import static ltd.qubit.commons.reflect.FieldUtils.getField;
import static ltd.qubit.commons.reflect.FieldUtils.readField;
import static ltd.qubit.commons.reflect.FieldUtils.writeField;
import static ltd.qubit.commons.reflect.Option.BEAN_FIELD;

/**
 * Provides functions to handle object graphs.
 *
 * @author Haixing Hu
 */
public class ObjectGraphUtils {

  /**
   * Tests whether the specified class has the property specified in a object
   * graph path.
   *
   * @param cls
   *     the specified class.
   * @param path
   *     the specified path in the object graph.
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
      final Field field = getField(c, BEAN_FIELD, name);
      if (field == null) {
        return false;
      }
      c = field.getType();
      p = p.substring(index + 1);
      index = p.indexOf('.');
    }
    return FieldUtils.hasField(c, BEAN_FIELD, p);
  }

  /**
   * Gets the value of a property of the specified object specified by an object
   * graph path.
   *
   * @param obj
   *     the specified object.
   * @param path
   *     the specified path in the object graph.
   * @return
   *     the value of a property of the specified object specified by the object
   *     graph path.
   * @throws FieldNotExistException
   *     if there is no such path exist in the object graph.
   */
  public static Object getPropertyValue(final Object obj, final String path) {
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
      final Field field = getField(currentClass, BEAN_FIELD, fieldName);
      if (field == null) {
        throw new FieldNotExistException(originalClass, BEAN_FIELD,
            pathBuilder.toString());
      }
      final Object fieldValue = readField(field, currentObject);
      if (fieldValue == null) {
        return null;
      }
      currentClass = field.getType();
      currentObject = fieldValue;
      currentPath = currentPath.substring(index + 1);
      index = currentPath.indexOf('.');
    }
    final Field field = getField(currentClass, BEAN_FIELD, currentPath);
    if (field == null) {
      throw new FieldNotExistException(originalClass, BEAN_FIELD, path);
    }
    return readField(field, currentObject);
  }

  /**
   * Gets the type of the property of the specified class specified by an object
   * graph path.
   *
   * @param cls
   *     the specified class.
   * @param path
   *     the specified path in the object graph.
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
      final Field field = getField(currentClass, BEAN_FIELD, fieldName);
      if (field == null) {
        throw new FieldNotExistException(cls, BEAN_FIELD, pathBuilder.toString());
      }
      currentClass = field.getType();
      currentPath = currentPath.substring(index + 1);
      index = currentPath.indexOf('.');
    }
    final Field field = getField(currentClass, BEAN_FIELD, currentPath);
    if (field == null) {
      throw new FieldNotExistException(cls, BEAN_FIELD, path);
    }
    return field.getType();
  }

  /**
   * Sets the value of a property of the specified object specified by an object
   * graph path.
   *
   * @param obj
   *     the specified object.
   * @param path
   *     the specified path in the object graph.
   * @param value
   *     the value of a property to be set.
   * @throws FieldNotExistException
   *     if there is no such path exist in the object graph.
   */
  public static void setPropertyValue(final Object obj, final String path,
      @Nullable final Object value) {
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
      final Field field = getField(currentClass, BEAN_FIELD, fieldName);
      if (field == null) {
        throw new FieldNotExistException(originalClass, BEAN_FIELD,
            pathBuilder.toString());
      }
      final Object fieldValue = readField(field, currentObject);
      if (fieldValue == null) {
        throw new NullPointerException("The " + pathBuilder
            + " of the specified object is null.");
      }
      currentClass = field.getType();
      currentObject = fieldValue;
      currentPath = currentPath.substring(index + 1);
      index = currentPath.indexOf('.');
    }
    final Field field = getField(currentClass, BEAN_FIELD, currentPath);
    if (field == null) {
      throw new FieldNotExistException(originalClass, BEAN_FIELD, path);
    }
    writeField(field, currentObject, value);
  }
}
