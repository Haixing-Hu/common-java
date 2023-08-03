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

public class AnnotationUtils {

  /**
   * Gets the value of an attribute of an annotation.
   *
   * @param annotation
   *     the annotation object.
   * @param name
   *     the name of the attribute.
   * @return
   *     the value of the attribute.
   */
  @SuppressWarnings("unchecked")
  public static <T> T getAttribute(final Annotation annotation, final String name) {
    try {
      return (T) annotation.annotationType().getMethod(name).invoke(annotation);
    } catch (final Exception e) {
      throw new RuntimeException(e);
    }
  }
}
