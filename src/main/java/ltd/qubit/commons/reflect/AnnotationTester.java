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

/**
 * The functional interface to check the existence of an annotation.
 *
 * @author Haixing Hu
 */
@FunctionalInterface
public interface AnnotationTester {

  /**
   * Tests whether the specified annotation exists.
   *
   * @param annotationClass
   *     the class of the specified annotation.
   * @return
   *     {@code true} if the specified annotation exists; {@code false} otherwise.
   */
  boolean exist(Class<? extends Annotation> annotationClass);
}
