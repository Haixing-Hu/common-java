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

  /**
   * 构造一个判断指定属性是否为null的条件。
   *
   * @param type
   *     实体类的类型。
   * @param getter
   *     用于访问属性的getter方法引用。
   * @return 一个判断属性是否为null的条件对象。
   */
  public static <T, R> SimpleCriterion<T> isNull(final Class<T> type,
      final GetterMethod<T, R> getter) {
    return new SimpleCriterion<>(type, getPropertyPath(type, getter), EQUAL, null);
  }

  /**
   * 构造一个判断通过两个getter方法访问的嵌套属性是否为null的条件。
   *
   * @param type
   *     实体类的类型。
   * @param g1
   *     第一级getter方法。
   * @param g2
   *     第二级getter方法。
   * @return 一个判断嵌套属性是否为null的条件对象。
   */
  public static <T, P, R> SimpleCriterion<T> isNull(final Class<T> type,
      final GetterMethod<T, P> g1, final GetterMethod<P, R> g2) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2), EQUAL, null);
  }

  /**
   * 构造一个判断指定属性是否不为null的条件。
   *
   * @param type
   *     实体类的类型。
   * @param getter
   *     用于访问属性的getter方法引用。
   * @return 一个判断属性是否不为null的条件对象。
   */
  public static <T, R> SimpleCriterion<T> isNotNull(final Class<T> type,
      final GetterMethod<T, R> getter) {
    return new SimpleCriterion<>(type, getPropertyPath(type, getter), NOT_EQUAL, null);
  }

  /**
   * 构造一个判断通过两个getter方法访问的嵌套属性是否不为null的条件。
   *
   * @param type
   *     实体类的类型。
   * @param g1
   *     第一级getter方法。
   * @param g2
   *     第二级getter方法。
   * @return 一个判断嵌套属性是否不为null的条件对象。
   */
  public static <T, P, R> SimpleCriterion<T> isNotNull(final Class<T> type,
      final GetterMethod<T, P> g1, final GetterMethod<P, R> g2) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2), NOT_EQUAL, null);
  }

  /**
   * 构造一个判断指定属性是否等于给定值的条件。
   *
   * @param type
   *     实体类的类型。
   * @param getter
   *     用于访问属性的getter方法引用。
   * @param value
   *     要比较的值。
   * @return 一个判断属性等于给定值的条件对象。
   */
  public static <T, R> SimpleCriterion<T> equal(final Class<T> type,
      final GetterMethod<T, R> getter, final Object value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, getter), EQUAL, value);
  }

  /**
   * 构造一个判断通过两个getter方法访问的嵌套属性是否等于给定值的条件。
   *
   * @param type
   *     实体类的类型。
   * @param g1
   *     第一级getter方法。
   * @param g2
   *     第二级getter方法。
   * @param value
   *     要比较的值。
   * @return 一个判断嵌套属性等于给定值的条件对象。
   */
  public static <T, P, R> SimpleCriterion<T> equal(final Class<T> type,
      final GetterMethod<T, P> g1, final GetterMethod<P, R> g2, final Object value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2), EQUAL, value);
  }

  /**
   * 构造一个判断通过三个getter方法访问的嵌套属性是否等于给定值的条件。
   *
   * @param type
   *     实体类的类型。
   * @param g1
   *     第一级getter方法。
   * @param g2
   *     第二级getter方法。
   * @param g3
   *     第三级getter方法。
   * @param value
   *     要比较的值。
   * @return 一个判断嵌套属性等于给定值的条件对象。
   */
  public static <T, P1, P2, R> SimpleCriterion<T> equal(final Class<T> type,
      final GetterMethod<T, P1> g1, final GetterMethod<P1, P2> g2,
      final GetterMethod<P2, R> g3, final Object value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2, g3), EQUAL, value);
  }

  /**
   * 构造一个判断指定属性是否不等于给定值的条件。
   *
   * @param type
   *     实体类的类型。
   * @param getter
   *     用于访问属性的getter方法引用。
   * @param value
   *     要比较的值。
   * @return 一个判断属性不等于给定值的条件对象。
   */
  public static <T, R> SimpleCriterion<T> notEqual(final Class<T> type,
      final GetterMethod<T, R> getter, final Object value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, getter), NOT_EQUAL, value);
  }

  /**
   * 构造一个判断通过两个getter方法访问的嵌套属性是否不等于给定值的条件。
   *
   * @param type
   *     实体类的类型。
   * @param g1
   *     第一级getter方法。
   * @param g2
   *     第二级getter方法。
   * @param value
   *     要比较的值。
   * @return 一个判断嵌套属性不等于给定值的条件对象。
   */
  public static <T, P, R> SimpleCriterion<T> notEqual(final Class<T> type,
      final GetterMethod<T, P> g1, final GetterMethod<P, R> g2, final Object value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2), NOT_EQUAL, value);
  }

  /**
   * 构造一个判断通过三个getter方法访问的嵌套属性是否不等于给定值的条件。
   *
   * @param type
   *     实体类的类型。
   * @param g1
   *     第一级getter方法。
   * @param g2
   *     第二级getter方法。
   * @param g3
   *     第三级getter方法。
   * @param value
   *     要比较的值。
   * @return 一个判断嵌套属性不等于给定值的条件对象。
   */
  public static <T, P1, P2, R> SimpleCriterion<T> notEqual(final Class<T> type,
      final GetterMethod<T, P1> g1, final GetterMethod<P1, P2> g2,
      final GetterMethod<P2, R> g3, final Object value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2, g3), NOT_EQUAL, value);
  }

  /**
   * 构造一个判断指定属性是否小于给定值的条件。
   *
   * @param type
   *     实体类的类型。
   * @param getter
   *     用于访问属性的getter方法引用。
   * @param value
   *     要比较的值。
   * @return 一个判断属性小于给定值的条件对象。
   */
  public static <T, R> SimpleCriterion<T> less(final Class<T> type,
      final GetterMethod<T, R> getter, final Object value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, getter), LESS, value);
  }

  /**
   * 构造一个判断通过两个getter方法访问的嵌套属性是否小于给定值的条件。
   *
   * @param type
   *     实体类的类型。
   * @param g1
   *     第一级getter方法。
   * @param g2
   *     第二级getter方法。
   * @param value
   *     要比较的值。
   * @return 一个判断嵌套属性小于给定值的条件对象。
   */
  public static <T, P, R> SimpleCriterion<T> less(final Class<T> type,
      final GetterMethod<T, P> g1, final GetterMethod<P, R> g2, final Object value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2), LESS, value);
  }

  /**
   * 构造一个判断通过三个getter方法访问的嵌套属性是否小于给定值的条件。
   *
   * @param type
   *     实体类的类型。
   * @param g1
   *     第一级getter方法。
   * @param g2
   *     第二级getter方法。
   * @param g3
   *     第三级getter方法。
   * @param value
   *     要比较的值。
   * @return 一个判断嵌套属性小于给定值的条件对象。
   */
  public static <T, P1, P2, R> SimpleCriterion<T> less(final Class<T> type,
      final GetterMethod<T, P1> g1, final GetterMethod<P1, P2> g2,
      final GetterMethod<P2, R> g3, final Object value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2, g3), LESS, value);
  }

  /**
   * 构造一个判断指定属性是否小于等于给定值的条件。
   *
   * @param type
   *     实体类的类型。
   * @param getter
   *     用于访问属性的getter方法引用。
   * @param value
   *     要比较的值。
   * @return 一个判断属性小于等于给定值的条件对象。
   */
  public static <T, R> SimpleCriterion<T> lessEqual(final Class<T> type,
      final GetterMethod<T, R> getter, final Object value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, getter), LESS_EQUAL, value);
  }

  /**
   * 构造一个判断通过两个getter方法访问的嵌套属性是否小于等于给定值的条件。
   *
   * @param type
   *     实体类的类型。
   * @param g1
   *     第一级getter方法。
   * @param g2
   *     第二级getter方法。
   * @param value
   *     要比较的值。
   * @return 一个判断嵌套属性小于等于给定值的条件对象。
   */
  public static <T, P, R> SimpleCriterion<T> lessEqual(final Class<T> type,
      final GetterMethod<T, P> g1, final GetterMethod<P, R> g2, final Object value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2), LESS_EQUAL, value);
  }

  /**
   * 构造一个判断通过三个getter方法访问的嵌套属性是否小于等于给定值的条件。
   *
   * @param type
   *     实体类的类型。
   * @param g1
   *     第一级getter方法。
   * @param g2
   *     第二级getter方法。
   * @param g3
   *     第三级getter方法。
   * @param value
   *     要比较的值。
   * @return 一个判断嵌套属性小于等于给定值的条件对象。
   */
  public static <T, P1, P2, R> SimpleCriterion<T> lessEqual(final Class<T> type,
      final GetterMethod<T, P1> g1, final GetterMethod<P1, P2> g2,
      final GetterMethod<P2, R> g3, final Object value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2, g3), LESS_EQUAL, value);
  }

  /**
   * 构造一个判断指定属性是否大于给定值的条件。
   *
   * @param type
   *     实体类的类型。
   * @param getter
   *     用于访问属性的getter方法引用。
   * @param value
   *     要比较的值。
   * @return 一个判断属性大于给定值的条件对象。
   */
  public static <T, R> SimpleCriterion<T> greater(final Class<T> type,
      final GetterMethod<T, R> getter, final Object value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, getter), GREATER, value);
  }

  /**
   * 构造一个判断通过两个getter方法访问的嵌套属性是否大于给定值的条件。
   *
   * @param type
   *     实体类的类型。
   * @param g1
   *     第一级getter方法。
   * @param g2
   *     第二级getter方法。
   * @param value
   *     要比较的值。
   * @return 一个判断嵌套属性大于给定值的条件对象。
   */
  public static <T, P, R> SimpleCriterion<T> greater(final Class<T> type,
      final GetterMethod<T, P> g1, final GetterMethod<P, R> g2, final Object value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2), GREATER, value);
  }

  /**
   * 构造一个判断通过三个getter方法访问的嵌套属性是否大于给定值的条件。
   *
   * @param type
   *     实体类的类型。
   * @param g1
   *     第一级getter方法。
   * @param g2
   *     第二级getter方法。
   * @param g3
   *     第三级getter方法。
   * @param value
   *     要比较的值。
   * @return 一个判断嵌套属性大于给定值的条件对象。
   */
  public static <T, P1, P2, R> SimpleCriterion<T> greater(final Class<T> type,
      final GetterMethod<T, P1> g1, final GetterMethod<P1, P2> g2,
      final GetterMethod<P2, R> g3, final Object value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2, g3), GREATER, value);
  }

  /**
   * 构造一个判断指定属性是否大于等于给定值的条件。
   *
   * @param type
   *     实体类的类型。
   * @param getter
   *     用于访问属性的getter方法引用。
   * @param value
   *     要比较的值。
   * @return 一个判断属性大于等于给定值的条件对象。
   */
  public static <T, R> SimpleCriterion<T> greaterEqual(final Class<T> type,
      final GetterMethod<T, R> getter, final Object value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, getter), GREATER_EQUAL, value);
  }

  /**
   * 构造一个判断通过两个getter方法访问的嵌套属性是否大于等于给定值的条件。
   *
   * @param type
   *     实体类的类型。
   * @param g1
   *     第一级getter方法。
   * @param g2
   *     第二级getter方法。
   * @param value
   *     要比较的值。
   * @return 一个判断嵌套属性大于等于给定值的条件对象。
   */
  public static <T, P, R> SimpleCriterion<T> greaterEqual(final Class<T> type,
      final GetterMethod<T, P> g1, final GetterMethod<P, R> g2, final Object value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2), GREATER_EQUAL, value);
  }

  /**
   * 构造一个判断通过三个getter方法访问的嵌套属性是否大于等于给定值的条件。
   *
   * @param type
   *     实体类的类型。
   * @param g1
   *     第一级getter方法。
   * @param g2
   *     第二级getter方法。
   * @param g3
   *     第三级getter方法。
   * @param value
   *     要比较的值。
   * @return 一个判断嵌套属性大于等于给定值的条件对象。
   */
  public static <T, P1, P2, R> SimpleCriterion<T> greaterEqual(final Class<T> type,
      final GetterMethod<T, P1> g1, final GetterMethod<P1, P2> g2,
      final GetterMethod<P2, R> g3, final Object value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2, g3), GREATER_EQUAL, value);
  }

  /**
   * 构造一个判断指定属性值是否在给定值集合中的条件。
   *
   * @param type
   *     实体类的类型。
   * @param getter
   *     用于访问属性的getter方法引用。
   * @param values
   *     要比较的值集合。
   * @return 一个判断属性值在集合中的条件对象。
   */
  public static <T, R> SimpleCriterion<T> in(final Class<T> type,
      final GetterMethod<T, R> getter, final Object... values) {
    return new SimpleCriterion<>(type, getPropertyPath(type, getter), IN, Arrays.asList(values));
  }

  /**
   * 构造一个判断通过两个getter方法访问的嵌套属性值是否在给定值集合中的条件。
   *
   * @param type
   *     实体类的类型。
   * @param g1
   *     第一级getter方法。
   * @param g2
   *     第二级getter方法。
   * @param values
   *     要比较的值集合。
   * @return 一个判断嵌套属性值在集合中的条件对象。
   */
  public static <T, P, R> SimpleCriterion<T> in(final Class<T> type,
      final GetterMethod<T, P> g1, final GetterMethod<P, R> g2, final Object... values) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2), IN, Arrays.asList(values));
  }

  /**
   * 构造一个判断通过三个getter方法访问的嵌套属性值是否在给定值集合中的条件。
   *
   * @param type
   *     实体类的类型。
   * @param g1
   *     第一级getter方法。
   * @param g2
   *     第二级getter方法。
   * @param g3
   *     第三级getter方法。
   * @param values
   *     要比较的值集合。
   * @return 一个判断嵌套属性值在集合中的条件对象。
   */
  public static <T, P1, P2, R> SimpleCriterion<T> in(final Class<T> type,
      final GetterMethod<T, P1> g1, final GetterMethod<P1, P2> g2,
      final GetterMethod<P2, R> g3, final Object... values) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2, g3), IN, Arrays.asList(values));
  }

  /**
   * 构造一个判断指定属性值是否不在给定值集合中的条件。
   *
   * @param type
   *     实体类的类型。
   * @param getter
   *     用于访问属性的getter方法引用。
   * @param values
   *     要比较的值集合。
   * @return 一个判断属性值不在集合中的条件对象。
   */
  public static <T, R> SimpleCriterion<T> notIn(final Class<T> type,
      final GetterMethod<T, R> getter, final Object... values) {
    return new SimpleCriterion<>(type, getPropertyPath(type, getter), NOT_IN, Arrays.asList(values));
  }

  /**
   * 构造一个判断通过两个getter方法访问的嵌套属性值是否不在给定值集合中的条件。
   *
   * @param type
   *     实体类的类型。
   * @param g1
   *     第一级getter方法。
   * @param g2
   *     第二级getter方法。
   * @param values
   *     要比较的值集合。
   * @return 一个判断嵌套属性值不在集合中的条件对象。
   */
  public static <T, P, R> SimpleCriterion<T> notIn(final Class<T> type,
      final GetterMethod<T, P> g1, final GetterMethod<P, R> g2, final Object... values) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2), NOT_IN, Arrays.asList(values));
  }

  /**
   * 构造一个判断通过三个getter方法访问的嵌套属性值是否不在给定值集合中的条件。
   *
   * @param type
   *     实体类的类型。
   * @param g1
   *     第一级getter方法。
   * @param g2
   *     第二级getter方法。
   * @param g3
   *     第三级getter方法。
   * @param values
   *     要比较的值集合。
   * @return 一个判断嵌套属性值不在集合中的条件对象。
   */
  public static <T, P1, P2, R> SimpleCriterion<T> notIn(final Class<T> type,
      final GetterMethod<T, P1> g1, final GetterMethod<P1, P2> g2,
      final GetterMethod<P2, R> g3, final Object... values) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2, g3), NOT_IN,
        Arrays.asList(values));
  }

  /**
   * 构造一个判断指定字符串属性是否包含给定子串的条件（SQL LIKE操作，自动加%通配符）。
   *
   * @param type
   *     实体类的类型。
   * @param getter
   *     用于访问属性的getter方法引用。
   * @param value
   *     要匹配的子串。
   * @return 一个判断属性LIKE条件的条件对象。
   */
  public static <T, R> SimpleCriterion<T> like(final Class<T> type,
      final GetterMethod<T, R> getter, final String value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, getter), LIKE, '%' + value + '%');
  }

  /**
   * 构造一个判断通过两个getter方法访问的嵌套字符串属性是否包含给定子串的条件（SQL LIKE操作，自动加%通配符）。
   *
   * @param type
   *     实体类的类型。
   * @param g1
   *     第一级getter方法。
   * @param g2
   *     第二级getter方法。
   * @param value
   *     要匹配的子串。
   * @return 一个判断嵌套属性LIKE条件的条件对象。
   */
  public static <T, P, R> SimpleCriterion<T> like(final Class<T> type,
      final GetterMethod<T, P> g1, final GetterMethod<P, R> g2, final String value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2), LIKE, '%' + value + '%');
  }

  /**
   * 构造一个判断通过三个getter方法访问的嵌套字符串属性是否包含给定子串的条件（SQL LIKE操作，自动加%通配符）。
   *
   * @param type
   *     实体类的类型。
   * @param g1
   *     第一级getter方法。
   * @param g2
   *     第二级getter方法。
   * @param g3
   *     第三级getter方法。
   * @param value
   *     要匹配的子串。
   * @return 一个判断嵌套属性LIKE条件的条件对象。
   */
  public static <T, P1, P2, R> SimpleCriterion<T> like(final Class<T> type,
      final GetterMethod<T, P1> g1, final GetterMethod<P1, P2> g2,
      final GetterMethod<P2, R> g3, final String value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2, g3), LIKE, '%' + value + '%');
  }

  /**
   * 构造一个判断指定字符串属性是否不包含给定子串的条件（SQL NOT LIKE操作，自动加%通配符）。
   *
   * @param type
   *     实体类的类型。
   * @param getter
   *     用于访问属性的getter方法引用。
   * @param value
   *     要匹配的子串。
   * @return 一个判断属性NOT LIKE条件的条件对象。
   */
  public static <T, R> SimpleCriterion<T> notLike(final Class<T> type,
      final GetterMethod<T, R> getter, final String value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, getter), NOT_LIKE, '%' + value + '%');
  }

  /**
   * 构造一个判断通过两个getter方法访问的嵌套字符串属性是否不包含给定子串的条件（SQL NOT LIKE操作，自动加%通配符）。
   *
   * @param type
   *     实体类的类型。
   * @param g1
   *     第一级getter方法。
   * @param g2
   *     第二级getter方法。
   * @param value
   *     要匹配的子串。
   * @return 一个判断嵌套属性NOT LIKE条件的条件对象。
   */
  public static <T, P, R> SimpleCriterion<T> notLike(final Class<T> type,
      final GetterMethod<T, P> g1, final GetterMethod<P, R> g2, final String value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2), NOT_LIKE, '%' + value + '%');
  }

  /**
   * 构造一个判断通过三个getter方法访问的嵌套字符串属性是否不包含给定子串的条件（SQL NOT LIKE操作，自动加%通配符）。
   *
   * @param type
   *     实体类的类型。
   * @param g1
   *     第一级getter方法。
   * @param g2
   *     第二级getter方法。
   * @param g3
   *     第三级getter方法。
   * @param value
   *     要匹配的子串。
   * @return 一个判断嵌套属性NOT LIKE条件的条件对象。
   */
  public static <T, P1, P2, R> SimpleCriterion<T> notLike(final Class<T> type,
      final GetterMethod<T, P1> g1, final GetterMethod<P1, P2> g2,
      final GetterMethod<P2, R> g3, final String value) {
    return new SimpleCriterion<>(type, getPropertyPath(type, g1, g2, g3), NOT_LIKE, '%' + value + '%');
  }

  /**
   * 构造一个复合条件，要求所有参数中的属性都等于对应的值。
   *
   * @param type
   *     实体类的类型。
   * @param params
   *     属性名与属性值的映射。
   * @return 一个所有属性都等于对应值的复合条件对象。
   */
  public static <T> Criterion<T> allEqual(final Class<T> type,
      final Map<String, Object> params) {
    final List<SimpleCriterion<T>> criteria = new ArrayList<>();
    for (final Map.Entry<String, Object> entry : params.entrySet()) {
      criteria.add(new SimpleCriterion<>(type, entry.getKey(), EQUAL, entry.getValue()));
    }
    return new ComposedCriterion<>(type, AND, criteria);
  }

  /**
   * 构造一个复合条件，要求任一参数中的属性等于对应的值。
   *
   * @param type
   *     实体类的类型。
   * @param params
   *     属性名与属性值的映射。
   * @return 一个任一属性等于对应值的复合条件对象。
   */
  public static <T> Criterion<T> anyEqual(final Class<T> type,
      final Map<String, Object> params) {
    final List<SimpleCriterion<T>> criteria = new ArrayList<>();
    for (final Map.Entry<String, Object> entry : params.entrySet()) {
      criteria.add(new SimpleCriterion<>(type, entry.getKey(), EQUAL, entry.getValue()));
    }
    return new ComposedCriterion<>(type, OR, criteria);
  }

  /**
   * 构造一个AND逻辑关系的复合条件。
   *
   * @param type
   *     实体类的类型。
   * @param criteria
   *     需要AND组合的条件。
   * @return 一个AND逻辑关系的复合条件对象。
   */
  @SuppressWarnings("varargs")
  @SafeVarargs
  public static <T> ComposedCriterion<T> and(final Class<T> type,
      final SimpleCriterion<T>... criteria) {
    return new ComposedCriterion<>(type, AND, criteria);
  }

  /**
   * 构造一个OR逻辑关系的复合条件。
   *
   * @param type
   *     实体类的类型。
   * @param criteria
   *     需要OR组合的条件。
   * @return 一个OR逻辑关系的复合条件对象。
   */
  @SuppressWarnings("varargs")
  @SafeVarargs
  public static <T> ComposedCriterion<T> or(final Class<T> type,
      final SimpleCriterion<T>... criteria) {
    return new ComposedCriterion<>(type, OR, criteria);
  }

  /**
   * 构造一个NOT逻辑关系的复合条件。
   *
   * @param type
   *     实体类的类型。
   * @param criterion
   *     需要NOT取反的条件。
   * @return 一个NOT逻辑关系的复合条件对象。
   */
  public static <T> ComposedCriterion<T> not(final Class<T> type,
      final SimpleCriterion<T> criterion) {
    return new ComposedCriterion<>(type, NOT, criterion);
  }
}