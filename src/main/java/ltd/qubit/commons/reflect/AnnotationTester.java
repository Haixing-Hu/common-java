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
 * 用于检查注解是否存在的函数式接口。
 *
 * @author 胡海星
 */
@FunctionalInterface
public interface AnnotationTester {

  /**
   * 测试指定的注解是否存在。
   *
   * @param annotationClass
   *     指定注解的类。
   * @return
   *     如果指定的注解存在则返回 {@code true}；否则返回 {@code false}。
   */
  boolean exist(Class<? extends Annotation> annotationClass);
}