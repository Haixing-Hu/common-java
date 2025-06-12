////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import java.io.Serial;

/**
 * 抛出异常以指示指定的字段不存在。
 *
 * @author 胡海星
 */
public class FieldNotExistException extends ReflectionException {

  @Serial
  private static final long serialVersionUID = 5409166838535034334L;

  /**
   * 拥有者类。
   */
  private final Class<?> ownerClass;

  /**
   * 选项。
   */
  private final int options;

  /**
   * 字段名称。
   */
  private final String fieldName;

  /**
   * 构造一个 {@link FieldNotExistException} 实例。
   *
   * @param cls
   *     拥有者类。
   * @param options
   *     选项。
   * @param name
   *     字段名称。
   */
  public FieldNotExistException(final Class<?> cls, final int options,
      final String name) {
    super("There is no "
        + Option.toString(options)
        + " field named with '"
        + name
        + "' for the class "
        + cls.getName());
    this.ownerClass = cls;
    this.options = options;
    this.fieldName = name;
  }

  /**
   * 构造一个 {@link FieldNotExistException} 实例。
   *
   * @param cls
   *     拥有者类。
   * @param name
   *     字段名称。
   */
  public FieldNotExistException(final Class<?> cls, final String name) {
    super("There is no field named with '"
        + name
        + "' for the class "
        + cls.getName());
    this.ownerClass = cls;
    this.options = 0;
    this.fieldName = name;
  }

  /**
   * 获取拥有者类。
   *
   * @return 拥有者类。
   */
  public final Class<?> getOwnerClass() {
    return ownerClass;
  }

  /**
   * 获取选项。
   *
   * @return 选项。
   */
  public final int getOptions() {
    return options;
  }

  /**
   * 获取字段名称。
   *
   * @return 字段名称。
   */
  public final String getFieldName() {
    return fieldName;
  }
}