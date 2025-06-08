////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql;

import java.io.Serial;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.reflect.FieldNotExistException;
import ltd.qubit.commons.reflect.impl.GetterMethod;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.lang.ObjectUtils.defaultIfNull;
import static ltd.qubit.commons.reflect.FieldUtils.getFieldName;
import static ltd.qubit.commons.reflect.ObjectGraphUtils.getPropertyType;
import static ltd.qubit.commons.reflect.ObjectGraphUtils.getPropertyValue;
import static ltd.qubit.commons.reflect.ObjectGraphUtils.hasProperty;
import static ltd.qubit.commons.sql.impl.CriterionImplUtils.isPartialOrderComparable;
import static ltd.qubit.commons.sql.impl.CriterionImplUtils.isSupportedDataType;
import static ltd.qubit.commons.sql.impl.CriterionImplUtils.toComparableValue;
import static ltd.qubit.commons.text.NamingStyleUtils.propertyPathToDatabaseField;

/**
 * 此模型表示对SQL查询结果的排序要求。
 *
 * @param <T>
 *     待排序的实体的类型。
 * @author 胡海星
 */
@Immutable
public class SortRequest<T> implements Serializable {

  @Serial
  private static final long serialVersionUID = -1666296704696820914L;

  /**
   * 待排序的实体的类型。
   */
  private final Class<T> entityClass;

  /**
   * 排序所依据的属性路径。
   *
   * <p>支持形如"app.code"这样的属性路径。</p>
   */
  private final String property;

  /**
   * 排序的顺序，默认为{@link SortOrder#ASC}。
   */
  private final SortOrder order;

  /**
   * 对NULL值的排序策略，默认为{@link NullSortOption#NULL_FIRST}。
   */
  private final NullSortOption nullSortOption;

  /**
   * 创建一个排序要求。
   *
   * @param <T>
   *     待排序的实体的类型。
   * @param entityClass
   *     待排序的实体的类对象。
   * @param property
   *     排序所依据的属性路径。
   * @return
   *     排序要求；若{@code property}为{@code null}则返回{@code null}。
   */
  @Nullable
  public static <T> SortRequest<T> create(final Class<T> entityClass,
      @Nullable final String property) {
    if (property == null) {
      return null;
    } else {
      return new SortRequest<>(entityClass, property, SortOrder.ASC);
    }
  }

  /**
   * 创建一个排序要求。
   *
   * @param <T>
   *     待排序的实体的类型。
   * @param entityClass
   *     待排序的实体的类对象。
   * @param property
   *     排序所依据的属性路径。
   * @param defaultProperty
   *     默认的排序属性，当{@code property}为{@code null}时使用。
   * @return
   *     排序要求；若{@code property}为{@code null}则返回{@code null}。
   */
  public static <T> SortRequest<T> create(final Class<T> entityClass,
      @Nullable final String property, final String defaultProperty) {
    if (property == null) {
      return new SortRequest<>(entityClass, defaultProperty, SortOrder.ASC);
    } else {
      return new SortRequest<>(entityClass, property, SortOrder.ASC);
    }
  }

  /**
   * 创建一个排序要求。
   *
   * @param <T>
   *     待排序的实体的类型。
   * @param entityClass
   *     待排序的实体的类对象。
   * @param property
   *     排序所依据的属性路径。
   * @param defaultPropertyGetter
   *     默认的排序属性的Getter函数，当{@code property}为{@code null}时使用。
   * @return
   *     排序要求；若{@code property}为{@code null}则返回{@code null}。
   */
  public static <T, R> SortRequest<T> create(final Class<T> entityClass,
      @Nullable final String property, final GetterMethod<T, R> defaultPropertyGetter) {
    final String defaultProperty = getFieldName(entityClass, defaultPropertyGetter);
    return create(entityClass, property, defaultProperty);
  }

  /**
   * 创建一个排序要求。
   *
   * @param <T>
   *     待排序的实体的类型。
   * @param entityClass
   *     待排序的实体的类对象。
   * @param property
   *     排序所依据的属性路径。
   * @param order
   *     正反序要求。
   * @return
   *     排序要求；若{@code property}为{@code null}则返回{@code null}。
   */
  @Nullable
  public static <T> SortRequest<T> create(final Class<T> entityClass,
      @Nullable final String property,
      @Nullable final SortOrder order) {
    if (property == null) {
      return null;
    } else {
      return new SortRequest<>(entityClass, property, defaultIfNull(order, SortOrder.ASC));
    }
  }

  /**
   * 创建一个排序要求。
   *
   * @param <T>
   *     待排序的实体的类型。
   * @param entityClass
   *     待排序的实体的类对象。
   * @param property
   *     排序所依据的属性路径。
   * @param defaultProperty
   *     默认的排序属性，当{@code property}为{@code null}时使用。
   * @param order
   *     正反序要求。
   * @param defaultSortOrder
   *     默认的正反序要求，当{@code order}为{@code null}时使用。
   * @return
   *     排序要求；若{@code property}为{@code null}则返回{@code null}。
   */
  public static <T> SortRequest<T> create(final Class<T> entityClass,
      @Nullable final String property,
      final String defaultProperty,
      @Nullable final SortOrder order,
      final SortOrder defaultSortOrder) {
    if (property == null) {
      return new SortRequest<>(entityClass, defaultProperty, defaultIfNull(order, defaultSortOrder));
    } else {
      return new SortRequest<>(entityClass, property, defaultIfNull(order, defaultSortOrder));
    }
  }

  /**
   * 创建一个排序要求。
   *
   * @param <T>
   *     待排序的实体的类型。
   * @param entityClass
   *     待排序的实体的类对象。
   * @param params
   *     排序请求参数。
   * @param defaultPropertyGetter
   *     默认的排序属性的Getter函数，当{@code property}为{@code null}时使用。
   * @param defaultSortOrder
   *     默认的正反序要求，当{@code order}为{@code null}时使用。
   * @return
   *     排序要求；若{@code property}为{@code null}则返回{@code null}。
   */
  public static <T, R> SortRequest<T> create(final Class<T> entityClass,
      final WithSortRequestParams params,
      final GetterMethod<T, R> defaultPropertyGetter,
      final SortOrder defaultSortOrder) {
    final String defaultProperty = getFieldName(entityClass, defaultPropertyGetter);
    return create(entityClass, params.getSortField(), defaultProperty,
        params.getSortOrder(), defaultSortOrder, params.getNullSortOption());
  }

  /**
   * 创建一个排序要求。
   *
   * @param <T>
   *     待排序的实体的类型。
   * @param entityClass
   *     待排序的实体的类对象。
   * @param property
   *     排序所依据的属性路径。
   * @param defaultPropertyGetter
   *     默认的排序属性的Getter函数，当{@code property}为{@code null}时使用。
   * @param order
   *     正反序要求。
   * @param defaultSortOrder
   *     默认的正反序要求，当{@code order}为{@code null}时使用。
   * @return
   *     排序要求；若{@code property}为{@code null}则返回{@code null}。
   */
  public static <T, R> SortRequest<T> create(final Class<T> entityClass,
      @Nullable final String property,
      final GetterMethod<T, R> defaultPropertyGetter,
      @Nullable final SortOrder order,
      final SortOrder defaultSortOrder) {
    final String defaultProperty = getFieldName(entityClass, defaultPropertyGetter);
    return create(entityClass, property, defaultProperty, order, defaultSortOrder);
  }

  /**
   * 创建一个排序要求。
   *
   * @param <T>
   *     待排序的实体的类型。
   * @param entityClass
   *     待排序的实体的类对象。
   * @param property
   *     排序所依据的属性路径。
   * @param order
   *     正反序要求。
   * @param nullSortOption
   *     对NULL值的排序策略。
   * @return
   *     排序要求；若{@code property}为{@code null}则返回{@code null}。
   */
  @Nullable
  public static <T> SortRequest<T> create(final Class<T> entityClass,
      @Nullable final String property,
      @Nullable final SortOrder order,
      @Nullable final NullSortOption nullSortOption) {
    if (property == null) {
      return null;
    } else {
      return new SortRequest<>(entityClass, property,
          defaultIfNull(order, SortOrder.ASC),
          defaultIfNull(nullSortOption, NullSortOption.NULL_FIRST));
    }
  }

  /**
   * 创建一个排序要求。
   *
   * @param <T>
   *     待排序的实体的类型。
   * @param entityClass
   *     待排序的实体的类对象。
   * @param property
   *     排序所依据的属性路径。
   * @param defaultProperty
   *     默认的排序属性，当{@code property}为{@code null}时使用。
   * @param order
   *     正反序要求。
   * @param defaultSortOrder
   *     默认的正反序要求，当{@code order}为{@code null}时使用。
   * @param nullSortOption
   *     对NULL值的排序策略。
   * @return
   *     排序要求；若{@code property}为{@code null}则返回{@code null}。
   */
  public static <T> SortRequest<T> create(final Class<T> entityClass,
      @Nullable final String property,
      final String defaultProperty,
      @Nullable final SortOrder order,
      final SortOrder defaultSortOrder,
      @Nullable final NullSortOption nullSortOption) {
    if (property == null) {
      return new SortRequest<>(entityClass, defaultProperty,
          defaultIfNull(order, defaultSortOrder),
          defaultIfNull(nullSortOption, NullSortOption.NULL_FIRST));
    } else {
      return new SortRequest<>(entityClass, property,
          defaultIfNull(order, defaultSortOrder),
          defaultIfNull(nullSortOption, NullSortOption.NULL_FIRST));
    }
  }

  /**
   * 创建一个排序要求。
   *
   * @param <T>
   *     待排序的实体的类型。
   * @param entityClass
   *     待排序的实体的类对象。
   * @param property
   *     排序所依据的属性路径。
   * @param defaultPropertyGetter
   *     默认的排序属性的Getter函数，当{@code property}为{@code null}时使用。
   * @param order
   *     正反序要求。
   * @param defaultSortOrder
   *     默认的正反序要求，当{@code order}为{@code null}时使用。
   * @param nullSortOption
   *     对NULL值的排序策略。
   * @return
   *     排序要求；若{@code property}为{@code null}则返回{@code null}。
   */
  public static <T, R> SortRequest<T> create(final Class<T> entityClass,
      @Nullable final String property,
      final GetterMethod<T, R> defaultPropertyGetter,
      @Nullable final SortOrder order,
      final SortOrder defaultSortOrder,
      @Nullable final NullSortOption nullSortOption) {
    final String defaultProperty = getFieldName(entityClass, defaultPropertyGetter);
    return create(entityClass, property, defaultProperty, order, defaultSortOrder, nullSortOption);
  }

  public SortRequest(final Class<T> entityClass, final String property) {
    this(entityClass, property, SortOrder.ASC);
  }

  public SortRequest(final Class<T> entityClass, final String property,
      final SortOrder order) {
    this(entityClass, property, order, NullSortOption.NULL_FIRST);
  }

  public SortRequest(final Class<T> entityClass, final String property,
      final SortOrder order, final NullSortOption nullSortOption) {
    this.entityClass = requireNonNull("entityClass", entityClass);
    this.property = requireNonNull("property", property);
    this.order = requireNonNull("order", order);
    this.nullSortOption = requireNonNull("nullSortOption", nullSortOption);
  }

  public <R> SortRequest(final Class<T> entityClass, final GetterMethod<T, R> getter) {
    this(entityClass, getFieldName(entityClass, getter), SortOrder.ASC);
  }

  public <R> SortRequest(final Class<T> entityClass, final GetterMethod<T, R> getter,
      final SortOrder order) {
    this(entityClass, getFieldName(entityClass, getter), order);
  }

  public <R> SortRequest(final Class<T> entityClass, final GetterMethod<T, R> getter,
      final SortOrder order, final NullSortOption nullSortOption) {
    this(entityClass, getFieldName(entityClass, getter), order, nullSortOption);
  }

  /**
   * 获取待排序的实体的类型。
   *
   * @return
   *     待排序的实体的类型。
   */
  public final Class<T> getEntityClass() {
    return entityClass;
  }

  /**
   * 获取排序依据的属性路径。
   *
   * @return
   *     排序依据的属性路径。
   */
  public final String getProperty() {
    return property;
  }

  /**
   * 获取排序依据的属性对应的数据库表字段名。
   *
   * <p>对于形如"app.code"这样的属性路径将转换为"app_code"这样的字段名。</p>
   *
   * @return
   *     排序依据的属性对应的数据库表字段名。
   */
  public final String getField() {
    return propertyPathToDatabaseField(property);
  }

  /**
   * 获取排序依据的顺序。
   *
   * @return
   *     排序依据的顺序。
   */
  public final SortOrder getOrder() {
    return order;
  }

  /**
   * 获取对NULL值的排序策略。
   *
   * @return
   *     对NULL值的排序策略。
   */
  public final NullSortOption getNullSortOption() {
    return nullSortOption;
  }

  /**
   * 判定此排序要求是否合法。
   *
   * @return
   *     如果此排序要求合法则返回{@code true}；否则返回{@code false}。
   */
  public final boolean isValid() {
    if (! hasProperty(entityClass, property)) {
      return false;
    }
    final Class<?> propertyType = getPropertyType(entityClass, property);
    if (! propertyType.isAssignableFrom(Comparable.class)) {
      return false;
    }
    return isSupportedDataType(propertyType);
  }

  /**
   * 按照此排序要求，对指定的列表中的元素进行排序。
   *
   * @param list
   *     指定的列表。
   */
  public final void sort(final List<T> list) {
    if (list == null || list.isEmpty()) {
      return;
    }
    list.sort(getComparator());
  }

  /**
   * 按照此排序要求，返回用于比较列表中元素的{@link Comparator}。
   *
   * @return
   *     按照此排序要求，用于比较列表中元素的{@link Comparator}。
   */
  public Comparator<T> getComparator() {
    if (! hasProperty(entityClass, property)) {
      throw new FieldNotExistException(entityClass, property);
    }
    final Class<?> type = getPropertyType(entityClass, property);
    if (! isPartialOrderComparable(type)) {
      throw new IllegalArgumentException("The property '" + property
          + "' of the class " + entityClass.getName() + " must be comparable.");
    }
    return (x, y) -> {
      final Object keyX = getPropertyValue(x, property);
      final Object keyY = getPropertyValue(y, property);
      if (keyX == null || keyY == null) {
        return nullSortOption.compare(keyX, keyY, order);
      } else {
        @SuppressWarnings({"unchecked"})
        final Comparable<Object> cpX = (Comparable<Object>) toComparableValue(keyX);
        final Object cpY = toComparableValue(keyY);
        final int rc = cpX.compareTo(cpY);
        switch (order) {
          case ASC:
            return rc;
          case DESC:
            return -rc;
          default:
            throw new AssertionError("Unknown sort order: " + order);
        }
      }
    };
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final SortRequest<?> other = (SortRequest<?>) o;
    return Equality.equals(entityClass, other.entityClass)
        && Equality.equals(property, other.property)
        && Equality.equals(order, other.order)
        && Equality.equals(nullSortOption,
        other.nullSortOption);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, entityClass);
    result = Hash.combine(result, multiplier, property);
    result = Hash.combine(result, multiplier, order);
    result = Hash.combine(result, multiplier, nullSortOption);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("entityClass", entityClass)
        .append("property", property)
        .append("order", order)
        .append("nullSortOption", nullSortOption)
        .toString();
  }
}