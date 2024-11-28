////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql;

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

  private final String symbol;

  ComparisonOperator(final String symbol) {
    this.symbol = symbol;
  }

  public final String getSymbol() {
    return symbol;
  }
}
