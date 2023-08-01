////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import ltd.qubit.commons.reflect.impl.GetterMethod;
import ltd.qubit.commons.util.LogicRelation;

import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.sql.ComparisonOperator.EQUAL;
import static ltd.qubit.commons.sql.ComparisonOperator.GREATER;
import static ltd.qubit.commons.sql.ComparisonOperator.GREATER_EQUAL;
import static ltd.qubit.commons.sql.ComparisonOperator.IN;
import static ltd.qubit.commons.sql.ComparisonOperator.LESS;
import static ltd.qubit.commons.sql.ComparisonOperator.LESS_EQUAL;
import static ltd.qubit.commons.sql.ComparisonOperator.LIKE;
import static ltd.qubit.commons.sql.ComparisonOperator.NOT_EQUAL;
import static ltd.qubit.commons.sql.ComparisonOperator.NOT_IN;
import static ltd.qubit.commons.sql.ComparisonOperator.NOT_LIKE;
import static ltd.qubit.commons.sql.impl.CriterionImplUtils.getProperty;

/**
 * 用于构建{@link ComposedCriterion}的构造器。
 *
 * <p>使用方式如下面例子所示：</p>
 * <pre><code>
 *     final Criterion&lt;Device&gt; criterion =
 *         new ComposedCriterionBuilder&lt;&gt;(Device.class, AND)
 *             .equal(Device::getApp, StatefulInfo::getId, appId)
 *             .like(Device::getName, name)
 *             .equal(Device::getModel, model)
 *             .equal(Device::getOwner, PersonInfo::getId, ownerId)
 *             .equal(Device::getDeployAddress, Address::getStreet, Info::getId, streetId)
 *             .equal(Device::getState, state)
 *             .requireNotNull(Device::getDeleteTime, deleted)
 *             .greaterEqual(Device::getCreateTime, createTimeStart)
 *             .lessEqual(Device::getCreateTime, createTimeEnd)
 *             .build();
 * </code></pre>
 *
 * @author 胡海星
 */
public class ComposedCriterionBuilder<T> {

  private final Class<T> type;
  private final LogicRelation relation;
  private final List<SimpleCriterion<T>> criteria;

  public ComposedCriterionBuilder(final Class<T> type, final LogicRelation relation) {
    this.type = requireNonNull("type", type);
    this.relation = requireNonNull("relation", relation);
    this.criteria = new ArrayList<>();
  }

  public <R> ComposedCriterionBuilder<T> isNull(final GetterMethod<T, R> getter) {
    criteria.add(new SimpleCriterion<>(type, getProperty(type, getter), EQUAL, null));
    return this;
  }

  public <P, R> ComposedCriterionBuilder<T> isNull(final GetterMethod<T, P> g1,
      final GetterMethod<P, R> g2) {
    criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2), EQUAL, null));
    return this;
  }

  public <P1, P2, R> ComposedCriterionBuilder<T> isNull(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, R> g3) {
    criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2, g3), EQUAL, null));
    return this;
  }

  public <P1, P2, P3, R> ComposedCriterionBuilder<T> isNull(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, R> g4) {
    criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2, g3, g4),
          EQUAL, null));
    return this;
  }

  public <R> ComposedCriterionBuilder<T> isNotNull(final GetterMethod<T, R> getter) {
    criteria.add(new SimpleCriterion<>(type, getProperty(type, getter),
        NOT_EQUAL, null));
    return this;
  }

  public <P, R> ComposedCriterionBuilder<T> isNotNull(final GetterMethod<T, P> g1,
      final GetterMethod<P, R> g2) {
    criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2),
        NOT_EQUAL, null));
    return this;
  }

  public <P1, P2, R> ComposedCriterionBuilder<T> isNotNull(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, R> g3) {
    criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2, g3), NOT_EQUAL, null));
    return this;
  }

  public <P1, P2, P3, R> ComposedCriterionBuilder<T> isNotNull(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, R> g4) {
    criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2, g3, g4),
        NOT_EQUAL, null));
    return this;
  }

  public <R> ComposedCriterionBuilder<T> requireNull(final GetterMethod<T, R> getter,
      @Nullable final Boolean isNull) {
    if (isNull != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, getter),
          (isNull ? EQUAL : NOT_EQUAL), null));
    }
    return this;
  }

  public <P, R> ComposedCriterionBuilder<T> requireNull(final GetterMethod<T, P> g1,
      final GetterMethod<P, R> g2, @Nullable final Boolean isNull) {
    if (isNull != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2),
          (isNull ? EQUAL : NOT_EQUAL), null));
    }
    return this;
  }

  public <P1, P2, R> ComposedCriterionBuilder<T> requireNull(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, R> g3,
      @Nullable final Boolean isNull) {
    if (isNull != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2, g3),
          (isNull ? EQUAL : NOT_EQUAL), null));
    }
    return this;
  }

  public <P1, P2, P3, R> ComposedCriterionBuilder<T> requireNull(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, R> g4, @Nullable final Boolean isNull) {
    if (isNull != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2, g3, g4),
          (isNull ? EQUAL : NOT_EQUAL), null));
    }
    return this;
  }

  public <R> ComposedCriterionBuilder<T> requireNotNull(final GetterMethod<T, R> getter,
      @Nullable final Boolean isNotNull) {
    if (isNotNull != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, getter),
          (isNotNull ? NOT_EQUAL : EQUAL), null));
    }
    return this;
  }

  public <P, R> ComposedCriterionBuilder<T> requireNotNull(final GetterMethod<T, P> g1,
      final GetterMethod<P, R> g2, @Nullable final Boolean isNotNull) {
    if (isNotNull != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2),
          (isNotNull ? NOT_EQUAL : EQUAL), null));
    }
    return this;
  }

  public <P1, P2, R> ComposedCriterionBuilder<T> requireNotNull(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, R> g3,
      @Nullable final Boolean isNotNull) {
    if (isNotNull != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2, g3),
          (isNotNull ? NOT_EQUAL : EQUAL), null));
    }
    return this;
  }

  public <P1, P2, P3, R> ComposedCriterionBuilder<T> requireNotNull(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, R> g4, @Nullable final Boolean isNotNull) {
    if (isNotNull != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2, g3, g4),
          (isNotNull ? NOT_EQUAL : EQUAL), null));
    }
    return this;
  }

  public <R> ComposedCriterionBuilder<T> equal(final GetterMethod<T, R> getter,
      @Nullable final R value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, getter),
          EQUAL, value));
    }
    return this;
  }

  public <P, R> ComposedCriterionBuilder<T> equal(final GetterMethod<T, P> g1,
      final GetterMethod<P, R> g2, @Nullable final R value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2),
          EQUAL, value));
    }
    return this;
  }

  public <P1, P2, R> ComposedCriterionBuilder<T> equal(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, R> g3,
      @Nullable final R value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2, g3),
          EQUAL, value));
    }
    return this;
  }

  public <P1, P2, P3, R> ComposedCriterionBuilder<T> equal(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, R> g4, @Nullable final R value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2, g3, g4),
          EQUAL, value));
    }
    return this;
  }

  public <R> ComposedCriterionBuilder<T> notEqual(final GetterMethod<T, R> getter,
      @Nullable final R value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, getter),
          NOT_EQUAL, value));
    }
    return this;
  }

  public <P, R> ComposedCriterionBuilder<T> notEqual(final GetterMethod<T, P> g1,
      final GetterMethod<P, R> g2, @Nullable final R value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2),
          NOT_EQUAL, value));
    }
    return this;
  }

  public <P1, P2, R> ComposedCriterionBuilder<T> notEqual(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, R> g3,
      @Nullable final R value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2, g3),
          NOT_EQUAL, value));
    }
    return this;
  }

  public <P1, P2, P3, R> ComposedCriterionBuilder<T> notEqual(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, R> g4, @Nullable final R value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2, g3, g4),
          NOT_EQUAL, value));
    }
    return this;
  }

  public <R> ComposedCriterionBuilder<T> less(final GetterMethod<T, R> getter,
      @Nullable final R value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, getter),
          LESS, value));
    }
    return this;
  }

  public <P, R> ComposedCriterionBuilder<T> less(final GetterMethod<T, P> g1,
      final GetterMethod<P, R> g2, @Nullable final R value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2),
          LESS, value));
    }
    return this;
  }

  public <P1, P2, R> ComposedCriterionBuilder<T> less(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, R> g3,
      @Nullable final R value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2, g3),
          LESS, value));
    }
    return this;
  }

  public <P1, P2, P3, R> ComposedCriterionBuilder<T> less(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, R> g4, @Nullable final R value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2, g3, g4),
          LESS, value));
    }
    return this;
  }

  public <R> ComposedCriterionBuilder<T> lessEqual(final GetterMethod<T, R> getter,
      @Nullable final R value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, getter),
          LESS_EQUAL, value));
    }
    return this;
  }

  public <P, R> ComposedCriterionBuilder<T> lessEqual(final GetterMethod<T, P> g1,
      final GetterMethod<P, R> g2, @Nullable final R value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2),
          LESS_EQUAL, value));
    }
    return this;
  }

  public <P1, P2, R> ComposedCriterionBuilder<T> lessEqual(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, R> g3, @Nullable final R value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2, g3),
          LESS_EQUAL, value));
    }
    return this;
  }

  public <P1, P2, P3, R> ComposedCriterionBuilder<T> lessEqual(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, R> g4, @Nullable final R value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2, g3, g4),
          LESS_EQUAL, value));
    }
    return this;
  }

  public <R> ComposedCriterionBuilder<T> greater(final GetterMethod<T, R> getter,
      @Nullable final R value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, getter),
          GREATER, value));
    }
    return this;
  }

  public <P, R> ComposedCriterionBuilder<T> greater(final GetterMethod<T, P> g1,
      final GetterMethod<P, R> g2, @Nullable final R value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2),
          GREATER, value));
    }
    return this;
  }

  public <P1, P2, R> ComposedCriterionBuilder<T> greater(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, R> g3, @Nullable final R value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2, g3),
          GREATER, value));
    }
    return this;
  }

  public <P1, P2, P3, R> ComposedCriterionBuilder<T> greater(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, R> g4, @Nullable final R value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2, g3, g4),
          GREATER, value));
    }
    return this;
  }

  public <R> ComposedCriterionBuilder<T> greaterEqual(final GetterMethod<T, R> getter,
      @Nullable final R value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, getter),
          GREATER_EQUAL, value));
    }
    return this;
  }

  public <P, R> ComposedCriterionBuilder<T> greaterEqual(final GetterMethod<T, P> g1,
      final GetterMethod<P, R> g2, @Nullable final R value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2),
          GREATER_EQUAL, value));
    }
    return this;
  }

  public <P1, P2, R> ComposedCriterionBuilder<T> greaterEqual(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, R> g3, @Nullable final R value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2, g3),
          GREATER_EQUAL, value));
    }
    return this;
  }

  public <P1, P2, P3, R> ComposedCriterionBuilder<T> greaterEqual(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, R> g4, @Nullable final R value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2, g3, g4),
          GREATER_EQUAL, value));
    }
    return this;
  }

  public <R> ComposedCriterionBuilder<T> in(final GetterMethod<T, R> getter,
      @Nullable final List<R> values) {
    if (values != null && values.size() > 0) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, getter), IN, values));
    }
    return this;
  }

  public <P, R> ComposedCriterionBuilder<T> in(final GetterMethod<T, P> g1,
      final GetterMethod<P, R> g2, @Nullable final List<R> values) {
    if (values != null && values.size() > 0) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2), IN, values));
    }
    return this;
  }

  public <P1, P2, R> ComposedCriterionBuilder<T> in(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, R> g3,
      @Nullable final List<R> values) {
    if (values != null && values.size() > 0) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2, g3), IN, values));
    }
    return this;
  }

  public <P1, P2, P3, R> ComposedCriterionBuilder<T> in(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, R> g4, @Nullable final List<R> values) {
    if (values != null && values.size() > 0) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2, g3, g4),
          IN, values));
    }
    return this;
  }

  public <R> ComposedCriterionBuilder<T> notIn(final GetterMethod<T, R> getter,
      @Nullable final List<R> values) {
    if (values != null && values.size() > 0) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, getter), NOT_IN, values));
    }
    return this;
  }

  public <P, R> ComposedCriterionBuilder<T> notIn(final GetterMethod<T, P> g1,
      final GetterMethod<P, R> g2, @Nullable final List<R> values) {
    if (values != null && values.size() > 0) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2), NOT_IN, values));
    }
    return this;
  }

  public <P1, P2, R> ComposedCriterionBuilder<T> notIn(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, R> g3,
      @Nullable final List<R> values) {
    if (values != null && values.size() > 0) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2, g3), NOT_IN, values));
    }
    return this;
  }

  public <P1, P2, P3, R> ComposedCriterionBuilder<T> notIn(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, R> g4, @Nullable final List<R> values) {
    if (values != null && values.size() > 0) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2, g3, g4),
          NOT_IN, values));
    }
    return this;
  }

  public ComposedCriterionBuilder<T> like(final GetterMethod<T, String> getter,
      @Nullable final String value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, getter), LIKE,
          '%' + value + '%'));
    }
    return this;
  }

  public <P> ComposedCriterionBuilder<T> like(final GetterMethod<T, P> g1,
      final GetterMethod<P, String> g2, @Nullable final String value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2), LIKE,
          '%' + value + '%'));
    }
    return this;
  }

  public <P1, P2> ComposedCriterionBuilder<T> like(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, String> g3,
      @Nullable final String value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2, g3), LIKE,
          '%' + value + '%'));
    }
    return this;
  }

  public <P1, P2, P3> ComposedCriterionBuilder<T> like(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, String> g4, @Nullable final String value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2, g3, g4),
          LIKE, '%' + value + '%'));
    }
    return this;
  }

  public ComposedCriterionBuilder<T> notLike(final GetterMethod<T, String> getter,
      @Nullable final String value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, getter), NOT_LIKE,
          '%' + value + '%'));
    }
    return this;
  }

  public <P> ComposedCriterionBuilder<T> notLike(final GetterMethod<T, P> g1,
      final GetterMethod<P, String> g2, @Nullable final String value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2), NOT_LIKE,
          '%' + value + '%'));
    }
    return this;
  }

  public <P1, P2> ComposedCriterionBuilder<T> notLike(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, String> g3,
      @Nullable final String value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2, g3), NOT_LIKE,
          '%' + value + '%'));
    }
    return this;
  }

  public <P1, P2, P3> ComposedCriterionBuilder<T> notLike(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, String> g4, @Nullable final String value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(type, getProperty(type, g1, g2, g3, g4),
          NOT_LIKE, '%' + value + '%'));
    }
    return this;
  }

  public ComposedCriterion<T> build() {
    return new ComposedCriterion<T>(type, relation, criteria);
  }
}
