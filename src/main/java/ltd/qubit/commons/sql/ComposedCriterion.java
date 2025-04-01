////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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

  private final Class<T> entityClass;

  private final List<SimpleCriterion<T>> criteria;

  private final LogicRelation relation;

  public ComposedCriterion(final Class<T> entityClass,
      final LogicRelation relation,
      final List<SimpleCriterion<T>> criteria) {
    this.entityClass = requireNonNull("entityClass", entityClass);
    this.relation = requireNonNull("relation", relation);
    this.criteria = requireNonNull("criteria", criteria);
  }

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

  @Override
  public Class<T> getEntityClass() {
    return entityClass;
  }

  public LogicRelation getRelation() {
    return relation;
  }

  public List<SimpleCriterion<T>> getCriteria() {
    return criteria;
  }

  public ComposedCriterion<T> addCriterion(final SimpleCriterion<T> criterion) {
    criteria.add(requireNonNull("criterion", criterion));
    return this;
  }

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
   * Extracts a criterion for a sub-entity from the current criterion.
   * <p>
   * This method is useful when dealing with nested entity properties. It checks
   * if the current criterion's property path starts with the given sub-entity's
   * property path. If it does, it creates a new criterion for the sub-entity by
   * removing the common prefix from the property path.
   * <p>
   * For example, if the current criterion is "order.customer.name = 'John' AND order.customer.age > 18"
   * and we want to get a criterion for the Customer entity, this method will return
   * "name = 'John' AND age > 18".
   *
   * @param <P>
   *     The type of the sub-entity.
   * @param propertyClass
   *     The class of the sub-entity.
   * @param propertyGetter
   *     The getter method for the sub-entity property.
   * @return
   *     A new criterion for the sub-entity containing all matching criteria, or
   *     {@code null} if no matching criteria are found.
   */
  @Nullable
  public <P, Q> ComposedCriterion<Q> extractSubEntityCriterion(final Class<Q> propertyClass,
      final GetterMethod<T, P> propertyGetter) {
    final String path = getPropertyPath(entityClass, propertyGetter);
    return extractSubEntityCriterion(propertyClass, path);
  }

  /**
   * Extracts a criterion for a sub-entity from the current criterion.
   * <p>
   * This method is useful when dealing with nested entity properties. It checks
   * if the current criterion's property path starts with the given sub-entity's
   * property path. If it does, it creates a new criterion for the sub-entity by
   * removing the common prefix from the property path.
   * <p>
   * For example, if the current criterion is "order.customer.name = 'John' AND order.customer.age > 18"
   * and we want to get a criterion for the Customer entity, this method will return
   * "name = 'John' AND age > 18".
   *
   * @param <P>
   *     The type of the sub-entity.
   * @param propertyClass
   *     The class of the sub-entity.
   * @param propertyPath
   *     The property path string for the sub-entity.
   * @return
   *     A new criterion for the sub-entity containing all matching criteria.
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
   * Excludes criteria matching the sub-entity path and returns the remaining criteria.
   * <p>
   * This method is the dual of {@link #extractSubEntityCriterion(Class, String)}. It returns
   * a new criterion containing all criteria that do not match the given sub-entity path.
   * <p>
   * For example, if the current criterion is "order.customer.name = 'John' AND order.customer.age > 18
   * AND order.status = 'NEW'" and we want to exclude the criteria for the Customer entity,
   * this method will return "order.status = 'NEW'".
   *
   * @param <P>
   *     The type of the sub-entity.
   * @param propertyClass
   *     The class of the sub-entity.
   * @param propertyGetter
   *     The getter method for the sub-entity property.
   * @return
   *     A new criterion containing all criteria that do not match the sub-entity path,
   *     or {@code null} if all criteria match the sub-entity path.
   */
  @Nullable
  public <P, Q> ComposedCriterion<T> excludeSubEntityCriterion(final Class<Q> propertyClass,
      final GetterMethod<T, P> propertyGetter) {
    final String path = getPropertyPath(entityClass, propertyGetter);
    return excludeSubEntityCriterion(propertyClass, path);
  }

  /**
   * Excludes criteria matching the sub-entity path and returns the remaining criteria.
   * <p>
   * This method is the dual of {@link #extractSubEntityCriterion(Class, String)}. It returns
   * a new criterion containing all criteria that do not match the given sub-entity path.
   * <p>
   * For example, if the current criterion is "order.customer.name = 'John' AND order.customer.age > 18
   * AND order.status = 'NEW'" and we want to exclude the criteria for the Customer entity,
   * this method will return "order.status = 'NEW'".
   *
   * @param <P>
   *     The type of the sub-entity.
   * @param propertyClass
   *     The class of the sub-entity.
   * @param propertyPath
   *     The property path string for the sub-entity.
   * @return
   *     A new criterion containing all criteria that do not match the sub-entity path,
   *     or {@code null} if all criteria match the sub-entity path.
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
   * Extracts a criterion for a deeply nested sub-entity from the current criterion.
   * <p>
   * This method is similar to {@link #extractSubEntityCriterion(Class, GetterMethod)} but
   * supports deeper property paths through multiple getters.
   * <p>
   * For example, if the current criterion is "order.customer.address.city = 'Beijing'"
   * and we want to get a criterion for the Address entity, this method will return
   * "city = 'Beijing'".
   *
   * @param <P>
   *     The type of the sub-entity.
   * @param propertyClass
   *     The class of the sub-entity.
   * @param g1
   *     The first getter method.
   * @param g2
   *     The second getter method.
   * @return
   *     A new criterion for the sub-entity containing all matching criteria, or
   *     {@code null} if no matching criteria are found.
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
   * Extracts a criterion for a deeply nested sub-entity from the current criterion.
   * <p>
   * This method is similar to {@link #extractSubEntityCriterion(Class, GetterMethod)} but
   * supports deeper property paths through multiple getters.
   * <p>
   * For example, if the current criterion is "order.customer.address.city.country = 'China'"
   * and we want to get a criterion for the Country entity, this method will return
   * "name = 'China'".
   *
   * @param <P>
   *     The type of the sub-entity.
   * @param propertyClass
   *     The class of the sub-entity.
   * @param g1
   *     The first getter method.
   * @param g2
   *     The second getter method.
   * @param g3
   *     The third getter method.
   * @return
   *     A new criterion for the sub-entity containing all matching criteria, or
   *     {@code null} if no matching criteria are found.
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
   * Excludes criteria matching the deeply nested sub-entity path and returns the remaining criteria.
   * <p>
   * This method is similar to {@link #excludeSubEntityCriterion(Class, GetterMethod)} but
   * supports deeper property paths through multiple getters.
   * <p>
   * For example, if the current criterion is "order.customer.address.city = 'Beijing'
   * AND order.status = 'NEW'" and we want to exclude the criteria for the Address entity,
   * this method will return "order.status = 'NEW'".
   *
   * @param <P>
   *     The type of the sub-entity.
   * @param propertyClass
   *     The class of the sub-entity.
   * @param g1
   *     The first getter method.
   * @param g2
   *     The second getter method.
   * @return
   *     A new criterion containing all criteria that do not match the sub-entity path,
   *     or {@code null} if all criteria match the sub-entity path.
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
   * Excludes criteria matching the deeply nested sub-entity path and returns the remaining criteria.
   * <p>
   * This method is similar to {@link #excludeSubEntityCriterion(Class, GetterMethod)} but
   * supports deeper property paths through multiple getters.
   * <p>
   * For example, if the current criterion is "order.customer.address.city.country = 'China'
   * AND order.status = 'NEW'" and we want to exclude the criteria for the Country entity,
   * this method will return "order.status = 'NEW'".
   *
   * @param <P>
   *     The type of the sub-entity.
   * @param propertyClass
   *     The class of the sub-entity.
   * @param g1
   *     The first getter method.
   * @param g2
   *     The second getter method.
   * @param g3
   *     The third getter method.
   * @return
   *     A new criterion containing all criteria that do not match the sub-entity path,
   *     or {@code null} if all criteria match the sub-entity path.
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