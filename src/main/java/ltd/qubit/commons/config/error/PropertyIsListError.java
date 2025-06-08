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
 * 此错误表示具有指定名称的属性包含一个值列表。
 *
 * @author 胡海星
 */
public class PropertyIsListError extends ConfigurationError {

  private static final long serialVersionUID = 1286137143396514506L;

  private final String propertyName;

  /**
   * 构造一个 {@link PropertyIsListError}。
   *
   * @param propertyName
   *     作为列表的属性的名称。
   */
  public PropertyIsListError(final String propertyName) {
    super("The specified property contains a list of values: " + propertyName);
    this.propertyName = propertyName;
  }

  /**
   * 获取作为列表的属性的名称。
   *
   * @return
   *     作为列表的属性的名称。
   */
  public String getPropertyName() {
    return propertyName;
  }

}