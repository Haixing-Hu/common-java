////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ltd.qubit.commons.reflect.impl.GetterMethod;

import static ltd.qubit.commons.reflect.ObjectGraphUtils.getPropertyPath;
import static ltd.qubit.commons.util.ComparisonOperator.EQUAL;
import static ltd.qubit.commons.util.ComparisonOperator.GREATER;
import static ltd.qubit.commons.util.ComparisonOperator.GREATER_EQUAL;
import static ltd.qubit.commons.util.ComparisonOperator.IN;
import static ltd.qubit.commons.util.ComparisonOperator.LESS;
import static ltd.qubit.commons.util.ComparisonOperator.LESS_EQUAL;
import static ltd.qubit.commons.util.ComparisonOperator.LIKE;
import static ltd.qubit.commons.util.ComparisonOperator.NOT_EQUAL;
import static ltd.qubit.commons.util.ComparisonOperator.NOT_IN;
import static ltd.qubit.commons.util.ComparisonOperator.NOT_LIKE;
import static ltd.qubit.commons.util.LogicRelation.AND;
import static ltd.qubit.commons.util.LogicRelation.NOT;
import static ltd.qubit.commons.util.LogicRelation.OR;

/**
 * 提供工具函数辅助构造{@link Criterion}对象。
 *
 * @author 胡海星
 */
public class CriterionBuilder {

  public static <T, R> SimpleCriterion<T> isNull(final Class<T> type,
      final GetterMethod<T, R> getter) {
    return new SimpleCriterion<>(type, getPropertyPath(type, getter), EQUAL, null);
  }

  public static <T, P, R> SimpleCriterion<T> isNull(final Class<T> type,
      final GetterMethod<T, P> g1, final GetterMethod<P, R> g2) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2), EQUAL, null);
  }

  public static <T, R> SimpleCriterion<T> isNotNull(final Class<T> type,
      final GetterMethod<T, R> getter) {
    return new SimpleCriterion<>(type, getPropertyPath(type, getter), NOT_EQUAL, null);
  }

  public static <T, P, R> SimpleCriterion<T> isNotNull(final Class<T> type,
      final GetterMethod<T, P> g1, final GetterMethod<P, R> g2) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2), NOT_EQUAL, null);
  }

  public static <T, R> SimpleCriterion<T> equal(final Class<T> type,
      final GetterMethod<T, R> getter, final Object value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, getter), EQUAL, value);
  }

  public static <T, P, R> SimpleCriterion<T> equal(final Class<T> type,
      final GetterMethod<T, P> g1, final GetterMethod<P, R> g2, final Object value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2), EQUAL, value);
  }

  public static <T, P1, P2, R> SimpleCriterion<T> equal(final Class<T> type,
      final GetterMethod<T, P1> g1, final GetterMethod<P1, P2> g2,
      final GetterMethod<P2, R> g3, final Object value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2, g3), EQUAL, value);
  }

  public static <T, R> SimpleCriterion<T> notEqual(final Class<T> type,
      final GetterMethod<T, R> getter, final Object value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, getter), NOT_EQUAL, value);
  }

  public static <T, P, R> SimpleCriterion<T> notEqual(final Class<T> type,
      final GetterMethod<T, P> g1, final GetterMethod<P, R> g2, final Object value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2), NOT_EQUAL, value);
  }

  public static <T, P1, P2, R> SimpleCriterion<T> notEqual(final Class<T> type,
      final GetterMethod<T, P1> g1, final GetterMethod<P1, P2> g2,
      final GetterMethod<P2, R> g3, final Object value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2, g3), NOT_EQUAL, value);
  }

  public static <T, R> SimpleCriterion<T> less(final Class<T> type,
      final GetterMethod<T, R> getter, final Object value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, getter), LESS, value);
  }

  public static <T, P, R> SimpleCriterion<T> less(final Class<T> type,
      final GetterMethod<T, P> g1, final GetterMethod<P, R> g2, final Object value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2), LESS, value);
  }

  public static <T, P1, P2, R> SimpleCriterion<T> less(final Class<T> type,
      final GetterMethod<T, P1> g1, final GetterMethod<P1, P2> g2,
      final GetterMethod<P2, R> g3, final Object value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2, g3), LESS, value);
  }

  public static <T, R> SimpleCriterion<T> lessEqual(final Class<T> type,
      final GetterMethod<T, R> getter, final Object value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, getter), LESS_EQUAL, value);
  }

  public static <T, P, R> SimpleCriterion<T> lessEqual(final Class<T> type,
      final GetterMethod<T, P> g1, final GetterMethod<P, R> g2, final Object value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2), LESS_EQUAL, value);
  }

  public static <T, P1, P2, R> SimpleCriterion<T> lessEqual(final Class<T> type,
      final GetterMethod<T, P1> g1, final GetterMethod<P1, P2> g2,
      final GetterMethod<P2, R> g3, final Object value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2, g3), LESS_EQUAL, value);
  }

  public static <T, R> SimpleCriterion<T> greater(final Class<T> type,
      final GetterMethod<T, R> getter, final Object value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, getter), GREATER, value);
  }

  public static <T, P, R> SimpleCriterion<T> greater(final Class<T> type,
      final GetterMethod<T, P> g1, final GetterMethod<P, R> g2, final Object value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2), GREATER, value);
  }

  public static <T, P1, P2, R> SimpleCriterion<T> greater(final Class<T> type,
      final GetterMethod<T, P1> g1, final GetterMethod<P1, P2> g2,
      final GetterMethod<P2, R> g3, final Object value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2, g3), GREATER, value);
  }

  public static <T, R> SimpleCriterion<T> greaterEqual(final Class<T> type,
      final GetterMethod<T, R> getter, final Object value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, getter), GREATER_EQUAL, value);
  }

  public static <T, P, R> SimpleCriterion<T> greaterEqual(final Class<T> type,
      final GetterMethod<T, P> g1, final GetterMethod<P, R> g2, final Object value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2), GREATER_EQUAL, value);
  }

  public static <T, P1, P2, R> SimpleCriterion<T> greaterEqual(final Class<T> type,
      final GetterMethod<T, P1> g1, final GetterMethod<P1, P2> g2,
      final GetterMethod<P2, R> g3, final Object value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2, g3), GREATER_EQUAL, value);
  }

  public static <T, R> SimpleCriterion<T> in(final Class<T> type,
      final GetterMethod<T, R> getter, final Object... values) {
    return new SimpleCriterion<>(type, getPropertyPath(type, getter), IN, Arrays.asList(values));
  }

  public static <T, P, R> SimpleCriterion<T> in(final Class<T> type,
      final GetterMethod<T, P> g1, final GetterMethod<P, R> g2, final Object... values) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2), IN, Arrays.asList(values));
  }

  public static <T, P1, P2, R> SimpleCriterion<T> in(final Class<T> type,
      final GetterMethod<T, P1> g1, final GetterMethod<P1, P2> g2,
      final GetterMethod<P2, R> g3, final Object... values) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2, g3), IN, Arrays.asList(values));
  }

  public static <T, R> SimpleCriterion<T> notIn(final Class<T> type,
      final GetterMethod<T, R> getter, final Object... values) {
    return new SimpleCriterion<>(type, getPropertyPath(type, getter), NOT_IN, Arrays.asList(values));
  }

  public static <T, P, R> SimpleCriterion<T> notIn(final Class<T> type,
      final GetterMethod<T, P> g1, final GetterMethod<P, R> g2, final Object... values) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2), NOT_IN, Arrays.asList(values));
  }

  public static <T, P1, P2, R> SimpleCriterion<T> notIn(final Class<T> type,
      final GetterMethod<T, P1> g1, final GetterMethod<P1, P2> g2,
      final GetterMethod<P2, R> g3, final Object... values) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2, g3), NOT_IN,
        Arrays.asList(values));
  }

  public static <T, R> SimpleCriterion<T> like(final Class<T> type,
      final GetterMethod<T, R> getter, final String value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, getter), LIKE, '%' + value + '%');
  }

  public static <T, P, R> SimpleCriterion<T> like(final Class<T> type,
      final GetterMethod<T, P> g1, final GetterMethod<P, R> g2, final String value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2), LIKE, '%' + value + '%');
  }

  public static <T, P1, P2, R> SimpleCriterion<T> like(final Class<T> type,
      final GetterMethod<T, P1> g1, final GetterMethod<P1, P2> g2,
      final GetterMethod<P2, R> g3, final String value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2, g3), LIKE, '%' + value + '%');
  }

  public static <T, R> SimpleCriterion<T> notLike(final Class<T> type,
      final GetterMethod<T, R> getter, final String value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, getter), NOT_LIKE, '%' + value + '%');
  }

  public static <T, P, R> SimpleCriterion<T> notLike(final Class<T> type,
      final GetterMethod<T, P> g1, final GetterMethod<P, R> g2, final String value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2), NOT_LIKE, '%' + value + '%');
  }

  public static <T, P1, P2, R> SimpleCriterion<T> notLike(final Class<T> type,
      final GetterMethod<T, P1> g1, final GetterMethod<P1, P2> g2,
      final GetterMethod<P2, R> g3, final String value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2, g3), NOT_LIKE, '%' + value + '%');
  }

  public static <T> Criterion<T> allEqual(final Class<T> type,
      final Map<String, Object> params) {
    final List<SimpleCriterion<T>> criteria = new ArrayList<>();
    for (final Map.Entry<String, Object> entry : params.entrySet()) {
      criteria.add(new SimpleCriterion<>(type, entry.getKey(), EQUAL, entry.getValue()));
    }
    return new ComposedCriterion<>(type, AND, criteria);
  }

  public static <T> Criterion<T> anyEqual(final Class<T> type,
      final Map<String, Object> params) {
    final List<SimpleCriterion<T>> criteria = new ArrayList<>();
    for (final Map.Entry<String, Object> entry : params.entrySet()) {
      criteria.add(new SimpleCriterion<>(type, entry.getKey(), EQUAL, entry.getValue()));
    }
    return new ComposedCriterion<>(type, OR, criteria);
  }

  @SuppressWarnings("varargs")
  @SafeVarargs
  public static <T> ComposedCriterion<T> and(final Class<T> type,
      final SimpleCriterion<T>... criteria) {
    return new ComposedCriterion<>(type, AND, criteria);
  }

  @SuppressWarnings("varargs")
  @SafeVarargs
  public static <T> ComposedCriterion<T> or(final Class<T> type,
      final SimpleCriterion<T>... criteria) {
    return new ComposedCriterion<>(type, OR, criteria);
  }

  public static <T> ComposedCriterion<T> not(final Class<T> type,
      final SimpleCriterion<T> criterion) {
    return new ComposedCriterion<>(type, NOT, criterion);
  }
}