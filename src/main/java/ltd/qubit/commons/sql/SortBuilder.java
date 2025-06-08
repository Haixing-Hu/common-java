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

  public static <T, R> SortRequest<T> asc(final Class<T> type,
      final GetterMethod<T, R> getter) {
    return new SortRequest<>(type, getPropertyPath(type, getter), ASC);
  }

  public static <T, P, R> SortRequest<T> asc(final Class<T> type,
      final GetterMethod<T, P> g1, final GetterMethod<P, R> g2) {
    return new SortRequest<>(type, getPropertyPath(type, g1, g2), ASC);
  }

  public static <T, P1, P2, R> SortRequest<T> asc(final Class<T> type,
      final GetterMethod<T, P1> g1, final GetterMethod<P1, P2> g2,
      final GetterMethod<P2, R> g3) {
    return new SortRequest<>(type, getPropertyPath(type, g1, g2, g3), ASC);
  }

  public static <T, R> SortRequest<T> desc(final Class<T> type,
      final GetterMethod<T, R> getter) {
    return new SortRequest<>(type, getPropertyPath(type, getter), DESC);
  }

  public static <T, P, R> SortRequest<T> desc(final Class<T> type,
      final GetterMethod<T, P> g1, final GetterMethod<P, R> g2) {
    return new SortRequest<>(type, getPropertyPath(type, g1, g2), DESC);
  }

  public static <T, P1, P2, R> SortRequest<T> desc(final Class<T> type,
      final GetterMethod<T, P1> g1, final GetterMethod<P1, P2> g2,
      final GetterMethod<P2, R> g3) {
    return new SortRequest<>(type, getPropertyPath(type, g1, g2, g3), DESC);
  }
}