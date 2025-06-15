////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql;

import ltd.qubit.commons.reflect.impl.GetterMethod;

import static ltd.qubit.commons.reflect.ObjectGraphUtils.getPropertyPath;
import static ltd.qubit.commons.sql.SortOrder.ASC;
import static ltd.qubit.commons.sql.SortOrder.DESC;

/**
 * 提供工具函数辅助构造{@link SortRequest}对象。
 *
 * @author 胡海星
 */
public class SortBuilder {

  /**
   * 创建一个升序排序请求。
   *
   * @param <T>
   *     实体类型。
   * @param <R>
   *     属性类型。
   * @param type
   *     实体类。
   * @param getter
   *     属性的getter方法。
   * @return
   *     升序排序请求。
   */
  public static <T, R> SortRequest<T> asc(final Class<T> type,
      final GetterMethod<T, R> getter) {
    return new SortRequest<>(type, getPropertyPath(type, getter), ASC);
  }

  /**
   * 创建一个升序排序请求（两级属性路径）。
   *
   * @param <T>
   *     实体类型。
   * @param <P>
   *     第一级属性类型。
   * @param <R>
   *     第二级属性类型。
   * @param type
   *     实体类。
   * @param g1
   *     第一级属性的getter方法。
   * @param g2
   *     第二级属性的getter方法。
   * @return
   *     升序排序请求。
   */
  public static <T, P, R> SortRequest<T> asc(final Class<T> type,
      final GetterMethod<T, P> g1, final GetterMethod<P, R> g2) {
    return new SortRequest<>(type, getPropertyPath(type, g1, g2), ASC);
  }

  /**
   * 创建一个升序排序请求（三级属性路径）。
   *
   * @param <T>
   *     实体类型。
   * @param <P1>
   *     第一级属性类型。
   * @param <P2>
   *     第二级属性类型。
   * @param <R>
   *     第三级属性类型。
   * @param type
   *     实体类。
   * @param g1
   *     第一级属性的getter方法。
   * @param g2
   *     第二级属性的getter方法。
   * @param g3
   *     第三级属性的getter方法。
   * @return
   *     升序排序请求。
   */
  public static <T, P1, P2, R> SortRequest<T> asc(final Class<T> type,
      final GetterMethod<T, P1> g1, final GetterMethod<P1, P2> g2,
      final GetterMethod<P2, R> g3) {
    return new SortRequest<>(type, getPropertyPath(type, g1, g2, g3), ASC);
  }

  /**
   * 创建一个降序排序请求。
   *
   * @param <T>
   *     实体类型。
   * @param <R>
   *     属性类型。
   * @param type
   *     实体类。
   * @param getter
   *     属性的getter方法。
   * @return
   *     降序排序请求。
   */
  public static <T, R> SortRequest<T> desc(final Class<T> type,
      final GetterMethod<T, R> getter) {
    return new SortRequest<>(type, getPropertyPath(type, getter), DESC);
  }

  /**
   * 创建一个降序排序请求（两级属性路径）。
   *
   * @param <T>
   *     实体类型。
   * @param <P>
   *     第一级属性类型。
   * @param <R>
   *     第二级属性类型。
   * @param type
   *     实体类。
   * @param g1
   *     第一级属性的getter方法。
   * @param g2
   *     第二级属性的getter方法。
   * @return
   *     降序排序请求。
   */
  public static <T, P, R> SortRequest<T> desc(final Class<T> type,
      final GetterMethod<T, P> g1, final GetterMethod<P, R> g2) {
    return new SortRequest<>(type, getPropertyPath(type, g1, g2), DESC);
  }

  /**
   * 创建一个降序排序请求（三级属性路径）。
   *
   * @param <T>
   *     实体类型。
   * @param <P1>
   *     第一级属性类型。
   * @param <P2>
   *     第二级属性类型。
   * @param <R>
   *     第三级属性类型。
   * @param type
   *     实体类。
   * @param g1
   *     第一级属性的getter方法。
   * @param g2
   *     第二级属性的getter方法。
   * @param g3
   *     第三级属性的getter方法。
   * @return
   *     降序排序请求。
   */
  public static <T, P1, P2, R> SortRequest<T> desc(final Class<T> type,
      final GetterMethod<T, P1> g1, final GetterMethod<P1, P2> g2,
      final GetterMethod<P2, R> g3) {
    return new SortRequest<>(type, getPropertyPath(type, g1, g2, g3), DESC);
  }
}