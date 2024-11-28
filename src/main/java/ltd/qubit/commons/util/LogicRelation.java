////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util;

/**
 * 此枚举表示逻辑表达式之间的逻辑关系。
 *
 * @author 胡海星
 */
public enum LogicRelation {

  /**
   * 表示逻辑与关系。
   */
  AND("AND"),

  /**
   * 表示逻辑或关系。
   */
  OR("OR"),

  /**
   * 表示逻辑非关系。
   */
  NOT("NOT");

  private final String symbol;

  LogicRelation(final String symbol) {
    this.symbol = symbol;
  }

  public final String getSymbol() {
    return symbol;
  }
}
