////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.config.error;

import ltd.qubit.commons.lang.Type;

/**
 * 此错误表示具有指定名称的属性的类型不匹配。
 *
 * @author 胡海星
 */
public class PropertyTypeMismatchError extends ConfigurationError {

  private static final long serialVersionUID = 7007685636732043825L;

  private final String propertyName;
  private final Type expectedType;
  private final Type actualType;

  /**
   * 构造一个 {@link PropertyTypeMismatchError}。
   *
   * @param propertyName
   *     属性的名称。
   * @param expectedType
   *     属性的预期类型。
   * @param actualType
   *     属性的实际类型。
   */
  public PropertyTypeMismatchError(final String propertyName,
      final Type expectedType, final Type actualType) {
    super("The type of property '" + propertyName
        + "' mismatch: expected type is " + expectedType
        + " but the actual type is " + actualType);
    this.propertyName = propertyName;
    this.expectedType = expectedType;
    this.actualType = actualType;
  }

  /**
   * 获取属性的名称。
   *
   * @return
   *     属性的名称。
   */
  public String getPropertyName() {
    return propertyName;
  }

  /**
   * 获取属性的预期类型。
   *
   * @return
   *     属性的预期类型。
   */
  public Type getExpectedType() {
    return expectedType;
  }

  /**
   * 获取属性的实际类型。
   *
   * @return
   *     属性的实际类型。
   */
  public Type getActualType() {
    return actualType;
  }

}