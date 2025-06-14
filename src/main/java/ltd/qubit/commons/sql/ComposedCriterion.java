////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql;

import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.reflect.impl.GetterMethod;
import ltd.qubit.commons.text.tostring.ToStringBuilder;
import ltd.qubit.commons.util.LogicRelation;

import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.reflect.ObjectGraphUtils.getPropertyPath;

/**
 * 此模型表示用于过滤实体的复合条件表达式。
 *
 * <p><b>注意：</b>因为MyBatis对递归引用SQL片段的限制，我们只能限制{@link ComposedCriterion}
 * 中的子条件为{@link SimpleCriterion}，而非通用的{@link Criterion}。换句话说，
 * {@link ComposedCriterion}只能是由{@link SimpleCriterion}组合而成，不能由其他的
 * {@link ComposedCriterion}组合而成。</p>
 *
 * @param <T>
 *     待过滤的实体的类。
 * @author 胡海星
 */
public class ComposedCriterion<T> implements Criterion<T> {

  /**
   * 待过滤的实体的类。
   */
  private final Class<T> entityClass;

  /**
   * 子条件之间的逻辑关系。
   */
  private final LogicRelation relation;

  /**
   * 子条件列表。
   */
  private final List<SimpleCriterion<T>> criteria;

  /**
   * 构造一个新的复合条件。
   *
   * @param entityClass
   *     实体类。
   * @param relation
   *     子条件之间的逻辑关系。
   * @param criteria
   *     子条件列表。
   */
  public ComposedCriterion(final Class<T> entityClass,
      final LogicRelation relation,
      final List<SimpleCriterion<T>> criteria) {
    this.entityClass = requireNonNull("entityClass", entityClass);
    this.relation = requireNonNull("relation", relation);
    this.criteria = requireNonNull("criteria", criteria);
  }

  /**
   * 构造一个新的复合条件。
   *
   * @param entityClass
   *     实体类。
   * @param relation
   *     逻辑关系。
   * @param criteria
   *     子条件数组。
   */
  @SuppressWarnings("varargs")
  @SafeVarargs
  public ComposedCriterion(final Class<T> entityClass,
      final LogicRelation relation,
      final SimpleCriterion<T>... criteria) {
    this.entityClass = requireNonNull("entityClass", entityClass);
    this.relation = requireNonNull("relation", relation);
    this.criteria = new ArrayList<>();
    for (final SimpleCriterion<T> criterion : criteria) {
      this.criteria.add(requireNonNull("criteria", criterion));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<T> getEntityClass() {
    return entityClass;
  }

  /**
   * 获取逻辑关系。
   *
   * @return
   *     逻辑关系。
   */
  public LogicRelation getRelation() {
    return relation;
  }

  /**
   * 获取子条件列表。
   *
   * @return
   *     子条件列表。
   */
  public List<SimpleCriterion<T>> getCriteria() {
    return criteria;
  }

  /**
   * 添加一个子条件。
   *
   * @param criterion
   *     要添加的子条件。
   * @return
   *     当前复合条件对象，用于链式调用。
   */
  public ComposedCriterion<T> addCriterion(final SimpleCriterion<T> criterion) {
    criteria.add(requireNonNull("criterion", criterion));
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toSql() throws SQLSyntaxErrorException {
    final StringBuilder builder = new StringBuilder();
    if (relation == LogicRelation.NOT) {
      if (criteria.size() != 1) {
        throw new SQLSyntaxErrorException("Cannot convert to SQL: " + this);
      }
      final Criterion<T> criterion = criteria.get(0);
      if (criterion == null) {
        throw new SQLSyntaxErrorException("Cannot convert to SQL: " + this);
      }
      builder.append(relation.getSymbol())
          .append(" (")
          .append(criterion.toSql())
          .append(")");
    } else {
      boolean first = true;
      for (final Criterion<T> criterion : criteria) {
        if (criterion == null) {
          throw new SQLSyntaxErrorException("Cannot convert to SQL: " + this);
        }
        if (first) {
          first = false;
        } else {
          builder.append(" ")
              .append(relation.getSymbol())
              .append(" ");
        }
        builder.append("(")
               .append(criterion.toSql())
               .append(")");
      }
    }
    return builder.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isValid() {
    if (criteria.isEmpty()) {
      return false;
    }
    if (relation == LogicRelation.NOT && criteria.size() != 1) {
      return false;
    }
    for (final Criterion<T> criterion : criteria) {
      if (criterion == null) {
        return false;
      }
      if (! criterion.isValid()) {
        return false;
      }
    }
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean accept(final T obj) {
    if (obj == null) {
      return false;
    }
    switch (relation) {
      case NOT:
        if (criteria.size() != 1) {
          return false;
        }
        return (! criteria.get(0).accept(obj));
      case AND:
        for (final Criterion<T> criterion : criteria) {
          if (! criterion.accept(obj)) {
            return false;
          }
        }
        return true;
      case OR:
        for (final Criterion<T> criterion : criteria) {
          if (criterion.accept(obj)) {
            return true;
          }
        }
        return false;
      default:
        return false;
    }
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final ComposedCriterion<?> other = (ComposedCriterion<?>) o;
    return Equality.equals(entityClass, other.entityClass)
        && Equality.equals(criteria, other.criteria)
        && Equality.equals(relation, other.relation);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, entityClass);
    result = Hash.combine(result, multiplier, criteria);
    result = Hash.combine(result, multiplier, relation);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("entityClass", entityClass)
        .append("criteria", criteria)
        .append("relation", relation)
        .toString();
  }

  /**
   * 从当前条件中提取子实体的条件。
   * <p>
   * 此方法在处理嵌套实体属性时很有用。它检查当前条件的属性路径是否以给定子实体的属性路径开头。
   * 如果是，它通过从属性路径中移除公共前缀来为子实体创建一个新条件。
   * <p>
   * 例如，如果当前条件是"order.customer.name = 'John' AND order.customer.age > 18"，
   * 我们想要获取Customer实体的条件，此方法将返回"name = 'John' AND age > 18"。
   *
   * @param <P>
   *     子实体的类型。
   * @param propertyClass
   *     子实体的类。
   * @param propertyGetter
   *     子实体属性的getter方法。
   * @return
   *     包含所有匹配条件的子实体新条件，如果没有找到匹配条件则返回{@code null}。
   */
  @Nullable
  public <P, Q> ComposedCriterion<Q> extractSubEntityCriterion(final Class<Q> propertyClass,
      final GetterMethod<T, P> propertyGetter) {
    final String path = getPropertyPath(entityClass, propertyGetter);
    return extractSubEntityCriterion(propertyClass, path);
  }

  /**
   * 从当前条件中提取子实体的条件。
   * <p>
   * 此方法在处理嵌套实体属性时很有用。它检查当前条件的属性路径是否以给定子实体的属性路径开头。
   * 如果是，它通过从属性路径中移除公共前缀来为子实体创建一个新条件。
   * <p>
   * 例如，如果当前条件是"order.customer.name = 'John' AND order.customer.age > 18"，
   * 我们想要获取Customer实体的条件，此方法将返回"name = 'John' AND age > 18"。
   *
   * @param <P>
   *     子实体的类型。
   * @param propertyClass
   *     子实体的类。
   * @param propertyPath
   *     子实体的属性路径字符串。
   * @return
   *     包含所有匹配条件的子实体新条件。
   */
  @Nullable
  public <P> ComposedCriterion<P> extractSubEntityCriterion(final Class<P> propertyClass,
      final String propertyPath) {
    final ComposedCriterion<P> result = new ComposedCriterion<>(propertyClass, relation);
    boolean hasMatchingCriteria = false;
    for (final SimpleCriterion<T> criterion : criteria) {
      final SimpleCriterion<P> subCriterion =
          criterion.extractSubEntityCriterion(propertyClass, propertyPath);
      if (subCriterion != null) {
        result.criteria.add(subCriterion);
        hasMatchingCriteria = true;
      }
    }
    return hasMatchingCriteria ? result : null;
  }

  /**
   * 排除匹配子实体路径的条件并返回剩余条件。
   * <p>
   * 此方法是{@link #extractSubEntityCriterion(Class, String)}的对偶方法。它返回
   * 一个包含所有不匹配给定子实体路径的条件的新条件。
   * <p>
   * 例如，如果当前条件是"order.customer.name = 'John' AND order.customer.age > 18
   * AND order.status = 'NEW'"，我们想要排除Customer实体的条件，
   * 此方法将返回"order.status = 'NEW'"。
   *
   * @param <P>
   *     子实体的类型。
   * @param propertyClass
   *     子实体的类。
   * @param propertyGetter
   *     子实体属性的getter方法。
   * @return
   *     包含所有不匹配子实体路径的条件的新条件，
   *     如果所有条件都匹配子实体路径则返回{@code null}。
   */
  @Nullable
  public <P, Q> ComposedCriterion<T> excludeSubEntityCriterion(final Class<Q> propertyClass,
      final GetterMethod<T, P> propertyGetter) {
    final String path = getPropertyPath(entityClass, propertyGetter);
    return excludeSubEntityCriterion(propertyClass, path);
  }

  /**
   * 排除匹配子实体路径的条件并返回剩余条件。
   * <p>
   * 此方法是{@link #extractSubEntityCriterion(Class, String)}的对偶方法。它返回
   * 一个包含所有不匹配给定子实体路径的条件的新条件。
   * <p>
   * 例如，如果当前条件是"order.customer.name = 'John' AND order.customer.age > 18
   * AND order.status = 'NEW'"，我们想要排除Customer实体的条件，
   * 此方法将返回"order.status = 'NEW'"。
   *
   * @param <P>
   *     子实体的类型。
   * @param propertyClass
   *     子实体的类。
   * @param propertyPath
   *     子实体的属性路径字符串。
   * @return
   *     包含所有不匹配子实体路径的条件的新条件，
   *     如果所有条件都匹配子实体路径则返回{@code null}。
   */
  @Nullable
  public <P> ComposedCriterion<T> excludeSubEntityCriterion(final Class<P> propertyClass,
      final String propertyPath) {
    final ComposedCriterion<T> result = new ComposedCriterion<>(entityClass, relation);
    boolean hasRemainingCriteria = false;
    for (final SimpleCriterion<T> criterion : criteria) {
      final SimpleCriterion<P> subCriterion =
          criterion.extractSubEntityCriterion(propertyClass, propertyPath);
      if (subCriterion == null) {
        result.criteria.add(criterion);
        hasRemainingCriteria = true;
      }
    }
    return hasRemainingCriteria ? result : null;
  }

  /**
   * 从当前条件中提取深度嵌套子实体的条件。
   * <p>
   * 此方法与{@link #extractSubEntityCriterion(Class, GetterMethod)}类似，但
   * 支持通过多个getter实现更深的属性路径。
   * <p>
   * 例如，如果当前条件是"order.customer.address.city = 'Beijing'"，
   * 我们想要获取Address实体的条件，此方法将返回"city = 'Beijing'"。
   *
   * @param <P>
   *     子实体的类型。
   * @param propertyClass
   *     子实体的类。
   * @param g1
   *     第一个getter方法。
   * @param g2
   *     第二个getter方法。
   * @return
   *     包含所有匹配条件的子实体新条件，如果没有找到匹配条件则返回{@code null}。
   */
  @Nullable
  public <P, P1, P2> ComposedCriterion<P> extractSubEntityCriterion(
      final Class<P> propertyClass,
      final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2) {
    final String path = getPropertyPath(entityClass, g1, g2);
    return extractSubEntityCriterion(propertyClass, path);
  }

  /**
   * 从当前条件中提取深度嵌套子实体的条件。
   * <p>
   * 此方法与{@link #extractSubEntityCriterion(Class, GetterMethod)}类似，但
   * 支持通过多个getter实现更深的属性路径。
   * <p>
   * 例如，如果当前条件是"order.customer.address.city.country = 'China'"，
   * 我们想要获取Country实体的条件，此方法将返回"name = 'China'"。
   *
   * @param <P>
   *     子实体的类型。
   * @param propertyClass
   *     子实体的类。
   * @param g1
   *     第一个getter方法。
   * @param g2
   *     第二个getter方法。
   * @param g3
   *     第三个getter方法。
   * @return
   *     包含所有匹配条件的子实体新条件，如果没有找到匹配条件则返回{@code null}。
   */
  @Nullable
  public <P, P1, P2, P3> ComposedCriterion<P> extractSubEntityCriterion(
      final Class<P> propertyClass,
      final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2,
      final GetterMethod<P2, P3> g3) {
    final String path = getPropertyPath(entityClass, g1, g2, g3);
    return extractSubEntityCriterion(propertyClass, path);
  }

  /**
   * 排除匹配深度嵌套子实体路径的条件并返回剩余条件。
   * <p>
   * 此方法与{@link #excludeSubEntityCriterion(Class, GetterMethod)}类似，但
   * 支持通过多个getter实现更深的属性路径。
   * <p>
   * 例如，如果当前条件是"order.customer.address.city = 'Beijing'
   * AND order.status = 'NEW'"，我们想要排除Address实体的条件，
   * 此方法将返回"order.status = 'NEW'"。
   *
   * @param <P>
   *     子实体的类型。
   * @param propertyClass
   *     子实体的类。
   * @param g1
   *     第一个getter方法。
   * @param g2
   *     第二个getter方法。
   * @return
   *     包含所有不匹配子实体路径的条件的新条件，
   *     如果所有条件都匹配子实体路径则返回{@code null}。
   */
  @Nullable
  public <P, P1, P2> ComposedCriterion<T> excludeSubEntityCriterion(
      final Class<P> propertyClass,
      final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2) {
    final String path = getPropertyPath(entityClass, g1, g2);
    return excludeSubEntityCriterion(propertyClass, path);
  }

  /**
   * 排除匹配深度嵌套子实体路径的条件并返回剩余条件。
   * <p>
   * 此方法与{@link #excludeSubEntityCriterion(Class, GetterMethod)}类似，但
   * 支持通过多个getter实现更深的属性路径。
   * <p>
   * 例如，如果当前条件是"order.customer.address.city.country = 'China'
   * AND order.status = 'NEW'"，我们想要排除Country实体的条件，
   * 此方法将返回"order.status = 'NEW'"。
   *
   * @param <P>
   *     子实体的类型。
   * @param propertyClass
   *     子实体的类。
   * @param g1
   *     第一个getter方法。
   * @param g2
   *     第二个getter方法。
   * @param g3
   *     第三个getter方法。
   * @return
   *     包含所有不匹配子实体路径的条件的新条件，
   *     如果所有条件都匹配子实体路径则返回{@code null}。
   */
  @Nullable
  public <P, P1, P2, P3> ComposedCriterion<T> excludeSubEntityCriterion(
      final Class<P> propertyClass,
      final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2,
      final GetterMethod<P2, P3> g3) {
    final String path = getPropertyPath(entityClass, g1, g2, g3);
    return excludeSubEntityCriterion(propertyClass, path);
  }
}