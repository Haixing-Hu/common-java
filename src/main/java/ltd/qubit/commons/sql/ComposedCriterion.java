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
import java.util.Arrays;
import java.util.List;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;
import ltd.qubit.commons.util.LogicRelation;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

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
    this.criteria = Arrays.asList(criteria);
  }

  @Override
  public Class<T> getEntityClass() {
    return entityClass;
  }

  public final LogicRelation getRelation() {
    return relation;
  }

  public final List<SimpleCriterion<T>> getCriteria() {
    return criteria;
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
}
