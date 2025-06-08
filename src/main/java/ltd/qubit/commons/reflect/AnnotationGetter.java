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

import javax.annotation.Nullable;

/**
 * The functional interface to get an annotation.
 *
 * @author Haixing Hu
 */
@FunctionalInterface
public interface AnnotationGetter {

  /**
   * Get the specified annotation.
   *
   * @param annotationClass
   *     the class of the specified annotation.
   * @return
   *     the annotation object of the specified class, or {@code null} if it does
   *     not exist.
   */
  @Nullable
  Annotation get(Class<? extends Annotation> annotationClass);
}