////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 枚举器与其名称之间的映射。
 *
 * @author 胡海星
 */
public class EnumMap {

  private String value;

  private String name;

  /**
   * 获取枚举值。
   *
   * @return
   *     枚举值。
   */
  public final String getValue() {
    return value;
  }

  /**
   * 设置枚举值。
   *
   * @param value
   *     新的枚举值。
   * @return
   *     返回此对象。
   */
  public final EnumMap setValue(final String value) {
    this.value = value;
    return this;
  }

  /**
   * 获取枚举名称。
   *
   * @return
   *     枚举名称。
   */
  public final String getName() {
    return name;
  }

  /**
   * 设置枚举名称。
   *
   * @param name
   *     新的枚举名称。
   * @return
   *     返回此对象。
   */
  public final EnumMap setName(final String name) {
    this.name = name;
    return this;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final EnumMap other = (EnumMap) o;
    return Equality.equals(value, other.value)
            && Equality.equals(name, other.name);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, value);
    result = Hash.combine(result, multiplier, name);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
            .append("value", value)
            .append("name", name)
            .toString();
  }
}