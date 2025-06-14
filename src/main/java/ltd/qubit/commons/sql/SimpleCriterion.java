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

import javax.annotation.Nullable;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.reflect.impl.GetterMethod;
import ltd.qubit.commons.text.tostring.ToStringBuilder;
import ltd.qubit.commons.util.ComparisonOperator;

import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.reflect.FieldUtils.getFieldName;
import static ltd.qubit.commons.reflect.ObjectGraphUtils.getPropertyPath;
import static ltd.qubit.commons.reflect.ObjectGraphUtils.getPropertyType;
import static ltd.qubit.commons.reflect.ObjectGraphUtils.getPropertyValue;
import static ltd.qubit.commons.reflect.ObjectGraphUtils.hasProperty;
import static ltd.qubit.commons.sql.impl.CriterionImplUtils.arrayToSql;
import static ltd.qubit.commons.sql.impl.CriterionImplUtils.fieldToSql;
import static ltd.qubit.commons.sql.impl.CriterionImplUtils.isComparable;
import static ltd.qubit.commons.sql.impl.CriterionImplUtils.isSupportedDataType;
import static ltd.qubit.commons.sql.impl.CriterionImplUtils.valueToSql;
import static ltd.qubit.commons.text.NamingStyleUtils.propertyPathToDatabaseField;

/**
 * 此模型表示用于过滤实体的简单条件表达式。
 *
 * @author 胡海星
 */
public class SimpleCriterion<T> implements Criterion<T> {

  /**
   * 表示表达式所针对的实体的类。
   */
  private final Class<T> entityClass;

  /**
   * 表示表达式左边的对象的属性路径。
   */
  private final String property;

  /**
   * 表示表达式中间的比较操作符。
   */
  private final ComparisonOperator operator;

  /**
   * 表示表达式右边的值。
   */
  @Nullable
  private final Object value;

  /**
   * 表示此表达式是否是比较两个属性值。
   *
   * <p>如果此属性为{@code true}，则{@code value}属性应该是另一个被比较的属性的路径。</p>
   */
  private final boolean compareProperties;

  /**
   * 创建一个{@link SimpleCriterion}对象。
   *
   * @param entityClass
   *     待创建的{@link SimpleCriterion}对象所过滤的实体的类。
   * @param property
   *     表达式左边的实体类的属性的路径。
   * @param operator
   *     表达式中间的比较操作符。
   * @param value
   *     表达式右边的数值。支持的数值类型包括：{@code Boolean}, {@code Character},
   *     {@code Byte}, {@code Short}, {@code Integer}, {@code Long},
   *     {@code Float}, {@code Double}, {@code BigInteger}, {@code BigDecimal},
   *     {@code String}, {@code Instant}, {@code LocalDate},
   *     {@code LocalTime}, {@code LocalDateTime}, {@code ZonedDateTime},
   *     {@code java.sql.Date}, {@code java.sql.Time},
   *     {@code java.sql.Timestamp}, {@code java.util.Date},
   *     {@code Enum}及其派生类，以及以上诸类型所构成的数组。
   */
  public SimpleCriterion(final Class<T> entityClass, final String property,
      final ComparisonOperator operator, @Nullable final Object value) {
    this(entityClass, property, operator, value, false);
  }

  /**
   * 创建一个{@link SimpleCriterion}对象。
   *
   * @param entityClass
   *     待创建的{@link SimpleCriterion}对象所过滤的实体的类。
   * @param getter
   *     表达式左边的实体类的属性的getter。
   * @param operator
   *     表达式中间的比较操作符。
   * @param value
   *     表达式右边的数值。支持的数值类型包括：{@code Boolean}, {@code Character},
   *     {@code Byte}, {@code Short}, {@code Integer}, {@code Long},
   *     {@code Float}, {@code Double}, {@code BigInteger}, {@code BigDecimal},
   *     {@code String}, {@code Instant}, {@code LocalDate},
   *     {@code LocalTime}, {@code LocalDateTime}, {@code ZonedDateTime},
   *     {@code java.sql.Date}, {@code java.sql.Time},
   *     {@code java.sql.Timestamp}, {@code java.util.Date},
   *     {@code Enum}及其派生类，以及以上诸类型所构成的数组。
   */
  public <R> SimpleCriterion(final Class<T> entityClass, final GetterMethod<T, R> getter,
      final ComparisonOperator operator, @Nullable final Object value) {
    this(entityClass, getFieldName(entityClass, getter), operator, value, false);
  }

  /**
   * 创建一个{@link SimpleCriterion}对象。
   *
   * @param entityClass
   *     待创建的{@link SimpleCriterion}对象所过滤的实体的类。
   * @param property
   *     表达式左边的实体类的属性的路径。
   * @param operator
   *     表达式中间的比较操作符。
   * @param value
   *     表达式右边的值，可以是一个数值，或者是一个实体类的属性路径，取决于参数
   *     {@code compareProperties}。支持的数值类型包括：{@code Boolean},
   *     {@code Character}, {@code Byte}, {@code Short}, {@code Integer},
   *     {@code Long}, {@code Float}, {@code Double}, {@code BigInteger},
   *     {@code BigDecimal}, {@code String}, {@code Instant},
   *     {@code LocalDate}, {@code LocalTime}, {@code LocalDateTime},
   *     {@code ZonedDateTime}, {@code java.sql.Date}, {@code java.sql.Time},
   *     {@code java.sql.Timestamp}, {@code java.util.Date},
   *     {@code Enum}及其派生类，以及以上诸类型所构成的数组。
   * @param compareProperties
   *     指明该表达式是否是比较实体类的两个属性值，即其左右两边是否都是实体类的属性路径。
   */
  public SimpleCriterion(final Class<T> entityClass, final String property,
      final ComparisonOperator operator, @Nullable final Object value,
      final boolean compareProperties) {
    this.entityClass = requireNonNull("entityClass", entityClass);
    this.property = requireNonNull("field", property);
    this.operator = requireNonNull("operator", operator);
    this.value = value;
    this.compareProperties = compareProperties;
  }

  /**
   * 创建一个{@link SimpleCriterion}对象。
   *
   * @param entityClass
   *     待创建的{@link SimpleCriterion}对象所过滤的实体的类。
   * @param getter
   *     表达式左边的实体类的属性的getter。
   * @param operator
   *     表达式中间的比较操作符。
   * @param value
   *     表达式右边的值，可以是一个数值，或者是一个实体类的属性路径，取决于参数
   *     {@code compareProperties}。支持的数值类型包括：{@code Boolean},
   *     {@code Character}, {@code Byte}, {@code Short}, {@code Integer},
   *     {@code Long}, {@code Float}, {@code Double}, {@code BigInteger},
   *     {@code BigDecimal}, {@code String}, {@code Instant},
   *     {@code LocalDate}, {@code LocalTime}, {@code LocalDateTime},
   *     {@code ZonedDateTime}, {@code java.sql.Date}, {@code java.sql.Time},
   *     {@code java.sql.Timestamp}, {@code java.util.Date},
   *     {@code Enum}及其派生类，以及以上诸类型所构成的数组。
   * @param compareProperties
   *     指明该表达式是否是比较实体类的两个属性值，即其左右两边是否都是实体类的属性路径。
   */
  public <R> SimpleCriterion(final Class<T> entityClass, final GetterMethod<T, R> getter,
      final ComparisonOperator operator, @Nullable final Object value,
      final boolean compareProperties) {
    this(entityClass, getFieldName(entityClass, getter), operator, value, compareProperties);
  }

  /**
   * 获取此表达式所过滤的实体的类型。
   *
   * @return
   *     此表达式所过滤的实体的类型。
   */
  @Override
  public Class<T> getEntityClass() {
    return entityClass;
  }

  /**
   * 获取表达式左边的属性路径。
   *
   * <p>支持形如"app.code"这样的属性路径。</p>
   *
   * @return
   *     表达式左边的属性路径。
   */
  public final String getProperty() {
    return property;
  }

  /**
   * 获取此表达式左边的属性对应的数据库表字段名。
   *
   * <p>对于形如"app.code"这样的属性路径将转换为"app_code"这样的字段名。</p>
   *
   * @return
   *     此表达式左边的属性对应的数据库表字段名。
   */
  public final String getField() {
    return propertyPathToDatabaseField(property);
  }

  /**
   * 获取此表达式中间的比较操作符。
   *
   * @return
   *     此表达式中间的比较操作符。
   */
  public final ComparisonOperator getOperator() {
    return operator;
  }

  /**
   * 获取此表达式右边的值。
   *
   * @return
   *     此表达式右边的值，可能为{@code null}；也可能是另一个属性路径。
   */
  @Nullable
  public final Object getValue() {
    return value;
  }

  /**
   * 判定此表达式是否是比较实体类的两个属性值。
   *
   * @return
   *     如果此表达式是比较实体类的两个属性值，即其左右两边都是实体类的属性路径，则返回
   *     {@code true}；否则返回{@code false}。
   */
  public final boolean isCompareProperties() {
    return compareProperties;
  }

  @Override
  public String toSql() throws SQLSyntaxErrorException {
    final StringBuilder builder = new StringBuilder();
    builder.append(fieldToSql(getField()))
           .append(" ");
    if (value == null) {
      if (compareProperties) {
        throw new SQLSyntaxErrorException("Cannot convert to SQL: " + this);
      }
      switch (operator) {
        case EQUAL:
          builder.append("IS NULL");
          break;
        case NOT_EQUAL:
          builder.append("IS NOT NULL");
          break;
        default:
          throw new SQLSyntaxErrorException("Cannot convert to SQL: " + this);
      }
    } else {
      builder.append(operator.getSymbol())
             .append(" ");
      switch (operator) {
        case EQUAL:
        case NOT_EQUAL:
        case LESS:
        case LESS_EQUAL:
        case GREATER:
        case GREATER_EQUAL:
          if (compareProperties) {
            if (!(value instanceof String)) {
              throw new SQLSyntaxErrorException("Cannot convert to SQL: " + this);
            }
            final String otherField = propertyPathToDatabaseField((String) value);
            builder.append(fieldToSql(otherField));
          } else {
            builder.append(valueToSql(value));
          }
          break;
        case IN:
        case NOT_IN:
          if (compareProperties) {
            throw new SQLSyntaxErrorException("Cannot convert to SQL: " + this);
          }
          if (value.getClass().isArray()) {
            builder.append(arrayToSql(value));
          } else {
            throw new SQLSyntaxErrorException("Cannot convert to SQL: " + this);
          }
          break;
        case LIKE:
        case NOT_LIKE:
          if (compareProperties || !(value instanceof String)) {
            throw new SQLSyntaxErrorException("Cannot convert to SQL: " + this);
          }
          builder.append(valueToSql(value));
          break;
        default:
          break;
      }
    }
    return builder.toString();
  }

  @Override
  public boolean isValid() {
    if (! hasProperty(entityClass, property)) {
      return false;
    }
    final Class<?> lhsType = getPropertyType(entityClass, property);
    if (!isSupportedDataType(lhsType)) {
      return false;
    }
    final Class<?> rhsType;
    if (compareProperties) {
      if (!(value instanceof String)) {
        return false;
      }
      if (!hasProperty(entityClass, (String) value)) {
        return false;
      }
      rhsType = getPropertyType(entityClass, (String) value);
      if (!isSupportedDataType(rhsType)) {
        return false;
      }
    } else {
      rhsType = (value == null ? null : value.getClass());
    }
    return isComparable(lhsType, operator, rhsType);
  }

  @Override
  public boolean accept(final T obj) {
    if (obj == null) {
      return false;
    }
    if (! isValid()) {
      return false;
    }
    final Object lhsValue = getPropertyValue(obj, property);
    final Object rhsValue;
    if (compareProperties) {
      rhsValue = getPropertyValue(obj, (String) value);
    } else {
      rhsValue = value;
    }
    return operator.test(lhsValue, rhsValue);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final SimpleCriterion<?> other = (SimpleCriterion<?>) o;
    return Equality.equals(entityClass, other.entityClass)
        && Equality.equals(property, other.property)
        && Equality.equals(operator, other.operator)
        && Equality.equals(value, other.value)
        && Equality.equals(compareProperties, other.compareProperties);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, entityClass);
    result = Hash.combine(result, multiplier, property);
    result = Hash.combine(result, multiplier, operator);
    result = Hash.combine(result, multiplier, value);
    result = Hash.combine(result, multiplier, compareProperties);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("entityClass", entityClass)
        .append("property", property)
        .append("operator", operator)
        .append("value", value)
        .append("compareProperties", compareProperties)
        .toString();
  }

  /**
   * Extracts a criterion for a sub-entity.
   * <p>
   * If the property path of the current criterion starts with the property path
   * of the sub-entity, extracts a criterion for the sub-entity.
   * For example:
   * <ul>
   *   <li>Current criterion: customer.country.name = "USA"
   *   <li>Sub-entity path: customer.country
   *   <li>Result: name = "USA"
   * </ul>
   * <p>
   * If the property path of the current criterion exactly equals the property
   * path of the sub-entity, returns null.
   * For example:
   * <ul>
   *   <li>Current criterion: name = "John"
   *   <li>Sub-entity path: name
   *   <li>Result: null
   * </ul>
   *
   * @param subEntityClass the class of the sub-entity
   * @param propertyPath the property path of the sub-entity
   * @return the criterion for the sub-entity, or null if there is no sub-path
   */
  public <S> SimpleCriterion<S> extractSubEntityCriterion(final Class<S> subEntityClass,
      final String propertyPath) {
    final String prefix = propertyPath + ".";
    if (!property.startsWith(prefix)) {
      return null;
    }
    final String subEntityPropertyPath = property.substring(prefix.length());
    return new SimpleCriterion<>(
        subEntityClass,
        subEntityPropertyPath,
        operator,
        value,
        compareProperties
    );
  }

  /**
   * Extracts a criterion for a sub-entity from the current criterion.
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
  public <P>
  SimpleCriterion<P> extractSubEntityCriterion(final Class<P> propertyClass,
      final GetterMethod<T, P> propertyGetter) {
    final String path = getPropertyPath(entityClass, propertyGetter);
    return extractSubEntityCriterion(propertyClass, path);
  }

  /**
   * Extracts a criterion for a sub-entity from the current criterion.
   *
   * @param <P>
   *     The type of the sub-entity.
   * @param <P1>
   *     The type of the first intermediate property.
   * @param <P2>
   *     The type of the second intermediate property.
   * @param propertyClass
   *     The class of the sub-entity.
   * @param getter1
   *     The first getter method in the property path.
   * @param getter2
   *     The second getter method in the property path.
   * @return
   *     A new criterion for the sub-entity containing all matching criteria, or
   *     {@code null} if no matching criteria are found.
   */
  @Nullable
  public <P, P1, P2>
  SimpleCriterion<P> extractSubEntityCriterion(final Class<P> propertyClass,
      final GetterMethod<T, P1> getter1,
      final GetterMethod<P1, P2> getter2) {
    final String path = getPropertyPath(entityClass, getter1, getter2);
    return extractSubEntityCriterion(propertyClass, path);
  }

  /**
   * Extracts a criterion for a sub-entity from the current criterion.
   *
   * @param <P>
   *     The type of the sub-entity.
   * @param <P1>
   *     The type of the first intermediate property.
   * @param <P2>
   *     The type of the second intermediate property.
   * @param <P3>
   *     The type of the third intermediate property.
   * @param propertyClass
   *     The class of the sub-entity.
   * @param getter1
   *     The first getter method in the property path.
   * @param getter2
   *     The second getter method in the property path.
   * @param getter3
   *     The third getter method in the property path.
   * @return
   *     A new criterion for the sub-entity containing all matching criteria, or
   *     {@code null} if no matching criteria are found.
   */
  @Nullable
  public <P, P1, P2, P3>
  SimpleCriterion<P> extractSubEntityCriterion(final Class<P> propertyClass,
      final GetterMethod<T, P1> getter1,
      final GetterMethod<P1, P2> getter2,
      final GetterMethod<P2, P3> getter3) {
    final String path = getPropertyPath(entityClass, getter1, getter2, getter3);
    return extractSubEntityCriterion(propertyClass, path);
  }
}