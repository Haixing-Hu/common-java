////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.config.error;

/**
 * 抛出以表示属性值无效。
 *
 * @author 胡海星
 */
public class InvalidPropertyValueError extends ConfigurationError {

  private static final long serialVersionUID = 7500607643203773303L;

  /**
   * 构造一个新的 {@link InvalidPropertyValueError}。
   *
   * @param name
   *     属性名称。
   * @param value
   *     无效的属性值。
   */
  public InvalidPropertyValueError(final String name, final Object value) {
    super("The property value of '" + name + "' is invalid: " + value);
  }

}