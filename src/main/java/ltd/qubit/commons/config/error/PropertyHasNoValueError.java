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
 * 此错误表示具有指定名称的属性没有任何值。
 *
 * @author 胡海星
 */
public class PropertyHasNoValueError extends ConfigurationError {

  private static final long serialVersionUID = - 2617774805970541714L;

  private final String propertyName;

  /**
   * 构造一个新的 {@link PropertyHasNoValueError}。
   *
   * @param propertyName
   *     没有值的属性的名称。
   */
  public PropertyHasNoValueError(final String propertyName) {
    super("The specified property has no value: " + propertyName);
    this.propertyName = propertyName;
  }

  /**
   * 获取没有值的属性的名称。
   *
   * @return
   *     没有值的属性的名称。
   */
  public String getPropertyName() {
    return propertyName;
  }
}