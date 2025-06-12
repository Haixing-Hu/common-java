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

/**
 * 提供注解相关的实用功能。
 *
 * @author 胡海星
 */
public class AnnotationUtils {
  /**
   * 获取注解中指定属性的值。
   *
   * @param annotation
   *     注解对象。
   * @param name
   *     属性名称。
   * @return
   *     属性的值。
   */
  @SuppressWarnings("unchecked")
  public static <T> T getAttribute(final Annotation annotation, final String name) {
    try {
      return (T) annotation.annotationType().getMethod(name).invoke(annotation);
    } catch (final Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 获取注解中指定属性的值。
   *
   * @param annotation
   *     注解对象。
   * @param name
   *     属性名称。
   * @return
   *     属性的值，如果属性不存在则返回 {@code null}。
   */
  @SuppressWarnings("unchecked")
  public static <T> T getAttributeOrNull(final Annotation annotation, final String name) {
    try {
      return (T) annotation.annotationType().getMethod(name).invoke(annotation);
    } catch (final Exception e) {
      return null;
    }
  }
}