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
import java.util.List;

import javax.annotation.Nullable;

import ltd.qubit.commons.reflect.impl.GetterMethod;
import ltd.qubit.commons.util.LogicRelation;

import static ltd.qubit.commons.lang.Argument.requireNonNull;
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

/**
 * 用于构造{@link ComposedCriterion}对象的建造者，使用流式API。
 *
 * <p>此类提供了类型安全且灵活的方式来构建复杂的查询条件。
 * 它支持各种比较操作符（等于、不等于、小于、大于等）
 * 并且可以处理任意深度的属性路径。建造者还支持空值安全操作，
 * 只有在提供的值不为null时才添加条件。</p>
 *
 * <p>建造者可以同时使用直接属性路径（作为字符串指定）和
 * 属性访问器方法（使用方法引用指定），在需要时提供编译时类型安全。</p>
 *
 * <p>使用示例：</p>
 * <pre><code>
 *     final Criterion&lt;Device&gt; criterion =
 *         new ComposedCriterionBuilder&lt;&gt;(Device.class, LogicRelation.AND)
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
 * <p>建造者使用方法链模式提供一种清晰可读的方式来构造复杂条件。
 * 对于每个比较操作，都有多个重载方法，可以接受字符串形式的属性路径
 * 或用于类型安全属性访问的getter方法引用。</p>
 *
 * @param <T> 此条件建造者针对的实体类型。
 * @author 胡海星
 */
public class ComposedCriterionBuilder<T> {

  private final Class<T> entityClass;
  private final LogicRelation relation;
  private final List<SimpleCriterion<T>> criteria;

  /**
   * 为给定实体类构造一个新的建造者，使用默认的AND逻辑关系。
   *
   * @param entityClass
   *      此建造者创建条件针对的实体类
   */
  public ComposedCriterionBuilder(final Class<T> entityClass) {
    this(entityClass, LogicRelation.AND);
  }

  /**
   * 为给定实体类构造一个新的建造者，使用指定的逻辑关系。
   *
   * @param entityClass
   *      此建造者创建条件针对的实体类
   * @param relation
   *      组合多个条件时使用的逻辑关系（AND/OR）
   */
  public ComposedCriterionBuilder(final Class<T> entityClass, final LogicRelation relation) {
    this.entityClass = requireNonNull("entityClass", entityClass);
    this.relation = requireNonNull("relation", relation);
    this.criteria = new ArrayList<>();
  }

  /**
   * 基于现有的复合条件构造新的建造者。
   *
   * <p>此构造函数可用于通过添加额外条件来修改现有条件。</p>
   *
   * @param criterion
   *      作为此建造者基础的现有复合条件
   */
  public ComposedCriterionBuilder(final ComposedCriterion<T> criterion) {
    this.entityClass = criterion.getEntityClass();
    this.relation = criterion.getRelation();
    this.criteria = new ArrayList<>(criterion.getCriteria());
  }

  /**
   * 添加一个检查指定属性是否为null的条件。
   *
   * @param path
   *      要检查的属性路径
   * @return 此建造者实例，用于方法链调用
   */
  public ComposedCriterionBuilder<T> isNull(final String path) {
    criteria.add(new SimpleCriterion<>(entityClass, path, EQUAL, null));
    return this;
  }

  /**
   * 添加一个检查通过getter方法访问的属性是否为null的条件。
   *
   * @param <R>
   *      getter方法的返回类型
   * @param getter
   *      用于访问属性的getter方法引用
   * @return 此建造者实例，用于方法链调用
   */
  public <R> ComposedCriterionBuilder<T> isNull(final GetterMethod<T, R> getter) {
    final String path = getPropertyPath(entityClass, getter);
    criteria.add(new SimpleCriterion<>(entityClass, path, EQUAL, null));
    return this;
  }

  /**
   * 添加一个检查通过两个getter方法访问的嵌套属性是否为null的条件。
   *
   * @param <P>
   *      第一级属性的类型
   * @param <R>
   *      第二个getter方法的返回类型
   * @param g1
   *      第一级getter方法
   * @param g2
   *      第二级getter方法
   * @return 此建造者实例，用于方法链调用
   */
  public <P, R> ComposedCriterionBuilder<T> isNull(final GetterMethod<T, P> g1,
      final GetterMethod<P, R> g2) {
    final String path = getPropertyPath(entityClass, g1, g2);
    criteria.add(new SimpleCriterion<>(entityClass, path, EQUAL, null));
    return this;
  }

  /**
   * 添加一个检查通过三个getter方法访问的嵌套属性是否为null的条件。
   *
   * @param <P1>
   *      第一级属性的类型
   * @param <P2>
   *      第二级属性的类型
   * @param <R>
   *      第三个getter方法的返回类型
   * @param g1
   *      第一级getter方法
   * @param g2
   *      第二级getter方法
   * @param g3
   *      第三级getter方法
   * @return 此建造者实例，用于方法链调用
   */
  public <P1, P2, R> ComposedCriterionBuilder<T> isNull(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, R> g3) {
    final String path = getPropertyPath(entityClass, g1, g2, g3);
    criteria.add(new SimpleCriterion<>(entityClass, path, EQUAL, null));
    return this;
  }

  /**
   * 添加一个检查通过四个getter方法访问的嵌套属性是否为null的条件。
   *
   * @param <P1>
   *      第一级属性的类型
   * @param <P2>
   *      第二级属性的类型
   * @param <P3>
   *      第三级属性的类型
   * @param <R>
   *      第四个getter方法的返回类型
   * @param g1
   *      第一级getter方法
   * @param g2
   *      第二级getter方法
   * @param g3
   *      第三级getter方法
   * @param g4
   *      第四级getter方法
   * @return 此建造者实例，用于方法链调用
   */
  public <P1, P2, P3, R> ComposedCriterionBuilder<T> isNull(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, R> g4) {
    final String path = getPropertyPath(entityClass, g1, g2, g3, g4);
    criteria.add(new SimpleCriterion<>(entityClass, path, EQUAL, null));
    return this;
  }

  /**
   * 添加一个检查通过五个getter方法访问的嵌套属性是否为null的条件。
   *
   * @param <P1>
   *      第一级属性的类型
   * @param <P2>
   *      第二级属性的类型
   * @param <P3>
   *      第三级属性的类型
   * @param <P4>
   *      第四级属性的类型
   * @param <R>
   *      第五个getter方法的返回类型
   * @param g1
   *      第一级getter方法
   * @param g2
   *      第二级getter方法
   * @param g3
   *      第三级getter方法
   * @param g4
   *      第四级getter方法
   * @param g5
   *      第五级getter方法
   * @return 此建造者实例，用于方法链调用
   */
  public <P1, P2, P3, P4, R> ComposedCriterionBuilder<T> isNull(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, P4> g4, final GetterMethod<P4, R> g5) {
    final String path = getPropertyPath(entityClass, g1, g2, g3, g4, g5);
    criteria.add(new SimpleCriterion<>(entityClass, path, EQUAL, null));
    return this;
  }

  /**
   * 添加一个条件，检查指定的属性是否不为空。
   *
   * @param path
   *      要检查的属性路径
   * @return 此构建器实例，用于方法链式调用
   */
  public ComposedCriterionBuilder<T> isNotNull(final String path) {
    criteria.add(new SimpleCriterion<>(entityClass, path, NOT_EQUAL, null));
    return this;
  }

  /**
   * 添加一个条件，检查通过 getter 方法访问的属性是否不为空。
   *
   * @param <R>
   *      getter 方法的返回类型
   * @param getter
   *      用于访问属性的 getter 方法引用
   * @return 此构建器实例，用于方法链式调用
   */
  public <R> ComposedCriterionBuilder<T> isNotNull(final GetterMethod<T, R> getter) {
    final String path = getPropertyPath(entityClass, getter);
    criteria.add(new SimpleCriterion<>(entityClass, path, NOT_EQUAL, null));
    return this;
  }

  /**
   * 添加一个条件，检查通过两个 getter 方法访问的嵌套属性是否不为空。
   *
   * @param <P>
   *      第一级属性的类型
   * @param <R>
   *      第二个 getter 方法的返回类型
   * @param g1
   *      第一级 getter 方法
   * @param g2
   *      第二级 getter 方法
   * @return 此构建器实例，用于方法链式调用
   */
  public <P, R> ComposedCriterionBuilder<T> isNotNull(final GetterMethod<T, P> g1,
      final GetterMethod<P, R> g2) {
    final String path = getPropertyPath(entityClass, g1, g2);
    criteria.add(new SimpleCriterion<>(entityClass, path, NOT_EQUAL, null));
    return this;
  }

  /**
   * 添加一个条件，检查通过三个 getter 方法访问的嵌套属性是否不为空。
   *
   * @param <P1>
   *      第一级属性的类型
   * @param <P2>
   *      第二级属性的类型
   * @param <R>
   *      第三个 getter 方法的返回类型
   * @param g1
   *      第一级 getter 方法
   * @param g2
   *      第二级 getter 方法
   * @param g3
   *      第三级 getter 方法
   * @return 此构建器实例，用于方法链式调用
   */
  public <P1, P2, R> ComposedCriterionBuilder<T> isNotNull(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, R> g3) {
    final String path = getPropertyPath(entityClass, g1, g2, g3);
    criteria.add(new SimpleCriterion<>(entityClass, path, NOT_EQUAL, null));
    return this;
  }

  /**
   * 添加一个条件，检查通过四个 getter 方法访问的嵌套属性是否不为空。
   *
   * @param <P1>
   *      第一级属性的类型
   * @param <P2>
   *      第二级属性的类型
   * @param <P3>
   *      第三级属性的类型
   * @param <R>
   *      第四个 getter 方法的返回类型
   * @param g1
   *      第一级 getter 方法
   * @param g2
   *      第二级 getter 方法
   * @param g3
   *      第三级 getter 方法
   * @param g4
   *      第四级 getter 方法
   * @return 此构建器实例，用于方法链式调用
   */
  public <P1, P2, P3, R> ComposedCriterionBuilder<T> isNotNull(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, R> g4) {
    final String path = getPropertyPath(entityClass, g1, g2, g3, g4);
    criteria.add(new SimpleCriterion<>(entityClass, path, NOT_EQUAL, null));
    return this;
  }

  /**
   * 添加一个条件，检查通过五个 getter 方法访问的嵌套属性是否不为空。
   *
   * @param <P1>
   *      第一级属性的类型
   * @param <P2>
   *      第二级属性的类型
   * @param <P3>
   *      第三级属性的类型
   * @param <P4>
   *      第四级属性的类型
   * @param <R>
   *      第五个 getter 方法的返回类型
   * @param g1
   *      第一级 getter 方法
   * @param g2
   *      第二级 getter 方法
   * @param g3
   *      第三级 getter 方法
   * @param g4
   *      第四级 getter 方法
   * @param g5
   *      第五级 getter 方法
   * @return 此构建器实例，用于方法链式调用
   */
  public <P1, P2, P3, P4, R> ComposedCriterionBuilder<T> isNotNull(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, P4> g4, final GetterMethod<P4, R> g5) {
    final String path = getPropertyPath(entityClass, g1, g2, g3, g4, g5);
    criteria.add(new SimpleCriterion<>(entityClass, path, NOT_EQUAL, null));
    return this;
  }

  /**
   * 根据提供的标志，添加一个条件来检查指定的属性是否为空或不为空。
   *
   * <p>如果提供的标志为 {@code null}，则不会添加任何条件。</p>
   *
   * @param path
   *      要检查的属性路径
   * @param isNull
   *      如果为 {@code true}，检查属性是否为空；如果为 {@code false}，
   *      检查属性是否不为空。
   * @return 此构建器实例，用于方法链式调用
   */
  public ComposedCriterionBuilder<T> requireNull(final String path,
      @Nullable final Boolean isNull) {
    if (isNull != null) {
      criteria.add(new SimpleCriterion<>(entityClass, path,
          (isNull ? EQUAL : NOT_EQUAL), null));
    }
    return this;
  }

  /**
   * 根据提供的标志，添加一个条件来检查通过 getter 方法访问的属性是否为空或不为空。
   *
   * <p>如果提供的标志为 {@code null}，则不会添加任何条件。</p>
   *
   * @param <R>
   *      getter 方法的返回类型
   * @param getter
   *      用于访问属性的 getter 方法引用
   * @param isNull
   *      如果为 {@code true}，检查属性是否为空；如果为 {@code false}，
   * 检查属性是否不为空
   * @return 此构建器实例，用于方法链式调用
   */
  public <R> ComposedCriterionBuilder<T> requireNull(final GetterMethod<T, R> getter,
      @Nullable final Boolean isNull) {
    if (isNull != null) {
      final String path = getPropertyPath(entityClass, getter);
      criteria.add(new SimpleCriterion<>(entityClass, path,
          (isNull ? EQUAL : NOT_EQUAL), null));
    }
    return this;
  }

  /**
   * 添加一个条件，检查通过两个getter方法访问的嵌套属性
   * 是否为null或不为null，基于提供的标志。
   *
   * <p>如果提供的标志为{@code null}，则不会添加任何条件。</p>
   *
   * @param <P>
   *      第一级属性的类型
   * @param <R>
   *      第二个getter方法的返回类型
   * @param g1
   *      第一级getter方法
   * @param g2
   *      第二级getter方法
   * @param isNull
   *      如果为{@code true}，检查属性是否为null；如果为{@code false}，
   *      检查属性是否不为null。
   * @return 此建造者实例，用于方法链调用
   */
  public <P, R> ComposedCriterionBuilder<T> requireNull(final GetterMethod<T, P> g1,
      final GetterMethod<P, R> g2, @Nullable final Boolean isNull) {
    if (isNull != null) {
      final String path = getPropertyPath(entityClass, g1, g2);
      criteria.add(new SimpleCriterion<>(entityClass, path,
          (isNull ? EQUAL : NOT_EQUAL), null));
    }
    return this;
  }

  /**
   * 添加一个条件，检查通过三个getter方法访问的嵌套属性
   * 是否为null或不为null，基于提供的标志。
   *
   * <p>如果提供的标志为{@code null}，则不会添加任何条件。</p>
   *
   * @param <P1>
   *      第一级属性的类型
   * @param <P2>
   *      第二级属性的类型
   * @param <R>
   *      第三个getter方法的返回类型
   * @param g1
   *      第一级getter方法
   * @param g2
   *      第二级getter方法
   * @param g3
   *      第三级getter方法
   * @param isNull
   *      如果为{@code true}，检查属性是否为null；如果为{@code false}，
   *      检查属性是否不为null。
   * @return 此建造者实例，用于方法链调用
   */
  public <P1, P2, R> ComposedCriterionBuilder<T> requireNull(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, R> g3,
      @Nullable final Boolean isNull) {
    if (isNull != null) {
      criteria.add(new SimpleCriterion<>(entityClass, getPropertyPath(entityClass, g1, g2, g3),
          (isNull ? EQUAL : NOT_EQUAL), null));
    }
    return this;
  }

  /**
   * 添加一个条件，检查通过四个getter方法访问的嵌套属性
   * 是否为null或不为null，基于提供的标志。
   *
   * <p>如果提供的标志为{@code null}，则不会添加任何条件。</p>
   *
   * @param <P1>
   *      第一级属性的类型
   * @param <P2>
   *      第二级属性的类型
   * @param <P3>
   *      第三级属性的类型
   * @param <R>
   *      第四个getter方法的返回类型
   * @param g1
   *      第一级getter方法
   * @param g2
   *      第二级getter方法
   * @param g3
   *      第三级getter方法
   * @param g4
   *      第四级getter方法
   * @param isNull
   *      如果为{@code true}，检查属性是否为null；如果为{@code false}，
   *      检查属性是否不为null。
   * @return 此建造者实例，用于方法链调用
   */
  public <P1, P2, P3, R> ComposedCriterionBuilder<T> requireNull(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, R> g4, @Nullable final Boolean isNull) {
    if (isNull != null) {
      final String path = getPropertyPath(entityClass, g1, g2, g3, g4);
      criteria.add(new SimpleCriterion<>(entityClass, path, (isNull ? EQUAL : NOT_EQUAL), null));
    }
    return this;
  }

  /**
   * 添加一个条件，检查通过五个getter方法访问的嵌套属性
   * 是否为null或不为null，基于提供的标志。
   *
   * <p>如果提供的标志为{@code null}，则不会添加任何条件。</p>
   *
   * @param <P1>
   *      第一级属性的类型
   * @param <P2>
   *      第二级属性的类型
   * @param <P3>
   *      第三级属性的类型
   * @param <P4>
   *      第四级属性的类型
   * @param <R>
   *      第五个getter方法的返回类型
   * @param g1
   *      第一级getter方法
   * @param g2
   *      第二级getter方法
   * @param g3
   *      第三级getter方法
   * @param g4
   *      第四级getter方法
   * @param g5
   *      第五级getter方法
   * @param isNull
   *      如果为{@code true}，检查属性是否为null；如果为{@code false}，
   *      检查属性是否不为null
   * @return 此建造者实例，用于方法链调用
   */
  public <P1, P2, P3, P4, R> ComposedCriterionBuilder<T> requireNull(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, P4> g4, final GetterMethod<P4, R> g5,
      @Nullable final Boolean isNull) {
    if (isNull != null) {
      final String path = getPropertyPath(entityClass, g1, g2, g3, g4, g5);
      criteria.add(new SimpleCriterion<>(entityClass, path,
          (isNull ? EQUAL : NOT_EQUAL), null));
    }
    return this;
  }

  /**
   * 添加一个条件，检查指定属性是否不为null，基于提供的标志。
   *
   * <p>如果提供的标志为{@code null}，则不会添加任何条件。</p>
   *
   * @param path
   *      要检查的属性路径
   * @param isNotNull
   *      如果为{@code true}，检查属性是否不为null；如果为{@code false}，
   *      检查属性是否为null。
   * @return 此建造者实例，用于方法链调用
   */
  public ComposedCriterionBuilder<T> requireNotNull(final String path,
      @Nullable final Boolean isNotNull) {
    if (isNotNull != null) {
      criteria.add(new SimpleCriterion<>(entityClass, path,
          (isNotNull ? NOT_EQUAL : EQUAL), null));
    }
    return this;
  }

  /**
   * 添加一个条件，检查通过getter方法访问的属性是否不为null，基于提供的标志。
   *
   * <p>如果提供的标志为{@code null}，则不会添加任何条件。
   *
   * @param <R>
   *      getter方法的返回类型
   * @param getter
   *      用于访问属性的getter方法引用
   * @param isNotNull
   *      如果为{@code true}，检查属性是否不为null；如果为{@code false}，
   *      检查属性是否为null
   * @return 此建造者实例，用于方法链调用
   */
  public <R> ComposedCriterionBuilder<T> requireNotNull(final GetterMethod<T, R> getter,
      @Nullable final Boolean isNotNull) {
    if (isNotNull != null) {
      final String path = getPropertyPath(entityClass, getter);
      criteria.add(new SimpleCriterion<>(entityClass, path,
          (isNotNull ? NOT_EQUAL : EQUAL), null));
    }
    return this;
  }

  /**
   * 添加一个条件，检查通过两个getter方法访问的嵌套属性
   * 是否为非null值（基于提供的标志）。
   *
   * <p>如果提供的标志为{@code null}，则不会添加任何条件。</p>
   *
   * @param <P>
   *      第一级属性的类型
   * @param <R>
   *      第二个getter方法的返回类型
   * @param g1
   *      第一级getter方法
   * @param g2
   *      第二级getter方法
   * @param isNotNull
   *      如果为{@code true}，检查属性是否不为null；如果为{@code false}，
   *      检查属性是否为null
   * @return 此建造者实例，用于方法链调用
   */
  public <P, R> ComposedCriterionBuilder<T> requireNotNull(final GetterMethod<T, P> g1,
      final GetterMethod<P, R> g2, @Nullable final Boolean isNotNull) {
    if (isNotNull != null) {
      final String path = getPropertyPath(entityClass, g1, g2);
      criteria.add(new SimpleCriterion<>(entityClass, path,
          (isNotNull ? NOT_EQUAL : EQUAL), null));
    }
    return this;
  }

  /**
   * 添加一个条件，检查通过三个getter方法访问的嵌套属性
   * 是否为非null值（基于提供的标志）。
   *
   * <p>如果提供的标志为{@code null}，则不会添加任何条件。</p>
   *
   * @param <P1>
   *      第一级属性的类型
   * @param <P2>
   *      第二级属性的类型
   * @param <R>
   *      第三个getter方法的返回类型
   * @param g1
   *      第一级getter方法
   * @param g2
   *      第二级getter方法
   * @param g3
   *      第三级getter方法
   * @param isNotNull
   *      如果为{@code true}，检查属性是否不为null；如果为{@code false}，
   *      检查属性是否为null
   * @return 此建造者实例，用于方法链调用
   */
  public <P1, P2, R> ComposedCriterionBuilder<T> requireNotNull(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, R> g3,
      @Nullable final Boolean isNotNull) {
    if (isNotNull != null) {
      final String path = getPropertyPath(entityClass, g1, g2, g3);
      criteria.add(new SimpleCriterion<>(entityClass, path,
          (isNotNull ? NOT_EQUAL : EQUAL), null));
    }
    return this;
  }

  /**
   * 添加一个条件，检查通过四个getter方法访问的嵌套属性
   * 是否为非null值（基于提供的标志）。
   *
   * <p>如果提供的标志为{@code null}，则不会添加任何条件。</p>
   *
   * @param <P1>
   *      第一级属性的类型
   * @param <P2>
   *      第二级属性的类型
   * @param <P3>
   *      第三级属性的类型
   * @param <R>
   *      第四个getter方法的返回类型
   * @param g1
   *      第一级getter方法
   * @param g2
   *      第二级getter方法
   * @param g3
   *      第三级getter方法
   * @param g4
   *      第四级getter方法
   * @param isNotNull
   *      如果为{@code true}，检查属性是否不为null；如果为{@code false}，
   *      检查属性是否为null
   * @return 此建造者实例，用于方法链调用
   */
  public <P1, P2, P3, R> ComposedCriterionBuilder<T> requireNotNull(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, R> g4, @Nullable final Boolean isNotNull) {
    if (isNotNull != null) {
      final String path = getPropertyPath(entityClass, g1, g2, g3, g4);
      criteria.add(new SimpleCriterion<>(entityClass, path,
          (isNotNull ? NOT_EQUAL : EQUAL), null));
    }
    return this;
  }

  /**
   * 添加一个条件，检查通过五个getter方法访问的嵌套属性
   * 是否为非null值（基于提供的标志）。
   *
   * <p>如果提供的标志为{@code null}，则不会添加任何条件。</p>
   *
   * @param <P1>
   *      第一级属性的类型
   * @param <P2>
   *      第二级属性的类型
   * @param <P3>
   *      第三级属性的类型
   * @param <P4>
   *      第四级属性的类型
   * @param <R>
   *      第五个getter方法的返回类型
   * @param g1
   *      第一级getter方法
   * @param g2
   *      第二级getter方法
   * @param g3
   *      第三级getter方法
   * @param g4
   *      第四级getter方法
   * @param g5
   *      第五级getter方法
   * @param isNotNull
   *      如果为{@code true}，检查属性是否不为null；如果为{@code false}，
   *      检查属性是否为null
   * @return 此建造者实例，用于方法链调用
   */
  public <P1, P2, P3, P4, R> ComposedCriterionBuilder<T> requireNotNull(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, P4> g4, final GetterMethod<P4, R> g5,
      @Nullable final Boolean isNotNull) {
    if (isNotNull != null) {
      final String path = getPropertyPath(entityClass, g1, g2, g3, g4, g5);
      criteria.add(new SimpleCriterion<>(entityClass, path,
          (isNotNull ? NOT_EQUAL : EQUAL), null));
    }
    return this;
  }

  /**
   * 添加一个条件，检查指定的属性是否等于给定的值。
   *
   * <p>如果提供的值为 {@code null}，则不会添加任何条件。</p>
   *
   * @param <R>
   *      要比较的值的类型
   * @param path
   *      要检查的属性路径
   * @param value
   *      要比较的值，可以为 {@code null}
   * @return 此构建器实例，用于方法链式调用
   */
  public <R> ComposedCriterionBuilder<T> equal(final String path, @Nullable final R value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(entityClass, path, EQUAL, value));
    }
    return this;
  }

  /**
   * 添加一个条件，检查通过 getter 方法访问的属性是否等于给定的值。
   *
   * <p>如果提供的值为 {@code null}，则不会添加任何条件。</p>
   *
   * @param <R>
   *      属性和值的类型
   * @param getter
   *      用于访问属性的 getter 方法引用
   * @param value
   *      要比较的值，可以为 {@code null}
   * @return 此构建器实例，用于方法链式调用
   */
  public <R> ComposedCriterionBuilder<T> equal(final GetterMethod<T, R> getter,
      @Nullable final R value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, getter);
      criteria.add(new SimpleCriterion<>(entityClass, path, EQUAL, value));
    }
    return this;
  }

  /**
   * 添加一个条件，检查通过两个 getter 方法访问的嵌套属性是否等于给定的值。
   *
   * <p>如果提供的值为 {@code null}，则不会添加任何条件。</p>
   *
   * @param <P>
   *      第一级属性的类型
   * @param <R>
   *      要比较的值的类型
   * @param g1
   *      第一级 getter 方法
   * @param g2
   *      第二级 getter 方法
   * @param value
   *      要比较的值，可以为 {@code null}
   * @return 此构建器实例，用于方法链式调用
   */
  public <P, R> ComposedCriterionBuilder<T> equal(final GetterMethod<T, P> g1,
      final GetterMethod<P, R> g2, @Nullable final R value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, g1, g2);
      criteria.add(new SimpleCriterion<>(entityClass, path, EQUAL, value));
    }
    return this;
  }

  /**
   * 添加一个条件，检查通过三个 getter 方法访问的嵌套属性是否等于给定的值。
   *
   * <p>如果提供的值为 {@code null}，则不会添加任何条件。</p>
   *
   * @param <P1>
   *      第一级属性的类型
   * @param <P2>
   *      第二级属性的类型
   * @param <R>
   *      要比较的值的类型
   * @param g1
   *      第一级 getter 方法
   * @param g2
   *      第二级 getter 方法
   * @param g3
   *      第三级 getter 方法
   * @param value
   *      要比较的值，可以为 {@code null}
   * @return 此构建器实例，用于方法链式调用
   */
  public <P1, P2, R> ComposedCriterionBuilder<T> equal(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, R> g3,
      @Nullable final R value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, g1, g2, g3);
      criteria.add(new SimpleCriterion<>(entityClass, path, EQUAL, value));
    }
    return this;
  }

  /**
   * 添加一个条件，检查通过四个 getter 方法访问的嵌套属性是否等于给定的值。
   *
   * <p>如果提供的值为 {@code null}，则不会添加任何条件。</p>
   *
   * @param <P1>
   *      第一级属性的类型
   * @param <P2>
   *      第二级属性的类型
   * @param <P3>
   *      第三级属性的类型
   * @param <R>
   *      要比较的值的类型
   * @param g1
   *      第一级 getter 方法
   * @param g2
   *      第二级 getter 方法
   * @param g3
   *      第三级 getter 方法
   * @param g4
   *      第四级 getter 方法
   * @param value
   *      要比较的值，可以为 {@code null}
   * @return 此构建器实例，用于方法链式调用
   */
  public <P1, P2, P3, R> ComposedCriterionBuilder<T> equal(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, R> g4, @Nullable final R value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, g1, g2, g3, g4);
      criteria.add(new SimpleCriterion<>(entityClass, path, EQUAL, value));
    }
    return this;
  }

  /**
   * 添加一个条件，检查通过五个 getter 方法访问的嵌套属性是否等于给定的值。
   *
   * <p>如果提供的值为 {@code null}，则不会添加任何条件。</p>
   *
   * @param <P1>
   *      第一级属性的类型
   * @param <P2>
   *      第二级属性的类型
   * @param <P3>
   *      第三级属性的类型
   * @param <P4>
   *      第四级属性的类型
   * @param <R>
   *      要比较的值的类型
   * @param g1
   *      第一级 getter 方法
   * @param g2
   *      第二级 getter 方法
   * @param g3
   *      第三级 getter 方法
   * @param g4
   *      第四级 getter 方法
   * @param g5
   *      第五级 getter 方法
   * @param value
   *      要比较的值，可以为 {@code null}
   * @return 此构建器实例，用于方法链式调用
   */
  public <P1, P2, P3, P4, R> ComposedCriterionBuilder<T> equal(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, P4> g4, final GetterMethod<P4, R> g5,
      @Nullable final R value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, g1, g2, g3, g4, g5);
      criteria.add(new SimpleCriterion<>(entityClass, path, EQUAL, value));
    }
    return this;
  }

  /**
   * 添加一个条件，检查指定的属性是否不等于给定的值。
   *
   * <p>如果提供的值为 {@code null}，则不会添加任何条件。</p>
   *
   * @param <R>
   *      要比较的值的类型
   * @param path
   *      要检查的属性路径
   * @param value
   *      要比较的值，可以为 {@code null}
   * @return 此构建器实例，用于方法链式调用
   */
  public <R> ComposedCriterionBuilder<T> notEqual(final String path,
      @Nullable final R value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(entityClass, path, NOT_EQUAL, value));
    }
    return this;
  }

  /**
   * 添加一个条件，检查通过 getter 方法访问的属性是否不等于给定的值。
   *
   * <p>如果提供的值为 {@code null}，则不会添加任何条件。</p>
   *
   * @param <R>
   *      属性和值的类型
   * @param getter
   *      用于访问属性的 getter 方法引用
   * @param value
   *      要比较的值，可以为 {@code null}
   * @return 此构建器实例，用于方法链式调用
   */
  public <R> ComposedCriterionBuilder<T> notEqual(final GetterMethod<T, R> getter,
      @Nullable final R value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, getter);
      criteria.add(new SimpleCriterion<>(entityClass, path, NOT_EQUAL, value));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested property accessed by two getter
   * methods is not equal to the given value.
   *
   * <p>No criterion will be added if the provided value is {@code null}.</p>
   *
   * @param <P>
   *      the type of the first level property
   * @param <R>
   *      the type of the value to compare with
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method
   * @param value
   *      the value to compare with, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <P, R> ComposedCriterionBuilder<T> notEqual(final GetterMethod<T, P> g1,
      final GetterMethod<P, R> g2, @Nullable final R value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, g1, g2);
      criteria.add(new SimpleCriterion<>(entityClass, path, NOT_EQUAL, value));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested property accessed by three getter
   * methods is not equal to the given value.
   *
   * <p>No criterion will be added if the provided value is {@code null}.</p>
   *
   * @param <P1>
   *      the type of the first level property
   * @param <P2>
   *      the type of the second level property
   * @param <R>
   *      the type of the value to compare with
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method
   * @param g3
   *      the third level getter method
   * @param value
   *      the value to compare with, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <P1, P2, R> ComposedCriterionBuilder<T> notEqual(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, R> g3, @Nullable final R value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, g1, g2, g3);
      criteria.add(new SimpleCriterion<>(entityClass, path, NOT_EQUAL, value));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested property accessed by four getter
   * methods is not equal to the given value.
   *
   * <p>No criterion will be added if the provided value is {@code null}.</p>
   *
   * @param <P1>
   *      the type of the first level property
   * @param <P2>
   *      the type of the second level property
   * @param <P3>
   *      the type of the third level property
   * @param <R>
   *      the type of the value to compare with
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method
   * @param g3
   *      the third level getter method
   * @param g4
   *      the fourth level getter method
   * @param value
   *      the value to compare with, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <P1, P2, P3, R> ComposedCriterionBuilder<T> notEqual(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, R> g4, @Nullable final R value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, g1, g2, g3, g4);
      criteria.add(new SimpleCriterion<>(entityClass, path, NOT_EQUAL, value));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested property accessed by five getter
   * methods is not equal to the given value.
   *
   * <p>No criterion will be added if the provided value is {@code null}.</p>
   *
   * @param <P1>
   *      the type of the first level property
   * @param <P2>
   *      the type of the second level property
   * @param <P3>
   *      the type of the third level property
   * @param <P4>
   *      the type of the fourth level property
   * @param <R>
   *      the type of the value to compare with
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method
   * @param g3
   *      the third level getter method
   * @param g4
   *      the fourth level getter method
   * @param g5
   *      the fifth level getter method
   * @param value
   *      the value to compare with, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <P1, P2, P3, P4, R> ComposedCriterionBuilder<T> notEqual(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, P4> g4, final GetterMethod<P4, R> g5,
      @Nullable final R value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, g1, g2, g3, g4, g5);
      criteria.add(new SimpleCriterion<>(entityClass, path, NOT_EQUAL, value));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if the specified property is less than the
   * given value.
   *
   * <p>No criterion will be added if the provided value is {@code null}.</p>
   *
   * @param <R>
   *      the type of the value to compare with
   * @param path
   *      the path of the property to check
   * @param value
   *      the value to compare with, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <R> ComposedCriterionBuilder<T> less(final String path,
      @Nullable final R value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(entityClass, path, LESS, value));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if the property accessed by the getter method
   * is less than the given value.
   *
   * <p>No criterion will be added if the provided value is {@code null}.</p>
   *
   * @param <R>
   *      the type of the property and value
   * @param getter
   *      the getter method reference to access the property
   * @param value
   *      the value to compare with, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <R> ComposedCriterionBuilder<T> less(final GetterMethod<T, R> getter,
      @Nullable final R value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, getter);
      criteria.add(new SimpleCriterion<>(entityClass, path, LESS, value));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested property accessed by two getter
   * methods is less than the given value.
   *
   * <p>No criterion will be added if the provided value is {@code null}.</p>
   *
   * @param <P>
   *      the type of the first level property
   * @param <R>
   *      the type of the value to compare with
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method
   * @param value
   *      the value to compare with, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <P, R> ComposedCriterionBuilder<T> less(final GetterMethod<T, P> g1,
      final GetterMethod<P, R> g2, @Nullable final R value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, g1, g2);
      criteria.add(new SimpleCriterion<>(entityClass, path, LESS, value));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested property accessed by three getter
   * methods is less than the given value.
   *
   * <p>No criterion will be added if the provided value is {@code null}.</p>
   *
   * @param <P1>
   *      the type of the first level property
   * @param <P2>
   *      the type of the second level property
   * @param <R>
   *      the type of the value to compare with
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method
   * @param g3
   *      the third level getter method
   * @param value
   *      the value to compare with, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <P1, P2, R> ComposedCriterionBuilder<T> less(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, R> g3,
      @Nullable final R value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, g1, g2, g3);
      criteria.add(new SimpleCriterion<>(entityClass, path, LESS, value));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested property accessed by four getter
   * methods is less than the given value.
   *
   * <p>No criterion will be added if the provided value is {@code null}.</p>
   *
   * @param <P1>
   *      the type of the first level property
   * @param <P2>
   *      the type of the second level property
   * @param <P3>
   *      the type of the third level property
   * @param <R>
   *      the type of the value to compare with
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method
   * @param g3
   *      the third level getter method
   * @param g4
   *      the fourth level getter method
   * @param value
   *      the value to compare with, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <P1, P2, P3, R> ComposedCriterionBuilder<T> less(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, R> g4, @Nullable final R value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, g1, g2, g3, g4);
      criteria.add(new SimpleCriterion<>(entityClass, path, LESS, value));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested property accessed by five getter
   * methods is less than the given value.
   *
   * <p>No criterion will be added if the provided value is {@code null}.</p>
   *
   * @param <P1>
   *      the type of the first level property
   * @param <P2>
   *      the type of the second level property
   * @param <P3>
   *      the type of the third level property
   * @param <P4>
   *      the type of the fourth level property
   * @param <R>
   *      the type of the value to compare with
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method
   * @param g3
   *      the third level getter method
   * @param g4
   *      the fourth level getter method
   * @param g5
   *      the fifth level getter method
   * @param value
   *      the value to compare with, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <P1, P2, P3, P4, R> ComposedCriterionBuilder<T> less(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, P4> g4, final GetterMethod<P4, R> g5,
      @Nullable final R value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, g1, g2, g3, g4, g5);
      criteria.add(new SimpleCriterion<>(entityClass, path, LESS, value));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if the specified property is less than or
   * equal to the given value.
   *
   * <p>No criterion will be added if the provided value is {@code null}.</p>
   *
   * @param <R>
   *      the type of the value to compare with
   * @param path
   *      the path of the property to check
   * @param value
   *      the value to compare with, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <R> ComposedCriterionBuilder<T> lessEqual(final String path,
      @Nullable final R value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(entityClass, path, LESS_EQUAL, value));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if the property accessed by the getter method
   * is less than or equal to the given value.
   *
   * <p>No criterion will be added if the provided value is {@code null}.</p>
   *
   * @param <R>
   *      the type of the property and value
   * @param getter
   *      the getter method reference to access the property
   * @param value
   *      the value to compare with, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <R> ComposedCriterionBuilder<T> lessEqual(final GetterMethod<T, R> getter,
      @Nullable final R value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, getter);
      criteria.add(new SimpleCriterion<>(entityClass, path, LESS_EQUAL, value));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested property accessed by two getter
   * methods is less than or equal to the given value.
   *
   * <p>No criterion will be added if the provided value is {@code null}.</p>
   *
   * @param <P>
   *      the type of the first level property
   * @param <R>
   *      the type of the value to compare with
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method
   * @param value
   *      the value to compare with, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <P, R> ComposedCriterionBuilder<T> lessEqual(final GetterMethod<T, P> g1,
      final GetterMethod<P, R> g2, @Nullable final R value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, g1, g2);
      criteria.add(new SimpleCriterion<>(entityClass, path, LESS_EQUAL, value));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested property accessed by three getter
   * methods is less than or equal to the given value.
   *
   * <p>No criterion will be added if the provided value is {@code null}.</p>
   *
   * @param <P1>
   *      the type of the first level property
   * @param <P2>
   *      the type of the second level property
   * @param <R>
   *      the type of the value to compare with
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method
   * @param g3
   *      the third level getter method
   * @param value
   *      the value to compare with, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <P1, P2, R> ComposedCriterionBuilder<T> lessEqual(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, R> g3, @Nullable final R value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, g1, g2, g3);
      criteria.add(new SimpleCriterion<>(entityClass, path, LESS_EQUAL, value));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested property accessed by four getter
   * methods is less than or equal to the given value.
   *
   * <p>No criterion will be added if the provided value is {@code null}.</p>
   *
   * @param <P1>
   *      the type of the first level property
   * @param <P2>
   *      the type of the second level property
   * @param <P3>
   *      the type of the third level property
   * @param <R>
   *      the type of the value to compare with
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method
   * @param g3
   *      the third level getter method
   * @param g4
   *      the fourth level getter method
   * @param value
   *      the value to compare with, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <P1, P2, P3, R> ComposedCriterionBuilder<T> lessEqual(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, R> g4, @Nullable final R value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, g1, g2, g3, g4);
      criteria.add(new SimpleCriterion<>(entityClass, path, LESS_EQUAL, value));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested property accessed by five getter
   * methods is less than or equal to the given value.
   *
   * <p>No criterion will be added if the provided value is {@code null}.</p>
   *
   * @param <P1>
   *      the type of the first level property
   * @param <P2>
   *      the type of the second level property
   * @param <P3>
   *      the type of the third level property
   * @param <P4>
   *      the type of the fourth level property
   * @param <R>
   *      the type of the value to compare with
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method
   * @param g3
   *      the third level getter method
   * @param g4
   *      the fourth level getter method
   * @param g5
   *      the fifth level getter method
   * @param value
   *      the value to compare with, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <P1, P2, P3, P4, R> ComposedCriterionBuilder<T> lessEqual(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, P4> g4, final GetterMethod<P4, R> g5,
      @Nullable final R value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, g1, g2, g3, g4, g5);
      criteria.add(new SimpleCriterion<>(entityClass, path, LESS_EQUAL, value));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if the specified property is greater than the
   * given value.
   *
   * <p>No criterion will be added if the provided value is {@code null}.</p>
   *
   * @param <R>
   *      the type of the value to compare with
   * @param path
   *      the path of the property to check
   * @param value
   *      the value to compare with, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <R> ComposedCriterionBuilder<T> greater(final String path,
      @Nullable final R value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(entityClass, path, GREATER, value));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if the property accessed by the getter method
   * is greater than the given value.
   *
   * <p>No criterion will be added if the provided value is {@code null}.</p>
   *
   * @param <R>
   *      the type of the property and value
   * @param getter
   *      the getter method reference to access the property
   * @param value
   *      the value to compare with, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <R> ComposedCriterionBuilder<T> greater(final GetterMethod<T, R> getter,
      @Nullable final R value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, getter);
      criteria.add(new SimpleCriterion<>(entityClass, path, GREATER, value));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested property accessed by two getter
   * methods is greater than the given value.
   *
   * <p>No criterion will be added if the provided value is {@code null}.</p>
   *
   * @param <P>
   *      the type of the first level property
   * @param <R>
   *      the type of the value to compare with
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method
   * @param value
   *      the value to compare with, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <P, R> ComposedCriterionBuilder<T> greater(final GetterMethod<T, P> g1,
      final GetterMethod<P, R> g2, @Nullable final R value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, g1, g2);
      criteria.add(new SimpleCriterion<>(entityClass, path, GREATER, value));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested property accessed by three getter
   * methods is greater than the given value.
   *
   * <p>No criterion will be added if the provided value is {@code null}.</p>
   *
   * @param <P1>
   *      the type of the first level property
   * @param <P2>
   *      the type of the second level property
   * @param <R>
   *      the type of the value to compare with
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method
   * @param g3
   *      the third level getter method
   * @param value
   *      the value to compare with, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <P1, P2, R> ComposedCriterionBuilder<T> greater(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, R> g3, @Nullable final R value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, g1, g2, g3);
      criteria.add(new SimpleCriterion<>(entityClass, path, GREATER, value));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested property accessed by four getter
   * methods is greater than the given value.
   *
   * <p>No criterion will be added if the provided value is {@code null}.</p>
   *
   * @param <P1>
   *      the type of the first level property
   * @param <P2>
   *      the type of the second level property
   * @param <P3>
   *      the type of the third level property
   * @param <R>
   *      the type of the value to compare with
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method
   * @param g3
   *      the third level getter method
   * @param g4
   *      the fourth level getter method
   * @param value
   *      the value to compare with, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <P1, P2, P3, R> ComposedCriterionBuilder<T> greater(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, R> g4, @Nullable final R value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, g1, g2, g3, g4);
      criteria.add(new SimpleCriterion<>(entityClass, path, GREATER, value));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested property accessed by five getter
   * methods is greater than the given value.
   *
   * <p>No criterion will be added if the provided value is {@code null}.</p>
   *
   * @param <P1>
   *      the type of the first level property
   * @param <P2>
   *      the type of the second level property
   * @param <P3>
   *      the type of the third level property
   * @param <P4>
   *      the type of the fourth level property
   * @param <R>
   *      the type of the value to compare with
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method
   * @param g3
   *      the third level getter method
   * @param g4
   *      the fourth level getter method
   * @param g5
   *      the fifth level getter method
   * @param value
   *      the value to compare with, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <P1, P2, P3, P4, R> ComposedCriterionBuilder<T> greater(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, P4> g4, final GetterMethod<P4, R> g5,
      @Nullable final R value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, g1, g2, g3, g4, g5);
      criteria.add(new SimpleCriterion<>(entityClass, path, GREATER, value));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if the specified property is greater than or
   * equal to the given value.
   *
   * <p>No criterion will be added if the provided value is {@code null}.</p>
   *
   * @param <R>
   *      the type of the value to compare with
   * @param path
   *      the path of the property to check
   * @param value
   *      the value to compare with, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <R> ComposedCriterionBuilder<T> greaterEqual(final String path,
      @Nullable final R value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(entityClass, path, GREATER_EQUAL, value));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if the property accessed by the getter method
   * is greater than or equal to the given value.
   *
   * <p>No criterion will be added if the provided value is {@code null}.</p>
   *
   * @param <R>
   *      the type of the property and value
   * @param getter
   *      the getter method reference to access the property
   * @param value
   *      the value to compare with, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <R> ComposedCriterionBuilder<T> greaterEqual(final GetterMethod<T, R> getter,
      @Nullable final R value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, getter);
      criteria.add(new SimpleCriterion<>(entityClass, path, GREATER_EQUAL, value));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested property accessed by two getter
   * methods is greater than or equal to the given value.
   *
   * <p>No criterion will be added if the provided value is {@code null}.</p>
   *
   * @param <P>
   *      the type of the first level property
   * @param <R>
   *      the type of the value to compare with
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method
   * @param value
   *      the value to compare with, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <P, R> ComposedCriterionBuilder<T> greaterEqual(final GetterMethod<T, P> g1,
      final GetterMethod<P, R> g2, @Nullable final R value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, g1, g2);
      criteria.add(new SimpleCriterion<>(entityClass, path, GREATER_EQUAL, value));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested property accessed by three getter
   * methods is greater than or equal to the given value.
   *
   * <p>No criterion will be added if the provided value is {@code null}.</p>
   *
   * @param <P1>
   *      the type of the first level property
   * @param <P2>
   *      the type of the second level property
   * @param <R>
   *      the type of the value to compare with
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method
   * @param g3
   *      the third level getter method
   * @param value
   *      the value to compare with, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <P1, P2, R> ComposedCriterionBuilder<T> greaterEqual(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, R> g3, @Nullable final R value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, g1, g2, g3);
      criteria.add(new SimpleCriterion<>(entityClass, path, GREATER_EQUAL, value));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested property accessed by four getter
   * methods is greater than or equal to the given value.
   *
   * <p>No criterion will be added if the provided value is {@code null}.</p>
   *
   * @param <P1>
   *      the type of the first level property
   * @param <P2>
   *      the type of the second level property
   * @param <P3>
   *      the type of the third level property
   * @param <R>
   *      the type of the value to compare with
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method
   * @param g3
   *      the third level getter method
   * @param g4
   *      the fourth level getter method
   * @param value
   *      the value to compare with, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <P1, P2, P3, R> ComposedCriterionBuilder<T> greaterEqual(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, R> g4, @Nullable final R value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, g1, g2, g3, g4);
      criteria.add(new SimpleCriterion<>(entityClass, path, GREATER_EQUAL, value));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested property accessed by five getter
   * methods is greater than or equal to the given value.
   *
   * <p>No criterion will be added if the provided value is {@code null}.</p>
   *
   * @param <P1>
   *      the type of the first level property
   * @param <P2>
   *      the type of the second level property
   * @param <P3>
   *      the type of the third level property
   * @param <P4>
   *      the type of the fourth level property
   * @param <R>
   *      the type of the value to compare with
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method
   * @param g3
   *      the third level getter method
   * @param g4
   *      the fourth level getter method
   * @param g5
   *      the fifth level getter method
   * @param value
   *      the value to compare with, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <P1, P2, P3, P4, R> ComposedCriterionBuilder<T> greaterEqual(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, P4> g4, final GetterMethod<P4, R> g5,
      @Nullable final R value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, g1, g2, g3, g4, g5);
      criteria.add(new SimpleCriterion<>(entityClass, path, GREATER_EQUAL, value));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if the specified property is between the given
   * lower and upper bounds.
   *
   * <p>No criterion will be added if the provided lower bound is {@code null}
   * and the upper bound is {@code null}.</p>
   *
   * @param <R>
   *      the type of the value to compare with
   * @param path
   *      the path of the property to check
   * @param lowerBound
   *      the lower bound to compare with, can be {@code null}
   * @param upperBound
   *      the upper bound to compare with, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <R> ComposedCriterionBuilder<T> between(final String path,
      @Nullable final R lowerBound, @Nullable final R upperBound) {
    if (lowerBound != null) {
      criteria.add(new SimpleCriterion<>(entityClass, path, GREATER_EQUAL, lowerBound));
    }
    if (upperBound != null) {
      criteria.add(new SimpleCriterion<>(entityClass, path, LESS_EQUAL, upperBound));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if the property accessed by the getter method
   * is between the given lower and upper bounds.
   *
   * <p>No criterion will be added if the provided lower bound is {@code null}
   * and the upper bound is {@code null}.</p>
   *
   * @param <R>
   *      the type of the property and value
   * @param getter
   *      the getter method reference to access the property
   * @param lowerBound
   *      the lower bound to compare with, can be {@code null}
   * @param upperBound
   *      the upper bound to compare with, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <R> ComposedCriterionBuilder<T> between(final GetterMethod<T, R> getter,
      @Nullable final R lowerBound, @Nullable final R upperBound) {
    final String path =  getPropertyPath(entityClass, getter);
    if (lowerBound != null) {
      criteria.add(new SimpleCriterion<>(entityClass, path, GREATER_EQUAL, lowerBound));
    }
    if (upperBound != null) {
      criteria.add(new SimpleCriterion<>(entityClass, path, LESS_EQUAL, upperBound));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested property accessed by two getter
   * methods is between the given lower and upper bounds.
   *
   * <p>No criterion will be added if the provided lower bound is {@code null}
   * and the upper bound is {@code null}.</p>
   *
   * @param <P>
   *      the type of the first level property
   * @param <R>
   *      the type of the value to compare with
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method
   * @param lowerBound
   *      the lower bound to compare with, can be {@code null}
   * @param upperBound
   *      the upper bound to compare with, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <P, R> ComposedCriterionBuilder<T> between(final GetterMethod<T, P> g1,
      final GetterMethod<P, R> g2, @Nullable final R lowerBound, @Nullable final R upperBound) {
    final String path =  getPropertyPath(entityClass, g1, g2);
    if (lowerBound != null) {
      criteria.add(new SimpleCriterion<>(entityClass, path, GREATER_EQUAL, lowerBound));
    }
    if (upperBound != null) {
      criteria.add(new SimpleCriterion<>(entityClass, path, LESS_EQUAL, upperBound));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested property accessed by three getter
   * methods is between the given lower and upper bounds.
   *
   * <p>No criterion will be added if the provided lower bound is {@code null}
   * and the upper bound is {@code null}.</p>
   *
   * @param <P1>
   *      the type of the first level property
   * @param <P2>
   *      the type of the second level property
   * @param <R>
   *      the type of the value to compare with
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method
   * @param g3
   *      the third level getter method
   * @param lowerBound
   *      the lower bound to compare with, can be {@code null}
   * @param upperBound
   *      the upper bound to compare with, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <P1, P2, R> ComposedCriterionBuilder<T> between(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, R> g3,
      @Nullable final R lowerBound, @Nullable final R upperBound) {
    final String path =  getPropertyPath(entityClass, g1, g2, g3);
    if (lowerBound != null) {
      criteria.add(new SimpleCriterion<>(entityClass, path, GREATER_EQUAL, lowerBound));
    }
    if (upperBound != null) {
      criteria.add(new SimpleCriterion<>(entityClass, path, LESS_EQUAL, upperBound));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested property accessed by four getter
   * methods is between the given lower and upper bounds.
   *
   * <p>No criterion will be added if the provided lower bound is {@code null}
   * and the upper bound is {@code null}.</p>
   *
   * @param <P1>
   *      the type of the first level property
   * @param <P2>
   *      the type of the second level property
   * @param <P3>
   *      the type of the third level property
   * @param <R>
   *      the type of the value to compare with
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method
   * @param g3
   *      the third level getter method
   * @param g4
   *      the fourth level getter method
   * @param lowerBound
   *      the lower bound to compare with, can be {@code null}
   * @param upperBound
   *      the upper bound to compare with, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <P1, P2, P3, R> ComposedCriterionBuilder<T> between(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3, final GetterMethod<P3, R> g4,
      @Nullable final R lowerBound, @Nullable final R upperBound) {
    final String path =  getPropertyPath(entityClass, g1, g2, g3, g4);
    if (lowerBound != null) {
      criteria.add(new SimpleCriterion<>(entityClass, path, GREATER_EQUAL, lowerBound));
    }
    if (upperBound != null) {
      criteria.add(new SimpleCriterion<>(entityClass, path, LESS_EQUAL, upperBound));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested property accessed by five getter
   * methods is between the given lower and upper bounds.
   *
   * <p>No criterion will be added if the provided lower bound is {@code null}
   * and the upper bound is {@code null}.</p>
   *
   * @param <P1>
   *      the type of the first level property
   * @param <P2>
   *      the type of the second level property
   * @param <P3>
   *      the type of the third level property
   * @param <P4>
   *      the type of the fourth level property
   * @param <R>
   *      the type of the value to compare with
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method
   * @param g3
   *      the third level getter method
   * @param g4
   *      the fourth level getter method
   * @param g5
   *      the fifth level getter method
   * @param lowerBound
   *      the lower bound to compare with, can be {@code null}
   * @param upperBound
   *      the upper bound to compare with, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <P1, P2, P3, P4, R> ComposedCriterionBuilder<T> between(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, P4> g4, final GetterMethod<P4, R> g5,
      @Nullable final R lowerBound, @Nullable final R upperBound) {
    final String path =  getPropertyPath(entityClass, g1, g2, g3, g4, g5);
    if (lowerBound != null) {
      criteria.add(new SimpleCriterion<>(entityClass, path, GREATER_EQUAL, lowerBound));
    }
    if (upperBound != null) {
      criteria.add(new SimpleCriterion<>(entityClass, path, LESS_EQUAL, upperBound));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if the specified property is in the given list
   * of values.
   *
   * <p>No criterion will be added if the provided values list is {@code null}
   * or empty.</p>
   *
   * @param <R>
   *      the type of the value to compare with
   * @param path
   *      the path of the property to check
   * @param values
   *      the list of values to compare with, can be {@code null} or empty
   * @return this builder instance for method chaining
   */
  public <R> ComposedCriterionBuilder<T> in(final String path,
      @Nullable final List<R> values) {
    if (values != null) { // 注意：允许values为空列表
      criteria.add(new SimpleCriterion<>(entityClass, path, IN, values));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if the property accessed by the getter method
   * is in the given list of values.
   *
   * <p>No criterion will be added if the provided values list is {@code null}
   * or empty.</p>
   *
   * @param <R>
   *      the type of the property and value
   * @param getter
   *      the getter method reference to access the property
   * @param values
   *      the list of values to compare with, can be {@code null} or empty
   * @return this builder instance for method chaining
   */
  public <R> ComposedCriterionBuilder<T> in(final GetterMethod<T, R> getter,
      @Nullable final List<R> values) {
    if (values != null) { // 注意：允许values为空列表
      final String path = getPropertyPath(entityClass, getter);
      criteria.add(new SimpleCriterion<>(entityClass, path, IN, values));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested property accessed by two getter
   * methods is in the given list of values.
   *
   * <p>No criterion will be added if the provided values list is {@code null}
   * or empty.</p>
   *
   * @param <P>
   *      the type of the first level property
   * @param <R>
   *      the type of the value to compare with
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method
   * @param values
   *      the list of values to compare with, can be {@code null} or empty
   * @return this builder instance for method chaining
   */
  public <P, R> ComposedCriterionBuilder<T> in(final GetterMethod<T, P> g1,
      final GetterMethod<P, R> g2, @Nullable final List<R> values) {
    if (values != null) { // 注意：允许values为空列表
      final String path = getPropertyPath(entityClass, g1, g2);
      criteria.add(new SimpleCriterion<>(entityClass, path, IN, values));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested property accessed by three getter
   * methods is in the given list of values.
   *
   * <p>No criterion will be added if the provided values list is {@code null}
   * or empty.</p>
   *
   * @param <P1>
   *      the type of the first level property
   * @param <P2>
   *      the type of the second level property
   * @param <R>
   *      the type of the value to compare with
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method
   * @param g3
   *      the third level getter method
   * @param values
   *      the list of values to compare with, can be {@code null} or empty
   * @return this builder instance for method chaining
   */
  public <P1, P2, R> ComposedCriterionBuilder<T> in(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, R> g3,
      @Nullable final List<R> values) {
    if (values != null)      { // 注意：允许values为空列表
      final String path = getPropertyPath(entityClass, g1, g2, g3);
      criteria.add(new SimpleCriterion<>(entityClass, path, IN, values));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested property accessed by four getter
   * methods is in the given list of values.
   *
   * <p>No criterion will be added if the provided values list is {@code null}
   * or empty.</p>
   *
   * @param <P1>
   *      the type of the first level property
   * @param <P2>
   *      the type of the second level property
   * @param <P3>
   *      the type of the third level property
   * @param <R>
   *      the type of the value to compare with
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method
   * @param g3
   *      the third level getter method
   * @param g4
   *      the fourth level getter method
   * @param values
   *      the list of values to compare with, can be {@code null} or empty
   * @return this builder instance for method chaining
   */
  public <P1, P2, P3, R> ComposedCriterionBuilder<T> in(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, R> g4, @Nullable final List<R> values) {
    if (values != null)      { // 注意：允许values为空列表
      final String path = getPropertyPath(entityClass, g1, g2, g3, g4);
      criteria.add(new SimpleCriterion<>(entityClass, path, IN, values));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested property accessed by five getter
   * methods is in the given list of values.
   *
   * <p>No criterion will be added if the provided values list is {@code null}
   * or empty.</p>
   *
   * @param <P1>
   *      the type of the first level property
   * @param <P2>
   *      the type of the second level property
   * @param <P3>
   *      the type of the third level property
   * @param <P4>
   *      the type of the fourth level property
   * @param <R>
   *      the type of the value to compare with
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method
   * @param g3
   *      the third level getter method
   * @param g4
   *      the fourth level getter method
   * @param g5
   *      the fifth level getter method
   * @param values
   *      the list of values to compare with, can be {@code null} or empty
   * @return this builder instance for method chaining
   */
  public <P1, P2, P3, P4, R> ComposedCriterionBuilder<T> in(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, P4> g4, final GetterMethod<P4, R> g5,
      @Nullable final List<R> values) {
    if (values != null)      { // 注意：允许values为空列表
      final String path = getPropertyPath(entityClass, g1, g2, g3, g4, g5);
      criteria.add(new SimpleCriterion<>(entityClass, path, IN, values));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if the specified property is not in the given
   * list of values.
   *
   * <p>No criterion will be added if the provided values list is {@code null}
   * or empty.</p>
   *
   * @param <R>
   *      the type of the value to compare with
   * @param path
   *      the path of the property to check
   * @param values
   *      the list of values to compare with, can be {@code null} or empty
   * @return this builder instance for method chaining
   */
  public <R> ComposedCriterionBuilder<T> notIn(final String path,
      @Nullable final List<R> values) {
    if (values != null)      { // 注意：允许values为空列表
      criteria.add(new SimpleCriterion<>(entityClass, path, NOT_IN, values));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if the property accessed by the getter method
   * is not in the given list of values.
   *
   * <p>No criterion will be added if the provided values list is {@code null}
   *  or empty.</p>
   *
   * @param <R>
   *      the type of the property and value
   * @param getter
   *      the getter method reference to access the property
   * @param values
   *      the list of values to compare with, can be {@code null} or empty
   * @return this builder instance for method chaining
   */
  public <R> ComposedCriterionBuilder<T> notIn(final GetterMethod<T, R> getter,
      @Nullable final List<R> values) {
    if (values != null)      { // 注意：允许values为空列表
      final String path = getPropertyPath(entityClass, getter);
      criteria.add(new SimpleCriterion<>(entityClass, path, NOT_IN, values));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested property accessed by two getter
   *  methods is not in the given list of values.
   *
   * <p>No criterion will be added if the provided values list is {@code null}
   * or empty.</p>
   *
   * @param <P>
   *      the type of the first level property
   * @param <R>
   *      the type of the value to compare with
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method
   * @param values
   *      the list of values to compare with, can be {@code null} or empty
   * @return this builder instance for method chaining
   */
  public <P, R> ComposedCriterionBuilder<T> notIn(final GetterMethod<T, P> g1,
      final GetterMethod<P, R> g2, @Nullable final List<R> values) {
    if (values != null)      { // 注意：允许values为空列表
      final String path = getPropertyPath(entityClass, g1, g2);
      criteria.add(new SimpleCriterion<>(entityClass, path, NOT_IN, values));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested property accessed by three getter
   * methods is not in the given list of values.
   *
   * <p>No criterion will be added if the provided values list is {@code null}
   * or empty.</p>
   *
   * @param <P1>
   *      the type of the first level property
   * @param <P2>
   *      the type of the second level property
   * @param <R>
   *      the type of the value to compare with
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method
   * @param g3
   *      the third level getter method
   * @param values
   *      the list of values to compare with, can be {@code null} or empty
   * @return this builder instance for method chaining
   */
  public <P1, P2, R> ComposedCriterionBuilder<T> notIn(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, R> g3,
      @Nullable final List<R> values) {
    if (values != null)      { // 注意：允许values为空列表
      final String path = getPropertyPath(entityClass, g1, g2, g3);
      criteria.add(new SimpleCriterion<>(entityClass, path, NOT_IN, values));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested property accessed by four getter
   * methods is not in the given list of values.
   *
   * <p>No criterion will be added if the provided values list is {@code null}
   * or empty.</p>
   *
   * @param <P1>
   *      the type of the first level property
   * @param <P2>
   *      the type of the second level property
   * @param <P3>
   *      the type of the third level property
   * @param <R>
   *      the type of the value to compare with
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method
   * @param g3
   *      the third level getter method
   * @param g4
   *      the fourth level getter method
   * @param values
   *      the list of values to compare with, can be {@code null} or empty
   * @return this builder instance for method chaining
   */
  public <P1, P2, P3, R> ComposedCriterionBuilder<T> notIn(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, R> g4, @Nullable final List<R> values) {
    if (values != null)      { // 注意：允许values为空列表
      final String path = getPropertyPath(entityClass, g1, g2, g3, g4);
      criteria.add(new SimpleCriterion<>(entityClass, path, NOT_IN, values));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested property accessed by five getter
   * methods is not in the given list of values.
   *
   * <p>No criterion will be added if the provided values list is {@code null}
   * or empty.</p>
   *
   * @param <P1>
   *      the type of the first level property
   * @param <P2>
   *      the type of the second level property
   * @param <P3>
   *      the type of the third level property
   * @param <P4>
   *      the type of the fourth level property
   * @param <R>
   *      the type of the value to compare with
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method
   * @param g3
   *      the third level getter method
   * @param g4
   *      the fourth level getter method
   * @param g5
   *      the fifth level getter method
   * @param values
   *      the list of values to compare with, can be {@code null} or empty
   * @return this builder instance for method chaining
   */
  public <P1, P2, P3, P4, R> ComposedCriterionBuilder<T> notIn(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, P4> g4, final GetterMethod<P4, R> g5,
      @Nullable final List<R> values) {
    if (values != null)      { // 注意：允许values为空列表
      final String path = getPropertyPath(entityClass, g1, g2, g3, g4, g5);
      criteria.add(new SimpleCriterion<>(entityClass, path, NOT_IN, values));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if the specified string property contains the
   * given substring.
   *
   * <p>This performs a SQL LIKE operation, automatically adding '%' wildcards
   * before and after the given value. No criterion will be added if the
   * provided value is {@code null}.</p>
   *
   * @param path
   *      the path of the property to check
   * @param value
   *      the substring to search for, can be {@code null}
   * @return this builder instance for method chaining
   */
  public ComposedCriterionBuilder<T> like(final String path,
      @Nullable final String value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(entityClass, path, LIKE, '%' + value + '%'));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if the string property accessed by the getter
   * method contains the given substring.
   *
   * <p>This performs a SQL LIKE operation, automatically adding '%' wildcards
   * before and after the given value. No criterion will be added if the
   * provided value is {@code null}.</p>
   *
   * @param getter
   *      the getter method reference to access the string property
   * @param value
   *      the substring to search for, can be {@code null}
   * @return this builder instance for method chaining
   */
  public ComposedCriterionBuilder<T> like(final GetterMethod<T, String> getter,
      @Nullable final String value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, getter);
      criteria.add(new SimpleCriterion<>(entityClass, path, LIKE, '%' + value + '%'));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested string property accessed by two
   * getter methods contains the given substring.
   *
   * <p>This performs a SQL LIKE operation, automatically adding '%' wildcards
   * before and after the given value. No criterion will be added if the provided
   * value is {@code null}.</p>
   *
   * @param <P>
   *      the type of the first level property
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method that returns a String
   * @param value
   *      the substring to search for, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <P> ComposedCriterionBuilder<T> like(final GetterMethod<T, P> g1,
      final GetterMethod<P, String> g2, @Nullable final String value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, g1, g2);
      criteria.add(new SimpleCriterion<>(entityClass, path, LIKE, '%' + value + '%'));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested string property accessed by three
   * getter methods contains the given substring.
   *
   * <p>This performs a SQL LIKE operation, automatically adding '%' wildcards
   * before and after the given value. No criterion will be added if the
   * provided value is {@code null}.</p>
   *
   * @param <P1>
   *      the type of the first level property
   * @param <P2>
   *      the type of the second level property
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method
   * @param g3
   *      the third level getter method that returns a String
   * @param value
   *      the substring to search for, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <P1, P2> ComposedCriterionBuilder<T> like(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, String> g3,
      @Nullable final String value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, g1, g2, g3);
      criteria.add(new SimpleCriterion<>(entityClass, path, LIKE, '%' + value + '%'));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested string property accessed by four
   * getter methods contains the given substring.
   *
   * <p>This performs a SQL LIKE operation, automatically adding '%' wildcards
   * before and after the given value. No criterion will be added if the
   * provided value is {@code null}.</p>
   *
   * @param <P1>
   *      the type of the first level property
   * @param <P2>
   *      the type of the second level property
   * @param <P3>
   *      the type of the third level property
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method
   * @param g3
   *      the third level getter method
   * @param g4
   *      the fourth level getter method that returns a String
   * @param value
   *      the substring to search for, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <P1, P2, P3> ComposedCriterionBuilder<T> like(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, String> g4, @Nullable final String value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, g1, g2, g3, g4);
      criteria.add(new SimpleCriterion<>(entityClass, path, LIKE, '%' + value + '%'));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested string property accessed by five
   * getter methods contains the given substring.
   *
   * <p>This performs a SQL LIKE operation, automatically adding '%' wildcards
   * before and after the given value. No criterion will be added if the
   * provided value is {@code null}.</p>
   *
   * @param <P1>
   *      the type of the first level property
   * @param <P2>
   *      the type of the second level property
   * @param <P3>
   *      the type of the third level property
   * @param <P4>
   *      the type of the fourth level property
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method
   * @param g3
   *      the third level getter method
   * @param g4
   *      the fourth level getter method
   * @param g5
   *      the fifth level getter method that returns a String
   * @param value
   *      the substring to search for, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <P1, P2, P3, P4> ComposedCriterionBuilder<T> like(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, P4> g4, final GetterMethod<P4, String> g5,
      @Nullable final String value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, g1, g2, g3, g4, g5);
      criteria.add(new SimpleCriterion<>(entityClass, path, LIKE, '%' + value + '%'));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if the specified string property does not
   * contain the given substring.
   *
   * <p>This performs a SQL NOT LIKE operation, automatically adding '%'
   * wildcards before and after the given value. No criterion will be added if
   * the provided value is {@code null}.</p>
   *
   * @param path
   *      the path of the property to check
   * @param value
   *      the substring to search for, can be {@code null}
   * @return this builder instance for method chaining
   */
  public ComposedCriterionBuilder<T> notLike(final String path,
      @Nullable final String value) {
    if (value != null) {
      criteria.add(new SimpleCriterion<>(entityClass, path, NOT_LIKE, '%' + value + '%'));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if the string property accessed by the getter
   * method does not contain the given substring.
   *
   * <p>This performs a SQL NOT LIKE operation, automatically adding '%'
   * wildcards before and after the given value. No criterion will be added if
   * the provided value is {@code null}.</p>
   *
   * @param getter
   *      the getter method reference to access the string property
   * @param value
   *      the substring to search for, can be {@code null}
   * @return this builder instance for method chaining
   */
  public ComposedCriterionBuilder<T> notLike(final GetterMethod<T, String> getter,
      @Nullable final String value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, getter);
      criteria.add(new SimpleCriterion<>(entityClass, path, NOT_LIKE, '%' + value + '%'));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested string property accessed by two
   * getter methods does not contain the given substring.
   *
   * <p>This performs a SQL NOT LIKE operation, automatically adding '%' wildcards
   * before and after the given value. No criterion will be added if the
   * provided value is {@code null}.</p>
   *
   * @param <P>
   *      the type of the first level property
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method that returns a String
   * @param value
   *      the substring to search for, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <P> ComposedCriterionBuilder<T> notLike(final GetterMethod<T, P> g1,
      final GetterMethod<P, String> g2, @Nullable final String value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, g1, g2);
      criteria.add(new SimpleCriterion<>(entityClass, path, NOT_LIKE, '%' + value + '%'));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested string property accessed by three
   * getter methods does not contain the given substring.
   *
   * <p>This performs a SQL NOT LIKE operation, automatically adding '%'
   * wildcards before and after the given value. No criterion will be added if
   * the provided value is {@code null}.</p>
   *
   * @param <P1>
   *      the type of the first level property
   * @param <P2>
   *      the type of the second level property
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method
   * @param g3
   *      the third level getter method that returns a String
   * @param value
   *      the substring to search for, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <P1, P2> ComposedCriterionBuilder<T> notLike(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, String> g3,
      @Nullable final String value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, g1, g2, g3);
      criteria.add(new SimpleCriterion<>(entityClass, path, NOT_LIKE, '%' + value + '%'));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested string property accessed by four
   * getter methods does not contain the given substring.
   *
   * <p>This performs a SQL NOT LIKE operation, automatically adding '%'
   * wildcards before and after the given value. No criterion will be added if
   * the provided value is {@code null}.</p>
   *
   * @param <P1>
   *      the type of the first level property
   * @param <P2>
   *      the type of the second level property
   * @param <P3>
   *      the type of the third level property
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method
   * @param g3
   *      the third level getter method
   * @param g4
   *      the fourth level getter method that returns a String
   * @param value
   *      the substring to search for, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <P1, P2, P3> ComposedCriterionBuilder<T> notLike(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, String> g4, @Nullable final String value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, g1, g2, g3, g4);
      criteria.add(new SimpleCriterion<>(entityClass, path, NOT_LIKE, '%' + value + '%'));
    }
    return this;
  }

  /**
   * Adds a criterion that checks if a nested string property accessed by five
   * getter methods does not contain the given substring.
   *
   * <p>This performs a SQL NOT LIKE operation, automatically adding '%'
   * wildcards before and after the given value. No criterion will be added if
   * the provided value is {@code null}.</p>
   *
   * @param <P1>
   *      the type of the first level property
   * @param <P2>
   *      the type of the second level property
   * @param <P3>
   *      the type of the third level property
   * @param <P4>
   *      the type of the fourth level property
   * @param g1
   *      the first level getter method
   * @param g2
   *      the second level getter method
   * @param g3
   *      the third level getter method
   * @param g4
   *      the fourth level getter method
   * @param g5
   *      the fifth level getter method that returns a String
   * @param value
   *      the substring to search for, can be {@code null}
   * @return this builder instance for method chaining
   */
  public <P1, P2, P3, P4> ComposedCriterionBuilder<T> notLike(final GetterMethod<T, P1> g1,
      final GetterMethod<P1, P2> g2, final GetterMethod<P2, P3> g3,
      final GetterMethod<P3, P4> g4, final GetterMethod<P4, String> g5,
      @Nullable final String value) {
    if (value != null) {
      final String path = getPropertyPath(entityClass, g1, g2, g3, g4, g5);
      criteria.add(new SimpleCriterion<>(entityClass, path, NOT_LIKE, '%' + value + '%'));
    }
    return this;
  }

  /**
   * Appends another criterion to this builder.
   *
   * <p>If the provided criterion is a {@link SimpleCriterion}, it will be added
   * directly. If it's a {@link ComposedCriterion}, its criteria will be added
   * individually, but only if it has the same logical relation as this builder.</p>
   *
   * @param criterion
   *      the criterion to append, or {@code null} if none
   * @return this builder instance for method chaining
   * @throws IllegalArgumentException
   *      if the provided criterion is a {@link ComposedCriterion} with a
   *      different logical relation than this builder
   */
  public ComposedCriterionBuilder<T> append(@Nullable final Criterion<T> criterion) {
    if (criterion != null) {
      if (criterion instanceof SimpleCriterion<T>) {
        criteria.add((SimpleCriterion<T>) criterion);
      } else if (criterion instanceof final ComposedCriterion<T> composedCriterion) {
        if (composedCriterion.getRelation() != relation) {
          throw new IllegalArgumentException("Mismatched composed criterion relation: "
              + "expect " + relation + ", but got " + composedCriterion.getRelation());
        }
        criteria.addAll(composedCriterion.getCriteria());
      } else {
        throw new IllegalArgumentException("Unsupported criterion type: " + criterion.getClass().getName());
      }
    }
    return this;
  }

  /**
   * Builds and returns a composed criterion from the conditions added to this builder.
   *
   * @return a new {@link ComposedCriterion} instance containing all the criteria
   *         added to this builder with the specified logical relation
   */
  public ComposedCriterion<T> build() {
    return new ComposedCriterion<T>(entityClass, relation, criteria);
  }
}