////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util;

import ltd.qubit.commons.lang.ArrayUtils;
import ltd.qubit.commons.text.SqlLikePattern;

import static ltd.qubit.commons.sql.impl.CriterionImplUtils.toComparableValue;

/**
 * 此枚举表示表达式左右之间的比较操作符。
 *
 * @author 胡海星
 */
public enum ComparisonOperator {
  /**
   * 表示左边的值等于右边的值。
   */
  EQUAL("="),

  /**
   * 表示左边的值不等于右边的值。
   */
  NOT_EQUAL("!="),

  /**
   * 表示左边的值小于右边的值。
   */
  LESS("<"),

  /**
   * 表示左边的值小于或等于右边的值。
   */
  LESS_EQUAL("<="),

  /**
   * 表示左边的值大于右边的值。
   */
  GREATER(">"),

  /**
   * 表示左边的值大于或等于右边的值。
   */
  GREATER_EQUAL(">="),

  /**
   * 表示左边的值包含在右边的值表示的集合中。
   */
  IN("IN"),

  /**
   * 表示左边的值不包含在右边的值表示的集合中。
   */
  NOT_IN("NOT IN"),

  /**
   * 表示左边的字符值匹配右边的值表示的通配符表达式。
   *
   * <p>通配符表达式使用标准SQL的语法。</p>
   */
  LIKE("LIKE"),

  /**
   * 表示左边的字符值不匹配右边的值表示的通配符表达式。
   *
   * <p>通配符表达式使用标准SQL的语法。</p>
   */
  NOT_LIKE("NOT LIKE");

  /**
   * 返回指定数据类型所支持的所有比较操作符。
   *
   * @param dataType
   *     指定的数据类型。
   * @return
   *     指定数据类型所支持的所有比较操作符。
   */
  ComparisonOperator[] forDataType(final DataType dataType) {
    switch (dataType) {
      case INTEGER:
      case DECIMAL:
      case DATE:
      case TIME:
      case DATETIME:
      case TIMESTAMP:
        return new ComparisonOperator[] {
            EQUAL, NOT_EQUAL, LESS, LESS_EQUAL, GREATER, GREATER_EQUAL, IN, NOT_IN
        };
      case STRING:
        return new ComparisonOperator[] {
            EQUAL, NOT_EQUAL, LIKE, NOT_LIKE, IN, NOT_IN
        };
      case BOOLEAN:
        return new ComparisonOperator[] {
            EQUAL, NOT_EQUAL
        };
      case ENUM:
        return new ComparisonOperator[] {
            EQUAL, NOT_EQUAL, IN, NOT_IN
        };
      default:
        throw new IllegalArgumentException("Unsupported data type: " + dataType);
    }
  }

  private final String symbol;

  /**
   * 创建一个比较操作符实例。
   *
   * @param symbol
   *     此比较操作符对应的符号。
   */
  ComparisonOperator(final String symbol) {
    this.symbol = symbol;
  }

  /**
   * 获取此比较操作符对应的符号。
   *
   * @return
   *     此比较操作符对应的符号。
   */
  public final String getSymbol() {
    return symbol;
  }

  /**
   * 测试左侧值和右侧值是否满足此比较操作符的条件。
   *
   * @param lhsValue
   *     比较表达式左侧的值。
   * @param rhsValue
   *     比较表达式右侧的值。
   * @return
   *     如果左侧值和右侧值满足此比较操作符的条件，则返回{@code true}；否则返回{@code false}。
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public final boolean test(final Object lhsValue, final Object rhsValue) {
    if (lhsValue == null) {
      return (this == EQUAL && rhsValue == null);
    } else if (rhsValue == null) {   // lhsValue != null
      return (this == NOT_EQUAL);
    }
    //  now lhsValue != null && rhsValue != null
    final Object comparableLhs = toComparableValue(lhsValue);
    final Object comparableRhs = toComparableValue(rhsValue);
    return switch (this) {
      case EQUAL -> comparableLhs.equals(comparableRhs);
      case NOT_EQUAL -> !comparableLhs.equals(comparableRhs);
      case LESS -> (((Comparable) comparableLhs).compareTo(comparableRhs) < 0);
      case LESS_EQUAL -> (((Comparable) comparableLhs).compareTo(comparableRhs) <= 0);
      case GREATER -> (((Comparable) comparableLhs).compareTo(comparableRhs) > 0);
      case GREATER_EQUAL -> (((Comparable) comparableLhs).compareTo(comparableRhs) >= 0);
      case IN -> ArrayUtils.contains((Object[]) comparableRhs, comparableLhs);
      case NOT_IN -> (!ArrayUtils.contains((Object[]) comparableRhs, comparableLhs));
      case LIKE -> new SqlLikePattern((String) comparableRhs).match((String) comparableLhs);
      case NOT_LIKE -> (!(new SqlLikePattern((String) comparableRhs).match((String) comparableLhs)));
    };
  }
}