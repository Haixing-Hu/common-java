////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql;

import java.io.Serial;
import java.io.Serializable;

import javax.annotation.Nullable;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.lang.Argument.requireGreater;
import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * 此结构存储数据库支持的客户端信息。
 *
 * @author 胡海星
 */
public final class ClientInfo implements Serializable {

  @Serial
  private static final long serialVersionUID = 5116233033559100607L;

  /**
   * 客户端信息的名称。
   */
  private String name;

  /**
   * 客户端信息的最大长度。
   */
  private int maxLength;

  /**
   * 客户端信息的默认值。
   */
  @Nullable
  private String defaultValue;

  /**
   * 客户端信息的描述。
   */
  @Nullable
  private String description;

  /**
   * 构造一个新的ClientInfo对象。
   */
  public ClientInfo() {
    name = null;
    maxLength = 0;
    defaultValue = null;
    description = null;
  }

  /**
   * 构造一个带有指定参数的ClientInfo对象。
   *
   * @param name
   *     客户端信息的名称。
   * @param maxLength
   *     客户端信息的最大长度，必须大于0。
   * @param defaultValue
   *     客户端信息的默认值，可以为{@code null}。
   * @param description
   *     客户端信息的描述，可以为{@code null}。
   */
  public ClientInfo(final String name, final int maxLength,
      @Nullable final String defaultValue, @Nullable final String description) {
    this.name = requireNonNull("name", name);
    this.maxLength = requireGreater("maxLength", maxLength, "zero", 0);
    this.defaultValue = defaultValue;
    this.description = description;
  }

  /**
   * 获取客户端信息的名称。
   *
   * @return 客户端信息的名称。
   */
  public String getName() {
    return name;
  }

  /**
   * 设置客户端信息的名称。
   *
   * @param name
   *     新的客户端信息名称。
   */
  protected void setName(final String name) {
    this.name = requireNonNull("name", name);
  }

  /**
   * 获取客户端信息的最大长度。
   *
   * @return 客户端信息的最大长度。
   */
  public int getMaxLength() {
    return maxLength;
  }

  /**
   * 设置客户端信息的最大长度。
   *
   * @param maxLength
   *     新的最大长度，必须大于0。
   */
  protected void setMaxLength(final int maxLength) {
    this.maxLength = requireGreater("maxLength", maxLength, "zero", 0);
  }

  /**
   * 获取客户端信息的默认值。
   *
   * @return 客户端信息的默认值，可能为{@code null}。
   */
  public String getDefaultValue() {
    return defaultValue;
  }

  /**
   * 设置客户端信息的默认值。
   *
   * @param defaultValue
   *     新的默认值，可以为{@code null}。
   */
  protected void setDefaultValue(@Nullable final String defaultValue) {
    this.defaultValue = defaultValue;
  }

  /**
   * 获取客户端信息的描述。
   *
   * @return 客户端信息的描述，可能为{@code null}。
   */
  public String getDescription() {
    return description;
  }

  /**
   * 设置客户端信息的描述。
   *
   * @param description
   *     新的描述，可以为{@code null}。
   */
  protected void setDescription(@Nullable final String description) {
    this.description = description;
  }

  @Override
  public int hashCode() {
    final int multiplier = 3;
    int code = 123;
    code = Hash.combine(code, multiplier, name);
    code = Hash.combine(code, multiplier, maxLength);
    code = Hash.combine(code, multiplier, defaultValue);
    code = Hash.combine(code, multiplier, description);
    return code;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final ClientInfo other = (ClientInfo) obj;
    return Equality.equals(name, other.name)
         && Equality.equals(maxLength, maxLength)
         && Equality.equals(defaultValue, defaultValue)
         && Equality.equals(description, description);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("name", name)
               .append("maxLength", maxLength)
               .append("defaultValue", defaultValue)
               .append("description", description)
               .toString();
  }
}