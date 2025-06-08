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
 * 此错误表示具有指定名称的属性不存在。
 *
 * @author 胡海星
 */
public class PropertyNotExistError extends ConfigurationError {

  private static final long serialVersionUID = - 2617774805970541714L;

  private final String propertyName;

  /**
   * 构造一个 {@link PropertyNotExistError}。
   *
   * @param propertyName
   *     不存在的属性的名称。
   */
  public PropertyNotExistError(final String propertyName) {
    super("The specified property does not exist: " + propertyName);
    this.propertyName = propertyName;
  }

  /**
   * 获取不存在的属性的名称。
   *
   * @return
   *     不存在的属性的名称。
   */
  public String getPropertyName() {
    return propertyName;
  }
}