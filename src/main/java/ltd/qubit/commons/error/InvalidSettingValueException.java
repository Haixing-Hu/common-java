////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

import ltd.qubit.commons.util.pair.KeyValuePairList;

/**
 * 抛出以指示名称的值或值无效。
 *
 * @author 胡海星
 */
public class InvalidSettingValueException extends RuntimeException
    implements ErrorInfoConvertable {

  private static final long serialVersionUID = -2108303826996770048L;

  private final String name;
  private final String value;

  /**
   * 使用指定的名称和值构造一个新的 {@link InvalidSettingValueException}。
   *
   * @param name
   *     设置的名称。
   * @param value
   *     设置的无效值。
   */
  public InvalidSettingValueException(final String name, final String value) {
    super("The value of the setting '" + name + "' is invalid: " + value);
    this.name = name;
    this.value = value;
  }

  /**
   * 获取设置的名称。
   *
   * @return 设置的名称。
   */
  public String getName() {
    return name;
  }

  /**
   * 获取设置的值。
   *
   * @return 设置的值。
   */
  public String getValue() {
    return value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("SERVER_ERROR", "INVALID_SETTING_VALUE",
        KeyValuePairList.of("name", name, "value", value));
  }
}